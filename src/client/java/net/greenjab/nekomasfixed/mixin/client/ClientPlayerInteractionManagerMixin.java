package net.greenjab.nekomasfixed.mixin.client;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.greenjab.nekomasfixed.render.entity.model.MegaBoatEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import net.minecraft.entity.RideableInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "hasRidingInventory", at = @At(value = "RETURN"), cancellable = true)
    private void bigBoatNoChestNormal(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            if (MinecraftClient.getInstance().player.getVehicle() instanceof MegaBoatEntity megaBoatEntity) {
                cir.setReturnValue(megaBoatEntity.hasChest());
            }
        }
    }
}