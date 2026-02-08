package net.greenjab.nekomasfixed.registry.entity.WildFire;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;

public class WildFireMovementUtil {

	public static Vec3d getRandomPosBehindTarget(LivingEntity target, Random random) {
		float f = target.headYaw + 180.0F + (float)random.nextGaussian() * 90.0F / 2.0F;
		float g = MathHelper.lerp(random.nextFloat(), 4.0F, 8.0F);
		Vec3d vec3d = Vec3d.fromPolar(0.0F, f).multiply(g);
		return target.getEntityPos().add(vec3d);
	}

	public static boolean canMoveTo(WildFireEntity wildFire, Vec3d pos) {
		Vec3d vec3d = new Vec3d(wildFire.getX(), wildFire.getY(), wildFire.getZ());
		return !(pos.distanceTo(vec3d) > getMaxMoveDistance(wildFire)) && wildFire.getEntityWorld().raycast(new RaycastContext(vec3d, pos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, wildFire)).getType()
                == HitResult.Type.MISS;
	}

	private static double getMaxMoveDistance(WildFireEntity wildFire) {
		return Math.max(50.0, wildFire.getAttributeValue(EntityAttributes.FOLLOW_RANGE));
	}
}
