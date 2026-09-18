package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.moobloom.Moobloom;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class MoobloomModel extends QuadrupedModel<Moobloom> {
    // Adult mooblooms have blooms; the baby mesh has none, so these stay null there.
    public final ModelPart flower1;
    public final ModelPart flower2;
    public final ModelPart flower3;

    public MoobloomModel(ModelPart root) {
        super(root, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
        ModelPart rotation = this.body.hasChild("rotation") ? this.body.getChild("rotation") : null;
        this.flower1 = rotation != null && rotation.hasChild("flower1") ? rotation.getChild("flower1") : null;
        this.flower2 = rotation != null && rotation.hasChild("flower2") ? rotation.getChild("flower2") : null;
        this.flower3 = this.head.hasChild("flower3") ? this.head.getChild("flower3") : null;
    }

    public static LayerDefinition getTexturedModelData() {
        return LayerDefinition.create(getModelData(), 64, 64);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 2.0F));

        PartDefinition head = root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F)
                        .texOffs(1, 33)
                        .addBox(-3.0F, 1.0F, -7.0F, 6.0F, 3.0F, 1.0F)
                        .texOffs(22, 0)
                        .addBox("right_horn", -5.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F)
                        .texOffs(22, 0)
                        .addBox("left_horn", 4.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 4.0F, -8.0F)
        );

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 2.0F));
        root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4), PartPose.offset(-4.0F, 12.0F, 7.0F));
        root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4), PartPose.offset(4.0F, 12.0F, 7.0F));
        root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4), PartPose.offset(-4.0F, 12.0F, -5.0F));
        root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4), PartPose.offset(4.0F, 12.0F, -5.0F));

        PartDefinition partDefinition = body.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(18, 4).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F)
                .texOffs(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition flower1 = partDefinition.addOrReplaceChild("flower1", CubeListBuilder.create().texOffs(0, 42).addBox(-7.975F, -16.0F, 1.2F, 16.0F, 16.0F, 0.0F), PartPose.offsetAndRotation(-2.025F, -2.0F, 2.8F, -1.5708F, 0.0F, 0.0F));
        flower1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F), PartPose.offsetAndRotation(0.025F, -8.0F, 1.2F, 0.0F, 3.1416F, 0.0F));

        PartDefinition flower2 = partDefinition.addOrReplaceChild("flower2", CubeListBuilder.create().texOffs(0, 42).addBox(-5.95F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F), PartPose.offsetAndRotation(2.95F, 5.0F, 3.0F, -1.5708F, 0.0F, 0.7854F));
        flower2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F), PartPose.offsetAndRotation(2.05F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition flower3 = head.addOrReplaceChild("flower3", CubeListBuilder.create().texOffs(0, 42).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F), PartPose.offsetAndRotation(0.0F, -4.0F, -3.2F, 0.0F, -0.576F, 0.0F));
        flower3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return mesh;
    }

    // A sheared moobloom loses its bloom; regrowing flips the flag back. Always set
    // visibility both ways so a bloom that was hidden while sheared reappears on regrow.
    // (The baby mesh has no blooms, so its flower parts are null here.)
    @Override
    public void setupAnim(@NonNull Moobloom entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        boolean sheared = entity.isSheared();
        if (this.flower1 != null) this.flower1.visible = !sheared;
        if (this.flower2 != null) this.flower2.visible = !sheared;
        if (this.flower3 != null) this.flower3.visible = !sheared;
    }
}