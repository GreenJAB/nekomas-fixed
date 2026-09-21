package net.greenjab.nekomasfixed.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.NekomasFixed;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Consumer;

import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;

public record EchoingLayer(
        Optional<Boolean> glowing,
        Optional<Boolean> hidden,
        Optional<Holder<TrimMaterial>> material
) {
    private static final Component SMITHING_TEXT_GLOWING;
    private static final Component SMITHING_TEXT_HIDDEN;
    public static final Component ECHOING_EFFECT_TEXT;
    private static final Component GLOWING_TEXT;
    private static final Component HIDDEN_TEXT;

    public static final Codec<EchoingLayer> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.BOOL.optionalFieldOf("glowing").forGetter(EchoingLayer::glowing),
            Codec.BOOL.optionalFieldOf("hidden").forGetter(EchoingLayer::hidden),
            TrimMaterial.CODEC.optionalFieldOf("material").forGetter(EchoingLayer::material)
    ).apply(builder, EchoingLayer::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, EchoingLayer> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ByteBufCodecs.BOOL), EchoingLayer::glowing,
            ByteBufCodecs.optional(ByteBufCodecs.BOOL), EchoingLayer::hidden,
            ByteBufCodecs.optional(TrimMaterial.STREAM_CODEC), EchoingLayer::material,
            EchoingLayer::new
    );

    public static List<EchoingLayer> emptyList() {
        ArrayList<EchoingLayer> list = new ArrayList<>();
        list.add(new EchoingLayer(Optional.empty(), Optional.empty(), Optional.empty()));
        return list;
    }

    public static ResourceLocation id(String name) {
        return NekomasFixed.id(name);
    }

    public static MutableComponent addition() {
        return Component.literal(" + ");
    }

    public static MutableComponent subtraction() {
        return Component.literal(" - ");
    }

    public static MutableComponent subtractionLineStart() {
        return Component.literal("- ");
    }

    public static Component getTextForMaterial(TrimMaterial material) {
        return Component.translatable(Util.makeDescriptionId("echoing_effect", id("material")), material.description());
    }

    public static void addNecessarySeparator(MutableComponent text, boolean lineStart, boolean value) {
        if (lineStart) {
            if (!value) {
                text.append(subtractionLineStart());
            }
        } else {
            if (value) {
                text.append(addition());
            } else {
                text.append(subtraction());
            }
        }
    }

    public Component getDescription() {
        MutableComponent description = Component.empty().withStyle(ChatFormatting.GRAY);
        boolean lineStart = true;
        if (material.isPresent()) {
            TrimMaterial material = this.material.get().value();
            description.setStyle(material.description().getStyle());
            description.append(getTextForMaterial(material));
            lineStart = false;
        }
        if (glowing.isPresent()) {
            addNecessarySeparator(description, lineStart, glowing.get());
            description.append(GLOWING_TEXT);
            lineStart = false;
        }
        if (hidden.isPresent()) {
            addNecessarySeparator(description, lineStart, hidden.get());
            description.append(HIDDEN_TEXT);
        }
        return description;
    }

    public static void appendTooltip(ItemStack stack, Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        ArmorTrim armorTrim = stack.get(DataComponents.TRIM);
        List<EchoingLayer> echoingLayers = stack.get(ComponentRegistry.ECHOING_LAYERS);
        if (armorTrim != null && echoingLayers != null && !echoingLayers.isEmpty()) {
            Style color = armorTrim.material().value().description().getStyle();
            EchoingLayer echoingLayer = echoingLayers.get(0);
            if (echoingLayer.glowing.isPresent() && echoingLayer.glowing.get()) {
                tooltip.accept(CommonComponents.space().append(SMITHING_TEXT_GLOWING).withStyle(color));
            }
            if (echoingLayer.hidden.isPresent() && echoingLayer.hidden.get()) {
                tooltip.accept(CommonComponents.space().append(SMITHING_TEXT_HIDDEN).withStyle(color));
            }

            if (echoingLayers.size() == 2) {
                tooltip.accept(CommonComponents.space().append(ECHOING_EFFECT_TEXT).setStyle(color));
                MutableComponent description = (MutableComponent) echoingLayers.get(1).getDescription();
                if (echoingLayers.get(1).material().isEmpty()) {
                    description.setStyle(color);
                }
                tooltip.accept(CommonComponents.space().append(CommonComponents.space()).append(description));

            } else if (echoingLayers.size() > 2) {
                tooltip.accept(CommonComponents.space().append(ECHOING_EFFECT_TEXT).withStyle(ChatFormatting.GRAY));
                for (ListIterator<EchoingLayer> it = echoingLayers.listIterator(1); it.hasNext(); ) {
                    EchoingLayer layer = it.next();
                    tooltip.accept(CommonComponents.space().append(CommonComponents.space()).append(layer.getDescription()));
                }
            }
        }
    }

    @Override
    public String toString() {
        return material.map(armorTrimMaterialRegistryEntry -> "EchoingLayer{" +
                "material=" + armorTrimMaterialRegistryEntry.value() +
                ", glowing=" + glowing +
                ", hidden=" + hidden +
                '}').orElseGet(() -> "EchoingLayer{" +
                "glowing=" + glowing +
                ", hidden=" + hidden +
                '}');
    }

    static {
        SMITHING_TEXT_GLOWING = Component.translatable(Util.makeDescriptionId(
            "item", id("smithing_template.glowing")
        ));
        SMITHING_TEXT_HIDDEN = Component.translatable(Util.makeDescriptionId(
                "item", id("smithing_template.glowing")
        ));
        ECHOING_EFFECT_TEXT = Component.translatable(Util.makeDescriptionId("item", id("smithing_template.echoing_effect")));
        GLOWING_TEXT = Component.translatable(Util.makeDescriptionId("echoing_effect", id("glowing")));
        HIDDEN_TEXT = Component.translatable(Util.makeDescriptionId("echoing_effect", id("hidden")));
    }
}
