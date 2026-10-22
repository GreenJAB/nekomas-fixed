package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.item.GoatHornHelmetItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.palette.PalettedTextureManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class GoatHornHelmetLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>>
        extends RenderLayer<S, M> {

    private final ModelPart helmetPart;
    private final ModelPart helmetPartTrim;
    private final PalettedTextureManager palettedTextures;

    public GoatHornHelmetLayer(RenderLayerParent<S, M> context, EntityModelSet entityModels) {
        super(context);
        ModelPart baked = entityModels.bakeLayer(ModModelLayerRegistry.GOAT_HORN_HELMET);
        this.helmetPart = baked.getChild("head").getChild("Helmet");

        ModelPart bakedTrim = entityModels.bakeLayer(ModModelLayerRegistry.GOAT_HORN_HELMET_TRIM);
        this.helmetPartTrim = bakedTrim.getChild("head").getChild("Helmet");

        this.palettedTextures = Minecraft.getInstance().getPalettedTextureManager();
    }

    @Override
    public void submit(@NonNull PoseStack poseStack,
                       @NonNull SubmitNodeCollector submitNodeCollector,
                       int light,
                       @NonNull S state,
                       float yRot,
                       float xRot) {
        if (state.isInvisible) return;
        ItemStack headStack = state.headEquipment;
        if (!(headStack.getItem() instanceof GoatHornHelmetItem helmet)) return;

        M parentModel = this.getParentModel();

        poseStack.pushPose();
        parentModel.root().translateAndRotate(poseStack);
        parentModel.head.translateAndRotate(poseStack);

        int overlay = LivingEntityRenderer.getOverlayCoords(state, 0.0F);
		
        Identifier texture = helmet.getTier().getTexture().withSuffix(".png");
        RenderType renderType = RenderTypes.entityCutout(texture);

        submitNodeCollector.order(0).submitModelPart(
                this.helmetPart,
                poseStack,
                renderType,
                light,
                overlay,
                null,
                -1,
                state.outlineColor
        );
		
        ArmorTrim trim = headStack.get(DataComponents.TRIM);
        if (trim != null) {
            TrimPattern pattern = trim.pattern().value();
            TrimMaterial material = trim.material().value();

            Identifier patternAsset = pattern.assetId();
            Identifier baseTexture = Identifier.fromNamespaceAndPath(
                    patternAsset.getNamespace(),
                    "trims/entity/humanoid/" + patternAsset.getPath());

            PalettedTextureManager.Handle handle =
                    this.palettedTextures.getOrPrepare(baseTexture, material.paletteId());
            Identifier trimTexture = handle.textureLocation();

            poseStack.pushPose();
            poseStack.scale(1.001F, 1.001F, 1.001F);

            RenderType trimRenderType = RenderTypes.armorTrim(trimTexture, pattern.decal());

            submitNodeCollector.order(1).submitModelPart(
                    this.helmetPartTrim,
                    poseStack,
                    trimRenderType,
                    light,
                    overlay,
                    handle,
                    -1,
                    state.outlineColor
            );

            poseStack.popPose();
        }

        poseStack.popPose();
    }
}