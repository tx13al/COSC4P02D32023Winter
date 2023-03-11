package com.example.museumapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class FirstFloor extends View {

    private Paint paint;
    private int width;
    private int height;

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.f;


    private float lastX, lastY;
    private float offsetX, offsetY;
    private float startX= 300, startY = 100;
    private float translateX, translateY;




    public FirstFloor(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setColor(Color.rgb(0,24,69));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                float dx = x - lastX;
                float dy = y - lastY;
                translateX += dx;
                translateY += dy;
                invalidate();
                lastX = x;
                lastY = y;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        offsetX = lastX - startX;
        offsetY = lastY - startY;
        canvas.translate(translateX, translateY);
        width = getWidth();
        height = getHeight();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.save();

        canvas.drawLine(450, 200,450+110, 200, paint);

        canvas.drawLine(450+110, 200, 450+110+110, 200, paint);

        canvas.drawLine(450, 200, 450, 200+239, paint);
        canvas.drawLine(450+110+110, 200, 450+110+110, 200+239, paint);
        canvas.drawLine(450+(110+110)/2, 200, 450+(110+110)/2, 200+239, paint);
        canvas.drawLine(450, 200+239,450+220, 200+239, paint);
        canvas.drawLine(450, 200+239,450, 200+239+122, paint);
        canvas.drawLine(450, 200+239+122,450, 200+239+122+144, paint);
        //canvas.drawLine(450, 200+239+122+144,450, 200+239+122+144+837, paint);
        canvas.drawLine(450-91, 200+239+122+144+837,450-91, 200+239+122+144+837+266, paint);
        canvas.drawLine(450-91, 200+239+122+144+837,450, 200+239+122+144+837, paint);
        canvas.drawLine(450-91, 200+239+122+144+837+266,450-91+568, 200+239+122+144+837+266, paint);
        canvas.drawLine(450-91+568, 200+239+122+144+837,450-91+568, 200+239+122+144+837+266, paint);
        canvas.drawLine(450-91+568, 200+239+122+144+837,450-91+568-317, 200+239+122+144+837, paint);
        canvas.drawLine(450-91+568-317, 200+239+122+144+837-239,450-91+568-317, 200+239+122+144+837, paint);
        canvas.drawLine(450-91+568-317+75, 200+239+122+144+837-239-81,450-91+568-317+75, 200+239+122+144+837-239, paint);
        canvas.drawLine(450-91+568-317, 200+239+122+144+837-239-81,450-91+568-317, 200+239+122+144+837-239-81-236, paint);
        canvas.drawLine(450-91+568-317, 200+239+122+144+837-239-81-236,450+110+110, 200+239+122+144+837-239-81-236, paint);
        canvas.drawLine(450+110+110, 200+239+122+144+837-239-81-236,450+110+110, 200+239+122+144+81, paint);
        canvas.drawLine(450+110+110+60, 200+239+122+144,450+110+110+60, 200+239+122+144+81, paint);
        canvas.drawLine(450+110+110, 200+239+122+144,450+110+110, 200+239, paint);
        canvas.drawLine(450+110+110, 200+239+122+144,450+110+110+60, 200+239+122+144, paint);
        canvas.drawLine(450+110+110, 200+239+122+144+81,450+110+110+60, 200+239+122+144+81, paint);
        canvas.drawLine(450-91+568-317+75, 200+239+122+144+837-239-81,450-91+568-317, 200+239+122+144+837-239-81, paint);
        canvas.drawLine(450-91+568-317+75, 200+239+122+144+837-239,450-91+568-317, 200+239+122+144+837-239, paint);
        canvas.drawLine(450,200+239+122+76+68+52,450-120,200+239+122+76+68+52,paint);
        canvas.drawLine(450-120,200+239+122+76+68+52+195,450-120,200+239+122+76+68+52,paint);
        canvas.drawLine(450,200+239+122+76+68+52+195,450-120,200+239+122+76+68+52+195,paint);
        canvas.drawLine(450,200+239+122+76+68+52+195,450,200+239+122+76+68+52+195+590,paint);
        canvas.drawLine(450,200+239+122,450+61,200+239+122,paint);
        canvas.drawLine(450+61,200+239+122,450+61,200+239,paint);
        canvas.drawLine(450+61,200+239+122+144,450+61,200+239+122,paint);
        canvas.drawLine(450+61,200+239+122+144,450,200+239+122+144,paint);
        canvas.drawLine(450+110+110-133,200+239+110,450+110+110,200+239+110,paint);
        canvas.drawLine(450+110+110-133,200+239+110+47+11,450+110+110,200+239+110+47+11,paint);
        canvas.drawLine(450+110+110-133,200+239+122+144,450+110+110,200+239+122+144,paint);
        canvas.drawLine(450-120,200+239+122+144+52+78,450,200+239+122+144+52+78,paint);
        canvas.drawLine(450-120,200+239+122+144+52+78+39,450,200+239+122+144+52+78+39,paint);
        canvas.drawLine(450,200+239+122+144+52+78,450,200+239+122+144+52,paint);
        canvas.drawLine(450,200+239+122+144+52+78+39,450,200+239+122+144+52+78+39+78,paint);
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 5.0f)); // 设置缩放范围为 0.1 到 10
            invalidate();
            return true;
        }
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }
}