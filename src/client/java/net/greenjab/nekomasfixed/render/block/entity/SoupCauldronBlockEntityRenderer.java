package net.greenjab.nekomasfixed.render.block.entity;

import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.render.block.entity.state.SoupCauldronBlockEntityRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SoupCauldronBlockEntityRenderer implements BlockEntityRenderer<SoupCauldronBlockEntity, SoupCauldronBlockEntityRenderState> {
    private final ItemModelManager itemModelManager;
    private static final Map<Item, Integer> FOOD_COLORS = Map.ofEntries(
            Map.entry(Items.APPLE, 0xFF3B3B),
            Map.entry(Items.CARROT, 0xFFA500),
            Map.entry(Items.BREAD, 0xD2B48C),
            Map.entry(Items.MELON_SLICE, 0xFF6B6B),
            Map.entry(Items.POTATO, 0xC8A060),
            Map.entry(Items.BEEF, 0x8B2E2E),
            Map.entry(Items.COOKED_BEEF, 0x5A2C2C),
            Map.entry(Items.GOLDEN_APPLE, 0xFFD700)
    );

    public SoupCauldronBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemModelManager = ctx.itemModelManager();
    }

    @Override
    public SoupCauldronBlockEntityRenderState createRenderState() {
        return new SoupCauldronBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(SoupCauldronBlockEntity blockEntity, SoupCauldronBlockEntityRenderState state, float f, Vec3d vec3d, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlayCommand) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, f, vec3d, crumblingOverlayCommand);
        int i = (int)blockEntity.getPos().asLong();
        state.inputItems = new ArrayList<>();
        assert blockEntity.getWorld() != null;
        state.animationTime = blockEntity.getWorld().getTime() + f;
        state.stirProgress = blockEntity.getStirTicks()-f*10;
        state.hasStirred = blockEntity.hasStirred;

        for(int j = 0; j < blockEntity.getInputs().size(); ++j) {
            ItemRenderState itemRenderState = new ItemRenderState();
            this.itemModelManager.clearAndUpdate(itemRenderState, blockEntity.getInputs().get(j), ItemDisplayContext.FIXED, blockEntity.getWorld(), null, i + j);
            state.inputItems.add(itemRenderState);
        }

    }

    @Override
    public void render(SoupCauldronBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        List<ItemRenderState> list = state.inputItems;
        for(int i = 0; i < list.size(); ++i) {
            ItemRenderState itemRenderState = list.get(i);

            if (!itemRenderState.isEmpty() && !state.hasStirred) {
                matrices.push();
                float bob = (float)Math.sin(state.animationTime * 0.1f) * 0.02f;
                matrices.translate(0.5F, 1F + bob, 0.5F);
                Direction direction2 = Direction.fromHorizontalQuarterTurns((i + Direction.NORTH.getHorizontalQuarterTurns()) % 4);
                float f = -direction2.getPositiveHorizontalDegrees();
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(f));
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-70.0F));
                matrices.translate(-0.23, -0.1, 0.0F);
                matrices.scale(0.275F, 0.375F, 0.275F);

                itemRenderState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
                matrices.pop();
            }
        }

    }

    public static int getTintIndex(BlockRenderView world, BlockPos pos, int tintIndex){
        if(world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity soupCauldronBlockEntity){
            return tintIndex == 0 ? blendFoodColors(soupCauldronBlockEntity.getInputs()) : 0xFFFFFFFF;
        }else{
            return -1;
        }
    }

    public static Optional<Integer> getFoodColor(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return Optional.empty();

        Item item = stack.getItem();
        Integer foodColor = FOOD_COLORS.get(item);
        if (foodColor != null) {
            return Optional.of(foodColor);
        }
        if (stack.contains(DataComponentTypes.POTION_CONTENTS)) {
            var contents = stack.get(DataComponentTypes.POTION_CONTENTS);
            if (contents != null) {
                return Optional.of(contents.getColor());
            }
        }

        return Optional.empty();
    }

    public static int blendFoodColors(List<ItemStack> items) {
        float totalR = 0.0F;
        float totalG = 0.0F;
        float totalB = 0.0F;
        float totalWeight = 0.0F;

        for (ItemStack stack : items) {
            if (stack.isEmpty()) continue;

            Optional<Integer> colorOpt = getFoodColor(stack);
            if (colorOpt.isEmpty()) continue;

            int color = colorOpt.get();

            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;

            float weight = stack.getCount();

            totalR += r * weight;
            totalG += g * weight;
            totalB += b * weight;
            totalWeight += weight;
        }

        if (totalWeight == 0) return 0x385DC6; // fallback

        float r = totalR / totalWeight;
        float g = totalG / totalWeight;
        float b = totalB / totalWeight;

        // optional brightness normalization (potion feel)
        float max = Math.max(r, Math.max(g, b));
        if (max > 0) {
            float scale = 1.0F / max;
            r *= scale;
            g *= scale;
            b *= scale;
        }

        int finalR = (int)(r * 255.0F);
        int finalG = (int)(g * 255.0F);
        int finalB = (int)(b * 255.0F);

        return (finalR << 16) | (finalG << 8) | finalB;
    }
}
