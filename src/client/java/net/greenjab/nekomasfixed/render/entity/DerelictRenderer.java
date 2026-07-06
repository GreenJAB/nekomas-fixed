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
public class DerelictRenderer extends ZombieRenderer {

    private static final Identifier TEXTURE = NekomasFixed.id( "textures/entity/zombie/derelict.png");

    public DerelictRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull ZombieRenderState state) {
        return TEXTURE;
    }
}

