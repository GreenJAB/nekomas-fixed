package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.block.AbstractEndermanHeadBlock;
import net.greenjab.nekomasfixed.registry.block.FloorEndermanHeadHead;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EndermanHeadBlockEntity extends BlockEntity implements HeldItemContext {

	protected EndermanHeadBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public EndermanHeadBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityTypeRegistry.ENDERMAN_HEAD_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void readData(ReadView view) {
		super.readData(view);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public boolean onSyncedBlockEvent(int type, int data) {
		return super.onSyncedBlockEvent(type, data);
	}

	@Override
	protected void readComponents(ComponentsAccess components) {
		super.readComponents(components);
	}

	@Override
	protected void addComponents(ComponentMap.Builder builder) {
		super.addComponents(builder);
	}

	@Override
	public World getEntityWorld() {
		return this.world;
	}

	@Override
	public Vec3d getEntityPos() {
		return this.getPos().toCenterPos();
	}

	@Override
	public float getBodyYaw() {
		return this.getCachedState().get(FloorEndermanHeadHead.ROTATION);
	}

	public static void tick(World world, BlockPos pos, BlockState state, EndermanHeadBlockEntity blockEntity) {
		int power = state.get(AbstractEndermanHeadBlock.POWER);
		int newPower = 0;
		if (world instanceof ServerWorld serverWorld && world.getTime() % 10L==0L) {
			newPower = getPlayerLooking(world, serverWorld.getServer()
							.getPlayerManager(),pos,world.getRegistryKey());
			if (power!=newPower) {
				((AbstractEndermanHeadBlock)state.getBlock()).setPower(world, pos, state, newPower);
				if (power == 0) world.playSound(null, pos, SoundEvents.ENTITY_ENDERMAN_SCREAM, SoundCategory.BLOCKS, 0.3F, 0.8f);
			}
		}

		if (state.getBlock() instanceof AbstractEndermanHeadBlock && newPower>0 && world.getTime() % 10L == 0L && world.random.nextInt(10)==0){
			world.playSound(null, pos, SoundEvents.ENTITY_ENDERMAN_SCREAM, SoundCategory.BLOCKS, 0.3F, 0.8f);
		}
	}

	public static int getPlayerLooking(World world, PlayerManager playerManager, BlockPos pos, RegistryKey<World> worldKey) {
		int max = 0;
		for (int i = 0; i < playerManager.getPlayerList().size(); i++) {
			ServerPlayerEntity SPE = playerManager.getPlayerList().get(i);
			if (!LivingEntity.NOT_WEARING_GAZE_DISGUISE_PREDICATE.test(SPE)) continue;
			if (SPE.getEntityWorld().getRegistryKey() == worldKey) {
				double x1 = pos.getX() - SPE.getX();
				double y1 = pos.getY() - SPE.getY();
				double z1 = pos.getZ() - SPE.getZ();
				double dist = Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
				if (dist < 50) {
					BlockHitResult hitResult = raycast(world, SPE);
					if (pos.equals(hitResult.getBlockPos())) {
						int v = (int) Math.min(Math.max((48-dist)/3,1),15);
						if (v>max) max = v;
						if (max == 15) return 15;
					}
				}
			}
		}
		return max;
	}

	protected static BlockHitResult raycast(World world, PlayerEntity player) {
		Vec3d vec3d = player.getEyePos();
		Vec3d vec3d2 = vec3d.add(player.getRotationVector(player.getPitch(), player.getYaw()).multiply(45));
		return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
	}
}
