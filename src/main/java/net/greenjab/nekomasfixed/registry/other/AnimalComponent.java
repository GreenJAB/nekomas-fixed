package net.greenjab.nekomasfixed.registry.other;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.storage.NbtWriteView;
import net.minecraft.text.Text;
import net.minecraft.util.ErrorReporter;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record AnimalComponent(List<AnimalComponent.StoredEntityData> animal) implements TooltipAppender {
	private static final Logger LOGGER = LogUtils.getLogger();

	public static final List<String> IRRELEVANT_ANIMAL_NBT_KEYS = Arrays.asList(
			"Air",
			"drop_chances",
			"Brain",
			"CanPickUpLoot",
			"DeathTime",
			"fall_distance",
			"FallFlying",
			"Fire",
			"HurtByTimestamp",
			"HurtTime",
			"LeftHanded",
			"Motion",
			"NoGravity",
			"OnGround",
			"PortalCooldown",
			"Pos",
			"Rotation",
			"sleeping_pos",
			"CannotEnterHiveTicks",
			"hive_pos",
			"Passengers",
			"leash",
			"UUID"
	);

	public record StoredEntityData(TypedEntityData<EntityType<?>> entityData, long tickEnteredHive) {

		public static final Codec<StoredEntityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				TypedEntityData.createCodec(EntityType.CODEC).fieldOf("entity_data").forGetter(StoredEntityData::entityData),
				Codec.LONG.fieldOf("tick_entered_hive").forGetter(StoredEntityData::tickEnteredHive)
				).apply(instance, StoredEntityData::new));

		public static final PacketCodec<RegistryByteBuf, StoredEntityData> PACKET_CODEC = PacketCodec.tuple(
				TypedEntityData.createPacketCodec(EntityType.PACKET_CODEC), StoredEntityData::entityData,
				PacketCodecs.VAR_LONG, StoredEntityData::tickEnteredHive,
				StoredEntityData::new
		);

		public static final Codec<List<StoredEntityData>> LIST_CODEC = CODEC.listOf();

		public static StoredEntityData of(Entity entity) {
			StoredEntityData data;
			try (ErrorReporter.Logging logging = new ErrorReporter.Logging(entity.getErrorReporterContext(), LOGGER)) {
				NbtWriteView nbtWriteView = NbtWriteView.create(logging, entity.getRegistryManager());
				entity.saveData(nbtWriteView);
				IRRELEVANT_ANIMAL_NBT_KEYS.forEach(nbtWriteView::remove);
				NbtCompound nbtCompound = nbtWriteView.getNbt();
				data = new StoredEntityData(TypedEntityData.create(entity.getType(), nbtCompound),
						entity.getEntityWorld().getTime());
			}
			return data;
		}

		@Nullable
		public Entity loadEntity(World world, BlockPos pos) {
			NbtCompound nbtCompound = this.entityData.copyNbtWithoutId();
			IRRELEVANT_ANIMAL_NBT_KEYS.forEach(nbtCompound::remove);
			return EntityType.loadEntityWithPassengers(this.entityData.getType(), nbtCompound, world, SpawnReason.LOAD, entity -> entity);
		}
	}

	public static final Codec<AnimalComponent> CODEC = StoredEntityData.LIST_CODEC
			.xmap(AnimalComponent::new, AnimalComponent::animal);

	public static final PacketCodec<RegistryByteBuf, AnimalComponent> PACKET_CODEC = StoredEntityData.PACKET_CODEC
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