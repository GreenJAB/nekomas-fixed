package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.MinecartEntityRenderState;

@Environment(EnvType.CLIENT)
public class CustomMinecartEntityRenderState extends MinecartEntityRenderState {
    public double wheelRotation = 0;
}
