package net.greenjab.nekomasfixed.world.spawn;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.BigBoatEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.SpecialSpawner;

public class PirateSpawner implements SpecialSpawner {
    private int cooldown;

    @Override
    public void spawn(ServerWorld world, boolean spawnMonsters) {
        if (spawnMonsters) {
            if (world.getGameRules().getBoolean(GameRules.DO_PATROL_SPAWNING)) {
                Random random = world.random;
                this.cooldown--;
                if (this.cooldown <= 0) {
                    this.cooldown = this.cooldown + 200 + random.nextInt(20); //12000 1200
                    long l = world.getTimeOfDay() / 24000L;
                    //if (l >= 5L && world.isDay()) {
                    if (random.nextInt(1) == 0) {//5
                        int i = world.getPlayers().size();
                        if (i >= 1) {
                            PlayerEntity playerEntity = (PlayerEntity)world.getPlayers().get(random.nextInt(i));
                            if (!playerEntity.isSpectator()) {
                                if (!world.isNearOccupiedPointOfInterest(playerEntity.getBlockPos(), 2)) {

                                    int j = (64 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                                    int k = (64 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                                    Mutable mutable = playerEntity.getBlockPos().mutableCopy().move(j, 0, k);
                                    int m = 10;
                                    if (world.isRegionLoaded(mutable.getX() - 10, mutable.getZ() - 10, mutable.getX() + 10, mutable.getZ() + 10)) {
                                        RegistryEntry<Biome> registryEntry = world.getBiome(mutable);
                                        if (registryEntry.isIn(BiomeTags.IS_OCEAN)) {
                                            mutable.setY(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, mutable).getY());
                                            if (world.getBlockState(mutable).isOf(Blocks.AIR) && world.getBlockState(mutable.down()).isOf(Blocks.WATER)) {
                                                for (int bx = mutable.getX() - 8; bx < mutable.getX() + 8; bx++) {
                                                    for (int by = mutable.getY() - 2; by < mutable.getY() + 4; by++) {
                                                        for (int bz = mutable.getZ() - 8; bz < mutable.getZ() + 8; bz++) {
                                                            BlockState blockState = world.getBlockState(new BlockPos(bx, by, bz));
                                                            if (!(blockState.isOf(Blocks.AIR) || blockState.isOf(Blocks.WATER))) {
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

                                                    mutable.setX(mutable.getX() + (3 + random.nextInt(3)) * (int)Math.signum(random.nextInt(2) - 0.5));
                                                    mutable.setY(mutable.getY() + 2);
                                                    mutable.setZ(mutable.getZ() + (3 + random.nextInt(3)) * (int)Math.signum(random.nextInt(2) - 0.5));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //}
                }
            }
        }
    }

    private boolean spawnBoat(ServerWorld world, BlockPos pos, Random random, int boatType, boolean captain) {
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

    boolean spawnCaptainBoat(ServerWorld world, BlockPos pos, Random random, int boatType){
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

    boolean spawnSmallBoat(ServerWorld world, BlockPos pos, Random random, int boatType){
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
