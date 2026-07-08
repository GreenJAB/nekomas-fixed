package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.TermiteEntity;
import net.minecraft.network.syncher.EntityDataSerializer;

public class CustomTrackedDataHandlerRegistry {
    public static final EntityDataSerializer<TermiteEntity.State> TERMITE_STATE = EntityDataSerializer.forValueType(TermiteEntity.State.PACKET_CODEC);
    public static void init() {
        FabricEntityDataRegistry.register(NekomasFixed.id("termite_state"), TERMITE_STATE);
    }
}
