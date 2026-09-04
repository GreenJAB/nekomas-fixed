package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.entity.PotMaps;
import net.greenjab.nekomasfixed.registry.block.entity.TerracottaDecoratedPotBlockEntity;
import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.SpriteFacing;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(PotDecorations.class)
public class PotDecorationsMixin {

    @Inject(method = "addToTooltip", at = @At("HEAD"), cancellable = true)
    private void addToCustomTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components, CallbackInfo ci) {
        PotDecorations decorations = (PotDecorations)(Object)this;
        PotEngravingDecoration engravingDecorations = components.get(ComponentRegistry.POT_ENGRAVED_DECORATIONS) == null ? PotEngravingDecoration.EMPTY :
                components.get(ComponentRegistry.POT_ENGRAVED_DECORATIONS);

        if (!decorations.equals(PotDecorations.EMPTY) ) {
                consumer.accept(CommonComponents.EMPTY);
                addCustomSideDetailsToTooltip(consumer, decorations.front(), engravingDecorations.getFront(), SpriteFacing.BACK);
                addCustomSideDetailsToTooltip(consumer, decorations.left(), engravingDecorations.getLeft(), SpriteFacing.LEFT);
                addCustomSideDetailsToTooltip(consumer, decorations.right(), engravingDecorations.getRight(), SpriteFacing.RIGHT);
                addCustomSideDetailsToTooltip(consumer, decorations.back(), engravingDecorations.getBack(), SpriteFacing.FRONT);

        }
        ci.cancel();
    }

    @Unique
    private static void addCustomSideDetailsToTooltip(final Consumer<Component> consumer, final Optional<Item> side, Item item, SpriteFacing facing) {
        Integer formatting = PotMaps.ITEM_TO_CHAT_FORMATTING_MAPPING.get(item);
        consumer.accept( Component.translatable("terracotta_decorated_pot."+facing.toString()).append(new ItemStack(side.orElse(Items.BRICK), 1).getHoverName()
                .plainCopy().withColor(formatting != null ? formatting : TextColor.GRAY.getValue())
                .withStyle(formatting != null ? ChatFormatting.BOLD : ChatFormatting.GRAY)));
    }

}


