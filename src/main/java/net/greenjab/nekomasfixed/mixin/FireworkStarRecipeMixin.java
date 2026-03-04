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
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import oshi.jna.platform.mac.SystemConfiguration;

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
    @Inject(method = "craft" , at = @At("HEAD"), cancellable = true)
    public void craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup, CallbackInfoReturnable<ItemStack> cir) {
        FireworkExplosionComponent.Type type = FireworkExplosionComponent.Type.SMALL_BALL;
        boolean bl = false;
        boolean bl2 = false;
        IntList intList = new IntArrayList();

        for(int i = 0; i < craftingRecipeInput.size(); ++i) {
            ItemStack itemStack = craftingRecipeInput.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                    Item var11 = itemStack.getItem();

                    if(isColorFromMod(var11)){
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
