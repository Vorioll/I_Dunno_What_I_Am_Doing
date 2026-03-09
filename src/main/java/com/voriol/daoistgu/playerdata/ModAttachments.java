package com.voriol.daoistgu.playerdata;

import com.voriol.daoistgu.DaoistGu;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, DaoistGu.MOD_ID);

    public static final Supplier<AttachmentType<PlayerGenes>> PLAYER_GENES =
            ATTACHMENT_TYPES.register("player_genes",
                    () -> AttachmentType.builder(PlayerGenes::new)
                            .serialize(PlayerGenes.CODEC, genes -> true)  // Predicate всегда true
                            .build()
            );

    public static void register(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }
}