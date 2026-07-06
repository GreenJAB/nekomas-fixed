package net.greenjab.nekomasfixed.registry.other;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record AnimalComponent(List<AnimalComponent.StoredEntityData> animal) implements TooltipProvider {
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
				TypedEntityData.codec(EntityType.CODEC).fieldOf("entity_data").forGetter(StoredEntityData::entityData),
				Codec.LONG.fieldOf("tick_entered_hive").forGetter(StoredEntityData::tickEnteredHive)
				).apply(instance, StoredEntityData::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, StoredEntityData> PACKET_CODEC = StreamCodec.composite(
				TypedEntityData.streamCodec(EntityType.STREAM_CODEC), StoredEntityData::entityData,
				ByteBufCodecs.VAR_LONG, StoredEntityData::tickEnteredHive,
				StoredEntityData::new
		);

		public static final Codec<List<StoredEntityData>> LIST_CODEC = CODEC.listOf();

		public static StoredEntityData of(Entity entity) {
			StoredEntityData data;
			try (ProblemReporter.ScopedCollector logging = new ProblemReporter.ScopedCollector(entity.problemPath(), LOGGER)) {
				TagValueOutput nbtWriteView = TagValueOutput.createWithContext(logging, entity.registryAccess());
				entity.save(nbtWriteView);
				IRRELEVANT_ANIMAL_NBT_KEYS.forEach(nbtWriteView::discard);
				CompoundTag nbtCompound = nbtWriteView.buildResult();
				data = new StoredEntityData(TypedEntityData.of(entity.getType(), nbtCompound),
						entity.level().getGameTime());
			}
			return data;
		}

		@Nullable
		public Entity loadEntity(Level world, BlockPos pos) {
			CompoundTag nbtCompound = this.entityData.copyTagWithoutId();
			IRRELEVANT_ANIMAL_NBT_KEYS.forEach(nbtCompound::remove);
			return EntityType.loadEntityRecursive(this.entityData.type(), nbtCompound, world, EntitySpawnReason.LOAD, entity -> entity);
		}
	}

	public static final Codec<AnimalComponent> CODEC = StoredEntityData.LIST_CODEC
			.xmap(AnimalComponent::new, AnimalComponent::animal);

	public static final StreamCodec<RegistryFriendlyByteBuf, AnimalComponent> PACKET_CODEC = StoredEntityData.PACKET_CODEC
			.apply(ByteBufCodecs.list())
			.map(AnimalComponent::new, AnimalComponent::animal);

	public static final AnimalComponent DEFAULT = new AnimalComponent(List.of());

	@Override
	public void addToTooltip(Item.@NonNull TooltipContext context, @NonNull Consumer<Component> textConsumer, @NonNull TooltipFlag type, @NonNull DataComponentGetter components) {
		if (!this.animal.isEmpty()) {
			TypedEntityData<EntityType<?>> entityData = this.animal.get(0).entityData();
			CompoundTag nbt = entityData.copyTagWithoutId();
			Optional<String> name = nbt.getString("CustomName");
			Optional<Integer> age = nbt.getInt("Age");
			Optional<String> variant = nbt.getString("variant");
			if (variant.isPresent()) {
				String s = variant.get().split(":")[1];
				String s1 = s.substring(0, 1).toUpperCase();
				String s2 = s.substring(1);
				variant = Optional.of(s1+s2);
			}
			textConsumer.accept(Component.translatable("container.nautilus",
					(age.isPresent() && age.get()<0?"Baby ":""),
							variant.map(s -> s + " ").orElse(""),
							Component.translatable(entityData.type().getDescriptionId()),
							name.map(s -> ": \"" + s + "\"").orElse("")
			).withStyle(ChatFormatting.GRAY));

		}
	}

}