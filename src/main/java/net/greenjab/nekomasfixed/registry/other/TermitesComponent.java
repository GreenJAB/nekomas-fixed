package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.block.entity.TermitehiveBlockEntity;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.List;
import java.util.function.Consumer;

public record TermitesComponent(List<TermitehiveBlockEntity.TermiteData> termites) implements TooltipAppender {
	public static final Codec<TermitesComponent> CODEC = TermitehiveBlockEntity.TermiteData.LIST_CODEC.xmap(TermitesComponent::new, TermitesComponent::termites);
	public static final PacketCodec<RegistryByteBuf, TermitesComponent> PACKET_CODEC = TermitehiveBlockEntity.TermiteData.PACKET_CODEC
			.collect(PacketCodecs.toList())
			.xmap(TermitesComponent::new, TermitesComponent::termites);
	public static final TermitesComponent DEFAULT = new TermitesComponent(List.of());

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
		textConsumer.accept(Text.translatable("container.termitehive.termite", this.termites.size(), 3).formatted(Formatting.GRAY));
	}
}