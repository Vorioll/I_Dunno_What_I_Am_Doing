package com.voriol.daoistgu.entity;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.entity.client.model.JadeMonkeyModel;
import com.voriol.daoistgu.entity.client.render.JadeMonkeyRenderer;
import com.voriol.daoistgu.entity.custom.JadeMonkeyEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, DaoistGu.MOD_ID);

    public static final Supplier<EntityType<JadeMonkeyEntity>> JADE_MONKEY =
            ENTITIES.register("jade_monkey",
                    () -> EntityType.Builder.of(JadeMonkeyEntity::new, MobCategory.CREATURE)
                            .sized(0.9f, 1.75f)   // размер хитбокса
                            .build("jade_monkey")
            );

    // Регистрация атрибутов (вызывается из главного класса)
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(JADE_MONKEY.get(), JadeMonkeyEntity.createAttributes().build());
    }

    // Клиентские регистрации – выполняются только на клиенте
    @EventBusSubscriber(modid = DaoistGu.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(JADE_MONKEY.get(), JadeMonkeyRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(JadeMonkeyModel.LAYER_LOCATION, JadeMonkeyModel::createBodyLayer);
        }
    }
}