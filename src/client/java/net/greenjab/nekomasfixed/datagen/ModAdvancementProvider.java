package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.greenjab.nekomasfixed.registry.advancement.AdvanceLiterals;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.greenjab.nekomasfixed.registry.registries.TrimRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.greenjab.nekomasfixed.util.ModItemLists;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.RecipeCraftedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.TrimPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaAdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {

    public ModAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }


    private static HolderGetter<Item> items;
    private static HolderGetter<TrimPattern> patterns;

    private static final Component ROOT_TITLE = Component.empty()
            .append(Component.translatable("nekoma").withColor(TextColor.RED))
            .append(Component.literal(" "))
            .append(Component.translatable("fixed").withColor(TextColor.WHITE));

    @Override
    public void generateAdvancement(HolderLookup.@NonNull Provider wrapperLookup, @NonNull Consumer<AdvancementHolder> consumer) {
        items = wrapperLookup.lookupOrThrow(Registries.ITEM);
        patterns =  wrapperLookup.lookupOrThrow(Registries.TRIM_PATTERN);

        AdvancementHolder root = Advancement.Builder.advancement()
                .rootDisplay(
                        ItemRegistry.TARGET_DUMMY,
                        ROOT_TITLE,
                        AdvanceLiterals.desc("root"),
                        Identifier.withDefaultNamespace("gui/advancements/backgrounds/stone"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE))
                .build(Identifier.fromNamespaceAndPath("nekomasfixed", "mod/root"));
        consumer.accept(root);

        AdvancementHolder turtleMaster = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.TURTLE_CHESTPLATE,
                        AdvanceLiterals.title("turtle_master"),
                        AdvanceLiterals.desc("turtle_master"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("turtle_master", InventoryChangeTrigger.TriggerInstance.hasItems(
                        Items.TURTLE_HELMET,
                        ItemRegistry.TURTLE_CHESTPLATE,
                        ItemRegistry.TURTLE_BOOTS,
                        ItemRegistry.TURTLE_LEGGINGS)
                ).build(getID("turtle_master"));
        consumer.accept(turtleMaster);

        Advancement.Builder rainBowSpottedWoolBuilder = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.WHITE_SPOTTED_WOOL,
                        AdvanceLiterals.title("rainbow_spotted_wool"),
                        AdvanceLiterals.desc("rainbow_spotted_wool"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                );
        fillObtainCriterionList(rainBowSpottedWoolBuilder, ModItemLists.SPOTTED_WOOL);
        consumer.accept(rainBowSpottedWoolBuilder.build(getID("rainbow_spotted_wool")));

        Advancement.Builder shipOfThesus = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.HUGE_OAK_BOAT,
                        AdvanceLiterals.title("ship_of_thesus"),
                        AdvanceLiterals.desc("ship_of_thesus"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                );
        fillObtainCriterionList(shipOfThesus, ModItemLists.HUGE_BOATS);
        shipOfThesus.requirements(AdvancementRequirements.Strategy.OR);
        consumer.accept(shipOfThesus.build(getID("ship_of_thesus")));

        AdvancementHolder imTheRuler = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.CROWN_SMITHING_TEMPLATE,
                        AdvanceLiterals.title("i_am_the_ruler"),
                        AdvanceLiterals.desc("i_am_the_ruler"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                ).addCriterion("has_trimmed_iron_crown", checkTrimmed(ItemRegistry.IRON_CROWN, TrimRegistry.JEWEL))
                .addCriterion("has_trimmed_golden_crown", checkTrimmed(ItemRegistry.GOLDEN_CROWN, TrimRegistry.JEWEL))
                .addCriterion("has_trimmed_diamond_crown", checkTrimmed(ItemRegistry.DIAMOND_CROWN, TrimRegistry.JEWEL))
                .addCriterion("has_trimmed_copper_crown", checkTrimmed(ItemRegistry.COPPER_CROWN, TrimRegistry.JEWEL))
                .addCriterion("has_trimmed_netherite_crown", checkTrimmed(ItemRegistry.NETHERITE_CROWN, TrimRegistry.JEWEL))
                .requirements(AdvancementRequirements.Strategy.OR)
                .build(getID("i_am_the_ruler"))
                ;

        consumer.accept(imTheRuler);

        AdvancementHolder imOnFireNow = Advancement.Builder.advancement()
                .parent(imTheRuler)
                .display(
                        ItemRegistry.JEWEL_ARMOR_TRIM_SMITHING_TEMPLATE,
                        AdvanceLiterals.title("i_am_on_fire_now"),
                        AdvanceLiterals.desc("i_am_on_fire_now"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("has_trimmed_helmet", checkTrimmed(Items.NETHERITE_HELMET, TrimRegistry.JEWEL))
                .addCriterion("has_trimmed_crown", checkTrimmed(ItemRegistry.NETHERITE_CROWN, TrimRegistry.JEWEL))
                .addCriterion("has_trimmed_chestplate", checkTrimmed(Items.NETHERITE_CHESTPLATE, TrimRegistry.JEWEL))
                .addCriterion("has_trimmed_leggings", checkTrimmed(Items.NETHERITE_LEGGINGS, TrimRegistry.JEWEL))
                .addCriterion("has_trimmed_boots", checkTrimmed(Items.NETHERITE_BOOTS, TrimRegistry.JEWEL))
                .requirements(new AdvancementRequirements(List.of(
                        List.of("has_trimmed_helmet", "has_trimmed_crown"),
                        List.of("has_trimmed_chestplate"),
                        List.of("has_trimmed_leggings"),
                        List.of("has_trimmed_boots")
                )))
                .build(getID("i_am_on_fire_now"));

        consumer.accept(imOnFireNow);

        Advancement.Builder sliceAndDice = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.IRON_SICKLE,
                        AdvanceLiterals.title("slice_&_dice"),
                        AdvanceLiterals.desc("slice_&_dice"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                );
                fillObtainCriterionList(sliceAndDice, ModItemLists.SICKLES);
                sliceAndDice.requirements(AdvancementRequirements.Strategy.OR);
                consumer.accept(sliceAndDice.build(getID("slice_and_dice")));

        Advancement.Builder spooky = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.WITHER_ROSE_FLOWER_CROWN,
                        AdvanceLiterals.title("spooky"),
                        AdvanceLiterals.desc("spooky"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("spooky", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WITHER_ROSE_FLOWER_CROWN));
        consumer.accept(spooky.build(getID("spooky")));

        Advancement.Builder aBowButWorse = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.SLINGSHOT,
                        AdvanceLiterals.title("a_bow_but_worse"),
                        AdvanceLiterals.desc("a_bow_but_worse"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("obtained_slingshot", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SLINGSHOT));

        consumer.accept(aBowButWorse.build(getID("a_bow_but_worse")));

        Advancement.Builder hollowKnight = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.HOLLOW_BIRCH_LOG,
                        AdvanceLiterals.title("hollow_knight"),
                        AdvanceLiterals.desc("hollow_knight"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                );
        fillObtainCriterionList(hollowKnight, ModItemLists.ALL_HOLLOW_LOGS);
        consumer.accept(hollowKnight.build(getID("hollow_knight")));

        Advancement.Builder flowerPower = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.ALLIUM_FLOWER_CROWN,
                        AdvanceLiterals.title("flower_power"),
                        AdvanceLiterals.desc("flower_power"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                );
        fillObtainCriterionList(flowerPower, ModItemLists.FLOWER_CROWNS);
        consumer.accept(flowerPower.build(getID("flower_power")));

        Advancement.Builder prismaniac = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ItemRegistry.RED_PRISM,
                        AdvanceLiterals.title("prismaniac"),
                        AdvanceLiterals.desc("prismaniac"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                );
        fillObtainCriterionList(prismaniac, ModItemLists.ALL_PRISMS);
        consumer.accept(prismaniac.build(getID("prismaniac")));
    }

    private enum ArmorType{
        HELMET, CHESTPLATE, LEGGINGS, BOOTS, CROWN
    }

    private static Identifier getID(String name){
        return Identifier.fromNamespaceAndPath("nekomasfixed", "mod/"+name);
    }

    private static void fillObtainCriterionList(Advancement.Builder builder, List<Item> items) {
        for (ItemLike item : items) {
            Identifier id = BuiltInRegistries.ITEM.getKey(item.asItem());
            builder.addCriterion("obtained_" + id.getPath(),
                    InventoryChangeTrigger.TriggerInstance.hasItems(item));
        }
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> checkTrimmed(Item armorPiece, ResourceKey<TrimPattern> pattern){
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item()
                                .of(items, armorPiece)
                                .withComponents(DataComponentMatchers.Builder.components()
                                        .partial(DataComponentPredicates.ARMOR_TRIM,
                                                new TrimPredicate(
                                                        Optional.empty(),
                                                        Optional.of(HolderSet.direct(patterns.getOrThrow(pattern)))))
                                        .build()));

    }

    private static Item armorItem(ArmorType type) {
        return switch (type) {
            case HELMET -> Items.NETHERITE_HELMET;
            case CHESTPLATE -> Items.NETHERITE_CHESTPLATE;
            case LEGGINGS -> Items.NETHERITE_LEGGINGS;
            case BOOTS -> Items.NETHERITE_BOOTS;
            case CROWN -> ItemRegistry.NETHERITE_CROWN;
        };
    }

    private static void fillTrimmedArmorCriterion(Advancement.Builder builder, ArmorType type, ResourceKey<TrimPattern> pattern) {
        builder.addCriterion("has_trimmed_" + type.name().toLowerCase(),
                checkTrimmed(armorItem(type), pattern));
    }
}