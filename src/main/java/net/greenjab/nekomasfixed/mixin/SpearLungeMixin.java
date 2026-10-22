package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class SpearLungeMixin {

    @Unique
    private double preCollisionSpeed = 0.0;

    @Unique
    private Vec3 preCollisionVelocity = Vec3.ZERO;

    @Inject(method = "tick", at = @At("HEAD"))
    private void captureMomentum(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        // Cache velocity before horizontalCollision zeroes it out
        this.preCollisionVelocity = player.getKnownSpeed();
        this.preCollisionSpeed = this.preCollisionVelocity.horizontalDistance();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onPlayerTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;

        if (player.level().isClientSide() || !player.horizontalCollision) return;

        // Correctly using the Vanilla 1.21.11 Spear tag
        if (!player.getMainHandItem().is(ItemTags.SPEARS)) return;

        // 1.2 threshold targets high-speed Elytra dives and lunges
        if (this.preCollisionSpeed < 1.2) return;

        // Expand the crash box using the pre-collision momentum
        Vec3 impactDirection = this.preCollisionVelocity.normalize().scale(0.5);
        AABB crashBox = player.getBoundingBox().expandTowards(impactDirection);

        ServerLevel serverLevel = (ServerLevel) player.level();
        boolean shatteredGlass = false;
        TagKey<Block> shatterTag = ModTags.SPEAR_SHATTER;

        Iterable<BlockPos> intersectingBlocks = BlockPos.betweenClosed(
                Mth.floor(crashBox.minX), Mth.floor(crashBox.minY), Mth.floor(crashBox.minZ),
                Mth.floor(crashBox.maxX), Mth.floor(crashBox.maxY), Mth.floor(crashBox.maxZ)
        );

        for (BlockPos pos : intersectingBlocks) {
            if (serverLevel.getBlockState(pos).is(shatterTag)) {
                serverLevel.destroyBlock(pos, false, player, 512);
                shatteredGlass = true;
            }
        }

        if (shatteredGlass) {
            float recoilDamage = (float) Mth.clamp(this.preCollisionSpeed * 3.0, 1.0, 4.0);
            player.hurtServer(serverLevel, player.damageSources().flyIntoWall(), recoilDamage);
            player.getMainHandItem().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            player.setDeltaMovement(player.getDeltaMovement().scale(0.5));
        }
    }
}