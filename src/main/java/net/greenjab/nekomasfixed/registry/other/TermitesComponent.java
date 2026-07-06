package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.block.entity.TermitehiveBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Consumer;

public record TermitesComponent(List<TermitehiveBlockEntity.TermiteData> termites) implements TooltipProvider {
	public static final Codec<TermitesComponent> CODEC = TermitehiveBlockEntity.TermiteData.LIST_CODEC.xmap(TermitesComponent::new, TermitesComponent::termites);
	public static final StreamCodec<RegistryFriendlyByteBuf, TermitesComponent> PACKET_CODEC = TermitehiveBlockEntity.TermiteData.PACKET_CODEC
			.apply(ByteBufCodecs.list())
			.map(TermitesComponent::new, TermitesComponent::termites);
	public static final TermitesComponent DEFAULT = new TermitesComponent(List.of());

	@Override
	public void addToTooltip(Item.@NonNull TooltipContext context, Consumer<Component> textConsumer, @NonNull TooltipFlag type, @NonNull DataComponentGetter components) {
		textConsumer.accept(Component.translatable("container.termitehive.termite", this.termites.size(), 3).withStyle(ChatFormatting.GRAY));
	}
}