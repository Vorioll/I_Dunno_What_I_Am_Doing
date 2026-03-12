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
    private static final int RELOAD_TICKS = 70 * 20;

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
        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;

        if (!isLightActive(stack)) return;

        long endTime = getEndTime(stack);
        if (level.getGameTime() >= endTime) {
            CompoundTag tag = getTag(stack);
            tag.remove(TAG_ACTIVE);
            tag.remove(TAG_END_TIME);
            setTag(stack, tag);
        }
    }

    @Override
    protected int getCooldownTime() {
        return RELOAD_TICKS;
    }

    public static boolean isLightActive(ItemStack stack) {
        return getCustomData(stack).getBoolean(TAG_ACTIVE);
    }

    public static long getEndTime(ItemStack stack) {
        return getCustomData(stack).getLong(TAG_END_TIME);
    }
}