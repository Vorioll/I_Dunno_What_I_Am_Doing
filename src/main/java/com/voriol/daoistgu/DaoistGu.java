package com.voriol.daoistgu;

import com.voriol.daoistgu.block.ModBlocks;
import com.voriol.daoistgu.entity.ModEntities;
import com.voriol.daoistgu.item.ModCreativeModTabs;
import com.voriol.daoistgu.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(DaoistGu.MOD_ID)
public class DaoistGu {
    public static final String MOD_ID = "daoistgu";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DaoistGu(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        // Регистрируем все DeferredRegister
        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);

        // Добавляем слушатели событий
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerAttributes);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Общая инициализация
    }

    // Регистрация атрибутов сущностей
    private void registerAttributes(EntityAttributeCreationEvent event) {
        ModEntities.registerAttributes(event);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.BISMUTH);
            event.accept(ModItems.PRIMORDIAL_STONE);
            event.accept(ModItems.RAW_BISMUTH);
            event.accept(ModItems.JADE);
            event.accept(ModItems.JADE_COIN);
            event.accept(ModItems.BASE_COIN);
            event.accept(ModItems.VINE_255);
            event.accept(ModItems.VINE_2);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.BISMUTH_BLOCK);
            event.accept(ModBlocks.BISMUTH_ORE);
            event.accept(ModBlocks.BISMUTH_DEEPSLATE_ORE);
            event.accept(ModBlocks.JADE_BLOCK);
            event.accept(ModBlocks.JADE_ORE);
            event.accept(ModBlocks.JADE_DEEPSLATE_ORE);
            event.accept(ModBlocks.MAGIC_BLOCK);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting!");
    }

    @EventBusSubscriber(modid = DaoistGu.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // Клиентская инициализация
        }
    }
}