package net.greenjab.nekomasfixed.screen;

import net.greenjab.nekomasfixed.screen.PyrotechnicsTableScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class PyrotechnicsTableScreen extends HandledScreen<PyrotechnicsTableScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of("nekomasfixed", "textures/gui/container/pyrotechnics_table.png");
    private static final Identifier SCROLLER_TEXTURE = Identifier.ofVanilla("container/loom/scroller");
    private static final Identifier PATTERN_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("container/loom/pattern_highlighted");

    private static final Identifier DYE_ICON = Identifier.ofVanilla("container/slot/dye");
    private static final Identifier DYE2_ICON = Identifier.of("nekomasfixed", "container/pyrotechnics/dye2");
    private static final Identifier PAPER_ICON = Identifier.ofVanilla("container/slot/banner_pattern");
    private static final Identifier GUNPOWDER_ICON = Identifier.ofVanilla("container/slot/redstone_dust");

    private float scrollPosition;
    private boolean scrollbarClicked;
    private int visibleTopRow;
    private final int totalPatterns = 16;

    public PyrotechnicsTableScreen(PyrotechnicsTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 186;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.renderMain(context, mouseX, mouseY, deltaTicks);
        this.renderCursorStack(context, mouseX, mouseY);
        this.renderLetGoTouchStack(context);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int i = this.x;
        int j = this.y;

        // 1. Main Background
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);

        // 2. Ghost Icons (Aligned to Slots 0, 1, 2)
        // These match the slot positions (8, 16), (8, 35), (8, 54) from your Handler
        renderGhostIcon(context, 0, DYE_ICON, i + 8, j + 16);
        renderGhostIcon(context, 1, DYE2_ICON, i + 8,j + 35);
        renderGhostIcon(context, 2, PAPER_ICON, i + 8, j + 54);
        renderGhostIcon(context, 3, GUNPOWDER_ICON, i + 8, j + 73);

        // 3. Scrollbar
        int scrollHeight = (int)(41.0F * this.scrollPosition);
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SCROLLER_TEXTURE, i + 88, j + 17 + scrollHeight, 12, 15);

        // 4. Pattern Buttons (Logic exists, Render is just highlights for now)
        int gridStartX = i + 30;
        int gridStartY = j + 17;

        for (int row = 0; row < 4; ++row) {
            for (int col = 0; col < 4; ++col) {
                int index = (row + this.visibleTopRow) * 4 + col;
                if (index >= totalPatterns) break;

                int bx = gridStartX + col * 14;
                int by = gridStartY + row * 14;

                // Show highlight on hover so you know the buttons work
                if (mouseX >= bx && mouseY >= by && mouseX < bx + 14 && mouseY < by + 14) {
                    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, PATTERN_HIGHLIGHTED_TEXTURE, bx, by, 14, 14);
                }

                // Note: We are NOT drawing the patterns (sprite) here yet as requested.
            }
        }
    }

    private void renderGhostIcon(DrawContext context, int slotId, Identifier icon, int x, int y) {
        Slot slot = (Slot)this.handler.slots.get(slotId);
        if (!slot.hasStack()) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, icon, x, y, 16, 16);
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        this.scrollbarClicked = false;

        // Pattern Selection logic remains active
        int gridX = this.x + 30;
        int gridY = this.y + 17;
        for (int row = 0; row < 4; ++row) {
            for (int col = 0; col < 4; ++col) {
                double dx = click.x() - (double)(gridX + col * 14);
                double dy = click.y() - (double)(gridY + row * 14);
                int index = (row + this.visibleTopRow) * 4 + col;

                if (dx >= 0.0 && dy >= 0.0 && dx < 14.0 && dy < 14.0 && index < totalPatterns) {
                    this.client.interactionManager.clickButton(this.handler.syncId, index);
                    return true;
                }
            }
        }

        // Scrollbar Click
        if (click.x() >= (double)(this.x + 88) && click.x() < (double)(this.x + 100) &&
                click.y() >= (double)(this.y + 17) && click.y() < (double)(this.y + 73)) {
            this.scrollbarClicked = true;
            return true;
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        int hiddenRows = MathHelper.ceilDiv(totalPatterns, 4) - 4;
        if (this.scrollbarClicked && hiddenRows > 0) {
            int topY = this.y + 17;
            int bottomY = topY + 56;
            this.scrollPosition = ((float)click.y() - (float)topY - 7.5F) / ((float)(bottomY - topY) - 15.0F);
            this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0F, 1.0F);
            this.visibleTopRow = Math.max((int)((double)(this.scrollPosition * (float)hiddenRows) + 0.5D), 0);
            return true;
        }
        return super.mouseDragged(click, offsetX, offsetY);
    }
}
