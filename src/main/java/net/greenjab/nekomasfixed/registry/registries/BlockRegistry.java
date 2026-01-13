package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.*;
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

    public static final Block CLOCK = registerVanilla("clock", ClockBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(1F).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));


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
