package net.greenjab.nekomasfixed.registry.block.entity;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.registry.entity.TermiteEntity;
import net.greenjab.nekomasfixed.registry.other.TermitesComponent;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.*;
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
import net.minecraft.util.annotation.Debug;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
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
    public void markDirty() {
        this.angerTermites(TermitehiveBlockEntity.TermiteState.EMERGENCY);
        super.markDirty();
    }

    public boolean hasNoTermites() {
        return this.termites.isEmpty();
    }

    public boolean isFullOfTermites() {
        return this.termites.size() == 2;
    }

    public void angerTermites(TermitehiveBlockEntity.TermiteState termiteState) {
        List<Entity> list = Lists.newArrayList();
        this.termites.removeIf( termite -> releaseTermite(this.world, this.pos, termite.createData(), list, termiteState));
        if (!list.isEmpty()) {
            super.markDirty();
        }
    }

    @Debug
    public int getTermiteCount() {
        return this.termites.size();
    }


    public void tryEnterMound(TermiteEntity entity) {
        if (this.termites.size() < 2) {
            entity.stopRiding();
            entity.removeAllPassengers();
            entity.detachLeash();
            this.addTermite(TermitehiveBlockEntity.TermiteData.of(entity));
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

    public void addTermite(TermitehiveBlockEntity.TermiteData termite) {
        this.termites.add(new TermitehiveBlockEntity.Termite(termite));
    }

    private static boolean releaseTermite(
            World world,
            BlockPos pos,
            TermitehiveBlockEntity.TermiteData termite,
            @Nullable List<Entity> entities,
            TermitehiveBlockEntity.TermiteState termiteState
    ) {

        Direction direction = Direction.fromHorizontalDegrees(world.random.nextInt(360));
        BlockPos blockPos = pos.offset(direction);
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

                    float f = entity.getWidth();
                    double d = bl ? 0.0 : 0.55 + f / 2.0F;
                    double e = pos.getX() + 0.5 + d * direction.getOffsetX();
                    double g = pos.getY() + 0.5 - entity.getHeight() / 2.0F;
                    double h = pos.getZ() + 0.5 + d * direction.getOffsetZ();
                    entity.refreshPositionAndAngles(e, g, h, entity.getYaw(), entity.getPitch());
                }

                world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity, world.getBlockState(pos)));
                return world.spawnEntity(entity);
            } else {
                return false;
            }
        }
    }

    private static void tickTermites(World world, BlockPos pos, BlockState state, List<TermitehiveBlockEntity.Termite> termites) {
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
            markDirty(world, pos, state);
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, TermitehiveBlockEntity blockEntity) {
        tickTermites(world, pos, state, blockEntity.termites);
        if (!blockEntity.termites.isEmpty() && world.getRandom().nextDouble() < 0.005) {
            double d = pos.getX() + 0.5;
            double e = pos.getY();
            double f = pos.getZ() + 0.5;
            world.playSound(null, d, e, f, SoundEvents.BLOCK_BEEHIVE_WORK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.termites.clear();
        (view.read("termites", TermitehiveBlockEntity.TermiteData.LIST_CODEC).orElse(List.of())).forEach(this::addTermite);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put("termites", TermitehiveBlockEntity.TermiteData.LIST_CODEC, this.createTermitesData());
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        this.termites.clear();
        List<TermitehiveBlockEntity.TermiteData> list = components.getOrDefault(OtherRegistry.TERMITES, TermitesComponent.DEFAULT).termites();
        list.forEach(this::addTermite);
    }

    @Override
    protected void addComponents(ComponentMap.Builder builder) {
        super.addComponents(builder);
        builder.add(OtherRegistry.TERMITES, new TermitesComponent(this.createTermitesData()));
    }

    @Override
    public void removeFromCopiedStackData(WriteView view) {
        super.removeFromCopiedStackData(view);
        view.remove("termites");
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
                                TypedEntityData.createCodec(EntityType.CODEC).fieldOf("entity_data").forGetter(TermitehiveBlockEntity.TermiteData::entityData),
                                Codec.INT.fieldOf("ticks_in_hive").forGetter(TermitehiveBlockEntity.TermiteData::ticksInHive),
                                Codec.INT.fieldOf("min_ticks_in_hive").forGetter(TermitehiveBlockEntity.TermiteData::minTicksInHive)
                        )
                        .apply(instance, TermitehiveBlockEntity.TermiteData::new)
        );
        public static final Codec<List<TermitehiveBlockEntity.TermiteData>> LIST_CODEC = CODEC.listOf();
        public static final PacketCodec<RegistryByteBuf, TermitehiveBlockEntity.TermiteData> PACKET_CODEC = PacketCodec.tuple(
                TypedEntityData.createPacketCodec(EntityType.PACKET_CODEC),
                TermitehiveBlockEntity.TermiteData::entityData,
                PacketCodecs.VAR_INT,
                TermitehiveBlockEntity.TermiteData::ticksInHive,
                PacketCodecs.VAR_INT,
                TermitehiveBlockEntity.TermiteData::minTicksInHive,
                TermitehiveBlockEntity.TermiteData::new
        );

        public static TermitehiveBlockEntity.TermiteData of(Entity entity) {
            TermitehiveBlockEntity.TermiteData var5;
            try (ErrorReporter.Logging logging = new ErrorReporter.Logging(entity.getErrorReporterContext(), TermitehiveBlockEntity.LOGGER)) {
                NbtWriteView nbtWriteView = NbtWriteView.create(logging, entity.getRegistryManager());
                entity.saveData(nbtWriteView);
                TermitehiveBlockEntity.IRRELEVANT_TERMITE_NBT_KEYS.forEach(nbtWriteView::remove);
                NbtCompound nbtCompound = nbtWriteView.getNbt();
                boolean bl = nbtCompound.getBoolean("HasNectar", false);
                var5 = new TermitehiveBlockEntity.TermiteData(TypedEntityData.create(entity.getType(), nbtCompound), 0, bl ? 2400 : 600);
            }

            return var5;
        }

        public static TermitehiveBlockEntity.TermiteData create(int ticksInHive) {
            return new TermitehiveBlockEntity.TermiteData(TypedEntityData.create(EntityTypeRegistry.TERMITE, new NbtCompound()), ticksInHive, 600);
        }

        @Nullable
        public Entity loadEntity(World world) {
            NbtCompound nbtCompound = this.entityData.copyNbtWithoutId();
            TermitehiveBlockEntity.IRRELEVANT_TERMITE_NBT_KEYS.forEach(nbtCompound::remove);
            Entity entity = EntityType.loadEntityWithPassengers(this.entityData.getType(), nbtCompound, world, SpawnReason.LOAD, LoadedEntityProcessor.NOOP);
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