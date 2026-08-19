package net.greenjab.nekomasfixed.registry.other;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public record TippedArrowCustomComponent(List<PotionContents> potionContents) implements TooltipProvider {
        public static TippedArrowCustomComponent EMPTY = new TippedArrowCustomComponent(List.of(PotionContents.EMPTY));

    public static void init() {
        EMPTY = new TippedArrowCustomComponent(
                List.of(
                        PotionContents.EMPTY,
                        Objects.requireNonNull(
                                Potions.HARMING.components()
                                        .get(DataComponents.POTION_CONTENTS)
                        )
                )
        );
    }

    static{
        if(Potions.HARMING.areComponentsBound()){
            EMPTY =  new TippedArrowCustomComponent(NonNullList.of(PotionContents.EMPTY, Objects.requireNonNull(Potions.HARMING.components().get(DataComponents.POTION_CONTENTS))));

        }
    }

    

//    @Override
//    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
//        consumer.accept(Component.nullToEmpty("THIS IS TOOLTIP"));
//        if(!potionContents.isEmpty()){
//            consumer.accept(Component.nullToEmpty("THIS IS 2 TOOLTIP"));
//
//            consumer.accept(Component.translatable("tipped_arrow_custom.contains").withColor(ChatFormatting.DARK_GRAY.ordinal()));
//
//            for(PotionContents potion : potionContents()){
//                for(MobEffectInstance effect : potion.getAllEffects()) {
//                    Holder<MobEffect> mobEffect = effect.getEffect();
//                    int amplifier = effect.getAmplifier();
//                    MutableComponent line = PotionContents.getPotionDescription(mobEffect, amplifier);
//                    if (!effect.endsWithin(20)) {
//                        line = Component.translatable("tipped_arrow_custom.potion_content", line);
//                    }
//
//                    consumer.accept(line.withStyle(mobEffect.value().getCategory().getTooltipFormatting()));
//                }
//            }
//        }
//    }

    public Consumer<Component> getToolTip(Item.TooltipContext context,
                                          Consumer<Component> consumer,
                                          TooltipFlag flag){

        consumer.accept(Component.literal("THIS IS TOOLTIP"));
        consumer.accept(Component.literal("SIZE: " + potionContents.size()));

        for (PotionContents potion : potionContents) {
            consumer.accept(Component.literal("POTION: " + potion));
        }
        return consumer;

    }

    @Override
    public void addToTooltip(
            Item.TooltipContext context,
            Consumer<Component> consumer,
            TooltipFlag flag,
            DataComponentGetter components
    ) {
        consumer.accept(Component.literal("THIS IS TOOLTIP"));
        consumer.accept(Component.literal("SIZE: " + potionContents.size()));

        for (PotionContents potion : potionContents) {
            consumer.accept(Component.literal("POTION: " + potion));
        }
//        return consumer;
    }

    public static final MapCodec<TippedArrowCustomComponent> FULL_CODEC =
            RecordCodecBuilder.mapCodec((i) -> i.group(
                    PotionContents.CODEC.listOf()
                            .optionalFieldOf("potion_contents", List.of())
                            .forGetter(TippedArrowCustomComponent::potionContents)
            ).apply(i, (list) -> {
                NonNullList<PotionContents> contents = NonNullList.create();
                contents.addAll(list);
                return new TippedArrowCustomComponent(contents);
            }));

    public static final Codec<TippedArrowCustomComponent> CODEC =
            FULL_CODEC.codec();

    public static final StreamCodec<RegistryFriendlyByteBuf, TippedArrowCustomComponent> STREAM_CODEC =
            PotionContents.STREAM_CODEC
                    .apply(ByteBufCodecs.list())
                    .map(
                            list -> {
                                NonNullList<PotionContents> contents = NonNullList.create();
                                contents.addAll(list);
                                return new TippedArrowCustomComponent(contents);
                            },
                            TippedArrowCustomComponent::potionContents
                    );
}
