package net.greenjab.nekomasfixed.registry.entity.Moobloom;

import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.Random;

public enum MoobloomEntityVariants {
    ANCIENT("ancient" , 1, Items.TORCHFLOWER.getDefaultStack(), StatusEffects.NIGHT_VISION),
    AQUA("aqua", 1, Items.BLUE_ORCHID.getDefaultStack(), StatusEffects.SATURATION),
    BLACK("black", 1, Items.WITHER_ROSE.getDefaultStack(), StatusEffects.WITHER),
    BLUE("blue", 1, Items.CORNFLOWER.getDefaultStack(), StatusEffects.JUMP_BOOST),
    GRAY("gray", 1, Items.LILY_OF_THE_VALLEY.getDefaultStack(), StatusEffects.POISON),
    ORANGE("orange", 1, Items.ORANGE_TULIP.getDefaultStack(), StatusEffects.WEAKNESS),
    PINK("pink", 1, Items.PINK_TULIP.getDefaultStack(), StatusEffects.WEAKNESS),
    PURPLE("purple", 1, Items.ALLIUM.getDefaultStack(), StatusEffects.FIRE_RESISTANCE),
    RED_1("red", 1, Items.RED_TULIP.getDefaultStack(), StatusEffects.WEAKNESS),
    RED_2("red", 2,Items.POPPY.getDefaultStack(), StatusEffects.STRENGTH),
    WHITE_1("white", 1, Items.AZURE_BLUET.getDefaultStack(), StatusEffects.BLINDNESS),
    WHITE_2("white", 2, Items.WHITE_TULIP.getDefaultStack(), StatusEffects.WEAKNESS),
    WHITE_3("white", 3, Items.OXEYE_DAISY.getDefaultStack(), StatusEffects.REGENERATION),
    YELLOW("yellow", 1, Items.DANDELION.getDefaultStack(), StatusEffects.SATURATION)
    ;

    public final String path;
    public final ItemStack flower;
    public final SuspiciousStewEffectsComponent.StewEffect effect;

    MoobloomEntityVariants(String path, int variant, ItemStack flower, RegistryEntry<StatusEffect> effect){
        this.path = path.concat("_cow_").concat(Integer.toString(variant));
        this.flower = flower;
        this.effect = new SuspiciousStewEffectsComponent.StewEffect(effect, 20*15);
    }

    public static MoobloomEntityVariants getRandomVariant(){
        int randInt = new Random().nextInt(0, MoobloomEntityVariants.values().length);
        return MoobloomEntityVariants.values()[randInt];
    }

    public static MoobloomEntityVariants fromPath(String path) {
        for (MoobloomEntityVariants variant : values()) {
            if (variant.path.equals(path)) {
                return variant;
            }
        }

        return ANCIENT;
    }
}
