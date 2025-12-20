package net.greenjab.nekomasfixed.registry.block.entity;

import com.google.common.collect.Lists;
import net.greenjab.nekomasfixed.registry.block.ZombieNautilusBlock;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZombieNautilusBlockEntity extends BlockEntity {
	private final List<AnimalComponent.StoredEntityData> animal = Lists.newArrayList();

	public ZombieNautilusBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityTypeRegistry.ZOMBIE_NAUTILUS_BLOCK_ENTITY, pos, state);
	}

	public boolean hasAnimal() {
		return !this.animal.isEmpty();
	}

	public void tryEnterNautilus(AnimalEntity entity) {
		if (this.animal.isEmpty()) {
			entity.stopRiding();
			entity.removeAllPassengers();
			entity.detachLeash();
			this.animal.add(AnimalComponent.StoredEntityData.of(entity));
			if (this.world != null) {

				BlockPos blockPos = this.getPos();
				this.world
						.playSound(
								null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0F, 1.0F
						);
				this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(entity, this.getCachedState()));
			}

			entity.discard();
			super.markDirty();
		}
	}

	public List<Entity> tryReleaseAnimal(BlockState state) {
		List<Entity> list = Lists.newArrayList();
		this.animal.removeIf( data -> releaseAnimal(this.world, this.pos, state, data, list));
		if (!list.isEmpty()) {
			super.markDirty();
		}
		return list;
	}

	public boolean releaseAnimal(
			World world,
			BlockPos pos,
			BlockState state,
			AnimalComponent.StoredEntityData animal,
			@Nullable List<Entity> entities
	) {
		Direction direction = state.get(ZombieNautilusBlock.FACING);
		BlockPos blockPos = pos.offset(direction);
		boolean bl = !world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty() ;
		if (bl) return false;
		if (animal.tickEnteredHive() == world.getTime()) return false;
		Entity entity = animal.loadEntity(world, pos);
		if (entity != null) {
			if (entities != null) entities.add(entity);
			double d = 0.55 + entity.getWidth() / 2.0F;
			double e = pos.getX() + 0.5 + d * direction.getOffsetX();
			double g = pos.getY();
			double h = pos.getZ() + 0.5 + d * direction.getOffsetZ();
			entity.refreshPositionAndAngles(e, g, h, entity.getYaw(), entity.getPitch());
			world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity, world.getBlockState(pos)));
			return world.spawnEntity(entity);
		} else return false;
	}

	@Override
	protected void readData(ReadView view) {
		super.readData(view);
		this.animal.clear();
		((List<AnimalComponent.StoredEntityData>) view.read("animal", AnimalComponent.StoredEntityData.LIST_CODEC)
				.orElse(List.of()))
				.forEach(this.animal::add);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		view.put("animal", AnimalComponent.StoredEntityData.LIST_CODEC, this.animal);
	}

	@Override
	protected void readComponents(ComponentsAccess components) {
		super.readComponents(components);
		this.animal.clear();
		this.animal.addAll(components.getOrDefault(OtherRegistry.ANIMAL, AnimalComponent.DEFAULT).animal());
	}

	@Override
	protected void addComponents(ComponentMap.Builder builder) {
		super.addComponents(builder);
		builder.add(OtherRegistry.ANIMAL, new AnimalComponent(Lists.newArrayList(this.animal)));
	}

	@Override
	public void removeFromCopiedStackData(WriteView view) {
		super.removeFromCopiedStackData(view);
		view.remove("animal");
	}
}