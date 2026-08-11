package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.render.entity.state.WildfireRenderState;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.resources.model.sprite.SpriteId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(FlameFeatureRenderer.class)
public class FlameFeatureRendererMixin {

    @Unique
    private static final SpriteId SOUL_FIRE_0 = Sheets.BLOCKS_MAPPER.defaultNamespaceApply("soul_fire_0");
    @Unique
    private static final SpriteId SOUL_FIRE_1 = Sheets.BLOCKS_MAPPER.defaultNamespaceApply("soul_fire_1");


    @ModifyArg(method = "buildGroup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/sprite/AtlasManager;get(Lnet/minecraft/client/resources/model/sprite/SpriteId;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0))
    private SpriteId soulFire0(SpriteId sprite, @Local(argsOnly = true) List<FlameFeatureRenderer.Submit> submits) {
        AtomicBoolean soul = new AtomicBoolean(false);
        submits.forEach(e-> {
            if (e.entityRenderState() instanceof WildfireRenderState wildFireRenderState) {
                if (wildFireRenderState.soul) soul.set(true);
            }
        });
        if (soul.get()) return SOUL_FIRE_0;
        return sprite;
    }
    @ModifyArg(method = "buildGroup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/sprite/AtlasManager;get(Lnet/minecraft/client/resources/model/sprite/SpriteId;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1))
    private SpriteId soulFire1(SpriteId sprite, @Local(argsOnly = true) List<FlameFeatureRenderer.Submit> submits) {
        AtomicBoolean soul = new AtomicBoolean(false);
        submits.forEach(e-> {
            if (e.entityRenderState() instanceof WildfireRenderState wildFireRenderState) {
                if (wildFireRenderState.soul) soul.set(true);
            }
        });
        if (soul.get()) return SOUL_FIRE_1;
        return sprite;
    }
}