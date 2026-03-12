package com.voriol.daoistgu.GuWorms.StarPath;

import com.voriol.daoistgu.GuWorms.GuWormItem;
import com.voriol.daoistgu.GuWorms.GuWormPath;
import com.voriol.daoistgu.GuWorms.GuWormRank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
        return 20;
    }

    @Override
    protected boolean applyAbility(Level level, Player player, ItemStack stack) {
        if (level.isClientSide) return true;

        if (isLightActive(stack)) {
            player.displayClientMessage(Component.literal("Свет уже активен"), true);
            return false;
        }

        CompoundTag tag = getTag(stack); // используем нестатический метод родителя
        tag.putBoolean(TAG_ACTIVE, true);
        tag.putLong(TAG_END_TIME, level.getGameTime() + DURATION_TICKS);
        setTag(stack, tag);

        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!(entity instanceof Player)) return;
        if (level.isClientSide) return;

        if (isLightActive(stack)) {
            long endTime = getEndTime(stack);
            if (level.getGameTime() >= endTime) {
                CompoundTag tag = GuWormItem.getCustomData(stack); // родительский статический метод
                tag.remove(TAG_ACTIVE);
                tag.remove(TAG_END_TIME);
                GuWormItem.setCustomData(stack, tag);
            }
        }
    }

    @Override
    protected int getCooldownTime() {
        return DURATION_TICKS + COOLDOWN_TICKS;
    }

    public static boolean isLightActive(ItemStack stack) {
        CompoundTag tag = GuWormItem.getCustomData(stack); // используем родительский статический метод
        return tag.contains(TAG_ACTIVE) && tag.getBoolean(TAG_ACTIVE);
    }

    private static long getEndTime(ItemStack stack) {
        CompoundTag tag = GuWormItem.getCustomData(stack);
        return tag.contains(TAG_END_TIME) ? tag.getLong(TAG_END_TIME) : 0;
    }
}