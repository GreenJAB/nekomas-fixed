package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedEntityRenderState;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class DrenchedEntityModel extends SkeletonEntityModel<DrenchedEntityRenderState> {

    public DrenchedEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();

        SkeletonEntityModel.addLimbs(modelPartData);

        ModelPartData head = modelPartData.getChild("head");
        ModelPartData bone2Data = head.addChild("bone2", ModelPartBuilder.create(), ModelTransform.NONE);

        bone2Data.addChild("plane_1",
                ModelPartBuilder.create().uv(33, 1).cuboid(-4.0F, -9.0F, 0.0F, 8.0F, 9.0F, 0.0F),
                ModelTransform.of(0.0F, -8.0F, 0.0F, 0.0F, ((float)Math.PI / 4F), 0.0F)
        );

        bone2Data.addChild("plane_2",
                ModelPartBuilder.create().uv(33, 1).cuboid(-4.0F, -9.0F, 0.0F, 8.0F, 9.0F, 0.0F),
                ModelTransform.of(0.0F, -8.0F, 0.0F, 0.0F, -((float)Math.PI / 4F), 0.0F)
        );

        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(DrenchedEntityRenderState drenchedEntityRenderState) {
        super.setAngles(drenchedEntityRenderState);

        float f = drenchedEntityRenderState.leaningPitch;
        if (f > 0.0F) {
            this.rightArm.pitch = MathHelper.lerpAngleRadians(f, this.rightArm.pitch, (float) (-Math.PI * 4.0 / 5.0))
                    + f * 0.35F * MathHelper.sin(0.1F * drenchedEntityRenderState.age);
            this.leftArm.pitch = MathHelper.lerpAngleRadians(f, this.leftArm.pitch, (float) (-Math.PI * 4.0 / 5.0))
                    - f * 0.35F * MathHelper.sin(0.1F * drenchedEntityRenderState.age);
            this.rightArm.roll = MathHelper.lerpAngleRadians(f, this.rightArm.roll, -0.15F);
            this.leftArm.roll = MathHelper.lerpAngleRadians(f, this.leftArm.roll, 0.15F);
            this.leftLeg.pitch = this.leftLeg.pitch - f * 0.55F * MathHelper.sin(0.1F * drenchedEntityRenderState.age);
            this.rightLeg.pitch = this.rightLeg.pitch + f * 0.55F * MathHelper.sin(0.1F * drenchedEntityRenderState.age);
            this.head.pitch = 0;
        }
    }
}