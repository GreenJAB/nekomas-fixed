package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.util.GlintContext;
import net.greenjab.nekomasfixed.util.GlintTinted;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFeatureRenderer.Submit.class)
public abstract class ItemFeatureRendererSumbitMixin implements GlintTinted {
    @Unique
    private int nekomasfixed$glintColor;

    @Unique
    private Identifier nekomasfixed$glintBase;

    @Override
    public int nekomasfixed$returnColor() {
        return nekomasfixed$glintColor;
    }

    @Override
    public Identifier nekomasfixed$getGlintBase() {
        return nekomasfixed$glintBase;
    }

    @Override
    public void nekomasfixed$setColor(int color) {
        this.nekomasfixed$glintColor = color;
    }

    @Override
    public void nekomasfixed$setGlintBase(Identifier base) {
        this.nekomasfixed$glintBase = base;
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void nekomasfixed$glintContextorSmth(CallbackInfo ci) {
        this.nekomasfixed$glintColor = GlintContext.getColor();
        this.nekomasfixed$glintBase = GlintContext.getBase();
    }
}
