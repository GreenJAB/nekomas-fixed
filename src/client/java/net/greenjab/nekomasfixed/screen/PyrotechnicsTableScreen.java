package net.greenjab.nekomasfixed.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Tuple;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PyrotechnicsTableScreen extends AbstractContainerScreen<PyrotechnicsTableScreenHandler> {

    private static final Identifier TEXTURE = NekomasFixed.id("textures/gui/container/pyrotechnics_table.png");

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

    private final CyclingSlotBackground dyeOrStarSlotIcon = new CyclingSlotBackground(0);
    private static final List<Identifier> DYE_OR_STAR_TEXTURES = List.of(
            DYE_ICON, FIREWORK_STAR_ICON);

    private final CyclingSlotBackground shapeSlotIcon = new CyclingSlotBackground(10);
    private static final List<Identifier> SHAPE_TEXTURES = List.of(
            EMPTY_ICON, FIRE_CHARGE_ICON, GOLD_NUGGET_ICON, CREEPER_PATTERN_ICON, FEATHER_ICON);

    private final CyclingSlotBackground twinkleSlotIcon = new CyclingSlotBackground(11);
    private static final List<Identifier> TWINKLE_TEXTURES = List.of(
            EMPTY_ICON, GLOWSTONE_ICON);

    private final CyclingSlotBackground trailSlotIcon = new CyclingSlotBackground(12);
    private static final List<Identifier> TRAIL_TEXTURES = List.of(
            EMPTY_ICON, DIAMOND);

    private static final Identifier CHEST_SLOTS_TEXTURE = Identifier.withDefaultNamespace("container/horse/chest_slots");


    private static final List<Tuple<String, Item>> ANIMATIONS = List.of(
        new Tuple<>("none", Items.AIR),
        new Tuple<>("large_ball", Items.FIRE_CHARGE),
        new Tuple<>("star", Items.GOLD_NUGGET),
        new Tuple<>("creeper", Items.CREEPER_BANNER_PATTERN),
        new Tuple<>("burst", Items.FEATHER),
        new Tuple<>("twinkle", Items.GLOWSTONE_DUST),
        new Tuple<>("trail", Items.DIAMOND)
    );

    private final int totalPatterns = 7;

    public PyrotechnicsTableScreen(PyrotechnicsTableScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, 176, 186);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.dyeOrStarSlotIcon.tick(DYE_OR_STAR_TEXTURES);
        this.shapeSlotIcon.tick(SHAPE_TEXTURES);
        this.twinkleSlotIcon.tick(TWINKLE_TEXTURES);
        this.trailSlotIcon.tick(TRAIL_TEXTURES);
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
        this.extractContents(context, mouseX, mouseY, deltaTicks);
        this.extractCarriedItem(context, mouseX, mouseY);
        this.extractSnapbackItem(context);
        this.extractTooltip(context, mouseX, mouseY);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        context.blitSprite(RenderPipelines.GUI_TEXTURED, NekomasFixed.id("container/pyrotechnics_table/"+ANIMATIONS.get(this.menu.getSelectedPattern()).getA()), leftPos+98, topPos+15, 71, 71);
        for (Slot slot : this.menu.slots){
            if (slot.isActive() && slot.getContainerSlot()>0 && slot.container instanceof SimpleContainer) {
                context.blitSprite(RenderPipelines.GUI_TEXTURED, CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, leftPos+slot.x-1, topPos+slot.y-1, 18, 18);
                if (!slot.hasItem()){
                    if (slot.mayPlace(Items.WHITE_DYE.getDefaultInstance()))
                        context.blitSprite(RenderPipelines.GUI_TEXTURED, DYE_ICON, leftPos + slot.x, topPos + slot.y, 16, 16);
                    else if (slot.mayPlace(Items.FIREWORK_STAR.getDefaultInstance()))
                        context.blitSprite(RenderPipelines.GUI_TEXTURED, FIREWORK_STAR_ICON, 16, 16, 0, 0, leftPos + slot.x, topPos + slot.y, 16, 16);
                    else if (slot.mayPlace(Items.GUNPOWDER.getDefaultInstance()))
                        context.blitSprite(RenderPipelines.GUI_TEXTURED, GUNPOWDER_ICON, 16, 16, 0, 0, leftPos + slot.x, topPos + slot.y, 16, 16);
                    else if (slot.mayPlace(Items.PAPER.getDefaultInstance()))
                        context.blitSprite(RenderPipelines.GUI_TEXTURED, PAPER_ICON, 16, 16, 0, 0, leftPos + slot.x, topPos + slot.y, 16, 16);
                }
            }
        }
        this.dyeOrStarSlotIcon.extractRenderState(this.menu, context, deltaTicks, this.leftPos, this.topPos);
        if (this.menu.slots.get(0).hasItem() && this.menu.slots.get(0).getItem().getItem() instanceof DyeItem){
            this.shapeSlotIcon.extractRenderState(this.menu, context, deltaTicks, this.leftPos, this.topPos);
            this.twinkleSlotIcon.extractRenderState(this.menu, context, deltaTicks, this.leftPos, this.topPos);
            this.trailSlotIcon.extractRenderState(this.menu, context, deltaTicks, this.leftPos, this.topPos);
        }
        if (this.menu.slots.get(14).isActive()) context.blitSprite(RenderPipelines.GUI_TEXTURED, CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, leftPos+151, topPos+72, 18, 18);

        int sx = leftPos + 7-14;
        int sy = topPos + 53+19;

        for (int index = 0; index < totalPatterns; ++index) {
            int bx = sx + index * 14;
            int by = sy;
            if (index==0) {bx = leftPos +7;by=topPos +53;}
            if (index>4) bx+=6;
            boolean bl = mouseX >= bx && mouseY >= by && mouseX < bx + 14 && mouseY < by + 18;
            Identifier identifier2;
            if (index == this.menu.getSelectedPattern()) identifier2 = BUTTON_SELECTED_TEXTURE;
            else if (bl) {
                identifier2 = BUTTON_HIGHLIGHTED_TEXTURE;
                context.setTooltipForNextFrame(Component.translatable("container.nekomasfixed.pyrotechnics."+ANIMATIONS.get(index).getA()), mouseX, mouseY);
                context.requestCursor(CursorTypes.POINTING_HAND);
            } else identifier2 = BUTTON_TEXTURE;

            context.blitSprite(RenderPipelines.GUI_TEXTURED, identifier2, bx, by, 14, 18);
            context.item(ANIMATIONS.get(index).getB().getDefaultInstance(), bx-1, by +1);
        }
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent click, boolean doubled) {
        int sx = leftPos + 7-14;
        int sy = topPos + 53+19;
        for (int index = 0; index < totalPatterns; ++index) {
            double dx = click.x() - (double) (sx + index * 14);
            double dy = click.y() - (double) (sy);
            if (index==0) { dx = click.x() - (leftPos +7);dy = click.y() - (topPos +53);}
            if (index>4) dx-=6;
            if (dx >= 0.0 && dy >= 0.0 && dx < 14.0 && dy < 18.0) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, index);
                return true;
            }
        }
        return super.mouseClicked(click, doubled);
    }
}