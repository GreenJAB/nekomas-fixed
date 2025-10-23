package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class MegaBoatEntity extends AbstractChestBoatEntity {

	private ItemStack BANNER = ItemStack.EMPTY;

	public MegaBoatEntity(EntityType<? extends AbstractChestBoatEntity> entityType, World world, Supplier<Item> supplier) {
		super(entityType, world, supplier);
	}

	@Override
	protected float getPassengerHorizontalOffset() {
		return 0.15F;
	}

	@Override
	protected double getPassengerAttachmentY(EntityDimensions dimensions) {
		return dimensions.height() / 3.0F;
	}

	@Override
	protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
		float f = 0.2f- this.getPassengerList().indexOf(passenger)*0.8f;
		return new Vec3d(0.0, this.getPassengerAttachmentY(dimensions), f).rotateY(-this.getYaw() * (float) (Math.PI / 180.0));
	}

	@Override
	protected int getMaxPassengers() {
		return 3;
	}

	@Override
	protected void writeCustomData(WriteView view) {
		super.writeCustomData(view);
		if (!BANNER.isEmpty()) {
			view.put("Banner", ItemStack.CODEC, BANNER);
		}
	}

	@Override
	protected void readCustomData(ReadView view) {
		super.readCustomData(view);
		BANNER = view.read("Banner", ItemStack.CODEC).orElse(ItemStack.EMPTY);
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isIn(ItemTags.BANNERS)) {
			if (BANNER.isEmpty()) {
				BANNER =itemStack.copyWithCount(1);
				itemStack.decrement(1);
				player.getEntityWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_WIND_CHARGE_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
			return ActionResult.SUCCESS;
		}
		if (itemStack.isOf(Items.SHEARS)) {
			if (!BANNER.isEmpty()) {
				player.getEntityWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_COPPER_GOLEM_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
				ItemStack banner = BANNER.copy();
				BANNER = ItemStack.EMPTY;
				if (player.getEntityWorld() instanceof ServerWorld serverWorld) {
				this.dropStack(serverWorld, banner, 1.5F);
				itemStack.damage(1, player);
				}
			}
			return ActionResult.SUCCESS;
		}
		return super.interact(player, hand);
	}

	public ItemStack getBanner() {
		return BANNER;
	}

	public float getSpeed() {
		//return 0.3f+getPlayerPassengers()*0.1f+(!BANNER.isEmpty()?0.2f:0f);
		return !BANNER.isEmpty()?0.8f:0.4f;
	}

}