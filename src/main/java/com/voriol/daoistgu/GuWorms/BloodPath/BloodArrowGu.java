package com.voriol.daoistgu.GuWorms.BloodPath;

import com.voriol.daoistgu.GuWorms.GuWormPath;
import com.voriol.daoistgu.GuWorms.GuWormRank;
import com.voriol.daoistgu.entity.custom.BloodProjectileEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class BloodArrowGu extends BloodPath {

    public BloodArrowGu(Properties properties, GuWormRank rank, Supplier<Item> foodItem) {
        super(properties, rank, GuWormPath.BLOOD, foodItem, 2);
    }

    @Override
    protected int getSatietyCost() {
        return 0;
    }

    @Override
    protected boolean applyAbility(Level level, Player player, ItemStack stack) {

        float cost = 1f * rank.getLevel();
        player.hurt(player.damageSources().magic(), cost);

        float damage = (float) getDamageByRank();

        float aoeRadius = 2f + rank.getLevel() * 0.2f;

        Vec3 look = player.getLookAngle();
        float speed = getSpeedByRank();

        BloodProjectileEntity projectile = new BloodProjectileEntity(
                level,
                look.x,
                look.y,
                look.z
        );

        projectile.setPos(
                player.getX(),
                player.getEyeY() - 0.1,
                player.getZ()
        );

        projectile.setDeltaMovement(
                look.x * speed,
                look.y * speed,
                look.z * speed
        );

        projectile.setDamage(damage);
        projectile.setAoeRadius(aoeRadius);

        level.addFreshEntity(projectile);

        spawnBloodParticles(level, player);

        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.GENERIC_DRINK,
                SoundSource.PLAYERS,
                1f,
                0.8f
        );

        return true;
    }

    private float getSpeedByRank() {
        return 0.4f + rank.getLevel() * 0.05f;
    }

    private void spawnBloodParticles(Level level, Player player) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        int count = 6 + rank.getLevel() * 2;

        serverLevel.sendParticles(
                ParticleTypes.DAMAGE_INDICATOR,
                player.getX(),
                player.getY() + 1,
                player.getZ(),
                count,
                0.3,
                0.3,
                0.3,
                0.02
        );
    }

    @Override
    protected int getCooldownTime() {
        return Math.max(40, 80 - rank.getLevel() * 10);
    }
}