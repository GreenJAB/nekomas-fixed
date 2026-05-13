package net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider;

import net.greenjab.nekomasfixed.registry.registries.CustomTrackedDataHandlerRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class SuspiciousSpiderEntity extends SpiderEntity {
    public SuspiciousSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }
    public static final TrackedData<StatusEffectInstance> EFFECT_ON_HIT = DataTracker.registerData(SuspiciousSpiderEntity.class, CustomTrackedDataHandlerRegistry.STATUS_EFFECT_INSTANCE);
    public static final TrackedData<StatusEffectInstance> EFFECT_ON_INIT = DataTracker.registerData(SuspiciousSpiderEntity.class, CustomTrackedDataHandlerRegistry.STATUS_EFFECT_INSTANCE);
    int tickOfSpider=0;

    public static DefaultAttributeContainer.Builder createPoisenousSpiderAttributes() {return HostileEntity.createHostileAttributes().add(EntityAttributes.MAX_HEALTH, 16.0F).add(EntityAttributes.MOVEMENT_SPEED, 0.3F);}

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(EFFECT_ON_INIT, instanceFromString("effect.minecraft.strength x 2, Duration: 1200"));
        builder.add(EFFECT_ON_HIT, instanceFromString("effect.minecraft.strength x 2, Duration: 1200"));
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData);
        this.dataTracker.set(EFFECT_ON_HIT, this.getRandomStatusEffectOnHit());
        this.dataTracker.set(EFFECT_ON_INIT, this.getRandomStatusEffectONInitialize());
        return data;
    }


    @Override
    public void tick() {
        super.tick();

        this.tickOfSpider++;

        if (this.tickOfSpider % 100 == 0) {
            this.addStatusEffect(this.dataTracker.get(EFFECT_ON_INIT));
        }
    }


    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        float f = (float)this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
        ItemStack itemStack = this.getWeaponStack();
        DamageSource damageSource = itemStack.getDamageSource(this, () -> this.getDamageSources().mobAttack(this));
        f = EnchantmentHelper.getDamage(world, itemStack, target, damageSource, f);
        f += itemStack.getItem().getBonusAttackDamage(target, f, damageSource);
        Vec3d vec3d = target.getVelocity();
        boolean bl = target.damage(world, damageSource, f);
        if (bl) {
            this.knockbackTarget(target, this.getAttackKnockbackAgainst(target, damageSource), vec3d);
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)target;
                livingEntity.addStatusEffect(this.dataTracker.get(EFFECT_ON_HIT));
                itemStack.postHit(livingEntity, this);
            }

            EnchantmentHelper.onTargetDamaged(world, target, damageSource);
            this.onAttacking(target);
            this.playAttackSound();
        }

        this.useAttackEnchantmentEffects();
        return bl;
    }


    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putString("EffectOnHit", this.dataTracker.get(EFFECT_ON_HIT).toString());
        view.putString("EffectOnInit", this.dataTracker.get(EFFECT_ON_INIT).toString());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(EFFECT_ON_HIT,instanceFromString(view.getString("EffectOnHit", "effect.minecraft.strength x 2, Duration: 1200")));
        this.dataTracker.set(EFFECT_ON_INIT, instanceFromString(view.getString("EffectOnInit", "effect.minecraft.strength x 2, Duration: 1200")));
    }

    private StatusEffectInstance getRandomStatusEffectONInitialize(){
        Random random = new Random();
        int randint = random.nextInt(0, 4);
        int dur = StatusEffectInstance.INFINITE;
        return switch (randint) {
            case 0 -> new StatusEffectInstance(StatusEffects.STRENGTH, dur, 1, false, false);
            case 1 -> new StatusEffectInstance(StatusEffects.INVISIBILITY, dur, 1, false, false);
            case 2 -> new StatusEffectInstance(StatusEffects.SPEED, dur, 1, false, false);
            default -> new StatusEffectInstance(StatusEffects.REGENERATION, dur, 1, false, false);
        };
    }

    private StatusEffectInstance getRandomStatusEffectOnHit(){
        Random random = new Random();
        int randint = random.nextInt(0, 4);
        int dur = 20*15;
        return switch (randint) {
            case 0 -> new StatusEffectInstance(StatusEffects.WEAKNESS, dur, 1, false, true);
            case 1 -> new StatusEffectInstance(StatusEffects.BLINDNESS, dur, 1, false, true);
            case 2 -> new StatusEffectInstance(StatusEffects.POISON, dur, 1, false, true);
            default -> new StatusEffectInstance(StatusEffects.WITHER, dur, 1, false, true);
        };
    }

    public static StatusEffectInstance instanceFromString(String string) {
        try {
            String[] parts = string.split(", ");
            String first = parts[0];

            String effectId;
            int amplifier = 0;

            if (first.contains(" x ")) {
                String[] effectParts = first.split(" x ");
                effectId = effectParts[0];
                amplifier = Integer.parseInt(effectParts[1]) - 1;
            } else {
                effectId = first;
            }

            int duration = 0;
            boolean ambient = false;
            boolean particles = true;
            boolean showIcon = true;

            for (String part : parts) {
                if (part.startsWith("Duration: ")) {
                    String value = part.replace("Duration: ", "");
                    if (value.equalsIgnoreCase("infinite")) {
                        duration = StatusEffectInstance.INFINITE;
                    } else {
                        duration = Integer.parseInt(value);
                    }
                } else if (part.startsWith("Ambient: ")) {
                    ambient = Boolean.parseBoolean(part.replace("Ambient: ", ""));
                } else if (part.startsWith("Particles: ")) {
                    particles = Boolean.parseBoolean(part.replace("Particles: ", ""));
                } else if (part.startsWith("Show Icon: ")) {
                    showIcon = Boolean.parseBoolean(part.replace("Show Icon: ", ""));
                }
            }

            Identifier id = Identifier.of(effectId.replace("effect.", "").replace('.', ':'));

            StatusEffect effect = Registries.STATUS_EFFECT.get(id);

            if (effect == null) {
                System.out.println("Unknown effect: " + id);
                return new StatusEffectInstance(StatusEffects.STRENGTH, 200);
            }

            RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(effect);
            return new StatusEffectInstance(entry, duration, amplifier, ambient, particles, showIcon);
        } catch (Exception e) {
            e.printStackTrace();

            return new StatusEffectInstance(
                    StatusEffects.STRENGTH,
                    200
            );
        }
    }

}
