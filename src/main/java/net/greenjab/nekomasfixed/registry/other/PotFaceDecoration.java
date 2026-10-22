package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.greenjab.nekomasfixed.registry.block.entity.PotMaps;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.crafting.DecoratedPotRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public record PotFaceDecoration(Optional<BlockState> potFaceBlock) implements TooltipProvider {
    public static PotFaceDecoration FALLBACK_BRICKS = new PotFaceDecoration(Blocks.BRICKS.defaultBlockState());
    public static PotFaceDecoration EMPTY = new PotFaceDecoration(Optional.empty());
    private static final StreamCodec<ByteBuf, BlockState> BLOCK_STATE_CODEC =
            ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY);

    private static final StreamCodec<ByteBuf, Optional<BlockState>> OPTIONAL_BLOCK_STATE_CODEC =
            ByteBufCodecs.optional(BLOCK_STATE_CODEC);

    public static final Codec<PotFaceDecoration> CODEC =
            BlockState.CODEC.optionalFieldOf("block").xmap(PotFaceDecoration::new, PotFaceDecoration::potFaceBlock)
                    .codec();


    public void dropItem(Level level, BlockPos pos, ItemStack stack){
        if(!stack.isEmpty() && stack.equals(this.getSafeBlock().asItem().getDefaultInstance())) return;
        ItemEntity entity = new ItemEntity(level, pos.getX()+0.2, pos.getY()+0.2, pos.getZ()+0.2, stack);
        level.addFreshEntity(entity);
    }

    public Block getSafeBlock(){
        return (potFaceBlock.isEmpty() || potFaceBlock.get().isAir() ? FALLBACK_BRICKS.potFaceBlock().get() : this.potFaceBlock.get()).getBlock();
    }

    public boolean equals(Object e){
        if(e instanceof PotFaceDecoration decoration){

            return  decoration.getSafeBlock().equals(this.getSafeBlock()) || decoration.potFaceBlock.equals(this.potFaceBlock);
        }
        return false;
    }

    public static final StreamCodec<ByteBuf, PotFaceDecoration> STREAM_CODEC =
            OPTIONAL_BLOCK_STATE_CODEC.map(PotFaceDecoration::new, PotFaceDecoration::potFaceBlock);

    public PotFaceDecoration(BlockState state){
        this(Optional.of(state));
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        consumer.accept(CommonComponents.EMPTY);
        consumer.accept(Component.translatable("terracotta_decorated_pot.made_of").append(getSafeBlock().getName()).withColor(PotMaps.POT_FACE_TO_COLOR_MAPPING.get(getSafeBlock())));
    }
}
