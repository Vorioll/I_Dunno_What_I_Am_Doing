package com.voriol.daoistgu.GuWorms.BloodPath;

import com.voriol.daoistgu.GuWorms.GuWormItem;
import com.voriol.daoistgu.GuWorms.GuWormPath;
import com.voriol.daoistgu.GuWorms.GuWormRank;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class BloodArrowGu extends GuWormItem {

    public BloodArrowGu(Properties properties, GuWormRank rank, Supplier<Item> foodItem) {
        super(properties, rank, GuWormPath.BLOOD, foodItem);
    }

    @Override
    protected boolean applyAbility(Level level, Player player, ItemStack stack) {

        float hp = player.getHealth();

        if (hp <= 1f) {
            return false;
        }

        float cost = 0.5f*rank.getLevel();
        player.hurt(player.damageSources().magic(), cost);

        double damage = getDamageByRank();

        Arrow arrow = new Arrow(EntityType.ARROW, level);

        arrow.setOwner(player);
        arrow.setBaseDamage(damage);

        arrow.setPos(
                player.getX(),
                player.getEyeY() - 0.1,
                player.getZ()
        );

        arrow.shootFromRotation(
                player,
                player.getXRot(),
                player.getYRot(),
                0,
                getSpeedByRank(),
                0
        );

        level.addFreshEntity(arrow);

        spawnBloodParticles(level, player);

        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS,
                1f,
                0.8f
        );

        return true;
    }

    private double getDamageByRank() {
        int r = rank.getLevel();
        return 5 + r * 2;
    }

    private float getSpeedByRank() {
        return 2.2f + rank.getLevel() * 0.15f;
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