package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.greenjab.nekomasfixed.render.entity.feature.BasePlateFeatureRenderer;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyArmorEntityModel;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyEntityModel;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyEntityRenderState;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.Rotations;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity.*;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

@Environment(EnvType.CLIENT)
public class TargetDummyEntityRenderer extends LivingEntityRenderer<TargetDummyEntity, TargetDummyEntityRenderState, TargetDummyArmorEntityModel> {
	private final PlayerSkinRenderCache skinCache;
	private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/target_dummy/default.png");
	private static final Identifier ZOMBIE_TEXTURE = NekomasFixed.id("textures/entity/target_dummy/zombie.png");

	public TargetDummyEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new TargetDummyEntityModel(context.bakeLayer(ModEntityLayerRegistry.TARGET_DUMMY)), 0.0F);
		this.skinCache = context.getPlayerSkinRenderCache();
		this.addLayer(
				new HumanoidArmorLayer<>(
						this,
						ArmorModelSet.bake(ModEntityLayerRegistry.TARGET_DUMMY_EQUIPMENT, context.getModelSet(), TargetDummyArmorEntityModel::new),
						context.getEquipmentRenderer()
				)
		);

		this.addLayer(new ItemInHandLayer<>(this));
		this.addLayer(new WingsLayer<>(this, context.getModelSet(), context.getEquipmentRenderer()));
		this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getPlayerSkinRenderCache()));
		this.addLayer(new BasePlateFeatureRenderer(this, context.getModelSet()));
	}

	public @NonNull Identifier getTextureLocation(TargetDummyEntityRenderState TargetDummyEntityRenderState) {
		if (TargetDummyEntityRenderState.skinTextures == null){
			return TargetDummyEntityRenderState.isZombie?ZOMBIE_TEXTURE:TEXTURE;
		}
		return TargetDummyEntityRenderState.skinTextures.body().texturePath();
	}

	public @NonNull TargetDummyEntityRenderState createRenderState() {
		return new TargetDummyEntityRenderState();
	}

	public void extractRenderState(@NonNull TargetDummyEntity targetDummyEntity, @NonNull TargetDummyEntityRenderState targetDummyEntityRenderState, float f) {
		super.extractRenderState(targetDummyEntity, targetDummyEntityRenderState, f);
		HumanoidMobRenderer.extractHumanoidRenderState(targetDummyEntity, targetDummyEntityRenderState, f, this.itemModelResolver);
		targetDummyEntityRenderState.skinTextures = getSkin(targetDummyEntity);
		targetDummyEntityRenderState.isZombie = targetDummyEntity.isZombie();
		targetDummyEntityRenderState.yaw = Mth.rotLerp(f, targetDummyEntity.yRotO, targetDummyEntity.getYRot());
		targetDummyEntityRenderState.bodyRotation = targetDummyEntity.getBodyRotation();
		targetDummyEntityRenderState.headRotation = targetDummyEntity.getHeadRotation();
		targetDummyEntityRenderState.leftArmRotation = targetDummyEntity.getLeftArmRotation();
		targetDummyEntityRenderState.rightArmRotation = targetDummyEntity.getRightArmRotation();
		if (targetDummyEntityRenderState.isZombie) {
			if (targetDummyEntityRenderState.leftArmRotation.equals(DEFAULT_LEFT_ARM_ROTATION)) {
				targetDummyEntityRenderState.leftArmRotation = new Rotations(-90.0F, 0.0F, -5.0F);
			}
			if (targetDummyEntityRenderState.rightArmRotation.equals(DEFAULT_RIGHT_ARM_ROTATION)) {
				targetDummyEntityRenderState.rightArmRotation = new Rotations(-90.0F, 0.0F, 5.0F);
			}
		}

		targetDummyEntityRenderState.leftLegRotation = targetDummyEntity.getLeftLegRotation();
		targetDummyEntityRenderState.rightLegRotation = targetDummyEntity.getRightLegRotation();
		targetDummyEntityRenderState.timeSinceLastHit = (float)(targetDummyEntity.level().getGameTime() - targetDummyEntity.lastHitTime) + f;
	}

	public PlayerSkin getSkin(TargetDummyEntity targetDummyEntity) {
		ResolvableProfile p = targetDummyEntity.getTargetDummyProfile();
		if (p==null || p.equals(DEFAULT_INFO)) {
			return null;
		}
        return skinCache.getOrDefault(p).playerSkin();
	}

	public void submit(
            @NonNull TargetDummyEntityRenderState TargetDummyEntityRenderState,
            @NonNull PoseStack matrixStack,
            @NonNull SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		super.submit(TargetDummyEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	protected void setupRotations(TargetDummyEntityRenderState TargetDummyEntityRenderState, PoseStack matrixStack, float f, float g) {
		matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - f));
		if (TargetDummyEntityRenderState.timeSinceLastHit < 5.0F) {
			matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(TargetDummyEntityRenderState.timeSinceLastHit / 1.5F * (float) Math.PI) * 3.0F));
		}
	}

	protected boolean shouldShowName(TargetDummyEntity TargetDummyEntity, double d) {
		return TargetDummyEntity.isCustomNameVisible();
	}

	@Nullable
	protected RenderType getRenderType(@NonNull TargetDummyEntityRenderState TargetDummyEntityRenderState, boolean bl, boolean bl2, boolean bl3) {
			Identifier identifier = this.getTextureLocation(TargetDummyEntityRenderState);
			if (bl2) {
				return RenderTypes.entityTranslucent(identifier, false);
			} else {
				return bl ? RenderTypes.entityCutoutNoCull(identifier, false) : null;
			}
	}
}
