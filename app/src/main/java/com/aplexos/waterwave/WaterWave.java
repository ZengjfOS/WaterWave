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
        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers

                Log.e("WaterWave", "" + event.getPointerCount());
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                }
                Log.e("WaterWave", "" + event.getPointerCount());
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                Log.e("WaterWave", "" + event.getPointerCount());
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    /*
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //raindrops.add(new Raindrop((int)event.getX(), (int)event.getY(), 1));
        for (int i = 0; i < event.getPointerCount(); i++ ) {
            raindrops.add(new Raindrop((int) event.getX(i), (int) event.getY(i), 1));
        }
        Log.e("WaterWave", "" + event.getPointerCount());

        return false;
    }
    */
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
