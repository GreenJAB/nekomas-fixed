package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.SpearEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultDispenseItemBehavior.class)
public abstract class ItemDispenserBehaviorMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true)
    public void SpearAttack(BlockSource pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {

        Level world = pointer.level();
        if (world.isClientSide())  return;
        if (!pointer.state().is(Blocks.DISPENSER))  return;

        BlockPos pos = BlockPos.containing(DispenserBlock.getDispensePosition(pointer));
        BlockState blockState = world.getBlockState(pos);

        if (!blockState.getCollisionShape(world, pos).isEmpty()) return;
        if (!world.getEntitiesOfClass(SpearEntity.class, new AABB(pos).inflate(-0.2, -0.2, -0.2)).isEmpty()){
            cir.setReturnValue(stack);
            return;
        }

        if (stack.is(ItemTags.SPEARS)) {
            SpearEntity entity = EntityTypeRegistry.SPEAR.create(world, EntitySpawnReason.DISPENSER);
            if (entity != null) {
                entity.absSnapTo(pos.getX()+0.5, pos.getY()+0.2, pos.getZ()+0.5, 0, 0);
                entity.setStack(stack);
                entity.setDirection( pointer.state().getValue(DispenserBlock.FACING));
                world.addFreshEntity(entity);
                cir.setReturnValue(stack);
            }
        }
    }
}
