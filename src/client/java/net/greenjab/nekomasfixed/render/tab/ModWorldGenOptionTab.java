package net.greenjab.nekomasfixed.render.tab;

import net.greenjab.nekomasfixed.render.tab.screens.NetherConfigScreen;
import net.greenjab.nekomasfixed.render.tab.screens.OtherConfigScreen;
import net.greenjab.nekomasfixed.render.tab.screens.WorldGenConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.tabs.GridLayoutTab;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;

public class ModWorldGenOptionTab extends GridLayoutTab {

    public ModWorldGenOptionTab(Screen parent) {
        super(TabLiterals.WORLD_MENU_TITLE);
        GridLayout.RowHelper helper = this.layout.rowSpacing(8).createRowHelper(1);
        helper.addChild(Button.builder(TabLiterals.GO_TO_MENU,
                        b -> Minecraft.getInstance().gui.setScreen(WorldGenConfigScreen.create(parent)))
                .width(210).build());

        helper.addChild(Button.builder(TabLiterals.GO_TO_MENU,
                b -> Minecraft.getInstance().gui.setScreen(NetherConfigScreen.create(parent)))
                .width(210).build()
        );

        helper.addChild(Button.builder(TabLiterals.GO_TO_MENU,
                        b -> Minecraft.getInstance().gui.setScreen(OtherConfigScreen.create(parent)))
                .width(210).build()
        );

    }
}
