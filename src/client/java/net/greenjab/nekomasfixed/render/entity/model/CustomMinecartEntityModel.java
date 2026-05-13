package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.render.entity.state.CustomMinecartEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.render.entity.state.MinecartEntityRenderState;

public class CustomMinecartEntityModel extends MinecartEntityModel {

    public final ModelPart wheel1;
    public final ModelPart wheel2;
    public final ModelPart wheel3;
    public final ModelPart wheel4;

    public CustomMinecartEntityModel(ModelPart root) {
        super(root);

        ModelPart group = root.getChild("group");
        ModelPart wheels = group.getChild("wheels");
        this.wheel1 = wheels.getChild("wheel1");
        this.wheel2 = wheels.getChild("wheel2");
        this.wheel3 = wheels.getChild("wheel3");
        this.wheel4 = wheels.getChild("wheel4");

    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData group = modelPartData.addChild("group", ModelPartBuilder.create(), ModelTransform.of(0.0F, -4F, 0.0F, 0.0F, 0F, 0.0F));

        group.addChild("base", ModelPartBuilder.create().uv(19, 17).cuboid(-11.0F, -1.0F, -9.0F, 22.0F, 1.0F, 18.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 6.0556F, 0.0F));

        ModelPartData top = group.addChild("top", ModelPartBuilder.create().uv(9, 4).cuboid(-10.4544F, -4.1927F, 7.4385F, 22.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(82, 10).cuboid(9.5456F, -4.1927F, -6.5615F, 2.0F, 2.0F, 14.0F, new Dilation(0.0F))
                .uv(9, 0).cuboid(-10.4544F, -4.1927F, -8.5615F, 22.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5456F, 0.2482F, -0.4385F));

        top.addChild("cube_r1", ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, -3.5F, -1.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(0, 5).cuboid(-19.5F, -3.5F, -1.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(9.7956F, 0.3073F, -6.5615F, 0.0873F, 0.0F, 0.0F));

        top.addChild("cube_r2", ModelPartBuilder.create().uv(7, 3).cuboid(-0.5F, -3.5F, -7.0F, 1.0F, 7.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(9.7456F, 1.3073F, 0.4385F, 0.0F, 0.0F, 0.0873F));

        top.addChild("cube_r3", ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, -3.5F, 0.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(0, 5).cuboid(-19.5F, -3.5F, 0.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(9.7956F, 0.3073F, 7.4385F, -0.0873F, 0.0F, 0.0F));

        top.addChild("cube_r4", ModelPartBuilder.create().uv(0, 5).cuboid(0.0F, -3.5F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(0, 5).cuboid(-19.5F, -3.5F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(9.7956F, 0.3073F, 6.2385F, -0.0873F, 0.0F, 0.0F));

        top.addChild("cube_r5", ModelPartBuilder.create().uv(27, 8).cuboid(-10.0F, -3.5F, -0.5F, 20.0F, 7.0F, 1.0F, new Dilation(0.01F)), ModelTransform.of(0.5456F, 1.2573F, 7.4885F, -0.1309F, 0.0F, 0.0F));

        top.addChild("cube_r6", ModelPartBuilder.create().uv(7, 3).mirrored().cuboid(-0.5F, -3.5F, -7.0F, 1.0F, 7.0F, 14.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-8.6794F, 1.3073F, 0.4385F, 0.0F, 0.0F, -0.0873F));

        top.addChild("cube_r7", ModelPartBuilder.create().uv(0, 5).cuboid(0.0F, -3.5F, -1.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(0, 5).cuboid(-19.5F, -3.5F, -1.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(9.7956F, 0.3073F, -5.3615F, 0.0873F, 0.0F, 0.0F));

        top.addChild("cube_r8", ModelPartBuilder.create().uv(6, 8).cuboid(-10.0F, -3.5F, -0.5F, 20.0F, 7.0F, 1.0F, new Dilation(0.01F)), ModelTransform.of(0.5456F, 1.2573F, -6.6115F, 0.1309F, 0.0F, 0.0F));

        top.addChild("front_tile", ModelPartBuilder.create().uv(82, 10).cuboid(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 14.0F, new Dilation(0.0F)), ModelTransform.origin(-9.4544F, -3.1927F, 0.4385F));

        ModelPartData chain = group.addChild("chain", ModelPartBuilder.create(), ModelTransform.origin(-2.4375F, 4.7569F, 0.5F));

        chain.addChild("chain_w_hook", ModelPartBuilder.create().uv(10, 22).mirrored().cuboid(-3.875F, -0.5F, 0.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(12, 22).cuboid(-3.875F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 22).mirrored().cuboid(-3.875F, -0.5F, -1.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(12, 22).cuboid(-1.875F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(-6.6875F, -0.2569F, 0.0F));

        ModelPartData separate_chain = chain.addChild("separate_chain", ModelPartBuilder.create(), ModelTransform.origin(13.6042F, 0.7431F, 0.0F));

        separate_chain.addChild("cube_r9", ModelPartBuilder.create().uv(11, 22).mirrored().cuboid(-0.75F, -0.5F, -1.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(12, 22).cuboid(-0.75F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(11, 22).mirrored().cuboid(-0.75F, -0.5F, 0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0833F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        chain.addChild("hook", ModelPartBuilder.create().uv(0, 15).cuboid(-0.5F, 0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 15).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.5F, 2.0F, new Dilation(0.0F))
                .uv(0, 15).cuboid(-0.5F, -1.5F, -1.0F, 1.0F, 0.5F, 2.0F, new Dilation(0.0F))
                .uv(1, 15).mirrored().cuboid(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F)).mirrored(false)
                .uv(1, 15).mirrored().cuboid(-0.5F, -1.0F, 0.5F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.0625F, -1.2014F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData wheels = group.addChild("wheels", ModelPartBuilder.create(), ModelTransform.origin(6.5F, 8.5556F, 5.1667F));

        wheels.addChild("wheel1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -0.5F, -2.1667F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        wheels.addChild("wheel2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -0.5F, -2.1667F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(-13.0F, 0.0F, 0.0F));

        wheels.addChild("wheel3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -0.5F, -0.1667F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).mirrored().cuboid(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-13.0F, 0.0F, -11.0F));

        wheels.addChild("wheel4", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -0.5F, -0.1667F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, -11.0F));

        group.addChild("foundation", ModelPartBuilder.create().uv(58, 0).cuboid(-10.0F, -1.0F, -3.0F, 20.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 7.0556F, 0.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    public void setAngles(MinecartEntityRenderState state) {
        super.setAngles(state);
        if (state instanceof CustomMinecartEntityRenderState c) {
            double rotation = c.wheelRotation;
            float rotationRadians = (float) Math.toRadians(rotation);

            this.wheel1.roll = rotationRadians;
            this.wheel2.roll = rotationRadians;
            this.wheel3.roll = rotationRadians;
            this.wheel4.roll = rotationRadians;
        }
    }
}
