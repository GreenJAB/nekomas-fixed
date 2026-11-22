package net.greenjab.nekomasfixed.mixin;

import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Fox.class)
public class FoxMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void customTick(CallbackInfo ci) {
        Fox fox = (Fox) (Object) this;
        Level level = fox.level();
        ItemStack stack = fox.getMainHandItem();
        if (stack.isEmpty() || fox.getRandom().nextInt(10) != 5) return;

        if (stack.is(ItemTags.HOES)) {
            if (level.getBlockState(fox.blockPosition().below()).is(BlockTags.DIRT)) {
                level.setBlockAndUpdate(fox.blockPosition().below(), Blocks.FARMLAND.defaultBlockState());
                stack.hurtAndBreak(1, fox, EquipmentSlot.MAINHAND);
            }
        } else if (stack.is(Items.SHEARS)) {
            List<Sheep> sheeps = level.getEntitiesOfClass(Sheep.class, fox.getBoundingBox().inflate(1));
            if (!sheeps.isEmpty() && sheeps.getFirst().readyForShearing() && !level.isClientSide()) {
                Sheep sheep = sheeps.getFirst();
                sheep.shear(SoundSource.PLAYERS);
                sheep.gameEvent(GameEvent.SHEAR, fox);
                stack.hurtAndBreak(1, fox, EquipmentSlot.MAINHAND);
            }
        } else if (stack.is(Items.FLINT_AND_STEEL)) {
            Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(fox.getRandom());
            if (level.getBlockState(fox.blockPosition().below()).is(Blocks.SOUL_SOIL)) {
                level.setBlockAndUpdate(fox.blockPosition().relative(dir), Blocks.SOUL_FIRE.defaultBlockState());
            } else {
                level.setBlockAndUpdate(fox.blockPosition().relative(dir), Blocks.FIRE.defaultBlockState());
            }
            stack.hurtAndBreak(1, fox, EquipmentSlot.MAINHAND);
        } else if (stack.is(Items.POTION)) {
            PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
            if (contents == null || !contents.hasEffects()) return;
            for (MobEffectInstance effect : contents.getAllEffects()) {
                fox.addEffect(new MobEffectInstance(effect));
            }
            fox.setItemSlot(EquipmentSlot.MAINHAND, Items.GLASS_BOTTLE.getDefaultInstance());
        } else if (stack.is(Items.BUCKET)) {
            var pos = fox.blockPosition().below().relative(fox.getNearestViewDirection());
            if (level.getFluidState(pos).is(Fluids.WATER)) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                fox.setItemSlot(EquipmentSlot.MAINHAND, Items.WATER_BUCKET.getDefaultInstance());
            } else if (level.getFluidState(pos).is(Fluids.LAVA)) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                fox.setItemSlot(EquipmentSlot.MAINHAND, Items.LAVA_BUCKET.getDefaultInstance());
            }
        }
    }
}
