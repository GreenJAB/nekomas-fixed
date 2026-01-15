package net.greenjab.nekomasfixed;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.greenjab.nekomasfixed.network.UpdateClockPayload;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.minecraft.util.math.BlockPos;

public class ClientSyncHandler {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(UpdateClockPayload.PACKET_ID, ClientSyncHandler::updateClockTimer);

    }
    private static void updateClockTimer(UpdateClockPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (context.client().world.getBlockEntity(new BlockPos(payload.x(), payload.y(), payload.z())) instanceof ClockBlockEntity clockBlockEntity){
                clockBlockEntity.setTimer(payload.timer());
                clockBlockEntity.setBell(payload.hasBell());
                clockBlockEntity.setShowsTime(payload.showsTime());
            }
        });
    }

}
