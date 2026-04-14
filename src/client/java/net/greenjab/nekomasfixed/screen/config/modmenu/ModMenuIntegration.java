package net.greenjab.nekomasfixed.screen.config.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.greenjab.nekomasfixed.screen.config.ConfigTrial;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigTrial::createConfigScreen;
    }
}