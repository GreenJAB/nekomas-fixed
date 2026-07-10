package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.ClamBlock;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method="onDestroyed", at = @At( value = "HEAD"), cancellable = true)
    private void releaseAnimalOnNautilusDestroyed(ItemEntity entity, CallbackInfo ci) {
        AnimalComponent animalComponent = entity.getItem().get(ComponentRegistry.ANIMAL);
        if (animalComponent != null && !animalComponent.animal().isEmpty()) {
            AnimalComponent.StoredEntityData animal = animalComponent.animal().getFirst();
            Level level = entity.level();
            BlockPos pos = entity.blockPosition();
            Entity releasedEntity = animal.loadEntity(level);
            if (releasedEntity != null) {
                double e = pos.getX() + 0.5;
                double g = pos.getY() + 0.5 - releasedEntity.getBbHeight() / 2.0F;
                double h = pos.getZ() + 0.5;
                releasedEntity.snapTo(e, g, h, releasedEntity.getYRot(), releasedEntity.getXRot());
                level.addFreshEntity(releasedEntity);
            }
            ci.cancel();
        }
    }

    @Inject(method="updateBlockStateFromTag", at = @At( value = "HEAD"))
    private void placeOpenClam(BlockPos pos, Level level, ItemStack itemStack, BlockState placedState, CallbackInfoReturnable<BlockState> cir) {
        if (itemStack.is(ModTags.CLAMTAG)) {
            Integer i = itemStack.getOrDefault(ComponentRegistry.CLAM_STATE, 0);
            if (i > 0) {
                placedState = placedState.setValue(ClamBlock.OPEN, true);
                level.setBlock(pos, placedState, Block.UPDATE_CLIENTS);
            }
        }
    }
}
