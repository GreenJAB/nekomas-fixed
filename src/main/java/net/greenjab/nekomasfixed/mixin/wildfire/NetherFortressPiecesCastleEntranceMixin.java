package net.greenjab.nekomasfixed.mixin.wildfire;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.LootTableRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Optional;

@Mixin(NetherFortressPieces.CastleEntrance.class)
public class NetherFortressPiecesCastleEntranceMixin {
    @Unique
    private static final int X = 17;
    @Unique
    private static final int XC = (X - 1) / 2;
    @Unique
    private static final int Y = 15;

    @ModifyArgs(method = "createPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/BoundingBox;orientBox(IIIIIIIIILnet/minecraft/core/Direction;)Lnet/minecraft/world/level/levelgen/structure/BoundingBox;"))
    private static void largerRoom(Args args) {
        args.set(3, -(XC - 1));
        args.set(6, X);
        args.set(7, Y);
        args.set(8, X);
    }

    @ModifyConstant(method = "addChildren", constant = @Constant(intValue = 5))
    private int roomOffset(int constant) {
        return XC - 1;
    }

    @Inject(method = "postProcess", at = @At("HEAD"), cancellable = true)
    private void generateRoom(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBB, ChunkPos chunkPos, BlockPos referencePos, CallbackInfo ci) {
        NetherFortressPieces.NetherBridgePiece piece = (NetherFortressPieces.NetherBridgePiece) (Object) this;

        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState bricks = Blocks.NETHER_BRICKS.defaultBlockState();
        BlockState fenceEW = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
        BlockState fenceNS = Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);

        piece.generateBox(level, chunkBB, 0, 2, 0, X-1, Y-2, X-1, bricks, bricks, false);
        piece.generateBox(level, chunkBB, 2, 5, 2, X-3, Y-3, X-3, air, air, false);
        piece.generateBox(level, chunkBB, 3, 4, 3, X-4, 4, X-4, air, air, false);

        piece.generateBox(level, chunkBB, XC-1, 5, 0, XC+1, 8, 1, air, air, false);
        piece.generateBox(level, chunkBB, XC-1, 5, X-2, XC+1, 8, X-1, air, air, false);
        piece.generateBox(level, chunkBB, XC-1, 8, 0, XC+1, 8, 0, Blocks.NETHER_BRICK_FENCE.defaultBlockState(), Blocks.NETHER_BRICK_FENCE.defaultBlockState(), false);
        piece.generateBox(level, chunkBB, XC-2, 0, 0, XC+2, 1, 0, bricks, bricks, false);

        int i;
        for (i = 1; i <= X-2; i += 2) {
            piece.generateBox(level, chunkBB, i, 11, 0, i, Y-3, 0, fenceEW, fenceEW, false);
            piece.generateBox(level, chunkBB, i, 11, X-1, i, Y-3, X-1, fenceEW, fenceEW, false);
            piece.generateBox(level, chunkBB, 0, 11, i, 0, Y-3, i, fenceNS, fenceNS, false);
            piece.generateBox(level, chunkBB, X-1, 11, i, X-1, Y-3, i, fenceNS, fenceNS, false);
            piece.placeBlock(level, bricks, i, Y-1, 0, chunkBB);
            piece.placeBlock(level, bricks, i, Y-1, X-1, chunkBB);
            piece.placeBlock(level, bricks, 0, Y-1, i, chunkBB);
            piece.placeBlock(level, bricks, X-1, Y-1, i, chunkBB);
            if (i != X-2) {
                piece.placeBlock(level, fenceEW, i + 1, Y-1, 0, chunkBB);
                piece.placeBlock(level, fenceEW, i + 1, Y-1, X-1, chunkBB);
                piece.placeBlock(level, fenceNS, 0, Y-1, i + 1, chunkBB);
                piece.placeBlock(level, fenceNS, X-1, Y-1, i + 1, chunkBB);
            }
        }

        piece.placeBlock(level, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.EAST, true), 0, Y-1, 0, chunkBB);
        piece.placeBlock(level, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.SOUTH, true).setValue(FenceBlock.EAST, true), 0, Y-1, X-1, chunkBB);
        piece.placeBlock(level, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.SOUTH, true).setValue(FenceBlock.WEST, true), X-1, Y-1, X-1, chunkBB);
        piece.placeBlock(level, Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.WEST, true), X-1, Y-1, 0, chunkBB);

        for(i = 3; i <= X-4; i += 2) {
            piece.generateBox(level, chunkBB, 1, 7, i, 1, 10, i, fenceNS.setValue(FenceBlock.WEST, true), fenceNS.setValue(FenceBlock.WEST, true), false);
            piece.generateBox(level, chunkBB, X-2, 7, i, X-2, 10, i, fenceNS.setValue(FenceBlock.EAST, true), fenceNS.setValue(FenceBlock.EAST, true), false);
            if (i != XC-1 && i != XC+1) {
                piece.generateBox(level, chunkBB, i, 7, 1, i, 10, 1, fenceEW.setValue(FenceBlock.WEST, true), fenceNS.setValue(FenceBlock.WEST, true), false);
                piece.generateBox(level, chunkBB, i, 7,X-2, i, 10, X-2, fenceEW.setValue(FenceBlock.EAST, true), fenceNS.setValue(FenceBlock.EAST, true), false);
            }
        }

        piece.placeBlock(level, Blocks.NETHERRACK.defaultBlockState(), 2, 4, 2, chunkBB);
        piece.placeBlock(level, Blocks.NETHERRACK.defaultBlockState(), 2, 4, X-3, chunkBB);
        piece.placeBlock(level, Blocks.NETHERRACK.defaultBlockState(), X-3, 4, 2, chunkBB);
        piece.placeBlock(level, Blocks.NETHERRACK.defaultBlockState(), X-3, 4, X-3, chunkBB);

        piece.placeBlock(level, Blocks.FIRE.defaultBlockState(), 2, 5, 2, chunkBB);
        piece.placeBlock(level, Blocks.FIRE.defaultBlockState(), 2, 5, X-3, chunkBB);
        piece.placeBlock(level, Blocks.FIRE.defaultBlockState(), X-3, 5, 2, chunkBB);
        piece.placeBlock(level, Blocks.FIRE.defaultBlockState(), X-3, 5, X-3, chunkBB);

        piece.placeBlock(level, bricks, 3, 4, 3, chunkBB);
        piece.placeBlock(level, bricks, 3, 4, X-4, chunkBB);
        piece.placeBlock(level, bricks, X-4, 4, 3, chunkBB);
        piece.placeBlock(level, bricks, X-4, 4, X-4, chunkBB);

        for (int xx = 3; xx <= X-4; xx++) {
            for (int zz = 3; zz <= X-4; zz++) {
                if (random.nextInt(3)==0) piece.placeBlock(level, Blocks.MAGMA_BLOCK.defaultBlockState(), xx, 3, zz, chunkBB);
            }
        }

        for (int xx = XC-2; xx <= XC+2; xx++) {
            for (int zz = XC-2; zz <= XC+2; zz++) {
                piece.placeBlock(level, (random.nextInt(3)==0)?bricks:Blocks.MAGMA_BLOCK.defaultBlockState(), xx, 4, zz, chunkBB);
            }
        }
        piece.placeBlock(level, air, XC-2, 4, XC-2, chunkBB);
        piece.placeBlock(level, air, XC+2, 4, XC-2, chunkBB);
        piece.placeBlock(level, air, XC-2, 4, XC+2, chunkBB);
        piece.placeBlock(level, air, XC+2, 4, XC+2, chunkBB);

        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC, 4, XC, chunkBB);
        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC-1, 4, XC, chunkBB);
        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC+1, 4, XC, chunkBB);
        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC, 4, XC-1, chunkBB);
        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC, 4, XC+1, chunkBB);
        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC-1, 3, XC, chunkBB);
        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC+1, 3, XC, chunkBB);
        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC, 3, XC-1, chunkBB);
        piece.placeBlock(level, Blocks.LAVA.defaultBlockState(), XC, 3, XC+1, chunkBB);

        BlockPos blockPos = piece.getWorldPos(XC, 3, XC);
        if (chunkBB.isInside(blockPos)) {
            level.setBlock(blockPos, Blocks.TRIAL_SPAWNER.defaultBlockState(), 2);
            if (level.getBlockEntity(blockPos) instanceof TrialSpawnerBlockEntity trialSpawner) {
                CompoundTag tag = new CompoundTag();
                tag.put("normal_config", spawnerConfig(1, SimpleWeightedRandomList.empty()));
                tag.put("ominous_config", spawnerConfig(2, SimpleWeightedRandomList.single(LootTableRegistry.WILDFIRE_SPAWNER_OMINOUS)));
                trialSpawner.loadWithComponents(tag, level.registryAccess());
            }
        }
        ci.cancel();
    }

    @Unique
    private static Tag spawnerConfig(int mobs, SimpleWeightedRandomList<ResourceKey<LootTable>> loot) {
        CompoundTag entity = new CompoundTag();
        entity.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(EntityTypeRegistry.WILDFIRE).toString());
        TrialSpawnerConfig config = new TrialSpawnerConfig(1, mobs, 2, 0.0F, 0.0F, 20,
                SimpleWeightedRandomList.single(new SpawnData(entity, Optional.empty(), Optional.empty())),
                loot, TrialSpawnerConfig.DEFAULT.itemsToDropWhenOminous());
        return TrialSpawnerConfig.CODEC.encodeStart(NbtOps.INSTANCE, config).getOrThrow();
    }
}
