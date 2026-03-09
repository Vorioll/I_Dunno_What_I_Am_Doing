package com.voriol.daoistgu.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import net.minecraft.ChatFormatting;

import java.util.List;

public class DaoIngredients extends Item {
    protected int rank;
    private final String type;

    public DaoIngredients(Properties properties, int rank, String type) {
        super(properties);
        this.rank = rank;

        if (type.equals("plant") || type.equals("ore") || type.equals("meat") ||
                type.equals("heaven") || type.equals("hell") || type.equals("alcohol")) {
            this.type = type;
        } else {
            this.type = "unknown";
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        String typeKey = "tooltip.daoistgu.type." + type;
        tooltipComponents.add(Component.translatable("tooltip.daoistgu.rank", rank)
                .withStyle(ChatFormatting.GOLD));

        tooltipComponents.add(Component.translatable("tooltip.daoistgu.type",
                        Component.translatable(typeKey))
                .withStyle(ChatFormatting.DARK_GREEN));
    }
}