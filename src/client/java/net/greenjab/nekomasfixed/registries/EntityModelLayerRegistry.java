package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.render.block.entity.ClamBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class EntityModelLayerRegistry {

    public static final EntityModelLayer CLAM = new EntityModelLayer(NekomasFixed.id("clam"), "main");

    public static void registerEntityModelLayer() {
        System.out.println("register EntityModelLayer");
    }
}
