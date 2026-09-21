package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.BannerEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BannerItem.class)
public class BannerItemMixin {
    @Inject(method = "appendHoverTextFromBannerBlockEntityTag", at = @At("TAIL"))
    private static void appendTooltip(ItemStack stack, List<Component> tooltipComponents, CallbackInfo ci) {
        BannerEffects component = stack.getOrDefault(ComponentRegistry.BANNER_EFFECTS, BannerEffects.EMPTY);
        if (component.isGlowing()) {
            tooltipComponents.add(Component.translatable("block.minecraft.banner.nekomasfixed.glowing").withStyle(ChatFormatting.WHITE));
        }
        if (component.isBackgroundHidden()) {
            tooltipComponents.add(Component.translatable("block.minecraft.banner.nekomasfixed.hide_background").withStyle(ChatFormatting.WHITE));
        }
    }
}
