package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class TargetDummyModel extends HumanoidModel<TargetDummy> {

    public TargetDummyModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyModel() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(@NonNull TargetDummy targetDummy, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(targetDummy, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = (float) (Math.PI / 180.0) * targetDummy.getHeadRotation().getX();
        this.head.yRot = (float) (Math.PI / 180.0) * targetDummy.getHeadRotation().getY();
        this.head.zRot = (float) (Math.PI / 180.0) * targetDummy.getHeadRotation().getZ();
        this.body.xRot = (float) (Math.PI / 180.0) * targetDummy.getBodyRotation().getX();
        this.body.yRot = (float) (Math.PI / 180.0) * targetDummy.getBodyRotation().getY();
        this.body.zRot = (float) (Math.PI / 180.0) * targetDummy.getBodyRotation().getZ();
        this.leftArm.xRot = (float) (Math.PI / 180.0) * targetDummy.getLeftArmRotation().getX();
        this.leftArm.yRot = (float) (Math.PI / 180.0) * targetDummy.getLeftArmRotation().getY();
        this.leftArm.zRot = (float) (Math.PI / 180.0) * targetDummy.getLeftArmRotation().getZ();
        this.rightArm.xRot = (float) (Math.PI / 180.0) * targetDummy.getRightArmRotation().getX();
        this.rightArm.yRot = (float) (Math.PI / 180.0) * targetDummy.getRightArmRotation().getY();
        this.rightArm.zRot = (float) (Math.PI / 180.0) * targetDummy.getRightArmRotation().getZ();
        this.leftLeg.xRot = (float) (Math.PI / 180.0) * targetDummy.getLeftLegRotation().getX();
        this.leftLeg.yRot = (float) (Math.PI / 180.0) * targetDummy.getLeftLegRotation().getY();
        this.leftLeg.zRot = (float) (Math.PI / 180.0) * targetDummy.getLeftLegRotation().getZ();
        this.rightLeg.xRot = (float) (Math.PI / 180.0) * targetDummy.getRightLegRotation().getX();
        this.rightLeg.yRot = (float) (Math.PI / 180.0) * targetDummy.getRightLegRotation().getY();
        this.rightLeg.zRot = (float) (Math.PI / 180.0) * targetDummy.getRightLegRotation().getZ();
    }
}