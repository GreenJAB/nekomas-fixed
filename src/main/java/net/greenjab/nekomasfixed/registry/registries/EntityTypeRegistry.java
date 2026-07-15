package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.*;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.Moobloom;
import net.greenjab.nekomasfixed.registry.entity.WildFire.FireBomb;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import java.util.List;
import java.util.function.Supplier;

public class EntityTypeRegistry {

    public static final EntityType<FakeBoat> FAKE_BOAT = register(
            "fake_boat", EntityType.Builder.of(FakeBoat::new, MobCategory.MISC)
                    .noLootTable().sized(1.65f, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    public static final EntityType<BigBoat> BIG_ACACIA_BOAT = bigBoatFactory("big_acacia_boat", () -> ItemRegistry.BIG_ACACIA_BOAT);
    public static final EntityType<BigBoat> BIG_BAMBOO_BOAT = bigBoatFactory("big_bamboo_boat", () -> ItemRegistry.BIG_BAMBOO_BOAT);
    public static final EntityType<BigBoat> BIG_BIRCH_BOAT = bigBoatFactory("big_birch_boat", () -> ItemRegistry.BIG_BIRCH_BOAT);
    public static final EntityType<BigBoat> BIG_CHERRY_BOAT = bigBoatFactory("big_cherry_boat", () -> ItemRegistry.BIG_CHERRY_BOAT);
    public static final EntityType<BigBoat> BIG_DARK_OAK_BOAT = bigBoatFactory("big_dark_oak_boat", () -> ItemRegistry.BIG_DARK_OAK_BOAT);
    public static final EntityType<BigBoat> BIG_JUNGLE_BOAT = bigBoatFactory("big_jungle_boat", () -> ItemRegistry.BIG_JUNGLE_BOAT);
    public static final EntityType<BigBoat> BIG_MANGROVE_BOAT = bigBoatFactory("big_mangrove_boat", () -> ItemRegistry.BIG_MANGROVE_BOAT);
    public static final EntityType<BigBoat> BIG_OAK_BOAT = bigBoatFactory("big_oak_boat", () -> ItemRegistry.BIG_OAK_BOAT);
    public static final EntityType<BigBoat> BIG_PALE_OAK_BOAT = bigBoatFactory("big_pale_oak_boat", () -> ItemRegistry.BIG_PALE_OAK_BOAT);
    public static final EntityType<BigBoat> BIG_SPRUCE_BOAT = bigBoatFactory("big_spruce_boat", () -> ItemRegistry.BIG_SPRUCE_BOAT);
    public static final EntityType<BigBoat> BIG_BAOBAB_BOAT = bigBoatFactory("big_baobab_boat", () -> ItemRegistry.BIG_BAOBAB_BOAT);

    public static final EntityType<HugeBoat> HUGE_ACACIA_BOAT = hugeBoatFactory("huge_acacia_boat", () -> ItemRegistry.HUGE_ACACIA_BOAT);
    public static final EntityType<HugeBoat> HUGE_BAMBOO_BOAT = hugeBoatFactory("huge_bamboo_boat", () -> ItemRegistry.HUGE_BAMBOO_BOAT);
    public static final EntityType<HugeBoat> HUGE_BIRCH_BOAT = hugeBoatFactory("huge_birch_boat", () -> ItemRegistry.HUGE_BIRCH_BOAT);
    public static final EntityType<HugeBoat> HUGE_CHERRY_BOAT = hugeBoatFactory("huge_cherry_boat", () -> ItemRegistry.HUGE_CHERRY_BOAT);
    public static final EntityType<HugeBoat> HUGE_DARK_OAK_BOAT = hugeBoatFactory("huge_dark_oak_boat", () -> ItemRegistry.HUGE_DARK_OAK_BOAT);
    public static final EntityType<HugeBoat> HUGE_JUNGLE_BOAT = hugeBoatFactory("huge_jungle_boat", () -> ItemRegistry.HUGE_JUNGLE_BOAT);
    public static final EntityType<HugeBoat> HUGE_MANGROVE_BOAT = hugeBoatFactory("huge_mangrove_boat", () -> ItemRegistry.HUGE_MANGROVE_BOAT);
    public static final EntityType<HugeBoat> HUGE_OAK_BOAT = hugeBoatFactory("huge_oak_boat", () -> ItemRegistry.HUGE_OAK_BOAT);
    public static final EntityType<HugeBoat> HUGE_PALE_OAK_BOAT = hugeBoatFactory("huge_pale_oak_boat", () -> ItemRegistry.HUGE_PALE_OAK_BOAT);
    public static final EntityType<HugeBoat> HUGE_SPRUCE_BOAT = hugeBoatFactory("huge_spruce_boat", () -> ItemRegistry.HUGE_SPRUCE_BOAT);
    public static final EntityType<HugeBoat> HUGE_BAOBAB_BOAT = hugeBoatFactory("huge_baobab_boat", () -> ItemRegistry.HUGE_BAOBAB_BOAT);

    public static final EntityType<Boat> BAOBAB_BOAT = register("baobab_boat",
            EntityType.Builder.of(getBoatFactory(() -> ItemRegistry.BAOBAB_BOAT), MobCategory.MISC)
                    .noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    public static final EntityType<ChestBoat> BAOBAB_CHEST_BOAT = register("baobab_chest_boat",
            EntityType.Builder.of(getChestBoatFactory(() -> ItemRegistry.BAOBAB_CHEST_BOAT), MobCategory.MISC)
                    .noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));

    public static List<EntityType<BigBoat>> bigBoats = List.of(BIG_ACACIA_BOAT, BIG_BAMBOO_BOAT, BIG_BIRCH_BOAT, BIG_CHERRY_BOAT, BIG_DARK_OAK_BOAT, BIG_JUNGLE_BOAT, BIG_MANGROVE_BOAT, BIG_OAK_BOAT, BIG_PALE_OAK_BOAT, BIG_SPRUCE_BOAT, BIG_BAOBAB_BOAT);
    public static List<EntityType<HugeBoat>> hugeBoats = List.of(HUGE_ACACIA_BOAT, HUGE_BAMBOO_BOAT, HUGE_BIRCH_BOAT, HUGE_CHERRY_BOAT, HUGE_DARK_OAK_BOAT, HUGE_JUNGLE_BOAT, HUGE_MANGROVE_BOAT, HUGE_OAK_BOAT, HUGE_PALE_OAK_BOAT, HUGE_SPRUCE_BOAT, HUGE_BAOBAB_BOAT);
    public static List<EntityType<? extends AbstractBoat>> boats = List.of(EntityTypes.ACACIA_BOAT, EntityTypes.BAMBOO_RAFT, EntityTypes.BIRCH_BOAT, EntityTypes.CHERRY_BOAT, EntityTypes.DARK_OAK_BOAT, EntityTypes.JUNGLE_BOAT, EntityTypes.MANGROVE_BOAT, EntityTypes.OAK_BOAT, EntityTypes.PALE_OAK_BOAT, EntityTypes.SPRUCE_BOAT, BAOBAB_BOAT);


    public static final EntityType<TargetDummy> TARGET_DUMMY = register("target_dummy",
            EntityType.Builder.of(TargetDummy::new, MobCategory.MISC).sized(0.5F, 1.975F).eyeHeight(1.7775F).clientTrackingRange(10));

    public static final EntityType<SpearEntity> SPEAR = register("spear",
            EntityType.Builder.of(SpearEntity::new, MobCategory.MISC)
                    .noLootTable().sized(0.6f, 0.6F).eyeHeight(0.3F).clientTrackingRange(10));

    public static final EntityType<WildfireTrident> WILDFIRE_TRIDENT = register("wildfire_trident",
            EntityType.Builder.<WildfireTrident>of(WildfireTrident::new, MobCategory.MISC)
                    .noLootTable().sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20));

    public static final EntityType<SlingshotProjectile> SLINGSHOT_PROJECTILE = register("slingshot_projectile",
            EntityType.Builder.<SlingshotProjectile>of(SlingshotProjectile::new, MobCategory.MISC)
                    .noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));

    public static final EntityType<FireBomb> FIRE_BOMB = register("fire_bomb",
            EntityType.Builder.<FireBomb>of(FireBomb::new, MobCategory.MISC)
                    .noLootTable().sized(0.25F, 0.25F) .clientTrackingRange(4).updateInterval(10));

    public static final EntityType<SlownessSnowball> SLOWNESS_SNOWBALL = register("slowness_snowball",
            EntityType.Builder.<SlownessSnowball>of(SlownessSnowball::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10));

    public static final EntityType<WildfireEntity> WILDFIRE = register("wildfire",
            EntityType.Builder.of(WildfireEntity::new, MobCategory.MONSTER).fireImmune().sized(0.75F, 1.975F).clientTrackingRange(8).notInPeaceful());

    public static final EntityType<Termite> TERMITE = register("termite",
            EntityType.Builder.of(Termite::new, MobCategory.MONSTER).sized(0.5f, 0.5f));

    public static final EntityType<Moobloom> MOOBLOOM = register("moobloom",
            EntityType.Builder.of(Moobloom::new, MobCategory.AMBIENT).sized(1f, 1f));

    public static final EntityType<SuspiciousSpider> SUSPICIOUS_SPIDER = register("suspicious_spider",
            EntityType.Builder.of(SuspiciousSpider::new, MobCategory.MONSTER).sized(1f, 1f).notInPeaceful());

    public static final EntityType<Derelict> DERELICT = register("derelict",
            EntityType.Builder.of(Derelict::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f).notInPeaceful());

    public static final EntityType<Rime> RIME = register("rime",
            EntityType.Builder.of(Rime::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f).notInPeaceful());

    public static final EntityType<Drenched> DRENCHED = register("drenched",
            EntityType.Builder.of(Drenched::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.99f).notInPeaceful());



    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }
    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type.build(key));
    }
    private static ResourceKey<EntityType<?>> keyOf(String id) {
        return ResourceKey.create(Registries.ENTITY_TYPE, NekomasFixed.id(id));
    }

    private static EntityType<BigBoat> bigBoatFactory(String id, Supplier<Item> item) {
        return register(id, EntityType.Builder.of(getBigBoatFactory(item), MobCategory.MISC)
                        .noLootTable().sized(1.9f, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    }
    private static EntityType.EntityFactory<BigBoat> getBigBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BigBoat(type, world, itemSupplier);
    }

    private static EntityType<HugeBoat> hugeBoatFactory(String id, Supplier<Item> item) {
        return register(id, EntityType.Builder.of(getHugeBoatFactory(item), MobCategory.MISC)
                        .noLootTable().sized(2.6f, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10));
    }
    private static EntityType.EntityFactory<HugeBoat> getHugeBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new HugeBoat(type, world, itemSupplier);
    }
    private static EntityType.EntityFactory<Boat> getBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new Boat(type, world, itemSupplier);
    }
    private static EntityType.EntityFactory<ChestBoat> getChestBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new ChestBoat(type, world, itemSupplier);
    }

    public static void registerEntityType() {
        System.out.println("register EntityType");
        FabricDefaultAttributeRegistry.register(TARGET_DUMMY, TargetDummy.createTargetDummyAttributes().build());
        FabricDefaultAttributeRegistry.register(WILDFIRE, WildfireEntity.createWildfireAttributes().build());
        FabricDefaultAttributeRegistry.register(TERMITE, Termite.createAttributes());
        FabricDefaultAttributeRegistry.register(MOOBLOOM, Moobloom.createAttributes());
        FabricDefaultAttributeRegistry.register(SUSPICIOUS_SPIDER, SuspiciousSpider.createSuspiciousSpiderAttributes());
        FabricDefaultAttributeRegistry.register(DERELICT, Zombie.createAttributes());
        FabricDefaultAttributeRegistry.register(RIME, Zombie.createAttributes());
        FabricDefaultAttributeRegistry.register(DRENCHED, Drenched.createDrenchedAttributes());
    }

    public static void init() {}
}
