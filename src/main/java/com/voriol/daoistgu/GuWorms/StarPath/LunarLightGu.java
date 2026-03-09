package com.voriol.daoistgu.GuWorms.StarPath;

import com.voriol.daoistgu.GuWorms.GuWormItem;
import com.voriol.daoistgu.GuWorms.GuWormPath;
import com.voriol.daoistgu.GuWorms.GuWormRank;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LunarLightGu extends GuWormItem {

    private static final double DISTANCE = 15.0;

    public LunarLightGu(Properties properties, GuWormRank rank, java.util.function.Supplier<net.minecraft.world.item.Item> foodItem) {
        super(properties, rank, GuWormPath.STAR, foodItem);
    }

    @Override
    protected boolean applyAbility(Level level, Player player, ItemStack stack) {
        if (level.isClientSide) return true;

        // Получаем бонус от активного света
        int lightCount = LightGu.getActiveLightCount(player);
        double damage = getBaseDamageByRank() * (1.0 + 0.5 * lightCount);

        Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 direction = player.getLookAngle().normalize();

        if (level instanceof ServerLevel serverLevel) {
            for (int i = 1; i <= DISTANCE; i++) {
                Vec3 point = start.add(direction.scale(i));

                serverLevel.sendParticles(ParticleTypes.END_ROD,
                        point.x, point.y, point.z,
                        1, 0, 0, 0, 0);

                List<Entity> entities = serverLevel.getEntities(player,
                        player.getBoundingBox().move(direction.scale(i)).inflate(0.5),
                        e -> e != player && e.isAlive());
                for (Entity e : entities) {
                    e.hurt(e.damageSources().playerAttack(player), (float) damage);
                }
            }
        }

        // Звук тихий, как у аметиста
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.2f, 1.2f);

        return true;
    }

    @Override
    protected int getCooldownTime() {
        return Math.max(20, 60 - rank.getLevel() * 5);
    }

    private double getBaseDamageByRank() {
        return 7.0 + rank.getLevel() * 1.5;
    }
}