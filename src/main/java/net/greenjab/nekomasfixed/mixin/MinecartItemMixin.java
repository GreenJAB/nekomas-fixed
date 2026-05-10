package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.CustomMinecartEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MinecartItem;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartItem.class)
public class MinecartItemMixin {
        @Inject(method ="useOnBlock", at = @At("HEAD"), cancellable = true)
        private static void nekomasfixed$createCustomMinecart(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
            CustomMinecartEntity  minecart = new CustomMinecartEntity(EntityTypeRegistry.CUSTOM_MINECART, context.getWorld());
            minecart.setPosition(context.getHitPos().x, context.getHitPos().y, context.getHitPos().z);
            if(!context.getWorld().isClient()){
                context.getWorld().spawnEntity(minecart);
            }
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }


