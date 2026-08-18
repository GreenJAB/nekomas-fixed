package net.greenjab.nekomasfixed.mixin.boat;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.BigBoatEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin {

    @ModifyConstant(method = "spawn", constant = @Constant(intValue = 5, ordinal = 0))
    private int moreCommon(int constant) {
        return 1;
    }

    @Inject(method = "spawn", at = @At(value = "INVOKE", target = "Ljava/lang/Math;ceil(D)D"), cancellable = true)
    private void addMeleeGoalForSpear(CallbackInfo ci, @Local(argsOnly = true)ServerWorld world, @Local PlayerEntity playerEntity, @Local BlockPos.Mutable mutable) {
        RegistryEntry<Biome> registryEntry = world.getBiome(mutable);
        Random random = world.random;
        if (registryEntry.isIn(BiomeTags.IS_OCEAN)) {
            if (random.nextInt(3) == 0) {
                if (notNearOtherPatrols(world, playerEntity.getBlockPos())) {
                    mutable.setY(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, mutable).getY());
                    if (world.getBlockState(mutable).isOf(Blocks.AIR) && world.getBlockState(mutable.down()).isOf(Blocks.WATER)) {
                        for (int bx = mutable.getX() - 8; bx < mutable.getX() + 8; bx++) {
                            for (int by = mutable.getY() - 2; by < mutable.getY() + 4; by++) {
                                for (int bz = mutable.getZ() - 8; bz < mutable.getZ() + 8; bz++) {
                                    BlockState blockState = world.getBlockState(new BlockPos(bx, by, bz));
                                    if (!(blockState.isOf(Blocks.AIR) || blockState.isOf(Blocks.WATER))) {
                                        ci.cancel();
                                        return;
                                    }
                                }
                            }
                        }
                        mutable.add(0, 2, 0);
                        int n = (int) Math.ceil(world.getLocalDifficulty(mutable).getLocalDifficulty()) + 1;

                        int boatType = random.nextInt(EntityTypeRegistry.bigBoats.size());
                        for (int o = 0; o < n; o++) {
                            if (o == 0) {
                                if (!this.spawnBoat(world, mutable, random, boatType, true)) {
                                    break;
                                }
                            } else {
                                this.spawnBoat(world, mutable, random, boatType, false);
                            }

                            mutable.setX(mutable.getX() + (3 + random.nextInt(3)) * (int) Math.signum(random.nextInt(2) - 0.5));
                            mutable.setY(mutable.getY() + 2);
                            mutable.setZ(mutable.getZ() + (3 + random.nextInt(3)) * (int) Math.signum(random.nextInt(2) - 0.5));
                        }
                    }
                }
                ci.cancel();
            }
        } else if (random.nextInt(5) != 0) ci.cancel();
    }

    @Unique private boolean notNearOtherPatrols(ServerWorld world, BlockPos blockPos) {
        return world.getEntitiesByClass(RaiderEntity.class, Box.from(Vec3d.of(blockPos)).expand(100), EntityPredicates.VALID_LIVING_ENTITY).isEmpty();
    }

    @Unique private boolean spawnBoat(ServerWorld world, BlockPos pos, Random random, int boatType, boolean captain) {
        BlockState blockState = world.getBlockState(pos);
        if (!SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), EntityType.PILLAGER)) {
            return false;
        } else if (!PatrolEntity.canSpawn(EntityType.PILLAGER, world, SpawnReason.PATROL, pos, random)) {
            return false;
        } else {
            if (captain) {
                return spawnCaptainBoat(world, pos, random, boatType);
            } else {
                return spawnSmallBoat(world, pos, random, boatType);
            }
        }
    }

    @Unique boolean spawnCaptainBoat(ServerWorld world, BlockPos pos, Random random, int boatType){
        BigBoatEntity bigBoatEntity = world.getDifficulty().getId()>2?
                EntityTypeRegistry.hugeBoats.get(boatType).create(world, SpawnReason.PATROL):
                EntityTypeRegistry.bigBoats.get(boatType).create(world, SpawnReason.PATROL);

        if (bigBoatEntity != null) {
            bigBoatEntity.setBanner(Raid.createOminousBanner(world.getRegistryManager().getOrThrow(RegistryKeys.BANNER_PATTERN)));
            bigBoatEntity.setHasChest(true);
            bigBoatEntity.setLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, NekomasFixed.id("chests/patrol_boat")));
            bigBoatEntity.setLootTableSeed(random.nextLong());
            bigBoatEntity.refreshPositionAfterTeleport(pos.toCenterPos());
            for (int i = 0; i < world.getDifficulty().getId();i++) {
                PatrolEntity patrolEntity = EntityType.PILLAGER.create(world, SpawnReason.PATROL);
                patrolEntity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                patrolEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null);
                patrolEntity.startRiding(bigBoatEntity);
            }

            world.spawnEntityAndPassengers(bigBoatEntity);
            return true;
        } else {
            return false;
        }
    }

    @Unique boolean spawnSmallBoat(ServerWorld world, BlockPos pos, Random random, int boatType){
        AbstractBoatEntity boatEntity = EntityTypeRegistry.boats.get(boatType).create(world, SpawnReason.PATROL);
        if (boatEntity != null) {
            boatEntity.refreshPositionAfterTeleport(pos.toCenterPos());
            PatrolEntity patrolEntity = EntityType.PILLAGER.create(world, SpawnReason.PATROL);
            patrolEntity.setPosition(pos.getX(), pos.getY(), pos.getZ());
            patrolEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null);
            patrolEntity.startRiding(boatEntity);
            world.spawnEntityAndPassengers(boatEntity);
            return true;
        } else {
            return false;
        }
    }
}