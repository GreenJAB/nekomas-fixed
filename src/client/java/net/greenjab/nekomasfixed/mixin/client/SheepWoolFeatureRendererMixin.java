package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.util.SpottedRenderStateAccess;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.state.SheepEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepWoolFeatureRenderer.class)
public abstract class SheepWoolFeatureRendererMixin extends FeatureRenderer<SheepEntityRenderState, SheepEntityModel> {

    public SheepWoolFeatureRendererMixin(FeatureRendererContext<SheepEntityRenderState, SheepEntityModel> context) {
        super(context);
    }

    @Shadow @Final private EntityModel<SheepEntityRenderState> woolModel;
    @Shadow @Final private EntityModel<SheepEntityRenderState> babyWoolModel;

    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/SheepEntityRenderState;FF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderSpottedWool(MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int light,
                                   SheepEntityRenderState state, float f, float g, CallbackInfo ci) {

        SpottedRenderStateAccess access = (SpottedRenderStateAccess) state;

        if (access.nekomasfixed$isSpottedState() && !state.sheared && !state.invisible) {
            EntityModel<SheepEntityRenderState> entityModel = state.baby ? this.babyWoolModel : this.woolModel;
            String colorName = state.color.getId();
            Identifier customTexture = Identifier.of("nekomasfixed", "textures/entity/sheep/spotted/" + colorName + "_spotted.png");
            render(entityModel, customTexture, matrixStack, orderedRenderCommandQueue, light, state, 0xFFFFFFFF, 0);

            ci.cancel();
        }
    }
}