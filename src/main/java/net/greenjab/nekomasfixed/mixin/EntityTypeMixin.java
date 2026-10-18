package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public class EntityTypeMixin {
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private void modConfigSpawn(Level level, CallbackInfoReturnable<Boolean> cir){
        EntityType<?> type = (EntityType<?>)(Object)this;
        if(type.getBaseClass().equals(SuspiciousSpider.class) && !ModConfigValues.spawnSusSpider.get()){
            cir.setReturnValue(false);
        }
    }
}
