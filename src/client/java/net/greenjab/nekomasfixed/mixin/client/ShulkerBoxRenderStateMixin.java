package net.greenjab.nekomasfixed.mixin.client;


import net.greenjab.nekomasfixed.util.CustomShulkerBoxTextureHolder;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.resources.model.sprite.SpriteId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShulkerBoxRenderState.class)
public class ShulkerBoxRenderStateMixin implements CustomShulkerBoxTextureHolder {

    @Unique
    private SpriteId nekomasfixed$customTexture;

    @Override
    public void nekomasfixed$setCustomTexture(SpriteId texture) {
        this.nekomasfixed$customTexture = texture;
    }

    @Override
    public SpriteId nekomasfixed$getCustomTexture() {
        return nekomasfixed$customTexture;
    }
}