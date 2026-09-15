package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.greenjab.nekomasfixed.registry.registries.EnchantmentRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

// Data-driven enchantments, registered like a dynamic registry so the enchantment
// tag provider can reference them. supported_items + exclusive_set are built from
// the vanilla tags so the emitted JSON keeps the `#tag` references.
public class ModEnchantmentProvider extends FabricDynamicRegistryProvider {
    public ModEnchantmentProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "nekomasfixed-enchantments";
    }

    @Override
    protected void configure(HolderLookup.Provider registriesFuture, Entries entries) {
        HolderLookup.Provider lookups = entries.getLookups();
        HolderSet<Item> supportedItems = lookups.lookupOrThrow(Registries.ITEM)
                .getOrThrow(TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("enchantable/sword")));
        HolderSet<Enchantment> exclusiveSet = lookups.lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(TagKey.create(Registries.ENCHANTMENT, ResourceLocation.withDefaultNamespace("exclusive_set/bow")));
        Enchantment leeching = new Enchantment(
                Component.translatable("enchantment.nekomasfixed.leeching"),
                Enchantment.definition(supportedItems, HolderSet.empty(), 5, 3,
                        Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(20, 10), 6,
                        EquipmentSlotGroup.MAINHAND),
                exclusiveSet,
                DataComponentMap.EMPTY);
        entries.add(EnchantmentRegistry.LEECHING, leeching);
    }
}