package net.greenjab.nekomasfixed.mixin;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ColorCollection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ColorCollection.class)
public abstract class ColorCollectionMixin<T> {

    @Inject(method = "pick", at = @At("HEAD"), cancellable = true)
    public void pick(DyeColor dyeColor, CallbackInfoReturnable<T> cir) {
        ColorCollection<T> collection = (ColorCollection<T>) (Object) this;
        T result;

        if (dyeColor == DyeColor.WHITE) {
            result = collection.white();
        } else if (dyeColor == DyeColor.ORANGE) {
            result = collection.orange();
        } else if (dyeColor == DyeColor.MAGENTA) {
            result = collection.magenta();
        } else if (dyeColor == DyeColor.LIGHT_BLUE) {
            result = collection.lightBlue();
        } else if (dyeColor == DyeColor.YELLOW) {
            result = collection.yellow();
        } else if (dyeColor == DyeColor.LIME) {
            result = collection.lime();
        } else if (dyeColor == DyeColor.PINK) {
            result = collection.pink();
        } else if (dyeColor == DyeColor.GRAY) {
            result = collection.gray();
        } else if (dyeColor == DyeColor.LIGHT_GRAY) {
            result = collection.lightGray();
        } else if (dyeColor == DyeColor.CYAN) {
            result = collection.cyan();
        } else if (dyeColor == DyeColor.PURPLE) {
            result = collection.purple();
        } else if (dyeColor == DyeColor.BLUE) {
            result = collection.blue();
        } else if (dyeColor == DyeColor.BROWN) {
            result = collection.brown();
        } else if (dyeColor == DyeColor.GREEN) {
            result = collection.green();
        } else if (dyeColor == DyeColor.RED) {
            result = collection.red();
        } else if (dyeColor == DyeColor.BLACK) {
            result = collection.black();
        }
        else if (dyeColor.name().equals("amber")) {
            result = collection.black();
        } else if (dyeColor.name().equals("aqua")) {
            result = collection.black();
        } else if (dyeColor.name().equals("indigo")) {
            result = collection.black();
        } else if (dyeColor.name().equals("maroon")) {
            result = collection.black();
        } else {
            result = collection.black();
        }

        cir.setReturnValue(result);
        cir.cancel();
    }
}
