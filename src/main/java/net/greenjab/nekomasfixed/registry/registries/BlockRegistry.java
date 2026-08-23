package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.*;
import net.greenjab.nekomasfixed.registry.block.cauldron.*;
import net.greenjab.nekomasfixed.registry.block.enums.ClamType;
import net.greenjab.nekomasfixed.registry.block.enums.NautilusBlockType;
import net.greenjab.nekomasfixed.registry.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import java.util.Optional;
import java.util.function.Function;

import static net.minecraft.world.level.block.Blocks.buttonProperties;
import static net.minecraft.world.level.block.Blocks.leavesProperties;

public class BlockRegistry {

    public static final Block CLAM = register("clam", settings -> new ClamBlock(ClamType.REGULAR, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(1F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final Block CLAM_BLUE = register("clam_blue", settings -> new ClamBlock(ClamType.BLUE, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final Block CLAM_PINK = register("clam_pink", settings -> new ClamBlock(ClamType.PINK, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(1F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final Block CLAM_PURPLE = register("clam_purple", settings -> new ClamBlock(ClamType.PURPLE, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(1F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final Block PEARL_BLOCK = register("pearl_block", BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.CALCITE).requiresCorrectToolForDrops().strength(0.75F));

    public static final Block NAUTILUS_BLOCK = register("nautilus_block", settings -> new NautilusBlock(NautilusBlockType.REGULAR, settings), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(1F).sound(SoundType.CORAL_BLOCK).pushReaction(PushReaction.DESTROY));
    public static final Block ZOMBIE_NAUTILUS_BLOCK = register("zombie_nautilus_block", settings -> new NautilusBlock(NautilusBlockType.ZOMBIE, settings), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(1F).sound(SoundType.CORAL_BLOCK).pushReaction(PushReaction.DESTROY));
    public static final Block CORAL_NAUTILUS_BLOCK = register("coral_nautilus_block",settings -> new NautilusBlock(NautilusBlockType.CORAL, settings), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(1F).sound(SoundType.CORAL_BLOCK).pushReaction(PushReaction.DESTROY));
    public static final Block GLISTERING_MELON = register("glistering_melon", settings -> new MelonBlock(true, settings), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(1F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final Block GEYSER = register("geyser", GeyserBlock::new , BlockBehaviour.Properties.of().randomTicks().strength(0.5f, 0.5f).lightLevel(_ -> 15));
    public static final Block KILN = register("kiln", KilnBlock::new,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.GILDED_BLACKSTONE).requiresCorrectToolForDrops().strength(3.5f));
    public static final Block PYROTECHNICS_TABLE = register("pyrotechnics_table", PyrotechnicsTableBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD).ignitedByLava());
    public static final Block ENDERMAN_HEAD = register("enderman_head", FloorEndermanHeadHead::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(1F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY).instrument(NoteBlockInstrument.CUSTOM_HEAD));
    public static final Block WALL_ENDERMAN_HEAD = register("wall_enderman_head", WallEndermanHeadHead::new, copyLootTable(ENDERMAN_HEAD, true).mapColor(MapColor.COLOR_BLACK).strength(1F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY));
    public static final Block GLOW_TORCH = register(
            "glow_torch",
            GlowTorchBlock::new,
            BlockBehaviour.Properties.of()
                    .noCollision()
                    .instabreak()
                    .lightLevel(state -> state.getValue(BlockStateProperties.WATERLOGGED) ? 13 : 0)
                    .sound(SoundType.WOOD)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block GLOW_WALL_TORCH = register(
            "glow_wall_torch",
            WallGlowTorchBlock::new,
            copyLootTable(GLOW_TORCH, true)
                    .noCollision()
                    .instabreak()
                    .lightLevel(state -> state.getValue(BlockStateProperties.WATERLOGGED) ? 13 : 0)
                    .sound(SoundType.WOOD)
                    .pushReaction(PushReaction.DESTROY)
    );

    public static final Block SWEETBERRY_CAKE = register("sweetberry_cake", StackedCakeBlock::new, BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(StackedCakeBlock.LIT)?3:0));
    public static final Block PAN_CAKE = register("pan_cake", StackedCakeBlock::new, BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(StackedCakeBlock.LIT)?3:0));
    public static final Block GLOWBERRY_CAKE = register("glowberry_cake", StackedCakeBlock::new, BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(StackedCakeBlock.LIT)?3:0));
    public static final Block APPLE_CAKE = register("apple_cake", StackedCakeBlock::new, BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(StackedCakeBlock.LIT)?3:0));
    public static final Block VANILLA_CAKE = register("vanilla_cake", StackedCakeBlock::new, BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(StackedCakeBlock.LIT)?3:0));
    public static final Block COOKIE_CAKE = register("cookie_cake", StackedCakeBlock::new, BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(StackedCakeBlock.LIT)?3:0));
    public static final Block CHOCOLATE_CAKE = register("chocolate_cake", StackedCakeBlock::new, BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(StackedCakeBlock.LIT)?3:0));
    public static final Block BEETROOT_CAKE = register("beetroot_cake", StackedCakeBlock::new, BlockBehaviour.Properties.of().lightLevel(state -> state.getValue(StackedCakeBlock.LIT)?3:0));

    static BlockSetType BAOBAB_BLOCKSETTYPE = BlockSetType.register(new BlockSetType("baobab"));
    static WoodType BAOBAB_WOODTYPE = WoodType.register(new WoodType("baobab", BAOBAB_BLOCKSETTYPE));
    public static final Block BAOBAB_LOG = register("baobab_log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.WOOD));
    public static final Block BAOBAB_WOOD = register("baobab_wood", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));
    public static final Block STRIPPED_BAOBAB_LOG = register(
            "stripped_baobab_log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG));
    public static final Block STRIPPED_BAOBAB_WOOD = register(
            "stripped_baobab_wood", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD));
    public static final Block BAOBAB_PLANKS = register("baobab_planks", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final Block BAOBAB_STAIRS = registerOldStairsBlock("baobab_stairs", BAOBAB_PLANKS);
    public static final Block BAOBAB_SLAB = register(
            "baobab_slab",
            SlabBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()
    );
    public static final Block BAOBAB_FENCE = register(
            "baobab_fence",
            FenceBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(BAOBAB_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD).ignitedByLava()
    );
    public static final Block BAOBAB_FENCE_GATE = register(
            "baobab_fence_gate",
            settings -> new FenceGateBlock(BAOBAB_WOODTYPE, settings),
            BlockBehaviour.Properties.of().mapColor(BAOBAB_PLANKS.defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()
    );
    public static final Block BAOBAB_DOOR = register(
            "baobab_door",
            settings -> new DoorBlock(BAOBAB_BLOCKSETTYPE, settings),
            BlockBehaviour.Properties.of()
                    .mapColor(BAOBAB_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.WOOD).ignitedByLava()
    );
    public static final Block BAOBAB_TRAPDOOR = register(
            "baobab_trapdoor",
            settings -> new TrapDoorBlock(BAOBAB_BLOCKSETTYPE, settings),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
                    .sound(SoundType.WOOD).ignitedByLava()
    );
    public static final Block BAOBAB_PRESSURE_PLATE = register(
            "baobab_pressure_plate",
            settings -> new PressurePlateBlock(BAOBAB_BLOCKSETTYPE, settings),
            BlockBehaviour.Properties.of()
                    .mapColor(BAOBAB_PLANKS.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY).ignitedByLava()
    );
    public static final Block BAOBAB_BUTTON = register(
            "baobab_button",settings -> new ButtonBlock(BAOBAB_BLOCKSETTYPE, 30, settings), buttonProperties().ignitedByLava()
    );
    public static final Block BAOBAB_LEAVES = register("baobab_leaves", settings -> new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, -9399763), settings), leavesProperties(SoundType.GRASS));
    public static final Block BAOBAB_SAPLING = register("baobab_sapling",(settings) -> new SaplingBlock(new TreeGrower("nekomasfixed:baobab",  Optional.of(ModConfiguredFeatures.BAOBAB_KEY),Optional.empty(), Optional.empty()),  settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING));
    public static final Block BAOBAB_FRUIT = register("baobab_fruit", BaobabFruitBlock::new, BlockBehaviour.Properties.of().randomTicks().strength(0.2f).isViewBlocking(BlockRegistry::never).ignitedByLava().instabreak());
    public static final Block ROPE = register("rope", RopeBlock::new, BlockBehaviour.Properties.of().strength(0.2f).isRedstoneConductor(BlockRegistry::never).ignitedByLava().noCollision());
    public static final Block BAOBAB_SHELF = register(
            "baobab_shelf",
            ShelfBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(BAOBAB_PLANKS.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f,3.0F)
                    .sound(SoundType.SHELF).ignitedByLava()
    );
    public static final Block BAOBAB_SIGN = register(
            "baobab_sign",
            settings -> new StandingSignBlock(BAOBAB_WOODTYPE, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava()
    );
    public static final Block BAOBAB_WALL_SIGN = register(
            "baobab_wall_sign",
            settings -> new WallSignBlock(BAOBAB_WOODTYPE, settings),
            copyLootTable(BAOBAB_SIGN, true).mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava()
    );
    public static final Block BAOBAB_HANGING_SIGN = register(
            "baobab_hanging_sign",
            settings -> new CeilingHangingSignBlock(BAOBAB_WOODTYPE, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava()
    );
    public static final Block BAOBAB_WALL_HANGING_SIGN = register(
            "baobab_wall_hanging_sign",
            settings -> new WallHangingSignBlock(BAOBAB_WOODTYPE, settings),
            copyLootTable(BAOBAB_HANGING_SIGN, true).mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).ignitedByLava()
    );

    public static final Block TERMITE_BLOCK = register("termite_block", BlockBehaviour.Properties.of().strength(1f));
    public static final Block TERMITE_HIVE = register("termite_hive", TermitehiveBlock::new, BlockBehaviour.Properties.of().strength(1f));
    public static final Block HOLLOW_OAK_LOG = register("hollow_oak_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_SPRUCE_LOG = register("hollow_spruce_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_BIRCH_LOG = register("hollow_birch_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_JUNGLE_LOG = register("hollow_jungle_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_ACACIA_LOG = register("hollow_acacia_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_DARK_OAK_LOG = register("hollow_dark_oak_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_MANGROVE_LOG = register("hollow_mangrove_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_CHERRY_LOG = register("hollow_cherry_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_PALE_OAK_LOG = register("hollow_pale_oak_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_OAK_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_BAMBOO_BLOCK = register("hollow_bamboo_block", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_BLOCK).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_CRIMSON_STEM = register("hollow_crimson_stem", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_HYPHAE).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_WARPED_STEM = register("hollow_warped_stem", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_HYPHAE).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_BAOBAB_LOG = register("hollow_baobab_log", HollowLogBlock::new , BlockBehaviour.Properties.ofFullCopy(BAOBAB_LOG).lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL)));


    public static final Block GOAT_HORN = register("horn", GoatHornBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).lightLevel(state -> state.getValue(GoatHornBlock.TORCH).getLight()).strength(0.2F).sound(SoundType.TUFF).pushReaction(PushReaction.DESTROY));
    public static final Block CLOCK = registerVanilla("clock", FloorClockBlock::new, BlockBehaviour.Properties.of().noCollision().mapColor(MapColor.COLOR_YELLOW).strength(0.2F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY));
    public static final Block WALL_CLOCK = registerVanilla("wall_clock", WallClockBlock::new, copyLootTable(CLOCK, true).noCollision().mapColor(MapColor.COLOR_YELLOW).strength(0.2F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY));
    public static Block HONEY_CAULDRON = null;
    public static Block MAGMA_CAULDRON = null;
    public static Block SLIME_CAULDRON = null;
    public static Block ICE_CAULDRON = null;
    public static Block SOUP_CAULDRON = null;




    public static final Block AMBER_WOOL = register("amber_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block AQUA_WOOL = register("aqua_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block INDIGO_WOOL = register("indigo_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block MAROON_WOOL = register("maroon_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block AMBER_CARPET = register("amber_carpet", (settings) -> new WoolCarpetBlock(DyeColor.YELLOW, settings), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block AQUA_CARPET = register("aqua_carpet", (settings) -> new WoolCarpetBlock(DyeColor.LIGHT_BLUE, settings),BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block INDIGO_CARPET = register("indigo_carpet", (settings) -> new WoolCarpetBlock(DyeColor.MAGENTA, settings), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block MAROON_CARPET = register("maroon_carpet", (settings) -> new WoolCarpetBlock(DyeColor.RED, settings), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());

    public static final Block AMBER_TERRACOTTA = register("amber_terracotta", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).strength(0.70F).explosionResistance(4.2F).requiresCorrectToolForDrops());
    public static final Block AQUA_TERRACOTTA = register("aqua_terracotta", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.70F).explosionResistance(4.2F).requiresCorrectToolForDrops());
    public static final Block INDIGO_TERRACOTTA = register("indigo_terracotta", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.70F).explosionResistance(4.2F).requiresCorrectToolForDrops());
    public static final Block MAROON_TERRACOTTA = register("maroon_terracotta", BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).instrument(NoteBlockInstrument.BASEDRUM).strength(0.70F).explosionResistance(4.2F).requiresCorrectToolForDrops());

    public static final Block AMBER_CONCRETE = register("amber_concrete", BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F));
    public static final Block AQUA_CONCRETE = register("aqua_concrete", BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F));
    public static final Block INDIGO_CONCRETE = register("indigo_concrete", BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F));
    public static final Block MAROON_CONCRETE = register("maroon_concrete", BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.8F));
    public static final Block AMBER_CONCRETE_POWDER = register("amber_concrete_powder", (settings) -> new ConcretePowderBlock(AMBER_CONCRETE, settings), BlockBehaviour.Properties.of().mapColor(DyeColor.YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));
    public static final Block AQUA_CONCRETE_POWDER = register("aqua_concrete_powder", (settings) -> new ConcretePowderBlock(AQUA_CONCRETE, settings), BlockBehaviour.Properties.of().mapColor(DyeColor.LIGHT_BLUE).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));
    public static final Block MAROON_CONCRETE_POWDER = register("maroon_concrete_powder", (settings) -> new ConcretePowderBlock(MAROON_CONCRETE, settings), BlockBehaviour.Properties.of().mapColor(DyeColor.RED).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));
    public static final Block INDIGO_CONCRETE_POWDER = register("indigo_concrete_powder", (settings) -> new ConcretePowderBlock(INDIGO_CONCRETE, settings), BlockBehaviour.Properties.of().mapColor(DyeColor.MAGENTA).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));

    public static final Block AMBER_GLAZED_TERRACOTTA = register("amber_glazed_terracotta", GlazedTerracottaBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.4F).explosionResistance(4.2F).requiresCorrectToolForDrops());
    public static final Block AQUA_GLAZED_TERRACOTTA = register("aqua_glazed_terracotta", GlazedTerracottaBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.4F).explosionResistance(4.2F).requiresCorrectToolForDrops());
    public static final Block INDIGO_GLAZED_TERRACOTTA = register("indigo_glazed_terracotta", GlazedTerracottaBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_MAGENTA).instrument(NoteBlockInstrument.BASEDRUM).strength(1.4F).explosionResistance(4.2F).requiresCorrectToolForDrops());
    public static final Block MAROON_GLAZED_TERRACOTTA = register("maroon_glazed_terracotta", GlazedTerracottaBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).instrument(NoteBlockInstrument.BASEDRUM).strength(1.4F).explosionResistance(4.2F).requiresCorrectToolForDrops());

    public static final Block AMBER_STAINED_GLASS = registerStainedGlassBlock("amber_stained_glass", DyeColor.YELLOW);
    public static final Block AQUA_STAINED_GLASS = registerStainedGlassBlock("aqua_stained_glass", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_STAINED_GLASS = registerStainedGlassBlock("indigo_stained_glass", DyeColor.MAGENTA);
    public static final Block MAROON_STAINED_GLASS = registerStainedGlassBlock("maroon_stained_glass", DyeColor.RED);
    public static final Block AMBER_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("amber_stained_glass_pane", DyeColor.YELLOW);
    public static final Block AQUA_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("aqua_stained_glass_pane", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("indigo_stained_glass_pane", DyeColor.MAGENTA);
    public static final Block MAROON_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("maroon_stained_glass_pane", DyeColor.RED);

    public static final Block AMBER_SHULKER_BOX = registerShulkerBoxBlock("amber_shulker_box", DyeColor.YELLOW);
    public static final Block AQUA_SHULKER_BOX = registerShulkerBoxBlock("aqua_shulker_box", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_SHULKER_BOX = registerShulkerBoxBlock("indigo_shulker_box", DyeColor.MAGENTA);
    public static final Block MAROON_SHULKER_BOX = registerShulkerBoxBlock("maroon_shulker_box", DyeColor.RED);

    public static final Block AMBER_BED = registerBedBlock("amber_bed", DyeColor.YELLOW);
    public static final Block AQUA_BED = registerBedBlock("aqua_bed", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_BED = registerBedBlock("indigo_bed", DyeColor.MAGENTA);
    public static final Block MAROON_BED = registerBedBlock("maroon_bed", DyeColor.RED);

    public static final Block AMBER_CANDLE = register("amber_candle", CandleBlock::new, createCandleSettings(MapColor.COLOR_YELLOW));
    public static final Block AQUA_CANDLE = register("aqua_candle", CandleBlock::new, createCandleSettings(MapColor.WARPED_NYLIUM));
    public static final Block INDIGO_CANDLE = register("indigo_candle", CandleBlock::new, createCandleSettings(MapColor.ICE));
    public static final Block MAROON_CANDLE = register("maroon_candle", CandleBlock::new, createCandleSettings(MapColor.CRIMSON_HYPHAE));


    public static final Block WHITE_BRICKS = register("white_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.WHITE));
    public static final Block ORANGE_BRICKS = register("orange_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.ORANGE));
    public static final Block MAGENTA_BRICKS = register("magenta_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_BRICKS = register("light_blue_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_BRICKS = register("yellow_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.YELLOW));
    public static final Block LIME_BRICKS = register("lime_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.LIME));
    public static final Block PINK_BRICKS = register("pink_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.PINK));
    public static final Block GRAY_BRICKS = register("gray_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_BRICKS = register("light_gray_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_BRICKS = register("cyan_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.CYAN));
    public static final Block PURPLE_BRICKS = register("purple_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.PURPLE));
    public static final Block BLUE_BRICKS = register("blue_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.BLUE));
    public static final Block BROWN_BRICKS = register("brown_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.BROWN));
    public static final Block GREEN_BRICKS = register("green_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.GREEN));
    public static final Block RED_BRICKS = register("red_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.RED));
    public static final Block BLACK_BRICKS = register("black_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.BLACK));
    public static final Block AMBER_BRICKS = register("amber_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.YELLOW));
    public static final Block AQUA_BRICKS = register("aqua_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.LIGHT_BLUE));
    public static final Block INDIGO_BRICKS = register("indigo_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.MAGENTA));
    public static final Block MAROON_BRICKS = register("maroon_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).mapColor(DyeColor.RED));

    public static final Block WHITE_BRICK_SLAB = register("white_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.WHITE));
    public static final Block LIGHT_GRAY_BRICK_SLAB = register("light_gray_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.LIGHT_GRAY));
    public static final Block GRAY_BRICK_SLAB = register("gray_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.GRAY));
    public static final Block BLACK_BRICK_SLAB = register("black_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.BLACK));
    public static final Block BROWN_BRICK_SLAB = register("brown_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.BROWN));
    public static final Block RED_BRICK_SLAB = register("red_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.RED));
    public static final Block ORANGE_BRICK_SLAB = register("orange_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.ORANGE));
    public static final Block YELLOW_BRICK_SLAB = register("yellow_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.YELLOW));
    public static final Block LIME_BRICK_SLAB = register("lime_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.LIME));
    public static final Block GREEN_BRICK_SLAB = register("green_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.GREEN));
    public static final Block CYAN_BRICK_SLAB = register("cyan_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.CYAN));
    public static final Block LIGHT_BLUE_BRICK_SLAB = register("light_blue_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.LIGHT_BLUE));
    public static final Block BLUE_BRICK_SLAB = register("blue_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.BLUE));
    public static final Block PURPLE_BRICK_SLAB = register("purple_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.PURPLE));
    public static final Block MAGENTA_BRICK_SLAB = register("magenta_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.MAGENTA));
    public static final Block PINK_BRICK_SLAB = register("pink_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.PINK));
    public static final Block AMBER_BRICK_SLAB = register("amber_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.YELLOW));
    public static final Block AQUA_BRICK_SLAB = register("aqua_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.LIGHT_BLUE));
    public static final Block INDIGO_BRICK_SLAB = register("indigo_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.MAGENTA));
    public static final Block MAROON_BRICK_SLAB = register("maroon_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB).mapColor(DyeColor.RED));

    public static final Block WHITE_BRICK_STAIRS = registerOldStairsBlock("white_brick_stairs", WHITE_BRICKS);
    public static final Block LIGHT_GRAY_BRICK_STAIRS = registerOldStairsBlock("light_gray_brick_stairs", LIGHT_GRAY_BRICKS);
    public static final Block GRAY_BRICK_STAIRS = registerOldStairsBlock("gray_brick_stairs", GRAY_BRICKS);
    public static final Block BLACK_BRICK_STAIRS = registerOldStairsBlock("black_brick_stairs", BLACK_BRICKS);
    public static final Block BROWN_BRICK_STAIRS = registerOldStairsBlock("brown_brick_stairs", BROWN_BRICKS);
    public static final Block RED_BRICK_STAIRS = registerOldStairsBlock("red_brick_stairs", RED_BRICKS);
    public static final Block ORANGE_BRICK_STAIRS = registerOldStairsBlock("orange_brick_stairs", ORANGE_BRICKS);
    public static final Block YELLOW_BRICK_STAIRS = registerOldStairsBlock("yellow_brick_stairs", YELLOW_BRICKS);
    public static final Block LIME_BRICK_STAIRS = registerOldStairsBlock("lime_brick_stairs", LIME_BRICKS);
    public static final Block GREEN_BRICK_STAIRS = registerOldStairsBlock("green_brick_stairs",GREEN_BRICKS);
    public static final Block CYAN_BRICK_STAIRS = registerOldStairsBlock("cyan_brick_stairs",CYAN_BRICKS);
    public static final Block LIGHT_BLUE_BRICK_STAIRS = registerOldStairsBlock("light_blue_brick_stairs", LIGHT_BLUE_BRICKS);
    public static final Block BLUE_BRICK_STAIRS = registerOldStairsBlock("blue_brick_stairs", BLUE_BRICKS);
    public static final Block PURPLE_BRICK_STAIRS = registerOldStairsBlock("purple_brick_stairs", PURPLE_BRICKS);
    public static final Block MAGENTA_BRICK_STAIRS = registerOldStairsBlock("magenta_brick_stairs", MAGENTA_BRICKS);
    public static final Block PINK_BRICK_STAIRS = registerOldStairsBlock("pink_brick_stairs", PINK_BRICKS);
    public static final Block AMBER_BRICK_STAIRS = registerOldStairsBlock("amber_brick_stairs", AMBER_BRICKS);
    public static final Block AQUA_BRICK_STAIRS = registerOldStairsBlock("aqua_brick_stairs", AQUA_BRICKS);
    public static final Block INDIGO_BRICK_STAIRS = registerOldStairsBlock("indigo_brick_stairs", INDIGO_BRICKS);
    public static final Block MAROON_BRICK_STAIRS = registerOldStairsBlock("maroon_brick_stairs", MAROON_BRICKS);
    
    public static final Block WHITE_BRICK_WALL = register("white_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(WHITE_BRICKS).forceSolidOn());
    public static final Block LIGHT_GRAY_BRICK_WALL = register("light_gray_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(LIGHT_GRAY_BRICKS).forceSolidOn());
    public static final Block GRAY_BRICK_WALL = register("gray_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(GRAY_BRICKS).forceSolidOn());
    public static final Block BLACK_BRICK_WALL = register("black_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(BLACK_BRICKS).forceSolidOn());
    public static final Block BROWN_BRICK_WALL = register("brown_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(BROWN_BRICKS).forceSolidOn());
    public static final Block RED_BRICK_WALL = register("red_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(RED_BRICKS).forceSolidOn());
    public static final Block ORANGE_BRICK_WALL = register("orange_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(ORANGE_BRICKS).forceSolidOn());
    public static final Block YELLOW_BRICK_WALL = register("yellow_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(YELLOW_BRICKS).forceSolidOn());
    public static final Block LIME_BRICK_WALL = register("lime_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(LIME_BRICKS).forceSolidOn());
    public static final Block GREEN_BRICK_WALL = register("green_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(GREEN_BRICKS).forceSolidOn());
    public static final Block CYAN_BRICK_WALL = register("cyan_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(CYAN_BRICKS).forceSolidOn());
    public static final Block LIGHT_BLUE_BRICK_WALL = register("light_blue_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(LIGHT_BLUE_BRICKS).forceSolidOn());
    public static final Block BLUE_BRICK_WALL = register("blue_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(BLUE_BRICKS).forceSolidOn());
    public static final Block PURPLE_BRICK_WALL = register("purple_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(PURPLE_BRICKS).forceSolidOn());
    public static final Block MAGENTA_BRICK_WALL = register("magenta_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(MAGENTA_BRICKS).forceSolidOn());
    public static final Block PINK_BRICK_WALL = register("pink_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(PINK_BRICKS).forceSolidOn());
    public static final Block AMBER_BRICK_WALL = register("amber_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(AMBER_BRICKS).forceSolidOn());
    public static final Block AQUA_BRICK_WALL = register("aqua_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(AQUA_BRICKS).forceSolidOn());
    public static final Block INDIGO_BRICK_WALL = register("indigo_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(INDIGO_BRICKS).forceSolidOn());
    public static final Block MAROON_BRICK_WALL = register("maroon_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofLegacyCopy(MAROON_BRICKS).forceSolidOn());

    public static final Block CLEAR_FROGLIGHT = register("clear_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block CLOUDY_FROGLIGHT = register("cloudy_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block CASCADING_FROGLIGHT = register("cascading_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block CLOUDBURST_FROGLIGHT = register("cloudburst_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.3F).lightLevel(_ -> 10).sound(SoundType.FROGLIGHT));
    public static final Block CHAMOISEE_FROGLIGHT = register("chamoisee_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block SANGUINE_FROGLIGHT = register("sanguine_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block VERMILION_FROGLIGHT = register("vermilion_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block MANDARIN_FROGLIGHT = register("mandarin_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block LEMON_FROGLIGHT = register("lemon_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block KIWI_FROGLIGHT = register("kiwi_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block SEAFOAM_FROGLIGHT = register("seafoam_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_NYLIUM).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block TEAL_FROGLIGHT = register("teal_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block CERULEAN_FROGLIGHT = register("cerulean_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block NAVY_FROGLIGHT = register("navy_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block LAVENDER_FROGLIGHT = register("lavender_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_HYPHAE).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block THULIAN_FROGLIGHT = register("thulian_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));
    public static final Block SAKURA_FROGLIGHT = register("sakura_froglight", RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(0.3F).lightLevel(_ -> 15).sound(SoundType.FROGLIGHT));

    public static final Block WHITE_SPOTTED_WOOL = register("white_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block LIGHT_GRAY_SPOTTED_WOOL = register("light_gray_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block GRAY_SPOTTED_WOOL = register("gray_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block BLACK_SPOTTED_WOOL = register("black_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block BROWN_SPOTTED_WOOL = register("brown_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block RED_SPOTTED_WOOL = register("red_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block ORANGE_SPOTTED_WOOL = register("orange_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block YELLOW_SPOTTED_WOOL = register("yellow_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block LIME_SPOTTED_WOOL = register("lime_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block GREEN_SPOTTED_WOOL = register("green_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block CYAN_SPOTTED_WOOL = register("cyan_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block LIGHT_BLUE_SPOTTED_WOOL = register("light_blue_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block BLUE_SPOTTED_WOOL = register("blue_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block PURPLE_SPOTTED_WOOL = register("purple_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block MAGENTA_SPOTTED_WOOL = register("magenta_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block PINK_SPOTTED_WOOL = register("pink_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block AMBER_SPOTTED_WOOL = register("amber_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block AQUA_SPOTTED_WOOL = register("aqua_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block INDIGO_SPOTTED_WOOL = register("indigo_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block MAROON_SPOTTED_WOOL = register("maroon_spotted_wool", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).ignitedByLava());

    public static final Block WHITE_SPOTTED_CARPET = register("white_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block LIGHT_GRAY_SPOTTED_CARPET = register("light_gray_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block GRAY_SPOTTED_CARPET = register("gray_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block BLACK_SPOTTED_CARPET = register("black_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block BROWN_SPOTTED_CARPET = register("brown_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block RED_SPOTTED_CARPET = register("red_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block ORANGE_SPOTTED_CARPET = register("orange_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block YELLOW_SPOTTED_CARPET = register("yellow_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block LIME_SPOTTED_CARPET = register("lime_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block GREEN_SPOTTED_CARPET = register("green_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block CYAN_SPOTTED_CARPET = register("cyan_spotted_carpet", CarpetBlock::new,BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block LIGHT_BLUE_SPOTTED_CARPET = register("light_blue_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block BLUE_SPOTTED_CARPET = register("blue_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block PURPLE_SPOTTED_CARPET = register("purple_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block MAGENTA_SPOTTED_CARPET = register("magenta_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block PINK_SPOTTED_CARPET = register("pink_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block AMBER_SPOTTED_CARPET = register("amber_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block AQUA_SPOTTED_CARPET = register("aqua_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block INDIGO_SPOTTED_CARPET = register("indigo_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());
    public static final Block MAROON_SPOTTED_CARPET = register("maroon_spotted_carpet", CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sound(SoundType.WOOL).ignitedByLava());


    private static Block register(String id, BlockBehaviour.Properties settings) {
        return register(id, Block::new, settings);
    }
    private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return register(keyOf(id), factory, settings);
    }

    private static Block registerVanilla(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return register(vanillaKeyOf(id), factory, settings);
    }
    private static ResourceKey<Block> keyOf(String id) {
        return ResourceKey.create(Registries.BLOCK, NekomasFixed.id(id));
    }
    private static ResourceKey<Block> vanillaKeyOf(String id) {
        return ResourceKey.create(Registries.BLOCK, Identifier.withDefaultNamespace(id));
    }
    public static Block register(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        Block block = factory.apply(settings.setId(key));
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    public static BlockBehaviour.Properties createCandleSettings(MapColor mapColor) {
        return BlockBehaviour.Properties.of().mapColor(mapColor).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(CandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY);
    }
    private static BlockBehaviour.Properties copyLootTable(Block block, boolean copyTranslationKey) {
        BlockBehaviour.Properties settings = BlockBehaviour.Properties.of().overrideLootTable(block.getLootTable());
        if (copyTranslationKey) {
            settings = settings.overrideDescription(block.getDescriptionId());
        }

        return settings;
    }

    private static Block registerStainedGlassBlock(String id, DyeColor color) {
        return register(id, (settings) -> new StainedGlassBlock(color, settings), BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.HAT).strength(0.3F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never));
    }

    private static Block registerStainedGlassPaneBlock(String id, DyeColor color) {
        return register(id, (settings) -> new StainedGlassPaneBlock(color, settings), BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.HAT).strength(0.3F).sound(SoundType.GLASS).noOcclusion());
    }
    private static Block registerShulkerBoxBlock(String id, DyeColor color) {
        return register(id, settings -> new ShulkerBoxBlock(color, settings), Blocks.shulkerBoxProperties(color.getMapColor()));
    }
    private static Block registerOldStairsBlock(String id, Block base) {
        return register(id, settings -> new StairBlock(base.defaultBlockState(), settings), BlockBehaviour.Properties.ofLegacyCopy(base));
    }
    public static boolean never(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    private static Block registerBedBlock(String id, DyeColor color) {
        return register(id,
                settings -> new BedBlock(color, settings),
                BlockBehaviour.Properties.of()
                        .mapColor(state -> state.getValue(BedBlock.PART) == BedPart.FOOT
                                ? color.getMapColor()
                                : MapColor.WOOL)
                        .sound(SoundType.WOOD)
                        .strength(0.2F)
                        .bounceRestitution(0.75F)
                        .noOcclusion()
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
        );
    }
	
    public static void registerBlocks() {
        System.out.println("register Blocks");
        HONEY_CAULDRON = register("honey_cauldron", HoneyCauldronBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
        MAGMA_CAULDRON = register("magma_cauldron", MagmaCauldronBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
        SLIME_CAULDRON = register("slime_cauldron", SlimeCauldronBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
        ICE_CAULDRON = register("ice_cauldron", IceCauldronBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
        SOUP_CAULDRON = register("soup_cauldron", SoupCauldronBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));

        FireBlock fireBlock = (FireBlock)Blocks.FIRE;
        fireBlock.setFlammable(BAOBAB_PLANKS, 5, 20);
        fireBlock.setFlammable(BAOBAB_SLAB, 5, 20);
        fireBlock.setFlammable(BAOBAB_FENCE_GATE, 5, 20);
        fireBlock.setFlammable(BAOBAB_FENCE, 5, 20);
        fireBlock.setFlammable(BAOBAB_STAIRS, 5, 20);
        fireBlock.setFlammable(BAOBAB_LOG, 5, 5);
        fireBlock.setFlammable(BAOBAB_WOOD, 5, 5);
        fireBlock.setFlammable(STRIPPED_BAOBAB_LOG, 5, 5);
        fireBlock.setFlammable(STRIPPED_BAOBAB_LOG, 5, 5);

        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
    }
}
