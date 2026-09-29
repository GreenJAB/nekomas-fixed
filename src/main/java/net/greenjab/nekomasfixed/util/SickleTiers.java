package net.greenjab.nekomasfixed.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

// 1.21.1 has no copper tool tier and no #X_tool_materials item tags, and vanilla
// Tiers bake a flat +N into a sword's base damage. Wrap each material's stats
// (durability, speed, enchantability, repair) in a Tier that reports the sickle's
// exact per-material attack bonus, so the item's ATTACK_DAMAGE modifier equals it.
public final class SickleTiers {
    public static final SickleTier WOODEN = new SickleTier(Tiers.WOOD, 1.0F, 10);
    public static final SickleTier STONE = new SickleTier(Tiers.STONE, 1.5F, 9);
    public static final SickleTier IRON = new SickleTier(Tiers.IRON, 2.0F, 8);
    public static final SickleTier GOLDEN = new SickleTier(Tiers.GOLD, 3.0F, 10);
    public static final SickleTier DIAMOND = new SickleTier(Tiers.DIAMOND, 4.5F, 7);
    public static final SickleTier NETHERITE = new SickleTier(Tiers.NETHERITE, 5.0F, 6);
    private static final TagKey<Block> NO_INCORRECT =
            TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("no_incorrect_blocks"));
    private static final Tier COPPER_BASE = new Tier() {
        @Override
        public int getUses() {
            return 200;
        }

        @Override
        public float getSpeed() {
            return 3.5F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0.0F;
        }

        @Override
        public @NonNull TagKey<Block> getIncorrectBlocksForDrops() {
            return NO_INCORRECT;
        }

        @Override
        public int getEnchantmentValue() {
            return 14;
        }

        @Override
        public @NonNull Ingredient getRepairIngredient() {
            return Ingredient.of(Items.COPPER_INGOT);
        }
    };
    public static final SickleTier COPPER = new SickleTier(COPPER_BASE, 1.15F, 9);

    // comboMultiplier mirrors main's (10 - the material's tool-tier attack bonus):
    // stronger materials trade base damage for a smaller stacking bonus.
    public record SickleTier(Tier base, float attackDamageBonus, int comboMultiplier) implements Tier {
        @Override
        public int getUses() {
            return base.getUses();
        }

        @Override
        public float getSpeed() {
            return base.getSpeed();
        }

        @Override
        public float getAttackDamageBonus() {
            return attackDamageBonus;
        }

        @Override
        public @NonNull TagKey<Block> getIncorrectBlocksForDrops() {
            return base.getIncorrectBlocksForDrops();
        }

        @Override
        public int getEnchantmentValue() {
            return base.getEnchantmentValue();
        }

        @Override
        public @NonNull Ingredient getRepairIngredient() {
            return base.getRepairIngredient();
        }
    }
}
