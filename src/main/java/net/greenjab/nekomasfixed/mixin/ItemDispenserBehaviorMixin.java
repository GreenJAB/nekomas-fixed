package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.SpearEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemDispenserBehavior.class)
public abstract class ItemDispenserBehaviorMixin {

    @Inject(at = @At("HEAD"), method = "dispenseSilently", cancellable = true)
    public void CauldronMixin(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {

        World world = pointer.world();
        if (world.isClient())  return;
        if (!pointer.state().isOf(Blocks.DISPENSER))  return;

        BlockPos pos = BlockPos.ofFloored(DispenserBlock.getOutputLocation(pointer));
        BlockState blockState = world.getBlockState(pos);

        if (!blockState.getCollisionShape(world, pos).isEmpty()) return;
        if (!world.getNonSpectatingEntities(SpearEntity.class, new Box(pos).expand(-0.2, -0.2, -0.2)).isEmpty()){
            cir.setReturnValue(stack);
            return;
        }

        if (stack.isIn(ItemTags.SPEARS)) {
            SpearEntity entity = EntityTypeRegistry.SPEAR.create(world, SpawnReason.DISPENSER);
            if (entity != null) {
                entity.updatePositionAndAngles(pos.getX()+0.5, pos.getY()+0.2, pos.getZ()+0.5, 0, 0);
                entity.setStack(stack);
                entity.setDirection( pointer.state().get(DispenserBlock.FACING));
                world.spawnEntity(entity);
                cir.setReturnValue(stack);
            }
        }
    }
}
