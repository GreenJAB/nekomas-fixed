package net.greenjab.nekomasfixed.mixin.client;

import net.greenjab.nekomasfixed.registry.other.ArmorGlint;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.greenjab.nekomasfixed.util.GlintTinted;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class ItemModelResolverMixin {
    @Inject(method = "updateForTopItem", at = @At(value = "RETURN"))
    private void nekomasfixed$glintModelResolver(ItemStackRenderState output, ItemStack item, ItemDisplayContext displayContext, Level level, ItemOwner owner, int seed, CallbackInfo ci) {
        int color;
        Identifier base;
        ArmorGlint glint = item.get(ComponentRegistry.ARMOR_GLINT);
        if (glint == null) {
            color = 0;
            base = null;
        } else {
            color = glint.color().value().colorAt(Util.getMillis());
            base = glint.color().value().texture().orElse(null);
        }
        ((GlintTinted) output).nekomasfixed$setColor(color);
        ((GlintTinted) output).nekomasfixed$setGlintBase(base);
    }
}
