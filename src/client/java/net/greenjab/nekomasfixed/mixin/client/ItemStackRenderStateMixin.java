package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.util.GlintContext;
import net.greenjab.nekomasfixed.util.GlintTinted;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.class)
public abstract class ItemStackRenderStateMixin implements GlintTinted {
    @Unique private int nekomasfixed$glintColor;
    @Unique private Identifier nekomasfixed$glintBase;

    @Override
    public int nekomasfixed$returnColor() {
        return nekomasfixed$glintColor;
    }

    @Override
    public Identifier nekomasfixed$getGlintBase() {
        return nekomasfixed$glintBase;
    }

    @Override
    public void nekomasfixed$setGlintBase(Identifier base) {
        this.nekomasfixed$glintBase = base;
    }

    @Override
    public void nekomasfixed$setColor(int color) {
        this.nekomasfixed$glintColor = color;
    }

    @Inject(method = "clear", at = @At(value = "RETURN"))
    public void nekomasfixed$clear(CallbackInfo ci) {
        nekomasfixed$glintColor = 0;
        nekomasfixed$glintBase = null;
    }

    @Inject(method = "submit", at = @At(value = "HEAD"))
    public void nekomasfixed$sumbitColor(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci) {
        GlintContext.setColor(this.nekomasfixed$glintColor);
        GlintContext.setBase(this.nekomasfixed$glintBase);
    }

    @Inject(method = "submit", at = @At(value = "RETURN"))
    public void nekomasfixed$sumbitColor2(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci) {
        GlintContext.setColor(0);
        GlintContext.setBase(null);
    }
}
