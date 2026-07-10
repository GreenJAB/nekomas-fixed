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
public abstract class DefaultDispenseItemBehaviorMixin {

    @Inject(at = @At("HEAD"), method = "execute", cancellable = true)
    public void SpearAttack(BlockSource source, ItemStack dispensed, CallbackInfoReturnable<ItemStack> cir) {

        Level level = source.level();
        if (level.isClientSide())  return;
        if (!source.state().is(Blocks.DISPENSER))  return;

        BlockPos pos = BlockPos.containing(DispenserBlock.getDispensePosition(source));
        BlockState blockState = level.getBlockState(pos);

        if (!blockState.getCollisionShape(level, pos).isEmpty()) return;
        if (!level.getEntitiesOfClass(SpearEntity.class, new AABB(pos).inflate(-0.2, -0.2, -0.2)).isEmpty()){
            cir.setReturnValue(dispensed);
            return;
        }

        if (dispensed.is(ItemTags.SPEARS)) {
            SpearEntity entity = EntityTypeRegistry.SPEAR.create(level, EntitySpawnReason.DISPENSER);
            if (entity != null) {
                entity.absSnapTo(pos.getX()+0.5, pos.getY()+0.2, pos.getZ()+0.5, 0, 0);
                entity.setStack(dispensed);
                entity.setDirection( source.state().getValue(DispenserBlock.FACING));
                level.addFreshEntity(entity);
                cir.setReturnValue(dispensed);
            }
        }
    }
}
