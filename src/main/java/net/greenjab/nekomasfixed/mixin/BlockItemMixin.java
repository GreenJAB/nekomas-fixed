package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.OtherRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method="onItemEntityDestroyed", at = @At( value = "HEAD"), cancellable = true)
    private void releaseAnimalOnNautilusDestroyed(ItemEntity itemEntity, CallbackInfo ci) {
        AnimalComponent animalComponent = itemEntity.getStack().get(OtherRegistry.ANIMAL);
        if (animalComponent != null && !animalComponent.animal().isEmpty()) {
            AnimalComponent.StoredEntityData animal = animalComponent.animal().get(0);
            World world = itemEntity.getEntityWorld();
            BlockPos pos = itemEntity.getBlockPos();
            Entity entity = animal.loadEntity(world, pos);
            if (entity != null) {
                double e = pos.getX() + 0.5;
                double g = pos.getY() + 0.5 - entity.getHeight() / 2.0F;
                double h = pos.getZ() + 0.5;entity.refreshPositionAndAngles(e, g, h, entity.getYaw(), entity.getPitch());
                world.spawnEntity(entity);
            }
            ci.cancel();
        }
    }
}
