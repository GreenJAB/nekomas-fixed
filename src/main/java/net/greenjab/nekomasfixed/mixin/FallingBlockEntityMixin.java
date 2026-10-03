package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {

    @Shadow
    public abstract BlockState getBlockState();

    public FallingBlockEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        BlockState state = this.getBlockState();
        if (!state.is(ModTags.SIEVABLE_BLOCKS)) {
            return;
        }
        if (this.level().isClientSide()) {
            return;
        }
        AABB box = this.getBoundingBox().deflate(0.1).expandTowards(0, -0.5, 0);
        List<Player> players = this.level().getEntitiesOfClass(Player.class, box);
        if (!players.isEmpty()) {
            Player player = players.getFirst();
            if (!player.isSpectator()) {
                ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
                if (helmet.is(Items.CHAINMAIL_HELMET)) {
                    helmet.hurtAndBreak(1, player, EquipmentSlot.HEAD);
                    Block.dropResources(state, this.level(), this.blockPosition());
                    this.level().levelEvent(2001, this.blockPosition(), Block.getId(state));
                    this.discard();
                    ci.cancel();
                }
            }
        }
    }
}
