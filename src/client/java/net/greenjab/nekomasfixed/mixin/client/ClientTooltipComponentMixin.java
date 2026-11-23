package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.other.AnimalTooltipData;
import net.greenjab.nekomasfixed.registry.other.ContainerTooltipData;
import net.greenjab.nekomasfixed.render.other.AnimalTooltipComponent;
import net.greenjab.nekomasfixed.render.other.ContainerTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.component.ItemContainerContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientTooltipComponent.class)
public interface ClientTooltipComponentMixin {

    @Inject(method = "create(Lnet/minecraft/world/inventory/tooltip/TooltipComponent;)Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipComponent;",
            at = @At("HEAD"), cancellable = true)
    private static void useContainerTooltip(TooltipComponent component,
                                            CallbackInfoReturnable<ClientTooltipComponent> cir) {
        if (component instanceof ContainerTooltipData(ItemContainerContents contents)) {
            cir.setReturnValue(new ContainerTooltipComponent(contents));
        } else if (component instanceof AnimalTooltipData(AnimalComponent contents)) {
            cir.setReturnValue(new AnimalTooltipComponent(contents));
        }
    }
}
