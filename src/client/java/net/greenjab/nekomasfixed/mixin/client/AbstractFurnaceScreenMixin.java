package net.greenjab.nekomasfixed.mixin.client;

import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public class AbstractFurnaceScreenMixin {


    private RecipeBookWidget recipeBook;

    @Inject(method = "init", at = @At("RETURN"))
    private void removeRecipeBook(CallbackInfo ci) {
        AbstractFurnaceScreen<?> screen = (AbstractFurnaceScreen<?>) (Object) this;
        AbstractFurnaceScreenHandler handler = screen.getScreenHandler();

        // Check if this is your kiln
        if (handler.getClass().getName().contains("KilnScreenHandler")) {
            this.recipeBook = null;
        }
    }
}