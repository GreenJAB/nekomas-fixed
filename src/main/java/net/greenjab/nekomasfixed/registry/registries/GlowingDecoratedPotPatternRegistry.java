package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.greenjab.nekomasfixed.target_access_class.BannerAccess;
import net.greenjab.nekomasfixed.target_access_class.DecoratedPotAccess;
import net.greenjab.nekomasfixed.util.BannerEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class GlowingDecoratedPotPatternRegistry {
    private static final Map<Item, ResourceKey<DecoratedPotPattern>> SHERD_TO_PATTERN = new Hashtable<>(21);
    private static final Item[] VANILLA_SHERDS = new Item[] {
        Items.ANGLER_POTTERY_SHERD,
        Items.ARCHER_POTTERY_SHERD,
        Items.ARMS_UP_POTTERY_SHERD,
        Items.BLADE_POTTERY_SHERD,
        Items.BREWER_POTTERY_SHERD,
        Items.BURN_POTTERY_SHERD,
        Items.DANGER_POTTERY_SHERD,
        Items.EXPLORER_POTTERY_SHERD,
        Items.FLOW_POTTERY_SHERD,
        Items.FRIEND_POTTERY_SHERD,
        Items.GUSTER_POTTERY_SHERD,
        Items.HEART_POTTERY_SHERD,
        Items.HEARTBREAK_POTTERY_SHERD,
        Items.HOWL_POTTERY_SHERD,
        Items.MINER_POTTERY_SHERD,
        Items.MOURNER_POTTERY_SHERD,
        Items.PLENTY_POTTERY_SHERD,
        Items.PRIZE_POTTERY_SHERD,
        Items.SCRAPE_POTTERY_SHERD,
        Items.SHEAF_POTTERY_SHERD,
        Items.SHELTER_POTTERY_SHERD,
        Items.SKULL_POTTERY_SHERD,
        Items.SNORT_POTTERY_SHERD,
    };

    static {
        for (Item sherd : VANILLA_SHERDS) {
            SHERD_TO_PATTERN.put(sherd, registerFromItem(sherd));
        }
    }


    public static void mapSherdToPatternKey(Item sherd, ResourceKey<DecoratedPotPattern> key) {
        SHERD_TO_PATTERN.put(sherd, key);
    }

    private static ResourceKey<DecoratedPotPattern> registerFromItem(Item item) {
        ResourceKey<DecoratedPotPattern> baseKey = DecoratedPotPatterns.getPatternFromItem(item);
        return registerFromBaseKey(baseKey);
    }

    private static ResourceKey<DecoratedPotPattern> registerFromBaseKey(ResourceKey<DecoratedPotPattern> basePatternKey) {
        ResourceLocation glowingPatternID = getIdentifier(basePatternKey);
        ResourceKey<DecoratedPotPattern> glowingPatternKey = getKey(basePatternKey);
        DecoratedPotPattern pattern = new DecoratedPotPattern(glowingPatternID);
        Registry.register(BuiltInRegistries.DECORATED_POT_PATTERN, glowingPatternKey, pattern);
        return glowingPatternKey;
    }

    public static ResourceLocation getIdentifier(ResourceKey<DecoratedPotPattern> basePatternKey) {
        return basePatternKey.location().withSuffix("_pottery_pattern_glowing");
    }

    public static ResourceKey<DecoratedPotPattern> getKey(ResourceKey<DecoratedPotPattern> basePatternKey) {
        ResourceLocation id = basePatternKey.location().withSuffix("_glowing");
        return ResourceKey.create(Registries.DECORATED_POT_PATTERN, id);
    }

    /**
     * Registers a sherd as such, that has a glowing override texture (thus, it requires such under `namespace:entity/decorated_pot/` path)
     * @param sherd Sherd sherd, whose decorated pot has a glowing texture override on face, that is this sherd.
     * @param textureID path after `namespace:entity/decorated_pot/` in which the override texture should reside.
     * @return some string, idk
     */
    public static DecoratedPotPattern register(Item sherd, ResourceLocation textureID) {
        return register(sherd, textureID, new DecoratedPotPattern(textureID));
    }

    public static DecoratedPotPattern register(Item sherd, ResourceLocation textureID, DecoratedPotPattern entry) {
        ResourceKey<DecoratedPotPattern> key = ResourceKey.create(Registries.DECORATED_POT_PATTERN, textureID);
        SHERD_TO_PATTERN.put(sherd, key);
        return Registry.register(BuiltInRegistries.DECORATED_POT_PATTERN, key, entry);
    }

    public static ResourceKey<DecoratedPotPattern> registerFromBaseKey(Item item, ResourceKey<DecoratedPotPattern> basePatternKey) {
        ResourceKey<DecoratedPotPattern> glowingPatternKey = getKey(basePatternKey);
        DecoratedPotPattern pattern = new DecoratedPotPattern(glowingPatternKey.location());
        Registry.register(BuiltInRegistries.DECORATED_POT_PATTERN, glowingPatternKey, pattern);
        SHERD_TO_PATTERN.put(item, glowingPatternKey);
        return glowingPatternKey;
    }

    @Nullable
    public static ResourceKey<DecoratedPotPattern> fromSherd(Item sherd) {
        return SHERD_TO_PATTERN.get(sherd);
    }

    public static void registerGlowingDecoratedPotPatterns() {}

    private static int getDecoratedPotFaceIndex(Direction hitDirection, Direction sideFacing) {
        if (hitDirection.getAxis().equals(Direction.Axis.Y)) {
            return -1;
        } else if (hitDirection.equals(sideFacing)) {
            return DecoratedPotAccess.BACK;
        } else if (hitDirection.getClockWise().equals(sideFacing)) {
            return DecoratedPotAccess.LEFT;
        } else if (hitDirection.getCounterClockWise().equals(sideFacing)) {
            return DecoratedPotAccess.RIGHT;
        } else {
            return DecoratedPotAccess.FRONT;
        }
    }

    static {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            ItemStack heldStack = player.getItemInHand(hand);
            boolean hasGlowInkSac = heldStack.is(Items.GLOW_INK_SAC) && player.mayBuild();
            boolean hasPhantomMembrane = heldStack.is(Items.PHANTOM_MEMBRANE) && player.mayBuild();
            BlockPos pos = hit.getBlockPos();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            // Process Banner use
            if (blockEntity instanceof BannerBlockEntity bannerBlockEntity) {
                if (world.isClientSide) {
                    return !(hasGlowInkSac || hasPhantomMembrane) ? InteractionResult.CONSUME : InteractionResult.SUCCESS;
                }
                BannerEffects effects = ((BannerAccess) bannerBlockEntity).nekomasfixed$getBannerEffects();
                if (hasPhantomMembrane && !effects.isBackgroundHidden()) {
                    world.playSound(null, pos, SoundEventRegistry.PHANTOM_MEMBRANE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    effects.setHideBackground(true);
                    blockEntity.setChanged();
                } else if (hasGlowInkSac && !effects.isGlowing()) {
                    world.playSound(null, pos, SoundEvents.GLOW_INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    effects.setGlowing(true);
                    blockEntity.setChanged();
                } else {
                    return InteractionResult.PASS;
                }
                // Process Decorated Pot use
            } else if (blockEntity instanceof DecoratedPotBlockEntity decoratedPotBlockEntity) {
                if (world.isClientSide) {
                    return !hasGlowInkSac ? InteractionResult.CONSUME : InteractionResult.SUCCESS;
                }
                DecoratedPotAccess nekomasfixedPot = (DecoratedPotAccess) decoratedPotBlockEntity;
                int face = getDecoratedPotFaceIndex(hit.getDirection(), decoratedPotBlockEntity.getDirection());
                if (face != -1 && !nekomasfixedPot.nekomasfixed$getSherdGlow(face)) {
                    world.playSound(null, pos, SoundEvents.GLOW_INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    nekomasfixedPot.nekomasfixed$setSherdGlow(face, true);
                    blockEntity.setChanged();
                } else {
                    return InteractionResult.PASS;
                }
                // If no blocks were procesed, return default ActionResult.PASS
            } else {
                return InteractionResult.PASS;
            }

            // If any block was processed, update stats and block and stuff
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, heldStack);
            }
            world.gameEvent(GameEvent.BLOCK_CHANGE, blockEntity.getBlockPos(), GameEvent.Context.of(player, blockEntity.getBlockState()));
            player.awardStat(Stats.ITEM_USED.get(heldStack.getItem()));

            world.sendBlockUpdated(pos, blockEntity.getBlockState(), blockEntity.getBlockState(), 0);
            if (!player.isCreative()) {
                heldStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        });
    }
}
