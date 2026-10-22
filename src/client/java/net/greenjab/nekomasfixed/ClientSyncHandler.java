package net.greenjab.nekomasfixed;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.greenjab.nekomasfixed.network.UpdateClockPayload;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.greenjab.nekomasfixed.render.other.TintedGlint;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class ClientSyncHandler {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(UpdateClockPayload.PACKET_ID, ClientSyncHandler::updateClockTimer);
        // Adding this here to keep NekomasFixedClient clean.
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(
                Identifier.fromNamespaceAndPath("nekomasfixed", "tinted_glint_clear"),
                (ResourceManagerReloadListener) manager -> TintedGlint.clearCache()
        );
    }

    private static void updateClockTimer(UpdateClockPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (context.client().level.getBlockEntity(new BlockPos(payload.x(), payload.y(), payload.z())) instanceof ClockBlockEntity clockBlockEntity){
                clockBlockEntity.setTimer(payload.timer());
                clockBlockEntity.setBell(payload.hasBell());
                clockBlockEntity.setShowsTime(payload.showsTime());
            }
        });
    }
}
