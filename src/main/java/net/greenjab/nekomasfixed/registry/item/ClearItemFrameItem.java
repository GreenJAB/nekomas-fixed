package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.registry.entity.ClearItemFrameEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemFrameItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class ClearItemFrameItem extends ItemFrameItem {
    public ClearItemFrameItem(EntityType<? extends HangingEntity> entityType, Properties settings) {
        super(entityType, settings);
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockPos2 = blockPos.relative(direction);
        Player playerEntity = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        if (playerEntity != null && !this.mayPlace(playerEntity, direction, itemStack, blockPos2)) {
            return InteractionResult.FAIL;
        } else {
            Level world = context.getLevel();
            HangingEntity abstractDecorationEntity = new ClearItemFrameEntity(world, blockPos2, direction);


            CustomData nbtComponent = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
            if (!nbtComponent.isEmpty()) {
                EntityType.updateCustomEntityTag(world, playerEntity, abstractDecorationEntity, nbtComponent);
            }

            if (abstractDecorationEntity.survives()) {
                if (!world.isClientSide) {
                    abstractDecorationEntity.playPlacementSound();
                    world.gameEvent(playerEntity, GameEvent.ENTITY_PLACE, abstractDecorationEntity.position());
                    world.addFreshEntity(abstractDecorationEntity);
                }

                itemStack.shrink(1);
                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }
}
