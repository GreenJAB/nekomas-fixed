package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;

public record GlintColor(int color, Component description) {
    public static final ResourceKey<Registry<GlintColor>> REGISTRY_KEY = ResourceKey.createRegistryKey(NekomasFixed.id("glint"));
    public static final Codec<GlintColor> DIRECT_CODEC = RecordCodecBuilder.create((i) -> i.group(ExtraCodecs.STRING_RGB_COLOR.fieldOf("color").forGetter(GlintColor::color), ComponentSerialization.CODEC.fieldOf("description").forGetter(GlintColor::description)).apply(i, GlintColor::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, GlintColor> DIRECT_STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, GlintColor::color, ComponentSerialization.STREAM_CODEC, GlintColor::description, GlintColor::new);
    public static final Codec<Holder<GlintColor>> CODEC = RegistryCodecs.holder(REGISTRY_KEY, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GlintColor>> STREAM_CODEC = ByteBufCodecs.holder(REGISTRY_KEY, DIRECT_STREAM_CODEC);
}