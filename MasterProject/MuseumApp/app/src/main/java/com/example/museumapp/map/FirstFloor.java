package com.example.museumapp.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import com.example.museumapp.MainActivity;
import com.example.museumapp.R;
import com.example.museumapp.objects.MapPin;
import com.example.museumapp.objects.ShowCase;
import java.util.ArrayList;

public class FirstFloor extends View implements Floor {
    private ArrayList<Edge> innerEdges;
    private ArrayList<Edge> outerEdges;
    private Paint paint;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private float lastX, lastY;
    private float translateX = 350, translateY = 450;
    // Create the pins
    private ArrayList <MapPin> pinList = null;

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 5.0f)); // set scale range from 0.5 to 5
            invalidate();
            for (MapPin mapPin: pinList) {
                mapPin.scalePinLocation(mScaleFactor, translateX, translateY);
            }
            return true;
        }
    }

    //initialize all the edges for the exhibition area.
    private void initInnerEdges() {
        innerEdges = new ArrayList<Edge>();
        Edge innerEdge1 = new Edge(0.0f,0.0f,284.0f,0.0f);
        Edge innerEdge2 = new Edge(284.0f,0.0f,284.0f,41.0f);
        Edge innerEdge3 = new Edge(284.0f,41.0f,284.0f,0.0f);
        Edge innerEdge4 = new Edge(284.0f,0.0f,356.0f,0.0f);
        Edge innerEdge5 = new Edge(356.0f,0.0f,356.0f,109.0f);
        Edge innerEdge6 = new Edge(356.0f,109.0f,284.0f,109.0f);
        Edge innerEdge7 = new Edge(284.0f,109.0f,284.0f,70.0f);
        Edge innerEdge8 = new Edge(284.0f,70.0f,284.0f,353.0f);
        Edge innerEdge9 = new Edge(284.0f,353.0f,168.0f,353.0f);
        Edge innerEdge10 = new Edge(168.0f,353.0f,192.0f,353.0f);
        Edge innerEdge11 = new Edge(192.0f,353.0f,192.0f,635.0f);
        Edge innerEdge12 = new Edge(192.0f,635.0f,252.0f,635.0f);
        Edge innerEdge13 = new Edge(252.0f,635.0f,252.0f,732.0f);
        Edge innerEdge14 = new Edge(252.0f,732.0f,192.0f,732.0f);
        Edge innerEdge15 = new Edge(192.0f,732.0f,192.0f,1017.0f);
        Edge innerEdge16 = new Edge(192.0f,1017.0f,571.0f,1017.0f);
        Edge innerEdge17 = new Edge(571.0f,1017.0f,571.0f,1135.0f);
        Edge innerEdge18 = new Edge(571.0f,1135.0f,497.0f,1135.0f);
        Edge innerEdge19 = new Edge(497.0f,1135.0f,571.0f,1135.0f);
        Edge innerEdge20 = new Edge(571.0f,1135.0f,571.0f,1235.0f);
        Edge innerEdge21 = new Edge(571.0f,1235.0f,497.0f,1235.0f);
        Edge innerEdge22 = new Edge(497.0f,1235.0f,497.0f,1335.0f);
        Edge innerEdge23 = new Edge(497.0f,1335.0f,-109.0f,1335.0f);
        Edge innerEdge24 = new Edge(-109.0f,1335.0f,-109.0f,1235.0f);
        Edge innerEdge25 = new Edge(-109.0f,1235.0f,-13.0f,1235.0f);
        Edge innerEdge26 = new Edge(-13.0f,1235.0f,-109.0f,1235.0f);
        Edge innerEdge27 = new Edge(-109.0f,1235.0f,-109.0f,1118.0f);
        Edge innerEdge28 = new Edge(-109.0f,1118.0f,-13.0f,1118.0f);
        Edge innerEdge29 = new Edge(-13.0f,1118.0f,-109.0f,1118.0f);
        Edge innerEdge30 = new Edge(-109.0f,1118.0f,-109.0f,1017.0f);
        Edge innerEdge31 = new Edge(-109.0f,1017.0f,120.0f,1017.0f);
        Edge innerEdge32 = new Edge(120.0f,1017.0f,81.0f,1017.0f);
        Edge innerEdge33 = new Edge(81.0f,1017.0f,81.0f,965.0f);
        Edge innerEdge34 = new Edge(81.0f,965.0f,52.0f,965.0f);
        Edge innerEdge35 = new Edge(52.0f,965.0f,52.0f,873.0f);
        Edge innerEdge36 = new Edge(52.0f,873.0f,81.0f,873.0f);
        Edge innerEdge37 = new Edge(81.0f,873.0f,81.0f,821.0f);
        Edge innerEdge38 = new Edge(81.0f,821.0f,0.0f,821.0f);
        Edge innerEdge39 = new Edge(0.0f,821.0f,0.0f,208.4f);
        Edge innerEdge40 = new Edge(0.0f,208.4f,-144.0f,208.4f);
        Edge innerEdge41 = new Edge(-144.0f,208.4f,-144.0f,161.6f);
        Edge innerEdge42 = new Edge(-144.0f,161.6f,0.0f,161.6f);
        Edge innerEdge43 = new Edge(0.0f,161.6f,0.0f,0.0f);

        //add all innerEdges to outEdges array list.
        innerEdges.add(innerEdge1);
        innerEdges.add(innerEdge2);
        innerEdges.add(innerEdge3);
        innerEdges.add(innerEdge4);
        innerEdges.add(innerEdge5);
        innerEdges.add(innerEdge6);
        innerEdges.add(innerEdge7);
        innerEdges.add(innerEdge8);
        innerEdges.add(innerEdge9);
        innerEdges.add(innerEdge10);
        innerEdges.add(innerEdge11);
        innerEdges.add(innerEdge12);
        innerEdges.add(innerEdge13);
        innerEdges.add(innerEdge14);
        innerEdges.add(innerEdge15);
        innerEdges.add(innerEdge16);
        innerEdges.add(innerEdge17);
        innerEdges.add(innerEdge18);
        innerEdges.add(innerEdge19);
        innerEdges.add(innerEdge20);
        innerEdges.add(innerEdge21);
        innerEdges.add(innerEdge22);
        innerEdges.add(innerEdge23);
        innerEdges.add(innerEdge24);
        innerEdges.add(innerEdge25);
        innerEdges.add(innerEdge26);
        innerEdges.add(innerEdge27);
        innerEdges.add(innerEdge28);
        innerEdges.add(innerEdge29);
        innerEdges.add(innerEdge30);
        innerEdges.add(innerEdge31);
        innerEdges.add(innerEdge32);
        innerEdges.add(innerEdge33);
        innerEdges.add(innerEdge34);
        innerEdges.add(innerEdge35);
        innerEdges.add(innerEdge36);
        innerEdges.add(innerEdge37);
        innerEdges.add(innerEdge38);
        innerEdges.add(innerEdge39);
        innerEdges.add(innerEdge40);
        innerEdges.add(innerEdge41);
        innerEdges.add(innerEdge42);
        innerEdges.add(innerEdge43);
    }

    //initialize all the edges for the other areas. (offices, washrooms, stair cases)
    private void initOuterEdges() {
        outerEdges = new ArrayList<Edge>();

        //offices
        Edge outerEdge1 = new Edge(0.0f,-601.0f,0.0f,0.0f);
        Edge outerEdge2 = new Edge(0.0f,-601.0f,284.0f,-601.0f);
        Edge outerEdge3 = new Edge(284.0f,-601.0f,284.0f,0.0f);
        Edge outerEdge4 = new Edge(131.28f,-601.0f,131.28f,-316.0f);
        Edge outerEdge5 = new Edge(0.0f,-316.0f,284.0f,-316.0f);
        Edge outerEdge6 = new Edge(82.0f,-316.0f,82.0f,-206.0f);
        Edge outerEdge7 = new Edge(82.0f,-206.0f,58.0f,-206.0f);
        Edge outerEdge8 = new Edge(58.0f,-206.0f,58.0f,-170.0f);
        Edge outerEdge9 = new Edge(0.0f,-170.0f,82.0f,-170.0f);
        Edge outerEdge10 = new Edge(82.0f,-170.0f,82.0f,0.0f);
        Edge outerEdge11 = new Edge(125.0f,0.0f,125.0f,-260.0f);
        Edge outerEdge12 = new Edge(125.0f,-260.0f,225.0f,-260.0f);
        Edge outerEdge13 = new Edge(125.0f,-110.0f,284.0f,-110.0f);
        Edge outerEdge14 = new Edge(125.0f,-188.0f,153.0f,-188.0f);
        Edge outerEdge15 = new Edge(153.0f,-188.0f,153.0f,-211.0f);
        Edge outerEdge16 = new Edge(125.0f,-211.0f,225.0f,-211.0f);
        Edge outerEdge17 = new Edge(181.0f,-211.0f,181.0f,-188.0f);
        Edge outerEdge18 = new Edge(181.0f,-188.0f,284.0f,-188.0f);

        //washrooms
        Edge outerEdge19 = new Edge(0.0f,68.0f,-144.0f,68.0f);
        Edge outerEdge20 = new Edge(-144.0f,68.0f,-144.0f,161.6f);
        Edge outerEdge21 = new Edge(-144.0f,208.4f,-144.0f,302.0f);
        Edge outerEdge22 = new Edge(-144.0f,302.0f,0.0f,302.0f);

        //stair cases in office area
        Edge outerEdge23 = new Edge(135.0f,-260.0f,135.0f,-211.0f);
        Edge outerEdge24 = new Edge(145.0f,-260.0f,145.0f,-211.0f);
        Edge outerEdge25 = new Edge(155.0f,-260.0f,155.0f,-211.0f);
        Edge outerEdge26 = new Edge(165.0f,-260.0f,165.0f,-211.0f);
        Edge outerEdge27 = new Edge(175.0f,-260.0f,175.0f,-211.0f);
        Edge outerEdge28 = new Edge(185.0f,-260.0f,185.0f,-211.0f);
        Edge outerEdge29 = new Edge(195.0f,-260.0f,195.0f,-211.0f);
        Edge outerEdge30 = new Edge(205.0f,-260.0f,205.0f,-211.0f);
        Edge outerEdge31 = new Edge(215.0f,-260.0f,215.0f,-211.0f);
        Edge outerEdge32 = new Edge(225.0f,-260.0f,225.0f,-211.0f);

        //stair cases 1
        Edge outerEdge33 = new Edge(0.0f,821.0f,0.0f,1017.0f);
        Edge outerEdge34 = new Edge(52.0f,965.0f,52.0f,1017.0f);
        Edge outerEdge35 = new Edge(62.0f,965.0f,62.0f,1017.0f);
        Edge outerEdge36 = new Edge(72.0f,965.0f,72.0f,1017.0f);
        Edge outerEdge37 = new Edge(52.0f,821.0f,52.0f,873.0f);
        Edge outerEdge38 = new Edge(62.0f,821.0f,62.0f,873.0f);
        Edge outerEdge39 = new Edge(72.0f,821.0f,72.0f,873.0f);
        Edge outerEdge40 = new Edge(0.0f,873.0f,52.0f,873.0f);
        Edge outerEdge41 = new Edge(0.0f,883.0f,52.0f,883.0f);
        Edge outerEdge42 = new Edge(0.0f,892.0f,52.0f,892.0f);
        Edge outerEdge43 = new Edge(0.0f,901.0f,52.0f,901.0f);
        Edge outerEdge44 = new Edge(0.0f,910.0f,52.0f,910.0f);
        Edge outerEdge45 = new Edge(0.0f,919.0f,52.0f,919.0f);
        Edge outerEdge46 = new Edge(0.0f,928.0f,52.0f,928.0f);
        Edge outerEdge47 = new Edge(0.0f,937.0f,52.0f,937.0f);
        Edge outerEdge48 = new Edge(0.0f,946.0f,52.0f,946.0f);
        Edge outerEdge49 = new Edge(0.0f,955.0f,52.0f,955.0f);
        Edge outerEdge50 = new Edge(0.0f,965.0f,52.0f,965.0f);

        //stair cases 2
        Edge outerEdge51 = new Edge(497.0f,1335.0f,571.0f,1335.0f);
        Edge outerEdge52 = new Edge(571.0f,1235.0f,571.0f,1335.0f);
        Edge outerEdge53 = new Edge(497.0f,1275.0f,571.0f,1275.0f);
        Edge outerEdge54 = new Edge(497.0f,1295.0f,571.0f,1295.0f);
        Edge outerEdge55 = new Edge(521.0f,1235.0f,521.0f,1335.0f);
        Edge outerEdge56 = new Edge(509.0f,1235.0f,509.0f,1275.0f);
        Edge outerEdge57 = new Edge(509.0f,1295.0f,509.0f,1335.0f);
        Edge outerEdge58 = new Edge(521.0f,1285.0f,571.0f,1285.0f);

        //add all outerEdges to outEdges array list.
        outerEdges.add(outerEdge1);
        outerEdges.add(outerEdge2);
        outerEdges.add(outerEdge3);
        outerEdges.add(outerEdge4);
        outerEdges.add(outerEdge5);
        outerEdges.add(outerEdge6);
        outerEdges.add(outerEdge7);
        outerEdges.add(outerEdge8);
        outerEdges.add(outerEdge9);
        outerEdges.add(outerEdge10);
        outerEdges.add(outerEdge11);
        outerEdges.add(outerEdge12);
        outerEdges.add(outerEdge13);
        outerEdges.add(outerEdge14);
        outerEdges.add(outerEdge15);
        outerEdges.add(outerEdge16);
        outerEdges.add(outerEdge17);
        outerEdges.add(outerEdge18);
        outerEdges.add(outerEdge19);
        outerEdges.add(outerEdge20);
        outerEdges.add(outerEdge21);
        outerEdges.add(outerEdge22);
        outerEdges.add(outerEdge23);
        outerEdges.add(outerEdge24);
        outerEdges.add(outerEdge25);
        outerEdges.add(outerEdge26);
        outerEdges.add(outerEdge27);
        outerEdges.add(outerEdge28);
        outerEdges.add(outerEdge29);
        outerEdges.add(outerEdge30);
        outerEdges.add(outerEdge31);
        outerEdges.add(outerEdge32);
        outerEdges.add(outerEdge33);
        outerEdges.add(outerEdge34);
        outerEdges.add(outerEdge35);
        outerEdges.add(outerEdge36);
        outerEdges.add(outerEdge37);
        outerEdges.add(outerEdge38);
        outerEdges.add(outerEdge39);
        outerEdges.add(outerEdge40);
        outerEdges.add(outerEdge41);
        outerEdges.add(outerEdge42);
        outerEdges.add(outerEdge43);
        outerEdges.add(outerEdge44);
        outerEdges.add(outerEdge45);
        outerEdges.add(outerEdge46);
        outerEdges.add(outerEdge47);
        outerEdges.add(outerEdge48);
        outerEdges.add(outerEdge49);
        outerEdges.add(outerEdge50);
        outerEdges.add(outerEdge51);
        outerEdges.add(outerEdge52);
        outerEdges.add(outerEdge53);
        outerEdges.add(outerEdge54);
        outerEdges.add(outerEdge55);
        outerEdges.add(outerEdge56);
        outerEdges.add(outerEdge57);
        outerEdges.add(outerEdge58);
    }

    //initialize all the edges.
    private void initEdges() {
        initInnerEdges();
        initOuterEdges();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(5.0f);
        paint.setColor(Color.rgb(0,24,69));
        initEdges();
    }

    public FirstFloor(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        init();
    }

    //Draw all pins and move them to the correct coordinates on the screen.
    //Warning: This method can only be called once.
    private void drawPins() {
        ViewGroup viewGroup = (ViewGroup) this.getParent();
        for (MapPin mapPin: pinList) {
            mapPin.create(viewGroup);
            mapPin.movePinLocation(translateX, translateY);
        }
    }

    //Add all showcases of the first floor to the view of the first floor, and draw the Pins.
    public void addShowCases(ArrayList<ShowCase> showCases) {
        Drawable pinIcon = getResources().getDrawable(R.drawable.location); //get the image of the pins.
        pinList = new ArrayList<MapPin>();
        for (ShowCase showCase: showCases) {
            if (showCase.getFloorNum() == 1) {  //first floor
                MapPin mapPin = new MapPin(pinIcon, showCase, this.getContext());
                Edge e1 = new Edge(showCase.getX(), showCase.getY(), showCase.getX() + showCase.getLength(), showCase.getY());
                Edge e2 = new Edge(showCase.getX() + showCase.getLength(), showCase.getY(),
                        showCase.getX() + showCase.getLength(), showCase.getY() + showCase.getWidth());
                Edge e3 = new Edge(showCase.getX() + showCase.getLength(), showCase.getY() + showCase.getWidth(),
                        showCase.getX(), showCase.getY() + showCase.getWidth());
                Edge e4 = new Edge(showCase.getX(), showCase.getY() + showCase.getWidth(), showCase.getX(), showCase.getY());
                innerEdges.add(e1);
                innerEdges.add(e2);
                innerEdges.add(e3);
                innerEdges.add(e4);
                pinList.add(mapPin);
            }
        }
        drawPins();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   //finger down
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:   //finger move
                if (this.getContext() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) this.getContext();
                    HorizontalScrollView showCaseItemListScrollView =
                            mainActivity.findViewById(R.id.showCase_item_list_scrollView);
                    showCaseItemListScrollView.setVisibility(View.INVISIBLE);
                }
                float x = event.getX();
                float y = event.getY();
                float dx = x - lastX;
                float dy = y - lastY;
                translateX += dx;
                translateY += dy;
                invalidate();
                for (MapPin mapPin: pinList) {
                    mapPin.movePinLocation(dx, dy);
                }
                lastX = x;
                lastY = y;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {  //be called in invalidate().
        super.onDraw(canvas);
        canvas.translate(translateX, translateY);
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.save();
        for(Edge e: innerEdges) {
            canvas.drawLine(e.from_x, e.from_y, e.to_x ,e.to_y, paint);
        }
        for(Edge e: outerEdges) {
            canvas.drawLine(e.from_x, e.from_y, e.to_x ,e.to_y, paint);
        }
        canvas.restore();
    }

    @Override
    public ArrayList<Edge> getEdges() {
        return innerEdges;
    }

    public void setPinsVisibility () {
        for (MapPin mapPin: pinList) {
            mapPin.setVisibility(this.getVisibility());
        }
    }
}