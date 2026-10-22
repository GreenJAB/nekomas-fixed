package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.monster.zombie.BabyZombieModel;

@Environment(EnvType.CLIENT)
public class BabyRimeModel extends RimeModel {
    public BabyRimeModel(final ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return BabyZombieModel.createBodyLayer(CubeDeformation.NONE);
    }

    public static LayerDefinition createOuterLayer() {
        return BabyZombieModel.createBodyLayer(new CubeDeformation(0.25F));
    }
}