package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registries.SheetRegistry;
import net.greenjab.nekomasfixed.registry.block.TerracottaDecoratedPotBlock;
import net.greenjab.nekomasfixed.registry.block.entity.PotMaps;
import net.greenjab.nekomasfixed.registry.block.entity.TerracottaDecoratedPotBlockEntity;
import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
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
import java.util.Optional;
import java.util.function.Consumer;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
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
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3fc;

public class TerracottaDecoratedPotBlockEntityRenderer implements BlockEntityRenderer<TerracottaDecoratedPotBlockEntity, TerracottaDecoratePotRenderState> {

    private static final Map<Direction, Transformation> TRANSFORMATIONS = Util.makeEnumMap(Direction.class, TerracottaDecoratedPotBlockEntityRenderer::createModelTransformation);
    public static final ModelLayerLocation CUSTOM_POT_SIDES = new ModelLayerLocation(Identifier.fromNamespaceAndPath("nekomasfixed", "custom_pot_sides"), "main");

    private final SpriteGetter customPotSprites;

    private final ModelPart neck;
    private final ModelPart frontSide;
    private final ModelPart backSide;
    private final ModelPart leftSide;
    private final ModelPart rightSide;
    private final ModelPart frontPane;
    private final ModelPart leftPane;
    private final ModelPart rightPane;
    private final ModelPart backPane;
    private final ModelPart top;
    private final ModelPart bottom;

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
        root.addOrReplaceChild("front_pane", sidePlane, PartPose.offsetAndRotation(14.9F, 16.0F, 1.1F, 0.0F, 0.0F, (float)Math.PI));
        root.addOrReplaceChild("back_pane", sidePlane, PartPose.offsetAndRotation(1.0F, 16.0F, 14.9F, (float)Math.PI, 0.0F, 0.0F));
        root.addOrReplaceChild("left_pane", sidePlane, PartPose.offsetAndRotation(1.1F, 16.0F, 1.0F, 0.0F, (-(float)Math.PI / 2F), (float)Math.PI));
        root.addOrReplaceChild("right_pane", sidePlane, PartPose.offsetAndRotation(14.9F, 16.0F, 15.0F, 0.0F, ((float)Math.PI / 2F), (float)Math.PI));

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
                float amplitude = 0.015625F;
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
        this.submit(state, poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY,  0);
        poseStack.popPose();
    }



    public void submit(TerracottaDecoratePotRenderState state,final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final int overlayCoords,  final int outlineColor) {

        RenderType renderTypeBase = SheetRegistry.TERRACOTTA_DECORATED_POT_BASE.renderType(RenderTypes::entitySolid);

        SpriteId potBase = new SpriteId(
                Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/terracotta_decorated_pot.png"),
                Identifier.fromNamespaceAndPath("nekomasfixed", "entity/terracotta_decorated_pot/terracotta_decorated_pot_"+PotMaps.POT_FACE_TO_STRING_MAPPING.get(state.potFace.getSafeBlock())+PotMaps.SpritePart.BASE)
        );

        TextureAtlasSprite spriteBase = this.customPotSprites.get(potBase);

        submitNodeCollector.submitModelPart(this.neck, poseStack, renderTypeBase, lightCoords, overlayCoords, spriteBase, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.top, poseStack, renderTypeBase, lightCoords, overlayCoords, spriteBase, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.bottom, poseStack, renderTypeBase, lightCoords, overlayCoords, spriteBase, -1, null, outlineColor);

        SpriteId potFace = new SpriteId(
                Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/terracotta_decorated_pot.png"),
                Identifier.fromNamespaceAndPath("nekomasfixed", "entity/terracotta_decorated_pot/terracotta_decorated_pot_"+PotMaps.POT_FACE_TO_STRING_MAPPING.get(state.potFace.getSafeBlock())+PotMaps.SpritePart.SIDE)
        );

        TextureAtlasSprite potSprite = this.customPotSprites.get(potFace);

        SpriteId rightSprite = this.getSpriteForPotEngravings(state, SpriteFacing.RIGHT);
        SpriteId leftSprite = this.getSpriteForPotEngravings(state, SpriteFacing.LEFT);
        SpriteId frontSprite = this.getSpriteForPotEngravings(state, SpriteFacing.FRONT);
        SpriteId backSprite = this.getSpriteForPotEngravings(state, SpriteFacing.BACK);


        TextureAtlasSprite frontText = this.customPotSprites.get(frontSprite);
        TextureAtlasSprite backText = this.customPotSprites.get(backSprite);
        TextureAtlasSprite leftText = this.customPotSprites.get(leftSprite);
        TextureAtlasSprite rightText = this.customPotSprites.get(rightSprite);


        if (frontText == null || leftText == null || backText == null || rightText == null) {
            return;
        }


        submitNodeCollector.submitModelPart(this.leftPane, poseStack, potFace.renderType(RenderTypes::entityCutout), lightCoords, overlayCoords, potSprite, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.rightPane, poseStack,potFace.renderType(RenderTypes::entityCutout), lightCoords, overlayCoords, potSprite, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.backPane, poseStack, potFace.renderType(RenderTypes::entityCutout), lightCoords, overlayCoords, potSprite, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.frontPane, poseStack, potFace.renderType(RenderTypes::entityCutout), lightCoords, overlayCoords, potSprite, -1, null, outlineColor);

        submitNodeCollector.submitModelPart(this.frontSide, poseStack, frontSprite.renderType(RenderTypes::entityCutout), lightCoords, overlayCoords, frontText, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.backSide, poseStack, backSprite.renderType(RenderTypes::entityCutout), lightCoords, overlayCoords, backText, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.leftSide, poseStack, leftSprite.renderType(RenderTypes::entityCutout), lightCoords, overlayCoords, leftText, -1, null, outlineColor);
        submitNodeCollector.submitModelPart(this.rightSide, poseStack, rightSprite.renderType(RenderTypes::entityCutout), lightCoords, overlayCoords, rightText, -1, null, outlineColor);
    }

    private SpriteId getSpriteForPotEngravings(TerracottaDecoratePotRenderState state, SpriteFacing facing) {
        PotEngravingDecoration decorations = state.engravedDecorations;
        return switch (facing) {
            case FRONT -> decorations.getFront().equals(Items.AIR) ?
                    new SpriteId(
                            Identifier.withDefaultNamespace("textures/atlas/decorated_pot.png"),
                            Identifier.withDefaultNamespace(getSideFaceString(state, facing))
                    ) : new SpriteId(
                    Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/custom_pot.png"),
                    Identifier.fromNamespaceAndPath("nekomasfixed", "trims/entity/pot/" + PotMaps.getSherdTexture(state.decorations).get(facing.ordinal) + "_" + PotMaps.ITEM_TO_STRING_MAPPING.get(state.engravedDecorations.getFront()))
            );

            case BACK ->
                    decorations.getBack().equals(Items.AIR) ? new SpriteId(
                            Identifier.withDefaultNamespace("textures/atlas/decorated_pot.png"),
                            Identifier.withDefaultNamespace(getSideFaceString(state, facing))) :
                            new SpriteId(
                                    Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/custom_pot.png"),
                                    Identifier.fromNamespaceAndPath("nekomasfixed", "trims/entity/pot/" + PotMaps.getSherdTexture(state.decorations).get(facing.ordinal) + "_" + PotMaps.ITEM_TO_STRING_MAPPING.get(state.engravedDecorations.getBack())));
            case LEFT ->
                    decorations.getLeft().equals(Items.AIR) ?
                            new SpriteId(
                                    Identifier.withDefaultNamespace("textures/atlas/decorated_pot.png"),
                                    Identifier.withDefaultNamespace(getSideFaceString(state, facing))) :
                            new SpriteId(
                                    Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/custom_pot.png"),
                                    Identifier.fromNamespaceAndPath("nekomasfixed", "trims/entity/pot/" + PotMaps.getSherdTexture(state.decorations).get(facing.ordinal) + "_" + PotMaps.ITEM_TO_STRING_MAPPING.get(state.engravedDecorations.getLeft())));
            case RIGHT ->
                    decorations.getRight().equals(Items.AIR) ?
                            new SpriteId(
                                    Identifier.withDefaultNamespace("textures/atlas/decorated_pot.png"),
                                    Identifier.withDefaultNamespace(getSideFaceString(state, facing))) :
                            new SpriteId(
                                    Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/custom_pot.png"),
                                    Identifier.fromNamespaceAndPath("nekomasfixed", "trims/entity/pot/" + PotMaps.getSherdTexture(state.decorations).get(facing.ordinal) + "_" + PotMaps.ITEM_TO_STRING_MAPPING.get(state.engravedDecorations.getRight())));
        };
    }


    private String getSideFaceString(TerracottaDecoratePotRenderState state, SpriteFacing facing){
        String str = PotMaps.getSherdTexture(state.decorations).get(facing.ordinal);
        if(str!= null && str.equals("blank")){
            return "entity/decorated_pot/decorated_pot_side";
        }else{
            return "entity/decorated_pot/"+PotMaps.getSherdTexture(state.decorations).get(facing.ordinal)+"_pottery_pattern";
        }
    }

    public void getExtents(final Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        this.neck.getExtentsForGui(poseStack, output);
        this.top.getExtentsForGui(poseStack, output);
        this.bottom.getExtentsForGui(poseStack, output);
    }

    public enum SpriteFacing {
        BACK(0), LEFT(1), RIGHT(2), FRONT(3);
        int ordinal;
        SpriteFacing(int ord) { this.ordinal = ord; }
    }
}