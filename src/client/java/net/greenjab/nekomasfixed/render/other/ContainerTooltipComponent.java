package net.greenjab.nekomasfixed.render.other;


import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ContainerTooltipComponent implements ClientTooltipComponent {
    private static final Identifier BUNDLE_SLOT_BACKGROUND_TEXTURE = Identifier.withDefaultNamespace("container/bundle/slot_background");
    private final ItemContainerContents contents;
    private int numberOfSlots;

    public ContainerTooltipComponent(ItemContainerContents contents) {
        this.contents = contents;
        numberOfSlots = (int) Math.min(27, contents.nonEmptyStream().count());
    }

    @Override
    public int getHeight(@NonNull Font textRenderer) {
        return this.getHeightOfNonEmpty();
    }

    @Override
    public int getWidth(@NonNull Font textRenderer) {
        return this.getColumnsHeight() ;
    }

    @Override
    public boolean showTooltipWithItemInHand() {
        return true;
    }

    private int getHeightOfNonEmpty() {
        return this.getRowsHeight();
    }

    private int getRowsHeight() {
        return this.getRows() * 24;
    }

    private int getRows() {
        if (numberOfSlots==0)return 0;
        return Math.min((int)Math.ceil(numberOfSlots/ (getColumns()+0.0)),3);
    }
    private int getColumns() {
        if (numberOfSlots == 0) return 0;
        return Mth.ceil(Math.max(Math.sqrt(numberOfSlots), numberOfSlots / 3.0));
    }

    private int getColumnsHeight() {
        return this.getColumns() * 24;
    }


    @Override
    public void renderImage(@NonNull Font textRenderer, int x, int y, int width, int height, @NonNull GuiGraphics context) {
            this.drawNonEmptyTooltip(textRenderer, x, y, context);
    }

    private void drawNonEmptyTooltip(Font textRenderer, int x, int y, GuiGraphics context) {
        List<ItemStack> list = this.firstStacksInContents();
        numberOfSlots = list.size();
        if (!list.isEmpty()) {
            int k = 0;
            for (int l = 0; l < this.getRows(); l++) {
                for (int m = 0; m < this.getColumns(); m++) {
                    int n = x + m * 24;
                    int o = y + l * 24;
                    if (k >= numberOfSlots) break;
                    this.drawItem(k, n, o, list, k, textRenderer, context);
                    k++;
                }
            }
        }

    }

    private List<ItemStack> firstStacksInContents() {
        int i = (int) Math.min(this.contents.nonEmptyStream().count(), 27);
        return this.contents.nonEmptyStream().toList().subList(0, i);
    }

    private void drawItem(int index, int x, int y, List<ItemStack> stacks, int seed, Font textRenderer, GuiGraphics drawContext) {
        ItemStack itemStack = stacks.get(index);
        drawContext.blitSprite(RenderPipelines.GUI_TEXTURED, BUNDLE_SLOT_BACKGROUND_TEXTURE, x, y, 24, 24);

        drawContext.renderItem(itemStack, x + 4, y + 4, seed);
        drawContext.renderItemDecorations(textRenderer, itemStack, x + 4, y + 4);
    }

}
