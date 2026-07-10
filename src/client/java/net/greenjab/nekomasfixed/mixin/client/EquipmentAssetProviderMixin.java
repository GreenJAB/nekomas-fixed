package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.util.ModEquipmentAssetKeys;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.BiConsumer;

@Mixin(EquipmentAssetProvider.class)
public class EquipmentAssetProviderMixin {
    @Inject(method = "bootstrap", at = @At("HEAD"), cancellable = true)
    private static void customBootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer, CallbackInfo ci) {
        consumer.accept(ModEquipmentAssetKeys.NETHERITE_CROWN, EquipmentClientInfo.builder().addMainHumanoidLayer(Identifier.withDefaultNamespace("netherite_crown"), false).build());
        consumer.accept(ModEquipmentAssetKeys.COPPER_CROWN, EquipmentClientInfo.builder().addMainHumanoidLayer(Identifier.withDefaultNamespace("copper_crown"), false).build());
        consumer.accept(ModEquipmentAssetKeys.IRON_CROWN, EquipmentClientInfo.builder().addMainHumanoidLayer(Identifier.withDefaultNamespace("iron_crown"), false).build());
        consumer.accept(ModEquipmentAssetKeys.GOLDEN_CROWN, EquipmentClientInfo.builder().addMainHumanoidLayer(Identifier.withDefaultNamespace("golden_crown"), false).build());
        consumer.accept(ModEquipmentAssetKeys.DIAMOND_CROWN, EquipmentClientInfo.builder().addMainHumanoidLayer(Identifier.withDefaultNamespace("diamond_crown"), false).build());
        ci.cancel();
    }
}
