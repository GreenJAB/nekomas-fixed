package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

public record EchoingLayer(
        Optional<Boolean> glowing,
        Optional<Boolean> hidden,
        Optional<RegistryEntry<ArmorTrimMaterial>> material
) {
    private static final Text SMITHING_TEXT_GLOWING;
    private static final Text SMITHING_TEXT_HIDDEN;
    public static final Text ECHOING_EFFECT_TEXT;
    private static final Text GLOWING_TEXT;
    private static final Text HIDDEN_TEXT;

    public static final Codec<EchoingLayer> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            Codec.BOOL.optionalFieldOf("glowing").forGetter(EchoingLayer::glowing),
            Codec.BOOL.optionalFieldOf("hidden").forGetter(EchoingLayer::hidden),
            ArmorTrimMaterial.ENTRY_CODEC.optionalFieldOf("material").forGetter(EchoingLayer::material)
    ).apply(builder, EchoingLayer::new));

    public static final PacketCodec<RegistryByteBuf, EchoingLayer> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.optional(PacketCodecs.BOOLEAN), EchoingLayer::glowing,
            PacketCodecs.optional(PacketCodecs.BOOLEAN), EchoingLayer::hidden,
            PacketCodecs.optional(ArmorTrimMaterial.ENTRY_PACKET_CODEC), EchoingLayer::material,
            EchoingLayer::new
    );

    public static List<EchoingLayer> emptyList() {
        ArrayList<EchoingLayer> list = new ArrayList<>();
        list.add(new EchoingLayer(Optional.empty(), Optional.empty(), Optional.empty()));
        return list;
    }

    public static Identifier id(String name) {
        return NekomasFixed.id(name);
    }

    public static MutableText addition() {
        return Text.literal(" + ");
    }

    public static MutableText subtraction() {
        return Text.literal(" - ");
    }

    public static MutableText subtractionLineStart() {
        return Text.literal("- ");
    }

    public static Text getTextForMaterial(ArmorTrimMaterial material) {
        return Text.translatable(Util.createTranslationKey("echoing_effect", id("material")), material.description());
    }

    public static void addNecessarySeparator(MutableText text, boolean lineStart, boolean value) {
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

    public Text getDescription() {
        MutableText description = Text.empty().formatted(Formatting.GRAY);
        boolean lineStart = true;
        if (material.isPresent()) {
            ArmorTrimMaterial material = this.material.get().value();
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

    public static void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipType type, List<Text> tooltip) {
        ArmorTrim armorTrim = stack.get(DataComponentTypes.TRIM);
        List<EchoingLayer> echoingLayers = stack.get(ComponentRegistry.ECHOING_LAYERS);
        if (armorTrim != null && echoingLayers != null && !echoingLayers.isEmpty()) {
            Style color = armorTrim.material().value().description().getStyle();
            EchoingLayer echoingLayer = echoingLayers.get(0);
            if (echoingLayer.glowing.isPresent() && echoingLayer.glowing.get()) {
                tooltip.add(ScreenTexts.space().append(SMITHING_TEXT_GLOWING).fillStyle(color));
            }
            if (echoingLayer.hidden.isPresent() && echoingLayer.hidden.get()) {
                tooltip.add(ScreenTexts.space().append(SMITHING_TEXT_HIDDEN).fillStyle(color));
            }

            if (echoingLayers.size() == 2) {
                tooltip.add(ScreenTexts.space().append(ECHOING_EFFECT_TEXT).setStyle(color));
                MutableText description = (MutableText) echoingLayers.get(1).getDescription();
                if (echoingLayers.get(1).material().isEmpty()) {
                    description.setStyle(color);
                }
                tooltip.add(ScreenTexts.space().append(ScreenTexts.space()).append(description));

            } else if (echoingLayers.size() > 2) {
                tooltip.add(ScreenTexts.space().append(ECHOING_EFFECT_TEXT).formatted(Formatting.GRAY));
                for (ListIterator<EchoingLayer> it = echoingLayers.listIterator(1); it.hasNext(); ) {
                    EchoingLayer layer = it.next();
                    tooltip.add(ScreenTexts.space().append(ScreenTexts.space()).append(layer.getDescription()));
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
        SMITHING_TEXT_GLOWING = Text.translatable(Util.createTranslationKey(
            "item", id("smithing_template.glowing")
        ));
        SMITHING_TEXT_HIDDEN = Text.translatable(Util.createTranslationKey(
                "item", id("smithing_template.glowing")
        ));
        ECHOING_EFFECT_TEXT = Text.translatable(Util.createTranslationKey("item", id("smithing_template.echoing_effect")));
        GLOWING_TEXT = Text.translatable(Util.createTranslationKey("echoing_effect", id("glowing")));
        HIDDEN_TEXT = Text.translatable(Util.createTranslationKey("echoing_effect", id("hidden")));
    }
}
