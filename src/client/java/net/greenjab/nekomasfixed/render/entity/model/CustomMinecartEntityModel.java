package net.greenjab.nekomasfixed.render.entity.model;

import net.greenjab.nekomasfixed.render.entity.state.CustomMinecartEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class CustomMinecartEntityModel extends EntityModel<CustomMinecartEntityRenderState> {
    public static final EntityModelLayer CUSTOM_MINECART = new EntityModelLayer(Identifier.of("nekomasfixed", "custom_minecart"), "main");

    private final ModelPart group;
    private final ModelPart base;
    private final ModelPart top;
    private final ModelPart front_tile;
    private final ModelPart chain;
    private final ModelPart chain_w_hook;
    private final ModelPart separate_chain;
    private final ModelPart hook;
    private final ModelPart wheels;
    public final ModelPart wheel1;
    public final ModelPart wheel2;
    public final ModelPart wheel3;
    public final ModelPart wheel4;
    private final ModelPart foundation;
    private final ModelPart root;

    public CustomMinecartEntityModel(ModelPart root) {
        super(root);

        this.root = root;

        this.group = root.getChild("group");
        this.base = this.group.getChild("base");
        this.top = this.group.getChild("top");
        this.front_tile = this.top.getChild("front_tile");
        this.chain = this.group.getChild("chain");
        this.chain_w_hook = this.chain.getChild("chain_w_hook");
        this.separate_chain = this.chain.getChild("separate_chain");
        this.hook = this.chain.getChild("hook");
        this.wheels = this.group.getChild("wheels");
        this.wheel1 = this.wheels.getChild("wheel1");
        this.wheel2 = this.wheels.getChild("wheel2");
        this.wheel3 = this.wheels.getChild("wheel3");
        this.wheel4 = this.wheels.getChild("wheel4");
        this.foundation = this.group.getChild("foundation");

    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData group = modelPartData.addChild("group", ModelPartBuilder.create(), ModelTransform.of(0.0F, -4F, 0.0F, 0.0F, 0F, 0.0F));

        ModelPartData base = group.addChild("base", ModelPartBuilder.create().uv(0, 11).cuboid(-11.0F, -1.0F, -9.0F, 22.0F, 1.0F, 18.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 6.0556F, 0.0F));

        ModelPartData top = group.addChild("top", ModelPartBuilder.create().uv(7, 4).cuboid(-10.4544F, -4.1927F, 7.4385F, 22.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(2, 14).cuboid(9.5456F, -4.1927F, -6.5615F, 2.0F, 2.0F, 14.0F, new Dilation(0.0F))
                .uv(2, 15).cuboid(-10.4544F, -4.1927F, -8.5615F, 22.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-0.5456F, 0.2482F, -0.4385F));

        ModelPartData cube_r1 = top.addChild("cube_r1", ModelPartBuilder.create().uv(2, 75).cuboid(-1.0F, -3.5F, -1.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(3, 74).cuboid(-19.5F, -3.5F, -1.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(3, 74).cuboid(-19.5F, -3.5F, -1.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(9.7956F, 0.3073F, -6.5615F, 0.0873F, 0.0F, 0.0F));

        ModelPartData cube_r2 = top.addChild("cube_r2", ModelPartBuilder.create().uv(2, 9).cuboid(-0.5F, -3.5F, -7.0F, 1.0F, 7.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(9.7456F, 1.3073F, 0.4385F, 0.0F, 0.0F, 0.0873F));

        ModelPartData cube_r3 = top.addChild("cube_r3", ModelPartBuilder.create().uv(2, 74).cuboid(-1.0F, -3.5F, 0.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(3, 71).cuboid(-19.5F, -3.5F, 0.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(9.7956F, 0.3073F, 7.4385F, -0.0873F, 0.0F, 0.0F));

        ModelPartData cube_r4 = top.addChild("cube_r4", ModelPartBuilder.create().uv(2, 74).cuboid(0.0F, -3.5F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(2, 74).cuboid(-19.5F, -3.5F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(9.7956F, 0.3073F, 6.2385F, -0.0873F, 0.0F, 0.0F));

        ModelPartData cube_r5 = top.addChild("cube_r5", ModelPartBuilder.create().uv(3, 4).cuboid(-10.0F, -3.5F, -0.5F, 20.0F, 7.0F, 1.0F, new Dilation(0.01F)), ModelTransform.of(0.5456F, 1.2573F, 7.4885F, -0.1309F, 0.0F, 0.0F));

        ModelPartData cube_r6 = top.addChild("cube_r6", ModelPartBuilder.create().uv(16, 8).mirrored().cuboid(-0.5F, -3.5F, -7.0F, 1.0F, 7.0F, 14.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-8.6794F, 1.3073F, 0.4385F, 0.0F, 0.0F, -0.0873F));

        ModelPartData cube_r7 = top.addChild("cube_r7", ModelPartBuilder.create().uv(2, 75).cuboid(0.0F, -3.5F, -1.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.1F))
                .uv(2, 74).cuboid(-19.5F, -3.5F, -1.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(9.7956F, 0.3073F, -5.3615F, 0.0873F, 0.0F, 0.0F));

        ModelPartData cube_r8 = top.addChild("cube_r8", ModelPartBuilder.create().uv(2, 18).cuboid(-10.0F, -3.5F, -0.5F, 20.0F, 7.0F, 1.0F, new Dilation(0.01F)), ModelTransform.of(0.5456F, 1.2573F, -6.6115F, 0.1309F, 0.0F, 0.0F));

        ModelPartData front_tile = top.addChild("front_tile", ModelPartBuilder.create().uv(3, 14).cuboid(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 14.0F, new Dilation(0.0F)), ModelTransform.origin(-9.4544F, -3.1927F, 0.4385F));

        ModelPartData chain = group.addChild("chain", ModelPartBuilder.create(), ModelTransform.origin(-2.4375F, 4.7569F, 0.5F));

        ModelPartData chain_w_hook = chain.addChild("chain_w_hook", ModelPartBuilder.create().uv(3, 6).mirrored().cuboid(-3.875F, -0.5F, 0.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(4, 6).cuboid(-3.875F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(3, 6).mirrored().cuboid(-3.875F, -0.5F, -1.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(4, 6).cuboid(-1.875F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(-6.6875F, -0.2569F, 0.0F));

        ModelPartData separate_chain = chain.addChild("separate_chain", ModelPartBuilder.create(), ModelTransform.origin(13.6042F, 0.7431F, 0.0F));

        ModelPartData cube_r9 = separate_chain.addChild("cube_r9", ModelPartBuilder.create().uv(3, 6).mirrored().cuboid(-0.75F, -0.5F, -1.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(4, 6).cuboid(-0.75F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(3, 6).mirrored().cuboid(-0.75F, -0.5F, 0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0833F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData hook = chain.addChild("hook", ModelPartBuilder.create().uv(3, 71).cuboid(-0.5F, 0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(3, 73).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.5F, 2.0F, new Dilation(0.0F))
                .uv(3, 73).cuboid(-0.5F, -1.5F, -1.0F, 1.0F, 0.5F, 2.0F, new Dilation(0.0F))
                .uv(3, 71).mirrored().cuboid(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F)).mirrored(false)
                .uv(3, 71).mirrored().cuboid(-0.5F, -1.0F, 0.5F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.0625F, -1.2014F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData wheels = group.addChild("wheels", ModelPartBuilder.create(), ModelTransform.origin(6.5F, 8.5556F, 5.1667F));

        ModelPartData wheel1 = wheels.addChild("wheel1", ModelPartBuilder.create().uv(2, 47).cuboid(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(6, 40).cuboid(-0.5F, -0.5F, -2.1667F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(10, 47).cuboid(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData wheel2 = wheels.addChild("wheel2", ModelPartBuilder.create().uv(14, 48).cuboid(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(15, 42).cuboid(-0.5F, -0.5F, -2.1667F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(12, 45).cuboid(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(-13.0F, 0.0F, 0.0F));

        ModelPartData wheel3 = wheels.addChild("wheel3", ModelPartBuilder.create().uv(15, 49).cuboid(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 48).cuboid(-0.5F, -0.5F, -0.1667F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(4, 44).mirrored().cuboid(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-13.0F, 0.0F, -11.0F));

        ModelPartData wheel4 = wheels.addChild("wheel4", ModelPartBuilder.create().uv(7, 38).cuboid(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(17, 50).cuboid(-0.5F, -0.5F, -0.1667F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(14, 35).cuboid(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, -11.0F));

        ModelPartData foundation = group.addChild("foundation", ModelPartBuilder.create().uv(9, 11).cuboid(-10.0F, -1.0F, -3.0F, 20.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 7.0556F, 0.0F));
        return TexturedModelData.of(modelData, 68, 86);
    }



    @Override
    public void setAngles(CustomMinecartEntityRenderState state) {
        float rotation = state.wheelRotation;

        this.group.visible = false;
        // Convert from degrees to radians
        float rotationRadians = (float)Math.toRadians(rotation);
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.group.pitch += 0.5f;
//        this.wheel1.yaw = rotationRadians;
        this.wheel2.yaw = rotationRadians;
        this.wheel3.yaw = rotationRadians;
        this.wheel4.yaw = rotationRadians;
    }

    public ModelPart getPart() {
        return this.root;
    }


}
