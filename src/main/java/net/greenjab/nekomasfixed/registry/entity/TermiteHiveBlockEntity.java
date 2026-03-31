package net.greenjab.nekomasfixed.registry.entity;

import com.google.common.collect.Lists;
import net.greenjab.nekomasfixed.registry.entity.Termite.TermiteEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class TermiteHiveBlockEntity extends BlockEntity {

    private static final List<NbtCompound> termites = Lists.newArrayList();
    public static final int MAX_TERMITE_COUNT = 2;

    public TermiteHiveBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.TERMITE_HIVE_BLOCK_ENTITY, pos, state);
    }

    public boolean hasNoTermites() {
        return termites.isEmpty();
    }

    public boolean isFullOfTermites() {
        return termites.size() >= MAX_TERMITE_COUNT;
    }

    public int getTermiteCount() {
        return termites.size();
    }

    public void addTermite(TermiteEntity termite) {
        if (termites.size() >= MAX_TERMITE_COUNT) return;
        NbtCompound nbt = new NbtCompound();
        termite.writeCustomDataToNbt(nbt);
        termites.add(nbt);
        termite.discard();
    }

    public void tryEnterMound(TermiteEntity entity) {
        if (termites.size() < 3) {
            entity.stopRiding();
            entity.removeAllPassengers();
            entity.detachLeash();
            this.addTermite(entity);
            if (this.world != null) {
               BlockPos blockPos = this.getPos();
                this.world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0F, 1.0F);
                this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(entity, this.getCachedState()));
            }

            entity.discard();
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, TermiteHiveBlockEntity blockEntity) {
        if (!termites.isEmpty() && world.getRandom().nextDouble() < 0.005) {
            double d = pos.getX() + 0.5;
            double e = pos.getY();
            double f = pos.getZ() + 0.5;
            world.playSound(null, d, e, f, SoundEvents.BLOCK_BEEHIVE_WORK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    private static void tickTermites(World world, BlockPos pos, BlockState state, List<NbtCompound> bees) {
        boolean bl = false;
        Iterator<NbtCompound> iterator = bees.iterator();

        while (iterator.hasNext()) {
            NbtCompound bee = iterator.next();
            if (bee.contains("TicksInHive") && bee.getInt("TicksInHive").get() >= 2400) {
                if (releaseTermite(world, pos, state, bee)) {
                    bl = true;
                    iterator.remove();
                }
            }
        }

        if (bl) {
            markDirty(world, pos, state);
        }
    }

    private static boolean releaseTermite(World world, BlockPos pos, BlockState state, NbtCompound nbt) {
        Direction direction = state.get(BeehiveBlock.FACING);
        BlockPos front = pos.offset(direction);

        if (!world.getBlockState(front).getCollisionShape(world, front).isEmpty()) {
            return false;
        }

        TermiteEntity termite = new TermiteEntity(EntityTypeRegistry.TERMITE, world);
        termite.readCustomDataFromNbt(nbt);
        termite.setMoundPosition(Optional.of(pos));
        termite.setNoGravity(false);

        double x = pos.getX() + 0.5 + direction.getOffsetX() * 0.6;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5 + direction.getOffsetZ() * 0.6;

        termite.refreshPositionAndAngles(x, y, z, termite.getYaw(), termite.getPitch());
        world.spawnEntity(termite);

        return true;
    }

    private Entity loadTermite(World world, BlockPos pos, NbtCompound nbt) {
        TermiteEntity termite = new TermiteEntity(EntityTypeRegistry.TERMITE, world);
        termite.readCustomDataFromNbt(nbt);
        termite.setMoundPosition(Optional.of(pos));
        termite.setNoGravity(false);
        return termite;
    }

    public boolean releaseAllTermites(World world, BlockState state) {
        if (termites.isEmpty()) return false;

        Direction direction = state.get(BeehiveBlock.FACING);
        BlockPos front = pos.offset(direction);

        if (!world.getBlockState(front).getCollisionShape(world, front).isEmpty()) {
            return false;
        }

        for (NbtCompound nbt : termites) {
            Entity entity = loadTermite(world, pos, nbt);

            double x = pos.getX() + 0.5 + direction.getOffsetX() * 0.6;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5 + direction.getOffsetZ() * 0.6;

            entity.refreshPositionAndAngles(x, y, z, entity.getYaw(), entity.getPitch());
            world.spawnEntity(entity);
        }

        termites.clear();

        world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);

        return true;
    }

    public void writeNbt(NbtCompound nbt) {

        nbt.putInt("count", termites.size());

        for (int i = 0; i < termites.size(); i++) {
            nbt.put("t" + i, termites.get(i));
        }
    }

    public void readNbt(NbtCompound nbt) {

        termites.clear();

        int count = nbt.contains("count") ? nbt.getInt("count").get() : 0;

        for (int i = 0; i < count; i++) {
            String key = "t" + i;
            if (nbt.contains(key)) {
                termites.add(nbt.getCompound(key).get());
            }
        }
    }
}