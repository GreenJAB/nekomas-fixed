package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class TermiteRenderState extends LivingEntityRenderState {
    public final AnimationState swipeAnimationState = new AnimationState();
}
