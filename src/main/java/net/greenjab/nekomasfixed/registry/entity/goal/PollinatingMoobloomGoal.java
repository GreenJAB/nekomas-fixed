package net.greenjab.nekomasfixed.registry.entity.goal;

import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.bee.Bee;
import java.util.List;

public class PollinatingMoobloomGoal extends Goal {
    private final Bee bee;
    private MoobloomEntity target;

    public PollinatingMoobloomGoal(Bee bee) {this.bee = bee;}

    @Override
    public boolean canUse() {
        if (bee.hasNectar()) {return false;}
        List<MoobloomEntity> list = bee.level().getEntitiesOfClass(MoobloomEntity.class, bee.getBoundingBox().inflate(8), entity -> !entity.getEntityData().get(MoobloomEntity.SHEARED));
        if (list.isEmpty()) {return false;}

        this.target = list.get(0);
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
        return target != null && target.isAlive() && !bee.hasNectar() && !target.getEntityData().get(MoobloomEntity.SHEARED);
    }

    @Override
    public boolean isInterruptable(){
        return bee.hasNectar();
    }
}
