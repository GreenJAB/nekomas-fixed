package net.greenjab.nekomasfixed.render.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class AnimalTooltipComponent implements ClientTooltipComponent {
    private final AnimalComponent animalComponent;

    public AnimalTooltipComponent(AnimalComponent animalComponent) {
        this.animalComponent = animalComponent;
    }

    @Override
    public int getHeight(@NonNull Font textRenderer) {
        return this.getHeight();
    }

    @Override
    public int getWidth(@NonNull Font textRenderer) {
        return this.getWidth() ;
    }

    @Override
    public boolean showTooltipWithItemInHand() {
        return true;
    }

    private int getHeight() {
        if (animalComponent.animal().isEmpty()) return 0;
        return 3 * 24;
    }

    private int getWidth() {
        if (animalComponent.animal().isEmpty()) return 0;
        return 3 * 24;
    }


    @Override
    public void extractImage(@NonNull Font textRenderer, int x, int y, int width, int height, @NonNull GuiGraphicsExtractor context) {
            this.drawNonEmptyTooltip(x, y, context);
    }

    private void drawNonEmptyTooltip( int x, int y, GuiGraphicsExtractor context) {
        Level level = Minecraft.getInstance().level;
        if (level !=null &&!animalComponent.animal().isEmpty()) {
            TypedEntityData<EntityType<?>> entityData = animalComponent.animal().getFirst().entityData();
            CompoundTag nbtCompound = entityData.copyTagWithoutId();
            AnimalComponent.IRRELEVANT_ANIMAL_NBT_KEYS.forEach(nbtCompound::remove);
            Entity entity = EntityType.loadEntityRecursive(entityData.type(), nbtCompound, level, EntitySpawnReason.LOAD, entityx -> entityx);
            if (entity!=null) {
                entity.tickCount = Math.toIntExact(level.getGameTime());
                float time = System.currentTimeMillis() % (20 * 1000);
                time *= (float) (2 * Math.PI) / (20 * 1000.0f);
                float dx = 10 * (float) (Math.cos(7 * time) + Math.sin(3 * time));
                float dy = 10 * (float) (Math.cos(5 * time) + Math.sin(2 * time));
                InventoryScreen.extractEntityInInventoryFollowsMouse(context, x, y - 20, x + getWidth(), y + getHeight(), 40, 0.25F, x - 15 + dx, y + 30 + dy, (LivingEntity) entity);
                entity.discard();
            }
        }
    }
}
