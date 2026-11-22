package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.goal.MoveToCoralReefGoal;
import net.greenjab.nekomasfixed.target_access_class.DolphinAccess;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Dolphin.class)
public abstract class DolphinMixin extends WaterAnimal implements DolphinAccess {
    @Unique
    private static final EntityDataAccessor<Boolean> TROPICAL_FISH_FED = SynchedEntityData.defineId(Dolphin.class, EntityDataSerializers.BOOLEAN);

    protected DolphinMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(TROPICAL_FISH_FED, false);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"))
    private void interactMob(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.is(Items.TROPICAL_FISH)) {
            entityData.set(TROPICAL_FISH_FED, true);
            stack.consume(1, player);
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void registerGoals(CallbackInfo ci) {
        goalSelector.addGoal(7, new MoveToCoralReefGoal((Dolphin) (Object) this));
    }

    public boolean nekomasfixed$isFishFed() {
        return entityData.get(TROPICAL_FISH_FED);
    }
}
