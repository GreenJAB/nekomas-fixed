package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;

@Environment(EnvType.CLIENT)
public class DrenchedRenderState extends SkeletonRenderState {
    public int variant = 0;
}