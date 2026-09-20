package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.*;
import net.greenjab.nekomasfixed.registry.block.enums.ClamType;
import net.greenjab.nekomasfixed.registry.worldgen.ModConfiguredFeatures;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Optional;
import java.util.function.Function;

public class BlockRegistry {
    public static final BlockSetType BAOBAB_BLOCKSETTYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(NekomasFixed.id("baobab"));
    public static final WoodType BAOBAB_WOODTYPE = WoodTypeBuilder.copyOf(WoodType.OAK).register(NekomasFixed.id("baobab"), BAOBAB_BLOCKSETTYPE);
    public static final Block GLOW_TORCH = register(
            "glow_torch",
            GlowTorchBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instabreak()
                    .noCollission()
                    .lightLevel(state -> state.getValue(GlowTorchBlock.WATERLOGGED) ? 13 : 0)
                    .sound(SoundType.WOOD)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block GLOW_WALL_TORCH = register(
            "glow_wall_torch",
            WallGlowTorchBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instabreak()
                    .noCollission()
                    .lightLevel(state -> state.getValue(GlowTorchBlock.WATERLOGGED) ? 13 : 0)
                    .sound(SoundType.WOOD)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block GEYSER = register(
            "geyser",
            GeyserBlock::new,
            BlockBehaviour.Properties.of()
                    .randomTicks()
                    .strength(0.5f, 0.5f)
                    .lightLevel(state -> 15)
    );
    public static final Block ENDERMAN_HEAD = register(
            "enderman_head",
            FloorEndermanHeadHead::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(1F)
                    .sound(SoundType.METAL)
                    .pushReaction(PushReaction.DESTROY)
                    .instrument(NoteBlockInstrument.CUSTOM_HEAD)
    );
    public static final Block WALL_ENDERMAN_HEAD = register(
            "wall_enderman_head",
            WallEndermanHeadHead::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(1F)
                    .sound(SoundType.METAL)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block SWEETBERRY_CAKE = register(
            "sweetberry_cake",
            StackedCakeBlock::new,
            cakeProperties()
    );
    public static final Block PAN_CAKE = register(
            "pan_cake",
            StackedCakeBlock::new,
            cakeProperties()
    );
    public static final Block GLOWBERRY_CAKE = register(
            "glowberry_cake",
            StackedCakeBlock::new,
            cakeProperties()
    );
    public static final Block APPLE_CAKE = register(
            "apple_cake",
            StackedCakeBlock::new,
            cakeProperties()
    );
    public static final Block VANILLA_CAKE = register(
            "vanilla_cake",
            StackedCakeBlock::new,
            cakeProperties()
    );
    public static final Block COOKIE_CAKE = register(
            "cookie_cake",
            StackedCakeBlock::new,
            cakeProperties()
    );
    public static final Block CHOCOLATE_CAKE = register(
            "chocolate_cake",
            StackedCakeBlock::new,
            cakeProperties()
    );
    public static final Block BEETROOT_CAKE = register(
            "beetroot_cake",
            StackedCakeBlock::new,
            cakeProperties()
    );
    public static final Block GOAT_HORN = register(
            "horn",
            GoatHornBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .lightLevel(state -> state.getValue(GoatHornBlock.TORCH).getLight())
                    .strength(0.2F)
                    .sound(SoundType.TUFF)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block GLISTERING_MELON = register(
            "glistering_melon",
            settings -> new MelonBlock(true, settings),
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(1F)
                    .sound(SoundType.WOOD)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final Block BAOBAB_LOG = register(
            "baobab_log",
            RotatedPillarBlock::new,
            baobabWoodProperties()
    );
    public static final Block BAOBAB_WOOD = register(
            "baobab_wood",
            RotatedPillarBlock::new,
            baobabWoodProperties()
    );
    public static final Block STRIPPED_BAOBAB_LOG = register(
            "stripped_baobab_log",
            RotatedPillarBlock::new,
            baobabWoodProperties()
    );
    public static final Block STRIPPED_BAOBAB_WOOD = register(
            "stripped_baobab_wood",
            RotatedPillarBlock::new,
            baobabWoodProperties()
    );
    public static final Block BAOBAB_PLANKS = register(
            "baobab_planks",
            baobabWoodProperties()
    );
    public static final Block BAOBAB_STAIRS = register(
            "baobab_stairs",
            settings -> new StairBlock(BAOBAB_PLANKS.defaultBlockState(), settings),
            baobabWoodProperties().strength(2.0F, 3.0F)
    );
    public static final Block BAOBAB_SLAB = register(
            "baobab_slab",
            SlabBlock::new,
            baobabWoodProperties().strength(2.0F, 3.0F)
    );
    public static final Block BAOBAB_FENCE = register(
            "baobab_fence",
            FenceBlock::new,
            baobabWoodProperties().strength(2.0F, 3.0F)
    );
    public static final Block BAOBAB_FENCE_GATE = register(
            "baobab_fence_gate",
            settings -> new FenceGateBlock(BAOBAB_WOODTYPE, settings),
            baobabWoodProperties().forceSolidOn().strength(2.0F, 3.0F)
    );
    public static final Block BAOBAB_DOOR = register(
            "baobab_door",
            settings -> new DoorBlock(BAOBAB_BLOCKSETTYPE, settings),
            baobabWoodProperties().strength(3.0F).noOcclusion().pushReaction(PushReaction.DESTROY)
    );
    public static final Block BAOBAB_TRAPDOOR = register(
            "baobab_trapdoor",
            settings -> new TrapDoorBlock(BAOBAB_BLOCKSETTYPE, settings),
            baobabWoodProperties().strength(3.0F).noOcclusion().isValidSpawn(Blocks::never)
    );
    public static final Block BAOBAB_PRESSURE_PLATE = register(
            "baobab_pressure_plate",
            settings -> new PressurePlateBlock(BAOBAB_BLOCKSETTYPE, settings),
            baobabWoodProperties().noCollission().forceSolidOn().strength(0.5F).pushReaction(PushReaction.DESTROY)
    );
    public static final Block BAOBAB_BUTTON = register(
            "baobab_button",
            settings -> new ButtonBlock(BAOBAB_BLOCKSETTYPE, 30, settings),
            baobabWoodProperties().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)
    );
    public static final Block BAOBAB_SIGN = register(
            "baobab_sign",
            settings -> new StandingSignBlock(BAOBAB_WOODTYPE, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()
    );
    public static final Block BAOBAB_WALL_SIGN = register(
            "baobab_wall_sign",
            settings -> new WallSignBlock(BAOBAB_WOODTYPE, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()
    );
    public static final Block BAOBAB_HANGING_SIGN = register(
            "baobab_hanging_sign",
            settings -> new CeilingHangingSignBlock(BAOBAB_WOODTYPE, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()
    );
    public static final Block BAOBAB_WALL_HANGING_SIGN = register(
            "baobab_wall_hanging_sign",
            settings -> new WallHangingSignBlock(BAOBAB_WOODTYPE, settings),
            BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()
    );
    public static final Block BAOBAB_LEAVES = register(
            "baobab_leaves",
            LeavesBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .isValidSpawn(((blockState, blockGetter, blockPos, object) -> false))
                    .isSuffocating(((blockState, blockGetter, blockPos) -> false))
                    .isViewBlocking(((blockState, blockGetter, blockPos) -> false))
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .isRedstoneConductor(((blockState, blockGetter, blockPos) -> false))
    );
    public static final Block ROPE = register(
            "rope",
            RopeBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(0.2F)
                    .isRedstoneConductor((state, level, pos) -> false)
                    .ignitedByLava()
                    .noCollission()
    );
    public static final Block BAOBAB_FRUIT = register(
            "baobab_fruit",
            BaobabFruitBlock::new,
            BlockBehaviour.Properties.of()
                    .randomTicks()
                    .strength(0.2F)
                    .isViewBlocking((state, level, pos) -> false)
                    .ignitedByLava()
                    .instabreak()
    );
    public static final Block BAOBAB_SAPLING = register(
            "baobab_sapling",
            settings -> new SaplingBlock(
                    new TreeGrower("nekomasfixed:baobab", Optional.of(ModConfiguredFeatures.BAOBAB_KEY), Optional.empty(), Optional.empty()),
                    settings),
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_SAPLING)
    );

    public static final Block CLAM = register(
            "clam",
            settings -> new ClamBlock(ClamType.REGULAR, settings),
            clamBlockProperties()
    );
    public static final Block CLAM_BLUE = register(
            "clam_blue",
            settings -> new ClamBlock(ClamType.BLUE, settings),
            clamBlockProperties()
    );
    public static final Block CLAM_PINK = register(
            "clam_pink",
            settings -> new ClamBlock(ClamType.PINK, settings),
            clamBlockProperties()
    );
    public static final Block CLAM_PURPLE = register(
            "clam_purple",
            settings -> new ClamBlock(ClamType.PURPLE, settings),
            clamBlockProperties()
    );
    public static final Block PEARL_BLOCK = register(
            "pearl_block",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .strength(0.5F)
                    .sound(SoundType.STONE)
    );
    public static final Block CLEAR_FROGLIGHT = register("clear_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.SNOW, 15));
    public static final Block CLOUDY_FROGLIGHT = register("cloudy_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_LIGHT_GRAY, 15));
    public static final Block CASCADING_FROGLIGHT = register("cascading_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_GRAY, 15));
    public static final Block CLOUDBURST_FROGLIGHT = register("cloudburst_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_BLACK, 10));
    public static final Block CHAMOISEE_FROGLIGHT = register("chamoisee_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_BROWN, 15));
    public static final Block SANGUINE_FROGLIGHT = register("sanguine_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.NETHER, 15));
    public static final Block VERMILION_FROGLIGHT = register("vermilion_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_RED, 15));
    public static final Block MANDARIN_FROGLIGHT = register("mandarin_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_ORANGE, 15));
    public static final Block LEMON_FROGLIGHT = register("lemon_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_YELLOW, 15));
    public static final Block KIWI_FROGLIGHT = register("kiwi_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_LIGHT_GREEN, 15));
    public static final Block SEAFOAM_FROGLIGHT = register("seafoam_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.WARPED_NYLIUM, 15));
    public static final Block TEAL_FROGLIGHT = register("teal_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_CYAN, 15));
    public static final Block CERULEAN_FROGLIGHT = register("cerulean_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_LIGHT_BLUE, 15));
    public static final Block NAVY_FROGLIGHT = register("navy_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_BLUE, 15));
    public static final Block LAVENDER_FROGLIGHT = register("lavender_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.WARPED_HYPHAE, 15));
    public static final Block THULIAN_FROGLIGHT = register("thulian_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_MAGENTA, 15));
    public static final Block SAKURA_FROGLIGHT = register("sakura_froglight", RotatedPillarBlock::new, froglightProperties(MapColor.COLOR_PINK, 15));
    public static final Block WHITE_SPOTTED_WOOL = register("white_spotted_wool", Block::new, spottedWoolProperties(MapColor.SNOW));
    public static final Block LIGHT_GRAY_SPOTTED_WOOL = register("light_gray_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_LIGHT_GRAY));
    public static final Block GRAY_SPOTTED_WOOL = register("gray_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_GRAY));
    public static final Block BLACK_SPOTTED_WOOL = register("black_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_BLACK));
    public static final Block BROWN_SPOTTED_WOOL = register("brown_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_BROWN));
    public static final Block RED_SPOTTED_WOOL = register("red_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_RED));
    public static final Block ORANGE_SPOTTED_WOOL = register("orange_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_ORANGE));
    public static final Block YELLOW_SPOTTED_WOOL = register("yellow_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_YELLOW));
    public static final Block LIME_SPOTTED_WOOL = register("lime_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_LIGHT_GREEN));
    public static final Block GREEN_SPOTTED_WOOL = register("green_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_GREEN));
    public static final Block CYAN_SPOTTED_WOOL = register("cyan_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_CYAN));
    public static final Block LIGHT_BLUE_SPOTTED_WOOL = register("light_blue_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block BLUE_SPOTTED_WOOL = register("blue_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_BLUE));
    public static final Block PURPLE_SPOTTED_WOOL = register("purple_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_PURPLE));
    public static final Block MAGENTA_SPOTTED_WOOL = register("magenta_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_MAGENTA));
    public static final Block PINK_SPOTTED_WOOL = register("pink_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_PINK));
    public static final Block AMBER_SPOTTED_WOOL = register("amber_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_YELLOW));
    public static final Block AQUA_SPOTTED_WOOL = register("aqua_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block INDIGO_SPOTTED_WOOL = register("indigo_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_MAGENTA));
    public static final Block MAROON_SPOTTED_WOOL = register("maroon_spotted_wool", Block::new, spottedWoolProperties(MapColor.COLOR_RED));
    public static final Block WHITE_SPOTTED_CARPET = register("white_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.SNOW));
    public static final Block LIGHT_GRAY_SPOTTED_CARPET = register("light_gray_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_LIGHT_GRAY));
    public static final Block GRAY_SPOTTED_CARPET = register("gray_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_GRAY));
    public static final Block BLACK_SPOTTED_CARPET = register("black_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_BLACK));
    public static final Block BROWN_SPOTTED_CARPET = register("brown_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_BROWN));
    public static final Block RED_SPOTTED_CARPET = register("red_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_RED));
    public static final Block ORANGE_SPOTTED_CARPET = register("orange_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_ORANGE));
    public static final Block YELLOW_SPOTTED_CARPET = register("yellow_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_YELLOW));
    public static final Block LIME_SPOTTED_CARPET = register("lime_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_LIGHT_GREEN));
    public static final Block GREEN_SPOTTED_CARPET = register("green_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_GREEN));
    public static final Block CYAN_SPOTTED_CARPET = register("cyan_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_CYAN));
    public static final Block LIGHT_BLUE_SPOTTED_CARPET = register("light_blue_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block BLUE_SPOTTED_CARPET = register("blue_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_BLUE));
    public static final Block PURPLE_SPOTTED_CARPET = register("purple_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_PURPLE));
    public static final Block MAGENTA_SPOTTED_CARPET = register("magenta_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_MAGENTA));
    public static final Block PINK_SPOTTED_CARPET = register("pink_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_PINK));
    public static final Block AMBER_SPOTTED_CARPET = register("amber_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_YELLOW));
    public static final Block AQUA_SPOTTED_CARPET = register("aqua_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block INDIGO_SPOTTED_CARPET = register("indigo_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_MAGENTA));
    public static final Block MAROON_SPOTTED_CARPET = register("maroon_spotted_carpet", CarpetBlock::new, spottedCarpetProperties(MapColor.COLOR_RED));
    public static final Block AMBER_WOOL = register("amber_wool", Block::new, woolProperties(MapColor.COLOR_YELLOW));
    public static final Block AQUA_WOOL = register("aqua_wool", Block::new, woolProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block INDIGO_WOOL = register("indigo_wool", Block::new, woolProperties(MapColor.COLOR_MAGENTA));
    public static final Block MAROON_WOOL = register("maroon_wool", Block::new, woolProperties(MapColor.COLOR_RED));
    public static final Block AMBER_CARPET = register("amber_carpet", CarpetBlock::new, carpetProperties(MapColor.COLOR_YELLOW));
    public static final Block AQUA_CARPET = register("aqua_carpet", CarpetBlock::new, carpetProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block INDIGO_CARPET = register("indigo_carpet", CarpetBlock::new, carpetProperties(MapColor.COLOR_MAGENTA));
    public static final Block MAROON_CARPET = register("maroon_carpet", CarpetBlock::new, carpetProperties(MapColor.COLOR_RED));

    // Ancient-colour stained glass + panes. Representational DyeColor (as in main):
    // the block class takes a vanilla DyeColor; the custom texture gives the colour.
    public static final Block AMBER_STAINED_GLASS = registerStainedGlassBlock("amber_stained_glass", DyeColor.YELLOW);
    public static final Block AQUA_STAINED_GLASS = registerStainedGlassBlock("aqua_stained_glass", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_STAINED_GLASS = registerStainedGlassBlock("indigo_stained_glass", DyeColor.MAGENTA);
    public static final Block MAROON_STAINED_GLASS = registerStainedGlassBlock("maroon_stained_glass", DyeColor.RED);
    public static final Block AMBER_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("amber_stained_glass_pane", DyeColor.YELLOW);
    public static final Block AQUA_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("aqua_stained_glass_pane", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("indigo_stained_glass_pane", DyeColor.MAGENTA);
    public static final Block MAROON_STAINED_GLASS_PANE = registerStainedGlassPaneBlock("maroon_stained_glass_pane", DyeColor.RED);

    // Ancient-colour candles: vanilla CandleBlock (no block entity), vanilla candle settings.
    public static final Block AMBER_CANDLE = register("amber_candle", CandleBlock::new, createCandleSettings(MapColor.COLOR_YELLOW));
    public static final Block AQUA_CANDLE = register("aqua_candle", CandleBlock::new, createCandleSettings(MapColor.WARPED_NYLIUM));
    public static final Block INDIGO_CANDLE = register("indigo_candle", CandleBlock::new, createCandleSettings(MapColor.ICE));
    public static final Block MAROON_CANDLE = register("maroon_candle", CandleBlock::new, createCandleSettings(MapColor.CRIMSON_HYPHAE));

    // Ancient-colour beds: vanilla BedBlock with a representational DyeColor. Rendered by the
    // vanilla BedRenderer keyed off that DyeColor (so amber renders as yellow in-world), and the
    // block must be attached to BlockEntityType.BED for the block-entity lookup (see BlockEntityRegistry).
    public static final Block AMBER_BED = registerBedBlock("amber_bed", DyeColor.YELLOW);
    public static final Block AQUA_BED = registerBedBlock("aqua_bed", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_BED = registerBedBlock("indigo_bed", DyeColor.MAGENTA);
    public static final Block MAROON_BED = registerBedBlock("maroon_bed", DyeColor.RED);

    // Ancient-colour shulker boxes: vanilla ShulkerBoxBlock with a representational DyeColor +
    // vanilla shulker properties. Rendered by the vanilla ShulkerBoxRenderer, which we override
    // (client ShulkerBoxRendererMixin) to swap in the amber/aqua/indigo/maroon texture. The block
    // must be attached to BlockEntityType.SHULKER_BOX for placement/storage (see BlockEntityRegistry).
    public static final Block AMBER_SHULKER_BOX = registerShulkerBoxBlock("amber_shulker_box", DyeColor.YELLOW);
    public static final Block AQUA_SHULKER_BOX = registerShulkerBoxBlock("aqua_shulker_box", DyeColor.LIGHT_BLUE);
    public static final Block INDIGO_SHULKER_BOX = registerShulkerBoxBlock("indigo_shulker_box", DyeColor.MAGENTA);
    public static final Block MAROON_SHULKER_BOX = registerShulkerBoxBlock("maroon_shulker_box", DyeColor.RED);

    // Ancient-dye terracotta + concrete + concrete powder. Terracotta/concrete are plain cubes
    // (terracotta + glazed need a pickaxe; concrete powder is a FallingBlock that hardens on
    // contact and converts to the concrete block). MapColors mirror main (baked terracotta uses
    // the TERRACOTTA_* tones; concrete is monochrome white on the map; powder uses the dye tones).
    public static final Block AMBER_TERRACOTTA = register("amber_terracotta", Block::new, terracottaProperties(MapColor.TERRACOTTA_YELLOW));
    public static final Block AQUA_TERRACOTTA = register("aqua_terracotta", Block::new, terracottaProperties(MapColor.TERRACOTTA_LIGHT_BLUE));
    public static final Block INDIGO_TERRACOTTA = register("indigo_terracotta", Block::new, terracottaProperties(MapColor.TERRACOTTA_BLUE));
    public static final Block MAROON_TERRACOTTA = register("maroon_terracotta", Block::new, terracottaProperties(MapColor.TERRACOTTA_RED));

    public static final Block AMBER_CONCRETE = register("amber_concrete", Block::new, concreteProperties());
    public static final Block AQUA_CONCRETE = register("aqua_concrete", Block::new, concreteProperties());
    public static final Block INDIGO_CONCRETE = register("indigo_concrete", Block::new, concreteProperties());
    public static final Block MAROON_CONCRETE = register("maroon_concrete", Block::new, concreteProperties());

    public static final Block AMBER_CONCRETE_POWDER = register("amber_concrete_powder",
            settings -> new ConcretePowderBlock(AMBER_CONCRETE, settings), concretePowderProperties(MapColor.COLOR_YELLOW));
    public static final Block AQUA_CONCRETE_POWDER = register("aqua_concrete_powder",
            settings -> new ConcretePowderBlock(AQUA_CONCRETE, settings), concretePowderProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block INDIGO_CONCRETE_POWDER = register("indigo_concrete_powder",
            settings -> new ConcretePowderBlock(INDIGO_CONCRETE, settings), concretePowderProperties(MapColor.COLOR_MAGENTA));
    public static final Block MAROON_CONCRETE_POWDER = register("maroon_concrete_powder",
            settings -> new ConcretePowderBlock(MAROON_CONCRETE, settings), concretePowderProperties(MapColor.COLOR_RED));

    // Ancient-dye glazed terracotta: vanilla GlazedTerracottaBlock (directional FACING state).
    // Renders via the vanilla glazed terracotta pattern with the {colour} texture; the soft
    // terracotta is smelted to glaze it (see ModRecipeProvider).
    public static final Block AMBER_GLAZED_TERRACOTTA = register("amber_glazed_terracotta",
            GlazedTerracottaBlock::new, glazedTerracottaProperties(MapColor.TERRACOTTA_WHITE));
    public static final Block AQUA_GLAZED_TERRACOTTA = register("aqua_glazed_terracotta",
            GlazedTerracottaBlock::new, glazedTerracottaProperties(MapColor.TERRACOTTA_LIGHT_BLUE));
    public static final Block INDIGO_GLAZED_TERRACOTTA = register("indigo_glazed_terracotta",
            GlazedTerracottaBlock::new, glazedTerracottaProperties(MapColor.TERRACOTTA_MAGENTA));
    public static final Block MAROON_GLAZED_TERRACOTTA = register("maroon_glazed_terracotta",
            GlazedTerracottaBlock::new, glazedTerracottaProperties(MapColor.TERRACOTTA_RED));

    public static final Block WHITE_BRICKS = register("white_bricks", brickProperties(MapColor.SNOW));
    public static final Block ORANGE_BRICKS = register("orange_bricks", brickProperties(MapColor.COLOR_ORANGE));
    public static final Block MAGENTA_BRICKS = register("magenta_bricks", brickProperties(MapColor.COLOR_MAGENTA));
    public static final Block LIGHT_BLUE_BRICKS = register("light_blue_bricks", brickProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block YELLOW_BRICKS = register("yellow_bricks", brickProperties(MapColor.COLOR_YELLOW));
    public static final Block LIME_BRICKS = register("lime_bricks", brickProperties(MapColor.COLOR_LIGHT_GREEN));
    public static final Block PINK_BRICKS = register("pink_bricks", brickProperties(MapColor.COLOR_PINK));
    public static final Block GRAY_BRICKS = register("gray_bricks", brickProperties(MapColor.COLOR_GRAY));
    public static final Block LIGHT_GRAY_BRICKS = register("light_gray_bricks", brickProperties(MapColor.COLOR_LIGHT_GRAY));
    public static final Block CYAN_BRICKS = register("cyan_bricks", brickProperties(MapColor.COLOR_CYAN));
    public static final Block PURPLE_BRICKS = register("purple_bricks", brickProperties(MapColor.COLOR_PURPLE));
    public static final Block BLUE_BRICKS = register("blue_bricks", brickProperties(MapColor.COLOR_BLUE));
    public static final Block BROWN_BRICKS = register("brown_bricks", brickProperties(MapColor.COLOR_BROWN));
    public static final Block GREEN_BRICKS = register("green_bricks", brickProperties(MapColor.COLOR_GREEN));
    public static final Block RED_BRICKS = register("red_bricks", brickProperties(MapColor.COLOR_RED));
    public static final Block BLACK_BRICKS = register("black_bricks", brickProperties(MapColor.COLOR_BLACK));
    public static final Block AMBER_BRICKS = register("amber_bricks", brickProperties(MapColor.COLOR_YELLOW));
    public static final Block AQUA_BRICKS = register("aqua_bricks", brickProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block INDIGO_BRICKS = register("indigo_bricks", brickProperties(MapColor.COLOR_MAGENTA));
    public static final Block MAROON_BRICKS = register("maroon_bricks", brickProperties(MapColor.COLOR_RED));
    public static final Block WHITE_BRICK_SLAB = register("white_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.SNOW));
    public static final Block ORANGE_BRICK_SLAB = register("orange_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_ORANGE));
    public static final Block MAGENTA_BRICK_SLAB = register("magenta_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_MAGENTA));
    public static final Block LIGHT_BLUE_BRICK_SLAB = register("light_blue_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block YELLOW_BRICK_SLAB = register("yellow_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_YELLOW));
    public static final Block LIME_BRICK_SLAB = register("lime_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_LIGHT_GREEN));
    public static final Block PINK_BRICK_SLAB = register("pink_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_PINK));
    public static final Block GRAY_BRICK_SLAB = register("gray_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_GRAY));
    public static final Block LIGHT_GRAY_BRICK_SLAB = register("light_gray_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_LIGHT_GRAY));
    public static final Block CYAN_BRICK_SLAB = register("cyan_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_CYAN));
    public static final Block PURPLE_BRICK_SLAB = register("purple_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_PURPLE));
    public static final Block BLUE_BRICK_SLAB = register("blue_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_BLUE));
    public static final Block BROWN_BRICK_SLAB = register("brown_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_BROWN));
    public static final Block GREEN_BRICK_SLAB = register("green_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_GREEN));
    public static final Block RED_BRICK_SLAB = register("red_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_RED));
    public static final Block BLACK_BRICK_SLAB = register("black_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_BLACK));
    public static final Block AMBER_BRICK_SLAB = register("amber_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_YELLOW));
    public static final Block AQUA_BRICK_SLAB = register("aqua_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_LIGHT_BLUE));
    public static final Block INDIGO_BRICK_SLAB = register("indigo_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_MAGENTA));
    public static final Block MAROON_BRICK_SLAB = register("maroon_brick_slab", SlabBlock::new, brickSlabProperties(MapColor.COLOR_RED));
    public static final Block WHITE_BRICK_STAIRS = registerBrickStairs("white_brick_stairs", WHITE_BRICKS);
    public static final Block ORANGE_BRICK_STAIRS = registerBrickStairs("orange_brick_stairs", ORANGE_BRICKS);
    public static final Block MAGENTA_BRICK_STAIRS = registerBrickStairs("magenta_brick_stairs", MAGENTA_BRICKS);
    public static final Block LIGHT_BLUE_BRICK_STAIRS = registerBrickStairs("light_blue_brick_stairs", LIGHT_BLUE_BRICKS);
    public static final Block YELLOW_BRICK_STAIRS = registerBrickStairs("yellow_brick_stairs", YELLOW_BRICKS);
    public static final Block LIME_BRICK_STAIRS = registerBrickStairs("lime_brick_stairs", LIME_BRICKS);
    public static final Block PINK_BRICK_STAIRS = registerBrickStairs("pink_brick_stairs", PINK_BRICKS);
    public static final Block GRAY_BRICK_STAIRS = registerBrickStairs("gray_brick_stairs", GRAY_BRICKS);
    public static final Block LIGHT_GRAY_BRICK_STAIRS = registerBrickStairs("light_gray_brick_stairs", LIGHT_GRAY_BRICKS);
    public static final Block CYAN_BRICK_STAIRS = registerBrickStairs("cyan_brick_stairs", CYAN_BRICKS);
    public static final Block PURPLE_BRICK_STAIRS = registerBrickStairs("purple_brick_stairs", PURPLE_BRICKS);
    public static final Block BLUE_BRICK_STAIRS = registerBrickStairs("blue_brick_stairs", BLUE_BRICKS);
    public static final Block BROWN_BRICK_STAIRS = registerBrickStairs("brown_brick_stairs", BROWN_BRICKS);
    public static final Block GREEN_BRICK_STAIRS = registerBrickStairs("green_brick_stairs", GREEN_BRICKS);
    public static final Block RED_BRICK_STAIRS = registerBrickStairs("red_brick_stairs", RED_BRICKS);
    public static final Block BLACK_BRICK_STAIRS = registerBrickStairs("black_brick_stairs", BLACK_BRICKS);
    public static final Block AMBER_BRICK_STAIRS = registerBrickStairs("amber_brick_stairs", AMBER_BRICKS);
    public static final Block AQUA_BRICK_STAIRS = registerBrickStairs("aqua_brick_stairs", AQUA_BRICKS);
    public static final Block INDIGO_BRICK_STAIRS = registerBrickStairs("indigo_brick_stairs", INDIGO_BRICKS);
    public static final Block MAROON_BRICK_STAIRS = registerBrickStairs("maroon_brick_stairs", MAROON_BRICKS);
    public static final Block WHITE_BRICK_WALL = register("white_brick_wall", WallBlock::new, brickWallProperties(WHITE_BRICKS));
    public static final Block ORANGE_BRICK_WALL = register("orange_brick_wall", WallBlock::new, brickWallProperties(ORANGE_BRICKS));
    public static final Block MAGENTA_BRICK_WALL = register("magenta_brick_wall", WallBlock::new, brickWallProperties(MAGENTA_BRICKS));
    public static final Block LIGHT_BLUE_BRICK_WALL = register("light_blue_brick_wall", WallBlock::new, brickWallProperties(LIGHT_BLUE_BRICKS));
    public static final Block YELLOW_BRICK_WALL = register("yellow_brick_wall", WallBlock::new, brickWallProperties(YELLOW_BRICKS));
    public static final Block LIME_BRICK_WALL = register("lime_brick_wall", WallBlock::new, brickWallProperties(LIME_BRICKS));
    public static final Block PINK_BRICK_WALL = register("pink_brick_wall", WallBlock::new, brickWallProperties(PINK_BRICKS));
    public static final Block GRAY_BRICK_WALL = register("gray_brick_wall", WallBlock::new, brickWallProperties(GRAY_BRICKS));
    public static final Block LIGHT_GRAY_BRICK_WALL = register("light_gray_brick_wall", WallBlock::new, brickWallProperties(LIGHT_GRAY_BRICKS));
    public static final Block CYAN_BRICK_WALL = register("cyan_brick_wall", WallBlock::new, brickWallProperties(CYAN_BRICKS));
    public static final Block PURPLE_BRICK_WALL = register("purple_brick_wall", WallBlock::new, brickWallProperties(PURPLE_BRICKS));
    public static final Block BLUE_BRICK_WALL = register("blue_brick_wall", WallBlock::new, brickWallProperties(BLUE_BRICKS));
    public static final Block BROWN_BRICK_WALL = register("brown_brick_wall", WallBlock::new, brickWallProperties(BROWN_BRICKS));
    public static final Block GREEN_BRICK_WALL = register("green_brick_wall", WallBlock::new, brickWallProperties(GREEN_BRICKS));
    public static final Block RED_BRICK_WALL = register("red_brick_wall", WallBlock::new, brickWallProperties(RED_BRICKS));
    public static final Block BLACK_BRICK_WALL = register("black_brick_wall", WallBlock::new, brickWallProperties(BLACK_BRICKS));
    public static final Block AMBER_BRICK_WALL = register("amber_brick_wall", WallBlock::new, brickWallProperties(AMBER_BRICKS));
    public static final Block AQUA_BRICK_WALL = register("aqua_brick_wall", WallBlock::new, brickWallProperties(AQUA_BRICKS));
    public static final Block INDIGO_BRICK_WALL = register("indigo_brick_wall", WallBlock::new, brickWallProperties(INDIGO_BRICKS));
    public static final Block MAROON_BRICK_WALL = register("maroon_brick_wall", WallBlock::new, brickWallProperties(MAROON_BRICKS));
    public static final Block HOLLOW_OAK_LOG = register("hollow_oak_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.OAK_LOG));
    public static final Block HOLLOW_SPRUCE_LOG = register("hollow_spruce_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.SPRUCE_LOG));
    public static final Block HOLLOW_BIRCH_LOG = register("hollow_birch_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.BIRCH_LOG));
    public static final Block HOLLOW_JUNGLE_LOG = register("hollow_jungle_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.JUNGLE_LOG));
    public static final Block HOLLOW_ACACIA_LOG = register("hollow_acacia_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.ACACIA_LOG));
    public static final Block HOLLOW_DARK_OAK_LOG = register("hollow_dark_oak_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.DARK_OAK_LOG));
    public static final Block HOLLOW_MANGROVE_LOG = register("hollow_mangrove_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.MANGROVE_LOG));
    public static final Block HOLLOW_CHERRY_LOG = register("hollow_cherry_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.CHERRY_LOG));
    public static final Block HOLLOW_BAMBOO_BLOCK = register("hollow_bamboo_block", HollowLogBlock::new,
            hollowLogProperties(Blocks.BAMBOO_BLOCK));
    public static final Block HOLLOW_CRIMSON_STEM = register("hollow_crimson_stem", HollowLogBlock::new,
            hollowLogProperties(Blocks.CRIMSON_HYPHAE));
    public static final Block HOLLOW_WARPED_STEM = register("hollow_warped_stem", HollowLogBlock::new,
            hollowLogProperties(Blocks.WARPED_HYPHAE));
    public static final Block HOLLOW_BAOBAB_LOG = register("hollow_baobab_log", HollowLogBlock::new,
            hollowLogProperties(BAOBAB_LOG));
    public static final Block HOLLOW_STRIPPED_OAK_LOG = register("stripped_oak_hollow_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_OAK_LOG));
    public static final Block HOLLOW_STRIPPED_SPRUCE_LOG = register("stripped_spruce_hollow_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_SPRUCE_LOG));
    public static final Block HOLLOW_STRIPPED_BIRCH_LOG = register("stripped_birch_hollow_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_BIRCH_LOG));
    public static final Block HOLLOW_STRIPPED_JUNGLE_LOG = register("stripped_jungle_hollow_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_JUNGLE_LOG));
    public static final Block HOLLOW_STRIPPED_ACACIA_LOG = register("stripped_acacia_hollow_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_ACACIA_LOG));
    public static final Block HOLLOW_STRIPPED_DARK_OAK_LOG = register("stripped_dark_oak_hollow_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_DARK_OAK_LOG));
    public static final Block HOLLOW_STRIPPED_MANGROVE_LOG = register("stripped_mangrove_hollow_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_MANGROVE_LOG));
    public static final Block HOLLOW_STRIPPED_CHERRY_LOG = register("stripped_cherry_hollow_log", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_CHERRY_LOG));
    public static final Block HOLLOW_STRIPPED_BAMBOO_BLOCK = register("stripped_bamboo_hollow_block", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_BAMBOO_BLOCK));
    public static final Block HOLLOW_STRIPPED_CRIMSON_STEM = register("stripped_crimson_hollow_stem", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_CRIMSON_STEM));
    public static final Block HOLLOW_STRIPPED_WARPED_STEM = register("stripped_warped_hollow_stem", HollowLogBlock::new,
            hollowLogProperties(Blocks.STRIPPED_WARPED_STEM));
    public static final Block HOLLOW_STRIPPED_BAOBAB_LOG = register("stripped_baobab_hollow_log", HollowLogBlock::new,
            hollowLogProperties(STRIPPED_BAOBAB_LOG));

    // The clock replaces vanilla minecraft:clock - registered under the vanilla namespace so the
    // same id also places from the (ItemsMixin-replaced) clock item. Wall clock drops via the floor
    // clock's loot table (dropsLike) so breaking it yields the minecraft:clock block item.
    public static final Block CLOCK = registerVanilla("clock", FloorClockBlock::new,
            BlockBehaviour.Properties.of()
                    .noCollission()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(0.2F).sound(SoundType.METAL)
                    .pushReaction(PushReaction.DESTROY));
    public static final Block WALL_CLOCK = registerVanilla("wall_clock", WallClockBlock::new,
            BlockBehaviour.Properties.of()
                    .noCollission()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(0.2F).sound(SoundType.METAL)
                    .pushReaction(PushReaction.DESTROY));

    // Build properties copied from the base log, with the emitted light driven by the
    // hollow log's LIGHT_LEVEL state property. Non-occluding (1.21.1 culls
    // neighbor faces by opaque-cube flag, unlike 26.x shape-based culling) so a
    // block behind the hollow opening stays visible.
    private static BlockBehaviour.Properties hollowLogProperties(Block baseLog) {
        return BlockBehaviour.Properties.ofFullCopy(baseLog)
                .lightLevel(state -> state.getValue(HollowLogBlock.LIGHT_LEVEL))
                .noOcclusion();
    }

    private static BlockBehaviour.Properties clamBlockProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .strength(0.5F)
                .sound(SoundType.STONE)
                .noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false);
    }

    private static BlockBehaviour.Properties baobabWoodProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.BASS)
                .sound(SoundType.WOOD)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties cakeProperties() {
        return BlockBehaviour.Properties.of()
                .strength(0.5F)
                .sound(SoundType.WOOL)
                .lightLevel(state -> state.getValue(StackedCakeBlock.LIT) ? 3 : 0);
    }

    private static BlockBehaviour.Properties froglightProperties(MapColor colour, int lightLevel) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .strength(0.3F)
                .lightLevel(state -> lightLevel)
                .sound(SoundType.FROGLIGHT);
    }

    private static BlockBehaviour.Properties spottedWoolProperties(MapColor colour) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOL)
                .ignitedByLava();
    }

    // Plain ancient-colour wool; note-block instrument matches vanilla wool (GUITAR).
    private static BlockBehaviour.Properties woolProperties(MapColor colour) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOL)
                .ignitedByLava();
    }

    // Plain ancient-colour carpet; no instrument() so it defaults to HARP (vanilla carpet).
    private static BlockBehaviour.Properties carpetProperties(MapColor colour) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .strength(0.1F)
                .sound(SoundType.WOOL)
                .ignitedByLava();
    }

    // Ancient-colour stained glass; transparent block (no occlusion/conduction/blocking),
    // same light-blocking profile as vanilla stained glass.
    private static Block registerStainedGlassBlock(String id, DyeColor colour) {
        return register(id, settings -> new StainedGlassBlock(colour, settings),
                BlockBehaviour.Properties.of()
                        .mapColor(colour)
                        .instrument(NoteBlockInstrument.HAT)
                        .strength(0.3F)
                        .sound(SoundType.GLASS)
                        .noOcclusion()
                        .isValidSpawn(Blocks::never)
                        .isRedstoneConductor(Blocks::never)
                        .isSuffocating(Blocks::never)
                        .isViewBlocking(Blocks::never));
    }

    // Ancient-colour stained glass pane.
    private static Block registerStainedGlassPaneBlock(String id, DyeColor colour) {
        return register(id, settings -> new StainedGlassPaneBlock(colour, settings),
                BlockBehaviour.Properties.of()
                        .mapColor(colour)
                        .instrument(NoteBlockInstrument.HAT)
                        .strength(0.3F)
                        .sound(SoundType.GLASS)
                        .noOcclusion());
    }

    // Vanilla candle settings (LightEmission from CandleBlock), candle sound + breakable.
    private static BlockBehaviour.Properties createCandleSettings(MapColor colour) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .noOcclusion()
                .strength(0.1F)
                .sound(SoundType.CANDLE)
                .lightLevel(CandleBlock.LIGHT_EMISSION)
                .pushReaction(PushReaction.DESTROY);
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
                        .jumpFactor(0.75F)
                        .noOcclusion()
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
        );
    }

    // Recreates main's Blocks.shulkerBoxProperties (which doesn't exist in 1.21.1) by
    // replicating vanilla's private shulkerBox() settings. The closed-predicate mirrors
    // vanilla's NOT_CLOSED_SHULKER: a shulker is suffocating/view-blocking only when closed.
    private static Block registerShulkerBoxBlock(String id, DyeColor color) {
        BlockBehaviour.StatePredicate isClosedShulker = (state, level, pos) ->
                level.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity be && be.isClosed();
        return register(id,
                settings -> new ShulkerBoxBlock(color, settings),
                BlockBehaviour.Properties.of()
                        .mapColor(color.getMapColor())
                        .forceSolidOn()
                        .strength(2.0F)
                        .dynamicShape()
                        .noOcclusion()
                        .isSuffocating(isClosedShulker)
                        .isViewBlocking(isClosedShulker)
                        .pushReaction(PushReaction.DESTROY)
        );
    }

    // note-block sound; main's spotted carpet GUITAR mismatch is being fixed.
    private static BlockBehaviour.Properties spottedCarpetProperties(MapColor colour) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .strength(0.1F)
                .sound(SoundType.WOOL)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties brickProperties(MapColor colour) {
        // Recreates main's ofFullCopy(BRICKS): stone sound + 2.0/6.0 strength.
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .strength(2.0F, 6.0F)
                .sound(SoundType.STONE);
    }

    private static BlockBehaviour.Properties brickSlabProperties(MapColor colour) {
        // Recreates main's ofFullCopy(BRICK_SLAB); slab drops double blocks for
        // the type=double state (vanilla-correct, main's self-drop is a bug).
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .strength(2.0F, 6.0F)
                .sound(SoundType.STONE);
    }

    // Reproduces main's registerOldStairsBlock; base is the colour's brick block.
    private static Block registerBrickStairs(String id, Block base) {
        return register(id, settings -> new StairBlock(base.defaultBlockState(), settings),
                brickProperties(base.defaultMapColor()));
    }

    // Reproduces main's ofLegacyCopy(X_BRICKS).forceSolidOn() for WallBlock; ofFullCopy
    // is the non-deprecated successor (also copies jump/redstone/occlusion/drops fields).
    private static BlockBehaviour.Properties brickWallProperties(Block base) {
        return BlockBehaviour.Properties.ofFullCopy(base).forceSolidOn();
    }

    // Baked terracotta: basedrum, 0.7/4.2 strength (as main; requires a pickaxe, no sound
    // specified so it stays stone-like). Glazed terracotta reuses these (BASE + 1.4 strength).
    private static BlockBehaviour.Properties terracottaProperties(MapColor colour) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .strength(0.7F, 4.2F)
                .requiresCorrectToolForDrops();
    }

    // Concrete: basedrum, 1.8 strength, pickaxe. All colours map white on the map (monochrome,
    // as in main's DyeColor.WHITE -> MapColor.SNOW); the texture provides the actual colour.
    private static BlockBehaviour.Properties concreteProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.SNOW)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .strength(1.8F)
                .requiresCorrectToolForDrops();
    }

    // Concrete powder: falling block, snare, sand sound; the DyeColor tone drives map colour.
    private static BlockBehaviour.Properties concretePowderProperties(MapColor colour) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .instrument(NoteBlockInstrument.SNARE)
                .strength(0.5F)
                .sound(SoundType.SAND);
    }

    // Glazed terracotta: same bases as terracotta but 1.4 strength (vanilla glazed hardness)
    // and the glazed map tone. The directional FACING state + pattern render come free from
    // GlazedTerracottaBlock; we only provide the per-colour texture.
    private static BlockBehaviour.Properties glazedTerracottaProperties(MapColor colour) {
        return BlockBehaviour.Properties.of()
                .mapColor(colour)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .strength(1.4F, 4.2F)
                .requiresCorrectToolForDrops();
    }

    private static Block register(String id, BlockBehaviour.Properties settings) {
        return register(id, Block::new, settings);
    }

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return Registry.register(BuiltInRegistries.BLOCK,
                ResourceKey.create(Registries.BLOCK, NekomasFixed.id(id)),
                factory.apply(settings));
    }

    // Registers a block that overrides/extends a vanilla id (minecraft:), e.g. the clock.
    private static Block registerVanilla(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        return Registry.register(BuiltInRegistries.BLOCK,
                ResourceKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace(id)),
                factory.apply(settings));
    }

    public static void registerBlocks() {
        NekomasFixed.LOGGER.info("Registering blocks");

        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(BAOBAB_PLANKS, 5, 20);
        fireBlock.setFlammable(BAOBAB_SLAB, 5, 20);
        fireBlock.setFlammable(BAOBAB_FENCE_GATE, 5, 20);
        fireBlock.setFlammable(BAOBAB_FENCE, 5, 20);
        fireBlock.setFlammable(BAOBAB_STAIRS, 5, 20);
        fireBlock.setFlammable(BAOBAB_LOG, 5, 5);
        fireBlock.setFlammable(BAOBAB_WOOD, 5, 5);
        fireBlock.setFlammable(STRIPPED_BAOBAB_LOG, 5, 5);
        fireBlock.setFlammable(STRIPPED_BAOBAB_WOOD, 5, 5);

        StrippableBlockRegistry.register(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        StrippableBlockRegistry.register(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
    }
}
