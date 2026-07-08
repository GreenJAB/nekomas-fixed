package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.util.CustomShulkerBoxTextureHolder;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxRenderer.class)
public class ShulkerBoxBlockEntityRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/level/block/entity/ShulkerBoxBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/ShulkerBoxRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", at = @At("TAIL"))
    private void detectCustomShulkerBox(
            ShulkerBoxBlockEntity ShulkerBoxBlockEntity,
            ShulkerBoxRenderState state,
            float tickDelta,
            Vec3 cameraPos,
            ModelFeatureRenderer.CrumblingOverlay overlay,
            CallbackInfo ci) {

        String identifier = null;
        if (ShulkerBoxBlockEntity.getBlockState().getBlock() == BlockRegistry.AMBER_SHULKER_BOX) identifier = "entity/shulker/shulker_amber";
        else if (ShulkerBoxBlockEntity.getBlockState().getBlock() == BlockRegistry.AQUA_SHULKER_BOX) identifier = "entity/shulker/shulker_aqua";
        else if (ShulkerBoxBlockEntity.getBlockState().getBlock() == BlockRegistry.MAROON_SHULKER_BOX) identifier = "entity/shulker/shulker_maroon";
        else if (ShulkerBoxBlockEntity.getBlockState().getBlock() == BlockRegistry.INDIGO_SHULKER_BOX) identifier = "entity/shulker/shulker_indigo";
        if (identifier!=null) ((CustomShulkerBoxTextureHolder) state).nekomasfixed$setCustomTexture(
                new SpriteId(Sheets.SHULKER_SHEET,NekomasFixed.id(identifier)));
    }

    @ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/ShulkerBoxRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(value = "STORE"), ordinal = 0)
    private SpriteId replaceTexture(SpriteId original, ShulkerBoxRenderState state) {
        SpriteId custom = ((CustomShulkerBoxTextureHolder) state).nekomasfixed$getCustomTexture();
        if (custom != null) return custom;
        return original;
    }
}