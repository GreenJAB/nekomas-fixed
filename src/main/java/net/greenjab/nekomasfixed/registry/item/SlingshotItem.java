package net.greenjab.nekomasfixed.registry.item;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.SlingshotProjectile;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Predicate;

public class SlingshotItem extends ProjectileWeaponItem {

    public static final Predicate<ItemStack> SLINGSHOT_PROJECTILES = stack -> stack.is(ModTags.SLINGSHOT_PROJECTILES);

    public SlingshotItem(Item.Properties settings) {
        super(settings);
    }

    public static float getPullProgress(int useTicks) {
        float f = useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 1.5F;
        if (f > 1.0F) f = 1.0F;
        return f;
    }

    @Override
    public int getEnchantmentValue() {
        // 1.21.1's ProjectileWeaponItem hardcodes enchantability 1; main's slingshot
        // has none (table stays dark. Force 0 to match.)
        return 0;
    }

    @Override
    public void releaseUsing(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity user, int remainingUseTicks) {
        if (user instanceof Player playerEntity) {
            ItemStack itemStack = playerEntity.getProjectile(stack);
            if (itemStack.isEmpty()) return;
            // Resin clump is 1.21.4+; dropped in this port. Arrows fire as nuggets.
            if (itemStack.is(Items.ARROW)) itemStack = Items.IRON_NUGGET.getDefaultInstance();
            float f = getPullProgress(this.getUseDuration(stack, user) - remainingUseTicks);
            if (f < 0.99) return;
            List<ItemStack> list = draw(stack, itemStack, playerEntity);
            if (level instanceof ServerLevel serverLevel && !list.isEmpty()) this.shoot(
                    serverLevel, playerEntity, playerEntity.getUsedItemHand(), stack, list,
                    f * 3.0F * ((itemStack.is(Items.AMETHYST_SHARD)) ? (1 / 2f) : 2 / 3f),
                    1.0F, f == 1.0F, null);
            level.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS,
                    1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    protected @NonNull Projectile createProjectile(@NonNull Level level, @NonNull LivingEntity shooter, @NonNull ItemStack weaponStack, @NonNull ItemStack projectileStack, boolean critical) {
        return new SlingshotProjectile(level, shooter, projectileStack, weaponStack, NekomasFixed.enchantLevel(weaponStack, "shatter") != 0);
    }

    @Override
    protected void shootProjectile(@NonNull LivingEntity shooter, Projectile projectile, int index, float speed, float divergence, float yaw, LivingEntity target) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + yaw, 0.0F, speed, divergence);
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack, @NonNull LivingEntity user) {
        return 72000;
    }

    @Override
    public @NonNull UseAnim getUseAnimation(@NonNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public @NonNull InteractionResultHolder<ItemStack> use(@NonNull Level level, Player user, @NonNull InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        boolean bl = !user.getProjectile(itemStack).isEmpty();
        if (!user.hasInfiniteMaterials() && !bl) return InteractionResultHolder.fail(itemStack);
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public @NonNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return SLINGSHOT_PROJECTILES;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}