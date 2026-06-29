package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.TermiteEntity;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.effect.StatusEffectInstance;

public class CustomTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<TermiteEntity.State> TERMITE_STATE = TrackedDataHandler.create(TermiteEntity.State.PACKET_CODEC);
    public static void init() {
        FabricTrackedDataRegistry.register(NekomasFixed.id("termite_state"), TERMITE_STATE);
    }
}
