package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.state.ZombieEntityRenderState;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class JungleZombieRenderer extends ZombieEntityRenderer {


    private static final Identifier TEXTURE = Identifier.of("nekomasfixed", "textures/entity/zombie/jungle_zombie.png");

    public JungleZombieRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ZombieEntityRenderState state) {
        return TEXTURE;
    }
}

