package com.voriol.daoistgu.playerdata;
import com.voriol.daoistgu.DaoistGu;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = DaoistGu.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                SyncGenesPayload.TYPE,
                SyncGenesPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> ClientGenesData.set(payload.genes()));
                }
        );
    }
}