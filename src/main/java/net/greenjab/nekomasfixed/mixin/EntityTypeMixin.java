package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.util.options.EntitySpawnOption;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.swing.text.html.parser.Entity;

@Mixin(EntityType.class)
public class EntityTypeMixin {
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private void diableSpawns(Level level, CallbackInfoReturnable<Boolean> cir) {
        if (EntitySpawnOption.isDisabled((EntityType<?>) (Object)this)) {
            cir.setReturnValue(false);
        }
    }
}
