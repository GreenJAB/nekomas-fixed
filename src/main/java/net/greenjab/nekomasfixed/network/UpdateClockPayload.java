package net.greenjab.nekomasfixed.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record UpdateClockPayload(int x, int y, int z, int timer, boolean hasBell, boolean showsTime) implements CustomPayload {
    public static final Id<UpdateClockPayload> PACKET_ID = new Id<>(NekomasFixed.id("update_clock"));

    public static final PacketCodec<RegistryByteBuf, UpdateClockPayload> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            UpdateClockPayload::x,
            PacketCodecs.VAR_INT,
            UpdateClockPayload::y,
            PacketCodecs.VAR_INT,
            UpdateClockPayload::z,
            PacketCodecs.VAR_INT,
            UpdateClockPayload::timer,
            PacketCodecs.BOOLEAN,
            UpdateClockPayload::hasBell,
            PacketCodecs.BOOLEAN,
            UpdateClockPayload::showsTime,
            UpdateClockPayload::new
    );


    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

    public static void register() {
        PayloadTypeRegistry.playS2C().register(PACKET_ID, PACKET_CODEC);
    }
}
