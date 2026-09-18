package net.greenjab.nekomasfixed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.greenjab.nekomasfixed.NekomasFixed;
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
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

// Data-driven enchantments, registered like a dynamic registry so the enchantment
// tag provider can reference them. supported_items + exclusive_set are built from
// the vanilla tags so the emitted JSON keeps the `#tag` references.
public class ModEnchantmentProvider extends FabricDynamicRegistryProvider {
    public ModEnchantmentProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public @NonNull String getName() {
        return "nekomasfixed-enchantments";
    }

    @Override
    protected void configure(HolderLookup.Provider registriesFuture, Entries entries) {
        HolderLookup.Provider lookups = entries.getLookups();
        HolderSet<Item> swordItems = lookups.lookupOrThrow(Registries.ITEM)
                .getOrThrow(TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("enchantable/sword")));
        HolderSet<Enchantment> bowExclusive = lookups.lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(TagKey.create(Registries.ENCHANTMENT, ResourceLocation.withDefaultNamespace("exclusive_set/bow")));
        Enchantment leeching = new Enchantment(
                Component.translatable("enchantment.nekomasfixed.leeching"),
                Enchantment.definition(swordItems, HolderSet.empty(), 5, 3,
                        Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(20, 10), 6,
                        EquipmentSlotGroup.MAINHAND),
                bowExclusive,
                DataComponentMap.EMPTY);
        entries.add(EnchantmentRegistry.LEECHING, leeching);

        HolderSet<Item> slingshotItems = lookups.lookupOrThrow(Registries.ITEM)
                .getOrThrow(TagKey.create(Registries.ITEM, NekomasFixed.id("enchantable/slingshot")));
        HolderSet<Enchantment> crossbowExclusive = lookups.lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(TagKey.create(Registries.ENCHANTMENT, ResourceLocation.withDefaultNamespace("exclusive_set/crossbow")));
        Enchantment shatter = new Enchantment(
                Component.translatable("enchantment.nekomasfixed.shatter"),
                Enchantment.definition(slingshotItems, HolderSet.empty(), 5, 1,
                        Enchantment.dynamicCost(20, 0), Enchantment.dynamicCost(50, 0), 8,
                        EquipmentSlotGroup.MAINHAND),
                crossbowExclusive,
                DataComponentMap.EMPTY);
        entries.add(EnchantmentRegistry.SHATTER, shatter);
    }
}