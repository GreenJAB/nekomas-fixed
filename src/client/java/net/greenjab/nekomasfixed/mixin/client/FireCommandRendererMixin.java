package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.render.entity.state.WildfireEntityRenderState;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.resources.model.sprite.SpriteId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FlameFeatureRenderer.class)
public class FireCommandRendererMixin {

    @Unique
    private static final SpriteId SOUL_FIRE_0 = Sheets.BLOCKS_MAPPER.defaultNamespaceApply("soul_fire_0");
    @Unique
    private static final SpriteId SOUL_FIRE_1 = Sheets.BLOCKS_MAPPER.defaultNamespaceApply("soul_fire_1");


    @ModifyArg(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/sprite/AtlasManager;get(Lnet/minecraft/client/resources/model/sprite/SpriteId;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0))
    private SpriteId soulFire0(SpriteId sprite, @Local(argsOnly = true) EntityRenderState state) {
        if (state instanceof WildfireEntityRenderState wildFireEntityRenderState) {
            if (wildFireEntityRenderState.soul) return SOUL_FIRE_0;
        }
        return sprite;
    }

    @ModifyArg(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/sprite/AtlasManager;get(Lnet/minecraft/client/resources/model/sprite/SpriteId;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1))
    private SpriteId soulFire1(SpriteId id, @Local(argsOnly = true) EntityRenderState state) {
        if (state instanceof WildfireEntityRenderState wildFireEntityRenderState) {
            if (wildFireEntityRenderState.soul) return SOUL_FIRE_1;
        }
        return id;
    }
}