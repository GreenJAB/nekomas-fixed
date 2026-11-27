package net.greenjab.nekomasfixed.render.entity;

import net.greenjab.nekomasfixed.registry.entity.FakeBoat;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

public class FakeBoatRenderer extends EntityRenderer<FakeBoat> {

    public FakeBoatRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NonNull ResourceLocation getTextureLocation(@NonNull FakeBoat entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
