package net.greenjab.nekomasfixed.mixin;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.other.ModCushionComponents;
import net.greenjab.nekomasfixed.util.CushionMap;
import net.greenjab.nekomasfixed.util.CushionMixinAccessor;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cushion.class)
public abstract class CushionMixin implements CushionMixinAccessor {

    @Unique
    private static final EntityDataAccessor<String> NEKOMAS_DATA_MOD_COLOR =
            SynchedEntityData.defineId(Cushion.class, EntityDataSerializers.STRING);

    @Unique
    private static final String NEKOMAS_DEFAULT_MOD_COLOR = "";

    @Override
    public String nekomas$getModColor() {
        return ((Cushion) (Object) this).getEntityData().get(NEKOMAS_DATA_MOD_COLOR);
    }

    @Override
    public void nekomas$setModColor(String color) {
        ((Cushion) (Object) this).getEntityData().set(NEKOMAS_DATA_MOD_COLOR, color);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void nekomas$defineData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEKOMAS_DATA_MOD_COLOR, NEKOMAS_DEFAULT_MOD_COLOR);
    }

    @Inject(method = "applyImplicitComponents", at = @At("TAIL"))
    private void nekomas$applyModColor(DataComponentGetter components, CallbackInfo ci) {
        String modColor = components.get(ModCushionComponents.MOD_CUSHION_COLOR);
        if (modColor != null && !modColor.isEmpty()) {
            this.nekomas$setModColor(modColor);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void nekomas$saveData(ValueOutput output, CallbackInfo ci) {
        output.store("nekomas_mod_color", Codec.STRING, this.nekomas$getModColor());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void nekomas$loadData(ValueInput input, CallbackInfo ci) {
        String loaded = input.read("nekomas_mod_color", Codec.STRING)
                .orElse(NEKOMAS_DEFAULT_MOD_COLOR);
        this.nekomas$setModColor(loaded);
    }

    @Inject(method = "getPickResult", at = @At("HEAD"), cancellable = true)
    private void nekomas$pickResult(CallbackInfoReturnable<ItemStack> cir) {
        String modColor = this.nekomas$getModColor();
        if (!modColor.isEmpty()) {
            Item item = CushionMap.BY_COLOR.get(modColor);
            if (item != null) {
                cir.setReturnValue(new ItemStack(item));
            }
        }
    }

    @Inject(method = "getCushionItemStackWithData", at = @At("HEAD"), cancellable = true)
    private void nekomas$cushionStack(CallbackInfoReturnable<ItemStack> cir) {
        String modColor = this.nekomas$getModColor();
        if (!modColor.isEmpty()) {
            Item item = CushionMap.BY_COLOR.get(modColor);
            if (item != null) {
                cir.setReturnValue(new ItemStack(item));
            }
        }
    }
}