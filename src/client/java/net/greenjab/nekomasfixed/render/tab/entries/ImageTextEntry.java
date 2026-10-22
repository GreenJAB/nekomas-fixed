package net.greenjab.nekomasfixed.render.tab.entries;


import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;
import java.util.Optional;

public class ImageTextEntry extends AbstractConfigListEntry<Object> {
    private static final int PADDING = 4;
    private static final int GAP = 8;

    private final Identifier texture;
    private final int imgW, imgH, texW, texH;
    private final Component text;

    private List<FormattedCharSequence> lines = List.of();
    private int lastTextWidth = -1;

    public ImageTextEntry(Identifier texture, int imgW, int imgH, int texW, int texH, Component text) {
        super(Component.empty(), false);
        this.texture = texture;
        this.imgW = imgW;
        this.imgH = imgH;
        this.texW = texW;
        this.texH = texH;
        this.text = text;
    }


    public ImageTextEntry(Identifier texture, int width, int height, Component text) {
        this(texture, width, height, width, height, text);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Optional<Object> getDefaultValue() {
        return Optional.empty();
    }

    @Override
    public void save() {
    }

    @Override
    public int getItemHeight() {
        Font font = Minecraft.getInstance().font;
        int textH = lines.size() * font.lineHeight;
        return Math.max(imgH, textH) + PADDING * 2;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return List.of();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int index, int y, int x,
                                   int entryWidth, int entryHeight, int mouseX, int mouseY,
                                   boolean isHovered, float delta) {
        Font font = Minecraft.getInstance().font;

        int textWidth = Math.max(20, entryWidth - imgW - GAP);
        if (textWidth != lastTextWidth) {
            lines = font.split(text, textWidth);
            lastTextWidth = textWidth;
        }

        int imgY = y + PADDING + Math.max(0, (rowContentHeight() - imgH) / 2);
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, imgY, 0.0F, 0.0F, imgW, imgH, texW, texH);

        int textH = lines.size() * font.lineHeight;
        int textX = x + imgW + GAP;
        int textY = y + PADDING + Math.max(0, (rowContentHeight() - textH) / 2);
        for (FormattedCharSequence line : lines) {
            graphics.text(font, line, textX, textY, 0xFFFFFFFF);
            textY += font.lineHeight;
        }
    }

    private int rowContentHeight() {
        return getItemHeight() - PADDING * 2;
    }
}
