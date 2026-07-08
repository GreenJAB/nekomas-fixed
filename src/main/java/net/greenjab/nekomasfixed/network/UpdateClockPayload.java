package net.greenjab.nekomasfixed.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record UpdateClockPayload(int x, int y, int z, int timer, boolean hasBell, boolean showsTime) implements CustomPacketPayload {
    public static final Type<UpdateClockPayload> PACKET_ID = new Type<>(NekomasFixed.id("update_clock"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateClockPayload> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            UpdateClockPayload::x,
            ByteBufCodecs.VAR_INT,
            UpdateClockPayload::y,
            ByteBufCodecs.VAR_INT,
            UpdateClockPayload::z,
            ByteBufCodecs.VAR_INT,
            UpdateClockPayload::timer,
            ByteBufCodecs.BOOL,
            UpdateClockPayload::hasBell,
            ByteBufCodecs.BOOL,
            UpdateClockPayload::showsTime,
            UpdateClockPayload::new
    );


    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }

    public static void register() {
        PayloadTypeRegistry.clientboundPlay().register(PACKET_ID, PACKET_CODEC);
    }
}
