package net.greenjab.nekomasfixed.registry.entity.WildFire;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class WildfireMovementUtil {

	public static boolean cantMoveTo(WildfireEntity wildFire, Vec3 pos) {
		Vec3 vec3d = new Vec3(wildFire.getX(), wildFire.getY(), wildFire.getZ());
		return pos.distanceTo(vec3d) > getMaxMoveDistance(wildFire) || wildFire.level().clip(new ClipContext(vec3d, pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, wildFire)).getType() != HitResult.Type.MISS;
	}

	private static double getMaxMoveDistance(WildfireEntity wildFire) {
		return Math.max(50.0, wildFire.getAttributeValue(Attributes.FOLLOW_RANGE));
	}

	public static Vec3 findFirePos(WildfireEntity wildFire, boolean findAnyway) {
		BlockPos blockPos = wildFire.blockPosition();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		RandomSource random = wildFire.getRandom();

		for (int i = 0; i < 100; i++) {
			int x = -7 + random.nextInt(15);
			int y = 2;
			int z = -7 + random.nextInt(15);

			mutable.setWithOffset(blockPos, x, y, z);
			while (wildFire.level().getBlockState(mutable).is(BlockTags.AIR)&&y>-4){
				y--;
				mutable.setWithOffset(blockPos, x, y, z);
			}
			if (wildFire.isWithinHome(mutable) && wildFire.level().getBlockState(mutable).is(BlockTags.FIRE)) {
				return mutable.getCenter();
			}
		}
		if (findAnyway) return mutable.getCenter();
		return null;
	}
}
