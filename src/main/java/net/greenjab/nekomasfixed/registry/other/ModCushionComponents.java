package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import java.util.function.UnaryOperator;

public class ModCushionComponents {

    public static final DataComponentType<String> MOD_CUSHION_COLOR =
            registerComponent("cushion_mod_color",
                    b -> b.persistent(Codec.STRING)
                          .networkSynchronized(ByteBufCodecs.STRING_UTF8));

    private static <T> DataComponentType<T> registerComponent(String id,
                                                              UnaryOperator<DataComponentType.Builder<T>> op) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
                id, op.apply(DataComponentType.builder()).build());
    }
}
