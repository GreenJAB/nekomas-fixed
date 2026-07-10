package net.greenjab.nekomasfixed.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;

@Mixin(Fox.class)
public class FoxMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void customTick(CallbackInfo ci){
        Fox foxEntity = (Fox)(Object)this;
        int chance = 10;
        boolean randInt = new Random().nextInt(0, chance) == 5;
        ItemStack stack = foxEntity.getMainHandItem();
        Level world = foxEntity.level();
        if(stack.is(ItemTags.HOES) && randInt){
            if(world.getBlockState(foxEntity.blockPosition().below()).is(BlockTags.DIRT)){
                world.setBlockAndUpdate(foxEntity.blockPosition().below(), Blocks.FARMLAND.defaultBlockState());
                stack.hurtAndBreak(1, foxEntity, InteractionHand.MAIN_HAND);
            }
        }else if(stack.is(Items.SHEARS) && randInt){
            List<Sheep> nearbySheeps = world.getEntitiesOfClass(Sheep.class, foxEntity.getBoundingBox().inflate(1), _ -> true);
            Sheep sheep;
            if(!nearbySheeps.isEmpty()){
                sheep = nearbySheeps.getFirst();
                if(sheep.readyForShearing()){
                    if (world.isClientSide()) {return;}
                    sheep.shear((ServerLevel) world, SoundSource.PLAYERS, stack);
                    sheep.gameEvent(GameEvent.SHEAR, foxEntity);
                    stack.hurtAndBreak(1, foxEntity, InteractionHand.MAIN_HAND);
                }
            }
        }
        else if(stack.is(Items.FLINT_AND_STEEL) && randInt){
            Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(foxEntity.getRandom());
            if(world.getBlockState(foxEntity.blockPosition().below()).is(Blocks.SOUL_SOIL)){
                world.setBlockAndUpdate(foxEntity.blockPosition().relative(dir), Blocks.SOUL_FIRE.defaultBlockState());
                stack.hurtAndBreak(1, foxEntity, InteractionHand.MAIN_HAND);
            }else{
                world.setBlockAndUpdate(foxEntity.blockPosition().relative(dir), Blocks.FIRE.defaultBlockState());
                stack.hurtAndBreak(1, foxEntity, InteractionHand.MAIN_HAND);
            }
        }else if(stack.is(Items.POTION) && randInt){
            if(stack.has(DataComponents.POTION_CONTENTS)){
                PotionContents potionContent = stack.get(DataComponents.POTION_CONTENTS);
                assert potionContent != null;
                if(!potionContent.hasEffects())return;
                for(MobEffectInstance effect : potionContent.getAllEffects()){
                    MobEffectInstance copy = new MobEffectInstance(effect);
                    foxEntity.addEffect(copy);
                }
                foxEntity.setItemInHand(InteractionHand.MAIN_HAND, Items.GLASS_BOTTLE.getDefaultInstance());
            }
        }else if(stack.is(Items.BUCKET) && randInt){
            if(world.getFluidState(foxEntity.blockPosition().below().relative(foxEntity.getNearestViewDirection())).is(Fluids.WATER)){
                world.setBlockAndUpdate(foxEntity.blockPosition().below().relative(foxEntity.getNearestViewDirection()), Blocks.AIR.defaultBlockState());
                foxEntity.setItemInHand(InteractionHand.MAIN_HAND, Items.WATER_BUCKET.getDefaultInstance());
            }else if(world.getFluidState(foxEntity.blockPosition().below().relative(foxEntity.getNearestViewDirection())).is(Fluids.LAVA)){
                world.setBlockAndUpdate(foxEntity.blockPosition().below().relative(foxEntity.getNearestViewDirection()), Blocks.AIR.defaultBlockState());
                foxEntity.setItemInHand(InteractionHand.MAIN_HAND, Items.LAVA_BUCKET.getDefaultInstance());
            }
        }
    }
}
