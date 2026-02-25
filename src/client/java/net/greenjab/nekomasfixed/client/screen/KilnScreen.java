package net.greenjab.nekomasfixed.client.screen;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.screen.KilnScreenHandler;
import net.minecraft.client.gui.ScreenPos;import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class KilnScreen extends AbstractFurnaceScreen<KilnScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(NekomasFixed.MOD_ID, "textures/gui/container/kiln.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");

    public KilnScreen(KilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(
                handler,
                inventory,
                title,
                null,
                TEXTURE,
                LIT_PROGRESS_TEXTURE,
                BURN_PROGRESS_TEXTURE,
                List.of()  // Empty list = no tabs = no recipe book functionality
        );
    }

    @Override
    public void init() {
        super.init();
        // The recipe book won't initialize properly because we passed null and empty list
        // The button position is handled by the parent class
    }

    @Override
    protected ScreenPos getRecipeBookButtonPos() {
        // Return position off-screen
        return new ScreenPos(-1000, -1000);
    }
}