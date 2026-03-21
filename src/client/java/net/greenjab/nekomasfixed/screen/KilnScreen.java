package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.List;

public class KilnScreen extends AbstractFurnaceScreen<KilnScreenHandler> {
    private static final Identifier TEXTURE = NekomasFixed.id("textures/gui/container/kiln.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");
    private static final Text TOGGLE_KILNABLE_TEXT = Text.translatable("gui.recipebook.toggleRecipes.kilnable");

    private static final List<RecipeBookWidget.Tab> TABS = List.of(
            new RecipeBookWidget.Tab(Items.COMPASS, RecipeRegistry.KILNING),
            new RecipeBookWidget.Tab(Items.SAND, RecipeRegistry.KILNING_BLOCK),
            new RecipeBookWidget.Tab(Items.CLAY_BALL, RecipeRegistry.KILNING_MISC)
    );

    public KilnScreen(KilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(
                handler,
                inventory,
                title,
                TOGGLE_KILNABLE_TEXT,
                TEXTURE,
                LIT_PROGRESS_TEXTURE,
                BURN_PROGRESS_TEXTURE,
                TABS
        );
    }
}