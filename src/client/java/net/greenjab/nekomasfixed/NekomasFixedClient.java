package net.greenjab.nekomasfixed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.greenjab.nekomasfixed.mixin.client.ItemPropertiesAccessor;
import net.greenjab.nekomasfixed.registries.ModBlockEntityRendererRegistry;
import net.greenjab.nekomasfixed.registries.ModEntityRendererRegistry;
import net.greenjab.nekomasfixed.render.entity.WildfireShieldModelRenderer;
import net.greenjab.nekomasfixed.render.entity.WildfireTridentModelRenderer;
import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ClamStateProperty;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.registry.registries.ScreenHandlerRegistry;
import net.greenjab.nekomasfixed.screen.KilnScreen;
import net.greenjab.nekomasfixed.screen.PyrotechnicsTableScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class NekomasFixedClient implements ClientModInitializer {
    public static final ResourceLocation WILDFIRE_TRIDENT_IN_HAND = NekomasFixed.id("item/wildfire_trident_in_hand");

    @Override
    public void onInitializeClient() {
        ClientSyncHandler.init();
        MenuScreens.register(ScreenHandlerRegistry.KILN, KilnScreen::new);
        MenuScreens.register(ScreenHandlerRegistry.PYROTECHNICS, PyrotechnicsTableScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BAOBAB_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BAOBAB_FRUIT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GHOST_PEPPER_SHRUB, RenderType.cutout());
        // Ancient stained glass + panes render translucent, like vanilla stained glass (else they
        // render solid because they're not in ItemBlockRenderTypes' per-block map).
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.AMBER_STAINED_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.AQUA_STAINED_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.INDIGO_STAINED_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MAROON_STAINED_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.AMBER_STAINED_GLASS_PANE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.AQUA_STAINED_GLASS_PANE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.INDIGO_STAINED_GLASS_PANE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MAROON_STAINED_GLASS_PANE, RenderType.translucent());
        // Corrupted beacon has a stained-glass shell in its model; without this it renders
        // solid (opaque purple in hand) like the ancient glass did.
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CORRUPTED_BEACON, RenderType.translucent());
        ModEntityRendererRegistry.registerEntityRenderer();
        ModBlockEntityRendererRegistry.registerBlockEntityRenderers();

        ClamStateProperty clamState = new ClamStateProperty();
        ItemPropertiesAccessor.invokeRegister(ItemRegistry.CLAM, net.greenjab.nekomasfixed.NekomasFixed.id("clam_state"), clamState);
        ItemPropertiesAccessor.invokeRegister(ItemRegistry.CLAM_BLUE, net.greenjab.nekomasfixed.NekomasFixed.id("clam_state"), clamState);
        ItemPropertiesAccessor.invokeRegister(ItemRegistry.CLAM_PINK, net.greenjab.nekomasfixed.NekomasFixed.id("clam_state"), clamState);
        ItemPropertiesAccessor.invokeRegister(ItemRegistry.CLAM_PURPLE, net.greenjab.nekomasfixed.NekomasFixed.id("clam_state"), clamState);

        ItemPropertiesAccessor.invokeRegister(ItemRegistry.WILDFIRE_SHIELD, ResourceLocation.withDefaultNamespace("blocking"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        ItemPropertiesAccessor.invokeRegister(ItemRegistry.WILDFIRE_TRIDENT, ResourceLocation.withDefaultNamespace("throwing"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        BuiltinItemRendererRegistry.INSTANCE.register(ItemRegistry.WILDFIRE_SHIELD, new WildfireShieldModelRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ItemRegistry.WILDFIRE_TRIDENT, new WildfireTridentModelRenderer());
        ModelLoadingPlugin.register(context -> context.addModels(WILDFIRE_TRIDENT_IN_HAND));
    }
}
