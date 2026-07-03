package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.UseRemainderComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class SoupCauldronBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<SoupCauldronBlock> CODEC = createCodec(SoupCauldronBlock::new);

    private static final VoxelShape RAYCAST_SHAPE = Block.createColumnShape(12.0, 4.0, 16.0);
    protected static final VoxelShape OUTLINE_SHAPE = Util.make(
             () -> VoxelShapes.combineAndSimplify(
                     VoxelShapes.fullCube(),
                     VoxelShapes.union(
                             Block.createColumnShape(16.0, 8.0, 0.0, 3.0), Block.createColumnShape(8.0, 16.0, 0.0, 3.0), Block.createColumnShape(12.0, 0.0, 3.0), RAYCAST_SHAPE
                     ),
                     BooleanBiFunction.ONLY_FIRST
             )
    );

    public SoupCauldronBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Random random = new Random();
        if (!(world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity be)) {
            return ActionResult.FAIL;
        } else if (stack.isOf(Items.STICK) && (world.getBlockState(pos.down()).isIn(BlockTags.FIRE) || world.getBlockState(pos.down()).isIn(BlockTags.CAMPFIRES))) {
            if(be.hasStirred){return ActionResult.FAIL;}
            be.setStirred(world);
            if (world.isClient()) for (int i = 0; i < 4; i++) world.addImportantParticleClient(ParticleTypes.POOF, true, pos.getX()+(0.5 + (random.nextDouble())*(random.nextBoolean()?1:-1)), pos.getY() + 1.0 , pos.getZ()+0.5+(random.nextDouble() * (random.nextBoolean()?1:-1)), 0.001  * (random.nextBoolean()?1:-1), 0.0001, 0.001 *  (random.nextBoolean()?1:-1));
            return ActionResult.SUCCESS;
        } else if ((FOOD_COLORS.containsKey(stack.getItem()) || stack.getComponents().contains(DataComponentTypes.POTION_CONTENTS)) && (world.getBlockState(pos.down()).isIn(BlockTags.FIRE) || world.getBlockState(pos.down()).isIn(BlockTags.CAMPFIRES)) ) {
            if(be.hasStirred){return ActionResult.FAIL;}
            if(be.getInputs().size()>=4){return ActionResult.FAIL;}
            if (!world.isClient()) {
                if (be.addInput(stack)) stack.decrementUnlessCreative(1, player);
            }
            world.updateComparators(pos, this);
            return ActionResult.SUCCESS;
        } else if(stack.isOf(Items.BOWL)){
            if(!be.hasStirred){return ActionResult.FAIL;}
            ItemStack soup = new ItemStack(ItemRegistry.SPECIAL_STEW);
            List<ItemStack> copiedInputs = be.getInputs().stream().map(ItemStack::copy).toList();
            soup.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(copiedInputs));
            soup.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(blendFoodColors(1, copiedInputs)));
            player.setStackInHand(Hand.MAIN_HAND, ItemUsage.exchangeStack(stack, player, soup));
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            for (ItemStack ingredient : copiedInputs) {
                UseRemainderComponent remainder = ingredient.get(DataComponentTypes.USE_REMAINDER);
                if (remainder != null) Block.dropStack(world, pos, remainder.convertInto());
            }
            return ActionResult.SUCCESS;
        } else if(stack.isOf(Items.AIR)){
            if(be.hasStirred){return ActionResult.FAIL;}
            player.setStackInHand(hand, be.removeInput());
        }
        return ActionResult.FAIL;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    protected VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    protected MapCodec<? extends SoupCauldronBlock> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoupCauldronBlockEntity(pos, state);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? validateTicker(type, BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY, SoupCauldronBlockEntity::clientTick) : null;
    }

    public static SoupCauldronBlock.PropertyRetriever< Float2FloatFunction> getAnimationProgressRetriever(LidOpenable progress) {
        return () -> progress::getAnimationProgress;
    }
    public interface PropertyRetriever<T> {
        T getFallback();
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
        int hunger = 0;
        if(world instanceof ServerWorld serverWorld && world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity soupCauldronBlockEntity) {
            for (ItemStack item : soupCauldronBlockEntity.getInputs()) {
                SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(item);
                Optional<RecipeEntry<SmeltingRecipe>> optional = serverWorld
                        .getRecipeManager()
                        .getFirstMatch(RecipeType.SMELTING, singleStackRecipeInput, world);
                if (optional.isPresent() && !item.isOf(Items.CHORUS_FRUIT)) {
                    ItemStack itemStack = (((RecipeEntry) optional.get()).value()).craft(singleStackRecipeInput, world.getRegistryManager());
                    if (!itemStack.isEmpty()) item=itemStack;
                }
                FoodComponent food = item.get(DataComponentTypes.FOOD);
                if (food != null) hunger += MathHelper.ceil(food.nutrition()/2f);
            }
        }
        return hunger;
    }

    public static final Map<Item, Integer> FOOD_COLORS = Map.ofEntries(
            Map.entry(Items.APPLE, 0xFC1C2A),
            Map.entry(Items.GOLDEN_APPLE, 0xE7EB56),
            Map.entry(Items.ENCHANTED_GOLDEN_APPLE, 0xE7EB56),
            Map.entry(Items.MELON_SLICE, 0xBD3023),
            Map.entry(Items.SWEET_BERRIES, 0xA30700),
            Map.entry(Items.GLOW_BERRIES, 0xF4DF6A),
            Map.entry(Items.CHORUS_FRUIT, 0x8C668B),
            Map.entry(Items.CARROT, 0xFC8C09),
            Map.entry(Items.GOLDEN_CARROT, 0xE7EB56),
            Map.entry(Items.POTATO, 0xD6A850),
            Map.entry(Items.BAKED_POTATO, 0xD6A850),
            Map.entry(Items.BEETROOT, 0xA2272B),
            Map.entry(Items.KELP, 0x3B3224),
            Map.entry(Items.DRIED_KELP, 0x3B3224),
            Map.entry(Items.BEEF, 0x703E2C),
            Map.entry(Items.COOKED_BEEF, 0x703E2C),
            Map.entry(Items.PORKCHOP, 0xD0BE86),
            Map.entry(Items.COOKED_PORKCHOP, 0xD0BE86),
            Map.entry(Items.MUTTON, 0x804739),
            Map.entry(Items.COOKED_MUTTON, 0x804739),
            Map.entry(Items.CHICKEN, 0xCB7C49),
            Map.entry(Items.COOKED_CHICKEN, 0xCB7C49),
            Map.entry(Items.RABBIT, 0xCF8C61),
            Map.entry(Items.COOKED_RABBIT, 0xCF8C61),
            Map.entry(Items.COD, 0xD3C3AB),
            Map.entry(Items.COOKED_COD, 0xD3C3AB),
            Map.entry(Items.SALMON, 0xB84E23),
            Map.entry(Items.COOKED_SALMON, 0xB84E23),
            Map.entry(Items.TROPICAL_FISH, 0xF16E20),
            Map.entry(Items.MILK_BUCKET, 0xFCFCFC),
            Map.entry(Items.HONEY_BOTTLE, 0xFC8F16),

            Map.entry(ItemRegistry.BAOBAB_FRUIT, 0x686D24)
    );

    public static int getTintIndex(BlockRenderView world, BlockPos pos, int tintIndex){
        if(world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity soupCauldronBlockEntity){
            float f = soupCauldronBlockEntity.getAnimationProgress(0);
            return tintIndex == 0 ? blendFoodColors(f, soupCauldronBlockEntity.getInputs()) : 0xFFFFFFFF;
        }else{
            return -1;
        }
    }

    public static Optional<Integer> getFoodColor(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return Optional.empty();

        Item item = stack.getItem();
        Integer foodColor = FOOD_COLORS.get(item);
        if (foodColor != null) {
            return Optional.of(foodColor);
        }
        if (stack.contains(DataComponentTypes.POTION_CONTENTS)) {
            var contents = stack.get(DataComponentTypes.POTION_CONTENTS);
            if (contents != null) {
                return Optional.of(contents.getColor());
            }
        }

        return Optional.empty();
    }

    public static int blendFoodColors(float f, List<ItemStack> items) {
        float totalR = 0.0F;
        float totalG = 0.0F;
        float totalB = 0.0F;
        float totalWeight = 0.0F;

        for (ItemStack stack : items) {
            if (stack.isEmpty()) continue;

            Optional<Integer> colorOpt = getFoodColor(stack);
            if (colorOpt.isEmpty()) continue;
            int color = colorOpt.get();

            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;

            totalR += r;
            totalG += g;
            totalB += b;
            totalWeight += 1;
        }

        if (totalWeight == 0) return 0x385DC6; // fallback

        float r = totalR / totalWeight;
        float g = totalG / totalWeight;
        float b = totalB / totalWeight;

        r *= 255.0F;
        g *= 255.0F;
        b *= 255.0F;

        int finalR = (int)(f*r+(1-f)*56);
        int finalG = (int)(f*g+(1-f)*93);
        int finalB = (int)(f*b+(1-f)*198);

        return (finalR << 16) | (finalG << 8) | finalB;
    }
}
