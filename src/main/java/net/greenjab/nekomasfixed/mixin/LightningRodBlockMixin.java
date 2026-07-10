package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin {

    @Inject(method = "onLightningStrike", at = @At("HEAD"))
    private void tryMakeLightningBottle(BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
        if (state.getValue(LightningRodBlock.FACING) == Direction.UP) {
            if (level.getBlockEntity(pos.below()) instanceof BrewingStandBlockEntity brewingStand) {
                for (int i = 0; i <3;i++) {
                    if (brewingStand.items.get(i).getItem() == Items.GLASS_BOTTLE) {
                        brewingStand.items.set(i, PotionContents.createItemStack(Items.POTION, ItemRegistry.LIGHTNING));
                    }
                }
                brewingStand.setChanged();
            }
        }
    }
}