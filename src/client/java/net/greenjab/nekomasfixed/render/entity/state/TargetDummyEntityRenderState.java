package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.util.math.EulerAngle;

@Environment(EnvType.CLIENT)
public class TargetDummyEntityRenderState extends BipedEntityRenderState {
	public SkinTextures skinTextures = DefaultSkinHelper.getSteve();
	public float yaw;
	public float timeSinceLastHit;
	public EulerAngle headRotation = TargetDummyEntity.DEFAULT_HEAD_ROTATION;
	public EulerAngle bodyRotation = TargetDummyEntity.DEFAULT_BODY_ROTATION;
	public EulerAngle leftArmRotation = TargetDummyEntity.DEFAULT_LEFT_ARM_ROTATION;
	public EulerAngle rightArmRotation = TargetDummyEntity.DEFAULT_RIGHT_ARM_ROTATION;
	public EulerAngle leftLegRotation = TargetDummyEntity.DEFAULT_LEFT_LEG_ROTATION;
	public EulerAngle rightLegRotation = TargetDummyEntity.DEFAULT_RIGHT_LEG_ROTATION;
}
