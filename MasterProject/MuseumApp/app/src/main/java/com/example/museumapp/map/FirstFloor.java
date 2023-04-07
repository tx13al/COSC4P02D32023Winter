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

import com.example.museumapp.R;
import com.example.museumapp.objects.MapPin;
import com.example.museumapp.objects.ShowCase;

import java.util.ArrayList;
import java.util.Map;

public class FirstFloor extends View implements Floor {
    ArrayList<Edge> edges;
    private Paint paint;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.f;
    private float lastX, lastY;
    private float translateX = 450, translateY = 200;

    // Create the pins
    ArrayList <MapPin> pinList = null;

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 5.0f)); // set scale range from 0.1 to 10
            invalidate();
            for (MapPin mapPin: pinList) {
                mapPin.scalePinLocation(mScaleFactor, translateX, translateY);
            }
            return true;
        }
    }

    //initialize all the wall edges.
    private void initEdges() {
        edges = new ArrayList<Edge>();
        Edge edge1 = new Edge();
        edge1.from_x = 0.0f;
        edge1.from_y = 0.0f;
        edge1.to_x = 110.0f;
        edge1.to_y = 0.0f;

        Edge edge2 = new Edge();
        edge2.from_x = 110.0f;
        edge2.from_y = 0.0f;
        edge2.to_x = 110.0f + 110.0f;
        edge2.to_y = 0.0f;

        Edge edge3 = new Edge();
        edge3.from_x = 0.0f;
        edge3.from_y = 0.0f;
        edge3.to_x = 0.0f;
        edge3.to_y = 239.0f;

        Edge edge4 = new Edge();
        edge4.from_x = 110f+110f;
        edge4.from_y = 0.0f;
        edge4.to_x = 220f;
        edge4.to_y = 239.0f;

        Edge edge5 = new Edge();
        edge5.from_x = 110f;
        edge5.from_y = 0.0f;
        edge5.to_x = 110f;
        edge5.to_y = 239.0f;

        Edge edge6 = new Edge();
        edge6.from_x = 0f;
        edge6.from_y = 239f;
        edge6.to_x = 220f;
        edge6.to_y = 239.0f;

        Edge edge7 = new Edge();
        edge7.from_x = 0f;
        edge7.from_y = 239f;
        edge7.to_x = 0f;
        edge7.to_y = 239.0f+122f;

        Edge edge8 = new Edge();
        edge8.from_x = 0f;
        edge8.from_y = 239f+122f;
        edge8.to_x = 0f;
        edge8.to_y = 239.0f+122f+144f;

        Edge edge9 = new Edge();
        edge9.from_x = -91.0f;
        edge9.from_y = 239.0f + 122.0f + 144.0f + 837.0f;
        edge9.to_x = -91.0f;
        edge9.to_y = 239.0f + 122.0f + 144.0f + 837.0f + 266.0f;

        Edge edge10 = new Edge();
        edge10.from_x = -91.0f;
        edge10.from_y = 239.0f + 122.0f + 144.0f + 837.0f;
        edge10.to_x = 0.0f;
        edge10.to_y = 239.0f + 122.0f + 144.0f + 837.0f;

        Edge edge11 = new Edge();
        edge11.from_x = -91.0f;
        edge11.from_y = 239.0f + 122.0f + 144.0f + 837.0f + 266.0f;
        edge11.to_x = -91.0f + 568.0f;
        edge11.to_y = 239.0f + 122.0f + 144.0f + 837.0f + 266.0f;

        Edge edge12 = new Edge();
        edge12.from_x = -91.0f + 568.0f;
        edge12.from_y = 239.0f + 122.0f + 144.0f + 837.0f;
        edge12.to_x = -91.0f + 568.0f;
        edge12.to_y = 239.0f + 122.0f + 144.0f + 837.0f + 266.0f;

        Edge edge13 = new Edge();
        edge13.from_x = -91.0f + 568.0f;
        edge13.from_y = 239.0f + 122.0f + 144.0f + 837.0f;
        edge13.to_x = -91.0f + 568.0f - 317.0f;
        edge13.to_y = 239.0f + 122.0f + 144.0f + 837.0f;

        Edge edge14 = new Edge();
        edge14.from_x = -91.0f + 568.0f - 317.0f;
        edge14.from_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f;
        edge14.to_x = -91.0f + 568.0f - 317.0f;
        edge14.to_y = 239.0f + 122.0f + 144.0f + 837.0f;

        Edge edge15 = new Edge();
        edge15.from_x = -91.0f + 568.0f - 317.0f + 75.0f;
        edge15.from_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f - 81.0f;
        edge15.to_x = -91.0f + 568.0f - 317.0f + 75.0f;
        edge15.to_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f;

        Edge edge16 = new Edge();
        edge16.from_x = -91.0f + 568.0f - 317.0f;
        edge16.from_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f - 81.0f - 236.0f;
        edge16.to_x = -91.0f + 568.0f - 317.0f;
        edge16.to_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f - 81.0f;

        Edge edge17 = new Edge();
        edge17.from_x = -91.0f + 568.0f - 317.0f;
        edge17.from_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f - 81.0f - 236.0f;
        edge17.to_x = 110.0f + 110.0f;
        edge17.to_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f - 81.0f - 236.0f;

        Edge edge18 = new Edge();
        edge18.from_x = 110.0f + 110.0f;
        edge18.from_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f - 81.0f - 236.0f;
        edge18.to_x = 110.0f + 110.0f;
        edge18.to_y = 239.0f + 122.0f + 144.0f + 81.0f;

        Edge edge19 = new Edge();
        edge19.from_x = 110.0f + 110.0f + 60.0f;
        edge19.from_y = 239.0f + 122.0f + 144.0f;
        edge19.to_x = 110.0f + 110.0f + 60.0f;
        edge19.to_y = 239.0f + 122.0f + 144.0f + 81.0f;

        Edge edge20 = new Edge();
        edge20.from_x = 110.0f + 110.0f;
        edge20.from_y = 239.0f + 122.0f + 144.0f;
        edge20.to_x = 110.0f + 110.0f;
        edge20.to_y = 239.0f;

        Edge edge21 = new Edge();
        edge21.from_x = 110.0f + 110.0f;
        edge21.from_y = 239.0f + 122.0f + 144.0f;
        edge21.to_x = 110.0f + 110.0f + 60.0f;
        edge21.to_y = 239.0f + 122.0f + 144.0f;

        Edge edge22 = new Edge();
        edge22.from_x = 110.0f + 110.0f;
        edge22.from_y = 239.0f + 122.0f + 144.0f + 81.0f;
        edge22.to_x = 110.0f + 110.0f + 60.0f;
        edge22.to_y = 239.0f + 122.0f + 144.0f + 81.0f;

        Edge edge23 = new Edge();
        edge23.from_x = -91.0f + 568.0f - 317.0f + 75.0f;
        edge23.from_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f - 81.0f;
        edge23.to_x = -91.0f + 568.0f - 317.0f;
        edge23.to_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f - 81.0f;

        Edge edge24 = new Edge();
        edge24.from_x = -91.0f + 568.0f - 317.0f + 75.0f;
        edge24.from_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f;
        edge24.to_x = -91.0f + 568.0f - 317.0f;
        edge24.to_y = 239.0f + 122.0f + 144.0f + 837.0f - 239.0f;

        Edge edge25 = new Edge();
        edge25.from_x = 0.0f;
        edge25.from_y = 239.0f + 122.0f + 76.0f + 68.0f + 52.0f;
        edge25.to_x = -120.0f;
        edge25.to_y = 239.0f + 122.0f + 76.0f + 68.0f + 52.0f;

        Edge edge26 = new Edge();
        edge26.from_x = -120.0f;
        edge26.from_y = 239.0f + 122.0f + 76.0f + 68.0f + 52.0f + 195.0f;
        edge26.to_x = -120.0f;
        edge26.to_y = 239.0f + 122.0f + 76.0f + 68.0f + 52.0f;

        Edge edge27 = new Edge();
        edge27.from_x = 0.0f;
        edge27.from_y = 239.0f + 122.0f + 76.0f + 68.0f + 52.0f + 195.0f;
        edge27.to_x = -120.0f;
        edge27.to_y = 239.0f + 122.0f + 76.0f + 68.0f + 52.0f + 195.0f;

        Edge edge28 = new Edge();
        edge28.from_x = 0.0f;
        edge28.from_y = 239.0f + 122.0f + 76.0f + 68.0f + 52.0f + 195.0f;
        edge28.to_x = 0.0f;
        edge28.to_y = 239.0f + 122.0f + 76.0f + 68.0f + 52.0f + 195.0f + 590.0f;

        Edge edge29 = new Edge();
        edge29.from_x = 0.0f;
        edge29.from_y = 239.0f + 122.0f;
        edge29.to_x = 61.0f;
        edge29.to_y = 239.0f + 122.0f;

        Edge edge30 = new Edge();
        edge30.from_x = 61.0f;
        edge30.from_y = 239.0f + 122.0f;
        edge30.to_x = 61.0f;
        edge30.to_y = 239.0f;

        Edge edge31 = new Edge();
        edge31.from_x = 61.0f;
        edge31.from_y = 239.0f + 122.0f + 144.0f;
        edge31.to_x = 61.0f;
        edge31.to_y = 239.0f + 122.0f;

        Edge edge32 = new Edge();
        edge32.from_x = 61.0f;
        edge32.from_y = 239.0f + 122.0f + 144.0f;
        edge32.to_x = 0.0f;
        edge32.to_y = 239.0f + 122.0f + 144.0f;

        Edge edge33 = new Edge();
        edge33.from_x = 110.0f + 110.0f - 133.0f;
        edge33.from_y = 239.0f + 110.0f;
        edge33.to_x = 110.0f + 110.0f;
        edge33.to_y = 239.0f + 110.0f;

        Edge edge34 = new Edge();
        edge34.from_x = 110.0f + 110.0f - 133.0f;
        edge34.from_y = 239.0f + 110.0f + 47.0f + 11.0f;
        edge34.to_x = 110.0f + 110.0f;
        edge34.to_y = 239.0f + 110.0f + 47.0f + 11.0f;

        Edge edge35 = new Edge();
        edge35.from_x = 110.0f + 110.0f - 133.0f;
        edge35.from_y = 239.0f + 122.0f + 144.0f;
        edge35.to_x = 110.0f + 110.0f;
        edge35.to_y = 239.0f + 122.0f + 144.0f;

        Edge edge36 = new Edge();
        edge36.from_x = -120.0f;
        edge36.from_y = 239.0f + 122.0f + 144.0f + 52.0f + 78.0f;
        edge36.to_x = 0.0f;
        edge36.to_y = 239.0f + 122.0f + 144.0f + 52.0f + 78.0f;

        Edge edge37 = new Edge();
        edge37.from_x = -120.0f;
        edge37.from_y = 239.0f + 122.0f + 144.0f + 52.0f + 78.0f + 39.0f;
        edge37.to_x = 0.0f;
        edge37.to_y = 239.0f + 122.0f + 144.0f + 52.0f + 78.0f + 39.0f;

        Edge edge38 = new Edge();
        edge38.from_x = 0.0f;
        edge38.from_y = 239.0f + 122.0f + 144.0f + 52.0f + 78.0f;
        edge38.to_x = 0.0f;
        edge38.to_y = 239.0f + 122.0f + 144.0f + 52.0f;

        Edge edge39 = new Edge();
        edge39.from_x = 0.0f;
        edge39.from_y = 239.0f + 122.0f + 144.0f + 52.0f + 78.0f + 39.0f;
        edge39.to_x = 0.0f;
        edge39.to_y = 239.0f + 122.0f + 144.0f + 52.0f + 78.0f + 39.0f + 78.0f;

        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);
        edges.add(edge5);
        edges.add(edge6);
        edges.add(edge7);
        edges.add(edge8);
        edges.add(edge9);
        edges.add(edge10);
        edges.add(edge11);
        edges.add(edge12);
        edges.add(edge13);
        edges.add(edge14);
        edges.add(edge15);
        edges.add(edge16);
        edges.add(edge17);
        edges.add(edge18);
        edges.add(edge19);
        edges.add(edge20);
        edges.add(edge21);
        edges.add(edge22);
        edges.add(edge23);
        edges.add(edge24);
        edges.add(edge25);
        edges.add(edge26);
        edges.add(edge27);
        edges.add(edge28);
        edges.add(edge29);
        edges.add(edge30);
        edges.add(edge31);
        edges.add(edge32);
        edges.add(edge33);
        edges.add(edge34);
        edges.add(edge35);
        edges.add(edge36);
        edges.add(edge37);
        edges.add(edge38);
        edges.add(edge39);
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(5f);
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
                edges.add(e1);
                edges.add(e2);
                edges.add(e3);
                edges.add(e4);
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
        for(Edge e: edges) {
            canvas.drawLine(e.from_x, e.from_y, e.to_x ,e.to_y, paint);
        }
        canvas.restore();
    }

    @Override
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setPinsVisibility () {
        for (MapPin mapPin: pinList) {
            mapPin.setVisibility(this.getVisibility());
        }
    }
}