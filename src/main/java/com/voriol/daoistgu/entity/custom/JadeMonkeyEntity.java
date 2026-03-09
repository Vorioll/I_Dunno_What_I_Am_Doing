package com.voriol.daoistgu.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;

public class JadeMonkeyEntity extends PathfinderMob {
    private int invisibilityCooldown = 0;
    private int teleportCooldown = 0;

    public JadeMonkeyEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected void registerGoals() {
        // Базовые цели выживания
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Атака
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));

        // Случайное движение
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));

        // Смотреть на игрока
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 25.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        // Цель - атаковать игрока
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide) {

            if (invisibilityCooldown > 0) invisibilityCooldown--;
            if (teleportCooldown > 0) teleportCooldown--;

            if (this.getTarget() instanceof Player player && player.isAlive()) {
                double distanceToPlayer = this.distanceToSqr(player);

                // 1. НЕВИДИМОСТЬ: когда игрок смотрит на обезьяну
                if (invisibilityCooldown <= 0 && player.hasLineOfSight(this)) {
                    this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 60, 0, false, false));
                    invisibilityCooldown = 150; // 7.5 секунд кулдаун

                }

                // 2. ТЕЛЕПОРТАЦИЯ: когда игрок близко, но не слишком
                if (teleportCooldown <= 0 && distanceToPlayer > 9.0D && distanceToPlayer < 25.0D) {
                    teleportBehindPlayer(player);
                    teleportCooldown = 120; // 6 секунд кулдаун
                }

                // 3. АТАКА: стандартная атака уже работает через MeleeAttackGoal
                // Но можно добавить усиление атаки, если невидима
                if (this.hasEffect(MobEffects.INVISIBILITY)) {
                    // Если невидима - увеличиваем урон (опционально)
                    this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
                } else {
                    this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
                }
            }
        }
    }

    private void teleportBehindPlayer(Player player) {
        // Получаем направление взгляда игрока
        Vec3 lookVec = player.getLookAngle();

        // Вычисляем позицию за спиной игрока (2 блока позади)
        double behindX = player.getX() - lookVec.x * 2.5D;
        double behindZ = player.getZ() - lookVec.z * 2.5D;
        double behindY = player.getY(); // на том же уровне

        // Проверяем, можно ли туда телепортироваться
        for (int i = 0; i < 5; i++) {
            // Немного варьируем позицию, если не получается
            double tryX = behindX + (random.nextInt(3) - 1);
            double tryZ = behindZ + (random.nextInt(3) - 1);

            if (this.randomTeleport(tryX, behindY, tryZ, true)) {
                // Успешно телепортировались
                this.setTarget(player); // Убеждаемся, что цель - этот игрок
                break;
            }
        }
    }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        // Когда обезьяну атакуют, она может стать невидимой (шанс 30%)
        if (!this.level().isClientSide && source.getEntity() instanceof Player) {
            if (this.random.nextFloat() < 0.3f && invisibilityCooldown <= 0) {
                this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 80, 0, false, false));
                invisibilityCooldown = 200;
            }
        }
        return super.hurt(source, amount);
    }
}