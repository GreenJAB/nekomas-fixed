package net.greenjab.nekomasfixed.render.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.block.entity.NautilusBlockEntity;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.entity.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class AnimalTooltipComponent implements TooltipComponent {
    private final AnimalComponent animalComponent;

    public AnimalTooltipComponent(AnimalComponent animalComponent) {
        this.animalComponent = animalComponent;
    }

    @Override
    public int getHeight(TextRenderer textRenderer) {
        return this.getHeight();
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return this.getWidth() ;
    }

    @Override
    public boolean isSticky() {
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
    public void drawItems(TextRenderer textRenderer, int x, int y, int width, int height, DrawContext context) {
            this.drawNonEmptyTooltip(textRenderer, x, y, context);
    }

    private void drawNonEmptyTooltip(TextRenderer textRenderer, int x, int y, DrawContext context) {
        World world = MinecraftClient.getInstance().world;
        if (!animalComponent.animal().isEmpty()) {
            TypedEntityData<EntityType<?>> entityData = animalComponent.animal().get(0).entityData();
            NbtCompound nbtCompound = entityData.copyNbtWithoutId();
            NautilusBlockEntity.IRRELEVANT_ANIMAL_NBT_KEYS.forEach(nbtCompound::remove);
            Entity entity = EntityType.loadEntityWithPassengers(entityData.getType(), nbtCompound, world, SpawnReason.LOAD, entityx -> entityx);
            InventoryScreen.drawEntity(context, x, y-20, x + getWidth(), y + getHeight(), 40, 0.25F, x-20, y+40, (LivingEntity) entity);
        }
    }
}
