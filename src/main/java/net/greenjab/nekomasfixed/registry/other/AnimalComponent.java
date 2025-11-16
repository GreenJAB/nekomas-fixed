package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.block.entity.NautilusBlockEntity;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record AnimalComponent(List<NautilusBlockEntity.AnimalData> animal) implements TooltipAppender {
	public static final Codec<AnimalComponent> CODEC = NautilusBlockEntity.AnimalData.LIST_CODEC.xmap(AnimalComponent::new, AnimalComponent::animal);
	public static final PacketCodec<RegistryByteBuf, AnimalComponent> PACKET_CODEC = NautilusBlockEntity.AnimalData.PACKET_CODEC
		.collect(PacketCodecs.toList())
		.xmap(AnimalComponent::new, AnimalComponent::animal);
	public static final AnimalComponent DEFAULT = new AnimalComponent(List.of());

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
		if (!this.animal.isEmpty()) {
			TypedEntityData<EntityType<?>> entityData = this.animal.get(0).entityData();
			NbtCompound nbt = entityData.copyNbtWithoutId();
			Optional<String> name = nbt.getString("CustomName");
			Optional<Integer> age = nbt.getInt("Age");
			Optional<String> variant = nbt.getString("variant");
			if (variant.isPresent()) {
				String s = variant.get().split(":")[1];
				String s1 = s.substring(0, 1).toUpperCase();
				String s2 = s.substring(1);
				variant = Optional.of(s1+s2);
			}
			textConsumer.accept(Text.translatable("container.nautilus",
					(age.isPresent() && age.get()<0?"Baby ":""),
							variant.map(s -> s + " ").orElse(""),
							Text.translatable(entityData.getType().getTranslationKey()),
							name.map(s -> ": \"" + s + "\"").orElse("")
			).formatted(Formatting.GRAY));

		}
	}

}
