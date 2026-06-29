package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.greenjab.nekomasfixed.registries.*;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.render.block.entity.*;
import net.greenjab.nekomasfixed.screen.KilnScreen;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.greenjab.nekomasfixed.screen.PyrotechnicsTableScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;

public class NekomasFixedClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.registerBlockEntityRenderer();
		ModEntityRendererRegistry.registerEntityRenderer();
		ModEntityLayerRegistry.registerEntityModelLayer();
		TextureRegistry.registerTextureRegistry();

		ClientSyncHandler.init();

		HandledScreens.register(ScreenHandlerRegistry.KILN_SCREEN_HANDLER, KilnScreen::new);
		HandledScreens.register(ScreenHandlerRegistry.PYROTECHNICS_TABLE_HANDLER, PyrotechnicsTableScreen::new);

		BlockRenderLayerMap.putBlocks(
				BlockRenderLayer.TRANSLUCENT,
				BlockRegistry.AMBER_STAINED_GLASS,
				BlockRegistry.AQUA_STAINED_GLASS,
				BlockRegistry.INDIGO_STAINED_GLASS,
				BlockRegistry.MAROON_STAINED_GLASS,
				BlockRegistry.AMBER_STAINED_GLASS_PANE,
				BlockRegistry.AQUA_STAINED_GLASS_PANE,
				BlockRegistry.INDIGO_STAINED_GLASS_PANE,
				BlockRegistry.MAROON_STAINED_GLASS_PANE
		);

		BlockRenderLayerMap.putBlocks(
				BlockRenderLayer.CUTOUT,
				BlockRegistry.BAOBAB_FRUIT,
				BlockRegistry.BAOBAB_LEAVES,
				BlockRegistry.BAOBAB_SAPLING,
				BlockRegistry.HOLLOW_OAK_LOG,
				BlockRegistry.HOLLOW_DARK_OAK_LOG,
				BlockRegistry.HOLLOW_ACACIA_LOG,
				BlockRegistry.HOLLOW_BIRCH_LOG,
				BlockRegistry.HOLLOW_CHERRY_LOG,
				BlockRegistry.HOLLOW_JUNGLE_LOG,
				BlockRegistry.HOLLOW_SPRUCE_LOG,
				BlockRegistry.HOLLOW_PALE_OAK_LOG,
				BlockRegistry.HOLLOW_CRIMSON_STEM,
				BlockRegistry.HOLLOW_WARPED_STEM

		);

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (state != null) {
                assert world != null;
                return SoupCauldronBlockEntityRenderer.getTintIndex(world, pos, tintIndex);
            } else {
                return 0;
            }
        }, BlockRegistry.SOUP_CAULDRON);

	}
}