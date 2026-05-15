package net.greenjab.nekomasfixed.render.entity.state;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class MoobloomEntityRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState;
    public final AnimationState runAnimationState;
    public final AnimationState crouchRunAnimationState;
    public final AnimationState crouchAnimationState;
    public boolean sheared;
    public String variantPath;
    public boolean baby;

    public  MoobloomEntityRenderState() {
        this.idleAnimationState = new AnimationState();
        this.runAnimationState = new AnimationState();
        this.crouchRunAnimationState = new AnimationState();
        this.crouchAnimationState = new AnimationState();
    }
}
