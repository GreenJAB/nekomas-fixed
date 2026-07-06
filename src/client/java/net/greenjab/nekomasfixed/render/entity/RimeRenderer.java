package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class RimeRenderer extends ZombieRenderer {

    private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/zombie/rime.png");

    public RimeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull ZombieRenderState state) {
        return TEXTURE;
    }
}