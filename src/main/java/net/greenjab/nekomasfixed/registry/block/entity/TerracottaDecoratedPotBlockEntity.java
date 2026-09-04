package net.greenjab.nekomasfixed.registry.block.entity;

import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.other.PotFaceDecoration;
import net.greenjab.nekomasfixed.registry.registries.BlockEntityTypeRegistry;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.DecoratedPotRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class TerracottaDecoratedPotBlockEntity extends BlockEntity implements ContainerSingleItem.BlockContainerSingleItem, RandomizableContainer {
    public TerracottaDecoratedPotBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BlockEntityTypeRegistry.TERRACOTTA_DECORATED_POT_BLOCK_ENTITY, worldPosition, blockState);
        this.decorations = PotDecorations.EMPTY;
        this.engravingDecorations = PotEngravingDecoration.EMPTY;
        this.faceDecoration = PotFaceDecoration.FALLBACK_BRICKS;
    }


    public static final String TAG_SHERDS = "sherds";
    public static final String TAG_ITEM = "item";
    public static final int EVENT_POT_WOBBLES = 1;
    public long wobbleStartedAtTick;
    public TerracottaDecoratedPotBlockEntity.@Nullable WobbleStyle lastWobbleStyle;
    private PotDecorations decorations;
    public PotEngravingDecoration engravingDecorations;
    public PotFaceDecoration faceDecoration;
    private ItemStack item = ItemStack.EMPTY;
    protected @Nullable ResourceKey<LootTable> lootTable;
    protected long lootTableSeed;



    @Override
    protected void saveAdditional(final ValueOutput output) {
        super.saveAdditional(output);
        if (!this.decorations.equals(PotDecorations.EMPTY)) {
            output.store("sherds", PotDecorations.CODEC, this.decorations);
        }

        if (!this.trySaveLootTable(output) && !this.item.isEmpty()) {
            output.store("item", ItemStack.CODEC, this.item);
        }

        if (this.engravingDecorations != null && !this.engravingDecorations.equals(PotEngravingDecoration.EMPTY)) {
            output.store("engraved", PotEngravingDecoration.CODEC, this.engravingDecorations);
        }

        if (this.faceDecoration != null && !this.faceDecoration.equals(PotFaceDecoration.EMPTY)) {
            output.store("pot_face", PotFaceDecoration.CODEC, this.faceDecoration);
        }


    }

    @Override
    protected void loadAdditional(final ValueInput input) {
        super.loadAdditional(input);
        this.decorations = input.read("sherds", PotDecorations.CODEC).orElse(PotDecorations.EMPTY);
        this.engravingDecorations = input.read("engraved", PotEngravingDecoration.CODEC).orElse(PotEngravingDecoration.EMPTY);
        this.faceDecoration = input.read("pot_face", PotFaceDecoration.CODEC).orElse(PotFaceDecoration.EMPTY);
        if (!this.tryLoadLootTable(input)) {
            this.item = input.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        } else {
            this.item = ItemStack.EMPTY;
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(final HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    public Direction getDirection() {
        return this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
    }

    public PotDecorations getDecorations() {
        return this.decorations;
    }
    public PotEngravingDecoration getEngravingDecorations() {
        return this.engravingDecorations;
    }
    public PotFaceDecoration getPotFace() {
        return this.faceDecoration;
    }

    public static ItemStackTemplate createDecoratedPotTemplate(final PotDecorations decorations, final PotEngravingDecoration engravingDecorations, PotFaceDecoration faceDecoration) {
        return new ItemStackTemplate(Items.DECORATED_POT, DataComponentPatch.builder().set(DataComponents.POT_DECORATIONS, decorations).set(ComponentRegistry.POT_ENGRAVED_DECORATIONS, engravingDecorations).set(ComponentRegistry.POT_FACE_BLOCK, faceDecoration).build());
    }

    public static ItemStack createDecoratedPotInstance(final PotDecorations decorations, final PotEngravingDecoration engravingDecorations, final PotFaceDecoration potFaceDecoration) {
        return createDecoratedPotTemplate(decorations,engravingDecorations, potFaceDecoration).create();
    }

    @Override
    public @Nullable ResourceKey<LootTable> getLootTable() {
        return this.lootTable;
    }

    @Override
    public void setLootTable(final @Nullable ResourceKey<LootTable> lootTable) {
        this.lootTable = lootTable;
    }

    @Override
    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    @Override
    public void setLootTableSeed(final long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    @Override
    protected void collectImplicitComponents(final DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.POT_DECORATIONS, this.decorations);
        components.set(ComponentRegistry.POT_ENGRAVED_DECORATIONS, this.engravingDecorations);
        components.set(ComponentRegistry.POT_FACE_BLOCK, this.faceDecoration);
        components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(List.of(this.item)));
    }

    @Override
    protected void applyImplicitComponents(final DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.decorations = components.getOrDefault(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY);
        this.engravingDecorations = components.getOrDefault(ComponentRegistry.POT_ENGRAVED_DECORATIONS, PotEngravingDecoration.EMPTY);
        this.faceDecoration = components.getOrDefault(ComponentRegistry.POT_FACE_BLOCK, PotFaceDecoration.FALLBACK_BRICKS);
        this.item = components.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyOne();
    }

    @Override
    public void removeComponentsFromTag(final ValueOutput output) {
        super.removeComponentsFromTag(output);
        output.discard("sherds");
        output.discard("item");
        output.discard("pot_face");
    }

    @Override
    public ItemStack getTheItem() {
        this.unpackLootTable(null);
        return this.item;
    }

    @Override
    public ItemStack splitTheItem(final int count) {
        this.unpackLootTable(null);
        ItemStack result = this.item.split(count);
        if (this.item.isEmpty()) {
            this.item = ItemStack.EMPTY;
        }

        return result;
    }

    @Override
    public void setTheItem(final ItemStack itemStack) {
        this.unpackLootTable(null);
        this.item = itemStack;
    }

    @Override
    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    public void wobble(final TerracottaDecoratedPotBlockEntity.WobbleStyle wobbleStyle) {
        if (this.level != null && !this.level.isClientSide()) {
            this.level.blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), 1, wobbleStyle.ordinal());
        }
    }

    @Override
    public boolean triggerEvent(final int event, final int data) {
        if (this.level != null && event == 1 && data >= 0 && data < TerracottaDecoratedPotBlockEntity.WobbleStyle.values().length) {
            this.wobbleStartedAtTick = this.level.getGameTime();
            this.lastWobbleStyle = TerracottaDecoratedPotBlockEntity.WobbleStyle.values()[data];
            return true;
        } else {
            return super.triggerEvent(event, data);
        }
    }

    public enum WobbleStyle {
        POSITIVE(7),
        NEGATIVE(10);

        public final int duration;

        WobbleStyle(final int duration) {
            this.duration = duration;
        }
    }
}
