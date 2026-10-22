package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.item.quiver.QuiverTooltip;
import net.greenjab.nekomasfixed.registry.other.AnimalTooltipData;
import net.greenjab.nekomasfixed.registry.other.ContainerTooltipData;
import net.greenjab.nekomasfixed.render.other.AnimalTooltipComponent;
import net.greenjab.nekomasfixed.render.other.ClientQuiverTooltip;
import net.greenjab.nekomasfixed.render.other.ContainerTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientActivePlayersTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientTooltipComponent.class)
public interface ClientTooltipComponentMixin {

    @Inject(
            method = "create(Lnet/minecraft/world/inventory/tooltip/TooltipComponent;)Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipComponent;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void create(TooltipComponent component, CallbackInfoReturnable<ClientTooltipComponent> cir) {
        // Handle your custom types
        if (component instanceof ContainerTooltipData containerTooltipData) {
            cir.setReturnValue(new ContainerTooltipComponent(containerTooltipData.contents()));
            return;
        }
        if(component instanceof QuiverTooltip quiverTooltip){
            cir.setReturnValue(new ClientQuiverTooltip(quiverTooltip.contents()));
        }
        if (component instanceof AnimalTooltipData animalTooltipData) {
            cir.setReturnValue(new AnimalTooltipComponent(animalTooltipData.contents()));
            return;
        }

        ClientTooltipComponent result = register(component);
        if (result != null) {
            cir.setReturnValue(result);
        }
    }

    private static ClientTooltipComponent register(final TooltipComponent component) {
        return switch (component) {
            case BundleTooltip bundleTooltip -> new ClientBundleTooltip(bundleTooltip.contents());
            case QuiverTooltip quiverTooltip -> new ClientQuiverTooltip(quiverTooltip.contents());
            case ClientActivePlayersTooltip.ActivePlayersTooltip activePlayersTooltip ->
                    new ClientActivePlayersTooltip(activePlayersTooltip);
            default -> null;
        };
    }
}