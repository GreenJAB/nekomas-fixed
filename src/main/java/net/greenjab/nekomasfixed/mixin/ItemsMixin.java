package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.util.math.Direction;
import net.minecraft.world.waypoint.Waypoint;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import static net.minecraft.item.Items.register;

@Mixin(Items.class)
public class ItemsMixin {

	@Redirect(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;)Lnet/minecraft/item/Item;"), slice = @Slice( from =
	@At(value = "CONSTANT", args = "stringValue=clock"), to =
	@At(value = "FIELD",target = "Lnet/minecraft/item/Items;CLOCK:Lnet/minecraft/item/Item;", opcode = Opcodes.PUTSTATIC)))
	private static Item wallFloorClock(String name) {
		return register(BlockRegistry.CLOCK, (block, settings) -> new VerticallyAttachableBlockItem(
						block, BlockRegistry.WALL_CLOCK, Direction.DOWN, Waypoint.disableTracking(settings)),
				new Item.Settings());
	}
}