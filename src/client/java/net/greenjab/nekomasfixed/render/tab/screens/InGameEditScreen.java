package net.greenjab.nekomasfixed.render.tab.screens;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;



public class InGameEditScreen extends Screen {
    private final Screen parent;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public InGameEditScreen(Screen parent) {
        super(Component.literal("Nekomas Fixed"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.layout.addToHeader(new StringWidget(this.title, this.font));

        GridLayout grid = this.layout.addToContents(new GridLayout());
        grid.rowSpacing(8);
        GridLayout.RowHelper helper = grid.createRowHelper(1);

        helper.addChild(Button.builder(Component.literal("Nether Features"),
                        b -> this.minecraft.gui.setScreen(NetherConfigScreen.create(this)))
                .width(200).build());
        helper.addChild(Button.builder(Component.literal("Other Features"),
                        b -> this.minecraft.gui.setScreen(OtherConfigScreen.create(this)))
                .width(200).build());

        this.layout.addToFooter(
                Button.builder(CommonComponents.GUI_DONE, b -> this.onClose()).width(200).build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }
}
