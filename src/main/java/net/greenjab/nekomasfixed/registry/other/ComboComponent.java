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

public record ComboComponent(int multiplier) implements TooltipAppender {
	public static final Codec<ComboComponent> CODEC = Codec.INT.xmap(ComboComponent::new, ComboComponent::multiplier);
	public static final PacketCodec<ByteBuf, ComboComponent> PACKET_CODEC = PacketCodecs.VAR_INT.xmap(ComboComponent::new, ComboComponent::multiplier);

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
		StringBuilder string = new StringBuilder();
		for (int i = 1; i <= 3;i++) string.append((i * multiplier)).append(i<3?"%, ":"%");
		string.append(" ... ");
		string.append((10 * multiplier)).append("%");
		textConsumer.accept(Text.translatable("component.nekomasfixed.combo", string.toString()).formatted(Formatting.GRAY));
	}
}