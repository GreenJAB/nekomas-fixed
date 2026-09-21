package net.greenjab.nekomasfixed.mixin.client;

import com.google.common.collect.ImmutableMap;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BlockStateModelLoader.class)
public abstract class BlockStateModelLoaderMixin {

    @Mutable
    @Final
    @Shadow
    private static Map<ResourceLocation, StateDefinition<Block, BlockState>> STATIC_DEFINITIONS;

    @Mutable
    @Final
    @Shadow
    private static StateDefinition<Block, BlockState> ITEM_FRAME_FAKE_DEFINITION;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injected(CallbackInfo ci) {
        STATIC_DEFINITIONS = new ImmutableMap.Builder<ResourceLocation, StateDefinition<Block, BlockState>>()
                .putAll(STATIC_DEFINITIONS)
                .put(NekomasFixed.id("clear_item_frame"), ITEM_FRAME_FAKE_DEFINITION)
                .build();
    }
}
