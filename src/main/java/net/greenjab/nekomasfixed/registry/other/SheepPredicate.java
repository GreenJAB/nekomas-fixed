package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.target_access.SheepAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.predicate.entity.EntitySubPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public record SheepPredicate(Optional<Boolean> sheared, Optional<Boolean> glowing) implements EntitySubPredicate {
    public static final MapCodec<SheepPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(
                    Codec.BOOL.optionalFieldOf("sheared").forGetter(SheepPredicate::sheared),
                    Codec.BOOL.optionalFieldOf("glowing").forGetter(SheepPredicate::glowing)
                    ).apply(instance, SheepPredicate::new));

    @Override
    public MapCodec<SheepPredicate> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(Entity entity, ServerWorld world, @Nullable Vec3d pos) {
        if (entity instanceof SheepEntity sheepEntity) {
            return (sheared.isEmpty() || sheepEntity.isSheared() == sheared.get()) &&
                    (glowing.isEmpty() || ((SheepAccess)sheepEntity).nekomasfixed$isGlowing() == glowing.get());
        } else {
            return false;
        }
    }

    public static SheepPredicate glowing(boolean glowing) {
        return new SheepPredicate(Optional.empty(), Optional.of(glowing));
    }
}
