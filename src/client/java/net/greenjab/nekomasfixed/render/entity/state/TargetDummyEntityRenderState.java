package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Rotations;
import net.minecraft.world.entity.player.PlayerSkin;

@Environment(EnvType.CLIENT)
public class TargetDummyEntityRenderState extends HumanoidRenderState {
	public PlayerSkin skinTextures = DefaultPlayerSkin.getDefaultSkin();
	public boolean isZombie;
	public float yaw;
	public float timeSinceLastHit;
	public Rotations headRotation = TargetDummy.DEFAULT_HEAD_ROTATION;
	public Rotations bodyRotation = TargetDummy.DEFAULT_BODY_ROTATION;
	public Rotations leftArmRotation = TargetDummy.DEFAULT_LEFT_ARM_ROTATION;
	public Rotations rightArmRotation = TargetDummy.DEFAULT_RIGHT_ARM_ROTATION;
	public Rotations leftLegRotation = TargetDummy.DEFAULT_LEFT_LEG_ROTATION;
	public Rotations rightLegRotation = TargetDummy.DEFAULT_RIGHT_LEG_ROTATION;
}
