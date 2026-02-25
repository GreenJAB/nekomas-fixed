package net.greenjab.nekomasfixed.registry.entity.WildFire;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;

public class WildFireMovementUtil {

	public static boolean cantMoveTo(WildFireEntity wildFire, Vec3d pos) {
		Vec3d vec3d = new Vec3d(wildFire.getX(), wildFire.getY(), wildFire.getZ());
		return pos.distanceTo(vec3d) > getMaxMoveDistance(wildFire) || wildFire.getEntityWorld().raycast(new RaycastContext(vec3d, pos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, wildFire)).getType() != HitResult.Type.MISS;
	}

	private static double getMaxMoveDistance(WildFireEntity wildFire) {
		return Math.max(50.0, wildFire.getAttributeValue(EntityAttributes.FOLLOW_RANGE));
	}

	public static Vec3d findFirePos(WildFireEntity wildFire, boolean findAnyway) {
		BlockPos blockPos = wildFire.getBlockPos();
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Random random = wildFire.getRandom();

		for (int i = 0; i < 100; i++) {
			int x = -7 + random.nextInt(15);
			int y = 2;
			int z = -7 + random.nextInt(15);

			mutable.set(blockPos, x, y, z);
			while (wildFire.getEntityWorld().getBlockState(mutable).isIn(BlockTags.AIR)&&y>-4){
				y--;
				mutable.set(blockPos, x, y, z);
			}
			if (wildFire.isInPositionTargetRange(mutable) && wildFire.getEntityWorld().getBlockState(mutable).isIn(BlockTags.FIRE)) {
				return mutable.toCenterPos();
			}
		}
		if (findAnyway) return mutable.toCenterPos();
		return null;
	}
}
