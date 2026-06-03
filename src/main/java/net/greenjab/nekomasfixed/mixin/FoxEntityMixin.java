package net.greenjab.nekomasfixed.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(FoxEntity.class)
public class FoxEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void customTick(CallbackInfo ci){
        FoxEntity foxEntity = (FoxEntity)(Object)this;
        int chance = 10;
        boolean randInt = new Random().nextInt(0, chance) == 5;
        ItemStack stack = foxEntity.getMainHandStack();
        World world = foxEntity.getEntityWorld();
        if(stack.isIn(ItemTags.HOES) && randInt){
            if(world.getBlockState(foxEntity.getBlockPos().down()).isIn(BlockTags.DIRT)){
                world.setBlockState(foxEntity.getBlockPos().down(), Blocks.FARMLAND.getDefaultState());
                stack.damage(1, foxEntity, Hand.MAIN_HAND);
            }
        }else if(stack.isOf(Items.SHEARS) && randInt){
            List<SheepEntity> nearbySheeps = world.getEntitiesByClass(SheepEntity.class, foxEntity.getBoundingBox().expand(1), sheepEntity -> true);
            SheepEntity sheep;
            if(!nearbySheeps.isEmpty()){
                sheep = nearbySheeps.get(0);
                if(sheep.isShearable()){
                    if (world.isClient()) {return;}
                    sheep.sheared((ServerWorld) world, SoundCategory.PLAYERS, stack);
                    sheep.emitGameEvent(GameEvent.SHEAR, foxEntity);
                    stack.damage(1, foxEntity, Hand.MAIN_HAND);
                }
            }
        }
        else if(stack.isOf(Items.FLINT_AND_STEEL) && randInt){
            Direction dir = Direction.Type.HORIZONTAL.random(foxEntity.getRandom());
            if(world.getBlockState(foxEntity.getBlockPos().down()).isOf(Blocks.SOUL_SOIL)){
                world.setBlockState(foxEntity.getBlockPos().offset(dir), Blocks.SOUL_FIRE.getDefaultState());
                stack.damage(1, foxEntity, Hand.MAIN_HAND);
            }else{
                world.setBlockState(foxEntity.getBlockPos().offset(dir), Blocks.FIRE.getDefaultState());
                stack.damage(1, foxEntity, Hand.MAIN_HAND);
            }
        }else if(stack.isOf(Items.POTION) && randInt){
            if(stack.contains(DataComponentTypes.POTION_CONTENTS)){
                PotionContentsComponent potionContent = stack.get(DataComponentTypes.POTION_CONTENTS);
                assert potionContent != null;
                if(!potionContent.hasEffects())return;
                for(StatusEffectInstance effect : potionContent.getEffects()){
                    StatusEffectInstance copy = new StatusEffectInstance(effect);
                    foxEntity.addStatusEffect(copy);
                }
                foxEntity.setStackInHand(Hand.MAIN_HAND, Items.GLASS_BOTTLE.getDefaultStack());
            }
        }else if(stack.isOf(Items.BUCKET) && randInt){
            if(world.getFluidState(foxEntity.getBlockPos().down().offset(foxEntity.getFacing())).isOf(Fluids.WATER)){
                world.setBlockState(foxEntity.getBlockPos().down().offset(foxEntity.getFacing()), Blocks.AIR.getDefaultState());
                foxEntity.setStackInHand(Hand.MAIN_HAND, Items.WATER_BUCKET.getDefaultStack());
            }else if(world.getFluidState(foxEntity.getBlockPos().down().offset(foxEntity.getFacing())).isOf(Fluids.LAVA)){
                world.setBlockState(foxEntity.getBlockPos().down().offset(foxEntity.getFacing()), Blocks.AIR.getDefaultState());
                foxEntity.setStackInHand(Hand.MAIN_HAND, Items.LAVA_BUCKET.getDefaultStack());
            }
        }
    }
}
