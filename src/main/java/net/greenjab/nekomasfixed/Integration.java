package net.greenjab.nekomasfixed;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Integration implements IMixinConfigPlugin {
    private static final Map<String, String> DEPENDENCIES = ImmutableMap.of(
            "net.greenjab.nekomasfixed.mixin.integration.GlowInkPlus", "glow_ink_plus"
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !DEPENDENCIES.containsKey(mixinClassName) || FabricLoader.getInstance().isModLoaded(DEPENDENCIES.get(mixinClassName));
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
