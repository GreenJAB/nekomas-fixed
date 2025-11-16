package net.greenjab.nekomasfixed.registry.block.entity;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.block.NautilusBlock;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.TypedEntityData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.NbtWriteView;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ErrorReporter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

public class NautilusBlockEntity extends BlockEntity {
	static final Logger LOGGER = LogUtils.getLogger();
	static final List<String> IRRELEVANT_ANIMAL_NBT_KEYS = Arrays.asList(
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

	private final List<NautilusBlockEntity.Animal> animal = Lists.newArrayList();

	public NautilusBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityTypeRegistry.NAUTILUS_BLOCK_ENTITY, pos, state);
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	public boolean hasAnimal() {
		return !this.animal.isEmpty();
	}

	public void tryEnterNautilus(AnimalEntity animal) {
		if (this.animal.isEmpty()) {
			animal.stopRiding();
			animal.removeAllPassengers();
			animal.detachLeash();
			this.addAnimal(NautilusBlockEntity.AnimalData.of(animal));
			if (this.world != null) {

				BlockPos blockPos = this.getPos();
				this.world
					.playSound(
						null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0F, 1.0F
					);
				this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(animal, this.getCachedState()));
			}

			animal.discard();
			super.markDirty();
		}
	}

	public void addAnimal(NautilusBlockEntity.AnimalData animal) {
		this.animal.add(new NautilusBlockEntity.Animal(animal));
	}

	public List<Entity> tryReleaseAnimal(BlockState state) {
		List<Entity> list = Lists.newArrayList();
		this.animal.removeIf( animal -> releaseAnimal(this.world, this.pos, state, animal.createData(), list));
		if (!list.isEmpty()) {
			super.markDirty();
		}
		return list;
	}

	public boolean releaseAnimal(
		World world,
		BlockPos pos,
		BlockState state,
		NautilusBlockEntity.AnimalData animal,
		@Nullable List<Entity> entities
	) {
			Direction direction = state.get(NautilusBlock.FACING);
			BlockPos blockPos = pos.offset(direction);
			boolean bl = !world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty() ;
			if (bl) return false;
			if (animal.tickEnteredHive ==world.getTime()) return false;
			Entity entity = animal.loadEntity(world, pos);
			if (entity != null) {
				if (entity instanceof AnimalEntity animalEntity) {
					if (entities != null) {
						entities.add(animalEntity);
					}

					float f = entity.getWidth();
					double d = 0.55 + f / 2.0F;
					double e = pos.getX() + 0.5 + d * direction.getOffsetX();
					double g = pos.getY() + 0.5 - entity.getHeight() / 2.0F;
					double h = pos.getZ() + 0.5 + d * direction.getOffsetZ();
					entity.refreshPositionAndAngles(e, g, h, entity.getYaw(), entity.getPitch());
				}

				world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity, world.getBlockState(pos)));
				return world.spawnEntity(entity);
			} else return false;
	}


	@Override
	protected void readData(ReadView view) {
		super.readData(view);
		this.animal.clear();
		((List)view.read("animal", NautilusBlockEntity.AnimalData.LIST_CODEC).orElse(List.of())).forEach(this::addAnimal);
	}

	private void addAnimal(Object o) {
		this.addAnimal((NautilusBlockEntity.AnimalData)o);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		view.put("animal", NautilusBlockEntity.AnimalData.LIST_CODEC, this.createAnimalData());
	}

	@Override
	protected void readComponents(ComponentsAccess components) {
		super.readComponents(components);
		this.animal.clear();
		List<NautilusBlockEntity.AnimalData> list = components.getOrDefault(OtherRegistry.ANIMAL, AnimalComponent.DEFAULT).animal();
		list.forEach(this::addAnimal);
	}

	@Override
	protected void addComponents(ComponentMap.Builder builder) {
		super.addComponents(builder);
		builder.add(OtherRegistry.ANIMAL, new AnimalComponent(this.createAnimalData()));
	}

	@Override
	public void removeFromCopiedStackData(WriteView view) {
		super.removeFromCopiedStackData(view);
		view.remove("animals");
	}

	private List<AnimalData> createAnimalData() {
		return this.animal.stream().map(NautilusBlockEntity.Animal::createData).toList();
	}

	static class Animal {
		private final NautilusBlockEntity.AnimalData data;

		Animal(NautilusBlockEntity.AnimalData data) {
			this.data = data;
		}


		public NautilusBlockEntity.AnimalData createData() {
			return new NautilusBlockEntity.AnimalData(this.data.entityData, this.data.tickEnteredHive);
		}

	}

	public record AnimalData(TypedEntityData<EntityType<?>> entityData, long tickEnteredHive) {
		public static final Codec<NautilusBlockEntity.AnimalData> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					TypedEntityData.createCodec(EntityType.CODEC).fieldOf("entity_data").forGetter(NautilusBlockEntity.AnimalData::entityData),
								Codec.LONG.fieldOf("tick_entered_hive").forGetter(NautilusBlockEntity.AnimalData::tickEnteredHive)
								)
				.apply(instance, NautilusBlockEntity.AnimalData::new)
		);
		public static final Codec<List<NautilusBlockEntity.AnimalData>> LIST_CODEC = CODEC.listOf();
		public static final PacketCodec<RegistryByteBuf, NautilusBlockEntity.AnimalData> PACKET_CODEC = PacketCodec.tuple(
			TypedEntityData.createPacketCodec(EntityType.PACKET_CODEC),
			NautilusBlockEntity.AnimalData::entityData,
				PacketCodecs.VAR_LONG,
				NautilusBlockEntity.AnimalData::tickEnteredHive,
			NautilusBlockEntity.AnimalData::new
		);

		public static NautilusBlockEntity.AnimalData of(Entity entity) {
			NautilusBlockEntity.AnimalData var5;
			try (ErrorReporter.Logging logging = new ErrorReporter.Logging(entity.getErrorReporterContext(), NautilusBlockEntity.LOGGER)) {
				NbtWriteView nbtWriteView = NbtWriteView.create(logging, entity.getRegistryManager());
				entity.saveData(nbtWriteView);
				NautilusBlockEntity.IRRELEVANT_ANIMAL_NBT_KEYS.forEach(nbtWriteView::remove);
				NbtCompound nbtCompound = nbtWriteView.getNbt();
				var5 = new NautilusBlockEntity.AnimalData(TypedEntityData.create(entity.getType(), nbtCompound), entity.getEntityWorld().getTime());
			}

			return var5;
		}

		@Nullable
		public Entity loadEntity(World world, BlockPos pos) {
			NbtCompound nbtCompound = this.entityData.copyNbtWithoutId();
			NautilusBlockEntity.IRRELEVANT_ANIMAL_NBT_KEYS.forEach(nbtCompound::remove);
			return EntityType.loadEntityWithPassengers(this.entityData.getType(), nbtCompound, world, SpawnReason.LOAD, entityx -> entityx);
		}

	}

}
