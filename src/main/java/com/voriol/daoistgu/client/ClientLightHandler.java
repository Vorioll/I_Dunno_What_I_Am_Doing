package com.voriol.daoistgu.client;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.GuWorms.ModWorms;
import com.voriol.daoistgu.GuWorms.StarPath.LightGu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.thinkingstudio.ryoamiclights.api.item.ItemLightSource;
import org.thinkingstudio.ryoamiclights.api.item.ItemLightSources;

@EventBusSubscriber(modid = DaoistGu.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientLightHandler {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("ryoamiclights")) {
            System.out.println("RyoamicLights detected, registering light sources...");
            registerLightSources();
        } else {
            System.out.println("RyoamicLights not detected, skipping registration.");
        }
    }

    private static void registerLightSources() {
        // Создаём экземпляр анонимного класса, расширяющего ItemLightSource
        ItemLightSource source = new ItemLightSource(
                ResourceLocation.fromNamespaceAndPath(DaoistGu.MOD_ID, "light_gu"),
                ModWorms.LIGHT_GU_RANK_1.get(),
                false // waterSensitive: false, так как свет не зависит от воды
        ) {
            @Override
            public int getLuminance(ItemStack stack) {
                int baseLight = 3;
                if (stack.getItem() instanceof LightGu gu) {
                    baseLight += gu.getRank().getLevel(); // предполагаем, что getRank() есть
                }

                boolean active = LightGu.isLightActive(stack);
                System.out.println("LightGu luminance check: active=" + active + ", baseLight=" + baseLight);

                if (active) {
                    int activeBonus = 5;
                    if (stack.getItem() instanceof LightGu gu) {
                        activeBonus += gu.getRank().getLevel() * 2;
                    }
                    return Math.min(15, baseLight + activeBonus);
                } else {
                    return Math.min(15, baseLight);
                }
            }
        };

        // Регистрируем источник света
        ItemLightSources.registerItemLightSource(source);
    }
}