package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.target_access_class.SheepAccess;
import net.greenjab.nekomasfixed.util.GlobalVariables;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SheepFurLayer.class)
public class SheepFurLayerMixin {
    @ModifyArgs(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/Sheep;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/SheepFurLayer;coloredCutoutModelCopyLayerRender(Lnet/minecraft/client/model/EntityModel;Lnet/minecraft/client/model/EntityModel;Lnet/minecraft/resources/ResourceLocation;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFFI)V"
            )
    )
    private void render(Args args) {
        int light = args.get(5);
        SheepAccess sheepEntity = args.get(6);
        args.set(5, sheepEntity.nekomasfixed$isGlowing() ? GlobalVariables.GLOW_STRENGTH : light);
        // Changing rgb values, multiplying their values, making color x1.2 brighter
//        if (sheepEntity.nekomasfixed$isGlowing()) {
//            for (int c = 13; c <= 15; c++) {
//                float colorChannel = args.get(c);
//                colorChannel *= nekomasfixedinescenceClient.COLOR_VALUE_MULTIPLIER;
//                if (colorChannel > 1.0f) {
//                    colorChannel = 1.0f;
//                }
//                args.set(c, colorChannel);
//            }
//        }
    }
}
