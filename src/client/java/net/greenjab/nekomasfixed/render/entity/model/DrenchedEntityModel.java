package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedEntityRenderState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class DrenchedEntityModel extends SkeletonModel<DrenchedEntityRenderState> {

    public DrenchedEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition modelPartData = modelData.getRoot();

        SkeletonModel.createDefaultSkeletonMesh(modelPartData);

        PartDefinition head = modelPartData.getChild("head");
        PartDefinition bone2Data = head.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.ZERO);

        bone2Data.addOrReplaceChild("plane_1",
                CubeListBuilder.create().texOffs(33, 1).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, ((float)Math.PI / 4F), 0.0F));

        bone2Data.addOrReplaceChild("plane_2",
                CubeListBuilder.create().texOffs(33, 1).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -((float)Math.PI / 4F), 0.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void setupAnim(@NonNull DrenchedEntityRenderState drenchedEntityRenderState) {
        super.setupAnim(drenchedEntityRenderState);

        float swimAmount = drenchedEntityRenderState.swimAmount;
        if (swimAmount > 0.0F) {
            this.rightArm.xRot = Mth.rotLerpRad(swimAmount, this.rightArm.xRot, (float) (-Math.PI * 4.0 / 5.0 - 90*Math.PI/180.0)) + swimAmount * 0.35F * Mth.sin((0.1F * drenchedEntityRenderState.ageInTicks));
            this.leftArm.xRot = Mth.rotLerpRad(swimAmount, this.leftArm.xRot, (float) (-Math.PI * 4.0 / 5.0 - 90*Math.PI/180.0)) - swimAmount * 0.35F * Mth.sin((0.1F * drenchedEntityRenderState.ageInTicks));
            this.rightArm.zRot = Mth.rotLerpRad(swimAmount, this.rightArm.zRot, -0.15F);
            this.leftArm.zRot = Mth.rotLerpRad(swimAmount, this.leftArm.zRot, 0.15F);
            this.leftLeg.xRot -= swimAmount * 0.55F * Mth.sin((0.1F * drenchedEntityRenderState.ageInTicks));
            this.rightLeg.xRot += swimAmount * 0.55F * Mth.sin((0.1F * drenchedEntityRenderState.ageInTicks));
            this.head.xRot = 0.0F;
        }
    }
}