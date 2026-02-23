package net.greenjab.nekomasfixed.registry.block.cauldron;


import net.greenjab.nekomasfixed.registry.block.MagmaCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Map;

public class CauldronBehaviour {

    public static void register() {
        Map<Item, CauldronBehavior> waterMap = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();

            System.out.println("CauldronBehaviour.register() started");

            Map<Item, CauldronBehavior> emptyMap = CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.map();


        Map<Item, CauldronBehavior> magmaMap = CauldronBehavior.createMap("magma").map();

// Magma cream adds to cauldron
        magmaMap.put(Items.MAGMA_CREAM, (state, world, pos, player, hand, stack) -> {
            System.out.println("🔥 MAGMA CREAM used on cauldron at " + pos);

            if (!world.isClient()) {
                // Check if it's already a magma cauldron
                if (state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
                    int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);
                    if (level < MagmaCauldronBlock.MAX_LEVEL) {
                        world.setBlockState(pos, state.with(MagmaCauldronBlock.MAGMA_LEVEL, level + 1));
                        stack.decrement(1);
                        world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                                SoundCategory.BLOCKS, 1.0F, 1.0F);
                        System.out.println("✅ Magma level increased to " + (level + 1));
                    }
                } else if (state.getBlock() == Blocks.CAULDRON) {
                    // Convert empty cauldron to magma cauldron
                    world.setBlockState(pos, BlockRegistry.MAGMA_CAULDRON.getDefaultState()
                            .with(MagmaCauldronBlock.MAGMA_LEVEL, 1));
                    stack.decrement(1);
                    world.playSound(null, pos, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                    System.out.println("✅ Empty cauldron converted to magma cauldron");
                }
            }
            return ActionResult.SUCCESS;
        });

// Glass bottle takes magma cream
        magmaMap.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("🧪 GLASS BOTTLE used on magma cauldron at " + pos);

            if (!world.isClient() && state.getBlock() == BlockRegistry.MAGMA_CAULDRON) {
                int level = state.get(MagmaCauldronBlock.MAGMA_LEVEL);

                // Give magma cream to player
                player.getInventory().offerOrDrop(new ItemStack(Items.MAGMA_CREAM));

                if (level > 1) {
                    world.setBlockState(pos, state.with(MagmaCauldronBlock.MAGMA_LEVEL, level - 1));
                    System.out.println("✅ Magma level decreased to " + (level - 1));
                } else {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                    System.out.println("✅ Magma cauldron emptied");
                }

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

// Empty hand harvest when full




            emptyMap.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
                System.out.println("EMPTY CAULDRON: Honey bottle used at " + pos);
                if (!world.isClient()) {
                    world.setBlockState(pos, BlockRegistry.HONEY_CAULDRON.getDefaultState()
                            .with(LeveledCauldronBlock.LEVEL, 1));

                    player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return ActionResult.SUCCESS;
            });

            registerHoneyCauldronBehaviors();

            System.out.println("CauldronBehaviour.register() finished");



        addWoolBehavior(waterMap, Items.WHITE_WOOL);
        addWoolBehavior(waterMap, Items.ORANGE_WOOL);
        addWoolBehavior(waterMap, Items.MAGENTA_WOOL);
        addWoolBehavior(waterMap, Items.LIGHT_BLUE_WOOL);
        addWoolBehavior(waterMap, Items.YELLOW_WOOL);
        addWoolBehavior(waterMap, Items.LIME_WOOL);
        addWoolBehavior(waterMap, Items.PINK_WOOL);
        addWoolBehavior(waterMap, Items.GRAY_WOOL);
        addWoolBehavior(waterMap, Items.LIGHT_GRAY_WOOL);
        addWoolBehavior(waterMap, Items.CYAN_WOOL);
        addWoolBehavior(waterMap, Items.PURPLE_WOOL);
        addWoolBehavior(waterMap, Items.BLUE_WOOL);
        addWoolBehavior(waterMap, Items.BROWN_WOOL);
        addWoolBehavior(waterMap, Items.GREEN_WOOL);
        addWoolBehavior(waterMap, Items.RED_WOOL);
        addWoolBehavior(waterMap, Items.BLACK_WOOL);

        addTerracottaBehaviour(waterMap, Items.WHITE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.LIGHT_GRAY_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.GRAY_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.BLACK_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.BROWN_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.RED_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.ORANGE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.YELLOW_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.LIME_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.GREEN_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.CYAN_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.LIGHT_BLUE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.BLUE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.PURPLE_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.MAGENTA_TERRACOTTA);
        addTerracottaBehaviour(waterMap, Items.PINK_TERRACOTTA);

        addGlazedterracottaBehaviour(waterMap, Items.WHITE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.LIGHT_GRAY_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.GRAY_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.BLACK_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.BROWN_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.RED_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.ORANGE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.YELLOW_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.LIME_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.GREEN_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.CYAN_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.LIGHT_BLUE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.BLUE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.PURPLE_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.MAGENTA_GLAZED_TERRACOTTA);
        addGlazedterracottaBehaviour(waterMap, Items.PINK_GLAZED_TERRACOTTA);

        addStainedglassBehaviour(waterMap, Items.WHITE_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.LIGHT_GRAY_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.GRAY_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.BLACK_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.BROWN_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.RED_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.ORANGE_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.YELLOW_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.LIME_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.GREEN_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.CYAN_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.LIGHT_BLUE_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.BLUE_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.PURPLE_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.MAGENTA_STAINED_GLASS);
        addStainedglassBehaviour(waterMap, Items.PINK_STAINED_GLASS);

        addCandleBehaviour(waterMap, Items.WHITE_CANDLE);
        addCandleBehaviour(waterMap, Items.LIGHT_GRAY_CANDLE);
        addCandleBehaviour(waterMap, Items.GRAY_CANDLE);
        addCandleBehaviour(waterMap, Items.BLACK_CANDLE);
        addCandleBehaviour(waterMap, Items.BROWN_CANDLE);
        addCandleBehaviour(waterMap, Items.RED_CANDLE);
        addCandleBehaviour(waterMap, Items.ORANGE_CANDLE);
        addCandleBehaviour(waterMap, Items.YELLOW_CANDLE);
        addCandleBehaviour(waterMap, Items.LIME_CANDLE);
        addCandleBehaviour(waterMap, Items.GREEN_CANDLE);
        addCandleBehaviour(waterMap, Items.CYAN_CANDLE);
        addCandleBehaviour(waterMap, Items.LIGHT_BLUE_CANDLE);
        addCandleBehaviour(waterMap, Items.BLUE_CANDLE);
        addCandleBehaviour(waterMap, Items.PURPLE_CANDLE);
        addCandleBehaviour(waterMap, Items.MAGENTA_CANDLE);
        addCandleBehaviour(waterMap, Items.PINK_CANDLE);

        addStainedglasspaneBehaviour(waterMap, Items.WHITE_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.LIGHT_GRAY_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.GRAY_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.BLACK_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.BROWN_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.RED_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.ORANGE_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.YELLOW_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.LIME_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.GREEN_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.CYAN_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.LIGHT_BLUE_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.BLUE_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.PURPLE_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.MAGENTA_STAINED_GLASS_PANE);
        addStainedglasspaneBehaviour(waterMap, Items.PINK_STAINED_GLASS_PANE);

        addConcretepowderBehaviour(waterMap, Items.WHITE_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.LIGHT_GRAY_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.GRAY_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.BLACK_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.BROWN_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.RED_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.ORANGE_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.YELLOW_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.LIME_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.GREEN_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.CYAN_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.LIGHT_BLUE_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.BLUE_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.PURPLE_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.MAGENTA_CONCRETE_POWDER);
        addConcretepowderBehaviour(waterMap, Items.PINK_CONCRETE_POWDER);

        addCarpetBehaviour(waterMap, Items.WHITE_CARPET);
        addCarpetBehaviour(waterMap, Items.LIGHT_GRAY_CARPET);
        addCarpetBehaviour(waterMap, Items.GRAY_CARPET);
        addCarpetBehaviour(waterMap, Items.BLACK_CARPET);
        addCarpetBehaviour(waterMap, Items.BROWN_CARPET);
        addCarpetBehaviour(waterMap, Items.RED_CARPET);
        addCarpetBehaviour(waterMap, Items.ORANGE_CARPET);
        addCarpetBehaviour(waterMap, Items.YELLOW_CARPET);
        addCarpetBehaviour(waterMap, Items.LIME_CARPET);
        addCarpetBehaviour(waterMap, Items.GREEN_CARPET);
        addCarpetBehaviour(waterMap, Items.CYAN_CARPET);
        addCarpetBehaviour(waterMap, Items.LIGHT_BLUE_CARPET);
        addCarpetBehaviour(waterMap, Items.BLUE_CARPET);
        addCarpetBehaviour(waterMap, Items.PURPLE_CARPET);
        addCarpetBehaviour(waterMap, Items.MAGENTA_CARPET);
        addCarpetBehaviour(waterMap, Items.PINK_CARPET);

        addConcreteBehaviour(waterMap, Items.WHITE_CONCRETE);
        addConcreteBehaviour(waterMap, Items.LIGHT_GRAY_CONCRETE);
        addConcreteBehaviour(waterMap, Items.GRAY_CONCRETE);
        addConcreteBehaviour(waterMap, Items.BLACK_CONCRETE);
        addConcreteBehaviour(waterMap, Items.BROWN_CONCRETE);
        addConcreteBehaviour(waterMap, Items.RED_CONCRETE);
        addConcreteBehaviour(waterMap, Items.ORANGE_CONCRETE);
        addConcreteBehaviour(waterMap, Items.YELLOW_CONCRETE);
        addConcreteBehaviour(waterMap, Items.LIME_CONCRETE);
        addConcreteBehaviour(waterMap, Items.GREEN_CONCRETE);
        addConcreteBehaviour(waterMap, Items.CYAN_CONCRETE);
        addConcreteBehaviour(waterMap, Items.LIGHT_BLUE_CONCRETE);
        addConcreteBehaviour(waterMap, Items.BLUE_CONCRETE);
        addConcreteBehaviour(waterMap, Items.PURPLE_CONCRETE);
        addConcreteBehaviour(waterMap, Items.MAGENTA_CONCRETE);
        addConcreteBehaviour(waterMap, Items.PINK_CONCRETE);

        addBedBehaviour(waterMap, Items.WHITE_BED);
        addBedBehaviour(waterMap, Items.LIGHT_GRAY_BED);
        addBedBehaviour(waterMap, Items.GRAY_BED);
        addBedBehaviour(waterMap, Items.BLACK_BED);
        addBedBehaviour(waterMap, Items.BROWN_BED);
        addBedBehaviour(waterMap, Items.RED_BED);
        addBedBehaviour(waterMap, Items.ORANGE_BED);
        addBedBehaviour(waterMap, Items.YELLOW_BED);
        addBedBehaviour(waterMap, Items.LIME_BED);
        addBedBehaviour(waterMap, Items.GREEN_BED);
        addBedBehaviour(waterMap, Items.CYAN_BED);
        addBedBehaviour(waterMap, Items.LIGHT_BLUE_BED);
        addBedBehaviour(waterMap, Items.BLUE_BED);
        addBedBehaviour(waterMap, Items.PURPLE_BED);
        addBedBehaviour(waterMap, Items.MAGENTA_BED);
        addBedBehaviour(waterMap, Items.PINK_BED);

        addHarnessBehaviour(waterMap, Items.WHITE_HARNESS);
        addHarnessBehaviour(waterMap, Items.LIGHT_GRAY_HARNESS);
        addHarnessBehaviour(waterMap, Items.GRAY_HARNESS);
        addHarnessBehaviour(waterMap, Items.BLACK_HARNESS);
        addHarnessBehaviour(waterMap, Items.BROWN_HARNESS);
        addHarnessBehaviour(waterMap, Items.RED_HARNESS);
        addHarnessBehaviour(waterMap, Items.ORANGE_HARNESS);
        addHarnessBehaviour(waterMap, Items.YELLOW_HARNESS);
        addHarnessBehaviour(waterMap, Items.LIME_HARNESS);
        addHarnessBehaviour(waterMap, Items.GREEN_HARNESS);
        addHarnessBehaviour(waterMap, Items.CYAN_HARNESS);
        addHarnessBehaviour(waterMap, Items.LIGHT_BLUE_HARNESS);
        addHarnessBehaviour(waterMap, Items.BLUE_HARNESS);
        addHarnessBehaviour(waterMap, Items.PURPLE_HARNESS);
        addHarnessBehaviour(waterMap, Items.MAGENTA_HARNESS);
        addHarnessBehaviour(waterMap, Items.PINK_HARNESS);
    }
    private static void registerHoneyCauldronBehaviors() {
        System.out.println("Registering honey cauldron behaviors...");

        // Get the behavior map for honey cauldron
        // You need to create a custom behavior map for your honey cauldron
        CauldronBehavior.CauldronBehaviorMap honeyMap =
                CauldronBehavior.createMap("honey_cauldron");

        Map<Item, CauldronBehavior> map = honeyMap.map();

        // Glass bottle takes honey
        map.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("HONEY CAULDRON: Glass bottle used at " + pos);
            if (!world.isClient()) {
                int level = state.get(LeveledCauldronBlock.LEVEL);
                player.setStackInHand(hand, new ItemStack(Items.HONEY_BOTTLE));

                if (level > 1) {
                    world.setBlockState(pos, state.with(LeveledCauldronBlock.LEVEL, level - 1));
                } else {
                    world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                }

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return ActionResult.SUCCESS;
        });

        // Honey bottle adds honey
        map.put(Items.HONEY_BOTTLE, (state, world, pos, player, hand, stack) -> {
            System.out.println("HONEY CAULDRON: Honey bottle used at " + pos);
            if (!world.isClient()) {
                int level = state.get(LeveledCauldronBlock.LEVEL);
                if (level < 3) {
                    world.setBlockState(pos, state.with(LeveledCauldronBlock.LEVEL, level + 1));
                    player.setStackInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return ActionResult.SUCCESS;
        });

        // Now you need to associate this behavior map with your honey cauldron block
        // This might require a mixin or a different approach
        System.out.println("Honey cauldron behaviors registered, map size: " + map.size());
    }

    private static void addWoolBehavior(Map<Item, CauldronBehavior> map, Item woolItem) {
        map.put(woolItem, CauldronBehaviour::cleanWool);
    }
    private static void addTerracottaBehaviour(Map<Item, CauldronBehavior> map, Item terracottaItem) {
        map.put(terracottaItem, CauldronBehaviour::cleanTerracotta);
    }
    private static void addGlazedterracottaBehaviour(Map<Item, CauldronBehavior> map, Item glazedTerracottaItem) {
        map.put(glazedTerracottaItem, CauldronBehaviour::cleanGlazedTerracotta);
    }
    private static void addStainedglassBehaviour(Map<Item, CauldronBehavior> map, Item glassItem) {
        map.put(glassItem, CauldronBehaviour::cleanGlass);
    }
    private static void addCandleBehaviour(Map<Item, CauldronBehavior> map, Item candleItem) {
        map.put(candleItem, CauldronBehaviour::cleanCandle);
    }
    private static void addStainedglasspaneBehaviour(Map<Item, CauldronBehavior> map, Item paneItem) {
        map.put(paneItem, CauldronBehaviour::cleanGlassPane);
    }
    private static void addConcretepowderBehaviour(Map<Item, CauldronBehavior> map, Item concretePowder) {
        map.put(concretePowder, CauldronBehaviour::cleanConcretePowder);
    }
    private static void addCarpetBehaviour(Map<Item, CauldronBehavior> map, Item carpetItem) {
        map.put(carpetItem, CauldronBehaviour::cleanCarpet);
    }
    private static void addConcreteBehaviour(Map<Item, CauldronBehavior> map, Item concreteItem) {
        map.put(concreteItem, CauldronBehaviour::cleanConcrete);
    }
    private static void addBedBehaviour(Map<Item, CauldronBehavior> map, Item bedItem) {
        map.put(bedItem, CauldronBehaviour::cleanBed);
    }
    private static void addHarnessBehaviour(Map<Item, CauldronBehavior> map, Item harnessItem) {
        map.put(harnessItem, CauldronBehaviour::cleanHarness);
    }

    private static ActionResult cleanWool(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.WHITE_WOOL) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(ItemTags.WOOL)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack whiteWool = new ItemStack(Items.WHITE_WOOL, stack.getCount());
        player.setStackInHand(hand, whiteWool);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanTerracotta(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.TERRACOTTA) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(ItemTags.TERRACOTTA) ) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack terracotta = new ItemStack(Items.TERRACOTTA, stack.getCount());
        player.setStackInHand(hand, terracotta);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanGlazedTerracotta(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.TERRACOTTA) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(OtherRegistry.GLAZED_TERRACOTTAS)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack terracotta = new ItemStack(Items.TERRACOTTA, stack.getCount());
        player.setStackInHand(hand, terracotta);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanGlass(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.GLASS) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(OtherRegistry.STAINED_GLASSES)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack glass = new ItemStack(Items.GLASS, stack.getCount());
        player.setStackInHand(hand, glass);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanCandle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.CANDLE) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(ItemTags.CANDLES)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack candle = new ItemStack(Items.CANDLE, stack.getCount());
        player.setStackInHand(hand, candle);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanGlassPane(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.GLASS_PANE) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(OtherRegistry.STAINED_PANES)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack pane = new ItemStack(Items.GLASS_PANE, stack.getCount());
        player.setStackInHand(hand, pane);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanConcretePowder(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.WHITE_CONCRETE_POWDER) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(OtherRegistry.CONCRETE_POWDERS)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack pane = new ItemStack(Items.WHITE_CONCRETE_POWDER, stack.getCount());
        player.setStackInHand(hand, pane);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanCarpet(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.WHITE_CARPET) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(ItemTags.WOOL_CARPETS)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack carpet = new ItemStack(Items.WHITE_CARPET, stack.getCount());
        player.setStackInHand(hand, carpet);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanConcrete(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.WHITE_CONCRETE) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(OtherRegistry.CONCRETES)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack carpet = new ItemStack(Items.WHITE_CONCRETE, stack.getCount());
        player.setStackInHand(hand, carpet);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanBed(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.WHITE_BED) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(ItemTags.BEDS)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack carpet = new ItemStack(Items.WHITE_BED, stack.getCount());
        player.setStackInHand(hand, carpet);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
    private static ActionResult cleanHarness(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {

        if (stack.getItem() == Items.WHITE_HARNESS) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (!stack.isIn(ItemTags.HARNESSES)) {
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ItemStack carpet = new ItemStack(Items.WHITE_HARNESS, stack.getCount());
        player.setStackInHand(hand, carpet);
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                SoundCategory.BLOCKS, 1.0F, 1.0F);

        return ActionResult.SUCCESS;
    }
}