package net.greenjab.nekomasfixed.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class WildfireRenderState extends LivingEntityRenderState {
    public boolean soul;
    public int shields;
    public float shieldAngle;
    public float shieldExtraSpin;
}
