package net.greenjab.nekomasfixed.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;

@Environment(EnvType.CLIENT)
public class RimeEntityModel extends ZombieModel<ZombieRenderState> {

    public RimeEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        return LayerDefinition.create(modelData, 128, 128);
    }
}