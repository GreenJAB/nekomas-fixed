package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.Drenched;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class DrenchedModel extends SkeletonModel<Drenched> {

    public DrenchedModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = modelData.getRoot();

        createDefaultSkeletonMesh(root);

        PartDefinition head = root.getChild("head");
        PartDefinition bone2Data = head.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.ZERO);

        bone2Data.addOrReplaceChild("plane_1",
                CubeListBuilder.create().texOffs(33, 1).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, ((float) Math.PI / 4F), 0.0F));

        bone2Data.addOrReplaceChild("plane_2",
                CubeListBuilder.create().texOffs(33, 1).addBox(-4.0F, -9.0F, 0.0F, 8.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, -((float) Math.PI / 4F), 0.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void setupAnim(Drenched entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity.isPassenger()) {
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float) Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = -((float) Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
            return;
        }

        float swimAmount = this.swimAmount;
        if (swimAmount > 0.0F) {
            this.rightArm.xRot = this.rotlerpRad(swimAmount, this.rightArm.xRot, (float) (-Math.PI * 4.0 / 5.0))
                    + swimAmount * 0.35F * Mth.sin(0.1F * ageInTicks);
            this.leftArm.xRot = this.rotlerpRad(swimAmount, this.leftArm.xRot, (float) (-Math.PI * 4.0 / 5.0))
                    - swimAmount * 0.35F * Mth.sin(0.1F * ageInTicks);
            this.rightArm.zRot = this.rotlerpRad(swimAmount, this.rightArm.zRot, -0.15F);
            this.leftArm.zRot = this.rotlerpRad(swimAmount, this.leftArm.zRot, 0.15F);
            this.leftLeg.xRot -= swimAmount * 0.55F * Mth.sin(0.1F * ageInTicks);
            this.rightLeg.xRot += swimAmount * 0.55F * Mth.sin(0.1F * ageInTicks);
            this.head.xRot = 0.0F;
        }
    }
}
