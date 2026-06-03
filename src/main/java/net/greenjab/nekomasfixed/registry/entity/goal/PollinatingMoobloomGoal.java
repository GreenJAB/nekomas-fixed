package net.greenjab.nekomasfixed.registry.entity.goal;

import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;

import java.util.List;

public class PollinatingMoobloomGoal extends Goal {
    private final BeeEntity bee;
    private MoobloomEntity target;

    public PollinatingMoobloomGoal(BeeEntity bee) {this.bee = bee;}

    @Override
    public boolean canStart() {
        if (bee.hasNectar()) {return false;}
        List<MoobloomEntity> list = bee.getEntityWorld().getEntitiesByClass(MoobloomEntity.class, bee.getBoundingBox().expand(8), entity -> !entity.getDataTracker().get(MoobloomEntity.SHEARED));
        if (list.isEmpty()) {return false;}

        this.target = list.get(0);
        return true;
    }

    @Override
    public void start() {
        bee.getNavigation().startMovingTo(target, 1.2D);
    }

    @Override
    public void tick() {
        if (target == null) return;
        bee.getLookControl().lookAt(target);

        if (bee.squaredDistanceTo(target) < 2.0D) {
            bee.setHasNectar(true);

        } else {
            bee.getNavigation().startMovingTo(target, 1.2D);
        }
    }

    @Override
    public boolean shouldContinue() {
        return target != null && target.isAlive() && !bee.hasNectar() && !target.getDataTracker().get(MoobloomEntity.SHEARED);
    }

    @Override
    public boolean canStop(){
        return bee.hasNectar();
    }
}
