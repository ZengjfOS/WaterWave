package com.aplexos.waterwave;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

/**
 * Created by aplex on 16-6-18.
 */
public class Raindrop {

    int x;
    int y;
    int currentRadius = 1;
    int maxRadius = 300;
    float rate = 0.03f;
    int[] colors = new int[]{0x00f5f5f5, 0x88dfdfdf, 0x00f5f5f5};
    float[] positions = new float[]{0.4f, 0.7f, 1.0f };

    Raindrop(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.currentRadius = radius;
    }

    public boolean isInRadius() {
        return maxRadius > currentRadius;
    }

    public void increaseRadius(int radius) {
        this.currentRadius = this.currentRadius + radius + (int)(this.currentRadius * rate);
    }

    public void drawRaindrop(Canvas canvas, Paint paint) {
        RadialGradient radialGradient = new RadialGradient(x, y, currentRadius, colors, positions, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(x, y, currentRadius, paint);

        paint.reset();
    }
}
