package net.greenjab.nekomasfixed.render.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.registry.other.AnimalComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AnimalTooltipComponent implements ClientTooltipComponent {
    private static final int SIZE = 3 * 24;
    private final LivingEntity entity;

    public AnimalTooltipComponent(AnimalComponent animalComponent) {
        Level level = Minecraft.getInstance().level;
        if (level != null && !animalComponent.animal().isEmpty()
                && animalComponent.animal().getFirst().loadEntity(level) instanceof LivingEntity living) {
            entity = living;
            entity.tickCount = Math.toIntExact(level.getGameTime());
        } else {
            entity = null;
        }
    }

    @Override
    public int getHeight() {
        return entity == null ? 0 : SIZE;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return entity == null ? 0 : SIZE;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics) {
        if (entity == null) return;
        guiGraphics.flush();
        float time = System.currentTimeMillis() % (20 * 1000) * (float) (2 * Math.PI) / (20 * 1000.0f);
        float dx = 10 * (float) (Math.cos(7 * time) + Math.sin(3 * time));
        float dy = 10 * (float) (Math.cos(5 * time) + Math.sin(2 * time));
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x, y - 20, x + SIZE, y + SIZE, 40, 0.25F,
                x - 15 + dx, y + 30 + dy, entity);
    }
}
