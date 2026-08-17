package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.block.FletchingTableBlock;
import net.greenjab.nekomasfixed.registry.block.MelonBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Function;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Lnet/minecraft/references/BlockItemId;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0), slice = @Slice( from =
    @At(value = "FIELD", target = "Lnet/minecraft/references/BlockItemIds;MELON:Lnet/minecraft/references/BlockItemId;", opcode = Opcodes.GETSTATIC), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/level/block/Blocks;MELON:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.PUTSTATIC)))
    private static Block newMelon(BlockItemId id, BlockBehaviour.Properties properties) {
        return register(id.block(), settings -> new MelonBlock(false, settings), properties);
    }
    @Redirect(method="<clinit>", at = @At( value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Lnet/minecraft/references/BlockItemId;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0), slice = @Slice( from =
    @At(value = "FIELD", target = "Lnet/minecraft/references/BlockItemIds;FLETCHING_TABLE:Lnet/minecraft/references/BlockItemId;", opcode = Opcodes.GETSTATIC), to =
    @At(value = "FIELD",target = "Lnet/minecraft/world/level/block/Blocks;FLETCHING_TABLE:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.PUTSTATIC)))
    private static Block newFletchingTable(BlockItemId id, BlockBehaviour.Properties properties) {
        return register(id.block(), FletchingTableBlock::new, properties);
    }

    @Unique
    private static Block register(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        Block block = factory.apply(settings.setId(key));
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }
}
