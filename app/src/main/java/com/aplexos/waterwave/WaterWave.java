package com.aplexos.waterwave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aplex on 16-6-15.
 */
public class WaterWave extends View implements View.OnClickListener {
    Paint paint;
    int radius = 1;
    float rate = 0.03f;
    public WaterWave(Context context) {
        this(context, null);
    }

    public WaterWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (radius > 750 ) {
                        radius = 1;
                    } else {
                        radius += 3;
                        radius = radius + (int)(radius * rate);
                    }

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
        RadialGradient radialGradient = new RadialGradient(getMeasuredWidth()/2, getMeasuredHeight()/2, radius, new int[]{0x00f5f5f5, 0x88dfdfdf, 0x00f5f5f5}, new float[]{0.4f, 0.7f, 1.0f }, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, radius, paint);

        paint.reset();
    }

    @Override
    public void onClick(View v) {

    }
}
