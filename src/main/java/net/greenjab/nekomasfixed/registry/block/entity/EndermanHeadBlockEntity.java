package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.block.AbstractEndermanHeadBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
public class EndermanHeadBlockEntity extends BlockEntity {

	protected EndermanHeadBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public EndermanHeadBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityTypeRegistry.ENDERMAN_HEAD_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void loadAdditional(@NonNull ValueInput view) {
		super.loadAdditional(view);
	}

	@Override
	protected void saveAdditional(@NonNull ValueOutput view) {
		super.saveAdditional(view);
	}

	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public boolean triggerEvent(int type, int data) {
		return super.triggerEvent(type, data);
	}

	@Override
	protected void applyImplicitComponents(@NonNull DataComponentGetter components) {
		super.applyImplicitComponents(components);
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.@NonNull Builder builder) {
		super.collectImplicitComponents(builder);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, EndermanHeadBlockEntity blockEntity) {
		int power = state.getValue(AbstractEndermanHeadBlock.POWER);
		int newPower = 0;
		if (level instanceof ServerLevel serverLevel && level.getGameTime() % 10L==0L) {
			newPower = getPlayerLooking(level, serverLevel.getServer()
							.getPlayerList(),pos, level.dimension());
			if (power!=newPower) {
				((AbstractEndermanHeadBlock)state.getBlock()).setPower(level, pos, state, newPower);
				if (power == 0) level.playSound(null, pos, SoundEvents.ENDERMAN_SCREAM, SoundSource.BLOCKS, 0.3F, 0.8f);
			}
		}

		if (state.getBlock() instanceof AbstractEndermanHeadBlock && newPower>0 && level.getGameTime() % 10L == 0L && level.getRandom().nextInt(10)==0){
			level.playSound(null, pos, SoundEvents.ENDERMAN_SCREAM, SoundSource.BLOCKS, 0.3F, 0.8f);
		}
	}

	public static int getPlayerLooking(Level level, PlayerList playerManager, BlockPos pos, ResourceKey<Level> levelKey) {
		int max = 0;
		for (int i = 0; i < playerManager.getPlayers().size(); i++) {
			ServerPlayer SPE = playerManager.getPlayers().get(i);
			if (!LivingEntity.PLAYER_NOT_WEARING_DISGUISE_ITEM.test(SPE)) continue;
			if (SPE.isSpectator()) continue;
			if (SPE.level().dimension() == levelKey) {
				double x1 = pos.getX() - SPE.getX();
				double y1 = pos.getY() - SPE.getY();
				double z1 = pos.getZ() - SPE.getZ();
				double dist = Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
				if (dist < 50) {
					BlockHitResult hitResult = raycast(level, SPE);
					if (pos.equals(hitResult.getBlockPos())) {
						int v = (int) Mth.clamp((48 - dist) / 3, 1, 15);
						if (v>max) max = v;
						if (max == 15) return 15;
					}
				}
			}
		}
		return max;
	}

	protected static BlockHitResult raycast(Level level, Player player) {
		Vec3 vec3d = player.getEyePosition();
		Vec3 vec3d2 = vec3d.add(player.calculateViewVector(player.getXRot(), player.getYRot()).scale(45));
		return level.clip(new ClipContext(vec3d, vec3d2, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
	}
}
