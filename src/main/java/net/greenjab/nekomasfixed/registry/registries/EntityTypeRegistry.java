package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.*;
import net.greenjab.nekomasfixed.registry.entity.moobloom.Moobloom;
import net.greenjab.nekomasfixed.registry.entity.wildfire.FireBomb;
import net.greenjab.nekomasfixed.registry.entity.wildfire.WildfireAttackablesSensor;
import net.greenjab.nekomasfixed.registry.entity.wildfire.WildfireEntity;
import net.greenjab.nekomasfixed.util.BoatWoods;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.EnumMap;

public class EntityTypeRegistry {

    public static final EntityType<BaobabBoat> BAOBAB_BOAT = register(
            "baobab_boat",
            EntityType.Builder.<BaobabBoat>of(
                            BaobabBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F).clientTrackingRange(10)
    );
    public static final EntityType<BaobabChestBoat> BAOBAB_CHEST_BOAT = register(
            "baobab_chest_boat",
            EntityType.Builder.<BaobabChestBoat>of(
                            BaobabChestBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F).clientTrackingRange(10)
    );

    public static final EntityType<FakeBoat> FAKE_BOAT = register(
            "fake_boat",
            EntityType.Builder.of(FakeBoat::new, MobCategory.MISC)
                    .sized(1.65F, 0.5625F).clientTrackingRange(10)
    );

    public static final EnumMap<BoatWoods, EntityType<BigBoat>> BIG_BOATS = new EnumMap<>(BoatWoods.class);
    public static final EnumMap<BoatWoods, EntityType<HugeBoat>> HUGE_BOATS = new EnumMap<>(BoatWoods.class);

    static {
        for (BoatWoods wood : BoatWoods.values()) {
            BIG_BOATS.put(wood, register("big_" + wood.id() + "_boat",
                    EntityType.Builder.<BigBoat>of((type, level) -> new BigBoat(type, level, () -> ItemRegistry.BIG_BOATS.get(wood)), MobCategory.MISC)
                            .sized(1.9F, 0.5625F).clientTrackingRange(10)));
            HUGE_BOATS.put(wood, register("huge_" + wood.id() + "_boat",
                    EntityType.Builder.<HugeBoat>of((type, level) -> new HugeBoat(type, level, () -> ItemRegistry.HUGE_BOATS.get(wood)), MobCategory.MISC)
                            .sized(2.6F, 0.5625F).clientTrackingRange(10)));
        }
    }

    public static final EntityType<ClearItemFrameEntity> CLEAR_ITEM_FRAME = register(
            "clear_item_frame",
            EntityType.Builder.<ClearItemFrameEntity>of(
                            ClearItemFrameEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
    );

    public static final EntityType<TargetDummy> TARGET_DUMMY = register(
            "target_dummy",
            EntityType.Builder.of(TargetDummy::new, MobCategory.MISC)
                    .sized(0.5F, 1.975F).eyeHeight(1.7775F).clientTrackingRange(10)
    );

    public static final EntityType<SlingshotProjectile> SLINGSHOT_PROJECTILE = register(
            "slingshot_projectile",
            EntityType.Builder.<SlingshotProjectile>of(SlingshotProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    );

    // Moobloom: a flower cow. CREATURE + cow dimensions (main's 26.x AMBIENT/1x1 was loose).
    public static final EntityType<Moobloom> MOOBLOOM = register(
            "moobloom",
            EntityType.Builder.of(Moobloom::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.4F).eyeHeight(1.3F).passengerAttachments(1.36875F).clientTrackingRange(10)
    );

    public static final EntityType<Derelict> DERELICT = register(
            "derelict",
            EntityType.Builder.of(Derelict::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F).eyeHeight(1.74F).clientTrackingRange(8)
    );

    // Skeleton dimensions (1.99 tall) with the vanilla skeleton eye height.
    public static final EntityType<Drenched> DRENCHED = register(
            "drenched",
            EntityType.Builder.of(Drenched::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F).eyeHeight(1.74F).clientTrackingRange(8)
    );

    public static final EntityType<Rime> RIME = register(
            "rime",
            EntityType.Builder.of(Rime::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F).eyeHeight(1.74F).clientTrackingRange(8)
    );

    public static final EntityType<SlownessSnowball> SLOWNESS_SNOWBALL = register(
            "slowness_snowball",
            EntityType.Builder.<SlownessSnowball>of(SlownessSnowball::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    );

    public static final EntityType<SuspiciousSpider> SUSPICIOUS_SPIDER = register(
            "suspicious_spider",
            EntityType.Builder.of(SuspiciousSpider::new, MobCategory.MONSTER)
                    .sized(1.0F, 1.0F)
    );

    public static final EntityType<FireBomb> FIRE_BOMB = register(
            "fire_bomb",
            EntityType.Builder.<FireBomb>of(FireBomb::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    );

    public static final EntityType<WildfireEntity> WILDFIRE = register(
            "wildfire",
            EntityType.Builder.of(WildfireEntity::new, MobCategory.MONSTER)
                    .fireImmune().sized(0.75F, 1.975F).clientTrackingRange(8)
    );

    public static final SensorType<WildfireAttackablesSensor> WILDFIRE_ATTACK_ENTITY_SENSOR = Registry.register(
            BuiltInRegistries.SENSOR_TYPE, NekomasFixed.id("wildfire_attack_entity_sensor"), new SensorType<>(WildfireAttackablesSensor::new));

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE,
                ResourceKey.create(Registries.ENTITY_TYPE, NekomasFixed.id(id)),
                builder.build(id));
    }

    public static void registerEntityType() {
        FabricDefaultAttributeRegistry.register(TARGET_DUMMY, TargetDummy.createTargetDummyAttributes().build());
        FabricDefaultAttributeRegistry.register(MOOBLOOM, Moobloom.createAttributes());
        FabricDefaultAttributeRegistry.register(DERELICT, Zombie.createAttributes());
        FabricDefaultAttributeRegistry.register(DRENCHED, Drenched.createDrenchedAttributes());
        FabricDefaultAttributeRegistry.register(RIME, Zombie.createAttributes());
        FabricDefaultAttributeRegistry.register(SUSPICIOUS_SPIDER, SuspiciousSpider.createSuspiciousSpiderAttributes());
        FabricDefaultAttributeRegistry.register(WILDFIRE, WildfireEntity.createWildfireAttributes());
        SpawnPlacements.register(DERELICT, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(DRENCHED, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Drenched::canSpawn);
        SpawnPlacements.register(RIME, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(SUSPICIOUS_SPIDER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SuspiciousSpider::canSpawn);
        SpawnPlacements.register(WILDFIRE, SpawnPlacementTypes.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                WildfireEntity::canSpawn);
        NekomasFixed.LOGGER.info("Registering entity types");
    }
}
