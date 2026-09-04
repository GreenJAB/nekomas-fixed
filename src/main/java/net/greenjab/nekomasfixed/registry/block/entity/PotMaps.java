package net.greenjab.nekomasfixed.registry.block.entity;

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.PotDecorations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotMaps {
    public static Map<Item, String> ITEM_TO_STRING_MAPPING = new HashMap<>();
    public static Map<Item, String> SHERD_TO_STRING_MAPPING = new HashMap<>();
    public static Map<Block, String> POT_FACE_TO_STRING_MAPPING = new HashMap<>();
    public static Map<Item, Integer> ITEM_TO_CHAT_FORMATTING_MAPPING = new HashMap<>();

    public static void register(){
        ITEM_TO_STRING_MAPPING.put(Items.IRON_INGOT, "iron");
        ITEM_TO_STRING_MAPPING.put(Items.GOLD_INGOT, "gold");
        ITEM_TO_STRING_MAPPING.put(Items.DIAMOND, "diamond");
        ITEM_TO_STRING_MAPPING.put(Items.RESIN_BRICK, "resin");
        ITEM_TO_STRING_MAPPING.put(Items.REDSTONE, "redstone");
        ITEM_TO_STRING_MAPPING.put(Items.NETHERITE_INGOT, "netherite");
        ITEM_TO_STRING_MAPPING.put(Items.AMETHYST_SHARD, "amethyst");
        ITEM_TO_STRING_MAPPING.put(Items.COPPER_INGOT, "copper");
        ITEM_TO_STRING_MAPPING.put(Items.LAPIS_LAZULI, "lapis");
        ITEM_TO_STRING_MAPPING.put(Items.QUARTZ, "quartz");
        ITEM_TO_STRING_MAPPING.put(Items.EMERALD, "emerald");

        SHERD_TO_STRING_MAPPING.put(Items.BLADE_POTTERY_SHERD, "blade");
        SHERD_TO_STRING_MAPPING.put(Items.BRICK, "blank"); //fallback

        POT_FACE_TO_STRING_MAPPING.put(Blocks.GLAZED_TERRACOTTA.pick(DyeColor.LIGHT_BLUE), "light_blue");
        POT_FACE_TO_STRING_MAPPING.put(Blocks.BRICKS, "bricks");


        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.IRON_INGOT, 15527148);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.GOLD_INGOT, 14594349);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.DIAMOND, 7269586);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.RESIN_BRICK, 16545810);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.REDSTONE, 9901575);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.NETHERITE_INGOT, 6445145);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.AMETHYST_SHARD, 10116294);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.COPPER_INGOT, 11823181);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.LAPIS_LAZULI,4288151);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.QUARTZ, 14931140);
        ITEM_TO_CHAT_FORMATTING_MAPPING.put(Items.EMERALD, 1155126);
    }

    public static List<String> getSherdTexture(PotDecorations decorations) {
        return List.of(
                SHERD_TO_STRING_MAPPING.getOrDefault(decorations.back().orElse(Items.BRICK), "blank"),
                SHERD_TO_STRING_MAPPING.getOrDefault(decorations.left().orElse(Items.BRICK), "blank"),
                SHERD_TO_STRING_MAPPING.getOrDefault(decorations.right().orElse(Items.BRICK), "blank"),
                SHERD_TO_STRING_MAPPING.getOrDefault(decorations.front().orElse(Items.BRICK), "blank")
        );
    }

    public  enum SpritePart {
        BASE, SIDE;

        public String toString(){
            return this == SpritePart.BASE ? "_base" : "_side";
        }
    }

}
