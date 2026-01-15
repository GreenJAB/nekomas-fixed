package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record StoredTimeComponent(int time) implements TooltipAppender {
	public static final Codec<StoredTimeComponent> CODEC = Codec.INT.xmap(StoredTimeComponent::new, StoredTimeComponent::time);
	public static final PacketCodec<ByteBuf, StoredTimeComponent> PACKET_CODEC = PacketCodecs.VAR_INT.xmap(StoredTimeComponent::new, StoredTimeComponent::time);

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
		int hour = time/1000;
		int min = ((time%1000)*60)/1000;
		String string = (hour<10?"0":"") + hour + ":" + (min<10?"0":"") + min;
		textConsumer.accept(Text.translatable("component.nekomasfixed.storedtime", string).formatted(Formatting.GRAY));
	}
}