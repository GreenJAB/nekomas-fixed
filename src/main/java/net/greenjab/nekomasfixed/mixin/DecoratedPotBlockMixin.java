package net.greenjab.nekomasfixed.mixin;

import com.mojang.datafixers.util.Pair;
import net.greenjab.nekomasfixed.target_access_class.DecoratedPotAccess;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Stream;

@Mixin(DecoratedPotBlock.class)
public class DecoratedPotBlockMixin {

    @Inject(
            method = "appendHoverText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/stream/Stream;of([Ljava/lang/Object;)Ljava/util/stream/Stream;"
            ),
            cancellable = true
    )
    private void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag, CallbackInfo ci) {
        PotDecorations decorations = stack.getOrDefault(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY);
        List<Boolean> glowOverrides = stack.getOrDefault(ComponentRegistry.SHERD_GLOW_OVERRIDES, ComponentRegistry.DEFAULT_SHERD_GLOW_OVERRIDES);
        Stream.of(
                new Pair<>(decorations.front(), DecoratedPotAccess.FRONT),
                new Pair<>(decorations.left(), DecoratedPotAccess.LEFT),
                new Pair<>(decorations.right(), DecoratedPotAccess.RIGHT),
                new Pair<>(decorations.back(), DecoratedPotAccess.BACK)
        ).forEach(
                pair -> tooltipComponents.add((new ItemStack(pair.getFirst().orElse(Items.BRICK), 1)).getHoverName().plainCopy().withStyle(glowOverrides.get(pair.getSecond()) ? ChatFormatting.WHITE : ChatFormatting.GRAY))
        );
        ci.cancel();
    }
}
