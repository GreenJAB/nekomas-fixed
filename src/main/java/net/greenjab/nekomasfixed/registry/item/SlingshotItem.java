package net.greenjab.nekomasfixed.registry.item;

import java.util.List;
import java.util.function.Predicate;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.entity.SlingshotProjectile;
import net.greenjab.nekomasfixed.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SlingshotItem extends ProjectileWeaponItem {

    public SlingshotItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public boolean releaseUsing(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player playerEntity)) {
            return false;
        } else {
            ItemStack itemStack = playerEntity.getProjectile(stack);
            if (itemStack.isEmpty()) return false;
            if (itemStack.is(Items.ARROW)) itemStack = Items.IRON_NUGGET.getDefaultInstance();
            float f = getPullProgress(this.getUseDuration(stack, user) - remainingUseTicks);
            if (f < 0.99) return false;
            List<ItemStack> list = draw(stack, itemStack, playerEntity);
            if (level instanceof ServerLevel serverLevel && !list.isEmpty()) this.shoot(
                    serverLevel, playerEntity, playerEntity.getUsedItemHand(), stack, list,
                    f * 3.0F*((itemStack.is(Items.AMETHYST_SHARD)||itemStack.is(Items.RESIN_CLUMP))?(1/2f):2/3f),
                    1.0F, f == 1.0F, null);
            level.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS,
                    1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
            return true;
        }
    }

    @Override
    protected @NonNull Projectile createProjectile(@NonNull Level level, @NonNull LivingEntity shooter, @NonNull ItemStack weaponStack, @NonNull ItemStack projectileStack, boolean critical) {
        return new SlingshotProjectile(level, shooter, projectileStack, weaponStack, NekomasFixed.enchantLevel(weaponStack, "shatter")!=0);
    }

    @Override
    protected void shootProjectile(@NonNull LivingEntity shooter, Projectile projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + yaw, 0.0F, speed, divergence);
    }

    public static float getPullProgress(int useTicks) {
        float f = useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 1.5F;
        if (f > 1.0F) f = 1.0F;
        return f;
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack, @NonNull LivingEntity user) {
        return 72000;
    }

    @Override
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player user, @NonNull InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        boolean bl = !user.getProjectile(itemStack).isEmpty();
        if (!user.hasInfiniteMaterials() && !bl) return InteractionResult.FAIL;
        user.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    public static final Predicate<ItemStack> SLINGSHOT_PROJECTILES =stack -> stack.is(ModTags.SLINGSHOT_PROJECTILES);

    @Override
    public @NonNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return SLINGSHOT_PROJECTILES;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}
