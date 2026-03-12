package com.voriol.daoistgu.GuWorms.StarPath;

import com.voriol.daoistgu.GuWorms.GuWormItem;
import com.voriol.daoistgu.GuWorms.GuWormPath;
import com.voriol.daoistgu.GuWorms.GuWormRank;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class LightGu extends GuWormItem {

    private static final String TAG_ACTIVE = "lightActive";
    private static final String TAG_END_TIME = "lightEndTime";

    private static final int DURATION_TICKS = 60 * 20;
    private static final int COOLDOWN_TICKS = 20 * 10;

    public LightGu(Properties properties, GuWormRank rank, Supplier<net.minecraft.world.item.Item> foodItem) {
        super(properties, rank, GuWormPath.STAR, foodItem);
    }

    @Override
    protected int getSatietyCost() {
        return 20; // Активация требует 20 сытости
    }

    @Override
    protected boolean applyAbility(Level level, Player player, ItemStack stack) {
        if (level.isClientSide) return true;

        // Сытость уже проверена в GuWormItem.use() через getSatietyCost()
        if (isLightActive(stack)) {
            player.displayClientMessage(Component.literal("Свет уже активен"), true);
            return false;
        }

        CompoundTag tag = getTag(stack);
        tag.putBoolean(TAG_ACTIVE, true);
        tag.putLong(TAG_END_TIME, level.getGameTime() + DURATION_TICKS);
        setTag(stack, tag);

        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected); // ← обязательно!
        if (!(entity instanceof Player)) return;
        if (level.isClientSide) return;

        if (isLightActive(stack)) {
            long endTime = getEndTime(stack);
            if (level.getGameTime() >= endTime) {
                // Время вышло — деактивируем
                CompoundTag tag = getCustomData(stack).copyTag();
                tag.remove(TAG_ACTIVE);
                tag.remove(TAG_END_TIME);
                setCustomData(stack, tag);
            }
        }
    }

    @Override
    protected int getCooldownTime() {
        return DURATION_TICKS + COOLDOWN_TICKS; // 1200 + 200 = 1400 тиков (70 секунд)
    }

    /**
     * Метод для клиента: возвращает true, если способность активна.
     */
    public static boolean isLightActive(ItemStack stack) {
        CompoundTag tag = getCustomData(stack).copyTag();
        return tag.contains(TAG_ACTIVE) && tag.getBoolean(TAG_ACTIVE);
    }

    private static long getEndTime(ItemStack stack) {
        CompoundTag tag = getCustomData(stack).copyTag();
        return tag.contains(TAG_END_TIME) ? tag.getLong(TAG_END_TIME) : 0;
    }

    private static CustomData getCustomData(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    }

    private static void setCustomData(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
}