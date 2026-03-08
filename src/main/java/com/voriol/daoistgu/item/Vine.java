package com.voriol.daoistgu.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Vine extends DaoIngredients {
    private int power = 0;
    public Vine(Properties properties, int power, int rank, String type) {
        super(properties, rank, type); // передаём все нужные параметры
        this.power = power;
    }
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        if (!level.isClientSide) {
            // добавить эффекты
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, power)); // сила I на 30 сек
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, power)); // тошнота на 20 сек
        }
        return InteractionResultHolder.consume(itemStack);
    }
}
