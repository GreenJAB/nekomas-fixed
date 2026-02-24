package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.greenjab.nekomasfixed.registries.BlockEntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registries.EntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.TextureRegistry;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.util.Identifier;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;

public class NekomasFixedClient implements ClientModInitializer {
	public static EquipmentModel turtleArmorModel = createHumanoidOnlyModel("turtle_scute");

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.registerBlockEntityRenderer();
		EntityRendererRegistry.registerEntityRenderer();
		EntityModelLayerRegistry.registerEntityModelLayer();
		TextureRegistry.registerTextureRegistry();


		ClientSyncHandler.init();
	}

	private static EquipmentModel createHumanoidOnlyModel(String id) {
		return EquipmentModel.builder()
				.addHumanoidLayers(Identifier.ofVanilla(id))
				.build();
	}

}