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
    private void releaseAnimalOnNautilusDestroyed(ItemEntity itemEntity, CallbackInfo ci) {
        AnimalComponent animalComponent = itemEntity.getItem().get(ComponentRegistry.ANIMAL);
        if (animalComponent != null && !animalComponent.animal().isEmpty()) {
            AnimalComponent.StoredEntityData animal = animalComponent.animal().get(0);
            Level world = itemEntity.level();
            BlockPos pos = itemEntity.blockPosition();
            Entity entity = animal.loadEntity(world, pos);
            if (entity != null) {
                double e = pos.getX() + 0.5;
                double g = pos.getY() + 0.5 - entity.getBbHeight() / 2.0F;
                double h = pos.getZ() + 0.5;entity.snapTo(e, g, h, entity.getYRot(), entity.getXRot());
                world.addFreshEntity(entity);
            }
            ci.cancel();
        }
    }

    @Inject(method="updateBlockStateFromTag", at = @At( value = "HEAD"))
    private void placeOpenClam(BlockPos pos, Level world, ItemStack stack, BlockState state, CallbackInfoReturnable<BlockState> cir) {
        if (stack.is(ModTags.CLAMTAG)) {
            Integer i = stack.getOrDefault(ComponentRegistry.CLAM_STATE, 0);
            if (i > 0) {
                state = state.setValue(ClamBlock.OPEN, true);
                world.setBlock(pos, state, Block.UPDATE_CLIENTS);
            }
        }
    }
}
