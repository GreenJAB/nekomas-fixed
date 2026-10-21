package net.greenjab.nekomasfixed.mixin.client;

import com.terraformersmc.modmenu.gui.ModsScreen;
import net.greenjab.nekomasfixed.render.tab.screens.InGameEditScreen;
import net.greenjab.nekomasfixed.render.tab.screens.credits.ModCreditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonLinks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreditsAndAttributionScreen.class)
public class CreditAndAttributeScreenMixin {

    @Unique
    private static final Component TITLE = Component.translatable("credits_and_attribution.screen.title");
    @Unique
    private static final Component CREDITS_BUTTON = Component.translatable("credits_and_attribution.button.credits");
    @Unique
    private static final Component MOD_CREDITS_BUTTON = Component.translatable("credits.button.credits");
    @Unique
    private static final Component ATTRIBUTION_BUTTON = Component.translatable("credits_and_attribution.button.attribution");
    @Unique
    private static final Component LICENSES_BUTTON = Component.translatable("credits_and_attribution.button.licenses");


    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci){
        CreditsAndAttributionScreen screen = (CreditsAndAttributionScreen)(Object)this;
        screen.layout.addTitleHeader(TITLE, screen.getFont());
        LinearLayout content = ((LinearLayout)screen.layout.addToContents(LinearLayout.vertical())).spacing(8);
        content.defaultCellSetting().alignHorizontallyCenter();
        content.addChild(Button.builder(CREDITS_BUTTON, (button) -> screen.openCreditsScreen()).width(210).build());
        content.addChild(Button.builder(MOD_CREDITS_BUTTON, (button) -> Minecraft.getInstance().gui.setScreen(ModCreditScreen.create(screen))).width(210).build());
        content.addChild(Button.builder(ATTRIBUTION_BUTTON, ConfirmLinkScreen.confirmLink(screen, CommonLinks.ATTRIBUTION)).width(210).build());
        content.addChild(Button.builder(LICENSES_BUTTON, ConfirmLinkScreen.confirmLink(screen, CommonLinks.LICENSES)).width(210).build());
        screen.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (button) -> screen.onClose()).width(200).build());
        screen.layout.arrangeElements();
        screen.layout.visitWidgets(screen::addRenderableWidget);
        ci.cancel();
    }
}
