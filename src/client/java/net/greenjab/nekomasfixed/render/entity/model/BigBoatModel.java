package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class BigBoatModel extends HierarchicalModel<BigBoat> {
    private final ModelPart root;
    private final ModelPart chestBottom;
    private final ModelPart chestLid;
    private final ModelPart chestLock;
    private final ModelPart leftPaddle;
    private final ModelPart rightPaddle;
    private final ModelPart leftPaddle2;
    private final ModelPart rightPaddle2;

    public BigBoatModel(ModelPart root) {
        this.root = root;
        this.chestBottom = root.getChild("chest_bottom");
        this.chestLid = root.getChild("chest_lid");
        this.chestLock = root.getChild("chest_lock");
        this.leftPaddle = root.getChild("left_paddle");
        this.rightPaddle = root.getChild("right_paddle");
        this.leftPaddle2 = root.getChild("left_paddle2");
        this.rightPaddle2 = root.getChild("right_paddle2");
    }

    @Override
    public @NonNull ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(BigBoat boat, float partialTick, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean chest = boat.hasChest();
        chestBottom.visible = chest;
        chestLid.visible = chest;
        chestLock.visible = chest;
        int rowers = boat.countRowable();
        float left = boat.getRowingTime(0, partialTick);
        float right = boat.getRowingTime(1, partialTick);
        setPaddleAngles(left, 0, leftPaddle, rowers > 0);
        setPaddleAngles(right, 1, rightPaddle, rowers > 0);
        setPaddleAngles(left, 0, leftPaddle2, rowers > 1);
        setPaddleAngles(right, 1, rightPaddle2, rowers > 1);
    }

    protected static void setPaddleAngles(float angle, int paddle, ModelPart part, boolean active) {
        if (!active) angle = 0;
        part.xRot = (float) Math.PI;
        part.zRot = Mth.clampedLerp((float) (-Math.PI / 3), (float) (-Math.PI / 12), (Mth.sin(-angle) + 1.0F) / 2.0F);
        part.yRot = Mth.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), (Mth.sin(-angle + 1.0F) + 1.0F) / 2.0F);
        if (paddle == 1) part.yRot = (float) Math.PI - part.yRot;
        else part.zRot = -part.zRot;
    }

    private static void addParts(PartDefinition root) {
        root.addOrReplaceChild("boat_1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -0.0F, 16.0F, 3.0F, 23.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -23.0F, 16.0F, 3.0F, 23.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_3", CubeListBuilder.create().texOffs(0, 3).addBox(7.0F, -6.0F, 23.0F, 2.0F, 6.0F, 3.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_4", CubeListBuilder.create().texOffs(0, 3).addBox(-9.0F, -6.0F, 23.0F, 2.0F, 6.0F, 3.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_5", CubeListBuilder.create().texOffs(0, 3).addBox(-9.0F, -6.0F, -26.0F, 2.0F, 6.0F, 3.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_6", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -28.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_7", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, -30.0F, 10.0F, 6.0F, 2.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_8", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, 28.0F, 10.0F, 6.0F, 2.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_9", CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -6.0F, -28.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_10", CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -6.0F, 26.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_11", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, 26.0F, 2.0F, 6.0F, 2.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_12", CubeListBuilder.create().texOffs(0, 3).addBox(7.0F, -6.0F, -26.0F, 2.0F, 6.0F, 3.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_13", CubeListBuilder.create().texOffs(0, 0).addBox(8.0F, -6.0F, -23.0F, 2.0F, 6.0F, 46.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_14", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -6.0F, -23.0F, 2.0F, 6.0F, 46.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_15", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, 23.0F, 14.0F, 3.0F, 3.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_16", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.0F, -26.0F, 14.0F, 3.0F, 3.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_17", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -28.0F, 10.0F, 3.0F, 1.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_18", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -27.0F, 12.0F, 3.0F, 1.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_19", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, 26.0F, 12.0F, 3.0F, 1.0F), PartPose.ZERO);
        root.addOrReplaceChild("boat_20", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, 27.0F, 10.0F, 3.0F, 1.0F), PartPose.ZERO);

        root.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(48, 64).addBox(-1.0F, -45F, 8.0F, 2.0F, 45.0F, 2.0F), PartPose.ZERO);

        float dist = 20f;
        root.addOrReplaceChild("left_paddle",
                CubeListBuilder.create().texOffs(62, 0).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
                PartPose.offsetAndRotation(10.0F, -6.0F, -0.0F-dist, 0.0F, 0.0F, (float) (Math.PI / 16)));
        root.addOrReplaceChild("right_paddle",
                CubeListBuilder.create().texOffs(62, 20).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
                PartPose.offsetAndRotation(-10.0F, -6.0F, -0.0F-dist, 0.0F, (float) Math.PI, (float) (Math.PI / 16)));
        root.addOrReplaceChild("left_paddle2",
                CubeListBuilder.create().texOffs(62, 0).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, -1.001F, 7.0F, 6.0F, 1.0F),
                PartPose.offsetAndRotation(10.0F, -6.0F, -0.0F, 0.0F, 0.0F, (float) (Math.PI / 16)));
        root.addOrReplaceChild("right_paddle2",
                CubeListBuilder.create().texOffs(62, 20).addBox(-5.0F, 0.0F, -1.0F, 18.0F, 2.0F, 2.0F).addBox(8F, -3.0F, 0.001F, 7.0F, 6.0F, 1.0F),
                PartPose.offsetAndRotation(-10.0F, -6.0F, -0.0F, 0.0F, (float) Math.PI, (float) (Math.PI / 16)));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        addParts(root);
        root.addOrReplaceChild("chest_bottom",
            CubeListBuilder.create().texOffs(0, 92).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F),
            PartPose.offsetAndRotation(-6.0F, -8.0F, 13.0F, 0.0F, 0, 0.0F));
        root.addOrReplaceChild("chest_lid",
            CubeListBuilder.create().texOffs(0, 75).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F),
            PartPose.offsetAndRotation(-6.0F, -12.0F, 13.0F, 0f, 0, 0.0F));
        root.addOrReplaceChild("chest_lock",
            CubeListBuilder.create().texOffs(0, 75).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F),
            PartPose.offsetAndRotation(-1.0F, -9.0F, 12.0F, 0.0F, 0, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }
}
