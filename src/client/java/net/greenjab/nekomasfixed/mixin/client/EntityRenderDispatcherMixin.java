package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.entity.wildfire.WildfireEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Unique
    private static final Material SOUL_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/soul_fire_0"));
    @Unique
    private static final Material SOUL_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/soul_fire_1"));

    @ModifyExpressionValue(method = "renderFlame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/model/ModelBakery;FIRE_0:Lnet/minecraft/client/resources/model/Material;"))
    private Material soulFire0(Material original, @Local(argsOnly = true) Entity entity) {
        return entity instanceof WildfireEntity wildfire && wildfire.isSoulActive() ? SOUL_FIRE_0 : original;
    }

    @ModifyExpressionValue(method = "renderFlame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/model/ModelBakery;FIRE_1:Lnet/minecraft/client/resources/model/Material;"))
    private Material soulFire1(Material original, @Local(argsOnly = true) Entity entity) {
        return entity instanceof WildfireEntity wildfire && wildfire.isSoulActive() ? SOUL_FIRE_1 : original;
    }
}
