package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.registry.entity.wildfire.WildfireEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import java.util.Arrays;

public class WildfireModel extends HierarchicalModel<WildfireEntity> {
    private final ModelPart root;
    private final ModelPart[] rods;
    private final ModelPart[] shields;
    private final ModelPart head;
    private final ModelPart pillar;
    private float bodyRot;
    private int shieldCount;
    private float shieldAngle;
    private float shieldExtraSpin;

    public WildfireModel(ModelPart modelPart) {
        this.root = modelPart;
        this.head = modelPart.getChild(PartNames.HEAD);
        this.pillar = modelPart.getChild("pillar");
        this.rods = new ModelPart[12];
        this.shields = new ModelPart[4];
        Arrays.setAll(this.rods, i -> modelPart.getChild(getRodName(i)));
        Arrays.setAll(this.shields, i -> modelPart.getChild(getShieldName(i)));
    }

    private static String getRodName(int index) {
        return "rod" + index;
    }

    private static String getShieldName(int index) {
        return "shield" + index;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition modelPartData2 = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0, -3, 0));
        modelPartData2.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5f)), PartPose.ZERO);
        modelPartData.addOrReplaceChild("pillar", CubeListBuilder.create().texOffs(8, 32).addBox(-2.0F, 3.0F, -2.0F, 4.0F, 18.0F, 4.0F), PartPose.ZERO);

        CubeListBuilder modelPartBuilder = CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
        for (int i = 0; i < 12; i++) modelPartData.addOrReplaceChild(getRodName(i), modelPartBuilder, PartPose.offset(0, 0, 0));
        CubeListBuilder modelPartBuilder2 = CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, 0.0F, 0.5F, 12.0F, 22.0F, 1.0F);
        for (int i = 0; i < 4; i++) modelPartData.addOrReplaceChild(getShieldName(i), modelPartBuilder2, PartPose.offset(0, 0, 0));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void prepareMobModel(WildfireEntity entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.bodyRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        this.shieldCount = entity.getShieldsActive();
        this.shieldAngle = 1 - (Mth.cos((float) Math.PI * Mth.clamp(entity.clientFireTime + 0.5f * (partialTick / 20f) * (entity.isOnFire() ? 1 : -1), 0, 1)) + 1) / 2f;
        this.shieldExtraSpin = entity.clientExtraSpin + this.shieldAngle * 4 * partialTick;
    }

    @Override
    public void setupAnim(WildfireEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float bodyRad = this.bodyRot * (float) (Math.PI / 180f);
        float f = (ageInTicks + this.shieldExtraSpin) * (float) Math.PI * -0.03F + bodyRad;

        for (int i = 0; i < 4; i++) {
            this.rods[i].y = 0.0F + Mth.cos(ageInTicks * 0.25F);
            this.rods[i].x = Mth.cos(f) * 9.0F;
            this.rods[i].z = Mth.sin(f) * 9.0F;
            f += (float) Math.PI / 2f;
        }

        f = (float) (Math.PI / 4) + ageInTicks * (float) Math.PI * 0.03F + bodyRad;

        for (int i = 4; i < 8; i++) {
            this.rods[i].y = 5.0F + Mth.cos((i * 2 + ageInTicks) * 0.25F);
            this.rods[i].x = Mth.cos(f) * 7.0F;
            this.rods[i].z = Mth.sin(f) * 7.0F;
            f += (float) Math.PI / 2f;
        }

        f = 0.47123894F + ageInTicks * (float) Math.PI * -0.08F + bodyRad;

        for (int i = 8; i < 12; i++) {
            this.rods[i].y = 11.0F + Mth.cos((i * 1.5F + ageInTicks) * 0.5F);
            this.rods[i].x = Mth.cos(f) * 5.0F;
            this.rods[i].z = Mth.sin(f) * 5.0F;
            f += (float) Math.PI / 2f;
        }

        f = (ageInTicks + this.shieldExtraSpin) * (float) Math.PI * -0.03F + (float) Math.PI / 4f + bodyRad;

        for (int i = 0; i < 4; i++) {
            this.shields[i].y = -1.0F - Mth.cos(ageInTicks * 0.25F);
            this.shields[i].x = Mth.cos(f) * 9.0F;
            this.shields[i].z = Mth.sin(f) * 9.0F;
            this.shields[i].yRot = -f - (float) Math.PI / 2f;
            this.shields[i].xRot = -0.25f - this.shieldAngle * (float) (Math.PI / 2 - 0.25f);
            f += (float) Math.PI / 2f;
            int ii = 2 * i;
            if (ii > 3) ii -= 3;
            this.shields[ii].visible = this.shieldCount > i;
        }
        this.pillar.yRot = f;
        this.pillar.y = Mth.cos(ageInTicks * 0.25F);

        this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
    }
}
