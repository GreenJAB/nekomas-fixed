package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.waypoints.Waypoint;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import static net.minecraft.world.item.Items.registerBlock;

@Mixin(Items.class)
public class ItemsMixin {

	@Redirect(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;)Lnet/minecraft/world/item/Item;"), slice = @Slice( from =
	@At(value = "CONSTANT", args = "stringValue=clock"), to =
	@At(value = "FIELD",target = "Lnet/minecraft/world/item/Items;CLOCK:Lnet/minecraft/world/item/Item;", opcode = Opcodes.PUTSTATIC)))
	private static Item wallFloorClock(String name) {
		return registerBlock(BlockRegistry.CLOCK, (block, settings) -> new StandingAndWallBlockItem(
						block, BlockRegistry.WALL_CLOCK, Direction.DOWN, Waypoint.addHideAttribute(settings)),
				new Item.Properties());
	}
}