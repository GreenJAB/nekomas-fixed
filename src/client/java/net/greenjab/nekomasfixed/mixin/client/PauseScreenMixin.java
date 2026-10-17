package net.greenjab.nekomasfixed.mixin.client;


import net.greenjab.nekomasfixed.render.tab.ModWorldGenOptionTab;
import net.greenjab.nekomasfixed.render.tab.screens.InGameEditScreen;
import net.greenjab.nekomasfixed.render.tab.screens.NetherConfigScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void nekomasfixed$addConfigButton(CallbackInfo ci) {
        if (this.minecraft == null || this.minecraft.getSingleplayerServer() == null) return;

        this.addRenderableWidget(
                Button.builder(Component.literal("World Options"),
                                b -> this.minecraft.gui.setScreen(new InGameEditScreen(this)))
                        .bounds(10, 10, 100, 20)
                        .build());
    }
}