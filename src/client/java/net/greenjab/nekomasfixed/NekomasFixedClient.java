package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.greenjab.nekomasfixed.registries.BlockEntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registries.EntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.TextureRegistry;
import net.greenjab.nekomasfixed.util.ModRecipeBookCategories;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.util.Identifier;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;

public class NekomasFixedClient implements ClientModInitializer {
	public static EquipmentModel turtleArmorModel = createHumanoidOnlyModel("turtle_scute");
	public static EquipmentModel netheriteCrownModel = createHumanoidOnlyModel("netherite_crown");
	public static EquipmentModel copperCrownModel = createHumanoidOnlyModel("copper_crown");
	public static EquipmentModel ironCrownModel = createHumanoidOnlyModel("iron_crown");
	public static EquipmentModel goldenCrownModel = createHumanoidOnlyModel("golden_crown");
	public static EquipmentModel diamondCrownModel = createHumanoidOnlyModel("diamond_crown");

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