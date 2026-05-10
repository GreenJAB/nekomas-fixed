package net.greenjab.nekomasfixed.render.entity.animation;

import net.minecraft.client.render.entity.animation.*;

public class MinecartModelAnimations {
    public static final AnimationDefinition ANIM_IDLE = AnimationDefinition.Builder.create(0.0F).build();

    public static final AnimationDefinition ANIM_SLOW = AnimationDefinition.Builder.create(0.5F).looping()
            .addBoneAnimation("wheel2", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -360.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("wheel1", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -360.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("wheel3", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -360.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("wheel4", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -360.0F), Transformation.Interpolations.LINEAR)
            )).build();

    public static final AnimationDefinition ANIM_FAST = AnimationDefinition.Builder.create(0.5F).looping()
            .addBoneAnimation("wheel2", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -3600.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("wheel1", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -3600.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("wheel3", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -3600.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("wheel4", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -3600.0F), Transformation.Interpolations.LINEAR)
            )).build();
}
