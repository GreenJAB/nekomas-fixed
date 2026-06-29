package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.CustomShulkerBoxTextureHolder;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.ShulkerBoxBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public class ShulkerBoxBlockEntityRendererMixin {

    @Inject(method = "updateRenderState(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;Lnet/minecraft/client/render/block/entity/state/ShulkerBoxBlockEntityRenderState;FLnet/minecraft/util/math/Vec3d;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V", at = @At("TAIL"))
    private void detectCustomShulkerBox(
            ShulkerBoxBlockEntity ShulkerBoxBlockEntity,
            ShulkerBoxBlockEntityRenderState state,
            float tickDelta,
            Vec3d cameraPos,
            ModelCommandRenderer.CrumblingOverlayCommand overlay,
            CallbackInfo ci) {

        String identifier = null;
        if (ShulkerBoxBlockEntity.getCachedState().getBlock() == BlockRegistry.AMBER_SHULKER_BOX) identifier = "entity/shulker/amber";
        else if (ShulkerBoxBlockEntity.getCachedState().getBlock() == BlockRegistry.AQUA_SHULKER_BOX) identifier = "entity/shulker/aqua";
        else if (ShulkerBoxBlockEntity.getCachedState().getBlock() == BlockRegistry.MAROON_SHULKER_BOX) identifier = "entity/shulker/maroon";
        else if (ShulkerBoxBlockEntity.getCachedState().getBlock() == BlockRegistry.INDIGO_SHULKER_BOX) identifier = "entity/shulker/indigo";
        if (identifier!=null) ((CustomShulkerBoxTextureHolder) state).nekomasfixed$setCustomTexture(
                new SpriteIdentifier(TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE,NekomasFixed.id(identifier)));
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/render/block/entity/state/ShulkerBoxBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At(value = "STORE"), ordinal = 0)
    private SpriteIdentifier replaceTexture(SpriteIdentifier original, ShulkerBoxBlockEntityRenderState state) {
        SpriteIdentifier custom = ((CustomShulkerBoxTextureHolder) state).nekomasfixed$getCustomTexture();
        if (custom != null) return custom;
        return original;
    }
}