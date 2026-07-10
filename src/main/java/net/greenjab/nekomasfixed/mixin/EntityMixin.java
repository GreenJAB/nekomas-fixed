package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @ModifyVariable(method = "spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At("HEAD"),argsOnly = true)
    private ItemStack replaceSpottedSheepDrops(ItemStack itemStack) {
        if ((Object) this instanceof Sheep sheep) {
            if (((SpottedSheepAccess) sheep).nekomasfixed$isSpotted()) {
                Item replacementItem = this.getSpottedWoolItem(itemStack.getItem());
                if (replacementItem != null) return new ItemStack(replacementItem, itemStack.getCount());
            }
        }
        return itemStack;
    }

    @Unique
    private Item getSpottedWoolItem(Item original) {
        if (original == Items.WHITE_WOOL) return BlockRegistry.WHITE_SPOTTED_WOOL.asItem();
        if (original == Items.ORANGE_WOOL) return BlockRegistry.ORANGE_SPOTTED_WOOL.asItem();
        if (original == Items.MAGENTA_WOOL) return BlockRegistry.MAGENTA_SPOTTED_WOOL.asItem();
        if (original == Items.LIGHT_BLUE_WOOL) return BlockRegistry.LIGHT_BLUE_SPOTTED_WOOL.asItem();
        if (original == Items.YELLOW_WOOL) return BlockRegistry.YELLOW_SPOTTED_WOOL.asItem();
        if (original == Items.LIME_WOOL) return BlockRegistry.LIME_SPOTTED_WOOL.asItem();
        if (original == Items.PINK_WOOL) return BlockRegistry.PINK_SPOTTED_WOOL.asItem();
        if (original == Items.GRAY_WOOL) return BlockRegistry.GRAY_SPOTTED_WOOL.asItem();
        if (original == Items.LIGHT_GRAY_WOOL) return BlockRegistry.LIGHT_GRAY_SPOTTED_WOOL.asItem();
        if (original == Items.CYAN_WOOL) return BlockRegistry.CYAN_SPOTTED_WOOL.asItem();
        if (original == Items.PURPLE_WOOL) return BlockRegistry.PURPLE_SPOTTED_WOOL.asItem();
        if (original == Items.BLUE_WOOL) return BlockRegistry.BLUE_SPOTTED_WOOL.asItem();
        if (original == Items.BROWN_WOOL) return BlockRegistry.BROWN_SPOTTED_WOOL.asItem();
        if (original == Items.GREEN_WOOL) return BlockRegistry.GREEN_SPOTTED_WOOL.asItem();
        if (original == Items.RED_WOOL) return BlockRegistry.RED_SPOTTED_WOOL.asItem();
        if (original == Items.BLACK_WOOL) return BlockRegistry.BLACK_SPOTTED_WOOL.asItem();

        if (original == ItemRegistry.AMBER_WOOL) return BlockRegistry.AMBER_SPOTTED_WOOL.asItem();
        if (original == ItemRegistry.AQUA_WOOL) return BlockRegistry.AQUA_SPOTTED_WOOL.asItem();
        if (original == ItemRegistry.INDIGO_WOOL) return BlockRegistry.INDIGO_SPOTTED_WOOL.asItem();
        if (original == ItemRegistry.MAROON_WOOL) return BlockRegistry.MAROON_SPOTTED_WOOL.asItem();

        return null;
    }
}