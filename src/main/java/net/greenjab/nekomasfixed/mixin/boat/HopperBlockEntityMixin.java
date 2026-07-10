package net.greenjab.nekomasfixed.mixin.boat;

import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    @Unique
    private static final Predicate<Entity> NEW_VALID_INVENTORIES = entity -> entity instanceof Container && entity.isAlive() && (!(entity instanceof BigBoat bigBoat) || bigBoat.hasChest());

    @Redirect(method = "getEntityContainer", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/EntitySelector;CONTAINER_ENTITY_SELECTOR:Ljava/util/function/Predicate;", opcode = Opcodes.GETSTATIC))
    private static Predicate<Entity> dontGetBigChestWhenNoChest(){
        return NEW_VALID_INVENTORIES;
    }


}