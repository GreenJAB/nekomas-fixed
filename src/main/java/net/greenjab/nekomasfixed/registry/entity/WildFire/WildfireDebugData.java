package net.greenjab.nekomasfixed.registry.entity.WildFire;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record WildfireDebugData(Optional<Integer> attackTarget, Optional<BlockPos> jumpTarget) {
	public static final StreamCodec<ByteBuf, WildfireDebugData> PACKET_CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT.apply(ByteBufCodecs::optional),
			WildfireDebugData::attackTarget,
			BlockPos.STREAM_CODEC.apply(ByteBufCodecs::optional),
			WildfireDebugData::jumpTarget,
			WildfireDebugData::new
	);
}