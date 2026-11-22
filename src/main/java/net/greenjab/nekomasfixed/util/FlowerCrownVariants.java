package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum FlowerCrownVariants {
    TORCHFLOWER(Items.TORCHFLOWER, MobEffects.NIGHT_VISION),
    BLUE_ORCHID(Items.BLUE_ORCHID, MobEffects.SATURATION),
    WITHER_ROSE(Items.WITHER_ROSE, MobEffects.WITHER),
    CORNFLOWER(Items.CORNFLOWER, MobEffects.JUMP),
    LILY_OF_THE_VALLEY(Items.LILY_OF_THE_VALLEY, MobEffects.POISON),
    ORANGE_TULIP(Items.ORANGE_TULIP, MobEffects.WEAKNESS),
    PINK_TULIP(Items.PINK_TULIP, MobEffects.WEAKNESS),
    ALLIUM(Items.ALLIUM, MobEffects.FIRE_RESISTANCE),
    RED_TULIP(Items.RED_TULIP, MobEffects.WEAKNESS),
    POPPY(Items.POPPY, MobEffects.NIGHT_VISION),
    AZURE_BLUET(Items.AZURE_BLUET, MobEffects.BLINDNESS),
    WHITE_TULIP(Items.WHITE_TULIP, MobEffects.WEAKNESS),
    OXEYE_DAISY(Items.OXEYE_DAISY, MobEffects.REGENERATION),
    DANDELION(Items.DANDELION, MobEffects.SATURATION);

    public final Item flower;
    public final Holder<MobEffect> effect;
    public final ResourceLocation texture;

    FlowerCrownVariants(Item flower, Holder<MobEffect> effect) {
        this.flower = flower;
        this.effect = effect;
        this.texture = NekomasFixed.id("textures/entity/equipment/humanoid/flower_crown/" + name().toLowerCase() + ".png");
    }

    public String itemId() {
        return name().toLowerCase() + "_flower_crown";
    }
}
