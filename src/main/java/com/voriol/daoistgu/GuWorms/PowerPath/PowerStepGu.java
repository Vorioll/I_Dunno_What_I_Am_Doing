package com.voriol.daoistgu.GuWorms.PowerPath;

import com.voriol.daoistgu.GuWorms.GuWormItem;
import com.voriol.daoistgu.GuWorms.GuWormPath;
import com.voriol.daoistgu.GuWorms.GuWormRank;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Supplier;

public class PowerStepGu extends GuWormItem {

    public PowerStepGu(Properties properties, GuWormRank rank, Supplier<Item> foodItem) {
        super(properties, rank, GuWormPath.POWER, foodItem);
    }

    @Override
    protected boolean applyAbility(Level level, Player player, ItemStack stack) {
        if (level.isClientSide) return true; // вся логика только на сервере

        int rankLevel = rank.getLevel();

        // Параметры способности, зависящие от ранга
        double radius = 3.0 + rankLevel * 3.5;          // радиус поражения
        float damage = 2.0f + rankLevel * 3.0f;         // урон
        int slowDuration = (2 + rankLevel) * 20;        // длительность замедления в тиках (20 тиков = 1 сек)
        int slowAmplifier = Math.min(2, rankLevel - 1); // уровень замедления: 0 = замедление I, 1 = II, 2 = III

        // Собираем всех живых существ в радиусе, кроме самого игрока
        AABB box = player.getBoundingBox().inflate(radius);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, box,
                e -> e != player && e.isAlive());

        for (LivingEntity target : targets) {
            // Проверяем точное расстояние (из-за квадратной области AABB)
            if (target.distanceToSqr(player) > radius * radius) continue;

            // Наносим магический урон (аналогично BloodArrowGu)
            target.hurt(target.damageSources().magic(), damage);

            // Накладываем эффект замедления
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, slowDuration, slowAmplifier));
        }

        // Визуальные и звуковые эффекты "сотрясения земли"
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0f, 1.0f);

        if (level instanceof ServerLevel serverLevel) {
            // Частицы взрыва в центре
            serverLevel.sendParticles(ParticleTypes.EXPLOSION,
                    player.getX(), player.getY() + 0.5, player.getZ(),
                    1, 0, 0, 0, 0);

            // Облако пыли/дыма, имитирующее ударную волну
            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    player.getX(), player.getY() + 0.2, player.getZ(),
                    30, radius, 0.2, radius, 0.02);
        }

        return true;
    }

    @Override
    protected int getCooldownTime() {
        // Перезарядка уменьшается с ростом ранга: от 70 до 30 тиков
        return Math.max(30, 300 - rank.getLevel() * 30);
    }
}