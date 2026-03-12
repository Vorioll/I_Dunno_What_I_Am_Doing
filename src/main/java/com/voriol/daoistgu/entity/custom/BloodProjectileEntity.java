package com.voriol.daoistgu.entity.custom;

import com.voriol.daoistgu.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class BloodProjectileEntity extends AbstractHurtingProjectile {

    private float damage = 4f;
    private float aoeRadius = 2f;

    public BloodProjectileEntity(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public BloodProjectileEntity(Level level, double xDir, double yDir, double zDir) {
        super(ModEntities.BLOOD_PROJECTILE.get(), level);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setAoeRadius(float radius) {
        this.aoeRadius = radius;
    }

    @Override
    public void tick() {
        super.tick();

        // кровавый след
        if (level().isClientSide) {
            level().addParticle(
                    ParticleTypes.DAMAGE_INDICATOR,
                    getX(),
                    getY(),
                    getZ(),
                    0,
                    0,
                    0
            );
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {

        Entity target = result.getEntity();
        Entity owner = getOwner();

        DamageSource source = damageSources().indirectMagic(this, owner);

        target.hurt(source, damage);

        applyAoeDamage(source);

        spawnImpactParticles();

        discard();
    }

    @Override
    protected void onHit(HitResult result) {

        if (result.getType() == HitResult.Type.BLOCK) {

            DamageSource source = damageSources().magic();

            applyAoeDamage(source);
            spawnImpactParticles();

            discard();
        }
    }

    private void applyAoeDamage(DamageSource source) {

        float aoeDamage = damage / 2f;

        AABB box = new AABB(
                getX() - aoeRadius,
                getY() - aoeRadius,
                getZ() - aoeRadius,
                getX() + aoeRadius,
                getY() + aoeRadius,
                getZ() + aoeRadius
        );

        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, box);

        Entity owner = getOwner();

        for (LivingEntity entity : entities) {

            if (entity == owner) continue;

            entity.hurt(source, aoeDamage);
        }
    }

    private void spawnImpactParticles() {

        if (!(level() instanceof ServerLevel server)) return;

        server.sendParticles(
                ParticleTypes.DAMAGE_INDICATOR,
                getX(),
                getY(),
                getZ(),
                20,
                0.5,
                0.5,
                0.5,
                0.1
        );
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}