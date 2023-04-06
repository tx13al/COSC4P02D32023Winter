package com.example.museumapp.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.museumapp.R;
import com.example.museumapp.objects.MapPin;
import com.example.museumapp.objects.ShowCase;

import java.util.ArrayList;
import java.util.List;

public class SecondFloor extends View implements Floor {
    private Paint paint;
    ArrayList<Edge> outerEdges;
    ArrayList<Edge> innerEdges;
    private final ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.f;
    private float lastX, lastY;
    private float translateX, translateY;

    // Create the pins
    ArrayList<MapPin> pinList = null;


    // The actual length of museum wall
    private final float vertical_wall = 679.f / 12.f,    // feet
            hori_wall = 513.f / 12.f,
            wall_diagonal = (float)Math.sqrt(vertical_wall*vertical_wall + hori_wall*hori_wall);


    // Device Screen dpi
    private final DisplayMetrics metrics = getResources().getDisplayMetrics();
    private final int dpi = metrics.densityDpi;       // get device screen dpi
    private final float top_reserve = 100 * (dpi / 160f),
                        bottom_reserve = 150 * (dpi / 160f);


    // Screen properties, width height and diagonal
    private final int width = metrics.widthPixels - 20,      // screen width and height
            height = metrics.heightPixels - 20;   // reserve boarder
    private final float screen_diagonal = (float)Math.sqrt(width*width + height*height);


    // The ratio of actual length and length on screen
    private final float ratio = wall_diagonal / (screen_diagonal * 0.7f);


    // The following are coordinates:
    private final float main_wall_starting_X = 16.f/ratio,
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

    private void initEdges() {
        outerEdges = new ArrayList<>();
        innerEdges = new ArrayList<>();

        // main outer wall
        Edge outer_left = new Edge(main_wall_starting_X, main_wall_starting_Y, main_wall_starting_X, main_wall_starting_Y - mail_wall_height);
        Edge outer_top = new Edge(main_wall_starting_X, main_wall_starting_Y - mail_wall_height, main_wall_starting_X + mail_wall_width, main_wall_starting_Y - mail_wall_height);
        Edge outer_bottom = new Edge(main_wall_starting_X, main_wall_starting_Y, main_wall_starting_X + mail_wall_width, main_wall_starting_Y);
        Edge outer_right = new Edge(main_wall_starting_X + mail_wall_width, main_wall_starting_Y, main_wall_starting_X + mail_wall_width, main_wall_starting_Y - mail_wall_height);

        outerEdges.add(outer_left);
        outerEdges.add(outer_top);
        outerEdges.add(outer_bottom);
        outerEdges.add(outer_right);

        // left stair outer wall
        Edge stairL_bottom = new Edge(stair2_starting_X, stair2_starting_Y, stair2_width, stair2_starting_Y);
        Edge stairL_left = new Edge(stair2_starting_X, stair2_starting_Y, stair2_starting_X, stair2_starting_Y - stair2_heigt);
        Edge stairL_topleft = new Edge(stair2_starting_X, stair2_starting_Y - stair2_heigt, stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt);
        Edge stairL_topmidleft = new Edge(stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt, stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt + 1.1f/ratio);
        Edge stairL_topmidmid = new Edge(stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 7.3333f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio);
        Edge stairL_topmidright = new Edge(stair2_starting_X + widthOfStair + 7.3333f/ratio , stair2_starting_Y - stair2_heigt + 1.1f/ratio, stair2_starting_X + widthOfStair + 7.3333f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio - 2.0833f/ratio);
        Edge stairL_topright = new Edge(stair2_starting_X + widthOfStair + 7.3333f/ratio , stair2_starting_Y - stair2_heigt + 1.1f/ratio - 2.0833f/ratio, widthOfStair + 7.3333f/ratio + 4.42f/ratio, stair2_starting_Y - stair2_heigt + 1.1f/ratio - 2.0833f/ratio);

        outerEdges.add(stairL_bottom);
        outerEdges.add(stairL_left);
        outerEdges.add(stairL_topleft);
        outerEdges.add(stairL_topmidleft);
        outerEdges.add(stairL_topmidmid);
        outerEdges.add(stairL_topmidright);
        outerEdges.add(stairL_topright);

        // inner rail, count as outer edges
        Edge inner_left = new Edge(inner_starting_X , inner_starting_Y, inner_starting_X, inner_starting_Y - inner_height);
        Edge inner_top = new Edge(inner_starting_X , inner_starting_Y - inner_height, inner_starting_X + inner_width, inner_starting_Y - inner_height);
        Edge inner_bottom = new Edge(inner_starting_X , inner_starting_Y, inner_starting_X + inner_width, inner_starting_Y);
        Edge inner_right = new Edge(inner_starting_X + inner_width, inner_starting_Y, inner_starting_X + inner_width, inner_starting_Y - inner_height);

        outerEdges.add(inner_left);
        outerEdges.add(inner_top);
        outerEdges.add(inner_bottom);
        outerEdges.add(inner_right);

        // top-right stair
        Edge stairTR_bottomleft = new Edge(stair1_starting_X, stair1_starting_y, stair1_starting_X, stair1_starting_y + stair1_height);
        Edge stairTR_bottombottom = new Edge(stair1_starting_X, stair1_starting_y + stair1_height, stair1_starting_X + widthOfStair, stair1_starting_y + stair1_height);
        Edge stairTR_leftbottom = new Edge(main_wall_starting_X + mail_wall_width, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y + widthOfStair);
        Edge stairTR_leftleft = new Edge(main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y);

        innerEdges.add(stairTR_bottomleft);
        innerEdges.add(stairTR_bottombottom);
        innerEdges.add(stairTR_leftbottom);
        innerEdges.add(stairTR_leftleft);
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setColor(Color.rgb(0,24,69));
        initEdges();
    }

    public SecondFloor(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(@NonNull ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                mScaleFactor = Math.max(0.7f, Math.min(mScaleFactor, 5.0f)); // set scale range from 0.7 to 5.
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

    private void drawPins() {
        ViewGroup viewGroup = (ViewGroup) this.getParent();
        for (MapPin mapPin: pinList) {
            mapPin.create(viewGroup);
        }
    }

    public void addShowCases(ArrayList<ShowCase> showCases) {
        Drawable pinIcon = getResources().getDrawable(R.drawable.location);
        pinList = new ArrayList<MapPin>();
        for (ShowCase showCase: showCases) {
            if (showCase.getFloorNum() == 2) {
                MapPin mapPin = new MapPin(pinIcon, showCase, this.getContext());
                Edge e1 = new Edge(showCase.getX(), showCase.getY(), showCase.getX() + showCase.getLength(), showCase.getY());
                Edge e2 = new Edge(showCase.getX() + showCase.getLength(), showCase.getY(),
                        showCase.getX() + showCase.getLength(), showCase.getY() + showCase.getWidth());
                Edge e3 = new Edge(showCase.getX() + showCase.getLength(), showCase.getY() + showCase.getWidth(),
                        showCase.getX(), showCase.getY() + showCase.getWidth());
                Edge e4 = new Edge(showCase.getX(), showCase.getY() + showCase.getWidth(), showCase.getX(), showCase.getY());
                outerEdges.add(e1);
                outerEdges.add(e2);
                outerEdges.add(e3);
                outerEdges.add(e4);
                pinList.add(mapPin);
            }
        }
        drawPins();
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
        canvas.translate(translateX, translateY);
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.save();

        // outer wall
        paint.setStrokeWidth(5f);
        for(Edge e: outerEdges) {
            canvas.drawLine(e.from_x, e.from_y,
                    e.to_x,e.to_y, paint);
        }

        // inner rail and top-right stair
        paint.setStrokeWidth(2f);
        for(Edge e: innerEdges) {
            canvas.drawLine(e.from_x, e.from_y,
                    e.to_x,e.to_y, paint);
        }


        // stair 1
        paint.setStrokeWidth(2f);

/*
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + 0.73f/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + 0.73f/ratio, stair1_starting_y, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + 2*0.73f/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + 2*0.73f/ratio, stair1_starting_y, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + 3*0.73f/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + 3*0.73f/ratio, stair1_starting_y, paint);

        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 0.73f/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 2*0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 2*0.73f/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 3*0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 3*0.73f/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 4*0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 4*0.73f/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 5*0.73f/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 5*0.73f/ratio, paint);
*/


/*
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
*/

        canvas.restore();
    }

    @Override
    public ArrayList<Edge> getEdges() {
        return outerEdges;
    }

    public void setPinsVisibility () {
        for (MapPin mapPin: pinList) {
            mapPin.setVisibility(this.getVisibility());
        }
    }
}
