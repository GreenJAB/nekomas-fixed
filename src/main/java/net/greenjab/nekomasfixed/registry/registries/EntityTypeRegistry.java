package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.BaobabBoat;
import net.greenjab.nekomasfixed.registry.entity.BaobabChestBoat;
import net.greenjab.nekomasfixed.registry.entity.ClearItemFrameEntity;
import net.greenjab.nekomasfixed.registry.entity.SlingshotProjectile;
import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
import net.greenjab.nekomasfixed.registry.entity.moobloom.Moobloom;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

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

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE,
                ResourceKey.create(Registries.ENTITY_TYPE, NekomasFixed.id(id)),
                builder.build(id));
    }

    public static void registerEntityType() {
        FabricDefaultAttributeRegistry.register(TARGET_DUMMY, TargetDummy.createTargetDummyAttributes().build());
        FabricDefaultAttributeRegistry.register(MOOBLOOM, Moobloom.createAttributes());
        NekomasFixed.LOGGER.info("Registering entity types");
    }
}
