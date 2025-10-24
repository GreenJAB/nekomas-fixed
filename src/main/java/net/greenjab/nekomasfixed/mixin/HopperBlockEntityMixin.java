package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.MegaBoatEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    @Unique
    private static final Predicate<Entity> NEW_VALID_INVENTORIES = entity -> entity instanceof Inventory && entity.isAlive() && (!(entity instanceof MegaBoatEntity megaBoatEntity) || megaBoatEntity.hasChest());

    @Redirect(method = "getEntityInventoryAt", at = @At(value = "FIELD", target = "Lnet/minecraft/predicate/entity/EntityPredicates;VALID_INVENTORIES:Ljava/util/function/Predicate;"))
    private static Predicate<Entity> dontGetBigChestWhenNoChest(){
        return NEW_VALID_INVENTORIES;
    }


}