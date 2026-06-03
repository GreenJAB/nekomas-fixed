package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import java.util.ArrayList;
import java.util.Arrays;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin{

    @ModifyArg(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType;create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType;", ordinal = 0), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=sign"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/block/entity/BlockEntityType;SIGN:Lnet/minecraft/block/entity/BlockEntityType;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Block[] sign(Block[] validBlocks) {
        ArrayList<Block> newBlocks = new ArrayList<>(Arrays.asList(validBlocks));
        newBlocks.add(BlockRegistry.BAOBAB_SIGN);
        newBlocks.add(BlockRegistry.BAOBAB_WALL_SIGN);
        Block[] newBlocksArray = new Block[newBlocks.size()];
        for (int i = 0;i<newBlocksArray.length;i++) { newBlocksArray[i]=newBlocks.get(i); }
        return newBlocksArray;
    }

    @ModifyArg(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType;create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType;", ordinal = 0), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=hanging_sign"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/block/entity/BlockEntityType;HANGING_SIGN:Lnet/minecraft/block/entity/BlockEntityType;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Block[] hanging_sign(Block[] validBlocks) {
        ArrayList<Block> newBlocks = new ArrayList<>(Arrays.asList(validBlocks));
        newBlocks.add(BlockRegistry.BAOBAB_HANGING_SIGN);
        newBlocks.add(BlockRegistry.BAOBAB_WALL_HANGING_SIGN);
        Block[] newBlocksArray = new Block[newBlocks.size()];
        for (int i = 0;i<newBlocksArray.length;i++) { newBlocksArray[i]=newBlocks.get(i); }
        return newBlocksArray;
    }

    @ModifyArg(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType;create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType;", ordinal = 0), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=shelf"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/block/entity/BlockEntityType;SHELF:Lnet/minecraft/block/entity/BlockEntityType;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Block[] shelf(Block[] validBlocks) {
        ArrayList<Block> newBlocks = new ArrayList<>(Arrays.asList(validBlocks));
        newBlocks.add(BlockRegistry.BAOBAB_SHELF);
        Block[] newBlocksArray = new Block[newBlocks.size()];
        for (int i = 0;i<newBlocksArray.length;i++) { newBlocksArray[i]=newBlocks.get(i); }
        return newBlocksArray;
    }

    @ModifyArg(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType;create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType;", ordinal = 0), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=bed"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/block/entity/BlockEntityType;BED:Lnet/minecraft/block/entity/BlockEntityType;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Block[] bed(Block[] validBlocks) {
        ArrayList<Block> newBlocks = new ArrayList<>(Arrays.asList(validBlocks));
        newBlocks.add(BlockRegistry.AMBER_BED);
        newBlocks.add(BlockRegistry.AQUA_BED);
        newBlocks.add(BlockRegistry.INDIGO_BED);
        newBlocks.add(BlockRegistry.MAROON_BED);
        Block[] newBlocksArray = new Block[newBlocks.size()];
        for (int i = 0;i<newBlocksArray.length;i++) { newBlocksArray[i]=newBlocks.get(i); }
        return newBlocksArray;
    }

    @ModifyArg(method="<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType;create(Ljava/lang/String;Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType;", ordinal = 0), slice = @Slice(from =
    @At(value = "CONSTANT", args = "stringValue=shulker_box"), to =
    @At(value = "FIELD",target = "Lnet/minecraft/block/entity/BlockEntityType;SHULKER_BOX:Lnet/minecraft/block/entity/BlockEntityType;", opcode = Opcodes.PUTSTATIC)), index = 2)
    private static Block[] shulker(Block[] validBlocks) {
        ArrayList<Block> newBlocks = new ArrayList<>(Arrays.asList(validBlocks));
        newBlocks.add(BlockRegistry.AMBER_SHULKER_BOX);
        newBlocks.add(BlockRegistry.AQUA_SHULKER_BOX);
        newBlocks.add(BlockRegistry.INDIGO_SHULKER_BOX);
        newBlocks.add(BlockRegistry.MAROON_SHULKER_BOX);
        Block[] newBlocksArray = new Block[newBlocks.size()];
        for (int i = 0;i<newBlocksArray.length;i++) { newBlocksArray[i]=newBlocks.get(i); }
        return newBlocksArray;
    }


}