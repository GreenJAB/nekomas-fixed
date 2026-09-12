package net.greenjab.nekomasfixed.registry.entity.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.player.Player;
import java.util.EnumSet;

public class FollowPlayerIfTrustedGoal extends Goal {
    private final Ocelot ocelot;
    private Player player;
    private final double speedModifier;
    private final float stopDistance;
    private final float startDistance;

    public FollowPlayerIfTrustedGoal(Ocelot ocelot, double speedModifier, float startDistance, float stopDistance) {
        this.ocelot = ocelot;
        this.speedModifier = speedModifier;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.ocelot.isTrusting()) return false;
        this.player = this.ocelot.level().getNearestPlayer(this.ocelot, (double) this.startDistance);
        return this.player != null && !this.player.isSpectator() && this.ocelot.distanceToSqr(this.player) > (double) (this.stopDistance * this.stopDistance);
    }

    @Override
    public boolean canContinueToUse() {
        return this.player != null
                && this.player.isAlive()
                && !this.player.isSpectator()
                && this.ocelot.isTrusting()
                && this.ocelot.distanceToSqr(this.player) > (double) (this.stopDistance * this.stopDistance);
    }

    @Override
    public void start() {
        this.ocelot.getNavigation().moveTo(this.player, this.speedModifier);
    }

    @Override
    public void stop() {
        this.player = null;
        this.ocelot.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.ocelot.getLookControl().setLookAt(this.player, 10.0F, (float) this.ocelot.getMaxHeadXRot());
        if (this.ocelot.distanceToSqr(this.player) >= (double) (this.stopDistance * this.stopDistance)) {
            this.ocelot.getNavigation().moveTo(this.player, this.speedModifier);
        }
    }
}