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

                    newRaindrops = new ArrayList<>();
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

        for (int i = 0; i < event.getPointerCount(); i++ )
            raindrops.add(new Raindrop((int) event.getX(i), (int) event.getY(i), 1));

        //raindrops.add(new Raindrop((int) event.getX(), (int) event.getY(), 1));

        /*
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch(action) {
            case MotionEvent.ACTION_DOWN :
                break;
            case MotionEvent.ACTION_MOVE :
                for (int i = 0; i < event.getPointerCount(); i++ )
                    raindrops.add(new Raindrop((int) event.getX(i), (int) event.getY(i), 1));

                break;
            case MotionEvent.ACTION_POINTER_DOWN :
                break;
            case MotionEvent.ACTION_POINTER_UP :
                for (int i = 0; i < event.getPointerCount(); i++ )
                    raindrops.add(new Raindrop((int) event.getX(i), (int) event.getY(i), 1));

                break;
            case MotionEvent.ACTION_UP :
               raindrops.add(new Raindrop((int) event.getX(), (int) event.getY(), 1));
                break;
        }
        */
        return true;
    }
}

