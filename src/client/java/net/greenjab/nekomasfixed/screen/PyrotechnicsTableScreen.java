package net.greenjab.nekomasfixed.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.cursor.StandardCursors;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ColorHelper;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PyrotechnicsTableScreen extends HandledScreen<PyrotechnicsTableScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of("nekomasfixed", "textures/gui/container/pyrotechnics_table.png");

    private static final Identifier BUTTON_SELECTED_TEXTURE = NekomasFixed.id("container/pyrotechnics_table/button_selected");
    private static final Identifier BUTTON_HIGHLIGHTED_TEXTURE = NekomasFixed.id("container/pyrotechnics_table/button_highlighted");
    private static final Identifier BUTTON_TEXTURE = NekomasFixed.id("container/pyrotechnics_table/button");

    private static final Identifier DYE_ICON = NekomasFixed.id("container/pyrotechnics_table/dye");
    private static final Identifier PAPER_ICON = NekomasFixed.id("container/pyrotechnics_table/paper");
    private static final Identifier GUNPOWDER_ICON = NekomasFixed.id("container/pyrotechnics_table/gunpowder");
    private static final Identifier FIREWORK_STAR_ICON = NekomasFixed.id("container/pyrotechnics_table/firework_star");

    private static final Identifier EMPTY_ICON = NekomasFixed.id("container/pyrotechnics_table/empty");
    private static final Identifier FIRE_CHARGE_ICON = NekomasFixed.id("container/pyrotechnics_table/fire_charge");
    private static final Identifier GOLD_NUGGET_ICON = NekomasFixed.id("container/pyrotechnics_table/gold_nugget");
    private static final Identifier CREEPER_PATTERN_ICON = NekomasFixed.id("container/pyrotechnics_table/creeper_pattern");
    private static final Identifier FEATHER_ICON = NekomasFixed.id("container/pyrotechnics_table/feather");
    private static final Identifier GLOWSTONE_ICON = NekomasFixed.id("container/pyrotechnics_table/glowstone");
    private static final Identifier DIAMOND = NekomasFixed.id("container/pyrotechnics_table/diamond");

    private final CyclingSlotIcon dyeOrStarSlotIcon = new CyclingSlotIcon(0);
    private static final List<Identifier> DYE_OR_STAR_TEXTURES = List.of(
            DYE_ICON, FIREWORK_STAR_ICON);

    private final CyclingSlotIcon shapeSlotIcon = new CyclingSlotIcon(10);
    private static final List<Identifier> SHAPE_TEXTURES = List.of(
            EMPTY_ICON, FIRE_CHARGE_ICON, GOLD_NUGGET_ICON, CREEPER_PATTERN_ICON, FEATHER_ICON);

    private final CyclingSlotIcon twinkleSlotIcon = new CyclingSlotIcon(11);
    private static final List<Identifier> TWINKLE_TEXTURES = List.of(
            EMPTY_ICON, GLOWSTONE_ICON);

    private final CyclingSlotIcon trailSlotIcon = new CyclingSlotIcon(12);
    private static final List<Identifier> TRAIL_TEXTURES = List.of(
            EMPTY_ICON, DIAMOND);

    private static final Identifier CHEST_SLOTS_TEXTURE = Identifier.ofVanilla("container/horse/chest_slots");

    private static final List<Pair<String, Item>> ANIMATIONS = List.of(
        new Pair<>("small_ball", Items.AIR),
        new Pair<>("large_ball", Items.FIRE_CHARGE),
        new Pair<>("star", Items.GOLD_NUGGET),
        new Pair<>("creeper", Items.CREEPER_BANNER_PATTERN),
        new Pair<>("burst", Items.FEATHER),
        new Pair<>("none", Items.AIR),
        new Pair<>("twinkle", Items.GLOWSTONE_DUST),
        new Pair<>("trail", Items.DIAMOND)
    );

    private final int totalPatterns = 8;

    public PyrotechnicsTableScreen(PyrotechnicsTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 186;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.dyeOrStarSlotIcon.updateTexture(DYE_OR_STAR_TEXTURES);
        this.shapeSlotIcon.updateTexture(SHAPE_TEXTURES);
        this.twinkleSlotIcon.updateTexture(TWINKLE_TEXTURES);
        this.trailSlotIcon.updateTexture(TRAIL_TEXTURES);
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
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);

        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, NekomasFixed.id("container/pyrotechnics_table/"+ANIMATIONS.get(this.handler.getSelectedPattern()).getLeft()), x+98, y+15, 71, 71);

        for (Slot slot : handler.slots){
            if (slot.isEnabled() && slot.getIndex()>0 && slot.inventory instanceof SimpleInventory) {
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, x+slot.x-1, y+slot.y-1, 18, 18);
                if (!slot.hasStack()){
                    if (slot.canInsert(Items.WHITE_DYE.getDefaultStack()))
                        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, DYE_ICON, x + slot.x, y + slot.y, 16, 16, ColorHelper.getWhite(1));
                    else if (slot.canInsert(Items.FIREWORK_STAR.getDefaultStack()))
                        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, FIREWORK_STAR_ICON, x + slot.x, y + slot.y, 16, 16, ColorHelper.getWhite(1));
                    else if (slot.canInsert(Items.GUNPOWDER.getDefaultStack()))
                        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, GUNPOWDER_ICON, x + slot.x, y + slot.y, 16, 16, ColorHelper.getWhite(1));
                    else if (slot.canInsert(Items.PAPER.getDefaultStack()))
                        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, PAPER_ICON, x + slot.x, y + slot.y, 16, 16, ColorHelper.getWhite(1));
                }
            }
        }
        this.dyeOrStarSlotIcon.render(this.handler, context, deltaTicks, this.x, this.y);
        if (handler.slots.get(0).hasStack() && handler.slots.get(0).getStack().getItem() instanceof DyeItem){
            this.shapeSlotIcon.render(this.handler, context, deltaTicks, this.x, this.y);
            this.twinkleSlotIcon.render(this.handler, context, deltaTicks, this.x, this.y);
            this.trailSlotIcon.render(this.handler, context, deltaTicks, this.x, this.y);
        }
        if (handler.slots.get(14).isEnabled()) context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, x+151, y+72, 18, 18);

        int gridStartX = x + 8;//27
        int gridStartY = y + 53;

        for (int row = 0; row < 2; ++row) {
            for (int col = 0; col < 5; ++col) {
                int index = row * 5 + col;
                if (index >= totalPatterns) break;

                int bx = gridStartX + col * 14 ;//+ row * 14 * 2;
                int by = gridStartY + row * 19;

                boolean bl = mouseX >= bx && mouseY >= by && mouseX < bx + 14 && mouseY < by + 18;
                Identifier identifier2;
                if (index == this.handler.getSelectedPattern()) {
                    identifier2 = BUTTON_SELECTED_TEXTURE;
                } else if (bl) {
                    identifier2 = BUTTON_HIGHLIGHTED_TEXTURE;
                    context.drawTooltip(Text.translatable("container.nekomasfixed.pyrotechnics_table."+ANIMATIONS.get(index).getLeft()), mouseX, mouseY);
                    context.setCursor(StandardCursors.POINTING_HAND);
                } else {
                    identifier2 = BUTTON_TEXTURE;
                }

                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier2, bx, by, 14, 18);
                context.drawItem(ANIMATIONS.get(index).getRight().getDefaultStack(), bx-1, by+1);
            }
        }
    }


    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        int gridX = this.x + 8;//27
        int gridY = this.y + 53;
        for (int row = 0; row < 2; ++row) {
            for (int col = 0; col < 5; ++col) {
                int index = row * 5 + col;
                if (index >= totalPatterns) break;
                double dx = click.x() - (double)(gridX + col * 14); //+ row * 14*2);
                double dy = click.y() - (double)(gridY + row * 19);
                if (dx >= 0.0 && dy >= 0.0 && dx < 14.0 && dy < 18.0) {
                    this.client.interactionManager.clickButton(this.handler.syncId, index);
                    return true;
                }
            }
        }
        return super.mouseClicked(click, doubled);
    }
}
