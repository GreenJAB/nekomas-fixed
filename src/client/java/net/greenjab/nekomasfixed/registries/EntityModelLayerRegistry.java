package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class EntityModelLayerRegistry {

    public static final EntityModelLayer CLAM = new EntityModelLayer(NekomasFixed.id("clam"), "main");

    public static final EntityModelLayer MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat"), "main");
    public static final EntityModelLayer OAK_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/oak"), "main");

    public static void registerEntityModelLayer() {
        System.out.println("register EntityModelLayer");
    }
}
