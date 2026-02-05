package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;
import java.util.function.Supplier;

public class EntityTypeRegistry {

    public static final EntityType<FakeBoatEntity> FAKE_BOAT = register(
            "fake_boat", EntityType.Builder.create(FakeBoatEntity::new, SpawnGroup.MISC)
                    .dropsNothing().dimensions(1.65f, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    public static final EntityType<BigBoatEntity> BIG_ACACIA_BOAT = bigBoatFactory("big_acacia_boat", () -> ItemRegistry.BIG_ACACIA_BOAT);
    public static final EntityType<BigBoatEntity> BIG_BAMBOO_BOAT = bigBoatFactory("big_bamboo_boat", () -> ItemRegistry.BIG_BAMBOO_BOAT);
    public static final EntityType<BigBoatEntity> BIG_BIRCH_BOAT = bigBoatFactory("big_birch_boat", () -> ItemRegistry.BIG_BIRCH_BOAT);
    public static final EntityType<BigBoatEntity> BIG_CHERRY_BOAT = bigBoatFactory("big_cherry_boat", () -> ItemRegistry.BIG_CHERRY_BOAT);
    public static final EntityType<BigBoatEntity> BIG_DARK_OAK_BOAT = bigBoatFactory("big_dark_oak_boat", () -> ItemRegistry.BIG_DARK_OAK_BOAT);
    public static final EntityType<BigBoatEntity> BIG_JUNGLE_BOAT = bigBoatFactory("big_jungle_boat", () -> ItemRegistry.BIG_JUNGLE_BOAT);
    public static final EntityType<BigBoatEntity> BIG_MANGROVE_BOAT = bigBoatFactory("big_mangrove_boat", () -> ItemRegistry.BIG_MANGROVE_BOAT);
    public static final EntityType<BigBoatEntity> BIG_OAK_BOAT = bigBoatFactory("big_oak_boat", () -> ItemRegistry.BIG_OAK_BOAT);
    public static final EntityType<BigBoatEntity> BIG_PALE_OAK_BOAT = bigBoatFactory("big_pale_oak_boat", () -> ItemRegistry.BIG_PALE_OAK_BOAT);
    public static final EntityType<BigBoatEntity> BIG_SPRUCE_BOAT = bigBoatFactory("big_spruce_boat", () -> ItemRegistry.BIG_SPRUCE_BOAT);

    public static final EntityType<HugeBoatEntity> HUGE_ACACIA_BOAT = hugeBoatFactory("huge_acacia_boat", () -> ItemRegistry.HUGE_ACACIA_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_BAMBOO_BOAT = hugeBoatFactory("huge_bamboo_boat", () -> ItemRegistry.HUGE_BAMBOO_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_BIRCH_BOAT = hugeBoatFactory("huge_birch_boat", () -> ItemRegistry.HUGE_BIRCH_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_CHERRY_BOAT = hugeBoatFactory("huge_cherry_boat", () -> ItemRegistry.HUGE_CHERRY_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_DARK_OAK_BOAT = hugeBoatFactory("huge_dark_oak_boat", () -> ItemRegistry.HUGE_DARK_OAK_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_JUNGLE_BOAT = hugeBoatFactory("huge_jungle_boat", () -> ItemRegistry.HUGE_JUNGLE_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_MANGROVE_BOAT = hugeBoatFactory("huge_mangrove_boat", () -> ItemRegistry.HUGE_MANGROVE_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_OAK_BOAT = hugeBoatFactory("huge_oak_boat", () -> ItemRegistry.HUGE_OAK_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_PALE_OAK_BOAT = hugeBoatFactory("huge_pale_oak_boat", () -> ItemRegistry.HUGE_PALE_OAK_BOAT);
    public static final EntityType<HugeBoatEntity> HUGE_SPRUCE_BOAT = hugeBoatFactory("huge_spruce_boat", () -> ItemRegistry.HUGE_SPRUCE_BOAT);

    public static List<EntityType<BigBoatEntity>> bigBoats = List.of(BIG_ACACIA_BOAT, BIG_BAMBOO_BOAT, BIG_BIRCH_BOAT, BIG_CHERRY_BOAT, BIG_DARK_OAK_BOAT, BIG_JUNGLE_BOAT, BIG_MANGROVE_BOAT, BIG_OAK_BOAT, BIG_PALE_OAK_BOAT, BIG_SPRUCE_BOAT);
    public static List<EntityType<HugeBoatEntity>> hugeBoats = List.of(HUGE_ACACIA_BOAT, HUGE_BAMBOO_BOAT, HUGE_BIRCH_BOAT, HUGE_CHERRY_BOAT, HUGE_DARK_OAK_BOAT, HUGE_JUNGLE_BOAT, HUGE_MANGROVE_BOAT, HUGE_OAK_BOAT, HUGE_PALE_OAK_BOAT, HUGE_SPRUCE_BOAT);
    public static List<EntityType<? extends AbstractBoatEntity>> boats = List.of(EntityType.ACACIA_BOAT, EntityType.BAMBOO_RAFT, EntityType.BIRCH_BOAT, EntityType.CHERRY_BOAT, EntityType.DARK_OAK_BOAT, EntityType.JUNGLE_BOAT, EntityType.MANGROVE_BOAT, EntityType.OAK_BOAT, EntityType.PALE_OAK_BOAT, EntityType.SPRUCE_BOAT);

    private static EntityType<BigBoatEntity> bigBoatFactory(String id, Supplier<Item> item) {
        return register(
                id, EntityType.Builder.create(getBigBoatFactory(item), SpawnGroup.MISC)
                        .dropsNothing().dimensions(1.9f, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    }
    private static EntityType.EntityFactory<BigBoatEntity> getBigBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BigBoatEntity(type, world, itemSupplier);
    }

    private static EntityType<HugeBoatEntity> hugeBoatFactory(String id, Supplier<Item> item) {
        return register(
                id, EntityType.Builder.create(getHugeBoatFactory(item), SpawnGroup.MISC)
                        .dropsNothing().dimensions(2.6f, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    }
    private static EntityType.EntityFactory<HugeBoatEntity> getHugeBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new HugeBoatEntity(type, world, itemSupplier);
    }



    public static final EntityType<TargetDummyEntity> TARGET_DUMMY = register(
            "target_dummy",
            EntityType.Builder.create(TargetDummyEntity::new, SpawnGroup.MISC).dimensions(0.5F, 1.975F).eyeHeight(1.7775F).maxTrackingRange(10)
    );

    public static final EntityType<SpearEntity> SPEAR = register(
            "spear", EntityType.Builder.create(SpearEntity::new, SpawnGroup.MISC)
                    .dropsNothing().dimensions(0.6f, 0.6F).eyeHeight(0.3F).maxTrackingRange(10));

    public static final EntityType<WildFireEntity> WILD_FIRE = register(
            "wild_fire", EntityType.Builder.create(WildFireEntity::new, SpawnGroup.MONSTER).makeFireImmune().dimensions(0.75F, 1.975F).maxTrackingRange(8).notAllowedInPeaceful()
    );


    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }
    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }
    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, NekomasFixed.id(id));
    }

    public static void registerEntityType() {
        System.out.println("register EntityType");
        FabricDefaultAttributeRegistry.register(TARGET_DUMMY, TargetDummyEntity.createTargetDummyAttributes().build());
        FabricDefaultAttributeRegistry.register(WILD_FIRE, WildFireEntity.createWildFireAttributes().build());
    }
}
