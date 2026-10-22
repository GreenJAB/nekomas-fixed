package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.greenjab.nekomasfixed.util.SpottedSheepAccess;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Sheep.class)
public abstract class SheepMixin extends Animal implements SpottedSheepAccess {

    protected SheepMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Unique
    private static final EntityDataAccessor<Boolean> SPOTTED = SynchedEntityData.defineId(Sheep.class, EntityDataSerializers.BOOLEAN);

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void initSpottedTracker(SynchedEntityData.Builder entityData, CallbackInfo ci) {
        entityData.define(SPOTTED, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeSpottedNbt(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("Spotted", this.entityData.get(SPOTTED));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readSpottedNbt(ValueInput input, CallbackInfo ci) {
        this.entityData.set(SPOTTED, input.getBooleanOr("Spotted", false));
    }

    @Override
    public boolean nekomasfixed$isSpotted() {
        return this.entityData.get(SPOTTED);
    }

    @Override
    public void nekomasfixed$setSpotted(boolean spotted) {
        this.entityData.set(SPOTTED, spotted);
    }

    @Inject(method = "getColor", at = @At("HEAD"), cancellable = true)
    private void customGetColor(CallbackInfoReturnable<DyeColor> cir) {
        Sheep self = (Sheep)(Object)this;
        int id = self.getEntityData().get(Sheep.DATA_WOOL_ID) & 31;
        cir.setReturnValue(DyeColor.byId(id));
    }

    @Unique
    private static Item getWoolItem(DyeColor color) {
        return switch (color.getId()) {
            case 1 -> Items.WOOL.orange();
            case 2 -> Items.WOOL.magenta();
            case 3 -> Items.WOOL.lightBlue();
            case 4 -> Items.WOOL.yellow();
            case 5 -> Items.WOOL.lime();
            case 6 -> Items.WOOL.pink();
            case 7 -> Items.WOOL.gray();
            case 8 -> Items.WOOL.lightGray();
            case 9 -> Items.WOOL.cyan();
            case 10 -> Items.WOOL.purple();
            case 11 -> Items.WOOL.blue();
            case 12 -> Items.WOOL.brown();
            case 13 -> Items.WOOL.green();
            case 14 -> Items.WOOL.red();
            case 15 -> Items.WOOL.black();
            case 16 -> ItemRegistry.AMBER_WOOL;
            case 17 -> ItemRegistry.AQUA_WOOL;
            case 18 -> ItemRegistry.INDIGO_WOOL;
            case 19 -> ItemRegistry.MAROON_WOOL;
            default -> Items.WOOL.white();
        };
    }

    @Inject(method = "shear", at = @At("HEAD"), cancellable = true)
    public void shear(ServerLevel level, SoundSource soundSource, ItemStack tool, CallbackInfo ci) {
        Sheep self = (Sheep)(Object)this;

        level.playSound(null, this, SoundEvents.SHEEP_SHEAR, soundSource, 1.0F, 1.0F);

        DyeColor color = self.getColor();
        Item itemToDrop = getWoolItem(color);

        for (int i = 0; i < new Random().nextInt(1,3); i++) {
            ItemEntity entity = this.spawnAtLocation(level, itemToDrop.getDefaultInstance(), 1.0F);
            if (entity != null) {
                entity.setDeltaMovement(
                        entity.getDeltaMovement()
                                .add(
                                        (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                                        this.random.nextFloat() * 0.05F,
                                        (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                                )
                );
            }
        }

        //maybe needed


//        self.dropFromShearingLootTable(
//                level,
//                BuiltInLootTables.SHEAR_SHEEP,
//                tool,
//                /* lambda$shear$0 */ (l, drop) -> {
//                    for (int i = 0; i < drop.getCount(); i++) {
//                        ItemEntity entity = this.spawnAtLocation(l, finalItem.asItem().equals(Items.WOOL.white()) ? drop.copyWithCount(1) : finalItem.getDefaultInstance(), 1.0F);
//                        if (entity != null) {
//                            entity.setDeltaMovement(
//                                    entity.getDeltaMovement()
//                                            .add(
//                                                    (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
//                                                    this.random.nextFloat() * 0.05F,
//                                                    (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
//                                            )
//                            );
//                        }
//                    }
//                }
//        );


        self.setSheared(true);
        ci.cancel();
    }

    @Inject(method = "setColor", at = @At("HEAD"), cancellable = true)
    private void customSetColor(DyeColor color, CallbackInfo ci) {
        Sheep self = (Sheep)(Object)this;
        byte current = self.getEntityData().get(Sheep.DATA_WOOL_ID);
        self.getEntityData().set(Sheep.DATA_WOOL_ID, (byte)(current & 224 | color.getId() & 31));
        ci.cancel();
    }

    @Inject(method = "isSheared", at = @At("HEAD"), cancellable = true)
    private void customSheared(CallbackInfoReturnable<Boolean> cir) {
        Sheep self = (Sheep)(Object)this;
        cir.setReturnValue((self.getEntityData().get(Sheep.DATA_WOOL_ID) & 32) != 0);
    }

    @Inject(method = "setSheared", at = @At("HEAD"), cancellable = true)
    private void setShearedCustom(boolean value, CallbackInfo ci) {
        Sheep self = (Sheep)(Object)this;
        byte current = self.getEntityData().get(Sheep.DATA_WOOL_ID);
        if (value) {
            self.getEntityData().set(Sheep.DATA_WOOL_ID, (byte)(current | 32));
        } else {
            self.getEntityData().set(Sheep.DATA_WOOL_ID, (byte)(current & -33));
        }
        ci.cancel();
    }
}