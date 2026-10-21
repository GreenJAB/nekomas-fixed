package net.greenjab.nekomasfixed.render.tab.entries;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Optional;

public class ImageEntry extends AbstractConfigListEntry<Object> {
    private final Identifier texture;
    private final int imgW, imgH, texW, texH;

    public ImageEntry(Identifier texture, int imgW, int imgH, int texW, int texH) {
        super(Component.empty(), false);
        this.texture = texture;
        this.imgW = imgW; this.imgH = imgH;
        this.texW = texW; this.texH = texH;
    }

    @Override public Object getValue() { return null; }
    @Override public Optional<Object> getDefaultValue() { return Optional.empty(); }
    @Override public void save() {}
    @Override public int getItemHeight() { return imgH + 8; }
    @Override public List<? extends GuiEventListener> children() { return List.of(); }
    @Override public List<? extends NarratableEntry> narratables() { return List.of(); }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int index, int y, int x,
                                   int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        int drawX = x + (entryWidth - imgW) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, drawX, y + 4, 0.0F, 0.0F, imgW, imgH, texW, texH);
    }
}
