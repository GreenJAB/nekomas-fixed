package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.util.DamageHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

public class SickleItem extends Item {

    public static final float SPEED = 1.0f;

    public SickleItem(Item.Settings settings) {
        super(settings);
    }
    public static float getAttackDamage(ItemStack stack) {
        float damage = 0f;

        AttributeModifiersComponent comp =
                stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);

        if (comp == null) return 0f;

        for (var entry : comp.modifiers()) {
            if (entry.attribute().equals(EntityAttributes.ATTACK_DAMAGE)) {
                damage += (float) entry.modifier().value();
            }
        }
        return damage;
    }

    long lastHitAt = 0;
    int previousEntityId;
    private int attackCount = 1;




    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof PlayerEntity player)) return;

        long now = attacker.getEntityWorld().getTime();
        boolean inMain = player.getMainHandStack().isOf(stack.getItem());
        boolean inOff  = player.getOffHandStack().isOf(stack.getItem());

        int entityID = target.getId();

        if (inMain && inOff) {
            ItemStack offHandItem = player.getOffHandStack();
            if (!player.getEntityWorld().isClient()) {
                offHandItem.setDamage(offHandItem.getDamage() + 1);

                if (offHandItem.getDamage() >= offHandItem.getMaxDamage()) {
                    player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
                }
            }

            float damageDealt = DamageHelper.onPlayerAttack(player, target, getAttackDamage(stack));

            lastHitAt = now;
            if (entityID == previousEntityId) {
                attackCount++;
            } else {
                attackCount = 1;
                previousEntityId = entityID;
            }

            if (attackCount == 5) {
                float dmg = (float)((3.0 * damageDealt) * 1.5f);
                target.damage((ServerWorld) target.getEntityWorld(),
                        player.getDamageSources().playerAttack(player), dmg);
            }
            else if (attackCount == 6) {
                float dmg = 2.0f * 4.0f * damageDealt;
                target.damage((ServerWorld) target.getEntityWorld(),
                        player.getDamageSources().playerAttack(player), dmg);
                attackCount = 1;
            }
        }
    }

}
