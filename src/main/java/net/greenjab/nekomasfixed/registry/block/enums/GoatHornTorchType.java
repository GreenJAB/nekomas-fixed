package net.greenjab.nekomasfixed.registry.block.enums;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

public enum GoatHornTorchType implements StringRepresentable {
    NORMAL_TORCH(ParticleTypes.FLAME, 15),
    COPPER_TORCH(ParticleTypes.COPPER_FIRE_FLAME, 13),
    GLOW_TORCH(ParticleTypes.GLOW, 10),
    GLOW_TORCH_OFF(null, 0),
    SOUL_TORCH(ParticleTypes.SOUL_FIRE_FLAME, 10),
    REDSTONE_TORCH(DustParticleOptions.REDSTONE, 15),
    NONE(null, 0);

    private final int light;
    private final ParticleOptions particle;

    GoatHornTorchType(ParticleOptions particle, int light) {
        this.light = light;
        this.particle = particle;
    }

    public int getLight() {
        return this.light;
    }

    public ParticleOptions getParticle() {
        return this.particle;
    }

    @Override
    public @NonNull String getSerializedName() {
        return this.name().toLowerCase();
    }

    public static GoatHornTorchType fromItem(Item item, boolean waterLogged) {
        if (item == Items.TORCH) return NORMAL_TORCH;
        if (item == Items.COPPER_TORCH) return COPPER_TORCH;
        if (item == Items.SOUL_TORCH) return SOUL_TORCH;
        if (item == Items.REDSTONE_TORCH) return REDSTONE_TORCH;
        if (item == ItemRegistry.GLOW_TORCH) return waterLogged ? GLOW_TORCH : GLOW_TORCH_OFF;
        return NONE;
    }

    public Item toItem() {
        if (this==NORMAL_TORCH) return Items.TORCH;
        if (this==COPPER_TORCH) return Items.COPPER_TORCH;
        if (this==SOUL_TORCH) return Items.SOUL_TORCH;
        if (this==REDSTONE_TORCH) return Items.REDSTONE_TORCH;
        if (this==GLOW_TORCH || this==GLOW_TORCH_OFF) return ItemRegistry.GLOW_TORCH;
        return Items.AIR;
    }
}
