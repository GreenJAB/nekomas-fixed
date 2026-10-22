package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.other.PotFaceDecoration;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.DecoratedPotRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(DecoratedPotRecipe.class)
public class DecoratedPotRecipeMixin {

    @Inject(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void customAssemble(CraftingInput input, CallbackInfoReturnable<ItemStack> cir) {
        DecoratedPotRecipe recipe = (DecoratedPotRecipe)(Object)this;
        Optional<ItemStackTemplate> back = Optional.of(DecoratedPotRecipe.back(input)).filter(s -> !s.isEmpty()).map(ItemStackTemplate::fromStack);
        Optional<ItemStackTemplate> left = Optional.of(DecoratedPotRecipe.left(input)).filter(s -> !s.isEmpty()).map(ItemStackTemplate::fromStack);
        Optional<ItemStackTemplate> right = Optional.of(DecoratedPotRecipe.right(input)).filter(s -> !s.isEmpty()).map(ItemStackTemplate::fromStack);
        Optional<ItemStackTemplate> front = Optional.of(DecoratedPotRecipe.front(input)).filter(s -> !s.isEmpty()).map(ItemStackTemplate::fromStack);

        PotDecorations decorations = new PotDecorations(back, left, right, front);
        DataComponentPatch components = DataComponentPatch.builder().set(DataComponents.POT_DECORATIONS, decorations)
                .set(ComponentRegistry.POT_FACE_BLOCK, new PotFaceDecoration(Optional.of(Blocks.BRICKS.defaultBlockState())))
                .set(ComponentRegistry.POT_ENGRAVED_DECORATIONS, PotEngravingDecoration.EMPTY)
                .build();
         cir.setReturnValue(recipe.result.apply(components));
    }
}
