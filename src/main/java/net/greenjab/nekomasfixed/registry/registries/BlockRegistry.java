package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.*;
import net.greenjab.nekomasfixed.registry.block.cauldron.*;
import net.greenjab.nekomasfixed.registry.block.enums.ClamType;
import net.greenjab.nekomasfixed.registry.block.enums.NautilusBlockType;
import net.greenjab.nekomasfixed.registry.worldgen.ModConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.Optional;
import java.util.function.Function;

import static net.minecraft.block.Blocks.createButtonSettings;
import static net.minecraft.block.Blocks.createLeavesSettings;

public class BlockRegistry {

    public static final Block CLAM = register("clam", settings -> new ClamBlock(ClamType.REGULAR, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(1F).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block CLAM_BLUE = register("clam_blue", settings -> new ClamBlock(ClamType.BLUE, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).strength(1F).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block CLAM_PINK = register("clam_pink", settings -> new ClamBlock(ClamType.PINK, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(1F).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block CLAM_PURPLE = register("clam_purple", settings -> new ClamBlock(ClamType.PURPLE, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).strength(1F).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block PEARL_BLOCK = register("pearl_block", AbstractBlock.Settings.create().mapColor(MapColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM)
            .sounds(BlockSoundGroup.CALCITE).requiresTool().strength(0.75F));

    public static final Block KILN = register("kiln", KilnBlock::new,AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).instrument(NoteBlockInstrument.BASEDRUM)
            .sounds(BlockSoundGroup.GILDED_BLACKSTONE).requiresTool().strength(3.5f));

    public static final Block GLOW_TORCH = register(
            "glow_torch",
            GlowTorchBlock::new,
            AbstractBlock.Settings.create()
                    .noCollision()
                    .breakInstantly()
                    .luminance(state -> state.get(Properties.WATERLOGGED) ? 13 : 0)
                    .sounds(BlockSoundGroup.WOOD)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );
    public static final Block GLOW_WALL_TORCH = register(
            "glow_wall_torch",
            WallGlowTorchBlock::new,
            copyLootTable(GLOW_TORCH, true)
                    .noCollision()
                    .breakInstantly()
                    .luminance(state -> state.get(Properties.WATERLOGGED) ? 13 : 0)
                    .sounds(BlockSoundGroup.WOOD)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );

    public static final Block SWEETBERRY_CAKE = register("sweetberry_cake", StackedCakeBlock::new, AbstractBlock.Settings.create().luminance(state -> state.get(StackedCakeBlock.LIT)?3:0));
    public static final Block PAN_CAKE = register("pan_cake", StackedCakeBlock::new, AbstractBlock.Settings.create().luminance(state -> state.get(StackedCakeBlock.LIT)?3:0));
    public static final Block GLOWBERRY_CAKE = register("glowberry_cake", StackedCakeBlock::new, AbstractBlock.Settings.create().luminance(state -> state.get(StackedCakeBlock.LIT)?3:0));
    public static final Block APPLE_CAKE = register("apple_cake", StackedCakeBlock::new, AbstractBlock.Settings.create().luminance(state -> state.get(StackedCakeBlock.LIT)?3:0));
    public static final Block VANILLA_CAKE = register("vanilla_cake", StackedCakeBlock::new, AbstractBlock.Settings.create().luminance(state -> state.get(StackedCakeBlock.LIT)?3:0));
    public static final Block COOKIE_CAKE = register("cookie_cake", StackedCakeBlock::new, AbstractBlock.Settings.create().luminance(state -> state.get(StackedCakeBlock.LIT)?3:0));
    public static final Block CHOCOLATE_CAKE = register("chocolate_cake", StackedCakeBlock::new, AbstractBlock.Settings.create().luminance(state -> state.get(StackedCakeBlock.LIT)?3:0));
    public static final Block BEETROOT_CAKE = register("beetroot_cake", StackedCakeBlock::new, AbstractBlock.Settings.create().luminance(state -> state.get(StackedCakeBlock.LIT)?3:0));

    public static final Block GLISTERING_MELON = register("glistering_melon", settings -> new MelonBlock(true, settings), AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).strength(1F).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block NAUTILUS_BLOCK = register("nautilus_block", settings -> new NautilusBlock(NautilusBlockType.REGULAR, settings), AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(1F).sounds(BlockSoundGroup.CORAL).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block ZOMBIE_NAUTILUS_BLOCK = register("zombie_nautilus_block", settings -> new NautilusBlock(NautilusBlockType.ZOMBIE, settings), AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(1F).sounds(BlockSoundGroup.CORAL).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block CORAL_NAUTILUS_BLOCK = register("coral_nautilus_block",settings -> new NautilusBlock(NautilusBlockType.CORAL, settings), AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(1F).sounds(BlockSoundGroup.CORAL).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block CLOCK = registerVanilla("clock", FloorClockBlock::new, AbstractBlock.Settings.create().noCollision().mapColor(MapColor.YELLOW).strength(0.2F).sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.DESTROY));
    public static final Block WALL_CLOCK = registerVanilla("wall_clock", WallClockBlock::new, copyLootTable(CLOCK, true).noCollision().mapColor(MapColor.YELLOW).strength(0.2F).sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block ENDERMAN_HEAD = register("enderman_head", FloorEndermanHeadHead::new, AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(1F).sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.DESTROY).instrument(NoteBlockInstrument.CUSTOM_HEAD));
    public static final Block WALL_ENDERMAN_HEAD = register("wall_enderman_head", WallEndermanHeadHead::new, copyLootTable(ENDERMAN_HEAD, true).mapColor(MapColor.BLACK).strength(1F).sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block AMBER_TERRACOTTA = register("amber_terracotta", AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_YELLOW).instrument(NoteBlockInstrument.BASEDRUM).strength(0.70F).resistance(4.2F).requiresTool());
    public static final Block INDIGO_TERRACOTTA = register("indigo_terracotta", AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_BLUE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.70F).resistance(4.2F).requiresTool());
    public static final Block AQUA_TERRACOTTA = register("aqua_terracotta", AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.70F).resistance(4.2F).requiresTool());
    public static final Block MAROON_TERRACOTTA = register("maroon_terracotta", AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_RED).instrument(NoteBlockInstrument.BASEDRUM).strength(0.70F).resistance(4.2F).requiresTool());

    public static final Block AQUA_GLAZED_TERRACOTTA = register("aqua_glazed_terracotta", GlazedTerracottaBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.4F).resistance(4.2F).requiresTool());
    public static final Block AMBER_GLAZED_TERRACOTTA = register("amber_glazed_terracotta", GlazedTerracottaBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.4F).resistance(4.2F).requiresTool());
    public static final Block MAROON_GLAZED_TERRACOTTA = register("maroon_glazed_terracotta", GlazedTerracottaBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_RED).instrument(NoteBlockInstrument.BASEDRUM).strength(1.4F).resistance(4.2F).requiresTool());
    public static final Block INDIGO_GLAZED_TERRACOTTA = register("indigo_glazed_terracotta", GlazedTerracottaBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_MAGENTA).instrument(NoteBlockInstrument.BASEDRUM).strength(1.4F).resistance(4.2F).requiresTool());

    public static final Block AMBER_WOOL = register("amber_wool", AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block AQUA_WOOL = register("aqua_wool", AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block MAROON_WOOL = register("maroon_wool", AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block INDIGO_WOOL = register("indigo_wool", AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());

    public static final Block AMBER_CONCRETE = register("amber_concrete", AbstractBlock.Settings.create().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.8F));
    public static final Block AQUA_CONCRETE = register("aqua_concrete", AbstractBlock.Settings.create().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.8F));
    public static final Block INDIGO_CONCRETE = register("indigo_concrete", AbstractBlock.Settings.create().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.8F));
    public static final Block MAROON_CONCRETE = register("maroon_concrete", AbstractBlock.Settings.create().mapColor(DyeColor.WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.8F));

    public static final Block AMBER_CONCRETE_POWDER = register("amber_concrete_powder", (settings) -> new ConcretePowderBlock(AMBER_CONCRETE, settings), AbstractBlock.Settings.create().mapColor(DyeColor.YELLOW).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block AQUA_CONCRETE_POWDER = register("aqua_concrete_powder", (settings) -> new ConcretePowderBlock(AQUA_CONCRETE, settings), AbstractBlock.Settings.create().mapColor(DyeColor.LIGHT_BLUE).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block MAROON_CONCRETE_POWDER = register("maroon_concrete_powder", (settings) -> new ConcretePowderBlock(MAROON_CONCRETE, settings), AbstractBlock.Settings.create().mapColor(DyeColor.RED).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sounds(BlockSoundGroup.SAND));
    public static final Block INDIGO_CONCRETE_POWDER = register("indigo_concrete_powder", (settings) -> new ConcretePowderBlock(INDIGO_CONCRETE, settings), AbstractBlock.Settings.create().mapColor(DyeColor.MAGENTA).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sounds(BlockSoundGroup.SAND));

    public static final Block AMBER_CARPET = register("amber_carpet", (settings) -> new DyedCarpetBlock(DyeColor.YELLOW, settings), AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block AQUA_CARPET = register("aqua_carpet", (settings) -> new DyedCarpetBlock(DyeColor.LIGHT_BLUE, settings),AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block MAROON_CARPET = register("maroon_carpet", (settings) -> new DyedCarpetBlock(DyeColor.RED, settings), AbstractBlock.Settings.create().mapColor(MapColor.RED).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block INDIGO_CARPET = register("indigo_carpet", (settings) -> new DyedCarpetBlock(DyeColor.MAGENTA, settings), AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());

    public static final Block BAOBAB_LOG = register("baobab_log", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_LOG));
    public static final Block BAOBAB_SAPLING = register("baobab_sapling",(settings) -> new SaplingBlock(new SaplingGenerator("nekomasfixed:baobab",  Optional.of(ModConfiguredFeatures.BAOBAB_KEY),Optional.empty(), Optional.empty()),  settings), AbstractBlock.Settings.copy(Blocks.DARK_OAK_SAPLING));

    public static final Block TERMITE_BLOCK = register("termite_block", AbstractBlock.Settings.create().strength(1f));
    public static final Block TERMITE_HIVE = register("termite_hive", TermitehiveBlock::new, AbstractBlock.Settings.create().strength(1f));

    public static final Block CLEAR_FROGLIGHT = register("clear_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.WHITE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block CLOUDY_FROGLIGHT = register("cloudy_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block CASCADING_FROGLIGHT = register("cascading_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GRAY).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block CLOUDBURST_FROGLIGHT = register("cloudburst_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(0.3F).luminance((state) -> 10).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block CHAMOISEE_FROGLIGHT = register("chamoisee_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block SANGUINE_FROGLIGHT = register("sanguine_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.DARK_RED).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block VERMILION_FROGLIGHT = register("vermilion_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.RED).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block MANDARIN_FROGLIGHT = register("mandarin_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block LEMON_FROGLIGHT = register("lemon_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block KIWI_FROGLIGHT = register("kiwi_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIME).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block SEAFOAM_FROGLIGHT = register("seafoam_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TEAL).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block TEAL_FROGLIGHT = register("teal_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.CYAN).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block CERULEAN_FROGLIGHT = register("cerulean_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block NAVY_FROGLIGHT = register("navy_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BLUE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block LAVENDER_FROGLIGHT = register("lavender_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.DARK_DULL_PINK).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block THULIAN_FROGLIGHT = register("thulian_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block SAKURA_FROGLIGHT = register("sakura_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));

    public static final Block AMBER_CANDLE = register("amber_candle", CandleBlock::new, createCandleSettings(MapColor.YELLOW));
    public static final Block AQUA_CANDLE = register("aqua_candle", CandleBlock::new, createCandleSettings(MapColor.TEAL));
    public static final Block MAROON_CANDLE = register("maroon_candle", CandleBlock::new, createCandleSettings(MapColor.DARK_CRIMSON));
    public static final Block INDIGO_CANDLE = register("indigo_candle", CandleBlock::new, createCandleSettings(MapColor.PALE_PURPLE));

    public static Block HONEY_CAULDRON = null;
    public static Block MAGMA_CAULDRON = null;
    public static Block SLIME_CAULDRON = null;
    public static Block ICE_CAULDRON = null;
    public static Block SOUP_CAULDRON = null;

    public static final Block AMBER_BED = registerBedBlock("amber_bed", DyeColor.YELLOW);
    public static final Block AQUA_BED = registerBedBlock("aqua_bed", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_BED = registerBedBlock("indigo_bed", DyeColor.MAGENTA);
    public static final Block MAROON_BED = registerBedBlock("maroon_bed", DyeColor.RED);

    public static final Block AMBER_STAINED_GLASS = registerStainedGlassBlock("amber_stained_glass", DyeColor.YELLOW);
    public static final Block AQUA_STAINED_GLASS = registerStainedGlassBlock("aqua_stained_glass", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_STAINED_GLASS = registerStainedGlassBlock("indigo_stained_glass", DyeColor.MAGENTA);
    public static final Block MAROON_STAINED_GLASS = registerStainedGlassBlock("maroon_stained_glass", DyeColor.RED);

    public static final Block AMBER_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("amber_stained_glass_pane", DyeColor.YELLOW);
    public static final Block AQUA_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("aqua_stained_glass_pane", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("indigo_stained_glass_pane", DyeColor.MAGENTA);
    public static final Block MAROON_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("maroon_stained_glass_pane", DyeColor.RED);

    static BlockSetType BAOBAB_BLOCKSETTYPE = BlockSetType.register(new BlockSetType("baobab"));
    static WoodType BAOBAB_WOODTYPE = WoodType.register(new WoodType("baobab", BAOBAB_BLOCKSETTYPE));

    public static final Block BAOBAB_LEAVES = register("baobab_leaves", settings -> new TintedParticleLeavesBlock(0.01F, settings), createLeavesSettings(BlockSoundGroup.GRASS));
    public static final Block BAOBAB_PLANKS = register("baobab_planks", AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    public static final Block BAOBAB_WOOD = register("baobab_wood", AbstractBlock.Settings.copy(Blocks.OAK_WOOD));
    public static final Block BAOBAB_SIGN = register(
            "baobab_sign",
            settings -> new SignBlock(BAOBAB_WOODTYPE, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()
    );
    public static final Block BAOBAB_WALL_SIGN = register(
            "baobab_wall_sign",
           settings -> new WallSignBlock(BAOBAB_WOODTYPE, settings),
            copyLootTable(BAOBAB_SIGN, true).mapColor(MapColor.OAK_TAN).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()
    );
    public static final Block BAOBAB_HANGING_SIGN = register(
            "baobab_hanging_sign",
            settings -> new HangingSignBlock(BAOBAB_WOODTYPE, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()
    );
    public static final Block BAOBAB_WALL_HANGING_SIGN = register(
            "baobab_wall_hanging_sign",
            settings -> new WallHangingSignBlock(BAOBAB_WOODTYPE, settings),
            copyLootTable(BAOBAB_HANGING_SIGN, true).mapColor(MapColor.OAK_TAN).solid().instrument(NoteBlockInstrument.BASS).noCollision().strength(1.0F).burnable()
    );

    public static final Block BAOBAB_PRESSURE_PLATE = register(
            "baobab_pressure_plate",
            settings -> new PressurePlateBlock(BAOBAB_BLOCKSETTYPE, settings),
            AbstractBlock.Settings.create()
                    .mapColor(BAOBAB_PLANKS.getDefaultMapColor())
                    .solid()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F)
                    .pistonBehavior(PistonBehavior.DESTROY).burnable()
    );
    public static final Block BAOBAB_TRAPDOOR = register(
            "baobab_trapdoor",
            settings -> new TrapdoorBlock(BAOBAB_BLOCKSETTYPE, settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never).burnable()
    );
    public static final Block BAOBAB_BUTTON = register(
            "baobab_button",settings -> new ButtonBlock(BAOBAB_BLOCKSETTYPE, 30, settings), createButtonSettings().burnable()
    );
    public static final Block BAOBAB_STAIRS = registerOldStairsBlock("baobab_stairs", BAOBAB_PLANKS);
    public static final Block BAOBAB_SLAB = register(
            "baobab_slab",
            SlabBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()
    );
    public static final Block BAOBAB_FENCE_GATE = register(
            "baobab_fence_gate",
            settings -> new FenceGateBlock(BAOBAB_WOODTYPE, settings),
            AbstractBlock.Settings.create().mapColor(BAOBAB_PLANKS.getDefaultMapColor()).solid().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).burnable()
    );
    public static final Block BAOBAB_FENCE = register(
            "baobab_fence",
            FenceBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(BAOBAB_PLANKS.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD).burnable()
    );
    public static final Block BAOBAB_DOOR = register(
            "baobab_door",
            settings -> new DoorBlock(BAOBAB_BLOCKSETTYPE, settings),
            AbstractBlock.Settings.create()
                    .mapColor(BAOBAB_PLANKS.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(3.0F)
                    .nonOpaque()
                    .pistonBehavior(PistonBehavior.DESTROY).burnable()
    );

    public static final Block BAOBAB_SHELF = register(
            "baobab_shelf",
            ShelfBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(BAOBAB_PLANKS.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f,3.0F)
                    .sounds(BlockSoundGroup.SHELF).burnable()
    );

    public static final Block WHITE_BRICKS = register("white_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.WHITE));
    public static final Block ORANGE_BRICKS = register("orange_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.ORANGE));
    public static final Block MAGENTA_BRICKS = register("magenta_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_BRICKS = register("light_blue_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_BRICKS = register("yellow_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.YELLOW));
    public static final Block LIME_BRICKS = register("lime_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.LIME));
    public static final Block PINK_BRICKS = register("pink_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.PINK));
    public static final Block GRAY_BRICKS = register("gray_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_BRICKS = register("light_gray_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_BRICKS = register("cyan_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.CYAN));
    public static final Block PURPLE_BRICKS = register("purple_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.PURPLE));
    public static final Block BLUE_BRICKS = register("blue_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.BLUE));
    public static final Block BROWN_BRICKS = register("brown_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.BROWN));
    public static final Block GREEN_BRICKS = register("green_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.GREEN));
    public static final Block RED_BRICKS = register("red_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.RED));
    public static final Block BLACK_BRICKS = register("black_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.BLACK));
    public static final Block AMBER_BRICKS = register("amber_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.YELLOW));
    public static final Block AQUA_BRICKS = register("aqua_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.LIGHT_BLUE));
    public static final Block INDIGO_BRICKS = register("indigo_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.MAGENTA));
    public static final Block MAROON_BRICKS = register("maroon_bricks", AbstractBlock.Settings.copy(Blocks.BRICKS).mapColor(DyeColor.RED));

    public static final Block WHITE_BRICK_SLAB = register("white_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.WHITE));
    public static final Block ORANGE_BRICK_SLAB = register("orange_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.ORANGE));
    public static final Block MAGENTA_BRICK_SLAB = register("magenta_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_BRICK_SLAB = register("light_blue_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_BRICK_SLAB = register("yellow_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.YELLOW));
    public static final Block LIME_BRICK_SLAB = register("lime_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.LIME));
    public static final Block PINK_BRICK_SLAB = register("pink_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.PINK));
    public static final Block GRAY_BRICK_SLAB = register("gray_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_BRICK_SLAB = register("light_gray_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_BRICK_SLAB = register("cyan_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.CYAN));
    public static final Block PURPLE_BRICK_SLAB = register("purple_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.PURPLE));
    public static final Block BLUE_BRICK_SLAB = register("blue_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.BLUE));
    public static final Block BROWN_BRICK_SLAB = register("brown_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.BROWN));
    public static final Block GREEN_BRICK_SLAB = register("green_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.GREEN));
    public static final Block RED_BRICK_SLAB = register("red_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.RED));
    public static final Block BLACK_BRICK_SLAB = register("black_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.BLACK));
    public static final Block AMBER_BRICK_SLAB = register("amber_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.YELLOW));
    public static final Block AQUA_BRICK_SLAB = register("aqua_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.LIGHT_BLUE));
    public static final Block INDIGO_BRICK_SLAB = register("indigo_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.MAGENTA));
    public static final Block MAROON_BRICK_SLAB = register("maroon_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB).mapColor(DyeColor.RED));

    public static final Block WHITE_BRICK_STAIRS = registerOldStairsBlock("white_brick_stairs", WHITE_BRICKS);
    public static final Block ORANGE_BRICK_STAIRS = registerOldStairsBlock("orange_brick_stairs", ORANGE_BRICKS);
    public static final Block MAGENTA_BRICK_STAIRS = registerOldStairsBlock("magenta_brick_stairs", MAGENTA_BRICKS);
    public static final Block LIGHT_BLUE_BRICK_STAIRS = registerOldStairsBlock("light_blue_brick_stairs", LIGHT_BLUE_BRICKS);
    public static final Block YELLOW_BRICK_STAIRS = registerOldStairsBlock("yellow_brick_stairs", YELLOW_BRICKS);
    public static final Block LIME_BRICK_STAIRS = registerOldStairsBlock("lime_brick_stairs", LIME_BRICKS);
    public static final Block PINK_BRICK_STAIRS = registerOldStairsBlock("pink_brick_stairs", PINK_BRICKS);
    public static final Block GRAY_BRICK_STAIRS = registerOldStairsBlock("gray_brick_stairs", GRAY_BRICKS);
    public static final Block LIGHT_GRAY_BRICK_STAIRS = registerOldStairsBlock("light_gray_brick_stairs", LIGHT_GRAY_BRICKS);
    public static final Block CYAN_BRICK_STAIRS = registerOldStairsBlock("cyan_brick_stairs",CYAN_BRICKS);
    public static final Block PURPLE_BRICK_STAIRS = registerOldStairsBlock("purple_brick_stairs", PURPLE_BRICKS);
    public static final Block BLUE_BRICK_STAIRS = registerOldStairsBlock("blue_brick_stairs", BLUE_BRICKS);
    public static final Block BROWN_BRICK_STAIRS = registerOldStairsBlock("brown_brick_stairs", BROWN_BRICKS);
    public static final Block GREEN_BRICK_STAIRS = registerOldStairsBlock("green_brick_stairs",GREEN_BRICKS);
    public static final Block RED_BRICK_STAIRS = registerOldStairsBlock("red_brick_stairs", RED_BRICKS);
    public static final Block BLACK_BRICK_STAIRS = registerOldStairsBlock("black_brick_stairs", BLACK_BRICKS);
    public static final Block AMBER_BRICK_STAIRS = registerOldStairsBlock("amber_brick_stairs", AMBER_BRICKS);
    public static final Block AQUA_BRICK_STAIRS = registerOldStairsBlock("aqua_brick_stairs", AQUA_BRICKS);
    public static final Block INDIGO_BRICK_STAIRS = registerOldStairsBlock("indigo_brick_stairs", INDIGO_BRICKS);
    public static final Block MAROON_BRICK_STAIRS = registerOldStairsBlock("maroon_brick_stairs", MAROON_BRICKS);
    
    public static final Block WHITE_BRICK_WALL = register("white_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(WHITE_BRICKS).solid());
    public static final Block ORANGE_BRICK_WALL = register("orange_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(ORANGE_BRICKS).solid());
    public static final Block MAGENTA_BRICK_WALL = register("magenta_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(MAGENTA_BRICKS).solid());
    public static final Block LIGHT_BLUE_BRICK_WALL = register("light_blue_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(LIGHT_BLUE_BRICKS).solid());
    public static final Block YELLOW_BRICK_WALL = register("yellow_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(YELLOW_BRICKS).solid());
    public static final Block LIME_BRICK_WALL = register("lime_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(LIME_BRICKS).solid());
    public static final Block PINK_BRICK_WALL = register("pink_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(PINK_BRICKS).solid());
    public static final Block GRAY_BRICK_WALL = register("gray_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(GRAY_BRICKS).solid());
    public static final Block LIGHT_GRAY_BRICK_WALL = register("light_gray_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(LIGHT_GRAY_BRICKS).solid());
    public static final Block CYAN_BRICK_WALL = register("cyan_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(CYAN_BRICKS).solid());
    public static final Block PURPLE_BRICK_WALL = register("purple_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(PURPLE_BRICKS).solid());
    public static final Block BLUE_BRICK_WALL = register("blue_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(BLUE_BRICKS).solid());
    public static final Block BROWN_BRICK_WALL = register("brown_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(BROWN_BRICKS).solid());
    public static final Block GREEN_BRICK_WALL = register("green_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(GREEN_BRICKS).solid());
    public static final Block RED_BRICK_WALL = register("red_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(RED_BRICKS).solid());
    public static final Block BLACK_BRICK_WALL = register("black_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(BLACK_BRICKS).solid());
    public static final Block AMBER_BRICK_WALL = register("amber_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(AMBER_BRICKS).solid());
    public static final Block AQUA_BRICK_WALL = register("aqua_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(AQUA_BRICKS).solid());
    public static final Block INDIGO_BRICK_WALL = register("indigo_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(INDIGO_BRICKS).solid());
    public static final Block MAROON_BRICK_WALL = register("maroon_brick_wall", WallBlock::new, AbstractBlock.Settings.copyShallow(MAROON_BRICKS).solid());
    
    public static final Block AMBER_SHULKER_BOX = registerShulkerBoxBlock("amber_shulker_box", DyeColor.YELLOW);
    public static final Block AQUA_SHULKER_BOX = registerShulkerBoxBlock("aqua_shulker_box", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_SHULKER_BOX = registerShulkerBoxBlock("indigo_shulker_box", DyeColor.MAGENTA);
    public static final Block MAROON_SHULKER_BOX = registerShulkerBoxBlock("maroon_shulker_box", DyeColor.RED);


    public static final Block HOLLOW_OAK_LOG = register("hollow_oak_log", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.OAK_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_DARK_OAK_LOG = register("hollow_dark_oak_log", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.DARK_OAK_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_SPRUCE_LOG = register("hollow_spruce_log", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.SPRUCE_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_JUNGLE_LOG = register("hollow_jungle_log", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.JUNGLE_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_WARPED_STEM = register("hollow_warped_stem", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.WARPED_HYPHAE).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_CRIMSON_STEM = register("hollow_crimson_stem", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.CRIMSON_HYPHAE).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_ACACIA_LOG = register("hollow_acacia_log", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.ACACIA_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_CHERRY_LOG = register("hollow_cherry_log", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.CHERRY_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_BIRCH_LOG = register("hollow_birch_log", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.BIRCH_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_PALE_OAK_LOG = register("hollow_pale_oak_log", HollowLogBlock::new , AbstractBlock.Settings.copy(Blocks.PALE_OAK_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));
    public static final Block HOLLOW_BAOBAB_LOG = register("hollow_baobab_log", HollowLogBlock::new , AbstractBlock.Settings.copy(BAOBAB_LOG).luminance(state -> state.get(HollowLogBlock.LIGHT_LEVEL)));

    public static final Block GEYSER = register("geyser", GeyserBlock::new , AbstractBlock.Settings.create().ticksRandomly().strength(0.5f, 0.5f).luminance(state -> Blocks.LAVA.getDefaultState().getLuminance()));

    public static final Block AMBER_SPOTTED_WOOL = register("amber_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block AQUA_SPOTTED_WOOL = register("aqua_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block INDIGO_SPOTTED_WOOL = register("indigo_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block MAROON_SPOTTED_WOOL = register("maroon_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block WHITE_SPOTTED_WOOL = register("white_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.WHITE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block ORANGE_SPOTTED_WOOL = register("orange_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block MAGENTA_SPOTTED_WOOL = register("magenta_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block LIGHT_BLUE_SPOTTED_WOOL = register("light_blue_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block YELLOW_SPOTTED_WOOL = register("yellow_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block LIME_SPOTTED_WOOL = register("lime_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.LIME).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block PINK_SPOTTED_WOOL = register("pink_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.PINK).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block GRAY_SPOTTED_WOOL = register("gray_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block LIGHT_GRAY_SPOTTED_WOOL = register("light_gray_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block CYAN_SPOTTED_WOOL = register("cyan_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.CYAN).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block PURPLE_SPOTTED_WOOL = register("purple_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block BLUE_SPOTTED_WOOL = register("blue_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block BROWN_SPOTTED_WOOL = register("brown_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block GREEN_SPOTTED_WOOL = register("green_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.GREEN).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block RED_SPOTTED_WOOL = register("red_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block BLACK_SPOTTED_WOOL = register("black_spotted_wool", AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable());

    public static final Block AMBER_SPOTTED_CARPET = register("amber_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block AQUA_SPOTTED_CARPET = register("aqua_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block INDIGO_SPOTTED_CARPET = register("indigo_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block MAROON_SPOTTED_CARPET = register("maroon_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block WHITE_SPOTTED_CARPET = register("white_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.WHITE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block ORANGE_SPOTTED_CARPET = register("orange_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block MAGENTA_SPOTTED_CARPET = register("magenta_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block LIGHT_BLUE_SPOTTED_CARPET = register("light_blue_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block YELLOW_SPOTTED_CARPET = register("yellow_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block LIME_SPOTTED_CARPET = register("lime_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIME).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block PINK_SPOTTED_CARPET = register("pink_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.PINK).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block GRAY_SPOTTED_CARPET = register("gray_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block LIGHT_GRAY_SPOTTED_CARPET = register("light_gray_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block CYAN_SPOTTED_CARPET = register("cyan_spotted_carpet", CarpetBlock::new,AbstractBlock.Settings.create().mapColor(MapColor.CYAN).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block PURPLE_SPOTTED_CARPET = register("purple_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block BLUE_SPOTTED_CARPET = register("blue_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block BROWN_SPOTTED_CARPET = register("brown_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block GREEN_SPOTTED_CARPET = register("green_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GREEN).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block RED_SPOTTED_CARPET = register("red_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());
    public static final Block BLACK_SPOTTED_CARPET = register("black_spotted_carpet", CarpetBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(NoteBlockInstrument.GUITAR).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable());

    public static final Block ROPE = register("rope", RopeBlock::new, AbstractBlock.Settings.create().strength(0.2f).solidBlock(BlockRegistry::never).burnable().noCollision());
    public static final Block BAOBAB_FRUIT = register("baobab_fruit", BaobabFruitBlock::new, AbstractBlock.Settings.create().ticksRandomly().strength(0.2f).blockVision(BlockRegistry::never).burnable().breakInstantly());

    public static final Block GOAT_HORN = register("horn", GoatHornBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GRAY).luminance(state -> state.get(GoatHornBlock.TORCH).getLight()).strength(0.2F).sounds(BlockSoundGroup.TUFF).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block PYROTECHNICS_TABLE = register("pyrotechnics_table", PyrotechnicsTableBlock::new, AbstractBlock.Settings.create());

    private static Block register(String id, AbstractBlock.Settings settings) {
        return register(id, Block::new, settings);
    }
    private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    private static Block registerVanilla(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return register(vanillaKeyOf(id), factory, settings);
    }
    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, NekomasFixed.id(id));
    }
    private static RegistryKey<Block> vanillaKeyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(id));
    }
    public static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }

    public static AbstractBlock.Settings createCandleSettings(MapColor mapColor) {
        return AbstractBlock.Settings.create().mapColor(mapColor).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE).luminance(CandleBlock.STATE_TO_LUMINANCE).pistonBehavior(PistonBehavior.DESTROY);
    }
    private static AbstractBlock.Settings copyLootTable(Block block, boolean copyTranslationKey) {
        AbstractBlock.Settings settings = AbstractBlock.Settings.create().lootTable(block.getLootTableKey());
        if (copyTranslationKey) {
            settings = settings.overrideTranslationKey(block.getTranslationKey());
        }

        return settings;
    }

    private static Block registerStainedGlassBlock(String id, DyeColor color) {
        return register(id, (settings) -> new StainedGlassBlock(color, settings), AbstractBlock.Settings.create().mapColor(color).instrument(NoteBlockInstrument.HAT).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));
    }

    private static Block registerStainedGlassPaneBlock(String id, DyeColor color) {
        return register(id, (settings) -> new StainedGlassPaneBlock(color, settings), AbstractBlock.Settings.create().mapColor(color).instrument(NoteBlockInstrument.HAT).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque());
    }
    private static Block registerShulkerBoxBlock(String id, DyeColor color) {
        return register(id, settings -> new ShulkerBoxBlock(color, settings), Blocks.createShulkerBoxSettings(color.getMapColor()));
    }
    private static Block registerOldStairsBlock(String id, Block base) {
        return register(id, settings -> new StairsBlock(base.getDefaultState(), settings), AbstractBlock.Settings.copyShallow(base));
    }
    public static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static Block registerBedBlock(String id, DyeColor color) {
        return register(id,
                settings -> new BedBlock(color, settings),
                AbstractBlock.Settings.create()
                        .mapColor((state) -> state.get(BedBlock.PART) == BedPart.FOOT
                                ? color.getMapColor()
                                : MapColor.WHITE_GRAY)
                        .sounds(BlockSoundGroup.WOOD)
                        .strength(0.2F)
                        .nonOpaque()
                        .burnable()
                        .pistonBehavior(PistonBehavior.DESTROY)
        );
    }
	
    public static void registerBlocks() {
        System.out.println("register Blocks");
        HONEY_CAULDRON = register("honey_cauldron", HoneyCauldronBlock::new, AbstractBlock.Settings.copy(Blocks.CAULDRON));
        MAGMA_CAULDRON = register("magma_cauldron", MagmaCauldronBlock::new, AbstractBlock.Settings.copy(Blocks.CAULDRON));
        SLIME_CAULDRON = register("slime_cauldron", SlimeCauldronBlock::new, AbstractBlock.Settings.copy(Blocks.CAULDRON));
        ICE_CAULDRON = register("ice_cauldron", IceCauldronBlock::new, AbstractBlock.Settings.copy(Blocks.CAULDRON));
        SOUP_CAULDRON = register("soup_cauldron", SoupCauldronBlock::new, AbstractBlock.Settings.copy(Blocks.CAULDRON));
    }
}
