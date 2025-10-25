package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.FakeBoatEntity;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;
import java.util.function.Supplier;

public class EntityTypeRegistry {

    public static final EntityType<FakeBoatEntity> FAKE_MEGA_BOAT = register(
            "fake_mega_boat", EntityType.Builder.<FakeBoatEntity>create(FakeBoatEntity::new, SpawnGroup.MISC)
                    .dropsNothing().dimensions(2f, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    public static final EntityType<MegaBoatEntity> ACACIA_MEGA_BOAT = megaBoatFactory("acacia_mega_boat", () -> ItemRegistry.ACACIA_MEGA_BOAT);
    public static final EntityType<MegaBoatEntity> BAMBOO_MEGA_BOAT = megaBoatFactory("bamboo_mega_boat", () -> ItemRegistry.BAMBOO_MEGA_BOAT);
    public static final EntityType<MegaBoatEntity> BIRCH_MEGA_BOAT = megaBoatFactory("birch_mega_boat", () -> ItemRegistry.BIRCH_MEGA_BOAT);
    public static final EntityType<MegaBoatEntity> CHERRY_MEGA_BOAT = megaBoatFactory("cherry_mega_boat", () -> ItemRegistry.CHERRY_MEGA_BOAT);
    public static final EntityType<MegaBoatEntity> DARK_OAK_MEGA_BOAT = megaBoatFactory("dark_oak_mega_boat", () -> ItemRegistry.DARK_OAK_MEGA_BOAT);
    public static final EntityType<MegaBoatEntity> JUNGLE_MEGA_BOAT = megaBoatFactory("jungle_mega_boat", () -> ItemRegistry.JUNGLE_MEGA_BOAT);
    public static final EntityType<MegaBoatEntity> MANGROVE_MEGA_BOAT = megaBoatFactory("mangrove_mega_boat", () -> ItemRegistry.MANGROVE_MEGA_BOAT);
    public static final EntityType<MegaBoatEntity> OAK_MEGA_BOAT = megaBoatFactory("oak_mega_boat", () -> ItemRegistry.OAK_MEGA_BOAT);;
    public static final EntityType<MegaBoatEntity> PALE_OAK_MEGA_BOAT = megaBoatFactory("pale_oak_mega_boat", () -> ItemRegistry.PALE_OAK_MEGA_BOAT);
    public static final EntityType<MegaBoatEntity> SPRUCE_MEGA_BOAT = megaBoatFactory("spruce_mega_boat", () -> ItemRegistry.SPRUCE_MEGA_BOAT);

    public static List<EntityType<MegaBoatEntity>> megaBoats = List.of(ACACIA_MEGA_BOAT, BAMBOO_MEGA_BOAT, BIRCH_MEGA_BOAT, CHERRY_MEGA_BOAT, DARK_OAK_MEGA_BOAT, JUNGLE_MEGA_BOAT, MANGROVE_MEGA_BOAT, OAK_MEGA_BOAT, PALE_OAK_MEGA_BOAT, SPRUCE_MEGA_BOAT);
    public static List<EntityType<? extends AbstractBoatEntity>> boats = List.of(EntityType.ACACIA_BOAT, EntityType.BAMBOO_RAFT, EntityType.BIRCH_BOAT, EntityType.CHERRY_BOAT, EntityType.DARK_OAK_BOAT, EntityType.JUNGLE_BOAT, EntityType.MANGROVE_BOAT, EntityType.OAK_BOAT, EntityType.PALE_OAK_BOAT, EntityType.SPRUCE_BOAT);

    private static EntityType<MegaBoatEntity> megaBoatFactory(String id, Supplier<Item> item) {
        return register(
                id, EntityType.Builder.create(getMegaBoatFactory(item), SpawnGroup.MISC)
                        .dropsNothing().dimensions(2.6f, 0.5625F).eyeHeight(0.5625F).maxTrackingRange(10));
    }
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
        //OAK_MEGA_BOAT = megaBoatFactory("oak_mega_boat", ItemRegistry.OAK_MEGA_BOAT);
    }
}
