package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class TermiteRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState;
    public final AnimationState runAnimationState;
    public final AnimationState swipeAnimationState;

    public  TermiteRenderState() {
        this.idleAnimationState = new AnimationState();
        this.runAnimationState = new AnimationState();
        this.swipeAnimationState = new AnimationState();
    }
}
