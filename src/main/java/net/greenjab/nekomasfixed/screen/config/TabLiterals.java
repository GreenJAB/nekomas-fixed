package net.greenjab.nekomasfixed.screen.config;

import net.minecraft.ChatFormatting;import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.MapColor;


public class TabLiterals {
    private static final FontDescription ICONS = new FontDescription.Resource(
            Identifier.fromNamespaceAndPath("nekomasfixed", "icons"));

    static Component warningIcon = Component.literal("\uE000")
            .withStyle(style -> style.withFont(ICONS));

    public static final Component MAIN_TITLE = Component.empty()
            .append(Component.translatable("config.nekoma").withColor(0xFF0054).withoutShadow().withStyle(ChatFormatting.BOLD))
            .append(Component.literal(" "))
            .append(Component.translatable("config.fixed").withColor(TextColor.WHITE).withoutShadow().withStyle(ChatFormatting.BOLD))
            ;

    // TITLES, HEADERS AND SEPARATORS
    public static final Component IN_GAME = Component.translatable("config.in_game");

    public static final Component NETHER_TITLE = Literal.compMainTitle("nether");
    public static final Component ENTITY_TITLE = Literal.compMainTitle("entity");
    public static final Component ITEM_TITLE = Literal.compMainTitle("item");
    public static final Component BLOCKS_TITLE = Literal.compMainTitle("blocks");
    public static final Component OTHER_TITLE = Literal.compMainTitle("other");
    public static final Component WORLD_MENU_TITLE = Literal.compMainTitle("worldgen");

    public static final Component CANNOT_BE_CHANGED = Component.empty()
            .append(warningIcon)
            .append(Literal.compSeps("cannot_be_changed", TextColor.YELLOW.getValue()));

    public static final Component TERRAIN = Literal.compSeps("terrain", 0xBA2720);
    public static final Component NETHER = Literal.compSeps("nether", 0xBA2720);
    public static final Component VILLAGERS = Literal.compSeps("villagers", 0xBA2720);
    public static final Component SPAWN = Literal.compSeps("spawn", TextColor.YELLOW.getValue());
    public static final Component RECIPE = Literal.compSeps("recipe", TextColor.AQUA.getValue());
    public static final Component INTERACTION = Literal.compSeps("interaction", TextColor.GREEN.getValue());
    public static final Component MISC_BLUE = Literal.compSeps("misc_other", MapColor.COLOR_LIGHT_BLUE.col);

    //BUTTON TEXTS
    public static final Component GO_TO_WORLDGEN = Literal.compGoToText("worldgen");
    public static final Component GO_TO_ENTITY = Literal.compGoToText("entity");
    public static final Component GO_TO_NETHER = Literal.compGoToText("nether");
    public static final Component GO_TO_OTHER = Literal.compGoToText("other");
    public static final Component GO_TO_ITEM = Literal.compGoToText("item");
    public static final Component GO_TO_BLOCKS = Literal.compGoToText("blocks");

    //SUB CATEGORY TEXTS
    public static final Component CAKE = Literal.compSubCat("cake");

    // WORLDGEN OPTIONS
    public static final Component GENERATE_GEYSERS = Literal.compWorldText("gen_geyser");
    public static final Component GENERATE_GEYSERS_DESC = Literal.compWorldDesc("gen_geyser");

    public static final Component GENERATE_TERMITE_MOUND = Literal.compWorldText("gen_mound");
    public static final Component GENERATE_TERMITE_MOUND_DESC = Literal.compWorldDesc("gen_mound");

    // NETHER OPTIONS
    public static final Component DO_FOOD_ROTTING = Literal.compNetherText("do_food_rotting");
    public static final Component DO_FOOD_ROTTING_DESC = Literal.compNetherDesc("do_food_rotting");

    public static final Component MAGMA_BLOCK_CHANGES = Literal.compNetherText("magma_block_changes");
    public static final Component MAGMA_BLOCK_CHANGES_DESC = Literal.compNetherDesc("magma_block_changes");

    //OTHER OPTIONS
    public static final Component COPPER_ARMOR_CHANGES = Literal.compOtherText("copper_armor_changes");
    public static final Component COPPER_ARMOR_CHANGES_DESC = Literal.compOtherDesc("copper_armor_changes");

    public static final Component ENABLE_DYED_BRUSHES = Literal.compOtherText("enable_dyed_brushes");
    public static final Component ENABLE_DYED_BRUSHES_DESC = Literal.compOtherDesc("enable_dyed_brushes");

    //BLOCK OPTIONS
    public static final Component ENABLE_STACKED_CAKES = Literal.compBlockText("enable_stacked_cakes");
    public static final Component ENABLE_STACKED_CAKES_DESC = Literal.compBlockDesc("enable_stacked_cakes");

    public static final Component CAKE_SLIDER = Literal.compSlider("cake_layers");
    public static final Component CAKE_SLIDER_DESC = Literal.compSliderDesc("cake_layers");

    //ENTITY OPTIONS
    public static final Component SPAWN_SUS_SPIDERS = Literal.compEntityText("spawn_sus_spider");
    public static final Component SPAWN_SUS_SPIDERS_DESC = Literal.compEntityDesc("spawn_sus_spider");

    public static final Component SPAWN_MOOBLOOMS = Literal.compEntityText("spawn_mooblooms");
    public static final Component SPAWN_MOOBLOOMS_DESC = Literal.compEntityDesc("spawn_mooblooms");

    public static final Component SPAWN_TERMITE = Literal.compEntityText("spawn_termite");
    public static final Component SPAWN_TERMITE_DESC = Literal.compEntityDesc("spawn_termite");

    public static final Component SPAWN_DRENCHED = Literal.compEntityText("spawn_drenched");
    public static final Component SPAWN_DRENCHED_DESC = Literal.compEntityDesc("spawn_drenched");

    public static final Component SPAWN_RIME = Literal.compEntityText("spawn_rime");
    public static final Component SPAWN_RIME_DESC = Literal.compEntityDesc("spawn_rime");

    public static final Component SPAWN_DERELICT = Literal.compEntityText("spawn_derelict");
    public static final Component SPAWN_DERELICT_DESC = Literal.compEntityDesc("spawn_derelict");

    public static final Component SPAWN_WILDFIRE = Literal.compEntityText("spawn_wildfire");
    public static final Component SPAWN_WILDFIRE_DESC = Literal.compEntityDesc("spawn_wildfire");

    public static final Component ENABLE_MESSY_BEDS = Literal.compEntityText("enable_messy_beds");
    public static final Component ENABLE_MESSY_BEDS_DESC = Literal.compEntityDesc("enable_messy_beds");
}
