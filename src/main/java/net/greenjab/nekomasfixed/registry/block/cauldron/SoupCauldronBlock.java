package net.greenjab.nekomasfixed.registry.block.cauldron;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class SoupCauldronBlock extends BaseEntityBlock implements EntityBlock {
    public static final MapCodec<SoupCauldronBlock> CODEC = simpleCodec(SoupCauldronBlock::new);

    private static final VoxelShape RAYCAST_SHAPE = Block.column(12.0, 4.0, 16.0);
    protected static final VoxelShape OUTLINE_SHAPE = Util.make(
             () -> Shapes.join(
                     Shapes.block(),
                     Shapes.or(
                             Block.column(16.0, 8.0, 0.0, 3.0), Block.column(8.0, 16.0, 0.0, 3.0), Block.column(12.0, 0.0, 3.0), RAYCAST_SHAPE
                     ),
                     BooleanOp.ONLY_FIRST
             )
    );

    public SoupCauldronBlock(Properties settings) {
        super(settings);
    }

    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader world, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
        return Items.CAULDRON.getDefaultInstance();
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, Level world, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
        Random random = new Random();
        if (!(world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity be)) {
            return InteractionResult.FAIL;
        } else if (stack.is(Items.STICK) && (world.getBlockState(pos.below()).is(BlockTags.FIRE) || world.getBlockState(pos.below()).is(BlockTags.CAMPFIRES))) {
            if(be.hasStirred){return InteractionResult.FAIL;}
            be.setStirred(world);
            if (world.isClientSide()) for (int i = 0; i < 4; i++) world.addAlwaysVisibleParticle(ParticleTypes.POOF, true, pos.getX()+(0.5 + (random.nextDouble())*(random.nextBoolean()?1:-1)), pos.getY() + 1.0 , pos.getZ()+0.5+(random.nextDouble() * (random.nextBoolean()?1:-1)), 0.001  * (random.nextBoolean()?1:-1), 0.0001, 0.001 *  (random.nextBoolean()?1:-1));
            return InteractionResult.SUCCESS;
        } else if ((FOOD_COLORS.containsKey(stack.getItem())) && (world.getBlockState(pos.below()).is(BlockTags.FIRE) || world.getBlockState(pos.below()).is(BlockTags.CAMPFIRES)) ) {
            if(be.hasStirred){return InteractionResult.FAIL;}
            if(be.getInputs().size()>=4){return InteractionResult.FAIL;}
            if (!world.isClientSide()) {
                if (be.addInput(stack)) stack.consume(1, player);
            }
            world.updateNeighbourForOutputSignal(pos, this);
            return InteractionResult.SUCCESS;
        } else if(stack.is(Items.BOWL)){
            if(!be.hasStirred){return InteractionResult.FAIL;}
            ItemStack soup = new ItemStack(ItemRegistry.SPECIAL_STEW);
            List<ItemStack> copiedInputs = be.getInputs().stream().map(ItemStack::copy).toList();
            soup.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(copiedInputs));
            soup.set(DataComponents.DYED_COLOR, new DyedItemColor(blendFoodColors(copiedInputs)));
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemUtils.createFilledResult(stack, player, soup));
            world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
            for (ItemStack ingredient : copiedInputs) {
                UseRemainder remainder = ingredient.get(DataComponents.USE_REMAINDER);
                if (remainder != null) Block.popResource(world, pos, remainder.convertInto().create());
            }
            return InteractionResult.SUCCESS;
        } else if(stack.is(Items.AIR)){
            if(be.hasStirred){return InteractionResult.FAIL;}
            player.setItemInHand(hand, be.removeInput());
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    protected @NonNull VoxelShape getInteractionShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    protected boolean hasAnalogOutputSignal(@NonNull BlockState state) {
        return true;
    }

    @Override
    protected @NonNull MapCodec<? extends SoupCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new SoupCauldronBlockEntity(pos, state);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, @NonNull BlockState state, @NonNull BlockEntityType<T> type) {
        return world.isClientSide() ? createTickerHelper(type, BlockEntityTypeRegistry.SOUP_CAULDRON_BLOCK_ENTITY, SoupCauldronBlockEntity::clientTick) : null;
    }

    public static SoupCauldronBlock.PropertyRetriever< Float2FloatFunction> getAnimationProgressRetriever(LidBlockEntity progress) {
        return () -> progress::getOpenNess;
    }
    public interface PropertyRetriever<T> {
        T getFallback();
    }

    @Override
    protected int getAnalogOutputSignal(@NonNull BlockState state, @NonNull Level world, @NonNull BlockPos pos, @NonNull Direction direction) {
        int hunger = 0;
        if(world instanceof ServerLevel serverWorld && world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity soupCauldronBlockEntity) {
            for (ItemStack item : soupCauldronBlockEntity.getInputs()) {
                SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(item);
                Optional<RecipeHolder<SmeltingRecipe>> optional = serverWorld
                        .recipeAccess()
                        .getRecipeFor(RecipeType.SMELTING, singleStackRecipeInput, world);
                if (optional.isPresent() && !item.is(Items.CHORUS_FRUIT)) {
                    ItemStack itemStack = (((RecipeHolder) optional.get()).value()).assemble(singleStackRecipeInput);
                    if (!itemStack.isEmpty()) item=itemStack;
                }
                FoodProperties food = item.get(DataComponents.FOOD);
                if (food != null) hunger += Mth.ceil(food.nutrition()/2f);
            }
        }
        return hunger;
    }

    public static final Map<Item, Integer> FOOD_COLORS = Map.ofEntries(
            Map.entry(Items.POTION, 0x385DC6),
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

    public static Optional<Integer> getFoodColor(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return Optional.empty();

        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents != null) return Optional.of(contents.getColor());

        Integer foodColor = FOOD_COLORS.get(stack.getItem());
        if (foodColor != null) return Optional.of(foodColor);

        return Optional.empty();
    }

    public static int blendFoodColors(List<ItemStack> items) {
        int totalR = 0;
        int totalG = 0;
        int totalB = 0;
        int totalWeight = 0;

        for (ItemStack stack : items) {
            if (stack.isEmpty()) continue;
            Optional<Integer> colorOpt = getFoodColor(stack);
            if (colorOpt.isEmpty()) continue;
            int color = colorOpt.get();
            totalR += color >> 16 & 255;
            totalG += color >> 8 & 255;
            totalB += color & 255;
            totalWeight++;
        }
        if (totalWeight == 0) return 0x385DC6; // fallback

        int r = totalR / totalWeight;
        int g = totalG / totalWeight;
        int b = totalB / totalWeight;

        return (r << 16) | (g << 8) | b;
    }
}
