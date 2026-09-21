package net.greenjab.nekomasfixed.integration.jei;

import net.greenjab.nekomasfixed.NekomasFixed;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingFadeRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingPigmentRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingEchoingTwinkleRecipe;
import net.greenjab.nekomasfixed.registry.recipe.SmithingGlowingRecipe;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class Plugin implements IModPlugin {
    private static final ResourceLocation ID = NekomasFixed.id("jei_plugin");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getSmithingCategory().addExtension(SmithingEchoingFadeRecipe.class, new SmithingEchoingExtension<>());
        registration.getSmithingCategory().addExtension(SmithingEchoingPigmentRecipe.class, new SmithingEchoingExtension<>());
        registration.getSmithingCategory().addExtension(SmithingEchoingTwinkleRecipe.class, new SmithingEchoingExtension<>());
        registration.getSmithingCategory().addExtension(SmithingGlowingRecipe.class, new SmithingEchoingExtension<>());
    }
}
