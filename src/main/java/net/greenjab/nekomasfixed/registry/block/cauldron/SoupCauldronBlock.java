package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class SoupCauldronBlock extends AbstractCauldronBlock implements BlockEntityProvider {
    public static final MapCodec<SoupCauldronBlock> CODEC = createCodec(SoupCauldronBlock::new);

    public SoupCauldronBlock(Settings settings) {
        super(settings, createBehaviorMap());
    }

    private static CauldronBehavior.CauldronBehaviorMap createBehaviorMap() {
        return CauldronBehavior.createMap("soup");
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Random random = new Random();
        if (!(world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity be)) {
            return ActionResult.FAIL;
        }
        if (stack.isOf(Items.STICK) && (world.getBlockState(pos.down()).isIn(BlockTags.FIRE) || world.getBlockState(pos.down()).isIn(BlockTags.CAMPFIRES))) {
            if(be.hasStirred){return ActionResult.FAIL;}
            be.setHasStirred(true);
            if (world.isClient()) {for (int i = 0; i < 20; i++) {world.addImportantParticleClient(ParticleTypes.POOF, true, pos.getX()+(0.5 + (random.nextDouble())*(random.nextBoolean()?1:-1)), pos.getY() + 1.0 , pos.getZ()+0.5+(random.nextDouble() * (random.nextBoolean()?1:-1)), 0.001  * (random.nextBoolean()?1:-1), 0.0001, 0.001 *  (random.nextBoolean()?1:-1));}} else {be.startStirAnimation();}
            return ActionResult.SUCCESS;
        }
        if ((stack.getComponents().contains(DataComponentTypes.FOOD) || stack.getComponents().contains(DataComponentTypes.POTION_CONTENTS)) && (world.getBlockState(pos.down()).isIn(BlockTags.FIRE) || world.getBlockState(pos.down()).isIn(BlockTags.CAMPFIRES)) ) {
            if(be.hasStirred){return ActionResult.FAIL;}
            if(be.inputAmount()>=4){return ActionResult.FAIL;}
            if (!world.isClient()) {
                be.addInput(stack);
                stack.decrementUnlessCreative(1, player);
            }
            return ActionResult.SUCCESS;
        }
        if(stack.isOf(Items.BOWL) && be.hasStirred){
            be.setHasStirred(false);
            ItemStack soup = new ItemStack(ItemRegistry.SPECIAL_STEW);
            List<ItemStack> copiedInputs = be.getInputs().stream().map(ItemStack::copy).toList();
            soup.set(ComponentRegistry.SOUP_INGREDIENTS, copiedInputs);
            player.setStackInHand(Hand.MAIN_HAND, ItemUsage.exchangeStack(stack, player, soup));
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoupCauldronBlockEntity(pos, state);
    }
}
