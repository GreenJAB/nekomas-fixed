package net.greenjab.nekomasfixed.render.entity.animation;

import net.minecraft.client.render.entity.animation.AnimationDefinition;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.animation.Keyframe;

public class TermiteAnimations {
    public static final AnimationDefinition ANIM_TERMITE_IDLE = AnimationDefinition.Builder.create(0.0F)
            .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition ANIM_TERMITE_RUN =
            AnimationDefinition.Builder.create(0.5F)
                    .looping()

                    .addBoneAnimation("front_right_leg", new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F,  AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5F,  AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR)
                    ))

                    .addBoneAnimation("middle_right_leg", new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F,  AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5F,  AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR)
                    ))

                    .addBoneAnimation("back_right_leg", new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F,  AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5F,  AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR)
                    ))

                    // Left side = opposite phase (important for walking)
                    .addBoneAnimation("front_left_leg", new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F,  AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5F,  AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR)
                    ))

                    .addBoneAnimation("middle_left_leg", new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F,  AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5F,  AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR)
                    ))

                    .addBoneAnimation("back_left_leg", new Transformation(
                            Transformation.Targets.ROTATE,
                            new Keyframe(0.0F,  AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0, 20F, 0), Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5F,  AnimationHelper.createRotationalVector(0, -20F, 0), Transformation.Interpolations.LINEAR)
                    ))

                    .build();

    public static final AnimationDefinition ANIM_TERMITE_SWIPE = AnimationDefinition.Builder.create(0.5F)
            .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("pincher", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("pincher", new Transformation(Transformation.Targets.MOVE_ORIGIN,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("pincher", new Transformation(Transformation.Targets.SCALE,
                    new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.5F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();
}