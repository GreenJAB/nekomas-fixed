package net.greenjab.nekomasfixed.mixin.accessor;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// Exposes Mob.goalSelector (protected final in 1.21.1) so the bee mixin can add the
// moobloom-pollination goal. Declared on Mob because that's where the field lives.
@Mixin(Mob.class)
public interface MobAccessor {
    @Accessor("goalSelector")
    GoalSelector getGoalSelector();
}