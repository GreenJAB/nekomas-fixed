package net.greenjab.nekomasfixed.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.greenjab.nekomasfixed.render.entity.feature.BasePlateFeatureRenderer;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyArmorModel;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyModel;
import net.greenjab.nekomasfixed.render.entity.state.TargetDummyRenderState;
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
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Rotations;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import static net.greenjab.nekomasfixed.registry.entity.TargetDummy.*;

@Environment(EnvType.CLIENT)
public class TargetDummyEntityRenderer extends LivingEntityRenderer<TargetDummy, TargetDummyRenderState, TargetDummyArmorModel> {
	private final PlayerSkinRenderCache skinCache;
	private static final Identifier TEXTURE = NekomasFixed.id("textures/entity/target_dummy/default.png");
	private static final Identifier ZOMBIE_TEXTURE = NekomasFixed.id("textures/entity/target_dummy/zombie.png");

	public TargetDummyEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new TargetDummyModel(context.bakeLayer(ModModelLayerRegistry.TARGET_DUMMY)), 0.0F);
		this.skinCache = context.getPlayerSkinRenderCache();
		this.addLayer(
				new HumanoidArmorLayer<>(
						this,
						ArmorModelSet.bake(ModModelLayerRegistry.TARGET_DUMMY_EQUIPMENT, context.getModelSet(), TargetDummyArmorModel::new),
						context.getEquipmentRenderer()
				)
		);

		this.addLayer(new ItemInHandLayer<>(this));
		this.addLayer(new WingsLayer<>(this, context.getModelSet(), context.getEquipmentRenderer()));
		this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getPlayerSkinRenderCache()));
		this.addLayer(new BasePlateFeatureRenderer(this, context.getModelSet()));
	}

	public @NonNull Identifier getTextureLocation(TargetDummyRenderState TargetDummyRenderState) {
		if (TargetDummyRenderState.skinTextures == null) return TargetDummyRenderState.isZombie?ZOMBIE_TEXTURE:TEXTURE;
		return TargetDummyRenderState.skinTextures.body().texturePath();
	}

	public @NonNull TargetDummyRenderState createRenderState() {
		return new TargetDummyRenderState();
	}

	public void extractRenderState(@NonNull TargetDummy targetDummy, @NonNull TargetDummyRenderState targetDummyRenderState, float f) {
		super.extractRenderState(targetDummy, targetDummyRenderState, f);
		HumanoidMobRenderer.extractHumanoidRenderState(targetDummy, targetDummyRenderState, f, this.itemModelResolver);
		targetDummyRenderState.skinTextures = getSkin(targetDummy);
		targetDummyRenderState.isZombie = targetDummy.isZombie();
		targetDummyRenderState.yaw = Mth.rotLerp(f, targetDummy.yRotO, targetDummy.getYRot());
		targetDummyRenderState.bodyRotation = targetDummy.getBodyRotation();
		targetDummyRenderState.headRotation = targetDummy.getHeadRotation();
		targetDummyRenderState.leftArmRotation = targetDummy.getLeftArmRotation();
		targetDummyRenderState.rightArmRotation = targetDummy.getRightArmRotation();
		if (targetDummyRenderState.isZombie) {
			if (targetDummyRenderState.leftArmRotation.equals(DEFAULT_LEFT_ARM_ROTATION))
				targetDummyRenderState.leftArmRotation = new Rotations(-90.0F, 0.0F, -5.0F);
			if (targetDummyRenderState.rightArmRotation.equals(DEFAULT_RIGHT_ARM_ROTATION))
				targetDummyRenderState.rightArmRotation = new Rotations(-90.0F, 0.0F, 5.0F);
		}

		targetDummyRenderState.leftLegRotation = targetDummy.getLeftLegRotation();
		targetDummyRenderState.rightLegRotation = targetDummy.getRightLegRotation();
		targetDummyRenderState.timeSinceLastHit = (float)(targetDummy.level().getGameTime() - targetDummy.lastHitTime) + f;
	}

	public PlayerSkin getSkin(TargetDummy targetDummy) {
		ResolvableProfile p = targetDummy.getTargetDummyProfile();
		if (p==null || p.equals(DEFAULT_INFO)) return null;
        return skinCache.getOrDefault(p).playerSkin();
	}

	public void submit(
            @NonNull TargetDummyRenderState TargetDummyRenderState,
            @NonNull PoseStack matrixStack,
            @NonNull SubmitNodeCollector orderedRenderCommandQueue,
            @NonNull CameraRenderState cameraRenderState
	) {
		super.submit(TargetDummyRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	protected void setupRotations(TargetDummyRenderState TargetDummyRenderState, PoseStack matrixStack, float f, float g) {
		matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - f));
		if (TargetDummyRenderState.timeSinceLastHit < 5.0F)
			matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(TargetDummyRenderState.timeSinceLastHit / 1.5F * (float) Math.PI) * 3.0F));
	}

	protected boolean shouldShowName(TargetDummy TargetDummy, double d) {
		return TargetDummy.isCustomNameVisible();
	}

	@Nullable
	protected RenderType getRenderType(@NonNull TargetDummyRenderState TargetDummyRenderState, boolean bl, boolean bl2, boolean bl3) {
			Identifier identifier = this.getTextureLocation(TargetDummyRenderState);
			if (bl2) return RenderTypes.entityTranslucent(identifier, false);
			else return bl ? RenderTypes.entityCutout(identifier, false) : null;
	}
}
