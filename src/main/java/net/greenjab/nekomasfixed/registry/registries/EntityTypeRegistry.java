package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.*;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomEntity;
import net.greenjab.nekomasfixed.registry.entity.WildFire.FireBombEntity;
import net.greenjab.nekomasfixed.registry.entity.WildFire.WildfireEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
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
    public static final EntityType<BigBoatEntity> BIG_BAOBAB_BOAT = bigBoatFactory("big_baobab_boat", () -> ItemRegistry.BIG_BAOBAB_BOAT);

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
    public static final EntityType<HugeBoatEntity> HUGE_BAOBAB_BOAT = hugeBoatFactory("huge_baobab_boat", () -> ItemRegistry.HUGE_BAOBAB_BOAT);

    public static final EntityType<BoatEntity> BAOBAB_BOAT = register("baobab_boat",
            EntityType.Builder.create(getBoatFactory(() -> ItemRegistry.BAOBAB_BOAT), SpawnGroup.MISC)
                    .dropsNothing().dimensions(1.375F, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    public static final EntityType<ChestBoatEntity> BAOBAB_CHEST_BOAT = register("baobab_chest_boat",
            EntityType.Builder.create(getChestBoatFactory(() -> ItemRegistry.BAOBAB_CHEST_BOAT), SpawnGroup.MISC)
                    .dropsNothing().dimensions(1.375F, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));

    public static List<EntityType<BigBoatEntity>> bigBoats = List.of(BIG_ACACIA_BOAT, BIG_BAMBOO_BOAT, BIG_BIRCH_BOAT, BIG_CHERRY_BOAT, BIG_DARK_OAK_BOAT, BIG_JUNGLE_BOAT, BIG_MANGROVE_BOAT, BIG_OAK_BOAT, BIG_PALE_OAK_BOAT, BIG_SPRUCE_BOAT, BIG_BAOBAB_BOAT);
    public static List<EntityType<HugeBoatEntity>> hugeBoats = List.of(HUGE_ACACIA_BOAT, HUGE_BAMBOO_BOAT, HUGE_BIRCH_BOAT, HUGE_CHERRY_BOAT, HUGE_DARK_OAK_BOAT, HUGE_JUNGLE_BOAT, HUGE_MANGROVE_BOAT, HUGE_OAK_BOAT, HUGE_PALE_OAK_BOAT, HUGE_SPRUCE_BOAT, HUGE_BAOBAB_BOAT);
    public static List<EntityType<? extends AbstractBoatEntity>> boats = List.of(EntityType.ACACIA_BOAT, EntityType.BAMBOO_RAFT, EntityType.BIRCH_BOAT, EntityType.CHERRY_BOAT, EntityType.DARK_OAK_BOAT, EntityType.JUNGLE_BOAT, EntityType.MANGROVE_BOAT, EntityType.OAK_BOAT, EntityType.PALE_OAK_BOAT, EntityType.SPRUCE_BOAT, BAOBAB_BOAT);


    public static final EntityType<TargetDummyEntity> TARGET_DUMMY = register("target_dummy",
            EntityType.Builder.create(TargetDummyEntity::new, SpawnGroup.MISC).dimensions(0.5F, 1.975F).eyeHeight(1.7775F).maxTrackingRange(10));

    public static final EntityType<SpearEntity> SPEAR = register("spear",
            EntityType.Builder.create(SpearEntity::new, SpawnGroup.MISC)
                    .dropsNothing().dimensions(0.6f, 0.6F).eyeHeight(0.3F).maxTrackingRange(10));

    public static final EntityType<WildfireTridentEntity> WILDFIRE_TRIDENT = register("wildfire_trident",
            EntityType.Builder.<WildfireTridentEntity>create(WildfireTridentEntity::new, SpawnGroup.MISC)
                    .dropsNothing().dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));

    public static final EntityType<SlingshotProjectileEntity> SLINGSHOT_PROJECTILE = register("slingshot_projectile",
            EntityType.Builder.<SlingshotProjectileEntity>create(SlingshotProjectileEntity::new, SpawnGroup.MISC)
                    .dropsNothing().dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));

    public static final EntityType<FireBombEntity> FIRE_BOMB = register("fire_bomb",
            EntityType.Builder.<FireBombEntity>create(FireBombEntity::new, SpawnGroup.MISC)
                    .dropsNothing().dimensions(0.25F, 0.25F) .maxTrackingRange(4).trackingTickInterval(10));
    //is used
    public static final EntityType<SlownessSnowballEntity> SLOWNESS_SNOWBALL = register("slowness_snowball",
            EntityType.Builder.<SlownessSnowballEntity>create(SlownessSnowballEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10));


    public static final EntityType<WildfireEntity> WILDFIRE = register("wildfire",
            EntityType.Builder.create(WildfireEntity::new, SpawnGroup.MONSTER).makeFireImmune().dimensions(0.75F, 1.975F).maxTrackingRange(8).notAllowedInPeaceful());

    public static final EntityType<TermiteEntity> TERMITE = register("termite",
            EntityType.Builder.create(TermiteEntity::new, SpawnGroup.MONSTER).dimensions(0.5f, 0.5f));

    public static final EntityType<MoobloomEntity> MOOBLOOM = register("moobloom",
            EntityType.Builder.create(MoobloomEntity::new, SpawnGroup.AMBIENT).dimensions(1f, 1f));

    public static final EntityType<SuspiciousSpiderEntity> SUSPICIOUS_SPIDER = register("suspicious_spider",
            EntityType.Builder.create(SuspiciousSpiderEntity::new, SpawnGroup.MONSTER).dimensions(1f, 1f).notAllowedInPeaceful());

    public static final EntityType<DerelictEntity> DERELICT = register("derelict",
            EntityType.Builder.create(DerelictEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.95f).notAllowedInPeaceful());

    public static final EntityType<RimeEntity> RIME = register("rime",
            EntityType.Builder.create(RimeEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.95f).notAllowedInPeaceful());

    public static final EntityType<DrenchedEntity> DRENCHED = register("drenched",
            EntityType.Builder.create(DrenchedEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.99f).notAllowedInPeaceful());



    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }
    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }
    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, NekomasFixed.id(id));
    }

    private static EntityType<BigBoatEntity> bigBoatFactory(String id, Supplier<Item> item) {
        return register(id, EntityType.Builder.create(getBigBoatFactory(item), SpawnGroup.MISC)
                        .dropsNothing().dimensions(1.9f, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    }
    private static EntityType.EntityFactory<BigBoatEntity> getBigBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BigBoatEntity(type, world, itemSupplier);
    }

    private static EntityType<HugeBoatEntity> hugeBoatFactory(String id, Supplier<Item> item) {
        return register(id, EntityType.Builder.create(getHugeBoatFactory(item), SpawnGroup.MISC)
                        .dropsNothing().dimensions(2.6f, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    }
    private static EntityType.EntityFactory<HugeBoatEntity> getHugeBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new HugeBoatEntity(type, world, itemSupplier);
    }
    private static EntityType.EntityFactory<BoatEntity> getBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BoatEntity(type, world, itemSupplier);
    }
    private static EntityType.EntityFactory<ChestBoatEntity> getChestBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new ChestBoatEntity(type, world, itemSupplier);
    }

    public static void registerEntityType() {
        System.out.println("register EntityType");
        FabricDefaultAttributeRegistry.register(TARGET_DUMMY, TargetDummyEntity.createTargetDummyAttributes().build());
        FabricDefaultAttributeRegistry.register(WILDFIRE, WildfireEntity.createWildfireAttributes().build());
        FabricDefaultAttributeRegistry.register(TERMITE, TermiteEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MOOBLOOM, MoobloomEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SUSPICIOUS_SPIDER, SuspiciousSpiderEntity.createSuspiciousSpiderAttributes());
        FabricDefaultAttributeRegistry.register(DERELICT, ZombieEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(RIME, ZombieEntity.createZombieAttributes());
        FabricDefaultAttributeRegistry.register(DRENCHED, DrenchedEntity.createDrenchedAttributes());
    }

    public static void init() {}
}
