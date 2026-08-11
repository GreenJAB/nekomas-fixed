package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.render.entity.state.MoobloomEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BabyModelTransformer;
import net.minecraft.client.render.entity.model.ModelTransformer;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;

import java.util.Set;

public class MoobloomEntityModel extends QuadrupedEntityModel<MoobloomEntityRenderState> {
    public static final ModelTransformer BABY_TRANSFORMER = new BabyModelTransformer(false, 8.0F, 6.0F, Set.of("head"));

    public MoobloomEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 64);
    }

    public static TexturedModelData getBabyTexturedModelData() {
        ModelData modelData = getModelData();
        modelData.transform(BABY_TRANSFORMER);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 2.0F));

        ModelPartData head = modelPartData.addChild(
                "head",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F)
                        .uv(1, 33)
                        .cuboid(-3.0F, 1.0F, -7.0F, 6.0F, 3.0F, 1.0F)
                        .uv(22, 0)
                        .cuboid("right_horn", -5.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F)
                        .uv(22, 0)
                        .cuboid("left_horn", 4.0F, -5.0F, -5.0F, 1.0F, 3.0F, 1.0F),
                ModelTransform.origin(0.0F, 4.0F, -8.0F)
        );
        ModelPartData body = modelPartData.addChild("body",ModelPartBuilder.create(),ModelTransform.origin(0.0F, 5.0F, 2.0F));
        modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4),ModelTransform.origin(-4.0F, 12.0F, 7.0F));
        modelPartData.addChild("left_hind_leg",ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4),ModelTransform.origin(4.0F, 12.0F, 7.0F));
        modelPartData.addChild("right_front_leg",ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4),ModelTransform.origin(-4.0F, 12.0F, -5.0F));
        modelPartData.addChild("left_front_leg",ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4),ModelTransform.origin(4.0F, 12.0F, -5.0F));

        ModelPartData rotation = body.addChild("rotation", ModelPartBuilder.create().uv(18, 4).cuboid(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, new Dilation(0.0F))
                .uv(52, 0).cuboid(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData flower1 = rotation.addChild("flower1", ModelPartBuilder.create().uv(0, 42).cuboid(-7.975F, -16.0F, 1.2F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.025F, -2.0F, 2.8F, -1.5708F, 0.0F, 0.0F));
        flower1.addChild("cube_r1", ModelPartBuilder.create().uv(0, 26).cuboid(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.025F, -8.0F, 1.2F, 0.0F, 3.1416F, 0.0F));

        ModelPartData flower2 = rotation.addChild("flower2", ModelPartBuilder.create().uv(0, 42).cuboid(-5.95F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(2.95F, 5.0F, 3.0F, -1.5708F, 0.0F, 0.7854F));
        flower2.addChild("cube_r2", ModelPartBuilder.create().uv(0, 26).cuboid(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(2.05F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        
        ModelPartData flower3 = head.addChild("flower3", ModelPartBuilder.create().uv(0, 42).cuboid(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, -3.2F, 0.0F, -0.576F, 0.0F));
        flower3.addChild("cube_r3", ModelPartBuilder.create().uv(0, 26).cuboid(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return modelData;
    }
}
