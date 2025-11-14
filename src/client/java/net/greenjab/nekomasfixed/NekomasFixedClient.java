package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.greenjab.nekomasfixed.registries.BlockEntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registries.EntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.TextureRegistry;


public class NekomasFixedClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.registerBlockEntityRenderer();
		EntityRendererRegistry.registerEntityRenderer();
		EntityModelLayerRegistry.registerEntityModelLayer();
		TextureRegistry.registerTextureRegistry();
	}
}