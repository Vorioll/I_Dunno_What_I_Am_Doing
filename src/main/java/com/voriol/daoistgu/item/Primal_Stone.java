package com.voriol.daoistgu.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Primal_Stone extends Item {
    public Primal_Stone(Properties properties) {
        super(properties);
    }
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        return InteractionResultHolder.consume(itemStack);
    }
}
