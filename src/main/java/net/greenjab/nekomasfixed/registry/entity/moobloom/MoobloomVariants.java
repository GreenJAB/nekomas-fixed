package net.greenjab.nekomasfixed.registry.entity.moobloom;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;

import java.util.Random;

public enum MoobloomVariants {
    ANCIENT("ancient", 1, Items.TORCHFLOWER.getDefaultInstance(), MobEffects.NIGHT_VISION),
    AQUA("aqua", 1, Items.BLUE_ORCHID.getDefaultInstance(), MobEffects.SATURATION),
    BLACK("black", 1, Items.WITHER_ROSE.getDefaultInstance(), MobEffects.WITHER),
    BLUE("blue", 1, Items.CORNFLOWER.getDefaultInstance(), MobEffects.JUMP),
    GRAY("gray", 1, Items.LILY_OF_THE_VALLEY.getDefaultInstance(), MobEffects.POISON),
    ORANGE("orange", 1, Items.ORANGE_TULIP.getDefaultInstance(), MobEffects.WEAKNESS),
    PINK("pink", 1, Items.PINK_TULIP.getDefaultInstance(), MobEffects.WEAKNESS),
    PURPLE("purple", 1, Items.ALLIUM.getDefaultInstance(), MobEffects.FIRE_RESISTANCE),
    RED_1("red", 1, Items.RED_TULIP.getDefaultInstance(), MobEffects.WEAKNESS),
    RED_2("red", 2, Items.POPPY.getDefaultInstance(), MobEffects.DAMAGE_BOOST),
    WHITE_1("white", 1, Items.AZURE_BLUET.getDefaultInstance(), MobEffects.BLINDNESS),
    WHITE_2("white", 2, Items.WHITE_TULIP.getDefaultInstance(), MobEffects.WEAKNESS),
    WHITE_3("white", 3, Items.OXEYE_DAISY.getDefaultInstance(), MobEffects.REGENERATION),
    YELLOW("yellow", 1, Items.DANDELION.getDefaultInstance(), MobEffects.SATURATION);
    // 26.x had a GRAY_2 variant fed by open_eyeblossom; that flower isn't in 1.21.1, so it's dropped.

    public final String path;
    public final ItemStack flower;
    public final SuspiciousStewEffects.Entry effect;

    MoobloomVariants(String path, int variant, ItemStack flower, Holder<MobEffect> effect) {
        this.path = path.concat("_cow_").concat(Integer.toString(variant));
        this.flower = flower;
        this.effect = new SuspiciousStewEffects.Entry(effect, 20 * 15);
    }

    public static MoobloomVariants getRandomVariant() {
        int randInt = new Random().nextInt(0, MoobloomVariants.values().length);
        while (MoobloomVariants.values()[randInt].flower.is(Items.WITHER_ROSE) ||
                MoobloomVariants.values()[randInt].flower.is(Items.TORCHFLOWER))
            randInt = new Random().nextInt(0, MoobloomVariants.values().length);
        return MoobloomVariants.values()[randInt];
    }

    public static MoobloomVariants fromPath(String path) {
        for (MoobloomVariants variant : values()) {
            if (variant.path.equals(path)) return variant;
        }
        return ANCIENT;
    }

    public static MoobloomVariants fromFlower(Item flower) {
        for (MoobloomVariants variant : values()) {
            if (variant.flower.is(flower)) return variant;
        }
        return ANCIENT;
    }
}