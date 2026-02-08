package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.WildFireEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class WildFireEntityModel extends EntityModel<WildFireEntityRenderState> {
	private final ModelPart[] rods;
	private final ModelPart[] shields;
	private final ModelPart head;
	private final ModelPart pillar;

	public WildFireEntityModel(ModelPart modelPart) {
		super(modelPart);
		this.head = modelPart.getChild(EntityModelPartNames.HEAD);
		this.pillar = modelPart.getChild("pillar");
		this.rods = new ModelPart[12];
		this.shields = new ModelPart[4];
		Arrays.setAll(this.rods, i -> modelPart.getChild(getRodName(i)));
		Arrays.setAll(this.shields, i -> modelPart.getChild(getShieldName(i)));
	}

	private static String getRodName(int index) {return "rod" + index;}
	private static String getShieldName(int index) { return "shield" + index;}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData modelPartData2 = modelPartData.addChild(
				EntityModelPartNames.HEAD,
				ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.origin(0, -3, 0));
		modelPartData2.addChild(
				EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5f)), ModelTransform.NONE
		);

		modelPartData.addChild(
				"pillar", ModelPartBuilder.create().uv(8, 32).cuboid(-2.0F, 3.0F, -2.0F, 4.0F, 18.0F, 4.0F), ModelTransform.NONE
		);


		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
		for (int i = 0; i < 12; i++) {
			modelPartData.addChild(getRodName(i), modelPartBuilder, ModelTransform.origin(0, 0, 0));
		}

		ModelPartBuilder modelPartBuilder2 = ModelPartBuilder.create().uv(32, 0).cuboid(-6.0F, 0.0F, 0.5F, 12.0F, 22.0F, 1.0F);
		for (int i = 0; i < 4; i++) {
			modelPartData.addChild(getShieldName(i), modelPartBuilder2, ModelTransform.origin(0, 0, 0));
		}

		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAngles(WildFireEntityRenderState wildFireEntityRenderState) {
		super.setAngles(wildFireEntityRenderState);
		float f = (wildFireEntityRenderState.age+wildFireEntityRenderState.shieldExtraSpin) * (float) Math.PI * -0.03F + wildFireEntityRenderState.bodyYaw * (float)(Math.PI/180f);

		for (int i = 0; i < 4; i++) {
			this.rods[i].originY = 0.0F + MathHelper.cos((wildFireEntityRenderState.age) * 0.25F);
			this.rods[i].originX = MathHelper.cos(f) * 9.0F;
			this.rods[i].originZ = MathHelper.sin(f) * 9.0F;
			f+=(float) Math.PI/2f;
		}

		f = (float) (Math.PI / 4) + wildFireEntityRenderState.age * (float) Math.PI * 0.03F + wildFireEntityRenderState.bodyYaw * (float)(Math.PI/180f);

		for (int i = 4; i < 8; i++) {
			this.rods[i].originY = 5.0F + MathHelper.cos((i * 2 + wildFireEntityRenderState.age) * 0.25F);
			this.rods[i].originX = MathHelper.cos(f) * 7.0F;
			this.rods[i].originZ = MathHelper.sin(f) * 7.0F;
			f+=(float) Math.PI/2f;
		}

		f = 0.47123894F + wildFireEntityRenderState.age * (float) Math.PI * -0.08F + wildFireEntityRenderState.bodyYaw * (float)(Math.PI/180f);

		for (int i = 8; i < 12; i++) {
			this.rods[i].originY = 11.0F + MathHelper.cos((i * 1.5F + wildFireEntityRenderState.age) * 0.5F);
			this.rods[i].originX = MathHelper.cos(f) * 5.0F;
			this.rods[i].originZ = MathHelper.sin(f) * 5.0F;
			f+=(float) Math.PI/2f;
		}

		f = (wildFireEntityRenderState.age+wildFireEntityRenderState.shieldExtraSpin) * (float) Math.PI * -0.03F + (float) Math.PI/4f + wildFireEntityRenderState.bodyYaw * (float)(Math.PI/180f);

		for (int i = 0; i < 4; i++) {
			this.shields[i].originY = -1.0F - MathHelper.cos((wildFireEntityRenderState.age) * 0.25F);
			this.shields[i].originX = MathHelper.cos(f) * 9.0F;
			this.shields[i].originZ = MathHelper.sin(f) * 9.0F;
			this.shields[i].yaw = -f-(float) Math.PI/2f;
			this.shields[i].pitch = -0.25f - (wildFireEntityRenderState.shieldAngle)*(float) (Math.PI/2 -0.25f) ;
			f+=(float) Math.PI/2f;
			int ii = 2*i;
			if (ii>3) ii-=3;
			this.shields[ii].visible = wildFireEntityRenderState.shields>i;
		}
		this.pillar.yaw = f;

		this.head.yaw = wildFireEntityRenderState.relativeHeadYaw * (float) (Math.PI / 180.0);
		this.head.pitch = wildFireEntityRenderState.pitch * (float) (Math.PI / 180.0);
	}
}
