package net.greenjab.nekomasfixed.registry.entity.WildFire;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;
import java.util.Optional;

public record WildfireDebugData(Optional<Integer> attackTarget, Optional<BlockPos> jumpTarget) {
	public static final PacketCodec<ByteBuf, WildfireDebugData> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_INT.collect(PacketCodecs::optional),
			WildfireDebugData::attackTarget,
			BlockPos.PACKET_CODEC.collect(PacketCodecs::optional),
			WildfireDebugData::jumpTarget,
			WildfireDebugData::new
	);
}