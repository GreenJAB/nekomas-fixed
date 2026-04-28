package net.greenjab.nekomasfixed.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GeyserBlock extends Block {
    private static final VoxelShape RAYCAST_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);

    public static final VoxelShape OUTLINE_Y = Util.make(() -> VoxelShapes.combineAndSimplify(
            VoxelShapes.fullCube(),
            VoxelShapes.union(Block.createColumnShape(16.0F, 8.0F, 0.0F, 0.0F),
                    Block.createColumnShape(8.0F, 16.0F, 0.0F, 0.0F),
                    Block.createColumnShape(12.0F, 0.0F, 0.0F), RAYCAST_SHAPE),
            BooleanBiFunction.ONLY_FIRST));

    public GeyserBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {return VoxelShapes.fullCube();}

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {return VoxelShapes.fullCube();}



    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
            entity.setOnFireFor(3);
            entity.setVelocity(entity.getVelocity().x, 1.2, entity.getVelocity().z);
            if(world.isClient()){
                java.util.Random random = new java.util.Random();
                for(int i = 0; i<=20; ++i){
                    world.addImportantParticleClient(ParticleTypes.LARGE_SMOKE, true, pos.getX()+(0.5 + (random.nextDouble())*(random.nextBoolean()?1:-1)), pos.getY() + 1.0 , pos.getZ()+0.5+(random.nextDouble() * (random.nextBoolean()?1:-1)), 0.001  * (random.nextBoolean()?1:-1), 0.0001, 0.001 *  (random.nextBoolean()?1:-1));
                    if(i<=10)world.addImportantParticleClient(ParticleTypes.FLAME, true, pos.getX()+0.5, pos.getY() + 1.0 , pos.getZ()+0.5, 0, 0.2, 0);
                }
            }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isClient()) {
            world.addImportantParticleClient(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + 0.5 + random.nextDouble()/2 * (random.nextBoolean()?1:-1), pos.getY() + random.nextDouble() + random.nextDouble(), pos.getZ() + 0.5 + random.nextDouble()/2 * (random.nextBoolean()?1:-1), 0.0, 0.07, 0.0);
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            ItemStack tool = player.getMainHandStack();

            boolean silkTouch =EnchantmentHelper.getLevel(world.getRegistryManager()
                                    .getOrThrow(net.minecraft.registry.RegistryKeys.ENCHANTMENT)
                                    .getOrThrow(Enchantments.SILK_TOUCH), tool) > 0;

            if (!silkTouch) {
                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                return state;
            }
        }
        return super.onBreak(world, pos, state, player);
    }

}
