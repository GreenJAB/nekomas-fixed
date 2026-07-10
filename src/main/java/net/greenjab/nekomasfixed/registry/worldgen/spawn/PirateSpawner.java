package net.greenjab.nekomasfixed.registry.worldgen.spawn;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class PirateSpawner implements CustomSpawner {
    private int cooldown;

    @Override
    public void tick(@NonNull ServerLevel world, boolean spawnMonsters) {
        if (!spawnMonsters) return;
        if (!world.getGameRules().get(GameRules.SPAWN_PATROLS)) return;
        RandomSource random = world.getRandom();
        this.cooldown--;
        if (this.cooldown > 0) return;
        this.cooldown = this.cooldown + 12000 + random.nextInt(1200);
        long l = world.getGameTime() / 24000L;
        if (l >= 5L && world.isBrightOutside()) {
            if (random.nextInt(3) != 0) return;
            int i = world.players().size();
            if (i == 0) return;
            Player playerEntity = world.players().get(random.nextInt(i));
            if (playerEntity.isSpectator()) return;
            if (world.isCloseToVillage(playerEntity.blockPosition(), 2)) return;
            if (nearOtherPatrols(world, playerEntity.blockPosition())) return;
            int j = (64 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
            int k = (64 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
            MutableBlockPos mutable = playerEntity.blockPosition().mutable().move(j, 0, k);
            int m = 10;
            if (!world.hasChunksAt(mutable.getX() - m, mutable.getZ() - m, mutable.getX() + m, mutable.getZ() + m))
                return;
            Holder<Biome> registryEntry = world.getBiome(mutable);
            if (!registryEntry.is(BiomeTags.IS_OCEAN)) return;
            mutable.setY(world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, mutable).getY());
            if (!world.getBlockState(mutable).is(Blocks.AIR) && world.getBlockState(mutable.below()).is(Blocks.WATER))
                return;
            for (int bx = mutable.getX() - 8; bx < mutable.getX() + 8; bx++) {
                for (int by = mutable.getY() - 2; by < mutable.getY() + 4; by++) {
                    for (int bz = mutable.getZ() - 8; bz < mutable.getZ() + 8; bz++) {
                        BlockState blockState = world.getBlockState(new BlockPos(bx, by, bz));
                        if (!(blockState.is(Blocks.AIR) || blockState.is(Blocks.WATER))) return;
                    }
                }
            }
            mutable.offset(0, 2, 0);
            int n = (int) Math.ceil(world.getCurrentDifficultyAt(mutable).getEffectiveDifficulty()) + 1;
            int boatType = random.nextInt(EntityTypeRegistry.bigBoats.size());
            for (int o = 0; o < n; o++) {
                if (o == 0) if (!this.spawnBoat(world, mutable, random, boatType, true)) break;
                else this.spawnBoat(world, mutable, random, boatType, false);
                mutable.setX(mutable.getX() + (3 + random.nextInt(3)) * (int) Math.signum(random.nextInt(2) - 0.5));
                mutable.setY(mutable.getY() + 2);
                mutable.setZ(mutable.getZ() + (3 + random.nextInt(3)) * (int) Math.signum(random.nextInt(2) - 0.5));
            }
        }
    }

    private boolean nearOtherPatrols(ServerLevel world, BlockPos blockPos) {
        return !world.getEntitiesOfClass(Raider.class, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(blockPos)).inflate(100), EntitySelector.LIVING_ENTITY_STILL_ALIVE).isEmpty();
    }

    private boolean spawnBoat(ServerLevel world, BlockPos pos, RandomSource random, int boatType, boolean captain) {
        BlockState blockState = world.getBlockState(pos);
        if (!NaturalSpawner.isValidEmptySpawnBlock(world, pos, blockState, blockState.getFluidState(), EntityType.PILLAGER)) {
            return false;
        } else if (!PatrollingMonster.checkPatrollingMonsterSpawnRules(EntityType.PILLAGER, world, EntitySpawnReason.PATROL, pos, random)) {
            return false;
        } else {
            if (captain) return spawnCaptainBoat(world, pos, random, boatType);
            else return spawnSmallBoat(world, pos, random, boatType);
        }
    }

    boolean spawnCaptainBoat(ServerLevel world, BlockPos pos, RandomSource random, int boatType){
        BigBoat bigBoat = world.getDifficulty().getId()>2?
                EntityTypeRegistry.hugeBoats.get(boatType).create(world, EntitySpawnReason.PATROL):
                EntityTypeRegistry.bigBoats.get(boatType).create(world, EntitySpawnReason.PATROL);
        if (bigBoat == null) return false;
        bigBoat.setBanner(Raid.getOminousBannerInstance(world.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN)));
        bigBoat.setHasChest(true);
        bigBoat.setContainerLootTable(ResourceKey.create(Registries.LOOT_TABLE, NekomasFixed.id("chests/patrol_boat")));
        bigBoat.setContainerLootTableSeed(random.nextLong());
        bigBoat.snapTo(pos.getCenter());
        for (int i = 0; i < world.getDifficulty().getId();i++) {
            PatrollingMonster patrolEntity = EntityType.PILLAGER.create(world, EntitySpawnReason.PATROL);
            patrolEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
            patrolEntity.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), EntitySpawnReason.PATROL, null);
            patrolEntity.startRiding(bigBoat);
        }
        world.addFreshEntityWithPassengers(bigBoat);
        return true;
    }

    boolean spawnSmallBoat(ServerLevel world, BlockPos pos, RandomSource random, int boatType){
        AbstractBoat boatEntity = EntityTypeRegistry.boats.get(boatType).create(world, EntitySpawnReason.PATROL);
        if (boatEntity != null) {
            boatEntity.snapTo(pos.getCenter());
            PatrollingMonster patrolEntity = EntityType.PILLAGER.create(world, EntitySpawnReason.PATROL);
            patrolEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
            patrolEntity.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), EntitySpawnReason.PATROL, null);
            patrolEntity.startRiding(boatEntity);
            world.addFreshEntityWithPassengers(boatEntity);
            return true;
        } else return false;
    }
}
