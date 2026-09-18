package net.greenjab.nekomasfixed.registry.entity.goal;

import net.greenjab.nekomasfixed.mixin.accessor.BeeAccessor;
import net.greenjab.nekomasfixed.registry.entity.moobloom.Moobloom;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Bee;

import java.util.List;

public class PollinatingMoobloomGoal extends Goal {
    private final Bee bee;
    private Moobloom target;

    public PollinatingMoobloomGoal(Bee bee) {
        this.bee = bee;
    }

    @Override
    public boolean canUse() {
        if (bee.hasNectar()) {
            return false;
        }
        List<Moobloom> list = bee.level().getEntitiesOfClass(Moobloom.class, bee.getBoundingBox().inflate(8),
                entity -> !entity.isSheared());
        if (list.isEmpty()) {
            return false;
        }
        this.target = list.getFirst();
        return true;
    }

    @Override
    public void start() {
        bee.getNavigation().moveTo(target, 1.2D);
    }

    @Override
    public void tick() {
        if (target == null) {
            return;
        }
        bee.getLookControl().setLookAt(target);
        if (bee.distanceToSqr(target) < 2.0D) {
            ((BeeAccessor) bee).invokeSetHasNectar(true);
        } else {
            bee.getNavigation().moveTo(target, 1.2D);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive() && !bee.hasNectar() && !target.isSheared();
    }

    @Override
    public boolean isInterruptable() {
        return bee.hasNectar();
    }
}