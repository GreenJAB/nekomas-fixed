package net.greenjab.nekomasfixed.render.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.greenjab.nekomasfixed.render.entity.feature.BasePlateFeatureRenderer;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyArmorModel;
import net.greenjab.nekomasfixed.render.entity.model.TargetDummyModel;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Environment(EnvType.CLIENT)
public class TargetDummyRenderer extends LivingEntityRenderer<TargetDummy, TargetDummyModel> {

    private static final ResourceLocation TEXTURE =
            NekomasFixed.id("textures/entity/target_dummy/default.png");
    private static final ResourceLocation ZOMBIE_TEXTURE =
            NekomasFixed.id("textures/entity/target_dummy/zombie.png");

    // Name-only profiles get resolved in two steps: name -> uuid, then uuid -> a full
    // texture-bearing profile, then skin-loaded. Cached so the future is started once.
    private static final ConcurrentHashMap<ResolvableProfile, CompletableFuture<PlayerSkin>> RESOLVING =
            new ConcurrentHashMap<>();

    public TargetDummyRenderer(EntityRendererProvider.Context context) {
        super(context, new TargetDummyModel(context.bakeLayer(ModEntityRendererRegistry.TARGET_DUMMY)), 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new TargetDummyArmorModel(context.bakeLayer(ModEntityRendererRegistry.TARGET_DUMMY_INNER_ARMOR)),
                new TargetDummyArmorModel(context.bakeLayer(ModEntityRendererRegistry.TARGET_DUMMY_OUTER_ARMOR)),
                context.getModelManager()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new BasePlateFeatureRenderer(this, context.getModelSet()));
    }

    // A profile that already carries a real UUID may have its texture properties (e.g. PLAYER_HEAD).
    // Await the real skin asynchronously; while pending, fall back to the vanilla default player
    // skin (getInsecureSkin) rather than snapshotting it forever.
    private static PlayerSkin loadedOrInsecure(GameProfile gp) {
        PlayerSkin skin = Minecraft.getInstance().getSkinManager().getOrLoad(gp)
                .exceptionally(ex -> null)
                .getNow(null);
        return skin != null ? skin : Minecraft.getInstance().getSkinManager().getInsecureSkin(gp);
    }

    private static PlayerSkin resolveSkin(ResolvableProfile profile) {
        if (profile == null) {
            return null;
        }
        if (profile.isResolved() && profile.id().isPresent()
                && !profile.id().get().equals(Util.NIL_UUID)) {
            GameProfile gp = profile.gameProfile();
            return loadedOrInsecure(gp);
        }
        CompletableFuture<PlayerSkin> future = RESOLVING.computeIfAbsent(profile, TargetDummyRenderer::resolveByName);
        return future.getNow(null);
    }

    /*
     * Name-only profile: the 1.21.1 name lookup (SkullBlockEntity.fetchGameProfile(String))
     * returns id+name but no "textures" property, so getInsecureSkin would give a default
     * skin. Resolve in two steps - name -> uuid, then uuid -> a full profile that does
     * carry textures (the uuid path goes through MinecraftSessionService.fetchProfile) - then
     * await the real skin through getOrLoad (which loads the CDN texture asynchronously).
     */
    private static CompletableFuture<PlayerSkin> resolveByName(ResolvableProfile profile) {
        String name = profile.name().orElse(null);
        if (name == null) {
            return CompletableFuture.completedFuture(null);
        }
        return SkullBlockEntity.fetchGameProfile(name)
                .thenCompose(idOpt -> {
                    if (idOpt.isEmpty()) {
                        return CompletableFuture.completedFuture(Optional.empty());
                    }
                    GameProfile idProfile = idOpt.get();
                    UUID id = idProfile.getId();
                    if (id == null || id.equals(Util.NIL_UUID)) {
                        return CompletableFuture.completedFuture(Optional.empty());
                    }
                    return SkullBlockEntity.fetchGameProfile(id);
                })
                .thenCompose(profileOpt -> {
                    if (profileOpt.isEmpty()) {
                        return CompletableFuture.completedFuture(null);
                    }
                    GameProfile gp = profileOpt.get();
                    return Minecraft.getInstance().getSkinManager().getOrLoad(gp)
                            .exceptionally(ex -> null);
                });
    }

    @Override
    public @NonNull ResourceLocation getTextureLocation(TargetDummy targetDummy) {
        if (targetDummy.isZombie()) {
            return ZOMBIE_TEXTURE;
        }
        PlayerSkin skin = resolveSkin(targetDummy.getTargetDummyProfile());
        return skin != null ? skin.texture() : TEXTURE;
    }

    @Override
    protected boolean shouldShowName(TargetDummy entity) {
        return entity.isCustomNameVisible();
    }

    // After the body renders, draw the last hit's damage as a floating number above the head
    // (main uses a networked NUMBER particle; 1.21.1 has no submitNameTag, so we sync the damage
    // value and anchor on the hit time the armstand-wobble event records client-side). Rises,
    // scales in and fades like main's particle, for min(20+damage*2, 50) ticks.
    @Override
    public void render(@NonNull TargetDummy entity, float entityYaw, float partialTicks, @NonNull PoseStack poseStack,
                       @NonNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        this.renderDamageText(entity, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderDamageText(TargetDummy entity, float partialTicks, PoseStack poseStack,
                                  MultiBufferSource buffer, int packedLight) {
        float damage = entity.getLastHitDamage();
        long hitAge = entity.level().getGameTime() - entity.getLastHitTime();
        int lifetime = (int) Math.min(20.0F + damage * 2.0F, 50);
        if (damage <= 0.0F || hitAge < 0L || hitAge >= lifetime) {
            return;
        }
        // Mirrors main's NumberParticleRenderer.State.create: the number sits at the spawn point
        // (world y+2 above the dummy) and rises 0.015/tick, growing with a sine ramp and fading
        // over the last 8 ticks; the sine is capped at age 8 (sin(1.6) ≈ 1.0) so it stays put.
        float t = (float) hitAge + partialTicks;
        float ageScale = Mth.sin(Math.min(t, 8.0F) / 5.0F) * Math.min(0.5F + damage / 10.0F, 2.0F);
        String text = String.format("%.1f", Math.round(damage * 10.0) / 10.0);
        if (text.charAt(text.length() - 1) == '0') {
            text = text.substring(0, text.length() - 2);
        }
        Component label = Component.literal(text);
        int fade = (int) (Math.clamp((lifetime - t) / 8.0F, 0.0F, 1.0F) * 255.0F);
        int color = (fade << 24) | 0xFEFFFF;
        poseStack.pushPose();
        // main scatters each spawn with Gaussian noise; the synced HIT_JITTER carries that offset
        // (x,z /5, y /10) so every viewer places the number identically.
        Vector3f jit = entity.getLastHitJitter();
        float yPos = 2.0F + t * 0.015F + jit.y();
        // Mirror main's matrix order exactly: place at feet+2, grow by ageScale, then lift +0.5
        // (which rides up with ageScale), then face the camera and apply the name-tag size scale.
        // Folding ageScale into the 0.025 scale and dropping the +0.5 lift made the number sit
        // pinned at the head instead of floating above it like main.
        poseStack.translate(jit.x(), yPos, jit.z());
        poseStack.scale(ageScale, ageScale, ageScale);
        poseStack.translate(0.0F, 0.5F, 0.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.scale(0.025F, -0.025F, 0.025F);
        Matrix4f matrix = poseStack.last().pose();
        Font font = this.getFont();
        // main's number sits behind a per-glyph drop shadow (its name-tag path shadows the
        // glyphs, not a flat box) — pass dropShadow=true so each digit has a dark copy behind it.
        font.drawInBatch(label, -font.width(label) / 2.0F,
                0.0F, color, true, matrix, buffer, Font.DisplayMode.NORMAL, 0, packedLight);
        poseStack.popPose();
    }

    @Override
    protected void setupRotations(@NonNull TargetDummy entity, @NonNull PoseStack matrixStack, float ageInTicks,
                                  float rotationYaw, float partialTick, float deltaTime) {
        super.setupRotations(entity, matrixStack, ageInTicks, rotationYaw, partialTick, deltaTime);
        float timeSinceLastHit = (float) (entity.level().getGameTime() - entity.getLastHitTime());
        if (timeSinceLastHit < 5.0F) {
            matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(timeSinceLastHit / 1.5F * (float) Math.PI) * 3.0F));
        }
    }
}