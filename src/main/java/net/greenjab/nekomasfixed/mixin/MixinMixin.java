package net.greenjab.nekomasfixed.mixin;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class MixinMixin implements IMixinConfigPlugin {
    private static final Supplier<Boolean> TRUE = () -> true;

    // plz do not touch this class unless u ask cyber or green jab or akshaj
    // strikey can port this class to 1.21.1

    private static final Supplier<Boolean> HAS_ALTERNATE_CURRENT = 
            () -> FabricLoader.getInstance().isModLoaded("alternate-current");

    private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.of(
            "net.greenjab.nekomasfixed.mixin.alternate_current.WireHandlerAccessor", HAS_ALTERNATE_CURRENT,
            "net.greenjab.nekomasfixed.mixin.alternate_current.NodeMixin", HAS_ALTERNATE_CURRENT,
            "net.greenjab.nekomasfixed.mixin.accessor.NodeAccessor", HAS_ALTERNATE_CURRENT
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
    }

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}