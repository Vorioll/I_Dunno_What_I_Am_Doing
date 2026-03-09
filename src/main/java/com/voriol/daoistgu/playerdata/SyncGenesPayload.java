package com.voriol.daoistgu.playerdata;

import com.voriol.daoistgu.DaoistGu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import net.minecraft.network.codec.ByteBufCodecs;



public record SyncGenesPayload(PlayerGenes genes) implements CustomPacketPayload {
    public static final Type<SyncGenesPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(DaoistGu.MOD_ID, "sync_genes"));

    public static final StreamCodec<FriendlyByteBuf, SyncGenesPayload> STREAM_CODEC = StreamCodec.of(
            (buf, payload) -> {
                // Кодируем PlayerGenes в CompoundTag через Codec
                CompoundTag tag = (CompoundTag) PlayerGenes.CODEC.encodeStart(NbtOps.INSTANCE, payload.genes())
                        .result()
                        .orElseThrow(() -> new RuntimeException("Failed to encode genes"));
                buf.writeNbt(tag);
            },
            buf -> {
                // Читаем CompoundTag и декодируем обратно в PlayerGenes
                CompoundTag tag = buf.readNbt();
                PlayerGenes genes = PlayerGenes.CODEC.parse(NbtOps.INSTANCE, tag)
                        .result()
                        .orElseThrow(() -> new RuntimeException("Failed to decode genes"));
                return new SyncGenesPayload(genes);
            }
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}