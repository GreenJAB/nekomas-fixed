package net.greenjab.nekomasfixed.registry.entity.goal;

import net.greenjab.nekomasfixed.registry.entity.Moobloom.Moobloom;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.bee.Bee;
import java.util.List;

public class PollinatingMoobloomGoal extends Goal {
    private final Bee bee;
    private Moobloom target;

    public PollinatingMoobloomGoal(Bee bee) {this.bee = bee;}

    @Override
    public boolean canUse() {
        if (bee.hasNectar()) {return false;}
        List<Moobloom> list = bee.level().getEntitiesOfClass(Moobloom.class, bee.getBoundingBox().inflate(8), entity -> !entity.getEntityData().get(Moobloom.SHEARED));
        if (list.isEmpty()) {return false;}

        this.target = list.getFirst();
        return true;
    }

    @Override
    public void start() {
        bee.getNavigation().moveTo(target, 1.2D);
    }

    @Override
    public void tick() {
        if (target == null) return;
        bee.getLookControl().setLookAt(target);

        if (bee.distanceToSqr(target) < 2.0D) {
            bee.setHasNectar(true);

        } else {
            bee.getNavigation().moveTo(target, 1.2D);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive() && !bee.hasNectar() && !target.getEntityData().get(Moobloom.SHEARED);
    }

    @Override
    public boolean isInterruptable(){
        return bee.hasNectar();
    }
}
