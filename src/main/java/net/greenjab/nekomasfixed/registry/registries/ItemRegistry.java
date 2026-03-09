package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.item.*;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.util.HarnessHelper;
import net.greenjab.nekomasfixed.util.ModColors;
import net.greenjab.nekomasfixed.util.ModItemSettings;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Direction;
import net.minecraft.world.waypoint.Waypoint;


import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ItemRegistry {


    public static final Item CLAM = register(BlockRegistry.CLAM, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    public static final Item CLAM_BLUE = register(BlockRegistry.CLAM_BLUE, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    public static final Item CLAM_PINK = register(BlockRegistry.CLAM_PINK, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    public static final Item CLAM_PURPLE = register(BlockRegistry.CLAM_PURPLE, new Item.Settings().maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));

    public static final Item PEARL = register("pearl");
    public static final Item PEARL_BLOCK = register(BlockRegistry.PEARL_BLOCK);

    public static final Item GLOW_TORCH = register(
            BlockRegistry.GLOW_TORCH,
            ((block, settings) -> new VerticallyAttachableBlockItem(block, BlockRegistry.GLOW_WALL_TORCH, Direction.DOWN, settings))
    );

    public static final Item BIG_ACACIA_BOAT = register("big_acacia_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_ACACIA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_BAMBOO_BOAT = register("big_bamboo_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_BAMBOO_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_BIRCH_BOAT = register("big_birch_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_BIRCH_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_CHERRY_BOAT = register("big_cherry_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_CHERRY_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_DARK_OAK_BOAT = register("big_dark_oak_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_DARK_OAK_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_JUNGLE_BOAT = register("big_jungle_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_JUNGLE_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_MANGROVE_BOAT = register("big_mangrove_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_MANGROVE_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_OAK_BOAT = register("big_oak_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_OAK_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_PALE_OAK_BOAT = register("big_pale_oak_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_PALE_OAK_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item BIG_SPRUCE_BOAT = register("big_spruce_boat", settings -> new BoatItem(EntityTypeRegistry.BIG_SPRUCE_BOAT, settings), new Item.Settings().maxCount(1));

    public static final Item HUGE_ACACIA_BOAT = register("huge_acacia_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_ACACIA_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_BAMBOO_BOAT = register("huge_bamboo_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_BAMBOO_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_BIRCH_BOAT = register("huge_birch_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_BIRCH_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_CHERRY_BOAT = register("huge_cherry_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_CHERRY_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_DARK_OAK_BOAT = register("huge_dark_oak_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_DARK_OAK_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_JUNGLE_BOAT = register("huge_jungle_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_JUNGLE_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_MANGROVE_BOAT = register("huge_mangrove_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_MANGROVE_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_OAK_BOAT = register("huge_oak_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_OAK_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_PALE_OAK_BOAT = register("huge_pale_oak_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_PALE_OAK_BOAT, settings), new Item.Settings().maxCount(1));
    public static final Item HUGE_SPRUCE_BOAT = register("huge_spruce_boat", settings -> new BoatItem(EntityTypeRegistry.HUGE_SPRUCE_BOAT, settings), new Item.Settings().maxCount(1));

    public static final Item BOAT_UPGRADE_TEMPLATE = register(
            "boat_upgrade_template", settings -> new SmithingTemplateItem(
                    Text.translatable(
                            Util.createTranslationKey("item", NekomasFixed.id("boat"))
                            ).formatted(Formatting.BLUE),
                    Text.translatable(
                            Util.createTranslationKey("item", NekomasFixed.id("planks"))
                    ).formatted(Formatting.BLUE),
                    Text.of(""),
                    Text.of(""),
                    List.of(NekomasFixed.id("container/slot/boat")),
                    List.of(NekomasFixed.id("container/slot/planks")),
                    settings),new Item.Settings().rarity(Rarity.UNCOMMON)
    );

    public static final Item GLISTERING_MELON = register(BlockRegistry.GLISTERING_MELON, new Item.Settings());

    public static final Item NAUTILUS_BLOCK = register(BlockRegistry.NAUTILUS_BLOCK, new Item.Settings().maxCount(64).component(OtherRegistry.ANIMAL, AnimalComponent.DEFAULT));
    public static final Item ZOMBIE_NAUTILUS_BLOCK = register(BlockRegistry.ZOMBIE_NAUTILUS_BLOCK, new Item.Settings().maxCount(64).component(OtherRegistry.ANIMAL, AnimalComponent.DEFAULT));
    public static final Item CORAL_NAUTILUS_BLOCK = register(BlockRegistry.CORAL_NAUTILUS_BLOCK, new Item.Settings().maxCount(64).component(OtherRegistry.ANIMAL, AnimalComponent.DEFAULT));

    public static final Item TURTLE_CHESTPLATE = register("turtle_chestplate", new Item.Settings().armor(ArmorMaterials.TURTLE_SCUTE, EquipmentType.CHESTPLATE));
    public static final Item TURTLE_LEGGINGS = register("turtle_leggings", new Item.Settings().armor(ArmorMaterials.TURTLE_SCUTE, EquipmentType.LEGGINGS));
    public static final Item TURTLE_BOOTS = register("turtle_boots", new Item.Settings().armor(ArmorMaterials.TURTLE_SCUTE, EquipmentType.BOOTS));

    public static final Item WILD_FIRE_SMITHING_TEMPLATE = register(
            "wild_fire_smithing_template", SmithingTemplateItem::of, new Item.Settings().rarity(Rarity.UNCOMMON).fireproof()
    );


//    ------------------------------------------------------------------------------------------



    public static final Item TARGET_DUMMY = register("target_dummy", TargetDummyItem::new, new Item.Settings().maxCount(1));

    public static final Item ENDERMAN_HEAD = register(BlockRegistry.ENDERMAN_HEAD,
            (block, settings) -> new VerticallyAttachableBlockItem(
            block, BlockRegistry.WALL_ENDERMAN_HEAD, Direction.DOWN, Waypoint.disableTracking(settings)
    ),new Item.Settings().rarity(Rarity.UNCOMMON).equippableUnswappable(EquipmentSlot.HEAD));

    public static final Item SLINGSHOT = register("slingshot", SlingshotItem::new, new Item.Settings().maxDamage(384));
    public static final Item NETHER_HEART = register("nether_heart",  new Item.Settings().fireproof().rarity(Rarity.UNCOMMON));
    public static final Item SOULFIRE_TRIDENT = register("soulfire_trident", SoulfireTridentItem::new, new Item.Settings()
            .rarity(Rarity.RARE)
            .maxDamage(250)
            .attributeModifiers(SoulfireTridentItem.createAttributeModifiers())
            .repairable(ItemTags.REPAIRS_NETHERITE_ARMOR)
            .component(DataComponentTypes.TOOL, SoulfireTridentItem.createToolComponent())
            .enchantable(1)
            .component(DataComponentTypes.WEAPON, new WeaponComponent(1)).fireproof());
    public static final Item SOULFIRE_SHIELD = register(
            "soulfire_shield",
            SoulfireShieldItem::new,
            new Item.Settings()
                    .rarity(Rarity.RARE)
                    .maxDamage(336)
                    .repairable(ItemTags.REPAIRS_NETHERITE_ARMOR)
                    .equippableUnswappable(EquipmentSlot.OFFHAND)
                    .component(
                            DataComponentTypes.BLOCKS_ATTACKS,
                            new BlocksAttacksComponent(
                                    0.25F,
                                    1.0F,
                                    List.of(new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                                    new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F),
                                    Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                                    Optional.of(SoundEvents.ITEM_SHIELD_BLOCK),
                                    Optional.of(SoundEvents.ITEM_SHIELD_BREAK)
                            )
                    )
                    .component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK).fireproof()
    );
    public static final Item WILD_FIRE_SPAWN_EGG = registerSpawnEgg(EntityTypeRegistry.WILD_FIRE);



    public static final Item AMBER_DYE = registerDye("amber_dye", DyeColor.YELLOW);
    public static final Item INDIGO_DYE = registerDye("indigo_dye", DyeColor.PURPLE);
    public static final Item CRIMSON_DYE = registerDye("crimson_dye", DyeColor.RED);
    public static final Item AQUA_DYE = registerDye("aqua_dye", DyeColor.LIGHT_BLUE);

    public static final Item WOODEN_SICKLE = register("wooden_sickle", SickleItem::new, ModItemSettings.sickle(ToolMaterial.WOOD, SickleItem.SPEED));
    public static final Item STONE_SICKLE = register("stone_sickle", SickleItem::new, ModItemSettings.sickle(ToolMaterial.STONE, SickleItem.SPEED));
    public static final Item COPPER_SICKLE = register("copper_sickle", SickleItem::new, ModItemSettings.sickle(ToolMaterial.COPPER, SickleItem.SPEED));
    public static final Item IRON_SICKLE = register("iron_sickle", SickleItem::new, ModItemSettings.sickle(ToolMaterial.IRON, SickleItem.SPEED));
    public static final Item GOLDEN_SICKLE = register("golden_sickle", SickleItem::new, ModItemSettings.sickle(ToolMaterial.GOLD, SickleItem.SPEED));
    public static final Item DIAMOND_SICKLE = register("diamond_sickle", SickleItem::new, ModItemSettings.sickle(ToolMaterial.DIAMOND, SickleItem.SPEED));
    public static final Item NETHERITE_SICKLE = register("netherite_sickle", SickleItem::new, ModItemSettings.sickle(ToolMaterial.NETHERITE, SickleItem.SPEED).fireproof());

    public static final Item AMBER_TERRACOTTA = register(BlockRegistry.AMBER_TERRACOTTA);
    public static final Item INDIGO_TERRACOTTA = register(BlockRegistry.INDIGO_TERRACOTTA);
    public static final Item AQUA_TERRACOTTA = register(BlockRegistry.AQUA_TERRACOTTA);
    public static final Item CRIMSON_TERRACOTTA = register(BlockRegistry.CRIMSON_TERRACOTTA);

    public static final Item KILN = register(BlockRegistry.KILN);

    public static final Item AQUA_GLAZED_TERRACOTTA = register(BlockRegistry.AQUA_GLAZED_TERRACOTTA);
    public static final Item AMBER_GLAZED_TERRACOTTA = register(BlockRegistry.AMBER_GLAZED_TERRACOTTA);
    public static final Item CRIMSON_GLAZED_TERRACOTTA = register(BlockRegistry.CRIMSON_GLAZED_TERRACOTTA);
    public static final Item INDIGO_GLAZED_TERRACOTTA = register(BlockRegistry.INDIGO_GLAZED_TERRACOTTA);

    public static final Item AQUA_WOOL = register(BlockRegistry.AQUA_WOOL);
    public static final Item AMBER_WOOL = register(BlockRegistry.AMBER_WOOL);
    public static final Item CRIMSON_WOOL = register(BlockRegistry.CRIMSON_WOOL);
    public static final Item INDIGO_WOOL = register(BlockRegistry.INDIGO_WOOL);

    public static final Item AQUA_CONCRETE = register(BlockRegistry.AQUA_CONCRETE);
    public static final Item AMBER_CONCRETE = register(BlockRegistry.AMBER_CONCRETE);
    public static final Item CRIMSON_CONCRETE = register(BlockRegistry.CRIMSON_CONCRETE);
    public static final Item INDIGO_CONCRETE = register(BlockRegistry.INDIGO_CONCRETE);

    public static final Item AQUA_CONCRETE_POWDER = register(BlockRegistry.AQUA_CONCRETE_POWDER);
    public static final Item AMBER_CONCRETE_POWDER = register(BlockRegistry.AMBER_CONCRETE_POWDER);
    public static final Item CRIMSON_CONCRETE_POWDER = register(BlockRegistry.CRIMSON_CONCRETE_POWDER);
    public static final Item INDIGO_CONCRETE_POWDER = register(BlockRegistry.INDIGO_CONCRETE_POWDER);

    public static final Item AQUA_CARPET = register(BlockRegistry.AQUA_CARPET);
    public static final Item AMBER_CARPET = register(BlockRegistry.AMBER_CARPET);
    public static final Item CRIMSON_CARPET = register(BlockRegistry.CRIMSON_CARPET);
    public static final Item INDIGO_CARPET = register(BlockRegistry.INDIGO_CARPET);

    public static final Item AMBER_HARNESS = register("amber_harness",
            (new Item.Settings())
                    .maxCount(1)
                    .component(DataComponentTypes.EQUIPPABLE,
                            HarnessHelper.ofHarness(ModColors.AMBER)));

    public static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }

    public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register(keyOf(id), factory, settings);
    }
    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, NekomasFixed.id(id));
    }
    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }
        return Registry.register(Registries.ITEM, key, item);
    }

    public static Item register(Block block) {
        return register(block, BlockItem::new, new Item.Settings());
    }
    public static Item register(String id) {
        return register(keyOf(id), Item::new, new Item.Settings());
    }
    public static Item register(Block block, Item.Settings settings) {
        return register(block, BlockItem::new, settings);
    }
    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory) {
        return register(block, factory, new Item.Settings());
    }
    public static DyeItem registerDye(String id, DyeColor color) {
        return (DyeItem) register(
                keyOf(id),
                settings -> new DyeItem(color, settings),
                new Item.Settings()
        );
    }
    public static void registerItems() {
        NekomasFixed.LOGGER.info("Registering items...");

        try {
            Class.forName(ItemRegistry.class.getName());
            NekomasFixed.LOGGER.info("Items registered successfully");
        } catch (ClassNotFoundException e) {
            NekomasFixed.LOGGER.error("Failed to register items", e);
        }
    }
    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
        return register(
                keyOf(block.getRegistryEntry().registryKey()),
                itemSettings -> factory.apply(block, itemSettings),
                settings.useBlockPrefixedTranslationKey()
        );
    }
    private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
        return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
    }
    private static Item registerSpawnEgg(EntityType<?> type) {
        return register(
                RegistryKey.of(RegistryKeys.ITEM, EntityType.getId(type).withSuffixedPath("_spawn_egg")), SpawnEggItem::new, new Item.Settings().spawnEgg(type)
        );
    }






}
