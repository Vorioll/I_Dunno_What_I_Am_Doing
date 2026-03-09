package com.voriol.daoistgu.item;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.GuWorms.ModWorms;
import com.voriol.daoistgu.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DaoistGu.MOD_ID);


    public static final Supplier<CreativeModeTab> BISMUTH_ITEMS_TAB = CREATIVE_MODE_TAB.register("bismuth_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BISMUTH.get()))
                    .title(Component.translatable("creativetab.daoistgu.bismuth_items"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.BISMUTH);
                        output.accept(ModItems.RAW_BISMUTH);
                        output.accept(ModItems.CHISEL);

                    }).build());

    public static final Supplier<CreativeModeTab> BISMUTH_BLOCK_TAB = CREATIVE_MODE_TAB.register("bismuth_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.BISMUTH_BLOCK.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(DaoistGu.MOD_ID, "bismuth_items_tab"))
                    .title(Component.translatable("creativetab.daoistgu.bismuth_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModBlocks.BISMUTH_BLOCK);
                        output.accept(ModBlocks.BISMUTH_ORE);
                        output.accept(ModBlocks.BISMUTH_DEEPSLATE_ORE);
                        output.accept(ModBlocks.JADE_BLOCK);
                        output.accept(ModBlocks.JADE_ORE);
                        output.accept(ModBlocks.JADE_DEEPSLATE_ORE);
                        output.accept(ModBlocks.MAGIC_BLOCK);

                    }).build());

    public static final Supplier<CreativeModeTab> DAO_INGREDIENTS_TAB = CREATIVE_MODE_TAB.register("dao_ingredients_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PRIMORDIAL_STONE.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(DaoistGu.MOD_ID, "bismuth_blocks_tab"))
                    .title(Component.translatable("creativetab.daoistgu.dao_ingredients"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.JADE_COIN);
                        output.accept(ModItems.BASE_COIN);
                        output.accept(ModItems.PRIMORDIAL_STONE);
                        output.accept(ModItems.VINE_2);
                        output.accept(ModItems.VINE_255);

                    }).build());
    public static final Supplier<CreativeModeTab> GU_WORMS_TAB = CREATIVE_MODE_TAB.register("gu_worms_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VINE_255.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(DaoistGu.MOD_ID, "dao_ingredients_tab"))
                    .title(Component.translatable("creativetab.daoistgu.gu_worms"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept((ItemLike) ModWorms.BLOOD_ARROW_GU_RANK_1);
                        output.accept((ItemLike) ModWorms.BLOOD_ARROW_GU_RANK_5);
                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
