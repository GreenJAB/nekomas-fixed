package net.greenjab.nekomasfixed.registry.entity.Moobloom;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Random;

public enum MoobloomEntityVariants {
    ANCIENT("ancient" , 1, Items.TORCHFLOWER.getDefaultStack()),
    AQUA("aqua", 1, Items.BLUE_ORCHID.getDefaultStack()),
    BLACK("black", 1, Items.WITHER_ROSE.getDefaultStack()),
    BLUE("blue", 1, Items.CORNFLOWER.getDefaultStack()),
    GRAY("gray", 1, Items.LILY_OF_THE_VALLEY.getDefaultStack()),
    ORANGE("orange", 1, Items.ORANGE_TULIP.getDefaultStack()),
    PINK("pink", 1, Items.PINK_TULIP.getDefaultStack()),
    PURPLE("purple", 1, Items.ALLIUM.getDefaultStack()),
    RED_1("red", 1, Items.RED_TULIP.getDefaultStack()),
    RED_2("red", 2,Items.POPPY.getDefaultStack()),
    WHITE_1("white", 1, Items.AZURE_BLUET.getDefaultStack()),
    WHITE_2("white", 2, Items.WHITE_TULIP.getDefaultStack()),
    WHITE_3("white", 3, Items.OXEYE_DAISY.getDefaultStack()),
    YELLOW("yellow", 1, Items.DANDELION.getDefaultStack())
    ;

    public final String path;
    public final ItemStack flower;

    MoobloomEntityVariants(String path, int variant, ItemStack flower){
        this.path = path.concat("_cow_").concat(Integer.toString(variant));
        this.flower = flower;
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
