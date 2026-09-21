package net.greenjab.nekomasfixed.mixin.integration;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.target_access_class.SheepAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import static java.util.Map.entry;

@Mixin(Sheep.class)
public abstract class GlowInkPlus {
    @Shadow public abstract DyeColor getColor();

    @Unique
    private static Block get(String name) {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("glow_ink_plus", name));
    }
    @Unique
    private static Map<DyeColor, ItemLike> GLOW_DROPS;

    @Unique
    private static void nekomasfixed$initialize() {
        GLOW_DROPS = ImmutableMap.ofEntries(
                entry(DyeColor.WHITE, get("glow_white_wool")),
                entry(DyeColor.ORANGE, get("glow_orange_wool")),
                entry(DyeColor.MAGENTA, get("glow_magenta_wool")),
                entry(DyeColor.LIGHT_BLUE, get("glow_light_blue_wool")),
                entry(DyeColor.YELLOW, get("glow_yellow_wool")),
                entry(DyeColor.LIME, get("glow_lime_wool")),
                entry(DyeColor.PINK, get("glow_pink_wool")),
                entry(DyeColor.GRAY, get("glow_gray_wool")),
                entry(DyeColor.LIGHT_GRAY, get("glow_light_gray_wool")),
                entry(DyeColor.CYAN, get("glow_cyan_wool")),
                entry(DyeColor.PURPLE, get("glow_purple_wool")),
                entry(DyeColor.BLUE, get("glow_blue_wool")),
                entry(DyeColor.BROWN, get("glow_brown_wool")),
                entry(DyeColor.GREEN, get("glow_green_wool")),
                entry(DyeColor.RED, get("glow_red_wool")),
                entry(DyeColor.BLACK, get("glow_black_wool"))
        );
    }

    @WrapOperation(method = "shear", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object nekomasfixed$swapWithGlowingVariant(Map<DyeColor, ItemLike> instance, Object o, Operation<Object> original) {
        if (((SheepAccess) this).nekomasfixed$isGlowing()) {
            if (GLOW_DROPS == null) {
                nekomasfixed$initialize();
            }
            return GLOW_DROPS.get((DyeColor) o);
        }
        return original.call(instance, o);
    }
}