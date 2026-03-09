package com.voriol.daoistgu.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties RADISH = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 400), 0.35f).build();

    //public static final FoodProperties WINE = new FoodProperties.Builder().nutrition(2)
    //        .saturationModifier(0.15f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400),0.35f)
    //        .effect(() -> new MobEffectInstance(MobEffects.CONFUSION,200),1).build();
}
