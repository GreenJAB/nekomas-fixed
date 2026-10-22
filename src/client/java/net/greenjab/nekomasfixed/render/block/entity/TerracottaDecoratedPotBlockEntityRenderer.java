package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registries.SheetRegistry;
import net.greenjab.nekomasfixed.registry.block.TerracottaDecoratedPotBlock;
import net.greenjab.nekomasfixed.registry.block.entity.PotMaps;
import net.greenjab.nekomasfixed.registry.block.entity.TerracottaDecoratedPotBlockEntity;
import net.greenjab.nekomasfixed.registry.block.enums.SpriteFacing;
import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.other.PotFaceDecoration;
import net.greenjab.nekomasfixed.render.block.entity.state.TerracottaDecoratePotRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3fc;

public class TerracottaDecoratedPotBlockEntityRenderer implements BlockEntityRenderer<TerracottaDecoratedPotBlockEntity, TerracottaDecoratePotRenderState> {

    private static final Map<Direction, Transformation> TRANSFORMATIONS = Util.makeEnumMap(Direction.class, TerracottaDecoratedPotBlockEntityRenderer::createModelTransformation);
    public static final ModelLayerLocation CUSTOM_POT_SIDES = new ModelLayerLocation(Identifier.fromNamespaceAndPath("nekomasfixed", "custom_pot_sides"), "main");
    private static final Identifier CUSTOM_POT = Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/custom_pot.png");
    private static final Identifier TERRACOTTA_POT = Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/terracotta_decorated_pot.png");
    private static final Identifier VANILLA_POT = Identifier.withDefaultNamespace("textures/atlas/decorated_pot.png");

    private final SpriteGetter customPotSprites;
    private final ModelPart neck, frontSide, backSide, leftSide, rightSide;
    private final ModelPart frontPane, leftPane, rightPane, backPane;
    private final ModelPart top, bottom;

    public TerracottaDecoratedPotBlockEntityRenderer(final BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet(), context.sprites());
    }
    public TerracottaDecoratedPotBlockEntityRenderer(final SpecialModelRenderer.BakingContext context) {
        this(context.entityModelSet(), context.sprites());
    }

    public TerracottaDecoratedPotBlockEntityRenderer(final EntityModelSet entityModelSet, final SpriteGetter sprites) {
        PotMaps.register();
        ModelPart sidesRoot = entityModelSet.bakeLayer(CUSTOM_POT_SIDES);
        this.customPotSprites = Minecraft.getInstance().getAtlasManager();
        ModelPart baseRoot = entityModelSet.bakeLayer(ModelLayers.DECORATED_POT_BASE);
        this.neck = baseRoot.getChild("neck");
        this.top = baseRoot.getChild("top");
        this.bottom = baseRoot.getChild("bottom");
        this.frontSide = sidesRoot.getChild("front");
        this.backSide = sidesRoot.getChild("back");
        this.leftSide = sidesRoot.getChild("left");
        this.rightSide = sidesRoot.getChild("right");
        this.frontPane = sidesRoot.getChild("front_pane");
        this.leftPane = sidesRoot.getChild("left_pane");
        this.rightPane = sidesRoot.getChild("right_pane");
        this.backPane = sidesRoot.getChild("back_pane");
    }

    public static LayerDefinition createBaseLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeDeformation inflate = new CubeDeformation(0.2F);
        CubeDeformation deflate = new CubeDeformation(-0.1F);
        root.addOrReplaceChild(
                "neck",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(4.0F, 17.0F, 4.0F, 8.0F, 3.0F, 8.0F, deflate)
                        .texOffs(0, 5)
                        .addBox(5.0F, 20.0F, 5.0F, 6.0F, 1.0F, 6.0F, inflate),
                PartPose.offsetAndRotation(0.0F, 37.0F, 16.0F, (float) Math.PI, 0.0F, 0.0F)
        );
        CubeListBuilder topBottomPlane = CubeListBuilder.create().texOffs(-14, 13).addBox(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 14.0F);
        root.addOrReplaceChild("top", topBottomPlane, PartPose.offsetAndRotation(1.0F, 16.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("bottom", topBottomPlane, PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    public static LayerDefinition createSidesLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeListBuilder sidePlane = CubeListBuilder.create().texOffs(1, 0).addBox(0.0F, 0.0F, 0.0F, 14.0F, 16.0F, 0.0F, EnumSet.of(Direction.NORTH));
        root.addOrReplaceChild("front", sidePlane, PartPose.offsetAndRotation(15.0F, 16.0F, 1.0F, 0.0F, 0.0F, (float)Math.PI));
        root.addOrReplaceChild("back", sidePlane, PartPose.offsetAndRotation(1.0F, 16.0F, 15.0F, (float)Math.PI, 0.0F, 0.0F));
        root.addOrReplaceChild("left", sidePlane, PartPose.offsetAndRotation(1.0F, 16.0F, 1.0F, 0.0F, (-(float)Math.PI / 2F), (float)Math.PI));
        root.addOrReplaceChild("right", sidePlane, PartPose.offsetAndRotation(15.0F, 16.0F, 15.0F, 0.0F, ((float)Math.PI / 2F), (float)Math.PI));
        root.addOrReplaceChild("front_pane", sidePlane, PartPose.offsetAndRotation(14.9F+0.1F, 16.0F, 1.0F, 0.0F, 0.0F, (float)Math.PI));
        root.addOrReplaceChild("back_pane", sidePlane, PartPose.offsetAndRotation(1.0F, 16.0F, 14.9F+0.1F, (float)Math.PI, 0.0F, 0.0F));
        root.addOrReplaceChild("left_pane", sidePlane, PartPose.offsetAndRotation(1.0F, 16.0F, 1.0F, 0.0F, (-(float)Math.PI / 2F), (float)Math.PI));
        root.addOrReplaceChild("right_pane", sidePlane, PartPose.offsetAndRotation(14.9F + 0.1F, 16.0F, 15.0F, 0.0F, ((float)Math.PI / 2F), (float)Math.PI));
        return LayerDefinition.create(mesh, 16, 16);
    }

    private static Transformation createModelTransformation(final Direction entityDirection) {
        return new Transformation(new Matrix4f().rotateAround(Axis.YP.rotationDegrees(180.0F - entityDirection.toYRot()), 0.5F, 0.5F, 0.5F));
    }

    public static Transformation modelTransformation(final Direction facing) {
        return TRANSFORMATIONS.get(facing);
    }

    @Override
    public TerracottaDecoratePotRenderState createRenderState() {
        return new TerracottaDecoratePotRenderState();
    }

    @Override
    public void extractRenderState(final TerracottaDecoratedPotBlockEntity blockEntity, final TerracottaDecoratePotRenderState state, final float partialTicks, final Vec3 cameraPosition, final ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.decorations = blockEntity.getDecorations();
        state.engravedDecorations = blockEntity.getEngravingDecorations();
        state.direction = blockEntity.getDirection();
        state.potFace = blockEntity.getPotFace();
        if (blockEntity.getLevel() != null) {
            int blockLight = blockEntity.getLevel().getBrightness(LightLayer.BLOCK, blockEntity.getBlockPos());
            int skyLight = blockEntity.getLevel().getBrightness(LightLayer.SKY, blockEntity.getBlockPos());
            state.lightCoords = (skyLight << 20) | (blockLight << 4);
        }
        TerracottaDecoratedPotBlockEntity.WobbleStyle wobbleStyle = blockEntity.lastWobbleStyle;
        if (wobbleStyle != null && blockEntity.getLevel() != null) {
            state.wobbleProgress = ((float)(blockEntity.getLevel().getGameTime() - blockEntity.wobbleStartedAtTick) + partialTicks) / wobbleStyle.duration;
        } else {
            state.wobbleProgress = 0.0F;
        }
    }

    @Override
    public void submit(TerracottaDecoratePotRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.mulPose(modelTransformation(state.direction));
        if (state.wobbleProgress >= 0.0F && state.wobbleProgress <= 1.0F) {
            if (state.wobbleStyle == DecoratedPotBlockEntity.WobbleStyle.POSITIVE) {
                float deltaTime = state.wobbleProgress * (float) (Math.PI * 2);
                float tiltX = -1.5F * (Mth.cos(deltaTime) + 0.5F) * Mth.sin(deltaTime / 2.0F);
                poseStack.rotateAround(Axis.XP.rotation(tiltX * 0.015625F), 0.5F, 0.0F, 0.5F);
                float tiltZ = Mth.sin(deltaTime);
                poseStack.rotateAround(Axis.ZP.rotation(tiltZ * 0.015625F), 0.5F, 0.0F, 0.5F);
            } else {
                float turnAngle = Mth.sin(-state.wobbleProgress * 3.0F * (float) Math.PI) * 0.125F;
                float linearDecayFactor = 1.0F - state.wobbleProgress;
                poseStack.rotateAround(Axis.YP.rotation(turnAngle * linearDecayFactor), 0.5F, 0.0F, 0.5F);
            }
        }
        this.submit(state, poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    public void submit(TerracottaDecoratePotRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector,
                       final int lightCoords, final int overlayCoords, final int outlineColor) {

        RenderType renderTypeBase = SheetRegistry.TERRACOTTA_DECORATED_POT_BASE.renderType(RenderTypes::entitySolid);

        submitNodeCollector.submitModelPart(this.neck, poseStack, renderTypeBase, lightCoords, overlayCoords, null, -1, outlineColor);
        submitNodeCollector.submitModelPart(this.top, poseStack, renderTypeBase, lightCoords, overlayCoords, null, -1, outlineColor);
        submitNodeCollector.submitModelPart(this.bottom, poseStack, renderTypeBase, lightCoords, overlayCoords, null, -1, outlineColor);

        SpriteId potFace = new SpriteId(
                TERRACOTTA_POT,
                Identifier.fromNamespaceAndPath("nekomasfixed", "entity/terracotta_decorated_pot/terracotta_decorated_pot_" +
                        PotMaps.POT_FACE_TO_STRING_MAPPING.get(state.potFace.getSafeBlock()) + PotMaps.SpritePart.SIDE)
        );

        SpriteId frontSprite = getSpriteForSide(state, SpriteFacing.FRONT);
        SpriteId backSprite  = getSpriteForSide(state, SpriteFacing.BACK);
        SpriteId leftSprite  = getSpriteForSide(state, SpriteFacing.LEFT);
        SpriteId rightSprite = getSpriteForSide(state, SpriteFacing.RIGHT);

        RenderType potFaceRenderType = potFace.renderType(RenderTypes::entityCutout);
        submitNodeCollector.submitModelPart(this.leftPane, poseStack, potFaceRenderType, lightCoords, overlayCoords, null, -1, outlineColor);
        submitNodeCollector.submitModelPart(this.rightPane, poseStack, potFaceRenderType, lightCoords, overlayCoords, null, -1, outlineColor);
        submitNodeCollector.submitModelPart(this.backPane, poseStack, potFaceRenderType, lightCoords, overlayCoords, null, -1, outlineColor);
        submitNodeCollector.submitModelPart(this.frontPane, poseStack, potFaceRenderType, lightCoords, overlayCoords, null, -1, outlineColor);

        submitNodeCollector.submitModelPart(this.frontSide, poseStack, frontSprite.renderType(RenderTypes::entityTranslucent), lightCoords, overlayCoords, null, -1, outlineColor);
        submitNodeCollector.submitModelPart(this.backSide,  poseStack, backSprite.renderType(RenderTypes::entityTranslucent),  lightCoords, overlayCoords, null, -1, outlineColor);
        submitNodeCollector.submitModelPart(this.leftSide,  poseStack, leftSprite.renderType(RenderTypes::entityTranslucent),  lightCoords, overlayCoords, null, -1, outlineColor);
        submitNodeCollector.submitModelPart(this.rightSide, poseStack, rightSprite.renderType(RenderTypes::entityTranslucent), lightCoords, overlayCoords, null, -1, outlineColor);
    }

    private SpriteId spriteForVanillaSherd(String sherdName) {
        if(sherdName.equals("blank")){
            return new SpriteId(
                    VANILLA_POT,
                    Identifier.withDefaultNamespace("entity/decorated_pot/decorated_pot_side")
            );
        }
        return new SpriteId(
                VANILLA_POT,
                Identifier.withDefaultNamespace("entity/decorated_pot/" + sherdName + "_pottery_pattern")
        );
    }

    private SpriteId spriteForCustomPot(String sherdName, Item engravedItem) {
        String engravedName = PotMaps.ITEM_TO_STRING_MAPPING.get(engravedItem);
        return new SpriteId(
                CUSTOM_POT,
                Identifier.fromNamespaceAndPath("nekomasfixed", "trims/entity/pot/" + sherdName + "_" + engravedName)
        );
    }

    private SpriteId spriteForBlankSherd(String sherdName) {
        return new SpriteId(
                TERRACOTTA_POT,
                Identifier.fromNamespaceAndPath("nekomasfixed", "entity/terracotta_decorated_pot/terracotta_decorated_pot_blank_" + sherdName + "_pottery_pattern")
        );
    }

    private Item getEngravedItem(PotEngravingDecoration deco, SpriteFacing facing) {
        return switch (facing) {
            case FRONT -> deco.getFront();
            case BACK  -> deco.getBack();
            case LEFT  -> deco.getLeft();
            case RIGHT -> deco.getRight();
        };
    }

    private SpriteId getSpriteForSide(TerracottaDecoratePotRenderState state, SpriteFacing facing) {
        PotDecorations decorations = state.decorations;
        PotEngravingDecoration engravings = state.engravedDecorations;
        String sherdName = PotMaps.getSherdTexture(decorations).get(facing.ordinal);
        Item engravedItem = getEngravedItem(engravings, facing);
        boolean isBricks = state.potFace.getSafeBlock() == Blocks.BRICKS;

        if (engravedItem == Items.AIR) {
            if (isBricks) {
                return spriteForVanillaSherd(sherdName);
            } else {
                return spriteForBlankSherd(sherdName);
            }
        } else {
            return spriteForCustomPot(sherdName, engravedItem);
        }
    }

    public void getExtents(final Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        this.neck.getExtentsForGui(poseStack, output);
        this.top.getExtentsForGui(poseStack, output);
        this.bottom.getExtentsForGui(poseStack, output);
    }
}