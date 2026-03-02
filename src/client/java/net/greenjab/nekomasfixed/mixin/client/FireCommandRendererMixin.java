package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.render.entity.state.WildFireEntityRenderState;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.command.FireCommandRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.texture.AtlasManager;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FireCommandRenderer.class)
public class FireCommandRendererMixin {

    @Unique
    private static final SpriteIdentifier SOUL_FIRE_0 = TexturedRenderLayers.BLOCK_SPRITE_MAPPER.mapVanilla("soul_fire_0");
    @Unique
    private static final SpriteIdentifier SOUL_FIRE_1 = TexturedRenderLayers.BLOCK_SPRITE_MAPPER.mapVanilla("soul_fire_1");


    @ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/EntityRenderState;Lorg/joml/Quaternionf;Lnet/minecraft/client/texture/AtlasManager;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/AtlasManager;getSprite(Lnet/minecraft/client/util/SpriteIdentifier;)Lnet/minecraft/client/texture/Sprite;", ordinal = 0))
    private SpriteIdentifier soulFire0(SpriteIdentifier id, @Local(argsOnly = true) EntityRenderState renderState, @Local(argsOnly = true) AtlasManager atlasManager) {
        if (renderState instanceof WildFireEntityRenderState wildFireEntityRenderState) {
            if (wildFireEntityRenderState.soul) return SOUL_FIRE_0;
        }
        return id;
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/EntityRenderState;Lorg/joml/Quaternionf;Lnet/minecraft/client/texture/AtlasManager;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/AtlasManager;getSprite(Lnet/minecraft/client/util/SpriteIdentifier;)Lnet/minecraft/client/texture/Sprite;", ordinal = 1))
    private SpriteIdentifier soulFire1(SpriteIdentifier id, @Local(argsOnly = true) EntityRenderState renderState, @Local(argsOnly = true) AtlasManager atlasManager) {
        if (renderState instanceof WildFireEntityRenderState wildFireEntityRenderState) {
            if (wildFireEntityRenderState.soul) return SOUL_FIRE_1;
        }
        return id;
    }
}