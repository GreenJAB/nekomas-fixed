package net.greenjab.nekomasfixed.mixin.client;


import net.greenjab.nekomasfixed.util.CustomShulkerBoxTextureHolder;
import net.minecraft.client.render.block.entity.state.ShulkerBoxBlockEntityRenderState;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShulkerBoxBlockEntityRenderState.class)
public class ShulkerBoxBlockEntityRenderStateMixin implements CustomShulkerBoxTextureHolder {

    @Unique
    private SpriteIdentifier nekomasfixed$customTexture;

    @Override
    public void nekomasfixed$setCustomTexture(SpriteIdentifier texture) {
        this.nekomasfixed$customTexture = texture;
    }

    @Override
    public SpriteIdentifier nekomasfixed$getCustomTexture() {
        return nekomasfixed$customTexture;
    }
}