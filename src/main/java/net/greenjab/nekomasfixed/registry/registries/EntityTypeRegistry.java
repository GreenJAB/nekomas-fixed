package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class EntityTypeRegistry {

    public static final EntityType<MegaBoatEntity> OAK_MEGA_BOAT = register(
            "oak_mega_boat",
            EntityType.Builder.create(getMegaBoatFactory(() -> ItemRegistry.OAK_MEGA_BOAT), SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .maxTrackingRange(10)
    );

    private static EntityType.EntityFactory<MegaBoatEntity> getMegaBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new MegaBoatEntity(type, world, itemSupplier);
    }



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
    }
}
