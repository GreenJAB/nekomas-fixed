package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.util.CushionMixinAccessor;
import net.minecraft.client.renderer.entity.CushionRenderer;
import net.minecraft.client.renderer.entity.state.CushionRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.decoration.Cushion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(CushionRenderer.class)
public abstract class CushionRendererMixin {

    /** namespace:path for the 4 new entity textures. */
    private static final Map<String, Identifier> NEKOMAS_MOD_TEXTURES = new HashMap<>();
    static {
        NEKOMAS_MOD_TEXTURES.put("amber",
                Identifier.fromNamespaceAndPath("nekomasfixed", "textures/entity/cushion/amber_cushion.png"));
        NEKOMAS_MOD_TEXTURES.put("aqua",
                Identifier.fromNamespaceAndPath("nekomasfixed", "textures/entity/cushion/aqua_cushion.png"));
        NEKOMAS_MOD_TEXTURES.put("indigo",
                Identifier.fromNamespaceAndPath("nekomasfixed", "textures/entity/cushion/indigo_cushion.png"));
        NEKOMAS_MOD_TEXTURES.put("maroon",
                Identifier.fromNamespaceAndPath("nekomasfixed", "textures/entity/cushion/maroon_cushion.png"));
    }

    @Inject(method = "extractRenderState",
            at = @At("TAIL"))
    private void nekomas$swapTexture(Cushion cushion,
                                     CushionRenderState state,
                                     float partialTicks,
                                     CallbackInfo ci) {
        String modColor = ((CushionMixinAccessor) cushion).nekomas$getModColor();
        if (modColor != null && !modColor.isEmpty()) {
            Identifier tex = NEKOMAS_MOD_TEXTURES.get(modColor);
            if (tex != null) {
                state.texture = tex;
            }
        }
    }
}