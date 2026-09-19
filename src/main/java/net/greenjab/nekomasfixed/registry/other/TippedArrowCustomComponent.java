package net.greenjab.nekomasfixed.registry.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public record TippedArrowCustomComponent(List<PotionContents> potionContents) implements TooltipProvider {
        public static TippedArrowCustomComponent EMPTY = new TippedArrowCustomComponent(List.of(PotionContents.EMPTY));

    public static void init() {
        EMPTY = new TippedArrowCustomComponent(
                List.of(
                        PotionContents.EMPTY,
                        Objects.requireNonNull(Potions.HARMING.components().get(DataComponents.POTION_CONTENTS))
                )
        );
    }

    static{
        if(Potions.HARMING.areComponentsBound()){
            EMPTY =  new TippedArrowCustomComponent(NonNullList.of(PotionContents.EMPTY, Objects.requireNonNull(Potions.HARMING.components().get(DataComponents.POTION_CONTENTS))));
        }
    }


    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        for (PotionContents potion : potionContents){
            consumer.accept(Component.literal(getName(potion)).withColor(potion.getColor()));
        }
    }

    private String getName(PotionContents potion){
        String str = potion.getName("").getContents().toString();
        int start = str.indexOf('\'');
        int end = str.lastIndexOf('\'');
        String newStr = str.substring(start+1, end).replace('_', ' ');
        newStr = newStr.replaceFirst(String.valueOf(newStr.charAt(0)), String.valueOf((char) (newStr.charAt(0)-32)));
        return newStr;
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
