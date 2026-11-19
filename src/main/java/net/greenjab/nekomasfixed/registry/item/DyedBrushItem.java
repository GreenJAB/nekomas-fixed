package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.util.AllDyes;
import net.greenjab.nekomasfixed.util.BlockDyeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DyedBrushItem extends Item {
    private final AllDyes color;

    public DyedBrushItem(AllDyes color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public AllDyes getColor() {
        return color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (target(level.getBlockState(pos), color) == null) return InteractionResult.PASS;
        if (!level.isClientSide) {
            dye(level, pos, color);
            Player player = context.getPlayer();
            if (player != null) {
                context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }
            level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static boolean dye(Level level, BlockPos pos, AllDyes color) {
        BlockState state = level.getBlockState(pos);
        Block target = target(state, color);
        if (target == null) return false;
        if (state.getBlock() instanceof BedBlock) {
            Direction facing = state.getValue(BedBlock.FACING);
            BedPart part = state.getValue(BedBlock.PART);
            BlockPos otherPos = pos.relative(part == BedPart.FOOT ? facing : facing.getOpposite());
            BlockState other = level.getBlockState(otherPos);
            level.setBlock(pos, withProperties(target, state), Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
            if (other.is(state.getBlock()) && other.getValue(BedBlock.PART) != part) {
                level.setBlock(otherPos, withProperties(target, other), Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
            }
            return true;
        }
        BlockEntity old = level.getBlockEntity(pos);
        CompoundTag data = old == null ? null : old.saveWithoutMetadata(level.registryAccess());
        level.setBlockAndUpdate(pos, withProperties(target, state));
        if (data != null && level.getBlockEntity(pos) instanceof BlockEntity fresh) {
            fresh.loadWithComponents(data, level.registryAccess());
        }
        return true;
    }

    private static Block target(BlockState state, AllDyes color) {
        Map<AllDyes, Block> family = Families.BY_BLOCK.get(state.getBlock());
        if (family == null) return null;
        Block target = family.get(color);
        return target == state.getBlock() ? null : target;
    }

    private static BlockState withProperties(Block target, BlockState from) {
        BlockState result = target.defaultBlockState();
        for (Property<?> property : from.getProperties()) {
            if (result.hasProperty(property)) result = copy(result, from, property);
        }
        return result;
    }

    private static <T extends Comparable<T>> BlockState copy(BlockState to, BlockState from, Property<T> property) {
        return to.setValue(property, from.getValue(property));
    }

    private static class Families {
        static final Map<Block, Map<AllDyes, Block>> BY_BLOCK = new HashMap<>();

        static {
            vanilla("terracotta", BlockDyeMap.TERRACOTTA, Blocks.TERRACOTTA);
            vanilla("glazed_terracotta", BlockDyeMap.GLAZED_TERRACOTTA);
            vanilla("stained_glass", BlockDyeMap.STAINED_GLASS, Blocks.GLASS);
            vanilla("stained_glass_pane", BlockDyeMap.STAINED_GLASS_PANE, Blocks.GLASS_PANE);
            vanilla("wool", BlockDyeMap.WOOL);
            vanilla("carpet", BlockDyeMap.CARPET);
            vanilla("candle", BlockDyeMap.CANDLE, Blocks.CANDLE);
            vanilla("concrete", BlockDyeMap.CONCRETE);
            vanilla("concrete_powder", BlockDyeMap.CONCRETE_POWDER);
            vanilla("shulker_box", BlockDyeMap.SHULKER_BOX, Blocks.SHULKER_BOX);
            vanilla("bed", BlockDyeMap.BED);
            family(BlockDyeMap.BRICKS, Blocks.BRICKS);
            family(BlockDyeMap.BRICK_SLAB, Blocks.BRICK_SLAB);
            family(BlockDyeMap.BRICK_STAIRS, Blocks.BRICK_STAIRS);
            family(BlockDyeMap.BRICK_WALL, Blocks.BRICK_WALL);
            family(BlockDyeMap.SPOTTED_WOOL);
            family(BlockDyeMap.SPOTTED_CARPET);
            family(BlockDyeMap.FROGLIGHT);
        }

        private static void vanilla(String suffix, Map<AllDyes, Block> ancient, Block... undyed) {
            EnumMap<AllDyes, Block> all = new EnumMap<>(ancient);
            for (AllDyes dye : AllDyes.values()) {
                if (dye.ordinal() > AllDyes.BLACK.ordinal()) break;
                all.put(dye, BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(dye.getSerializedName() + "_" + suffix)));
            }
            family(all, undyed);
        }

        private static void family(Map<AllDyes, Block> all, Block... undyed) {
            for (Block block : all.values()) BY_BLOCK.put(block, all);
            for (Block block : undyed) BY_BLOCK.put(block, all);
        }
    }
}
