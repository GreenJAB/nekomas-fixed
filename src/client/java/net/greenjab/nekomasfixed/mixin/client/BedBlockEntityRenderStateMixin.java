package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.client.CustomBedTextureHolder;
import net.minecraft.client.render.block.entity.state.BedBlockEntityRenderState;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BedBlockEntityRenderState.class)
public class BedBlockEntityRenderStateMixin implements CustomBedTextureHolder {

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