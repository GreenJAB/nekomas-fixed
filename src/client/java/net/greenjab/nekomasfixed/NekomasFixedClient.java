package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.*;
import net.greenjab.nekomasfixed.registry.block.cauldron.SoupCauldronBlock;
import net.greenjab.nekomasfixed.registry.other.EchoingLayer;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.screen.KilnScreen;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.greenjab.nekomasfixed.screen.PyrotechnicsTableScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;

public class NekomasFixedClient implements ClientModInitializer {
	public static final int GLOW_STRENGTH = 15728880;

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

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (state != null) {
                assert world != null;
                return SoupCauldronBlock.getTintIndex(world, pos, tintIndex);
            } else {
                return 0;
            }
        }, BlockRegistry.SOUP_CAULDRON);

		EntityRendererRegistry.register(EntityTypeRegistry.CLEAR_ITEM_FRAME, ItemFrameEntityRenderer::new);
		ModelLoadingPlugin.register(context -> context.addModel(
				ExtraModelKey.create(() -> "clear_item_frame"),
				SimpleUnbakedExtraModel.blockStateModel(NekomasFixed.id("block/clear_item_frame"))
		));
		ModelLoadingPlugin.register(context -> context.addModel(
				ExtraModelKey.create(() -> "clear_item_frame_map"),
				SimpleUnbakedExtraModel.blockStateModel(NekomasFixed.id("block/clear_item_frame_map"))
		));

		ItemTooltipCallback.EVENT.register(EchoingLayer::appendTooltip);
	}
}