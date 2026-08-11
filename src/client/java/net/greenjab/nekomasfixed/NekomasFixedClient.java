package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.greenjab.nekomasfixed.registries.*;
import net.greenjab.nekomasfixed.registry.block.cauldron.SoupCauldronBlock;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.screen.KilnScreen;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.greenjab.nekomasfixed.screen.PyrotechnicsTableScreen;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class NekomasFixedClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.registerBlockEntityRenderer();
		ModEntityRendererRegistry.registerEntityRenderer();
		ModModelLayerRegistry.registerEntityModelLayer();
		TextureRegistry.registerTextureRegistry();

		ClientSyncHandler.init();

		MenuScreens.register(ScreenHandlerRegistry.KILN, KilnScreen::new);
		MenuScreens.register(ScreenHandlerRegistry.PYROTECHNICS, PyrotechnicsTableScreen::new);

		BlockColorRegistry.register(List.of(soup()), BlockRegistry.SOUP_CAULDRON);
	}

	public static BlockTintSource soup() {
		return new BlockTintSource() {
			@Override
			public int color(final @NonNull BlockState state) {
				return -1;
			}

			@Override
			public int colorInWorld(final @NonNull BlockState state, final @NonNull BlockAndTintGetter level, final @NonNull BlockPos pos) {
				if (level.getBlockEntity(pos) instanceof SoupCauldronBlockEntity soupCauldronBlockEntity){
					float f = soupCauldronBlockEntity.getOpenNess(0);
					int s = SoupCauldronBlock.blendFoodColors(soupCauldronBlockEntity.getInputs());
					int w = BiomeColors.getAverageWaterColor(level, pos);
					return ((int)(f*(s >> 16 & 255)+(1-f)*(w >> 16 & 255)) << 16)
							| ((int)(f*(s >> 8 & 255)+(1-f)*(w >> 8 & 255)) << 8)
							| (int)(f*(s & 255)+(1-f)*(w & 255))-(int)Math.pow(2,24);
				} else return BiomeColors.getAverageWaterColor(level, pos);
			}
		};
	}
}