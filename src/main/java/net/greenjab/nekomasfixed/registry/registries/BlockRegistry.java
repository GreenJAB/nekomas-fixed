package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.*;
import net.greenjab.nekomasfixed.registry.block.cauldron.HoneyCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.cauldron.IceCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.cauldron.MagmaCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.cauldron.SlimeCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.enums.ClamType;
import net.greenjab.nekomasfixed.registry.block.enums.NautilusBlockType;
import net.minecraft.block.*;
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

import java.util.function.Function;

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

    public static final Block GLISTERING_MELON = register("glistering_melon", settings -> new MelonBlock(true, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).strength(1F).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block NAUTILUS_BLOCK = register("nautilus_block", settings -> new NautilusBlock(NautilusBlockType.REGULAR, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(1F).sounds(BlockSoundGroup.CORAL).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block ZOMBIE_NAUTILUS_BLOCK = register("zombie_nautilus_block", settings -> new NautilusBlock(NautilusBlockType.ZOMBIE, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(1F).sounds(BlockSoundGroup.CORAL).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block CORAL_NAUTILUS_BLOCK = register("coral_nautilus_block",settings -> new NautilusBlock(NautilusBlockType.CORAL, settings),
            AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(1F).sounds(BlockSoundGroup.CORAL).pistonBehavior(PistonBehavior.DESTROY));

    public static final Block CLOCK = registerVanilla("clock", FloorClockBlock::new,
            AbstractBlock.Settings.create().noCollision().mapColor(MapColor.YELLOW).strength(0.2F).sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.DESTROY));
    public static final Block WALL_CLOCK = registerVanilla(
            "wall_clock", WallClockBlock::new,
            copyLootTable(CLOCK, true).noCollision().mapColor(MapColor.YELLOW).strength(0.2F).sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.DESTROY)
    );

    public static final Block ENDERMAN_HEAD = register("enderman_head", FloorEndermanHeadHead::new,
            AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(1F).sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.DESTROY).instrument(NoteBlockInstrument.CUSTOM_HEAD));
    public static final Block WALL_ENDERMAN_HEAD = register(
            "wall_enderman_head", WallEndermanHeadHead::new,
            copyLootTable(ENDERMAN_HEAD, true).mapColor(MapColor.BLACK).strength(1F).sounds(BlockSoundGroup.METAL).pistonBehavior(PistonBehavior.DESTROY)
    );

    public static final Block AMBER_TERRACOTTA = register("amber_terracotta", AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_YELLOW).instrument(NoteBlockInstrument.BASEDRUM)
            .strength(0.70F).resistance(4.2F).requiresTool());

    public static final Block INDIGO_TERRACOTTA = register("indigo_terracotta", AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_BLUE).instrument(NoteBlockInstrument.BASEDRUM)
            .strength(0.70F).resistance(4.2F).requiresTool());

    public static final Block AQUA_TERRACOTTA = register("aqua_terracotta", AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM)
            .strength(0.70F).resistance(4.2F).requiresTool());

    public static final Block CRIMSON_TERRACOTTA = register("crimson_terracotta", AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_RED).instrument(NoteBlockInstrument.BASEDRUM)
            .strength(0.70F).resistance(4.2F).requiresTool());

    public static final Block AQUA_GLAZED_TERRACOTTA = register("aqua_glazed_terracotta",
            GlazedTerracottaBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.4F)
                    .resistance(4.2F)
                    .requiresTool());

    public static final Block AMBER_GLAZED_TERRACOTTA = register("amber_glazed_terracotta",
            GlazedTerracottaBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.4F)
                    .resistance(4.2F)
                    .requiresTool());

    public static final Block CRIMSON_GLAZED_TERRACOTTA = register("crimson_glazed_terracotta",
            GlazedTerracottaBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_RED)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.4F)
                    .resistance(4.2F)
                    .requiresTool());

    public static final Block INDIGO_GLAZED_TERRACOTTA = register("indigo_glazed_terracotta",
            GlazedTerracottaBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_MAGENTA)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.4F)
                    .resistance(4.2F)
                    .requiresTool());

    public static final Block AMBER_WOOL = register("amber_wool",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable());

    public static final Block AQUA_WOOL = register("aqua_wool",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIGHT_BLUE)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable());

    public static final Block CRIMSON_WOOL = register("crimson_wool",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable());

    public static final Block INDIGO_WOOL = register("indigo_wool",
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.MAGENTA)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable());

    public static final Block AMBER_CONCRETE = register("amber_concrete",
            AbstractBlock.Settings.create()
                    .mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(1.8F));

    public static final Block AQUA_CONCRETE = register("aqua_concrete",
            AbstractBlock.Settings.create()
                    .mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(1.8F));

    public static final Block INDIGO_CONCRETE = register("indigo_concrete",
            AbstractBlock.Settings.create()
                    .mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(1.8F));

    public static final Block CRIMSON_CONCRETE = register("crimson_concrete",
            AbstractBlock.Settings.create()
                    .mapColor(DyeColor.WHITE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(1.8F));

    public static final Block AMBER_CONCRETE_POWDER = register("amber_concrete_powder", (settings) -> new ConcretePowderBlock(AMBER_CONCRETE, settings),
            AbstractBlock.Settings.create().mapColor(DyeColor.YELLOW)
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F).sounds(BlockSoundGroup.SAND));

    public static final Block AQUA_CONCRETE_POWDER = register("aqua_concrete_powder", (settings) -> new ConcretePowderBlock(AQUA_CONCRETE, settings),
            AbstractBlock.Settings.create().mapColor(DyeColor.LIGHT_BLUE)
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F).sounds(BlockSoundGroup.SAND));

    public static final Block CRIMSON_CONCRETE_POWDER = register("crimson_concrete_powder", (settings) -> new ConcretePowderBlock(CRIMSON_CONCRETE, settings),
            AbstractBlock.Settings.create().mapColor(DyeColor.RED)
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F).sounds(BlockSoundGroup.SAND));

    public static final Block INDIGO_CONCRETE_POWDER = register("indigo_concrete_powder", (settings) -> new ConcretePowderBlock(INDIGO_CONCRETE, settings),
            AbstractBlock.Settings.create().mapColor(DyeColor.MAGENTA)
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F).sounds(BlockSoundGroup.SAND));

    public static final Block AMBER_CARPET = register("amber_carpet", (settings) -> new DyedCarpetBlock(DyeColor.YELLOW, settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .strength(0.1F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable());

    public static final Block AQUA_CARPET = register("aqua_carpet", (settings) -> new DyedCarpetBlock(DyeColor.LIGHT_BLUE, settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIGHT_BLUE)
                    .strength(0.1F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable());

    public static final Block CRIMSON_CARPET = register("crimson_carpet", (settings) -> new DyedCarpetBlock(DyeColor.RED, settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .strength(0.1F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable());

    public static final Block INDIGO_CARPET = register("indigo_carpet", (settings) -> new DyedCarpetBlock(DyeColor.MAGENTA, settings),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.MAGENTA)
                    .strength(0.1F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable());

    public static final Block WHITE_FROGLIGHT = register("white_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.WHITE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block LIGHT_GRAY_FROGLIGHT = register("light_gray_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block GRAY_FROGLIGHT = register("gray_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.GRAY).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block BLACK_FROGLIGHT = register("black_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BLACK).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block BROWN_FROGLIGHT = register("brown_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block RED_FROGLIGHT = register("red_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.RED).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block ORANGE_FROGLIGHT = register("orange_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block LIME_FROGLIGHT = register("lime_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIME).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block CYAN_FROGLIGHT = register("cyan_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.CYAN).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block LIGHT_BLUE_FROGLIGHT = register("light_blue_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block BLUE_FROGLIGHT = register("blue_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BLUE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block PURPLE_FROGLIGHT = register("purple_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block PINK_FROGLIGHT = register("pink_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.PINK).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block AMBER_FROGLIGHT = register("amber_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_YELLOW).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block AQUA_FROGLIGHT = register("aqua_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.TEAL).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block CRIMSON_FROGLIGHT = register("crimson_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.DARK_RED).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));
    public static final Block INDIGO_FROGLIGHT = register("indigo_froglight", PillarBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.DARK_DULL_PINK).strength(0.3F).luminance((state) -> 15).sounds(BlockSoundGroup.FROGLIGHT));

    public static final Block AMBER_CANDLE = register("amber_candle", CandleBlock::new, createCandleSettings(MapColor.YELLOW));
    public static final Block AQUA_CANDLE = register("aqua_candle", CandleBlock::new, createCandleSettings(MapColor.TEAL));
    public static final Block CRIMSON_CANDLE = register("crimson_candle", CandleBlock::new, createCandleSettings(MapColor.DARK_CRIMSON));
    public static final Block INDIGO_CANDLE = register("indigo_candle", CandleBlock::new, createCandleSettings(MapColor.PALE_PURPLE));

    public static final Block HONEY_CAULDRON = register(
            "honey_cauldron",
            HoneyCauldronBlock::new,
            AbstractBlock.Settings.copy(Blocks.CAULDRON)
    );

    public static final Block MAGMA_CAULDRON = register(
            "magma_cauldron",
            MagmaCauldronBlock::new,
            AbstractBlock.Settings.copy(Blocks.CAULDRON)
    );

    public static final Block SLIME_CAULDRON = register(
            "slime_cauldron",
            SlimeCauldronBlock::new,
            AbstractBlock.Settings.copy(Blocks.CAULDRON)
    );

    public static final Block ICE_CAULDRON = register(
            "ice_cauldron",
            IceCauldronBlock::new,
            AbstractBlock.Settings.copy(Blocks.CAULDRON)
    );



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

    public static void registerBlocks() {
        System.out.println("register Blocks");
    }
}
