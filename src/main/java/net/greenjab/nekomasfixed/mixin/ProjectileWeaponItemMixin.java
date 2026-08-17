package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.item.quiver.QuiverContents;
import net.greenjab.nekomasfixed.registry.item.quiver.QuiverItem;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin {

    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    private static void drawFromQuiver(ItemStack weapon, ItemStack projectile, LivingEntity shooter, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (!(shooter instanceof Player player)) return;

        ItemStack quiverStack = null;
        QuiverContents contents = null;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof QuiverItem) {
                QuiverContents c = stack.get(ComponentRegistry.QUIVER_CONTENTS);
                if (c != null && !c.isEmpty()) {
                    quiverStack = stack;
                    contents = c;
                    break;
                }
            }
        }

        if (contents == null) return;
        Predicate<ItemStack> supported = ((ProjectileWeaponItem) weapon.getItem()).getAllSupportedProjectiles();
        ItemStack firstArrow = contents.itemCopyStream().filter(supported).findFirst().orElse(ItemStack.EMPTY);

        if (firstArrow.isEmpty()) return;
        int numProjectiles = 1;
        if (shooter.level() instanceof ServerLevel serverLevel) {
            numProjectiles = EnchantmentHelper.processProjectileCount(serverLevel, weapon, shooter, 1);
        }

        int totalArrows = contents.getTotalArrowCount();
        if (totalArrows < numProjectiles) return;

        List<ItemStack> drawn = new ArrayList<>(numProjectiles);
        for (int i = 0; i < numProjectiles; i++) {
            ItemStack copy = firstArrow.copy();
            drawn.add(copy);
        }

        QuiverContents.Mutable mutable = new QuiverContents.Mutable(contents);
        int remaining = numProjectiles;
        while (remaining > 0 && !mutable.items.isEmpty()) {
            ItemStack stack = mutable.items.get(0);
            int toTake = Math.min(remaining, stack.getCount());
            stack.shrink(toTake);
            remaining -= toTake;
            if (stack.isEmpty()) {
                mutable.items.remove(0);
            }
        }
        quiverStack.set(ComponentRegistry.QUIVER_CONTENTS, mutable.toImmutable());
        cir.setReturnValue(drawn);
    }
}