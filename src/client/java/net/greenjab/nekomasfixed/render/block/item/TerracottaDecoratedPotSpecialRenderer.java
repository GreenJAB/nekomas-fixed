package net.greenjab.nekomasfixed.render.block.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.other.PotFaceDecoration;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.render.block.entity.TerracottaDecoratedPotBlockEntityRenderer;
import net.greenjab.nekomasfixed.render.block.entity.state.TerracottaDecoratePotRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class TerracottaDecoratedPotSpecialRenderer implements SpecialModelRenderer<CombinedPotData> {

    private final TerracottaDecoratedPotBlockEntityRenderer decoratedPotRenderer;

    public TerracottaDecoratedPotSpecialRenderer(final TerracottaDecoratedPotBlockEntityRenderer decoratedPotRenderer) {
        this.decoratedPotRenderer = decoratedPotRenderer;
    }

    @Override
    public void submit(@Nullable CombinedPotData data, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        if (data == null) return;
        TerracottaDecoratePotRenderState state = new TerracottaDecoratePotRenderState();
        state.engravedDecorations = data.engraving().orElse(PotEngravingDecoration.EMPTY);
        state.potFace = data.face().orElse(PotFaceDecoration.FALLBACK_BRICKS);
        state.decorations = data.vanillaDecorations().orElse(PotDecorations.EMPTY);

        this.decoratedPotRenderer.submit(state, poseStack, submitNodeCollector, lightCoords, overlayCoords,  outlineColor);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        this.decoratedPotRenderer.getExtents(output);
    }

    @Override
    public @Nullable CombinedPotData extractArgument(ItemStack stack) {
        PotDecorations vanilla = stack.get(DataComponents.POT_DECORATIONS);
        PotFaceDecoration face = stack.get(ComponentRegistry.POT_FACE_BLOCK);
        PotEngravingDecoration engraving = stack.get(ComponentRegistry.POT_ENGRAVED_DECORATIONS);

        if (vanilla == null && face == null && engraving == null) {
            return null;
        }

        return new CombinedPotData(Optional.of(vanilla), Optional.of(engraving), Optional.of(face));
    }


    public record Unbaked() implements SpecialModelRenderer.Unbaked<CombinedPotData> {
        public static final MapCodec<TerracottaDecoratedPotSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new TerracottaDecoratedPotSpecialRenderer.Unbaked());

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked<CombinedPotData>> type() {
            return MAP_CODEC;
        }

        public TerracottaDecoratedPotSpecialRenderer bake(final SpecialModelRenderer.BakingContext context) {
            return new TerracottaDecoratedPotSpecialRenderer(new TerracottaDecoratedPotBlockEntityRenderer(context));
        }
    }
}