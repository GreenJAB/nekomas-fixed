package net.greenjab.nekomasfixed.mixin.client;


import net.greenjab.nekomasfixed.util.CustomShulkerBoxTextureHolder;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.resources.model.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShulkerBoxRenderState.class)
public class ShulkerBoxBlockEntityRenderStateMixin implements CustomShulkerBoxTextureHolder {

    @Unique
    private Material nekomasfixed$customTexture;

    @Override
    public void nekomasfixed$setCustomTexture(Material texture) {
        this.nekomasfixed$customTexture = texture;
    }

    @Override
    public Material nekomasfixed$getCustomTexture() {
        return nekomasfixed$customTexture;
    }
}