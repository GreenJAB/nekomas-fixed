package net.greenjab.nekomasfixed;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.greenjab.nekomasfixed.network.UpdateClockPayload;
import net.greenjab.nekomasfixed.registry.block.entity.ClockBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientSyncHandler {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(UpdateClockPayload.PACKET_ID, ClientSyncHandler::updateClock);
    }

    private static void updateClock(UpdateClockPayload payload, ClientPlayNetworking.Context context) {
        Minecraft client = context.client();
        if (client.level != null
                && client.level.getBlockEntity(new BlockPos(payload.x(), payload.y(), payload.z())) instanceof ClockBlockEntity clockBlockEntity) {
            clockBlockEntity.setTimer(payload.timer());
            clockBlockEntity.setBell(payload.hasBell());
            clockBlockEntity.setShowsTime(payload.showsTime());
        }
    }
}