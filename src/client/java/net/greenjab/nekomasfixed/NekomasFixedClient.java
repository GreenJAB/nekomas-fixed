package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.greenjab.nekomasfixed.registries.*;
import net.greenjab.nekomasfixed.registry.block.cauldron.SoupCauldronBlock;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.screen.KilnScreen;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.greenjab.nekomasfixed.screen.PyrotechnicsTableScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class NekomasFixedClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.registerBlockEntityRenderer();
		ModEntityRendererRegistry.registerEntityRenderer();
		ModEntityLayerRegistry.registerEntityModelLayer();
		TextureRegistry.registerTextureRegistry();

		ClientSyncHandler.init();

		MenuScreens.register(ScreenHandlerRegistry.KILN_SCREEN_HANDLER, KilnScreen::new);
		MenuScreens.register(ScreenHandlerRegistry.PYROTECHNICS_TABLE_HANDLER, PyrotechnicsTableScreen::new);

		BlockRenderLayerMap.putBlocks(
				ChunkSectionLayer.TRANSLUCENT,
				BlockRegistry.AMBER_STAINED_GLASS,
				BlockRegistry.AQUA_STAINED_GLASS,
				BlockRegistry.INDIGO_STAINED_GLASS,
				BlockRegistry.MAROON_STAINED_GLASS,
				BlockRegistry.AMBER_STAINED_GLASS_PANE,
				BlockRegistry.AQUA_STAINED_GLASS_PANE,
				BlockRegistry.INDIGO_STAINED_GLASS_PANE,
				BlockRegistry.MAROON_STAINED_GLASS_PANE
		);

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            assert world != null;
            return SoupCauldronBlock.getTintIndex(world, pos, tintIndex);
        }, BlockRegistry.SOUP_CAULDRON);

	}
}