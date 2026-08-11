package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.WildfireRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class WildfireModel extends EntityModel<WildfireRenderState> {
	private final ModelPart[] rods;
	private final ModelPart[] shields;
	private final ModelPart head;
	private final ModelPart pillar;

	public WildfireModel(ModelPart modelPart) {
		super(modelPart);
		this.head = modelPart.getChild(PartNames.HEAD);
		this.pillar = modelPart.getChild("pillar");
		this.rods = new ModelPart[12];
		this.shields = new ModelPart[4];
		Arrays.setAll(this.rods, i -> modelPart.getChild(getRodName(i)));
		Arrays.setAll(this.shields, i -> modelPart.getChild(getShieldName(i)));
	}

	private static String getRodName(int index) {return "rod" + index;}
	private static String getShieldName(int index) { return "shield" + index;}

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

	public void setupAnim(@NonNull WildfireRenderState wildFireRenderState) {
		super.setupAnim(wildFireRenderState);
		float f = (wildFireRenderState.ageInTicks+ wildFireRenderState.shieldExtraSpin) * (float) Math.PI * -0.03F + wildFireRenderState.bodyRot * (float)(Math.PI/180f);

		for (int i = 0; i < 4; i++) {
			this.rods[i].y = 0.0F + Mth.cos((wildFireRenderState.ageInTicks) * 0.25F);
			this.rods[i].x = Mth.cos(f) * 9.0F;
			this.rods[i].z = Mth.sin(f) * 9.0F;
			f+=(float) Math.PI/2f;
		}

		f = (float) (Math.PI / 4) + wildFireRenderState.ageInTicks * (float) Math.PI * 0.03F + wildFireRenderState.bodyRot * (float)(Math.PI/180f);

		for (int i = 4; i < 8; i++) {
			this.rods[i].y = 5.0F + Mth.cos((i * 2 + wildFireRenderState.ageInTicks) * 0.25F);
			this.rods[i].x = Mth.cos(f) * 7.0F;
			this.rods[i].z = Mth.sin(f) * 7.0F;
			f+=(float) Math.PI/2f;
		}

		f = 0.47123894F + wildFireRenderState.ageInTicks * (float) Math.PI * -0.08F + wildFireRenderState.bodyRot * (float)(Math.PI/180f);

		for (int i = 8; i < 12; i++) {
			this.rods[i].y = 11.0F + Mth.cos((i * 1.5F + wildFireRenderState.ageInTicks) * 0.5F);
			this.rods[i].x = Mth.cos(f) * 5.0F;
			this.rods[i].z = Mth.sin(f) * 5.0F;
			f+=(float) Math.PI/2f;
		}

		f = (wildFireRenderState.ageInTicks+ wildFireRenderState.shieldExtraSpin) * (float) Math.PI * -0.03F + (float) Math.PI/4f + wildFireRenderState.bodyRot * (float)(Math.PI/180f);

		for (int i = 0; i < 4; i++) {
			this.shields[i].y = -1.0F - Mth.cos((wildFireRenderState.ageInTicks) * 0.25F);
			this.shields[i].x = Mth.cos(f) * 9.0F;
			this.shields[i].z = Mth.sin(f) * 9.0F;
			this.shields[i].yRot = -f-(float) Math.PI/2f;
			this.shields[i].xRot = -0.25f - (wildFireRenderState.shieldAngle)*(float) (Math.PI/2 -0.25f) ;
			f+=(float) Math.PI/2f;
			int ii = 2*i;
			if (ii>3) ii-=3;
			this.shields[ii].visible = wildFireRenderState.shields>i;
		}
		this.pillar.yRot = f;
		this.pillar.y = Mth.cos((wildFireRenderState.ageInTicks) * 0.25F);

		this.head.yRot = wildFireRenderState.yRot * (float) (Math.PI / 180.0);
		this.head.xRot = wildFireRenderState.xRot * (float) (Math.PI / 180.0);
	}
}
