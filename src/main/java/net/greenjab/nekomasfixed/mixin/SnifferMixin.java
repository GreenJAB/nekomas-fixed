package net.greenjab.nekomasfixed.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Sniffer.class)
public abstract class SnifferMixin extends Animal {
    @Unique
    private static final ResourceKey<LootTable> SNOWY = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("gameplay/sniffer_digging_snowy"));
    @Unique
    private static final ResourceKey<LootTable> DESERT = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("gameplay/sniffer_digging_desert"));
    @Unique
    private static final ResourceKey<LootTable> BADLANDS = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("gameplay/sniffer_digging_badlands"));

    protected SnifferMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    protected abstract BlockPos getHeadBlock();

    @Redirect(method = "dropSeed", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/level/storage/loot/BuiltInLootTables;SNIFFER_DIGGING:Lnet/minecraft/resources/ResourceKey;"))
    private ResourceKey<LootTable> biomeLootTable() {
        var biome = level().getBiome(getHeadBlock());
        if (biome.value().getBaseTemperature() <= 0.15F) {
            return SNOWY;
        }
        String name = biome.unwrapKey().map(key -> key.location().toString()).orElse("");
        if (name.contains("des")) {
            return DESERT;
        }
        if (name.contains("badlands")) {
            return BADLANDS;
        }
        return BuiltInLootTables.SNIFFER_DIGGING;
    }
}
