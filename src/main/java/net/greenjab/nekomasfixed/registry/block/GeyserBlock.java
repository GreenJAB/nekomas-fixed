package net.greenjab.nekomasfixed.registry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class GeyserBlock extends Block {

    public GeyserBlock(Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull VoxelShape getInteractionShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos) {return Shapes.block();}

    @Override
    public @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {return Shapes.block();}

    @Override
    public void stepOn(Level world, @NonNull BlockPos pos, @NonNull BlockState state, Entity entity) {
            entity.igniteForSeconds(3);
            entity.setDeltaMovement(entity.getDeltaMovement().x, 1.2, entity.getDeltaMovement().z);
            if(world.isClientSide()){
                java.util.Random random = new java.util.Random();
                for(int i = 0; i<=20; ++i){
                    world.addAlwaysVisibleParticle(ParticleTypes.LARGE_SMOKE, true, pos.getX()+(0.5 + (random.nextDouble())*(random.nextBoolean()?1:-1)), pos.getY() + 1.0 , pos.getZ()+0.5+(random.nextDouble() * (random.nextBoolean()?1:-1)), 0.001  * (random.nextBoolean()?1:-1), 0.0001, 0.001 *  (random.nextBoolean()?1:-1));
                    if(i<=10)world.addAlwaysVisibleParticle(ParticleTypes.FLAME, true, pos.getX()+0.5, pos.getY() + 1.0 , pos.getZ()+0.5, 0, 0.2, 0);
                }
            }
    }

    @Override
    public void animateTick(@NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (world.isClientSide()) {
            world.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + 0.5 + random.nextDouble()/2 * (random.nextBoolean()?1:-1), pos.getY() + random.nextDouble() + random.nextDouble(), pos.getZ() + 0.5 + random.nextDouble()/2 * (random.nextBoolean()?1:-1), 0.0, 0.07, 0.0);
        }
    }

    @Override
    public @NonNull BlockState playerWillDestroy(Level world, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Player player) {
        if (!world.isClientSide()) {
            ItemStack tool = player.getMainHandItem();

            boolean silkTouch =EnchantmentHelper.getItemEnchantmentLevel(world.registryAccess()
                                    .lookupOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT)
                                    .getOrThrow(Enchantments.SILK_TOUCH), tool) > 0;

            if (!silkTouch) {
                world.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
                return state;
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

}
