package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class EntityModelLayerRegistry {

    public static final EntityModelLayer CLAM = new EntityModelLayer(NekomasFixed.id("clam"), "main");

    public static final EntityModelLayer ACACIA_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/acacia"), "main");
    public static final EntityModelLayer BAMBOO_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/bamboo"), "main");
    public static final EntityModelLayer BIRCH_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/birch"), "main");
    public static final EntityModelLayer CHERRY_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/cherry"), "main");
    public static final EntityModelLayer DARK_OAK_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/dark_oak"), "main");
    public static final EntityModelLayer JUNGLE_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/jungle"), "main");
    public static final EntityModelLayer MANGROVE_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/mangrove"), "main");
    public static final EntityModelLayer OAK_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/oak"), "main");
    public static final EntityModelLayer PALE_OAK_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/pale_oak"), "main");
    public static final EntityModelLayer SPRUCE_MEGA_BOAT = new EntityModelLayer(NekomasFixed.id("mega_boat/spruce"), "main");

    public static void registerEntityModelLayer() {
        System.out.println("register EntityModelLayer");
    }
}
