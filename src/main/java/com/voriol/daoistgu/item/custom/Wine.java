package com.voriol.daoistgu.item.custom;

import com.voriol.daoistgu.item.DaoIngredients;
import com.voriol.daoistgu.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class Wine extends DaoIngredients {

    public Wine(Properties properties, int rank, String type) {
        super(properties, rank, type);
    }

    // Фабричный метод для создания свойств предмета с правильной едой
    public static Item.Properties createProperties(int rank) {
        return new Item.Properties()
                .food(createFoodProperties(rank));
    }

    // Метод для создания FoodProperties на основе ранга
    private static FoodProperties createFoodProperties(int rank) {
        FoodProperties.Builder builder = new FoodProperties.Builder();

        // Базовые параметры зависят от ранга
        builder.nutrition(rank * 2)  // Питательность растёт с рангом
                .saturationModifier(0.1F * rank) // Насыщение тоже
                .usingConvertsTo(ModItems.WINE_BOTTLE.get());

        // Эффекты зависят от ранга
        switch (rank) {
            case 1:
                builder.effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100, 0), 0.8F);
                break;
            case 2:
                builder.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0), 0.8F);
                break;
            case 3:
                builder.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1), 0.9F);
                builder.effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 0.5F);
                break;
            case 4:
                builder.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 2), 1.0F);
                builder.effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 0.7F);
                builder.effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 0.4F);
                break;
            default:
                // Для рангов выше 4 (если понадобятся)
                builder.nutrition(rank * 3)
                        .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, rank * 100, rank/2), 1.0F);
                break;
        }

        return builder.build();
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide && entity instanceof Player player) {
            // Получаем ранг через геттер из DaoIngredients
            int rank = this.rank;

            // Добавляем дополнительный эффект после питья
            // Например, скорость или что-то тематическое для вина
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, rank * 100, 0));

            // Можно добавить сообщение в чат для проверки (потом убрать)
            // player.sendSystemMessage(Component.literal("Вы выпили вино ранга " + rank));
        }

        return result;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // Разрешаем использовать только если можно есть (голод не полный)
        if (player.canEat(false)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(player.getItemInHand(hand));
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }
}