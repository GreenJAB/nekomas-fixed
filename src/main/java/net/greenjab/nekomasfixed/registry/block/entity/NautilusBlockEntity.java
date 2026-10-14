package net.greenjab.nekomasfixed.registry.block.entity;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.block.NautilusBlock;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.entity.Leashable;
import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class NautilusBlockEntity extends BlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<Entity, Long> RELEASE_COOLDOWN = new WeakHashMap<>();
    private final List<AnimalComponent.StoredEntityData> animal = Lists.newArrayList();

    public NautilusBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.NAUTILUS_BLOCK_ENTITY, pos, state);
    }

    public boolean hasAnimal() {
        return !this.animal.isEmpty();
    }

    public void tryEnterNautilus(Animal animal) {
        if (this.animal.isEmpty()) {
            // Cooldown so a held-down use key doesn't instantly recapture a just-released animal.
            if (this.level != null) {
                Long until = RELEASE_COOLDOWN.get(animal);
                if (until != null && until > this.level.getGameTime()) {
                    return;
                }
            }
            animal.stopRiding();
            animal.ejectPassengers();
            if (animal instanceof Leashable leashable) {
                leashable.dropLeash(true, true);
            }
            this.animal.add(AnimalComponent.StoredEntityData.of(animal));
            if (this.level != null) {
                BlockPos blockPos = this.getBlockPos();
                this.level.playSound(
                        null, blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                        SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F
                );
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(animal, this.getBlockState()));
            }
            animal.discard();
            super.setChanged();
        }
    }

    public List<Entity> tryReleaseAnimal(BlockState state) {
        List<Entity> list = Lists.newArrayList();
        if (this.level != null) {
            this.animal.removeIf(data -> releaseAnimal(this.level, this.worldPosition, state, data, list));
            if (!list.isEmpty()) {
                super.setChanged();
            }
        }
        return list;
    }

    public boolean releaseAnimal(
            Level level,
            BlockPos pos,
            BlockState state,
            AnimalComponent.StoredEntityData animal,
            @Nullable List<Entity> entities
    ) {
        Direction direction = state.getValue(NautilusBlock.FACING);
        BlockPos blockPos = pos.relative(direction);
        boolean blocked = !level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty();
        if (blocked) return false;
        if (animal.tickEnteredHive() == level.getGameTime()) return false;
        Entity entity = animal.loadEntity(level);
        if (entity != null) {
            if (entities != null) entities.add(entity);
            double d = 0.55 + entity.getBbWidth() / 2.0F;
            double e = pos.getX() + 0.5 + d * direction.getStepX();
            double g = pos.getY();
            double h = pos.getZ() + 0.5 + d * direction.getStepZ();
            entity.setYRot(direction.toYRot());
            entity.setYBodyRot(direction.toYRot());
            entity.setYHeadRot(direction.toYRot());
            entity.yRotO = direction.toYRot();
            entity.moveTo(e, g, h, direction.toYRot(), entity.getXRot());
            RELEASE_COOLDOWN.put(entity, level.getGameTime() + 5);
            level.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, level.getBlockState(pos)));
            return level.addFreshEntity(entity);
        } else return false;
    }

    public static Codec<List<AnimalComponent.StoredEntityData>> listCodec() {
        return AnimalComponent.StoredEntityData.LIST_CODEC;
    }

    @Override
    protected void loadAdditional(@NonNull CompoundTag tag, HolderLookup.@NonNull Provider registries) {
        super.loadAdditional(tag, registries);
        this.animal.clear();
        if (tag.contains("animal")) {
            listCodec().parse(NbtOps.INSTANCE, tag.get("animal"))
                    .resultOrPartial(error -> LOGGER.error("Failed to parse animal: '{}'", error))
                    .ifPresent(this.animal::addAll);
        }
    }

    @Override
    protected void saveAdditional(@NonNull CompoundTag tag, HolderLookup.@NonNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("animal", listCodec().encodeStart(NbtOps.INSTANCE, this.animal).getOrThrow());
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.@NonNull DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.animal.clear();
        this.animal.addAll(componentInput.getOrDefault(ComponentRegistry.ANIMAL, AnimalComponent.DEFAULT).animal());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NonNull Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ComponentRegistry.ANIMAL, new AnimalComponent(Lists.newArrayList(this.animal)));
    }

    @Override
    public void removeComponentsFromTag(@NonNull CompoundTag tag) {
        super.removeComponentsFromTag(tag);
        tag.remove("animal");
    }
}
