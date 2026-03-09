package com.voriol.daoistgu.GuWorms;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public abstract class GuWormItem extends Item {

    protected final GuWormRank rank;
    protected final GuWormPath path;
    protected final Supplier<Item> foodItem;

    private static final String SATIETY_TAG = "satiety";
    private static final String COOLDOWN_TAG = "cooldown";

    private static final int MAX_SATIETY = 100;
    private static final int SATIETY_COST = 0;

    public GuWormItem(Properties properties, GuWormRank rank, GuWormPath path, Supplier<Item> foodItem) {
        super(properties.stacksTo(1));
        this.rank = rank;
        this.path = path;
        this.foodItem = foodItem;
    }

    private CompoundTag getTag(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.copyTag();
    }

    private void setTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    protected int getSatiety(ItemStack stack) {
        CompoundTag tag = getTag(stack);
        return tag.getInt(SATIETY_TAG);
    }

    protected void setSatiety(ItemStack stack, int value) {
        CompoundTag tag = getTag(stack);
        tag.putInt(SATIETY_TAG, Math.min(value, MAX_SATIETY));
        setTag(stack, tag);
    }

    protected int getCooldown(ItemStack stack) {
        CompoundTag tag = getTag(stack);
        return tag.getInt(COOLDOWN_TAG);
    }

    protected void setCooldown(ItemStack stack, int ticks) {
        CompoundTag tag = getTag(stack);
        tag.putInt(COOLDOWN_TAG, Math.max(ticks, 0));
        setTag(stack, tag);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {

        if (!level.isClientSide) {

            int cd = getCooldown(stack);

            if (cd > 0) {
                setCooldown(stack, cd - 1);
            }

        }

    }

    @Override
    public void onCraftedBy(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player) {

        setSatiety(stack, MAX_SATIETY);
        setCooldown(stack, 0);

    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.success(stack);
        }

        ItemStack offhand = player.getOffhandItem();

        // ===== КОРМЛЕНИЕ =====
        if (feed(stack, offhand)) {

            if (!player.getAbilities().instabuild) {
                offhand.shrink(1);
            }

            player.displayClientMessage(
                    Component.literal("Червь накормлен."),
                    true
            );

            return InteractionResultHolder.success(stack);
        }

        // ===== ПРОВЕРКА КУЛДАУНА =====
        int cd = getCooldown(stack);

        if (cd > 0) {

            player.displayClientMessage(
                    Component.literal("Червь восстанавливается. Осталось " + (cd / 20) + " сек."),
                    true
            );

            return InteractionResultHolder.fail(stack);
        }

        // ===== ПРОВЕРКА СЫТОСТИ =====
        int satiety = getSatiety(stack);

        if (satiety < SATIETY_COST) {

            player.displayClientMessage(
                    Component.literal("Червь голоден! Накормите его ")
                            .append(Component.translatable(foodItem.get().getDescriptionId())),
                    true
            );

            return InteractionResultHolder.fail(stack);
        }

        // ===== АКТИВАЦИЯ СПОСОБНОСТИ =====
        boolean success = applyAbility(level, player, stack);

        if (success) {

            setSatiety(stack, satiety - SATIETY_COST);
            setCooldown(stack, getCooldownTime());

            return InteractionResultHolder.consume(stack);
        }

        return InteractionResultHolder.fail(stack);
    }

    public boolean feed(ItemStack wormStack, ItemStack foodStack) {

        if (foodStack.getItem() == foodItem.get()) {

            int current = getSatiety(wormStack);

            setSatiety(wormStack, current + 20);

            return true;
        }

        return false;
    }

    protected abstract boolean applyAbility(Level level, Player player, ItemStack stack);

    protected abstract int getCooldownTime();

    @Override
    public void appendHoverText(@NotNull ItemStack stack,
                                @NotNull TooltipContext context,
                                @NotNull List<Component> tooltip,
                                @NotNull TooltipFlag flag) {

        // Ранг - пока оставляем как есть, но тоже можно будет перевести позже
        tooltip.add(Component.literal("§7Ранг: §f" + rank.getLevel()));

        // ИСПРАВЛЕНО: Путь теперь использует перевод
        tooltip.add(Component.literal("§7Путь: §f")
                .append(Component.translatable(path.getTranslationKey())));

        int satiety = getSatiety(stack);
        tooltip.add(Component.literal("§7Сытость: §f" + satiety + "/" + MAX_SATIETY));

        int cd = getCooldown(stack);
        if (cd > 0) {
            tooltip.add(Component.literal("§7Перезарядка: §f" + (cd / 20) + " сек."));
        }

        tooltip.add(
                Component.literal("§7Корм: §f")
                        .append(Component.translatable(foodItem.get().getDescriptionId()))
        );

        if (satiety < 20) {
            tooltip.add(Component.literal("§cЧервь голоден"));
        }
    }

}