package net.greenjab.nekomasfixed.world.spawn;

import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Items;
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
                    this.cooldown = this.cooldown + 120 + random.nextInt(12); //12000 1200
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
                                                int n = (int)Math.ceil(world.getLocalDifficulty(mutable).getLocalDifficulty()) + 1;
                                                int boatType = random.nextInt(EntityTypeRegistry.megaBoats.size());
                                                for (int o = 0; o < n; o++) {
                                                    mutable.setY(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable).getY()+2);
                                                    if (o == 0) {
                                                        if (!this.spawnBoat(world, mutable, random, boatType, true)) {
                                                            break;
                                                        }
                                                    } else {
                                                        this.spawnBoat(world, mutable, random, boatType, false);
                                                    }

                                                    mutable.setX(mutable.getX() + (5+random.nextInt(5))*random.nextInt(2)-1);
                                                    mutable.setY(mutable.getY() + 2);
                                                    mutable.setZ(mutable.getZ() + (5+random.nextInt(5))*random.nextInt(2)-1);
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
        MegaBoatEntity megaBoatEntity = EntityTypeRegistry.megaBoats.get(boatType).create(world, SpawnReason.PATROL);

        if (megaBoatEntity != null) {
            megaBoatEntity.setBanner(Raid.createOminousBanner(world.getRegistryManager().getOrThrow(RegistryKeys.BANNER_PATTERN)));
            megaBoatEntity.setHasChest(true);
            megaBoatEntity.getInventory().set(0, Items.OMINOUS_BOTTLE.getDefaultStack());
            megaBoatEntity.refreshPositionAfterTeleport(pos.toCenterPos());
            PatrolEntity patrolEntity = EntityType.PILLAGER.create(world, SpawnReason.PATROL);
            patrolEntity.setPosition(pos.getX(), pos.getY(), pos.getZ());
            patrolEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null);
            patrolEntity.startRiding(megaBoatEntity);

            PatrolEntity patrolEntity2 = EntityType.PILLAGER.create(world, SpawnReason.PATROL);
            patrolEntity2.setPosition(pos.getX(), pos.getY(), pos.getZ());
            patrolEntity2.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null);

            patrolEntity2.startRiding(megaBoatEntity);
            world.spawnEntityAndPassengers(megaBoatEntity);

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
