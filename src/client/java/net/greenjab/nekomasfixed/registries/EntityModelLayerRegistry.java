package net.greenjab.nekomasfixed.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.util.Identifier;

public class EntityModelLayerRegistry {

    public static final EntityModelLayer CLAM = new EntityModelLayer(NekomasFixed.id("clam"), "main");
    public static final EntityModelLayer CLOCK = new EntityModelLayer(NekomasFixed.id("clock"), "main");

    public static final EntityModelLayer BIG_ACACIA_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/acacia"), "main");
    public static final EntityModelLayer BIG_BAMBOO_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/bamboo"), "main");
    public static final EntityModelLayer BIG_BIRCH_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/birch"), "main");
    public static final EntityModelLayer BIG_CHERRY_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/cherry"), "main");
    public static final EntityModelLayer BIG_DARK_OAK_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/dark_oak"), "main");
    public static final EntityModelLayer BIG_JUNGLE_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/jungle"), "main");
    public static final EntityModelLayer BIG_MANGROVE_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/mangrove"), "main");
    public static final EntityModelLayer BIG_OAK_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/oak"), "main");
    public static final EntityModelLayer BIG_PALE_OAK_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/pale_oak"), "main");
    public static final EntityModelLayer BIG_SPRUCE_BOAT = new EntityModelLayer(NekomasFixed.id("big_boat/spruce"), "main");

    public static final EntityModelLayer HUGE_ACACIA_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/acacia"), "main");
    public static final EntityModelLayer HUGE_BAMBOO_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/bamboo"), "main");
    public static final EntityModelLayer HUGE_BIRCH_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/birch"), "main");
    public static final EntityModelLayer HUGE_CHERRY_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/cherry"), "main");
    public static final EntityModelLayer HUGE_DARK_OAK_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/dark_oak"), "main");
    public static final EntityModelLayer HUGE_JUNGLE_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/jungle"), "main");
    public static final EntityModelLayer HUGE_MANGROVE_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/mangrove"), "main");
    public static final EntityModelLayer HUGE_OAK_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/oak"), "main");
    public static final EntityModelLayer HUGE_PALE_OAK_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/pale_oak"), "main");
    public static final EntityModelLayer HUGE_SPRUCE_BOAT = new EntityModelLayer(NekomasFixed.id("huge_boat/spruce"), "main");

    public static final EntityModelLayer TARGET_DUMMY = new EntityModelLayer(NekomasFixed.id("target_dummy"), "main");
    public static final EntityModelLayer TARGET_DUMMY_BASE = new EntityModelLayer(NekomasFixed.id("target_dummy_base"), "main");
    public static final EquipmentModelData<EntityModelLayer> TARGET_DUMMY_EQUIPMENT = registerEquipment(NekomasFixed.id("target_dummy"));


    private static EquipmentModelData<EntityModelLayer> registerEquipment(Identifier id) {
        return new EquipmentModelData<>(new EntityModelLayer(id, "helmet"), new EntityModelLayer(id, "chestplate"), new EntityModelLayer(id, "leggings"), new EntityModelLayer(id, "boots"));
    }

    public static void registerEntityModelLayer() {
        System.out.println("register EntityModelLayer");
    }
}
