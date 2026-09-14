package net.greenjab.nekomasfixed.render.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class BasePlateModel extends EntityModel<TargetDummy> {

    private final ModelPart basePlate;

    public BasePlateModel(ModelPart root) {
        super();
        this.basePlate = root.getChild("base_plate");
    }

    public static LayerDefinition createBodyModel() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("base_plate", CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-6.0F, 11.0F, -6.0F, 12.0F, 1.0F, 12.0F),
                PartPose.offset(0.0F, 12.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(TargetDummy targetDummy, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.basePlate.yRot = (float) (Math.PI / 180.0) * -targetDummy.getYRot();
    }

    @Override
    public void renderToBuffer(@NonNull PoseStack poseStack, @NonNull VertexConsumer buffer, int packedLight,
                               int packedOverlay, int color) {
        this.basePlate.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}