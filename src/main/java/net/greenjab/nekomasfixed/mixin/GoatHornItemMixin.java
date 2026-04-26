package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.mixin.accessor.GoatHornItemInvoker;
import net.greenjab.nekomasfixed.registry.block.enums.GoatHornType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(GoatHornItem.class)
public class GoatHornItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(itemStack.getItem() instanceof GoatHornItemInvoker item && !world.isClient()){
            Optional<? extends RegistryEntry<Instrument>> optional = item.invokeGetInstrument(itemStack, user.getRegistryManager());
            ServerWorld serverWorld = (ServerWorld) world;
            BlockPos pos = user.getBlockPos();

            boolean inVillage = serverWorld.getStructureAccessor().getStructureContaining(pos,StructureTags.VILLAGE).hasChildren();
            if (inVillage) {
                StatusEffectInstance status= GoatHornType.getStatusEffect((GoatHornItem) item);
                for(Entity entity : serverWorld.iterateEntities()){
                    if(entity instanceof IronGolemEntity ironGolem){
                       ironGolem.addStatusEffect(status);
                       serverWorld.spawnParticles(ParticleTypes.BUBBLE, pos.getX(), pos.getY(), pos.getZ(), 10, pos.getX(), (double)pos.getY()+2, pos.getZ(), 0.5);
                    }
                }
            }
            if (optional.isPresent()) {
                Instrument instrument = (Instrument)((RegistryEntry<?>)optional.get()).value();
                user.setCurrentHand(hand);
                GoatHornItemInvoker.invokePlaySound(world, user, instrument);
            } else {
                cir.setReturnValue(ActionResult.FAIL);
            }
        }
    }
}
