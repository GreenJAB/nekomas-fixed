package net.greenjab.nekomasfixed.registry.block.enums;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;

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

    public static ItemStack getItem(GoatHornTorchType type) {
        return switch (type) {
            case TORCH -> Items.TORCH.getDefaultStack();
            case COPPER_TORCH -> Items.COPPER_TORCH.getDefaultStack();
            case REDSTONE_TORCH -> Items.REDSTONE_TORCH.getDefaultStack();
            case SOULFIRE_TORCH -> Items.SOUL_TORCH.getDefaultStack();
            case GLOW_TORCH, GLOW_TORCH_OFF ->
                    ItemRegistry.GLOW_TORCH != null ? ItemRegistry.GLOW_TORCH.getDefaultStack() : ItemStack.EMPTY;
            case NONE -> ItemStack.EMPTY;
        };
    }

   public static SimpleParticleType getTorchParticle(GoatHornTorchType type){
        return switch(type){
            case TORCH ->  ParticleTypes.FLAME;
            case COPPER_TORCH ->  ParticleTypes.COPPER_FIRE_FLAME;
            case GLOW_TORCH_OFF -> ParticleTypes.SMOKE;
            case GLOW_TORCH ->   ParticleTypes.GLOW;
            case SOULFIRE_TORCH ->  ParticleTypes.SOUL_FIRE_FLAME;
            case REDSTONE_TORCH ->  ParticleTypes.SMOKE;
            case NONE -> null;
        };
   }




}
