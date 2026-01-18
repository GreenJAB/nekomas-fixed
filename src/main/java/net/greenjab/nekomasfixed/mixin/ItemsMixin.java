package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.util.math.Direction;
import net.minecraft.world.waypoint.Waypoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import static net.minecraft.item.Items.register;

@Mixin(Items.class)
public class ItemsMixin {

	@Redirect(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;)Lnet/minecraft/item/Item;", ordinal = 0 ), slice = @Slice( from = @At(value = "FIELD",
			target = "Lnet/minecraft/item/Items;FISHING_ROD:Lnet/minecraft/item/Item;")))
	private static Item wallFloorBlock(String id) {
		return register(
				BlockRegistry.CLOCK,
				(block, settings) -> new VerticallyAttachableBlockItem(
						block, BlockRegistry.WALL_CLOCK, Direction.DOWN, Waypoint.disableTracking(settings)
				),
				new Item.Settings()
		);

	}

}