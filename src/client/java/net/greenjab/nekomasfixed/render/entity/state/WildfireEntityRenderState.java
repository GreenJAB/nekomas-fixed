package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class WildfireEntityRenderState extends LivingEntityRenderState {
    public boolean soul;
    public int shields;
    public float shieldAngle;
    public float shieldExtraSpin;
}
