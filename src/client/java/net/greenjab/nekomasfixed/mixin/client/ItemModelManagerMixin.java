package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.greenjab.nekomasfixed.mixin.client.accessor.ItemRenderStateAccessor;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.EchoingKeyframe;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import static net.minecraft.component.DataComponentTypes.TRIM;

@Mixin(ItemModelManager.class)
public class ItemModelManagerMixin {
    @Unique
    public void nekomasfixed$applyEchoingKeyFrame(ItemStack stack, EchoingKeyframe keyframe, ArmorTrim original) {
        if (keyframe.hidden()) {
            stack.remove(TRIM);
        } else {
            stack.set(TRIM, keyframe.toTrim(original));
        }
    }

    @Unique
    public void nekomasfixed$setOpacity(ItemRenderState state, float alpha, boolean renderBase) {
        ItemRenderState.LayerRenderState[] layers = ((ItemRenderStateAccessor) state).nekomasfixed$getLayers();
        ItemRenderState.LayerRenderState lastLayer = layers[layers.length-1];
        // Increase tints length
        int[] tints = lastLayer.initTints(lastLayer.initTints(-1).length + 2);

        // Add new opacity tint
        // (hide armor completely and make "top echoing layer trim" transparent)
        int tintArmorBase = ColorHelper.whiteWithAlpha(ColorHelper.channelFromFloat(renderBase ? 1f : 0f));
        int tintTrim = ColorHelper.whiteWithAlpha(ColorHelper.channelFromFloat(alpha));
        // The tints should be empty so I could've used just indexes 0 and 1
        // but other mods and resourcepacks could change something in the models or
        // the rendering itself so...
        // I'm actually not sure if that changes anything...
        tints[tints.length-2] = tintArmorBase;
        tints[tints.length-1] = tintTrim;
        state.addModelKey(tintArmorBase);
        state.addModelKey(tintTrim);
    }

    @WrapOperation(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/model/ItemModel;update(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/item/ItemModelManager;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/HeldItemContext;I)V")
    )
    private void updateEchoingLayers(ItemModel instance, ItemRenderState state, ItemStack stack, ItemModelManager itemModelManager, ItemDisplayContext itemDisplayContext, ClientWorld world, HeldItemContext heldItemContext, int i, Operation<Void> original) {
        if (world != null && stack.contains(TRIM) && stack.contains(ComponentRegistry.ECHOING_LAYERS)) {
            ArmorTrim savedTrim = stack.get(TRIM);

            long worldTime = world.getTime();
            float t = Math.floorMod(worldTime, 10L) * 0.1f;
            long time = Math.floorDiv(worldTime, 10L);
            Pair<EchoingKeyframe, EchoingKeyframe> pair = EchoingKeyframe.buildKeyFrames(stack, time);

            nekomasfixed$applyEchoingKeyFrame(stack, pair.getLeft(), savedTrim);
            original.call(instance, state, stack, itemModelManager, itemDisplayContext, world, heldItemContext, i);
            // Check if layers are different. If so, overlay one on top of the other
            if (
                    pair.getLeft().hidden() != pair.getRight().hidden() ||
                            !pair.getLeft().material().equals(pair.getRight().material())
            ) {
                // If the top layer is invisible, it can't be overlayed with alpha,
                // so the bottom one's opacity should be changed
                if (pair.getRight().hidden()) {
                    nekomasfixed$setOpacity(state, (MathHelper.cos(MathHelper.PI * t) + 1) * 0.5f, true);
                } else {
                    nekomasfixed$applyEchoingKeyFrame(stack, pair.getRight(), savedTrim);
                    original.call(instance, state, stack, itemModelManager, itemDisplayContext, world, heldItemContext, i);
                    nekomasfixed$setOpacity(state, (-MathHelper.cos(MathHelper.PI * t) + 1) * 0.5f, false);
                }
            }

            stack.set(TRIM, savedTrim);
        } else {
            original.call(instance, state, stack, itemModelManager, itemDisplayContext, world, heldItemContext, i);
        }
    }
}
