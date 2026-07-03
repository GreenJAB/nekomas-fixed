package net.greenjab.nekomasfixed.util;

import net.minecraft.util.math.MathHelper;

public class SoupCauldronAnimator  {
    private boolean started;
    private float progress;
    private float lastProgress;

    public void step() {
        this.lastProgress = this.progress;
        float f = 0.03F;
        if (!this.started && this.progress > 0.0F) {
            this.progress = Math.max(this.progress - f, 0.0F);
        } else if (this.started && this.progress < 1.0F) {
            this.progress = Math.min(this.progress + f, 1.0F);
        }
    }

    public float getProgress(float tickProgress) {
        return MathHelper.lerp(tickProgress, this.lastProgress, this.progress);
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
