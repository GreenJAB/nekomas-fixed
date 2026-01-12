package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.greenjab.nekomasfixed.render.entity.feature.BasePlateFeatureRenderer;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyArmorEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyEntityRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.PlayerSkinCache;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jspecify.annotations.Nullable;

import static net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity.*;

@Environment(EnvType.CLIENT)
public class TargetDummyEntityRenderer extends LivingEntityRenderer<TargetDummyEntity, TargetDummyEntityRenderState, TargetDummyArmorEntityModel> {
	private final PlayerSkinCache skinCache;
	private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/targetdummy/default.png");
	private static final Identifier ZOMBIE_TEXTURE = NekomasFixed.id("textures/entity/targetdummy/zombie.png");

	public TargetDummyEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new TargetDummyEntityModel(context.getPart(EntityModelLayerRegistry.TARGET_DUMMY)), 0.0F);
		this.skinCache = context.getPlayerSkinCache();
		this.addFeature(
				new ArmorFeatureRenderer<>(
						this,
						EquipmentModelData.mapToEntityModel(EntityModelLayerRegistry.TARGET_DUMMY_EQUIPMENT, context.getEntityModels(), TargetDummyArmorEntityModel::new),
						context.getEquipmentRenderer()
				)
		);

		this.addFeature(new HeldItemFeatureRenderer<>(this));
		this.addFeature(new ElytraFeatureRenderer<>(this, context.getEntityModels(), context.getEquipmentRenderer()));
		this.addFeature(new HeadFeatureRenderer<>(this, context.getEntityModels(), context.getPlayerSkinCache()));
		this.addFeature(new BasePlateFeatureRenderer(this, context.getEntityModels()));
	}

	public Identifier getTexture(TargetDummyEntityRenderState TargetDummyEntityRenderState) {
		if (TargetDummyEntityRenderState.skinTextures == null){
			return TargetDummyEntityRenderState.isZombie?ZOMBIE_TEXTURE:TEXTURE;
		}
		return TargetDummyEntityRenderState.skinTextures.body().texturePath();
	}

	public TargetDummyEntityRenderState createRenderState() {
		return new TargetDummyEntityRenderState();
	}

	public void updateRenderState(TargetDummyEntity targetDummyEntity, TargetDummyEntityRenderState targetDummyEntityRenderState, float f) {
		super.updateRenderState(targetDummyEntity, targetDummyEntityRenderState, f);
		BipedEntityRenderer.updateBipedRenderState(targetDummyEntity, targetDummyEntityRenderState, f, this.itemModelResolver);
		targetDummyEntityRenderState.skinTextures = getSkin(targetDummyEntity);
		targetDummyEntityRenderState.isZombie = targetDummyEntity.getZombie();
		targetDummyEntityRenderState.yaw = MathHelper.lerpAngleDegrees(f, targetDummyEntity.lastYaw, targetDummyEntity.getYaw());
		targetDummyEntityRenderState.bodyRotation = targetDummyEntity.getBodyRotation();
		targetDummyEntityRenderState.headRotation = targetDummyEntity.getHeadRotation();
		targetDummyEntityRenderState.leftArmRotation = targetDummyEntity.getLeftArmRotation();
		targetDummyEntityRenderState.rightArmRotation = targetDummyEntity.getRightArmRotation();
		if (targetDummyEntityRenderState.isZombie) {
			if (targetDummyEntityRenderState.leftArmRotation.equals(DEFAULT_LEFT_ARM_ROTATION)) {
				targetDummyEntityRenderState.leftArmRotation = new EulerAngle(-90.0F, 0.0F, -5.0F);
			}
			if (targetDummyEntityRenderState.rightArmRotation.equals(DEFAULT_RIGHT_ARM_ROTATION)) {
				targetDummyEntityRenderState.rightArmRotation = new EulerAngle(-90.0F, 0.0F, 5.0F);
			}
		}

		targetDummyEntityRenderState.leftLegRotation = targetDummyEntity.getLeftLegRotation();
		targetDummyEntityRenderState.rightLegRotation = targetDummyEntity.getRightLegRotation();
		targetDummyEntityRenderState.timeSinceLastHit = (float)(targetDummyEntity.getEntityWorld().getTime() - targetDummyEntity.lastHitTime) + f;
	}

	public SkinTextures getSkin(TargetDummyEntity targetDummyEntity) {
		ProfileComponent p = targetDummyEntity.getTargetDummyProfile();
		if (p==null || p.equals(DEFAULT_INFO)) {
			return null;
		}
        return skinCache.get(p).getTextures();
	}

	public void render(
			TargetDummyEntityRenderState TargetDummyEntityRenderState,
			MatrixStack matrixStack,
			OrderedRenderCommandQueue orderedRenderCommandQueue,
			CameraRenderState cameraRenderState
	) {
		super.render(TargetDummyEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	protected void setupTransforms(TargetDummyEntityRenderState TargetDummyEntityRenderState, MatrixStack matrixStack, float f, float g) {
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - f));
		if (TargetDummyEntityRenderState.timeSinceLastHit < 5.0F) {
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.sin(TargetDummyEntityRenderState.timeSinceLastHit / 1.5F * (float) Math.PI) * 3.0F));
		}
	}

	protected boolean hasLabel(TargetDummyEntity TargetDummyEntity, double d) {
		return TargetDummyEntity.isCustomNameVisible();
	}

	@Nullable
	protected RenderLayer getRenderLayer(TargetDummyEntityRenderState TargetDummyEntityRenderState, boolean bl, boolean bl2, boolean bl3) {
			Identifier identifier = this.getTexture(TargetDummyEntityRenderState);
			if (bl2) {
				return RenderLayers.entityTranslucent(identifier, false);
			} else {
				return bl ? RenderLayers.entityCutoutNoCull(identifier, false) : null;
			}
	}
}
