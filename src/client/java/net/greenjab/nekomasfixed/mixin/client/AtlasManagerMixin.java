package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registries.SheetRegistry;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AtlasManager.class)
public class AtlasManagerMixin {



    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyKnownAtlases(final TextureManager textureManager, final int maxMipmapLevels, CallbackInfo ci) {

         AtlasManager.AtlasConfig info = new AtlasManager.AtlasConfig(
                SheetRegistry.TERRACOTTA_DECORATED_POT_SHEET,
                Identifier.fromNamespaceAndPath("nekomasfixed", "terracotta_decorated_pot"), // FIXED: No more MAP_DECORATIONS!
                false
        );


        AtlasManager.AtlasConfig pot_face_items_sheet = new AtlasManager.AtlasConfig(
                SheetRegistry.POT_FACE_ITEMS_SHEET,
                Identifier.fromNamespaceAndPath("nekomasfixed", "custom_pot"), // FIXED: Correct definition ID!
                false
        );

        List<AtlasManager.AtlasConfig> customSheets = List.of(info, pot_face_items_sheet);

        AtlasManager manager = (AtlasManager)(Object)this;
        for (AtlasManager.AtlasConfig config : customSheets) {
            TextureAtlas atlasTexture = new TextureAtlas(config.textureId());
            textureManager.register(config.textureId(), atlasTexture);
            AtlasManager.AtlasEntry atlasEntry = new AtlasManager.AtlasEntry(atlasTexture, config);
            manager.atlasByTexture.put(config.textureId(), atlasEntry);
            manager.atlasById.put(config.definitionLocation(), atlasEntry);
        }
    }
}