package net.greenjab.nekomasfixed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.greenjab.nekomasfixed.mixin.accessor.MobAccessor;
import net.greenjab.nekomasfixed.network.SyncHandler;
import net.greenjab.nekomasfixed.registry.block.cauldron.CauldronBehaviour;
import net.greenjab.nekomasfixed.registry.other.DyedBrushBehaviour;
import net.minecraft.world.level.block.DispenserBlock;
import net.greenjab.nekomasfixed.registry.registries.*;
import net.greenjab.nekomasfixed.registry.worldgen.BiomeAdditions;
import net.greenjab.nekomasfixed.registry.worldgen.ModWorldGeneration;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
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
        CauldronBehaviour.register();
        ItemRegistry.registerItems();
        ItemRegistry.DYED_BRUSHES.values().forEach(brush -> DispenserBlock.registerBehavior(brush, new DyedBrushBehaviour()));
        ScreenHandlerRegistry.registerScreenHandlers();
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

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Monster monster && !(entity instanceof Creeper)) {
                ((MobAccessor) monster).getGoalSelector().addGoal(1, new AvoidEntityGoal<>(
                        monster,
                        Ocelot.class,
                        target -> target instanceof Ocelot ocelot && ocelot.isTrusting(),
                        8.0F,
                        1.0D,
                        1.3D,
                        livingEntity -> true
                ));
            }
        });
    }
}
