package net.greenjab.nekomasfixed.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class MoobloomEntityRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState;
    public final AnimationState runAnimationState;
    public final AnimationState crouchRunAnimationState;
    public final AnimationState crouchAnimationState;
    public boolean sheared;
    public String variantPath="red_cow_2";
    public boolean baby;

    public  MoobloomEntityRenderState() {
        this.idleAnimationState = new AnimationState();
        this.runAnimationState = new AnimationState();
        this.crouchRunAnimationState = new AnimationState();
        this.crouchAnimationState = new AnimationState();
    }
}
