package net.greenjab.nekomasfixed.mixin;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.ModColors;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.FireworkStarRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

//Note for GREEN_JAB: Plz check this class once, i am not sure if it going to override vanilla logic and give support to the trials and effects for fireworks
@Mixin(FireworkStarRecipe.class)
public class FireworkStarRecipeMixin {
    @Unique
    private boolean isColorFromMod(Item item){
        if (
                item.equals(ItemRegistry.AMBER_DYE)
                ||
                item.equals(ItemRegistry.CRIMSON_DYE)
                ||
                item.equals(ItemRegistry.INDIGO_DYE)
                ||
                item.equals(ItemRegistry.AQUA_DYE)
        ) {
           return true;
        }return false;
    }

    @Inject(method = "matches(Lnet/minecraft/recipe/input/CraftingRecipeInput;Lnet/minecraft/world/World;)Z", at = @At("HEAD"), cancellable = true)
    private void matches(CraftingRecipeInput craftingRecipeInput, World world, CallbackInfoReturnable<Boolean> cir) {
        if (craftingRecipeInput.getStackCount() < 2) {
            cir.setReturnValue(false);
        } else {
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;

            for(int i = 0; i < craftingRecipeInput.size(); ++i) {
                ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
                final Map<Item, FireworkExplosionComponent.Type> TYPE_MODIFIER_MAP;
                final Ingredient TRAIL_MODIFIER;
                final Ingredient FLICKER_MODIFIER;

                TYPE_MODIFIER_MAP = Map.of(Items.FIRE_CHARGE, FireworkExplosionComponent.Type.LARGE_BALL, Items.FEATHER, FireworkExplosionComponent.Type.BURST, Items.GOLD_NUGGET, FireworkExplosionComponent.Type.STAR, Items.SKELETON_SKULL, FireworkExplosionComponent.Type.CREEPER, Items.WITHER_SKELETON_SKULL, FireworkExplosionComponent.Type.CREEPER, Items.CREEPER_HEAD, FireworkExplosionComponent.Type.CREEPER, Items.PLAYER_HEAD, FireworkExplosionComponent.Type.CREEPER, Items.DRAGON_HEAD, FireworkExplosionComponent.Type.CREEPER, Items.ZOMBIE_HEAD, FireworkExplosionComponent.Type.CREEPER, Items.PIGLIN_HEAD, FireworkExplosionComponent.Type.CREEPER);
                TRAIL_MODIFIER = Ingredient.ofItem(Items.DIAMOND);
                FLICKER_MODIFIER = Ingredient.ofItem(Items.GLOWSTONE_DUST);

                final Ingredient GUNPOWDER = Ingredient.ofItem(Items.GUNPOWDER);
                if (!itemStack.isEmpty()) {


                    if (TYPE_MODIFIER_MAP.containsKey(itemStack.getItem())) {
                        if (bl3) {
                            cir.setReturnValue(false);
                        }

                        bl3 = true;
                    } else if (FLICKER_MODIFIER.test(itemStack)) {
                        if (bl5) {
                            cir.setReturnValue(false);

                        }

                        bl5 = true;
                    } else if (TRAIL_MODIFIER.test(itemStack)) {
                        if (bl4) {
                            cir.setReturnValue(false);

                        }

                        bl4 = true;
                    } else if (GUNPOWDER.test(itemStack)) {
                        if (bl) {
                            cir.setReturnValue(false);

                        }

                        bl = true;
                    } else {
                        if (!(itemStack.getItem() instanceof DyeItem && isColorFromMod(itemStack.getItem()))) {
                            cir.setReturnValue(false);
                        }

                        bl2 = true;
                    }
                }
            }

            cir.setReturnValue(bl&&bl2);

        }
    }

    @Inject(method = "craft" , at = @At("TAIL"), cancellable = true)
    private void craftCustom(CraftingRecipeInput craftingRecipeInput, World world, CallbackInfoReturnable<ItemStack> cir) {
        FireworkExplosionComponent.Type type = FireworkExplosionComponent.Type.SMALL_BALL;
        boolean bl = false;
        boolean bl2 = false;
        IntList intList = new IntArrayList();

        for(int i = 0; i < craftingRecipeInput.size(); ++i) {
            ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                    Item var11 = itemStack.getItem();

                    if(isColorFromMod(var11)){
                        System.out.println("WOOWWWW! How exciting it is!! Your craft method of fireworkstarrecipemixin works which no one care about, yeah!!!");
                        ModColors modColors = ModColors.AMBER;
                        if(var11.equals(ItemRegistry.AQUA_DYE)){
                            modColors = ModColors.AQUA;
                        }if(var11.equals(ItemRegistry.CRIMSON_DYE)){
                            modColors = ModColors.CRIMSON;
                        }if(var11.equals(ItemRegistry.INDIGO_DYE)){
                            modColors = ModColors.INDIGO;
                        }
                        intList.add(modColors.getColor());
                    }
                    else if(var11 instanceof DyeItem) {
                        DyeItem dyeItem = (DyeItem)var11;
                        intList.add(dyeItem.getColor().getFireworkColor());
                    }

            }
        }

        ItemStack itemStack2 = new ItemStack(Items.FIREWORK_STAR);
        itemStack2.set(DataComponentTypes.FIREWORK_EXPLOSION, new FireworkExplosionComponent(type, intList, IntList.of(), bl2, bl));
        cir.setReturnValue(itemStack2);
    }
}
