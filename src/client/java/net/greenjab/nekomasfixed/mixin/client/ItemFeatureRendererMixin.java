package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.render.other.TintedGlint;
import net.greenjab.nekomasfixed.util.GlintTinted;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFeatureRenderer.class)
public abstract class ItemFeatureRendererMixin {
    // Normal
    @WrapOperation(method = "prepareMainSubmit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialInfo;itemGlintRenderType()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType nekomasfixed$prepareMainSubmit(BakedQuad.MaterialInfo materialInfo, Operation<RenderType> original, @Local(argsOnly = true) ItemFeatureRenderer.Submit submit) {
        int color = ((GlintTinted) (Object) submit).nekomasfixed$returnColor();
        Identifier base = ((GlintTinted) (Object) submit).nekomasfixed$getGlintBase();
        RenderType tinted;
        if (color == 0) {
            return original.call(materialInfo);
        } else {
             tinted = TintedGlint.itemGlint(materialInfo.layer() == ChunkSectionLayer.TRANSLUCENT, false, materialInfo.sprite().atlasLocation(), color, base);
        }
        return tinted != null ? tinted : original.call(materialInfo);
    }

    // Special
    @WrapOperation(method = "prepareMainSubmit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialInfo;itemGlintSpecialRenderType()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType nekomasfixed$prepareMainSubmitSpecial(BakedQuad.MaterialInfo materialInfo, Operation<RenderType> original, @Local(argsOnly = true) ItemFeatureRenderer.Submit submit) {
        int color = ((GlintTinted) (Object) submit).nekomasfixed$returnColor();
        Identifier base = ((GlintTinted) (Object) submit).nekomasfixed$getGlintBase();
        RenderType tinted;
        if (color == 0) {
            return original.call(materialInfo);
        } else {
            tinted = TintedGlint.itemGlint(materialInfo.layer() == ChunkSectionLayer.TRANSLUCENT, true, materialInfo.sprite().atlasLocation(), color, base);
        }
        return tinted != null ? tinted : original.call(materialInfo);
    }
}
