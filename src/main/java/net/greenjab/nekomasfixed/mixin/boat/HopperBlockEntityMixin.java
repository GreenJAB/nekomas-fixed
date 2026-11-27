package net.greenjab.nekomasfixed.mixin.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @ModifyExpressionValue(method = "getEntityContainer", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/EntitySelector;CONTAINER_ENTITY_SELECTOR:Ljava/util/function/Predicate;"))
    private static Predicate<Entity> skipBigBoatsWithoutChest(Predicate<Entity> original) {
        return original.and(entity -> !(entity instanceof BigBoat bigBoat) || bigBoat.hasChest());
    }
}
