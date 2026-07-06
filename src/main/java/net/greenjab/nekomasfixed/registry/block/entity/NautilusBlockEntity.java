package net.greenjab.nekomasfixed.registry.block.entity;

import com.google.common.collect.Lists;
import net.greenjab.nekomasfixed.registry.block.NautilusBlock;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class NautilusBlockEntity extends BlockEntity {
	private final List<AnimalComponent.StoredEntityData> animal = Lists.newArrayList();

	public NautilusBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityTypeRegistry.NAUTILUS_BLOCK_ENTITY, pos, state);
	}

	public boolean hasAnimal() {
		return !this.animal.isEmpty();
	}

	public void tryEnterNautilus(Animal animal) {
		if (this.animal.isEmpty()) {
			animal.stopRiding();
			animal.ejectPassengers();
			animal.dropLeash();
			this.animal.add(AnimalComponent.StoredEntityData.of(animal));
			if (this.level != null) {

				BlockPos blockPos = this.getBlockPos();
				this.level
					.playSound(
						null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F
					);
				this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(animal, this.getBlockState()));
			}

			animal.discard();
			super.setChanged();
		}
	}

	public List<Entity> tryReleaseAnimal(BlockState state) {
		List<Entity> list = Lists.newArrayList();
		if (this.level!=null) {
			this.animal.removeIf(data -> releaseAnimal(this.level, this.worldPosition, state, data, list));
			if (!list.isEmpty()) {
				super.setChanged();
			}
		}
		return list;
	}

	public boolean releaseAnimal(
		Level world,
		BlockPos pos,
		BlockState state,
		AnimalComponent.StoredEntityData animal,
		@Nullable List<Entity> entities
	) {
		Direction direction = state.getValue(NautilusBlock.FACING);
		BlockPos blockPos = pos.relative(direction);
		boolean bl = !world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty() ;
		if (bl) return false;
		if (animal.tickEnteredHive() == world.getGameTime()) return false;
		Entity entity = animal.loadEntity(world, pos);
		if (entity != null) {
			if (entities != null) entities.add(entity);
			double d = 0.55 + entity.getBbWidth() / 2.0F;
			double e = pos.getX() + 0.5 + d * direction.getStepX();
			double g = pos.getY();
			double h = pos.getZ() + 0.5 + d * direction.getStepZ();
			entity.setYRot(direction.toYRot());
			entity.setYBodyRot(direction.toYRot());
			entity.setYHeadRot(direction.toYRot());
			entity.yRotO =direction.toYRot();
			entity.snapTo(e, g, h, direction.toYRot(), entity.getXRot());
			world.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
			world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, world.getBlockState(pos)));
			return world.addFreshEntity(entity);
		} else return false;
	}

	@Override
	protected void loadAdditional(@NonNull ValueInput view) {
		super.loadAdditional(view);
		this.animal.clear();
        this.animal.addAll((view.read("animal", AnimalComponent.StoredEntityData.LIST_CODEC)
                .orElse(List.of())));
	}

	@Override
	protected void saveAdditional(@NonNull ValueOutput view) {
		super.saveAdditional(view);
		view.store("animal", AnimalComponent.StoredEntityData.LIST_CODEC, this.animal);
	}

	@Override
	protected void applyImplicitComponents(@NonNull DataComponentGetter components) {
		super.applyImplicitComponents(components);
		this.animal.clear();
		this.animal.addAll(components.getOrDefault(ComponentRegistry.ANIMAL, AnimalComponent.DEFAULT).animal());
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.@NonNull Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(ComponentRegistry.ANIMAL, new AnimalComponent(Lists.newArrayList(this.animal)));
	}

	@Override
	public void removeComponentsFromTag(ValueOutput view) {
		super.removeComponentsFromTag(view);
		view.discard("animal");
	}
}
