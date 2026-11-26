package net.greenjab.nekomasfixed.render.entity.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.registry.item.GoatHornHelmetItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class GoatHornHelmetLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final ModelPart helmet;
    private final ModelPart helmetTrim;
    private final TextureAtlas armorTrimAtlas;

    public GoatHornHelmetLayer(RenderLayerParent<T, M> context, EntityModelSet entityModels, TextureAtlas armorTrimAtlas) {
        super(context);
        this.helmet = entityModels.bakeLayer(ModEntityRendererRegistry.GOAT_HORN_HELMET).getChild("head").getChild("Helmet");
        this.helmetTrim = entityModels.bakeLayer(ModEntityRendererRegistry.GOAT_HORN_HELMET_TRIM).getChild("head").getChild("Helmet");
        this.armorTrimAtlas = armorTrimAtlas;
    }

    @Override
    public void render(@NonNull PoseStack poseStack, @NonNull MultiBufferSource buffer, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!(stack.getItem() instanceof GoatHornHelmetItem item)) {
            return;
        }
        int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
        poseStack.pushPose();
        this.getParentModel().head.translateAndRotate(poseStack);
        this.helmet.render(poseStack, buffer.getBuffer(RenderType.entityCutout(item.tier.texture)), packedLight, overlay);

        ArmorTrim trim = stack.get(DataComponents.TRIM);
        if (trim != null) {
            TextureAtlasSprite sprite = this.armorTrimAtlas.getSprite(trim.outerTexture(item.tier.material));
            poseStack.scale(1.001F, 1.001F, 1.001F);
            this.helmetTrim.render(poseStack,
                    sprite.wrap(buffer.getBuffer(Sheets.armorTrimsSheet(trim.pattern().value().decal()))),
                    packedLight, overlay);
        }
        poseStack.popPose();
    }
}
