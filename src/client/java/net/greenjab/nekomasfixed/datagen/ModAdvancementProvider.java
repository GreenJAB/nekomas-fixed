package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

// Husbandry advancements migrated from the hand-written resources.
public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registriesFuture, Consumer<AdvancementHolder> consumer) {
        allFroglights(consumer);
        ancientDyes(consumer);
    }

    // Have all 20 froglights (3 vanilla + 17 mod) at once.
    // parent(ResourceLocation) is @Deprecated(forRemoval=true), but the Fabric datagen HolderLookup has
    // no minecraft:advancement registry, so the only non-deprecated parent(AdvancementHolder) can't be
    // resolved here — hence the explicit suppression. parent(ResourceLocation) is the clean option.
    @SuppressWarnings("removal")
    private void allFroglights(Consumer<AdvancementHolder> consumer) {
        ItemLike[] frogs = BlockDyeMap.FROGLIGHT.values().stream().map(Block::asItem).toArray(ItemLike[]::new);
        Advancement.Builder.advancement()
                .parent(ResourceLocation.withDefaultNamespace("husbandry/froglights"))
                .display(Items.PEARLESCENT_FROGLIGHT,
                        Component.translatable("advancements.husbandry.all_froglights.title"),
                        Component.translatable("advancements.husbandry.all_froglights.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .addCriterion("all_froglights", InventoryChangeTrigger.TriggerInstance.hasItems(frogs))
                .requirements(AdvancementRequirements.allOf(List.of("all_froglights")))
                .sendsTelemetryEvent()
                .save(consumer, NekomasFixed.id("husbandry/all_froglights").toString());
    }

    @SuppressWarnings("removal")
    private void ancientDyes(Consumer<AdvancementHolder> consumer) {
        Advancement.Builder.advancement()
                .parent(ResourceLocation.withDefaultNamespace("husbandry/plant_any_sniffer_seed"))
                .display(new ItemStack(ItemRegistry.MAROON_DYE),
                        Component.translatable("advancements.husbandry.ancient_dyes.title"),
                        Component.translatable("advancements.husbandry.ancient_dyes.description"),
                        null, AdvancementType.TASK, true, true, false)
                .addCriterion("ancient_dyes", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemRegistry.AMBER_DYE, ItemRegistry.AQUA_DYE, ItemRegistry.MAROON_DYE, ItemRegistry.INDIGO_DYE))
                .requirements(AdvancementRequirements.allOf(List.of("ancient_dyes")))
                .sendsTelemetryEvent()
                .save(consumer, NekomasFixed.id("husbandry/ancient_dyes").toString());
    }
}