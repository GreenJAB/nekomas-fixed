package net.greenjab.nekomasfixed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public class ItemsMixin {

    // Replaces the vanilla clock item (a plain Item) with a standing+wall block item. The plain
    // Item constructor already registers an intrusive holder keyed on that instance, so the
    // wrapped original call must run first to clear it before minecraft:clock is re-registered
    // with the block item.
    @WrapOperation(
            method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;Lnet/minecraft/world/item/Item;)Lnet/minecraft/world/item/Item;"),
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=clock"),
                    to = @At(value = "FIELD",
                            target = "Lnet/minecraft/world/item/Items;CLOCK:Lnet/minecraft/world/item/Item;",
                            opcode = Opcodes.PUTSTATIC)))
    private static Item wallFloorClock(String id, Item original, Operation<Item> originalCall) {
        originalCall.call(id, original);
        return Items.registerItem(
                ResourceKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("clock")),
                new StandingAndWallBlockItem(
                        BlockRegistry.CLOCK, BlockRegistry.WALL_CLOCK, new Item.Properties(), Direction.DOWN));
    }
}