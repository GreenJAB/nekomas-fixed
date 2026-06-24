package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.SkeletonEntityRenderState;

@Environment(EnvType.CLIENT)
public class DrenchedEntityRenderState extends SkeletonEntityRenderState {
    public int variant = 0;
}