package net.greenjab.nekomasfixed.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.greenjab.nekomasfixed.util.MixinGlobals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BlockStateManagers;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Function;

@Mixin(BlockStateManagers.class)
public abstract class BlockStatesManagersMixin {

    @Shadow
    private static StateManager<Block, BlockState> createItemFrameStateManager() {
        return null;
    }

    static {
        MixinGlobals.CLEAR_ITEM_FRAME = BlockStatesManagersMixin.createItemFrameStateManager();
    }

    @Inject(method = "createIdToManagerMapper", at = @At("TAIL"))
    private static void createIdToManagerMapper(CallbackInfoReturnable<Function<Identifier, StateManager<Block, BlockState>>> cir, @Local Map<Identifier, StateManager<Block, BlockState>> map) {
        map.put(MixinGlobals.CLEAR_ITEM_FRAME_ID, MixinGlobals.CLEAR_ITEM_FRAME);
    }
}
