package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedRenderState;
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
public class DrenchedModel extends SkeletonModel<DrenchedRenderState> {

    public DrenchedModel(ModelPart modelPart) {
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
    public void setupAnim(@NonNull DrenchedRenderState drenchedRenderState) {
        super.setupAnim(drenchedRenderState);

        float swimAmount = drenchedRenderState.swimAmount;
        if (swimAmount > 0.0F) {
            this.rightArm.xRot = Mth.rotLerpRad(swimAmount, this.rightArm.xRot, (float) (-Math.PI * 4.0 / 5.0 - 90*Math.PI/180.0)) + swimAmount * 0.35F * Mth.sin((0.1F * drenchedRenderState.ageInTicks));
            this.leftArm.xRot = Mth.rotLerpRad(swimAmount, this.leftArm.xRot, (float) (-Math.PI * 4.0 / 5.0 - 90*Math.PI/180.0)) - swimAmount * 0.35F * Mth.sin((0.1F * drenchedRenderState.ageInTicks));
            this.rightArm.zRot = Mth.rotLerpRad(swimAmount, this.rightArm.zRot, -0.15F - (float)(Math.PI));
            this.leftArm.zRot = Mth.rotLerpRad(swimAmount, this.leftArm.zRot, 0.15F - (float)(Math.PI));
            this.leftLeg.xRot -= swimAmount * 0.55F * Mth.sin((0.1F * drenchedRenderState.ageInTicks));
            this.rightLeg.xRot += swimAmount * 0.55F * Mth.sin((0.1F * drenchedRenderState.ageInTicks));
            this.head.xRot = 0.0F;
        }
    }
}