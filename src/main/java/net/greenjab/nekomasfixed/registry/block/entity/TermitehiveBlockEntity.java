package net.greenjab.nekomasfixed.registry.block.entity;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.entity.TermiteEntity;
import net.greenjab.nekomasfixed.registry.other.TermitesComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityProcessor;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TermitehiveBlockEntity extends BlockEntity {
    static final Logger LOGGER = LogUtils.getLogger();
    static final List<String> IRRELEVANT_TERMITE_NBT_KEYS = Arrays.asList(
            "Air",
            "drop_chances",
            "equipment",
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
            "TicksSincePollination",
            "CropsGrownSincePollination",
            "hive_pos",
            "Passengers",
            "leash",
            "UUID"
    );
    private final List<TermitehiveBlockEntity.Termite> termites = Lists.newArrayList();

    public TermitehiveBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.TERMITE_HIVE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void setChanged() {
        this.angerTermites(TermitehiveBlockEntity.TermiteState.EMERGENCY);
        super.setChanged();
    }

    public boolean hasNoTermites() {
        return this.termites.isEmpty();
    }

    public boolean isFullOfTermites() {
        return this.termites.size() == 2;
    }

    public void angerTermites(TermitehiveBlockEntity.TermiteState termiteState) {
        List<Entity> list = Lists.newArrayList();
        this.termites.removeIf( termite -> releaseTermite(this.level, this.worldPosition, termite.createData(), list, termiteState));
        if (!list.isEmpty()) {
            super.setChanged();
        }
    }

    @VisibleForDebug
    public int getTermiteCount() {
        return this.termites.size();
    }


    public void tryEnterMound(TermiteEntity entity) {
        if (this.termites.size() < 2) {
            entity.stopRiding();
            entity.ejectPassengers();
            entity.dropLeash();
            this.addTermite(TermitehiveBlockEntity.TermiteData.of(entity));
            if (this.level != null) {

                BlockPos blockPos = this.getBlockPos();
                this.level
                        .playSound(
                                null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F
                        );
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, this.getBlockState()));
            }

            entity.discard();
            super.setChanged();
        }
    }

    public void addTermite(TermitehiveBlockEntity.TermiteData termite) {
        this.termites.add(new TermitehiveBlockEntity.Termite(termite));
    }

    private static boolean releaseTermite(
            Level world,
            BlockPos pos,
            TermitehiveBlockEntity.TermiteData termite,
            @Nullable List<Entity> entities,
            TermitehiveBlockEntity.TermiteState termiteState
    ) {

        Direction direction = Direction.fromYRot(world.random.nextInt(360));
        BlockPos blockPos = pos.relative(direction);
        boolean bl = !world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty();
        if (bl && termiteState != TermitehiveBlockEntity.TermiteState.EMERGENCY) {
            return false;
        } else {
            Entity entity = termite.loadEntity(world);
            if (entity != null) {
                if (entity instanceof TermiteEntity termiteEntity) {

                    if (entities != null) {
                        entities.add(termiteEntity);
                    }

                    float f = entity.getBbWidth();
                    double d = bl ? 0.0 : 0.55 + f / 2.0F;
                    double e = pos.getX() + 0.5 + d * direction.getStepX();
                    double g = pos.getY() + 0.5 - entity.getBbHeight() / 2.0F;
                    double h = pos.getZ() + 0.5 + d * direction.getStepZ();
                    entity.snapTo(e, g, h, entity.getYRot(), entity.getXRot());
                }

                world.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, world.getBlockState(pos)));
                return world.addFreshEntity(entity);
            } else {
                return false;
            }
        }
    }

    private static void tickTermites(Level world, BlockPos pos, BlockState state, List<TermitehiveBlockEntity.Termite> termites) {
        boolean bl = false;
        Iterator<TermitehiveBlockEntity.Termite> iterator = termites.iterator();

        while (iterator.hasNext()) {
            TermitehiveBlockEntity.Termite termite = iterator.next();
            if (termite.canExitHive()) {
                TermitehiveBlockEntity.TermiteState termiteState = TermitehiveBlockEntity.TermiteState.TERMITE_RELEASED;
                if (releaseTermite(world, pos, termite.createData(), null, termiteState)) {
                    bl = true;
                    iterator.remove();
                }
            }
        }

        if (bl) {
            setChanged(world, pos, state);
        }
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, TermitehiveBlockEntity blockEntity) {
        tickTermites(world, pos, state, blockEntity.termites);
        if (!blockEntity.termites.isEmpty() && world.getRandom().nextDouble() < 0.005) {
            double d = pos.getX() + 0.5;
            double e = pos.getY();
            double f = pos.getZ() + 0.5;
            world.playSound(null, d, e, f, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput view) {
        super.loadAdditional(view);
        this.termites.clear();
        (view.read("termites", TermitehiveBlockEntity.TermiteData.LIST_CODEC).orElse(List.of())).forEach(this::addTermite);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput view) {
        super.saveAdditional(view);
        view.store("termites", TermitehiveBlockEntity.TermiteData.LIST_CODEC, this.createTermitesData());
    }

    @Override
    protected void applyImplicitComponents(@NonNull DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.termites.clear();
        List<TermitehiveBlockEntity.TermiteData> list = components.getOrDefault(ComponentRegistry.TERMITES, TermitesComponent.DEFAULT).termites();
        list.forEach(this::addTermite);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NonNull Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ComponentRegistry.TERMITES, new TermitesComponent(this.createTermitesData()));
    }

    @Override
    public void removeComponentsFromTag(@NonNull ValueOutput view) {
        super.removeComponentsFromTag(view);
        view.discard("termites");
    }

    private List<TermitehiveBlockEntity.TermiteData> createTermitesData() {
        return this.termites.stream().map(TermitehiveBlockEntity.Termite::createData).toList();
    }

   /* @Override
    public void registerTracking(ServerWorld world, DebugTrackable.Tracker tracker) {
        tracker.track(DebugSubscriptionTypes.TERMITE_HIVES, () -> TermiteHiveDebugData.fromTermitehive(this));
    }*/

    static class Termite {
        private final TermitehiveBlockEntity.TermiteData data;
        private int ticksInHive;

        Termite(TermitehiveBlockEntity.TermiteData data) {
            this.data = data;
            this.ticksInHive = data.ticksInHive();
        }

        public boolean canExitHive() {
            return this.ticksInHive++ > this.data.minTicksInHive;
        }

        public TermitehiveBlockEntity.TermiteData createData() {
            return new TermitehiveBlockEntity.TermiteData(this.data.entityData, this.ticksInHive, this.data.minTicksInHive);
        }

    }

    public record TermiteData(TypedEntityData<EntityType<?>> entityData, int ticksInHive, int minTicksInHive) {
        public static final Codec<TermitehiveBlockEntity.TermiteData> CODEC = RecordCodecBuilder.create(
                 instance -> instance.group(
                                TypedEntityData.codec(EntityType.CODEC).fieldOf("entity_data").forGetter(TermitehiveBlockEntity.TermiteData::entityData),
                                Codec.INT.fieldOf("ticks_in_hive").forGetter(TermitehiveBlockEntity.TermiteData::ticksInHive),
                                Codec.INT.fieldOf("min_ticks_in_hive").forGetter(TermitehiveBlockEntity.TermiteData::minTicksInHive)
                        )
                        .apply(instance, TermitehiveBlockEntity.TermiteData::new)
        );
        public static final Codec<List<TermitehiveBlockEntity.TermiteData>> LIST_CODEC = CODEC.listOf();
        public static final StreamCodec<RegistryFriendlyByteBuf, TermitehiveBlockEntity.TermiteData> PACKET_CODEC = StreamCodec.composite(
                TypedEntityData.streamCodec(EntityType.STREAM_CODEC),
                TermitehiveBlockEntity.TermiteData::entityData,
                ByteBufCodecs.VAR_INT,
                TermitehiveBlockEntity.TermiteData::ticksInHive,
                ByteBufCodecs.VAR_INT,
                TermitehiveBlockEntity.TermiteData::minTicksInHive,
                TermitehiveBlockEntity.TermiteData::new
        );

        public static TermitehiveBlockEntity.TermiteData of(Entity entity) {
            TermitehiveBlockEntity.TermiteData var5;
            try (ProblemReporter.ScopedCollector logging = new ProblemReporter.ScopedCollector(entity.problemPath(), TermitehiveBlockEntity.LOGGER)) {
                TagValueOutput nbtWriteView = TagValueOutput.createWithContext(logging, entity.registryAccess());
                entity.save(nbtWriteView);
                TermitehiveBlockEntity.IRRELEVANT_TERMITE_NBT_KEYS.forEach(nbtWriteView::discard);
                CompoundTag nbtCompound = nbtWriteView.buildResult();
                boolean bl = nbtCompound.getBooleanOr("HasNectar", false);
                var5 = new TermitehiveBlockEntity.TermiteData(TypedEntityData.of(entity.getType(), nbtCompound), 0, bl ? 2400 : 600);
            }

            return var5;
        }

        public static TermitehiveBlockEntity.TermiteData create(int ticksInHive) {
            return new TermitehiveBlockEntity.TermiteData(TypedEntityData.of(EntityTypeRegistry.TERMITE, new CompoundTag()), ticksInHive, 600);
        }

        @Nullable
        public Entity loadEntity(Level world) {
            CompoundTag nbtCompound = this.entityData.copyTagWithoutId();
            TermitehiveBlockEntity.IRRELEVANT_TERMITE_NBT_KEYS.forEach(nbtCompound::remove);
            Entity entity = EntityType.loadEntityRecursive(this.entityData.type(), nbtCompound, world, EntitySpawnReason.LOAD, EntityProcessor.NOP);
            if (entity != null && entity.getType()==EntityTypeRegistry.TERMITE) {
                return entity;
            } else {
                return null;
            }
        }
    }

    public enum TermiteState {
        TERMITE_RELEASED,
        EMERGENCY
    }
}