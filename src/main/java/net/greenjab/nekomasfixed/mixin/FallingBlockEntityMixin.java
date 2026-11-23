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
    private void sieve(CallbackInfo ci) {
        BlockState state = getBlockState();
        if (level().isClientSide() || !state.is(ModTags.SIEVABLE_BLOCKS)) {
            return;
        }
        AABB box = getBoundingBox().deflate(0.1).expandTowards(0, -0.5, 0);
        List<Player> players = level().getEntitiesOfClass(Player.class, box);
        if (players.isEmpty() || players.getFirst().isSpectator()) {
            return;
        }
        Player player = players.getFirst();
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.is(Items.CHAINMAIL_HELMET)) {
            helmet.hurtAndBreak(1, player, EquipmentSlot.HEAD);
            Block.dropResources(state, level(), blockPosition());
            level().levelEvent(2001, blockPosition(), Block.getId(state));
            discard();
            ci.cancel();
        }
    }
}
