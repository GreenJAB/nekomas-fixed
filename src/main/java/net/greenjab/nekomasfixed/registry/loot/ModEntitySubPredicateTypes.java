package net.greenjab.nekomasfixed.registry.loot;

import com.mojang.serialization.MapCodec;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.other.SheepPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntitySubPredicateTypes {
    public static final MapCodec<SheepPredicate> SHEEP = Registry.register(
            Registries.ENTITY_SUB_PREDICATE_TYPE,
            NekomasFixed.id("sheep"),
            SheepPredicate.CODEC);

    public static void onInitialize() {

    }
}
