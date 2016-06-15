package com.aplexos.waterwave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by aplex on 16-6-15.
 */
public class WaterWave extends View {
    Paint paint;

    ArrayList<Raindrop> raindrops;

    public WaterWave(Context context) {
        this(context, null);
    }

    public WaterWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        raindrops = new ArrayList<>();

        new Thread(new Runnable() {

            ArrayList<Raindrop> newRaindrops;

            @Override
            public void run() {
                while (true) {

                    newRaindrops = new ArrayList<Raindrop>();
                    for (int i = 0; i < raindrops.size(); i++) {
                        Raindrop raindrop = raindrops.get(i);
                        if (raindrop.isInRadius()) {
                            raindrop.increaseRadius(3);
                            newRaindrops.add(raindrop);
                        }
                    }
                    raindrops = newRaindrops;

                    postInvalidate();

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Raindrop raindrop : raindrops)
            raindrop.drawRaindrop(canvas, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        raindrops.add(new Raindrop((int)event.getX(), (int)event.getY(), 1));

        return super.onTouchEvent(event);
    }
}

class Raindrop {

    int x;
    int y;
    int currentRadius = 1;
    int maxRadius = 300;
    float rate = 0.03f;

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
        RadialGradient radialGradient = new RadialGradient(x, y, currentRadius, new int[]{0x00f5f5f5, 0x88dfdfdf, 0x00f5f5f5}, new float[]{0.4f, 0.7f, 1.0f }, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(x, y, currentRadius, paint);

        paint.reset();
    }
}
