package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.EntityTypeRegistry;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import net.greenjab.nekomasfixed.util.options.EntitySpawnOption;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(SpawnEggItem.class)
public class SpawnEggItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if(context.getLevel() instanceof ServerLevel){
            SpawnEggItem spawnEgg = (SpawnEggItem) (Object) this;
            EntityType<?> entity = SpawnEggItem.getType(spawnEgg.getDefaultInstance());

            assert entity != null;

            if(EntitySpawnOption.isDisabled(entity)){
                context.getPlayer().sendSystemMessage(Component.translatable(
                        "failed.entity_spawn"
                ).withColor(TextColor.RED));
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }
    }
}
