package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.item.Item;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DrownedEntity.class)
public class DrownedEntityMixin {

    @ModifyConstant(method = "initialize", constant = @Constant(floatValue = 0.03f))
    private float moreChanceForOffhand(float original){
        return original*2;
    }

    @ModifyExpressionValue(method = "initialize", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;NAUTILUS_SHELL:Lnet/minecraft/item/Item;"))
    private Item holdClam(Item original, @Local(argsOnly = true) ServerWorldAccess world){
        if (world.getRandom().nextFloat() > 0.5) {
            return getClam(world.getRandom().nextFloat());
        }
        return original;
    }

    @Unique
    private Item getClam(float rarity) {
        if (rarity>0.5) return ItemRegistry.CLAM;
        if (rarity>0.25) return ItemRegistry.CLAM_BLUE;
        if (rarity>0.125) return ItemRegistry.CLAM_PINK;
        if (rarity>0.0625) return ItemRegistry.CLAM_PURPLE;
        return ItemRegistry.CLAM;
    }

}
