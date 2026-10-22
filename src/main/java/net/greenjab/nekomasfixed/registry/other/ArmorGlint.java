package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.equipment.trim.ArmorTrim;

import java.util.function.Consumer;

public record ArmorGlint(Holder<GlintColor> color) implements TooltipProvider {
    public static final Codec<ArmorGlint> CODEC = GlintColor.CODEC.xmap(ArmorGlint::new, ArmorGlint::color);
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorGlint> STREAM_CODEC = GlintColor.STREAM_CODEC.map(ArmorGlint::new, ArmorGlint::color);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        consumer.accept(Component.translatable("tooltip_component.nekomasfixed.glint").withStyle(ChatFormatting.GRAY));
        consumer.accept(CommonComponents.space().append(this.color.value().description()));
    }
}
