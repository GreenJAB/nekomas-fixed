package net.greenjab.nekomasfixed.mixin;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DyeColor.class, priority = 1)
public enum DyeColorMixin {
    //plz don't touch anything in this class (I and green jab can) - its a very sensitive class

    AMBER(16,"amber", 0xE0AF0B, MapColor.GOLD, MapColor.GOLD, 0xE0AF0B, 0xE0AF0B),
    AQUA(17,"aqua", 0xA6CEC7, MapColor.TERRACOTTA_CYAN, MapColor.TERRACOTTA_CYAN, 0xA6CEC7, 0xA6CEC7),
    INDIGO(18, "indigo", 0xFF453C8F, MapColor.TERRACOTTA_PURPLE, MapColor.TERRACOTTA_PURPLE, 0xFF453C8F, 0xFF453C8F),
    MAROON(19,"maroon", 0xFFA62D10, MapColor.TERRACOTTA_RED, MapColor.TERRACOTTA_RED, 0xFFA62D10, 0xFFA62D10);

    private DyeColorMixin(
            final int id,
            final String name,
            final int textureDiffuseColor,
            final MapColor mapColor,
            final MapColor terracottaColor,
            final int fireworkColor,
            final int textColor
    ) {

    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void nekomasfixed$addCustomColors(CallbackInfo ci) {
        System.out.println("[NekomasFixed] DyeColorMixin applied! Adding custom colors...");

    }
}