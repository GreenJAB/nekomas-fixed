package net.greenjab.nekomasfixed.registry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.world.World;

public class CustomMinecartEntity extends MinecartEntity {
    public float wheelRotation;
    public CustomMinecartEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        double speed = this.getVelocity().horizontalLength();
        this.wheelRotation += (float)(speed * 25.0);
        this.wheelRotation %= (float) (Math.PI * 2);

    }
}
