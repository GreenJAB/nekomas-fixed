package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.entity.TargetDummyEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class TargetDummyItem extends Item {
    public TargetDummyItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Direction direction = context.getClickedFace();
        if (direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level world = context.getLevel();
            BlockPlaceContext itemPlacementContext = new BlockPlaceContext(context);
            BlockPos blockPos = itemPlacementContext.getClickedPos();
            ItemStack itemStack = context.getItemInHand();
            Vec3 vec3d = Vec3.atBottomCenterOf(blockPos);
            AABB box = EntityTypeRegistry.TARGET_DUMMY.getDimensions().makeBoundingBox(vec3d.x(), vec3d.y(), vec3d.z());
            if (world.noCollision(null, box) && world.getEntities(null, box).isEmpty()) {
                if (world instanceof ServerLevel serverWorld) {
                    Consumer<TargetDummyEntity> consumer = EntityType.createDefaultStackConfig(serverWorld, itemStack, context.getPlayer());
                    TargetDummyEntity targetDummyEntity = EntityTypeRegistry.TARGET_DUMMY.create(serverWorld, consumer, blockPos, EntitySpawnReason.SPAWN_ITEM_USE, true, true);
                    if (targetDummyEntity == null) {
                        return InteractionResult.FAIL;
                    }

                    float f = Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    targetDummyEntity.snapTo(targetDummyEntity.getX(), targetDummyEntity.getY(), targetDummyEntity.getZ(), f, 0.0F);
                    serverWorld.addFreshEntityWithPassengers(targetDummyEntity);
                    world.playSound(
                            null, targetDummyEntity.getX(), targetDummyEntity.getY(), targetDummyEntity.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F
                    );
                    targetDummyEntity.gameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
                }

                itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}
