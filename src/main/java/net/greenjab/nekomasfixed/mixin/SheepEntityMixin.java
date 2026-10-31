package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.target_access.SheepAccess;
import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity implements SheepAccess, SpottedSheepAccess {

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private static final TrackedData<Boolean> SPOTTED = DataTracker.registerData(SheepEntity.class, TrackedDataHandlerRegistry.BOOLEAN);@Unique
    private static final TrackedData<Boolean> GLOWING =
            DataTracker.registerData(SheepEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"), method = "interactMob", cancellable = true)
    private void interactMob (PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.GLOW_INK_SAC)) {
            if (!this.getEntityWorld().isClient() && !nekomasfixed$isGlowing()) {
                nekomasfixed$setGlowing(true);

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    Criteria.PLAYER_INTERACTED_WITH_ENTITY.trigger(serverPlayer, itemStack, this);
                }
                this.emitGameEvent(GameEvent.ENTITY_INTERACT, player);
                this.getEntityWorld().playSoundFromEntity(null, this, SoundEvents.ITEM_GLOW_INK_SAC_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
                itemStack.decrement(1);
                cir.setReturnValue(ActionResult.SUCCESS_SERVER);
            } else {
                cir.setReturnValue(ActionResult.CONSUME);
            }
        }
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(SPOTTED, false);
        builder.add(GLOWING, false);
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void writeCustomData(WriteView view, CallbackInfo ci) {
        view.putBoolean("Spotted", this.dataTracker.get(SPOTTED));
        if (nekomasfixed$isGlowing()) {
            view.putBoolean("nekomasfixed.glowing", true);
        }
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void readCustomData(ReadView view, CallbackInfo ci) {
        this.dataTracker.set(SPOTTED, view.getBoolean("Spotted", false));
        nekomasfixed$setGlowing(view.getBoolean("nekomasfixed.glowing", false));
    }

    @Override
    public boolean nekomasfixed$isSpotted() {
        return this.dataTracker.get(SPOTTED);
    }

    @Override
    public void nekomasfixed$setSpotted(boolean spotted) {
        this.dataTracker.set(SPOTTED, spotted);
    }

    @Override
    public boolean nekomasfixed$isGlowing() {
        return this.dataTracker.get(GLOWING);
    }

    @Override
    public void nekomasfixed$setGlowing(boolean glowing) {
        this.dataTracker.set(GLOWING, glowing);
    }

}