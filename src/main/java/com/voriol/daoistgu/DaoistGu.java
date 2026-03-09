package com.voriol.daoistgu;

import com.voriol.daoistgu.GuWorms.ModWorms;
import com.voriol.daoistgu.block.ModBlocks;
import com.voriol.daoistgu.entity.ModEntities;
import com.voriol.daoistgu.item.ModCreativeModTabs;
import com.voriol.daoistgu.item.ModItems;
import com.voriol.daoistgu.playerdata.ModAttachments;
import com.voriol.daoistgu.playerdata.PlayerGenes;
import com.voriol.daoistgu.playerdata.SyncGenesPayload;
import net.minecraft.server.level.ServerPlayer;
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
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(DaoistGu.MOD_ID)
public class DaoistGu {
    public static final String MOD_ID = "daoistgu";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DaoistGu(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        // Регистрируем слушатель события входа игрока
        NeoForge.EVENT_BUS.addListener(this::onPlayerLogin);

        // Регистрируем все DeferredRegister
        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);

        ModWorms.register(modEventBus);
        ModAttachments.register(modEventBus);

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
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.JADE_MONKEY_SPAWN_EGG);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting!");
    }

    /**
     * Обработчик входа игрока в мир.
     * Отправляет клиенту текущие значения генов для отображения HUD.
     */
    private void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            syncGenes(serverPlayer);
        }
    }

    /**
     * Синхронизирует гены игрока с клиентом.
     * Вызывайте этот метод при любом изменении генов.
     *
     * @param player игрок, которому нужно отправить обновление
     */
    public static void syncGenes(ServerPlayer player) {
        PlayerGenes genes = player.getData(ModAttachments.PLAYER_GENES);
        player.connection.send(new SyncGenesPayload(genes));
        LOGGER.debug("Synced genes for player: {}", player.getName().getString());
    }

    @EventBusSubscriber(modid = DaoistGu.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // Клиентская инициализация (здесь можно добавить что-то позже)
        }
    }
}