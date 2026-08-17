package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.other.TippedArrowCustomComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.List;
import java.util.function.Consumer;

public class CustomArrowItem extends Item {
    public CustomArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);

        TippedArrowCustomComponent component =
                itemStack.get(ComponentRegistry.TIPPED_POTION_CONTENTS);

        if (component != null) {
//            builder.accept( component.getToolTip(context, builder, tooltipFlag));
            component.addToTooltip(
                    context,
                    builder,
                    tooltipFlag,
                    itemStack
            );
        }
    }





}
