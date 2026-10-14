package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record AnimalComponent(List<AnimalComponent.StoredEntityData> animal) implements TooltipProvider {

    public static final List<String> IRRELEVANT_ANIMAL_NBT_KEYS = Arrays.asList(
            "Air", "drop_chances", "Brain", "CanPickUpLoot", "DeathTime", "fall_distance", "FallFlying",
            "Fire", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "NoGravity", "OnGround", "PortalCooldown",
            "Pos", "Rotation", "sleeping_pos", "CannotEnterHiveTicks", "hive_pos", "Passengers", "leash", "UUID"
    );

    public record StoredEntityData(CustomData entityData, long tickEnteredHive) {

        public static final Codec<StoredEntityData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CustomData.CODEC.fieldOf("entity_data").forGetter(StoredEntityData::entityData),
                Codec.LONG.fieldOf("tick_entered_hive").forGetter(StoredEntityData::tickEnteredHive)
        ).apply(instance, StoredEntityData::new));

        public static final StreamCodec<ByteBuf, StoredEntityData> PACKET_CODEC = StreamCodec.composite(
                CustomData.STREAM_CODEC, StoredEntityData::entityData,
                ByteBufCodecs.VAR_LONG, StoredEntityData::tickEnteredHive,
                StoredEntityData::new
        );

        public static final Codec<List<StoredEntityData>> LIST_CODEC = CODEC.listOf();

        public static StoredEntityData of(Entity entity) {
            CompoundTag tag = new CompoundTag();
            entity.save(tag);
            IRRELEVANT_ANIMAL_NBT_KEYS.forEach(tag::remove);
            return new StoredEntityData(CustomData.of(tag), entity.level().getGameTime());
        }

        @Nullable
        public Entity loadEntity(Level level) {
            CompoundTag tag = this.entityData.copyTag();
            IRRELEVANT_ANIMAL_NBT_KEYS.forEach(tag::remove);
            return EntityType.loadEntityRecursive(tag, level, entity -> entity);
        }

        public long tickEnteredHive() {
            return this.tickEnteredHive;
        }
    }

    public static final Codec<AnimalComponent> CODEC = StoredEntityData.LIST_CODEC
            .xmap(AnimalComponent::new, AnimalComponent::animal);

    public static final StreamCodec<ByteBuf, AnimalComponent> PACKET_CODEC = StoredEntityData.PACKET_CODEC
            .apply(ByteBufCodecs.list())
            .map(AnimalComponent::new, AnimalComponent::animal);

    public static final AnimalComponent DEFAULT = new AnimalComponent(List.of());

    @Override
    public void addToTooltip(Item.@NonNull TooltipContext context, @NonNull Consumer<Component> textConsumer, @NonNull TooltipFlag type) {
        if (!this.animal.isEmpty()) {
            CompoundTag nbt = this.animal.getFirst().entityData().copyTag();
            String id = nbt.getString("id");
            EntityType<?> entityType = id.isEmpty() ? null
                    : BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(id));
            Optional<String> name = nbt.contains("CustomName", net.minecraft.nbt.Tag.TAG_STRING)
                    ? Optional.of(nbt.getString("CustomName")).filter(s -> !s.isEmpty())
                    : Optional.empty();
            if (name.isPresent()) {
                String raw = name.get();
                // CustomName is stored as a JSON text component; extract the plain text
                // so we don't render the raw JSON (extra quotes).
                try {
                    com.google.gson.JsonElement json = com.google.gson.JsonParser.parseString(raw);
                    if (json.isJsonObject() && json.getAsJsonObject().has("text")) {
                        raw = json.getAsJsonObject().get("text").getAsString();
                    } else if (json.isJsonPrimitive()) {
                        raw = json.getAsString();
                    }
                } catch (Exception ignored) {
                }
                name = Optional.of(raw.replaceAll("^\"|\"$", "")).filter(s -> !s.isEmpty());
            }
            Optional<Integer> age = nbt.contains("Age", net.minecraft.nbt.Tag.TAG_INT)
                    ? Optional.of(nbt.getInt("Age"))
                    : Optional.empty();
            Optional<String> variant = nbt.contains("variant", net.minecraft.nbt.Tag.TAG_STRING)
                    ? Optional.of(nbt.getString("variant")).filter(s -> !s.isEmpty())
                    : Optional.empty();
            if (variant.isPresent()) {
                String raw = variant.get();
                String s = raw.contains(":") ? raw.split(":")[1] : raw;
                variant = Optional.of(s.substring(0, 1).toUpperCase() + s.substring(1));
            }
            Component typeName = entityType != null
                    ? Component.translatable(entityType.getDescriptionId())
                    : Component.literal(id.isEmpty() ? "Unknown" : id);
            textConsumer.accept(Component.translatable("container.nautilus",
                    (age.isPresent() && age.get() < 0 ? "Baby " : ""),
                    variant.map(s -> s + " ").orElse(""),
                    typeName,
                    name.map(s -> ": \"" + s + "\"").orElse("")
            ).withStyle(ChatFormatting.GRAY));
        }
    }
}
