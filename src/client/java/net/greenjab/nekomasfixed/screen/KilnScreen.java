package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.RecipeRegistry;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;
import java.util.List;

public class KilnScreen extends AbstractFurnaceScreen<KilnScreenHandler> {
    private static final Identifier TEXTURE = NekomasFixed.id("textures/gui/container/kiln.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.withDefaultNamespace("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.withDefaultNamespace("container/furnace/burn_progress");
    private static final Component TOGGLE_KILNABLE_TEXT = Component.translatable("gui.recipebook.toggleRecipes.kilnable");

    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(Items.COMPASS, RecipeRegistry.KILNING),
            new RecipeBookComponent.TabInfo(Items.SAND, RecipeRegistry.KILNING_BLOCK),
            new RecipeBookComponent.TabInfo(Items.CLAY_BALL, RecipeRegistry.KILNING_MISC)
    );

    public KilnScreen(KilnScreenHandler handler, Inventory inventory, Component title) {
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