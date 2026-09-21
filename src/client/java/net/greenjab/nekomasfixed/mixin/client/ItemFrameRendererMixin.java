package net.greenjab.nekomasfixed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.ClearItemFrameEntity;
import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameRenderer.class)
public abstract class ItemFrameRendererMixin<T extends ItemFrame> extends EntityRenderer<T> {
    @Unique
    private static final ModelResourceLocation CLEAR_FRAME = new ModelResourceLocation(NekomasFixed.id("clear_item_frame"), "map=false");
    @Unique
    private static final ModelResourceLocation MAP_CLEAR_FRAME = new ModelResourceLocation(NekomasFixed.id("clear_item_frame"), "map=true");

    protected ItemFrameRendererMixin(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Inject(method = "getFrameModelResourceLoc", at = @At("HEAD"), cancellable = true)
    private void getModelId(T entity, ItemStack stack, CallbackInfoReturnable<ModelResourceLocation> cir) {
        if (entity.getType() == EntityTypeRegistry.CLEAR_ITEM_FRAME) {
            cir.setReturnValue(stack.is(Items.FILLED_MAP) ? MAP_CLEAR_FRAME : CLEAR_FRAME);
        }
    }

    @Redirect(
            method = "render(Lnet/minecraft/world/entity/decoration/ItemFrame;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/Sheets;solidBlockSheet()Lnet/minecraft/client/renderer/RenderType;"
            )
    )
    private RenderType injected(T itemFrameEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {

        return itemFrameEntity instanceof ClearItemFrameEntity ? Sheets.translucentCullBlockSheet() : Sheets.solidBlockSheet();
    }
}
