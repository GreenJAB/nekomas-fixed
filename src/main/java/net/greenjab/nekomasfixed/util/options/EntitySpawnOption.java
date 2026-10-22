package net.greenjab.nekomasfixed.util.options;

import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class EntitySpawnOption extends Option<Boolean>{
    public static final List<EntitySpawnOption> ALL_SPAWN_CONFIGS = new ArrayList<>();

    private final Supplier<EntityType<?>> type;

    public EntitySpawnOption(String name, Supplier<EntityType<?>> type, boolean defaultValue) {
        super(name, defaultValue);
        this.type = type;
        ALL_SPAWN_CONFIGS.add(this);
    }

    public static boolean isDisabled(EntityType<?> t) {
        for (EntitySpawnOption o : ALL_SPAWN_CONFIGS) {
            if (o.type.get() == t) return !o.get();
        }
        return false;
    }
}
