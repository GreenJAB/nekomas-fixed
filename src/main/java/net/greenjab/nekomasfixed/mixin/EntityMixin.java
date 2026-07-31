package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.BlockRegistry;
import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.greenjab.nekomasfixed.screen.config.ModConfigValues;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        if (original == Items.WOOL.white()) return BlockRegistry.WHITE_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.orange()) return BlockRegistry.ORANGE_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.magenta()) return BlockRegistry.MAGENTA_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.lightBlue()) return BlockRegistry.LIGHT_BLUE_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.yellow()) return BlockRegistry.YELLOW_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.lime()) return BlockRegistry.LIME_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.pink()) return BlockRegistry.PINK_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.gray()) return BlockRegistry.GRAY_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.lightGray()) return BlockRegistry.LIGHT_GRAY_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.cyan()) return BlockRegistry.CYAN_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.purple()) return BlockRegistry.PURPLE_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.blue()) return BlockRegistry.BLUE_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.brown()) return BlockRegistry.BROWN_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.green()) return BlockRegistry.GREEN_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.red()) return BlockRegistry.RED_SPOTTED_WOOL.asItem();
        if (original == Items.WOOL.black()) return BlockRegistry.BLACK_SPOTTED_WOOL.asItem();

        if (original == ItemRegistry.AMBER_WOOL) return BlockRegistry.AMBER_SPOTTED_WOOL.asItem();
        if (original == ItemRegistry.AQUA_WOOL) return BlockRegistry.AQUA_SPOTTED_WOOL.asItem();
        if (original == ItemRegistry.INDIGO_WOOL) return BlockRegistry.INDIGO_SPOTTED_WOOL.asItem();
        if (original == ItemRegistry.MAROON_WOOL) return BlockRegistry.MAROON_SPOTTED_WOOL.asItem();

        return null;
    }

    @Inject(method = "thunderHit", at = @At("HEAD"))
    private void tickThunder(ServerLevel level, LightningBolt lightningBolt, CallbackInfo ci) {
        if (ModConfigValues.enableCopperBuff) {
            if ((Entity)(Object)this instanceof ServerPlayer player) {
                int armor = getCopperArmor(player);
                if (armor > 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.SPEED, 3*armor * 20, armor, false, false, false));
                    player.addEffect(new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, armor, false, false, false));
                }
            }
        }
    }

    @Unique
    private static int getCopperArmor(LivingEntity entity) {
        int i =0;
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(Items.COPPER_BOOTS)) i++;
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(Items.COPPER_LEGGINGS)) i++;
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(Items.COPPER_CHESTPLATE)) i++;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.COPPER_HELMET)) i++;
        return i;
    }
}
