package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.structure.NetherFortressGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(NetherFortressGenerator.CorridorExit.class)
public class NetherFortressGeneratorMixin {
    @Unique
    private static final int X = 17;
    @Unique
    private static final int XC = (X-1)/2;
    @Unique
    private static final int Y = 15;

    @ModifyArgs(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockBox;rotated(IIIIIIIIILnet/minecraft/util/math/Direction;)Lnet/minecraft/util/math/BlockBox;"))
    private static void largerRoom(Args args) {
        args.set(3, -(XC-1));
        args.set(6, X);
        args.set(7, Y);
        args.set(8, X);
    }

    @ModifyConstant(method = "fillOpenings", constant = @Constant(intValue = 5))
    private int roomOffset(int constant) {
        return XC-1;
    }

    @Inject(method = "generate", at = @At(value = "HEAD"), cancellable = true)
    private void generateRoom(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot, CallbackInfo ci) {

        NetherFortressGenerator.Piece piece = (NetherFortressGenerator.Piece)(Object)this;

        BlockState air = Blocks.AIR.getDefaultState();
        BlockState bricks = Blocks.NETHER_BRICKS.getDefaultState();
        BlockState fenceEW = Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true).with(FenceBlock.EAST, true);
        BlockState fenceNS = Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true).with(FenceBlock.SOUTH, true);


        //main box
        piece.fillWithOutline(world, chunkBox, 0, 2, 0, X-1, Y-2, X-1, bricks, bricks, false);
        piece.fillWithOutline(world, chunkBox, 2, 5, 2, X-3, Y-3, X-3, air, air, false);
        piece.fillWithOutline(world, chunkBox, 3, 4, 3, X-4, 4, X-4, air, air, false);

        //doorways
        piece.fillWithOutline(world, chunkBox, 7, 5, 0, 9, 8, 1, air, air, false);
        piece.fillWithOutline(world, chunkBox, 7, 5, X-2, 9, 7, X-1, air, air, false);
        piece.fillWithOutline(world, chunkBox, 7, 8, 0, 9, 8, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);

        //roof
        int i;
        for (i = 1; i <= X-2; i += 2) {
            piece.fillWithOutline(world, chunkBox, i, 11, 0, i, Y-3, 0, fenceEW, fenceEW, false);
            piece.fillWithOutline(world, chunkBox, i, 11, X-1, i, Y-3, X-1, fenceEW, fenceEW, false);
            piece.fillWithOutline(world, chunkBox, 0, 11, i, 0, Y-3, i, fenceNS, fenceNS, false);
            piece.fillWithOutline(world, chunkBox, X-1, 11, i, X-1, Y-3, i, fenceNS, fenceNS, false);
            piece.addBlock(world, bricks, i, Y-1, 0, chunkBox);
            piece.addBlock(world, bricks, i, Y-1, X-1, chunkBox);
            piece.addBlock(world, bricks, 0, Y-1, i, chunkBox);
            piece.addBlock(world, bricks, X-1, Y-1, i, chunkBox);
            if (i != X-2) {
                piece.addBlock(world, fenceEW, i + 1, Y-1, 0, chunkBox);
                piece.addBlock(world, fenceEW, i + 1, Y-1, X-1, chunkBox);
                piece.addBlock(world, fenceNS, 0, Y-1, i + 1, chunkBox);
                piece.addBlock(world, fenceNS, X-1, Y-1, i + 1, chunkBox);
            }
        }

        piece.addBlock(world, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true).with(FenceBlock.EAST, true), 0, Y-1, 0, chunkBox);
        piece.addBlock(world, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true).with(FenceBlock.EAST, true), 0, Y-1, X-1, chunkBox);
        piece.addBlock(world, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true).with(FenceBlock.WEST, true), X-1, Y-1, X-1, chunkBox);
        piece.addBlock(world, Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true).with(FenceBlock.WEST, true), X-1, Y-1, 0, chunkBox);

        //walls
        for(i = 3; i <= X-4; i += 2) {
            piece.fillWithOutline(world, chunkBox, 1, 7, i, 1, 10, i, fenceNS.with(FenceBlock.WEST, true), fenceNS.with(FenceBlock.WEST, true), false);
            piece.fillWithOutline(world, chunkBox, X-2, 7, i, X-2, 10, i, fenceNS.with(FenceBlock.EAST, true), fenceNS.with(FenceBlock.EAST, true), false);
            if (i != XC-1 && i != XC+1) {
                piece.fillWithOutline(world, chunkBox, i, 7, 1, i, 10, 1, fenceEW.with(FenceBlock.WEST, true), fenceNS.with(FenceBlock.WEST, true), false);
                piece.fillWithOutline(world, chunkBox, i, 7,X-2, i, 10, X-2, fenceEW.with(FenceBlock.EAST, true), fenceNS.with(FenceBlock.EAST, true), false);
            }
        }

        //floor
        piece.addBlock(world, Blocks.NETHERRACK.getDefaultState(), 2, 4, 2, chunkBox);
        piece.addBlock(world, Blocks.NETHERRACK.getDefaultState(), 2, 4, X-3, chunkBox);
        piece.addBlock(world, Blocks.NETHERRACK.getDefaultState(), X-3, 4, 2, chunkBox);
        piece.addBlock(world, Blocks.NETHERRACK.getDefaultState(), X-3, 4, X-3, chunkBox);

        piece.addBlock(world, Blocks.FIRE.getDefaultState(), 2, 5, 2, chunkBox);
        piece.addBlock(world, Blocks.FIRE.getDefaultState(), 2, 5, X-3, chunkBox);
        piece.addBlock(world, Blocks.FIRE.getDefaultState(), X-3, 5, 2, chunkBox);
        piece.addBlock(world, Blocks.FIRE.getDefaultState(), X-3, 5, X-3, chunkBox);

        piece.addBlock(world, bricks, 3, 4, 3, chunkBox);
        piece.addBlock(world, bricks, 3, 4, X-4, chunkBox);
        piece.addBlock(world, bricks, X-4, 4, 3, chunkBox);
        piece.addBlock(world, bricks, X-4, 4, X-4, chunkBox);

        for (int xx = 3; xx <= X-4; xx++) {
            for (int zz = 3; zz <= X-4; zz++) {
                if (world.getRandom().nextInt(3)==0) piece.addBlock(world, Blocks.MAGMA_BLOCK.getDefaultState(), xx, 3, zz, chunkBox);
            }
        }

        for (int xx = XC-2; xx <= XC+2; xx++) {
            for (int zz = XC-2; zz <= XC+2; zz++) {
                piece.addBlock(world, (world.getRandom().nextInt(3)==0)?bricks:Blocks.MAGMA_BLOCK.getDefaultState(), xx, 4, zz, chunkBox);
            }
        }
        piece.addBlock(world, air, XC-2, 4, XC-2, chunkBox);
        piece.addBlock(world, air, XC+2, 4, XC-2, chunkBox);
        piece.addBlock(world, air, XC-2, 4, XC+2, chunkBox);
        piece.addBlock(world, air, XC+2, 4, XC+2, chunkBox);

        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC, 4, XC, chunkBox);
        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC-1, 4, XC, chunkBox);
        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC+1, 4, XC, chunkBox);
        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC, 4, XC-1, chunkBox);
        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC, 4, XC+1, chunkBox);
        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC-1, 3, XC, chunkBox);
        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC+1, 3, XC, chunkBox);
        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC, 3, XC-1, chunkBox);
        piece.addBlock(world, Blocks.LAVA.getDefaultState(), XC, 3, XC+1, chunkBox);

        BlockPos blockPos = piece.offsetPos( XC, 3, XC);
        if (chunkBox.contains(blockPos)) {
            world.setBlockState(blockPos, Blocks.TRIAL_SPAWNER.getDefaultState(), 2);
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof TrialSpawnerBlockEntity trialSpawnerBlockEntity) {
                trialSpawnerBlockEntity.setEntityType(EntityTypeRegistry.WILD_FIRE, random);
            }
        }
        ci.cancel();
    }

}
