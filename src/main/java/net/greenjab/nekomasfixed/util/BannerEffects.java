package net.greenjab.nekomasfixed.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.BitSet;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;

public class BannerEffects implements TooltipProvider {
    public static final Codec<BannerEffects> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.BOOL.optionalFieldOf("glowing", false).forGetter(BannerEffects::isGlowing),
            Codec.BOOL.optionalFieldOf("hide_background", false).forGetter(BannerEffects::isBackgroundHidden)
    ).apply(builder, BannerEffects::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BannerEffects> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE, BannerEffects::getFlags,
            BannerEffects::new
    );

    public static BannerEffects empty() {
        return new BannerEffects((byte)0);
    }

    public static final BannerEffects EMPTY = empty();

    private static final int GLOWING = 0;
    private static final int HIDE_BACKGROUND = 1;

    private final BitSet flags;

    public BannerEffects(boolean glowing, boolean hideBackground) {
        flags = new BitSet(2);
        setGlowing(glowing);
        setHideBackground(hideBackground);
    }

    public BannerEffects(byte flags) {
        this.flags = BitSet.valueOf(new byte[] {flags});
    }

    public boolean isGlowing() {
        return flags.get(GLOWING);
    }

    public boolean isBackgroundHidden() {
        return flags.get(HIDE_BACKGROUND);
    }

    public void setGlowing(boolean value) {
        flags.set(GLOWING, value);
    }

    public void setHideBackground(boolean value) {
        flags.set(HIDE_BACKGROUND, value);
    }

    public byte getFlags() {
        byte[] bytes = flags.toByteArray();
        return bytes.length > 0 ? bytes[0] : (byte)0;
    }

    @Override
    public void addToTooltip(Item.@NotNull TooltipContext context, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag type) {
        if (isGlowing()) {
            tooltip.accept(Component.translatable("block.minecraft.banner.nekomasfixed.glowing").withStyle(ChatFormatting.WHITE));
        }
        if (isBackgroundHidden()) {
            tooltip.accept(Component.translatable("block.minecraft.banner.nekomasfixed.hide_background").withStyle(ChatFormatting.WHITE));
        }
    }
}
