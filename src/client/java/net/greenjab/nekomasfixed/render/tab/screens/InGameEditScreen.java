package net.greenjab.nekomasfixed.render.tab.screens;

import net.greenjab.nekomasfixed.screen.config.TabLiterals;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;



public class InGameEditScreen extends Screen {
    private final Screen parent;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public InGameEditScreen(Screen parent) {
        super(TabLiterals.MAIN_TITLE);
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.layout.addToHeader(new StringWidget(this.title, this.font));

        GridLayout grid = this.layout.addToContents(new GridLayout());
        grid.rowSpacing(8);
        GridLayout.RowHelper helper = grid.createRowHelper(1);

        helper.addChild(Button.builder(TabLiterals.NETHER_TITLE,
                        b -> this.minecraft.gui.setScreen(NetherConfigScreen.create(this)))
                .width(200).build());

        helper.addChild(Button.builder(TabLiterals.ENTITY_TITLE,
                        b -> this.minecraft.gui.setScreen(EntityConfigScreen.create(this)))
                .width(200).build());

        helper.addChild(Button.builder(TabLiterals.OTHER_TITLE,
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
