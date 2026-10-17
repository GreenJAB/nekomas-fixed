package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.render.tab.ModWorldGenOptionTab;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.client.gui.components.tabs.MenuTabBar;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {


    @ModifyArg(
            method = "init",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/tabs/MenuTabBar$Builder;addTabs([Lnet/minecraft/client/gui/components/tabs/Tab;)Lnet/minecraft/client/gui/components/tabs/MenuTabBar$Builder;")
            , index = 0
    )
    private Tab[] createNewTab(Tab[] tabs) {
        Tab[] out = Arrays.copyOf(tabs, tabs.length + 1);
        out[tabs.length] = new ModWorldGenOptionTab((Screen)(Object)this);
        return out;
    }

    @Inject(method = "createNewWorld", at = @At("HEAD"))
    private void markNewWorld(CallbackInfoReturnable<Boolean> cir) {
        ModConfigValues.applyToNextWorld = true;
    }

    @Inject(method = "popScreen", at = @At("HEAD"))
    private void onPopingMenu(CallbackInfo ci) {
        ModConfigValues.resetToDefaults();
    }
}
