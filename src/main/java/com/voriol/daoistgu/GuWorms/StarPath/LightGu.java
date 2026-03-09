package com.voriol.daoistgu.GuWorms.StarPath;

import com.voriol.daoistgu.GuWorms.GuWormItem;
import com.voriol.daoistgu.GuWorms.GuWormPath;
import com.voriol.daoistgu.GuWorms.GuWormRank;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.*;

public class LightGu extends GuWormItem {

    private static final int DURATION_TICKS = 60 * 20; // 60 секунд
    private static final int COOLDOWN_TICKS = 20 * 10; // 10 секунд после окончания
    private static final Map<UUID, LightArea> activeAreas = new HashMap<>();

    public LightGu(Properties properties, GuWormRank rank, java.util.function.Supplier<net.minecraft.world.item.Item> foodItem) {
        super(properties, rank, GuWormPath.STAR, foodItem);
    }

    @Override
    protected boolean applyAbility(Level level, Player player, ItemStack stack) {
        if (level.isClientSide) return true;

        // Проверка кормления
        int satiety = getSatiety(stack);
        if (satiety < 20) {
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Червь голоден!"), true);
            return false;
        }

        UUID id = player.getUUID();
        LightArea area = activeAreas.get(id);

        // Проверка перезарядки
        if (area != null && area.ticksLeft > 0) {
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Свет ещё активен!"), true);
            return false;
        }

        int radius = 5 + rank.getLevel() * 2;
        activeAreas.put(id, new LightArea(player, radius, DURATION_TICKS, COOLDOWN_TICKS));

        setSatiety(stack, satiety - 20); // тратим корм

        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (!(entity instanceof Player player)) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

        LightArea area = activeAreas.get(player.getUUID());
        if (area == null) return;

        if (area.ticksLeft > 0) {
            area.ticksLeft--;
            area.update(serverLevel);
        } else if (area.cooldownLeft > 0) {
            area.cooldownLeft--;
        } else {
            area.clear(serverLevel);
            activeAreas.remove(player.getUUID());
        }
    }

    @Override
    protected int getCooldownTime() {
        return COOLDOWN_TICKS;
    }

    public static int getActiveLightCount(Player player) {
        LightArea area = activeAreas.get(player.getUUID());
        if (area != null && area.ticksLeft > 0) return area.radius / 2;
        return 0;
    }

    private static class LightArea {
        final Player player;
        final int radius;
        int ticksLeft;
        int cooldownLeft;
        final Set<BlockPos> lastLightBlocks = new HashSet<>();

        LightArea(Player player, int radius, int duration, int cooldown) {
            this.player = player;
            this.radius = radius;
            this.ticksLeft = duration;
            this.cooldownLeft = cooldown;
        }

        void update(ServerLevel level) {
            clear(level); // удаляем старые блоки

            BlockPos center = player.blockPosition();
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        if (dx*dx + dy*dy + dz*dz <= radius*radius) {
                            BlockPos pos = center.offset(dx, dy, dz);
                            if (level.getBlockState(pos).isAir()) {
                                level.setBlock(pos, Blocks.LIGHT.defaultBlockState(), 3);
                                lastLightBlocks.add(pos);
                            }
                        }
                    }
                }
            }
        }

        void clear(ServerLevel level) {
            for (BlockPos pos : lastLightBlocks) {
                if (level.getBlockState(pos).is(Blocks.LIGHT)) {
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
            lastLightBlocks.clear();
        }
    }
}