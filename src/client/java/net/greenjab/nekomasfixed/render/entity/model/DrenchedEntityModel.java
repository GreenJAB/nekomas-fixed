package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.render.entity.state.DrenchedEntityRenderState;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;

@Environment(EnvType.CLIENT)
public class DrenchedEntityModel extends SkeletonEntityModel<DrenchedEntityRenderState> {

    private final ModelPart bone2;

    public DrenchedEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.bone2 = modelPart.getChild("head").getChild("bone2");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();

        SkeletonEntityModel.addLimbs(modelPartData);

        ModelPartData head = modelPartData.getChild("head");
        ModelPartData bone2Data = head.addChild("bone2", ModelPartBuilder.create(), ModelTransform.NONE);

        bone2Data.addChild("plane_1",
                ModelPartBuilder.create().uv(33, 1).cuboid(-4.0F, -9.0F, 0.0F, 8.0F, 9.0F, 0.0F),
                ModelTransform.of(0.0F, -8.0F, 0.0F, 0.0F, ((float)Math.PI / 4F), 0.0F)
        );

        bone2Data.addChild("plane_2",
                ModelPartBuilder.create().uv(33, 1).cuboid(-4.0F, -9.0F, 0.0F, 8.0F, 9.0F, 0.0F),
                ModelTransform.of(0.0F, -8.0F, 0.0F, 0.0F, -((float)Math.PI / 4F), 0.0F)
        );

        return TexturedModelData.of(modelData, 128, 128);
    }
}