package net.greenjab.nekomasfixed;

import net.fabricmc.api.ModInitializer;
import net.greenjab.nekomasfixed.network.SyncHandler;
import net.greenjab.nekomasfixed.registry.registries.*;
import net.greenjab.nekomasfixed.registry.worldgen.BiomeAdditions;
import net.greenjab.nekomasfixed.registry.worldgen.ModWorldGeneration;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NekomasFixed implements ModInitializer {
    public static final String MOD_NAME = "Nekomas' Fixed Minecraft";
    public static final String NAMESPACE = "nekomasfixed";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

    public static final int ECHOING_LAYER_LIMIT = 8;

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(NAMESPACE, path);
    }

    // Sums the level of any enchantment whose id contains the given name (e.g. "leeching").
    public static int enchantLevel(ItemStack stack, String name) {
        int level = 0;
        ItemEnchantments components = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (Holder<Enchantment> holder : stack.getEnchantments().keySet()) {
            if (holder.getRegisteredName().toLowerCase().contains(name.toLowerCase())) {
                level += components.getLevel(holder);
            }
        }
        return level;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("[{}] loaded (1.21.1 port, scaffold)", MOD_NAME);
        SyncHandler.init();
        ComponentRegistry.registerComponents();
        ArmorMaterialRegistry.registerArmorMaterials();
        EffectRegistry.registerEffects();
        ModTreeDecorators.register();
        ModTrunkPlacers.register();
        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();
        EntityTypeRegistry.registerEntityType();
        BiomeAdditions.addSpawns();
        BlockEntityTypeRegistry.registerBlockEntityTypes();
        RecipeRegistry.registerRecipes();
        EnchantmentRegistry.registerEnchantments();
        ItemGroupRegistry.registerItemGroup();
        BlockEntityRegistry.attachBlockEntities();
        ModWorldGeneration.generateModWorldGen();
        SoundEventRegistry.registerSoundEvents();
        LootTableModifierRegistry.registerLootTableModifiers();
        GlowingDecoratedPotPatternRegistry.registerGlowingDecoratedPotPatterns();
    }
}
