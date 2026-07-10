package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.entity.TargetDummy;
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
        if (direction == Direction.DOWN) return InteractionResult.FAIL;
        Level level = context.getLevel();
        BlockPlaceContext itemPlacementContext = new BlockPlaceContext(context);
        BlockPos blockPos = itemPlacementContext.getClickedPos();
        ItemStack itemStack = context.getItemInHand();
        Vec3 vec3d = Vec3.atBottomCenterOf(blockPos);
        AABB box = EntityTypeRegistry.TARGET_DUMMY.getDimensions().makeBoundingBox(vec3d.x(), vec3d.y(), vec3d.z());
        if (level.noCollision(null, box) && level.getEntities(null, box).isEmpty()) {
            if (level instanceof ServerLevel serverLevel) {
                Consumer<TargetDummy> consumer = EntityType.createDefaultStackConfig(serverLevel, itemStack, context.getPlayer());
                TargetDummy targetDummy = EntityTypeRegistry.TARGET_DUMMY.create(serverLevel, consumer, blockPos, EntitySpawnReason.SPAWN_ITEM_USE, true, true);
                if (targetDummy == null)  return InteractionResult.FAIL;
                float f = Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                targetDummy.snapTo(targetDummy.getX(), targetDummy.getY(), targetDummy.getZ(), f, 0.0F);
                serverLevel.addFreshEntityWithPassengers(targetDummy);
                level.playSound(null, targetDummy.getX(), targetDummy.getY(), targetDummy.getZ(),
                        SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                targetDummy.gameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
            }
            itemStack.shrink(1);
            return InteractionResult.SUCCESS;
        } else return InteractionResult.FAIL;
    }
}
