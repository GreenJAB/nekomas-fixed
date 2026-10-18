
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PyrotechnicsTableScreen extends AbstractContainerScreen<PyrotechnicsMenu> {

    private static final ResourceLocation TEXTURE = NekomasFixed.id("textures/gui/container/pyrotechnics.png");

    private static final ResourceLocation BUTTON_SELECTED_TEXTURE = NekomasFixed.id("container/pyrotechnics/button_selected");
    private static final ResourceLocation BUTTON_HIGHLIGHTED_TEXTURE = NekomasFixed.id("container/pyrotechnics/button_highlighted");
    private static final ResourceLocation BUTTON_TEXTURE = NekomasFixed.id("container/pyrotechnics/button");

    private static final ResourceLocation DYE_ICON = NekomasFixed.id("container/pyrotechnics/dye");
    private static final ResourceLocation PAPER_ICON = NekomasFixed.id("container/pyrotechnics/paper");
    private static final ResourceLocation GUNPOWDER_ICON = NekomasFixed.id("container/pyrotechnics/gunpowder");
    private static final ResourceLocation FIREWORK_STAR_ICON = NekomasFixed.id("container/pyrotechnics/firework_star");

    private static final ResourceLocation EMPTY_ICON = NekomasFixed.id("container/pyrotechnics/empty");
    private static final ResourceLocation FIRE_CHARGE_ICON = NekomasFixed.id("container/pyrotechnics/fire_charge");
    private static final ResourceLocation GOLD_NUGGET_ICON = NekomasFixed.id("container/pyrotechnics/gold_nugget");
    private static final ResourceLocation CREEPER_PATTERN_ICON = NekomasFixed.id("container/pyrotechnics/creeper_pattern");
    private static final ResourceLocation FEATHER_ICON = NekomasFixed.id("container/pyrotechnics/feather");
    private static final ResourceLocation GLOWSTONE_ICON = NekomasFixed.id("container/pyrotechnics/glowstone");
    private static final ResourceLocation DIAMOND = NekomasFixed.id("container/pyrotechnics/diamond");

    private final SlotCycler dyeOrStarSlot;
    private static final List<ResourceLocation> DYE_OR_STAR_TEXTURES = List.of(
            DYE_ICON, FIREWORK_STAR_ICON);

    private final SlotCycler shapeSlot;
    private static final List<ResourceLocation> SHAPE_TEXTURES = List.of(
            EMPTY_ICON, FIRE_CHARGE_ICON, GOLD_NUGGET_ICON, CREEPER_PATTERN_ICON, FEATHER_ICON);

    private final SlotCycler twinkleSlot;
    private static final List<ResourceLocation> TWINKLE_TEXTURES = List.of(
            EMPTY_ICON, GLOWSTONE_ICON);

    private final SlotCycler trailSlot;
    private static final List<ResourceLocation> TRAIL_TEXTURES = List.of(
            EMPTY_ICON, DIAMOND);

    private static final ResourceLocation CHEST_SLOTS_TEXTURE = ResourceLocation.withDefaultNamespace("container/horse/chest_slots");

    private static final List<Pair<String, Item>> ANIMATIONS = List.of(
        new Pair<>("none", Items.AIR),
        new Pair<>("large_ball", Items.FIRE_CHARGE),
        new Pair<>("star", Items.GOLD_NUGGET),
        new Pair<>("creeper", Items.CREEPER_BANNER_PATTERN),
        new Pair<>("burst", Items.FEATHER),
        new Pair<>("twinkle", Items.GLOWSTONE_DUST),
        new Pair<>("trail", Items.DIAMOND)
    );

    private final int totalPatterns = 7;

    private int hoveredPattern = -1;

    private static class SlotCycler {
        final int slotIndex;
        final List<ResourceLocation> icons;
        int tick;
        int iconIndex;

        SlotCycler(int slotIndex, List<ResourceLocation> icons) {
            this.slotIndex = slotIndex;
            this.icons = icons;
        }

        void tick() {
            if (!this.icons.isEmpty() && ++this.tick % 30 == 0) {
                this.iconIndex = (this.iconIndex + 1) % this.icons.size();
            }
        }
    }

    public PyrotechnicsTableScreen(PyrotechnicsMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 186;
        this.dyeOrStarSlot = new SlotCycler(0, DYE_OR_STAR_TEXTURES);
        this.shapeSlot = new SlotCycler(10, SHAPE_TEXTURES);
        this.twinkleSlot = new SlotCycler(11, TWINKLE_TEXTURES);
        this.trailSlot = new SlotCycler(12, TRAIL_TEXTURES);
        // The superclass ctor computes inventoryLabelY from the default imageHeight (166),
        // so recompute it for this screen's taller 186px texture (vanilla's 5-arg ctor does
        // this before the label offsets are set; 1.21.1 has no such ctor).
        this.inventoryLabelY = this.imageHeight - 94;
    }

    private void cycleIcon(GuiGraphics context, SlotCycler cycler, float deltaTicks) {
        Slot slot = this.menu.getSlot(cycler.slotIndex);
        List<ResourceLocation> icons = cycler.icons;
        if (icons.isEmpty() || slot.hasItem()) {
            return;
        }
        boolean transition = icons.size() > 1 && cycler.tick >= 30;
        float alpha = transition ? Math.min(cycler.tick % 30 + deltaTicks, 4.0F) / 4.0F : 1.0F;
        if (alpha < 1.0F) {
            int previous = Math.floorMod(cycler.iconIndex - 1, icons.size());
            this.blitIcon(context, icons.get(previous), slot, 1.0F - alpha);
        }
        this.blitIcon(context, icons.get(cycler.iconIndex), slot, alpha);
    }

    private void blitIcon(GuiGraphics context, ResourceLocation icon, Slot slot, float alpha) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getGuiSprites().getSprite(icon);
        context.blit(this.leftPos + slot.x, this.topPos + slot.y, 0, 16, 16, sprite, 1.0F, 1.0F, 1.0F, alpha);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.dyeOrStarSlot.tick();
        this.shapeSlot.tick();
        this.twinkleSlot.tick();
        this.trailSlot.tick();
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        this.renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {
        context.blit(TEXTURE, leftPos, topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        context.blitSprite(NekomasFixed.id("container/pyrotechnics/" + ANIMATIONS.get(this.menu.getSelectedPattern()).getFirst()), leftPos + 98, topPos + 15, 71, 71);
        for (Slot slot : this.menu.slots) {
            if (slot.isActive() && slot.getContainerSlot() > 0 && slot.container instanceof SimpleContainer) {
                context.blitSprite(CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, leftPos + slot.x - 1, topPos + slot.y - 1, 18, 18);
                if (!slot.hasItem()) {
                    if (slot.mayPlace(Items.WHITE_DYE.getDefaultInstance()))
                        context.blitSprite(DYE_ICON, leftPos + slot.x, topPos + slot.y, 16, 16);
                    else if (slot.mayPlace(Items.FIREWORK_STAR.getDefaultInstance()))
                        context.blitSprite(FIREWORK_STAR_ICON, 16, 16, 0, 0, leftPos + slot.x, topPos + slot.y, 16, 16);
                    else if (slot.mayPlace(Items.GUNPOWDER.getDefaultInstance()))
                        context.blitSprite(GUNPOWDER_ICON, 16, 16, 0, 0, leftPos + slot.x, topPos + slot.y, 16, 16);
                    else if (slot.mayPlace(Items.PAPER.getDefaultInstance()))
                        context.blitSprite(PAPER_ICON, 16, 16, 0, 0, leftPos + slot.x, topPos + slot.y, 16, 16);
                }
            }
        }
        this.cycleIcon(context, this.dyeOrStarSlot, deltaTicks);
        if (this.menu.slots.getFirst().hasItem() && PyrotechnicsMenu.isDye(this.menu.slots.getFirst().getItem().getItem())) {
            this.cycleIcon(context, this.shapeSlot, deltaTicks);
            this.cycleIcon(context, this.twinkleSlot, deltaTicks);
            this.cycleIcon(context, this.trailSlot, deltaTicks);
        }
        if (this.menu.slots.get(14).isActive()) context.blitSprite(CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, leftPos + 151, topPos + 72, 18, 18);

        this.hoveredPattern = -1;
        int sx = leftPos + 7-14;
        int sy = topPos + 53+19;

        for (int index = 0; index < totalPatterns; ++index) {
            int bx = sx + index * 14;
            int by = sy;
            if (index==0) {bx = leftPos +7;by=topPos +53;}
            if (index>4) bx+=6;
            boolean bl = mouseX >= bx && mouseY >= by && mouseX < bx + 14 && mouseY < by + 18;
            ResourceLocation identifier2;
            if (index == this.menu.getSelectedPattern()) identifier2 = BUTTON_SELECTED_TEXTURE;
            else if (bl) {
                identifier2 = BUTTON_HIGHLIGHTED_TEXTURE;
                this.hoveredPattern = index;
            } else identifier2 = BUTTON_TEXTURE;

            context.blitSprite(identifier2, bx, by, 14, 18);
            context.renderItem(ANIMATIONS.get(index).getSecond().getDefaultInstance(), bx-1, by +1);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics context, int mouseX, int mouseY) {
        super.renderTooltip(context, mouseX, mouseY);
        if (this.hoveredPattern >= 0) {
            context.renderTooltip(this.font, Component.translatable("container.nekomasfixed.pyrotechnics." + ANIMATIONS.get(this.hoveredPattern).getFirst()), mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int sx = leftPos + 7-14;
        int sy = topPos + 53+19;
        for (int index = 0; index < totalPatterns; ++index) {
            double dx = mouseX - (double) (sx + index * 14);
            double dy = mouseY - (double) (sy);
            if (index==0) { dx = mouseX - (leftPos +7);dy = mouseY - (topPos +53);}
            if (index>4) dx-=6;
            if (dx >= 0.0 && dy >= 0.0 && dx < 14.0 && dy < 18.0) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, index);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}