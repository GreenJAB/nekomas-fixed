package net.greenjab.nekomasfixed.render.entity.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.object.cart.MinecartModel;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class CustomMinecartModel extends MinecartModel {

    public final ModelPart wheel1;
    public final ModelPart wheel2;
    public final ModelPart wheel3;
    public final ModelPart wheel4;

    public CustomMinecartModel(ModelPart root) {
        super(root);
        ModelPart group = root.getChild("group");
        ModelPart wheels = group.getChild("wheels");
        this.wheel1 = wheels.getChild("wheel1");
        this.wheel2 = wheels.getChild("wheel2");
        this.wheel3 = wheels.getChild("wheel3");
        this.wheel4 = wheels.getChild("wheel4");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition group = modelPartData.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4F, 0.0F, 0.0F, 0F, 0.0F));

        group.addOrReplaceChild("base", CubeListBuilder.create().texOffs(19, 17).addBox(-11.0F, -1.0F, -9.0F, 22.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0556F, 0.0F));

        PartDefinition top = group.addOrReplaceChild("top", CubeListBuilder.create().texOffs(9, 4).addBox(-10.4544F, -4.1927F, 7.4385F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(82, 10).addBox(9.5456F, -4.1927F, -6.5615F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(9, 0).addBox(-10.4544F, -4.1927F, -8.5615F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5456F, 0.2482F, -0.4385F));

        top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, -3.5F, -1.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(0, 5).addBox(-19.5F, -3.5F, -1.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(9.7956F, 0.3073F, -6.5615F, 0.0873F, 0.0F, 0.0F));

        top.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(7, 3).addBox(-0.5F, -3.5F, -7.0F, 1.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.7456F, 1.3073F, 0.4385F, 0.0F, 0.0F, 0.0873F));

        top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, -3.5F, 0.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(0, 5).addBox(-19.5F, -3.5F, 0.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(9.7956F, 0.3073F, 7.4385F, -0.0873F, 0.0F, 0.0F));

        top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -3.5F, 0.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(0, 5).addBox(-19.5F, -3.5F, 0.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(9.7956F, 0.3073F, 6.2385F, -0.0873F, 0.0F, 0.0F));

        top.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(27, 8).addBox(-10.0F, -3.5F, -0.5F, 20.0F, 7.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.5456F, 1.2573F, 7.4885F, -0.1309F, 0.0F, 0.0F));

        top.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(7, 3).mirror().addBox(-0.5F, -3.5F, -7.0F, 1.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.6794F, 1.3073F, 0.4385F, 0.0F, 0.0F, -0.0873F));

        top.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -3.5F, -1.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(0, 5).addBox(-19.5F, -3.5F, -1.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(9.7956F, 0.3073F, -5.3615F, 0.0873F, 0.0F, 0.0F));

        top.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(6, 8).addBox(-10.0F, -3.5F, -0.5F, 20.0F, 7.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.5456F, 1.2573F, -6.6115F, 0.1309F, 0.0F, 0.0F));

        top.addOrReplaceChild("front_tile", CubeListBuilder.create().texOffs(82, 10).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.4544F, -3.1927F, 0.4385F));

        PartDefinition chain = group.addOrReplaceChild("chain", CubeListBuilder.create(), PartPose.offset(-2.4375F, 4.7569F, 0.5F));

        chain.addOrReplaceChild("chain_w_hook", CubeListBuilder.create().texOffs(10, 18).mirror().addBox(-3.875F, -0.5F, 0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 18).addBox(-3.875F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 18).mirror().addBox(-3.875F, -0.5F, -1.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 18).addBox(-1.875F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.6875F, -0.2569F, 0.0F));

        PartDefinition separate_chain = chain.addOrReplaceChild("separate_chain", CubeListBuilder.create(), PartPose.offset(13.6042F, 0.7431F, 0.0F));

        separate_chain.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(11, 18).mirror().addBox(-0.75F, -0.5F, -1.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 18).addBox(-0.75F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 18).mirror().addBox(-0.75F, -0.5F, 0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0833F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        chain.addOrReplaceChild("hook", CubeListBuilder.create().texOffs(0, 15).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-0.5F, -1.5F, -1.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 15).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 15).mirror().addBox(-0.5F, -1.0F, 0.5F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0625F, -1.2014F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition wheels = group.addOrReplaceChild("wheels", CubeListBuilder.create(), PartPose.offset(6.5F, 8.5556F, 5.1667F));

        wheels.addOrReplaceChild("wheel1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -0.5F, -2.1667F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        wheels.addOrReplaceChild("wheel2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -0.5F, -2.1667F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 0.0F, 0.0F));

        wheels.addOrReplaceChild("wheel3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -0.5F, -0.1667F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.0F, 0.0F, -11.0F));

        wheels.addOrReplaceChild("wheel4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.8333F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -0.5F, -0.1667F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -1.5F, -1.1667F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -11.0F));

        group.addOrReplaceChild("foundation", CubeListBuilder.create().texOffs(58, 0).addBox(-10.0F, -1.0F, -3.0F, 20.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0556F, 0.0F));
        return LayerDefinition.create(modelData, 128, 64);
    }

    public void setupAnim(@NonNull MinecartRenderState state) {
        super.setupAnim(state);
        double rotation = 360 * ((state.x-(int)state.x) * (Mth.degreesDifferenceAbs(state.yRot, 180)<90?1:-1) +
                (state.z-(int)state.z) * (Mth.degreesDifferenceAbs(state.yRot, 90)<90?1:-1));
        float rotationRadians = (float) Math.toRadians(rotation);

        this.wheel1.zRot = rotationRadians;
        this.wheel2.zRot = rotationRadians;
        this.wheel3.zRot = rotationRadians;
        this.wheel4.zRot = rotationRadians;
    }
}
