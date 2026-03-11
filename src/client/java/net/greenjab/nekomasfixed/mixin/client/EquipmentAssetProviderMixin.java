package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.util.ModEquipmentAssetKeys;
import net.minecraft.client.data.EquipmentAssetProvider;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.function.BiConsumer;

@Mixin(EquipmentAssetProvider.class)
public class EquipmentAssetProviderMixin {
    @Inject(method = "bootstrap", at = @At("HEAD"), cancellable = true)
    private static void customBootstrap(BiConsumer<RegistryKey<EquipmentAsset>, EquipmentModel> equipmentBiConsumer, CallbackInfo ci) {
        equipmentBiConsumer.accept(ModEquipmentAssetKeys.NETHERITE_CROWN, EquipmentModel.builder().addMainHumanoidLayer(Identifier.ofVanilla("netherite_crown"), false).build());

    }
}
