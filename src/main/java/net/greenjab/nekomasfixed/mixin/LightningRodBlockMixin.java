package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.LightningRodBlock;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin {

    @Inject(method = "setPowered", at = @At("HEAD"))
    private void tryMakeLightningBottle(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        if (state.get(LightningRodBlock.FACING) == Direction.UP) {
            if (world.getBlockEntity(pos.down()) instanceof BrewingStandBlockEntity brewingStand) {
                for (int i = 0; i <3;i++) {
                    if (brewingStand.inventory.get(i).getItem() == Items.GLASS_BOTTLE) {
                        brewingStand.inventory.set(i, PotionContentsComponent.createStack(Items.POTION, ItemRegistry.LIGHTNING));
                    }
                }
                brewingStand.markDirty();
            }
        }
    }
}