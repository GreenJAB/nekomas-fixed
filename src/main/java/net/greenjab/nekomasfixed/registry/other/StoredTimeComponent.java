package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jspecify.annotations.NonNull;

public record StoredTimeComponent(int time) implements TooltipProvider {
	public static final Codec<StoredTimeComponent> CODEC = Codec.INT.xmap(StoredTimeComponent::new, StoredTimeComponent::time);
	public static final StreamCodec<ByteBuf, StoredTimeComponent> PACKET_CODEC = ByteBufCodecs.VAR_INT.map(StoredTimeComponent::new, StoredTimeComponent::time);

	@Override
	public void addToTooltip(Item.@NonNull TooltipContext context, Consumer<Component> textConsumer, @NonNull TooltipFlag type, @NonNull DataComponentGetter components) {
		int hour = time/1000;
		int min = ((time%1000)*60)/1000;
		String string = (hour<10?"0":"") + hour + ":" + (min<10?"0":"") + min;
		textConsumer.accept(Component.translatable("component.nekomasfixed.storedtime", string).withStyle(ChatFormatting.GRAY));
	}
}