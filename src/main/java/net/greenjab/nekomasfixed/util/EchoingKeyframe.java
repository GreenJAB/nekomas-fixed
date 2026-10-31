package net.greenjab.nekomasfixed.util;

import net.greenjab.nekomasfixed.registry.other.EchoingLayer;
import net.greenjab.nekomasfixed.registry.registries.ComponentRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.ListIterator;

public record EchoingKeyframe(
        boolean glowing,
        boolean hidden,
        RegistryEntry<ArmorTrimMaterial> material
) {
    public static final boolean DEFAULT_GLOWING = false;
    public static final boolean DEFAULT_HIDDEN = false;

    public static EchoingKeyframe build(List<EchoingLayer> echoingLayers, int index) {
        ListIterator<EchoingLayer> iterator = echoingLayers.listIterator(index+1);

        @Nullable Boolean glowing1 = null;
        @Nullable Boolean hidden1 = null;
        @Nullable RegistryEntry<ArmorTrimMaterial> material1 = null;
        while (iterator.hasPrevious()) {
            EchoingLayer layer = iterator.previous();
            int nullCount = 0;

            if (glowing1 == null) if (layer.glowing().isPresent()) {
                glowing1 = layer.glowing().get();
            } else {
                nullCount++;
            }

            if (hidden1 == null) if (layer.hidden().isPresent()) {
                hidden1 = layer.hidden().get();
            } else {
                nullCount++;
            }

            if (material1 == null) if (layer.material().isPresent()) {
                material1 = layer.material().get();
            } else {
                nullCount++;
            }

            if (nullCount == 0) {
                return new EchoingKeyframe(glowing1, hidden1, material1);
            }
        }
        return new EchoingKeyframe(Boolean.TRUE.equals(glowing1), Boolean.TRUE.equals(hidden1), material1);
    }

    public static EchoingKeyframe buildNext(EchoingKeyframe previous, List<EchoingLayer> echoingLayers, int nextIndex) {
        if (nextIndex >= echoingLayers.size()) {
            EchoingLayer layer = echoingLayers.get(0);
            return new EchoingKeyframe(
                    layer.glowing().isPresent()? layer.glowing().get() : DEFAULT_GLOWING,
                    layer.hidden().isPresent()? layer.hidden().get() : DEFAULT_HIDDEN,
                    layer.material().isPresent()? layer.material().get() : null
            );
        }
        EchoingLayer nextLayer = echoingLayers.get(nextIndex);
        return new EchoingKeyframe(
                nextLayer.glowing().isPresent()? nextLayer.glowing().get() : previous.glowing(),
                nextLayer.hidden().isPresent()? nextLayer.hidden().get() : previous.hidden(),
                nextLayer.material().isPresent()? nextLayer.material().get() : previous.material()
        );
    }

    public static EchoingKeyframe buildLast(ItemStack item) {
        if (item.contains(ComponentRegistry.ECHOING_LAYERS)) {
            List<EchoingLayer> echoingLayers = item.get(ComponentRegistry.ECHOING_LAYERS);
            assert echoingLayers != null;
            EchoingKeyframe keyframe =  build(echoingLayers, echoingLayers.size()-1);
            if (keyframe.material == null) {
                ArmorTrim trim = item.get(DataComponentTypes.TRIM);
                assert trim != null;
                return new EchoingKeyframe(keyframe.glowing, keyframe.hidden, trim.material());
            }
            return keyframe;
        }
        ArmorTrim trim = item.get(DataComponentTypes.TRIM);
        assert trim != null;
        return new EchoingKeyframe(DEFAULT_GLOWING, DEFAULT_HIDDEN, trim.material());
    }

    public static Pair<EchoingKeyframe, EchoingKeyframe> buildKeyFrames(ItemStack item, long time) {
        List<EchoingLayer> echoingLayers = item.get(ComponentRegistry.ECHOING_LAYERS);
        assert echoingLayers != null;
        int index = (int) (time % (echoingLayers.size()));

        EchoingKeyframe first = build(echoingLayers, index);
        EchoingKeyframe second = buildNext(first, echoingLayers, index+1);

        ArmorTrim trim = item.get(DataComponentTypes.TRIM);
        assert trim != null;
        if (first.material == null) {
            first = new EchoingKeyframe(first.glowing, first.hidden, trim.material());
        }
        if (second.material == null) {
            second = new EchoingKeyframe(second.glowing, second.hidden, trim.material());
        }

        return new Pair<>(first, second);
    }

    public static EchoingKeyframe of(ArmorTrim armorTrim) {
        return new EchoingKeyframe(false, false, armorTrim.material());
    }

    public boolean equals(Object o) {
        if (!(o instanceof EchoingKeyframe keyframe)) {
            return false;
        } else {
            return this.glowing == keyframe.glowing
                    && this.hidden == keyframe.hidden
                    && this.material.equals(keyframe.material);
        }
    }

    public int hashCode() {
        int i = this.material.hashCode();
        i = 31 * i + (this.glowing ? 1 : 0);
        i = 31 * i + (this.hidden ? 1 : 0);
        return i;
    }

    public ArmorTrim toTrim(ArmorTrim base) {
        return new ArmorTrim(
                material,
                base.pattern()
        );
    }
}
