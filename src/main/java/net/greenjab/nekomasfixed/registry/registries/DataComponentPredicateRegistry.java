package net.greenjab.nekomasfixed.registry.registries;

import com.mojang.serialization.Codec;
import net.greenjab.nekomasfixed.registry.other.predicates.QuiverPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;

public class DataComponentPredicateRegistry {
    public static final DataComponentPredicate.Type<QuiverPredicate> HAS_ARROWS = register("has_arrows", QuiverPredicate.CODEC);

    private static <T extends DataComponentPredicate> DataComponentPredicate.Type<T> register(final String id, final Codec<T> codec) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_PREDICATE_TYPE, id, new DataComponentPredicate.ConcreteType<>(codec));
    }

    public static void register(){
        System.out.println("Registering predicates");
    }
}
