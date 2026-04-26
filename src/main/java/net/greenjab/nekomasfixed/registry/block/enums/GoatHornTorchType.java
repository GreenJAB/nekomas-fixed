package net.greenjab.nekomasfixed.registry.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum GoatHornTorchType implements StringIdentifiable {
    TORCH,
    COPPER_TORCH,
    GLOW_TORCH,
    GLOW_TORCH_OFF,
    SOULFIRE_TORCH,
    REDSTONE_TORCH,
    NONE;

    @Override
    public String asString() {
        return this.name().toLowerCase();
    }
}
