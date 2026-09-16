package net.greenjab.nekomasfixed.mixin.alternate_current;

import alternate.current.wire.Node;
import alternate.current.wire.WireHandler;
import alternate.current.wire.WireNode;
import net.greenjab.nekomasfixed.mixin.accessor.NodeAccessor;
import net.greenjab.nekomasfixed.registry.item.RedstoneStrikerItem;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WireHandler.class)
public class WireHandlerMixin  {
    @Shadow
    @Final
    private ServerLevel level;

    @Inject(method = "getDirectSignalTo", at = @At("HEAD"), cancellable = true)
    private void getDirectSignalTo(WireNode wire, Node node, CallbackInfoReturnable<Integer> cir) {
        NodeAccessor ac = (NodeAccessor)node;
        GlobalPos pos = GlobalPos.of(level.dimension(), ac.getPos());
        if(RedstoneStrikerItem.STRUCK_WIRES.containsKey(pos)){
            cir.setReturnValue(15);
        }
    }

    @Inject(method = "getExternalPower", at = @At("HEAD"), cancellable = true)
    private void getExternalPower(WireNode wire, CallbackInfoReturnable<Integer> cir) {
        NodeAccessor ac = (NodeAccessor)wire;
        GlobalPos pos = GlobalPos.of(level.dimension(), ac.getPos());
        if(RedstoneStrikerItem.STRUCK_WIRES.containsKey(pos)){
            cir.setReturnValue(15);
        }
    }
}

