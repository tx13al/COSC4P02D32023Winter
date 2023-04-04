package com.example.museumapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SecondFloor extends View {
    private Paint paint;

    private final float vertical_wall = 679.f / 12.f,    // feet
                        hori_wall = 513.f / 12.f,
                        wall_diagonal = (float)Math.sqrt(vertical_wall*vertical_wall + hori_wall*hori_wall);

    ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.f;

    private float lastX, lastY;
    //private float offsetX, offsetY;
    //private float startX, startY;
    private float translateX, translateY;


    // Create the pins
    List<MapPin> pinList = null;

    public SecondFloor(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(@NonNull ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                mScaleFactor = Math.max(0.7f, Math.min(mScaleFactor, 5.0f)); // 设置缩放范围为 0.7 到 5
                invalidate();
                return true;
            }

            @Override
            public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(@NonNull ScaleGestureDetector detector) {

            }

        });
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

        final int width = getWidth() - 20,      // screen width and height
                  height = getHeight() - 20;   // reserve boarder
        final float screen_diagonal = (float)Math.sqrt(width*width + height*height);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;

        float top_reserve = 100 * (dpi / 160f),
                bottom_reserve = 150 * (dpi / 160f);

        canvas.translate(translateX, translateY);
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.save();

        float ratio = wall_diagonal / (screen_diagonal * 0.7f);
        float main_wall_starting_X = 16.f/ratio,
                mail_wall_width = 26.75f/ratio,
                main_wall_starting_Y = height - bottom_reserve,      // y coordinate is upside down,
                mail_wall_height = 56.58332f/ratio,

                inner_starting_X = main_wall_starting_X + 6.3333f/ratio,
                inner_width = 14.0833f/ratio,
                inner_starting_Y = main_wall_starting_Y - 13.3333f/ratio,
                inner_height = 28f/ratio,

                widthOfStair = 4f/ratio,

                stair1_starting_X = main_wall_starting_X + mail_wall_width - 4f/ratio,
                stair1_starting_y = main_wall_starting_Y - mail_wall_height,
                stair1_height = 8f/ratio,

                stair2_starting_X = 20f,
                stair2_heigt = 6.1667f/ratio,
                stair2_width = 16.f/ratio,
                stair2_starting_Y = height - 9.25f/ratio - bottom_reserve;


        // outer wall
        canvas.drawLine(main_wall_starting_X, main_wall_starting_Y, main_wall_starting_X, main_wall_starting_Y - mail_wall_height, paint);  // left
        canvas.drawLine(main_wall_starting_X, main_wall_starting_Y - mail_wall_height, main_wall_starting_X + mail_wall_width, main_wall_starting_Y - mail_wall_height, paint); // top
        canvas.drawLine(main_wall_starting_X, main_wall_starting_Y, main_wall_starting_X + mail_wall_width, main_wall_starting_Y, paint);   // bottom
        canvas.drawLine(main_wall_starting_X + mail_wall_width, main_wall_starting_Y, main_wall_starting_X + mail_wall_width, main_wall_starting_Y - mail_wall_height, paint); // right

        // inner rail
        canvas.drawLine(inner_starting_X , inner_starting_Y, inner_starting_X, inner_starting_Y - inner_height, paint); // left
        canvas.drawLine(inner_starting_X , inner_starting_Y, inner_starting_X + inner_width, inner_starting_Y, paint);  // bottom
        canvas.drawLine(inner_starting_X + inner_width, inner_starting_Y, inner_starting_X + inner_width, inner_starting_Y - inner_height, paint);  // right
        canvas.drawLine(inner_starting_X , inner_starting_Y - inner_height, inner_starting_X + inner_width, inner_starting_Y - inner_height, paint);    // top

        // stair 1
        paint.setStrokeWidth(2f);
        canvas.drawLine(stair1_starting_X, stair1_starting_y, stair1_starting_X, stair1_starting_y + stair1_height, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height, stair1_starting_X + widthOfStair, stair1_starting_y + stair1_height, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y + widthOfStair, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y, paint);

        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + 0.73f/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + 0.73f/ratio, stair1_starting_y, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + 2*0.73f/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + 2*0.73f/ratio, stair1_starting_y, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + 3*0.73f/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + 3*0.73f/ratio, stair1_starting_y, paint);

        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 0.73f/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 2*0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 2*0.73f/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 3*0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 3*0.73f/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 4*0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 4*0.73f/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 5*0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 5*0.73f/ratio, paint);


        // stair 2
        paint.setStrokeWidth(5f);
        canvas.drawLine(stair2_starting_X, stair2_starting_Y, stair2_width, stair2_starting_Y, paint);  // bottom
        canvas.drawLine(stair2_starting_X, stair2_starting_Y, stair2_starting_X, stair2_starting_Y - stair2_heigt, paint);  // left
        canvas.drawLine(stair2_starting_X, stair2_starting_Y - stair2_heigt, stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt, paint);    // top_left
        canvas.drawLine(stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt, stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt + 1.1f/ratio, paint);    // top_mid_left
        canvas.drawLine(stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 7.3333f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, paint); // top_mid_width
        canvas.drawLine(stair2_starting_X + widthOfStair + 7.3333f/ratio , stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 7.3333f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio - 2.0833f/ratio, paint);  // top_mid_right
        canvas.drawLine(stair2_starting_X + widthOfStair + 7.3333f/ratio , stair2_starting_Y - stair2_heigt + 1.1f/ratio - 2.0833f/ratio, widthOfStair + 7.3333f/ratio + 4.42f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio - 2.0833f/ratio, paint);   // top_right

        paint.setStrokeWidth(2f);
        canvas.drawLine(stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 0.73f/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 2*0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 2*0.73f/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 3*0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 3*0.73f/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 4*0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 4*0.73f/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 5*0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 5*0.73f/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 6*0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 6*0.73f/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 7*0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 7*0.73f/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 8*0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 8*0.73f/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 9*0.73f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 9*0.73f/ratio, stair2_starting_Y, paint);
    }

    // Initiate map pins, but not display on the map. Only create them in the memory
    public void createPins(List<MapPin> list, ViewGroup parentView) {
        this.pinList = list;
        // Update the view with the new list here

        if (pinList != null) {
            for (MapPin pin : pinList) {
                pin.create(parentView);
            }
        }
    }

    // Set all pins in the second floor pin list to be invisible
    public void pinInvisible() {
        if (pinList != null) {
            for (MapPin pin : pinList) {
                pin.setInvisible();
            }
        }
    }

    // Set all pins in the second floor pin list to be visible
    public void pinVisible() {
        if (pinList != null) {
            for (MapPin pin : pinList) {
                pin.setVisible();
            }
        }
    }
}
