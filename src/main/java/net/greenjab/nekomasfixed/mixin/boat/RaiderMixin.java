package net.greenjab.nekomasfixed.mixin.boat;

import net.greenjab.nekomasfixed.registry.entity.BigBoat;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(Raider.class)
public class RaiderMixin {

    @Inject(method = "aiStep", at = @At("RETURN"))
    private void moveBoat(CallbackInfo ci) {
        Raider RE = (Raider)(Object)this;
        if (RE.isPassenger() && RE.getVehicle() instanceof AbstractBoat boatEntity && RE == boatEntity.getFirstPassenger()) {
            boatEntity.setInput(false, false, false, false);
            Vec3 target = null;
            if (RE.getTarget()!=null) target = RE.getTarget().position();
            else {
                updatePatrol(RE, boatEntity);
                if (RE.hasPatrolTarget()) target = RE.getPatrolTarget().getCenter();
            }

            if (target!=null) {
                Optional<Float> toYawOptional = getTargetYaw(target.x(), target.z(), RE.getX(), RE.getZ());
                if (toYawOptional.isPresent()) {
                    float targetYaw = toYawOptional.get();
                    float boatYaw = boatEntity.getYRot();
                    double toYaw = (targetYaw-boatYaw)*(Math.PI/180f);
                    toYaw = Math.atan(Math.tan(toYaw/2.0));
                    if (Vector3f.distanceSquared((float)target.x(), (float)target.y(), (float)target.z(), (float)RE.getX(), (float)RE.getY(), (float)RE.getZ())>150) {
                        if (toYaw > 0.25) boatEntity.setInput(false, true, Math.abs(toYaw)<Math.PI/4, false);
                        else if (toYaw < -0.25) boatEntity.setInput(true, false, Math.abs(toYaw)<Math.PI/4, false);
                        else boatEntity.setInput(false, false, true, false);
                    } else {
                        toYaw-=(Math.PI/4)*(toYaw>0?1:-1);
                        if (toYaw > 0.25) boatEntity.setInput(false, true, false, false);
                        else if (toYaw < -0.25) boatEntity.setInput(true, false, false, false);
                        else boatEntity.setInput(false, false, false, false);
                    }
                }
            }
        }
    }

    @Unique
    private void updatePatrol(Raider RE, AbstractBoat boatEntity) {
        if (boatEntity instanceof BigBoat bigBoat && RE == bigBoat.getFirstPassenger()) {
            Level level = RE.level();
            if (level.getGameTime() % 20 == 0 && level.getRandom().nextInt(10) == 0) {
                if (!RE.hasPatrolTarget()) {
                    if (level.getRandom().nextInt(10) == 0) RE.setPatrolTarget(null);
                    RandomSource random = level.getRandom();
                    BlockPos pos = RE.blockPosition();
                    pos = pos.offset(-50 + random.nextInt(100), 0, -50 + random.nextInt(100));
                    if (validOcean(pos, level, RE)) {
                        RE.setPatrolTarget(pos);
                        List<PatrollingMonster> list = level.getEntitiesOfClass(
                                PatrollingMonster.class, RE.getBoundingBox().inflate(32.0), patrolEntity -> patrolEntity.canJoinPatrol() && !patrolEntity.is(RE));

                        for (PatrollingMonster patrolEntity : list) {
                            patrolEntity.setPatrolTarget(pos);
                        }
                    }
                } else {
                    if (RE.getPatrolTarget().closerToCenterThan(RE.position(), 20.0) || level.getRandom().nextInt(3) == 0) {
                        RE.setPatrolTarget(null);
                    }
                }
            }
        }
    }

    @Unique
    private static boolean validOcean(BlockPos pos, Level level, Raider RE) {
        for (int bx = pos.getX() - 8; bx < pos.getX() + 8; bx++) {
            for (int by = pos.getY() - 2; by < pos.getY() + 4; by++) {
                for (int bz = pos.getZ() - 8; bz < pos.getZ() + 8; bz++) {
                    BlockState blockState = level.getBlockState(new BlockPos(bx, by, bz));
                    if (!(blockState.is(Blocks.AIR) || blockState.is(Blocks.WATER))) {
                        return false;
                    }
                }
            }
        }
        BlockHitResult blockHitResult = RE.level()
                .clip(new ClipContext(RE.position(), pos.getCenter(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, RE));
        return blockHitResult.getType() == HitResult.Type.MISS;
    }

    @Unique
    protected Optional<Float> getTargetYaw(double x1, double z1, double x2, double z2) {
        double d = x1 - x2;
        double e = z1 - z2;
        return !(Math.abs(e) > 1.0E-5F) && !(Math.abs(d) > 1.0E-5F)
                ? Optional.empty()
                : Optional.of((float)(Mth.atan2(e, d) * 180.0F / (float)Math.PI) - 90.0F);
    }
}