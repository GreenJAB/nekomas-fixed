package net.greenjab.nekomasfixed.mixin.client;

import net.minecraft.client.util.SpriteIdentifier;

public interface CustomBedTextureHolder {
    void nekomasfixed$setCustomTexture(SpriteIdentifier texture);
    SpriteIdentifier nekomasfixed$getCustomTexture();
}