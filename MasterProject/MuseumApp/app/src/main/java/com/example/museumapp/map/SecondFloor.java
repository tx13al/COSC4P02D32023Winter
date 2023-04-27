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

import androidx.annotation.NonNull;

import com.example.museumapp.Control;
import com.example.museumapp.MainActivity;
import com.example.museumapp.R;
import com.example.museumapp.objects.MapPin;
import com.example.museumapp.objects.ShowCase;
import java.util.ArrayList;

public class SecondFloor extends View implements Floor {
    private Paint paint;
    private ArrayList<Edge> innerEdges;
    private ArrayList<Edge> outerEdges;
    private ArrayList<Edge> navigationEdges;
    private final ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private float lastX, lastY;
    private float translateX = 241.0f, translateY = 1467.0f;
    // Create the pins
    private ArrayList<MapPin> pinList = null;
    // The length in feet of the second floor's diagonal.

    //initialize all the edges for the exhibition area.
    private void initInnerEdges() {
        innerEdges = new ArrayList<Edge>();
        Edge innerEdge1 = new Edge(0.0f,0.0f,679.0f,0.0f);
        Edge innerEdge2 = new Edge(679.0f,0.0f,679.0f,251.0f);
        Edge innerEdge3 = new Edge(679.0f,251.0f,629.0f,251.0f);
        Edge innerEdge4 = new Edge(629.0f,251.0f,629.0f,271.0f);
        Edge innerEdge5 = new Edge(629.0f,271.0f,589.0f,271.0f);
        Edge innerEdge6 = new Edge(589.0f,271.0f,589.0f,321.0f);
        Edge innerEdge7= new Edge(589.0f,321.0f,0.0f,321.0f);
        Edge innerEdge8 = new Edge(0.0f,321.0f,0.0f,0.0f);
        Edge innerEdge9 = new Edge(163.0f,76.0f,499.0f,76.0f);
        Edge innerEdge10 = new Edge(499.0f,76.0f,499.0f,245.0f);
        Edge innerEdge11 = new Edge(499.0f,245.0f,163.0f,245.0f);
        Edge innerEdge12 = new Edge(163.0f,245.0f,163.0f,76.0f);

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
    }

    //initialize all the edges for the other areas. (offices, washrooms, stair cases)
    private void initOuterEdges() {
        outerEdges = new ArrayList<Edge>();

        //stair cases 1
        Edge outerEdge1 = new Edge(111.0f,0.0f,111.0f,-192.0f);
        Edge outerEdge2 = new Edge(111.0f,-192.0f,185.0f,-192.0f);
        Edge outerEdge3 = new Edge(185.0f,-192.0f,185.0f,-143.0f);
        Edge outerEdge4 = new Edge(185.0f,-143.0f,163.0f,-143.0f);
        Edge outerEdge5 = new Edge(163.0f,-143.0f,163.0f,-53.0f);
        Edge outerEdge6 = new Edge(163.0f,-53.0f,188.0f,-53.0f);
        Edge outerEdge7 = new Edge(188.0f,-53.0f,188.0f,0.0f);
        Edge outerEdge8 = new Edge(111.0f,-53.0f,163.0f,-53.0f);
        Edge outerEdge9 = new Edge(111.0f,-63.0f,163.0f,-63.0f);
        Edge outerEdge10 = new Edge(111.0f,-73.0f,163.0f,-73.0f);
        Edge outerEdge11 = new Edge(111.0f,-83.0f,163.0f,-83.0f);
        Edge outerEdge12 = new Edge(111.0f,-93.0f,163.0f,-93.0f);
        Edge outerEdge13 = new Edge(111.0f,-103.0f,163.0f,-103.0f);
        Edge outerEdge14 = new Edge(111.0f,-113.0f,163.0f,-113.0f);
        Edge outerEdge15 = new Edge(111.0f,-123.0f,163.0f,-123.0f);
        Edge outerEdge16 = new Edge(111.0f,-133.0f,163.0f,-133.0f);
        Edge outerEdge17 = new Edge(111.0f,-143.0f,163.0f,-143.0f);
        Edge outerEdge18 = new Edge(163.0f,-192.0f,163.0f,-143.0f);
        Edge outerEdge19 = new Edge(174.0f,-192.0f,174.0f,-143.0f);

        //stair cases 2
        Edge outerEdge20 = new Edge(589.0f,321.0f,679.0f,321.0f);
        Edge outerEdge21 = new Edge(679.0f,321.0f,679.0f,251.0f);
        Edge outerEdge22 = new Edge(599.0f,271.0f,599.0f,321.0f);
        Edge outerEdge23 = new Edge(609.0f,271.0f,609.0f,321.0f);
        Edge outerEdge24 = new Edge(619.0f,271.0f,619.0f,321.0f);
        Edge outerEdge25 = new Edge(629.0f,271.0f,629.0f,321.0f);
        Edge outerEdge26 = new Edge(629.0f,271.0f,679.0f,271.0f);
        Edge outerEdge27 = new Edge(629.0f,261.0f,679.0f,261.0f);

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
    }

    private void initEdges() {
        initOuterEdges();
        initInnerEdges();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(5.0f);
        paint.setColor(Color.rgb(0,24,69));
        initEdges();
    }

    public SecondFloor(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(@NonNull ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 5.0f)); // set scale range from 0.5 to 5.
                invalidate();
                if (pinList != null) {
                    for (MapPin mapPin: pinList) {
                        mapPin.scalePinLocation(mScaleFactor, translateX, translateY);
                    }
                }
                return true;
            }

            @Override
            public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(@NonNull ScaleGestureDetector detector) { }

        });
        init();
    }

    //Draw all pins and move them to the correct coordinates on the screen.
    //Warning: This method can only be called once.
    private void drawPins() {
        ViewGroup viewGroup = (ViewGroup) this.getParent();
        if (pinList != null) {
            for (MapPin mapPin: pinList) {
                mapPin.create(viewGroup, translateX, translateY);
            }
        }
    }

    public void addShowCaseEdges(ShowCase showCase) {  //add all the edges of the showCase to the innerEdges.
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
    }

    public MapPin addShowCase(ShowCase showCase) {
        if (pinList == null) {
            pinList = new ArrayList<MapPin>();
        }
        if (showCase.getFloorNum() == 2) {  //second floor
            Drawable pinIcon = getResources().getDrawable(R.drawable.location); //get the image of the pins.
            MapPin mapPin = new MapPin(pinIcon, showCase, this.getContext());
            addShowCaseEdges(showCase);
            pinList.add(mapPin);
            return mapPin;
        }
        return null;
    }

    public void addShowCases(ArrayList<ShowCase> showCases) {
        for (ShowCase showCase: showCases) {
            addShowCase(showCase);
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
                if (this.getContext() instanceof Control) {
                    Control control = (Control) this.getContext();
                    HorizontalScrollView showCaseItemEditListScrollView =
                            control.findViewById(R.id.showCase_item_edit_list_scrollView);
                    showCaseItemEditListScrollView.setVisibility(View.INVISIBLE);
                }
                float x = event.getX();
                float y = event.getY();
                float dx = x - lastX;
                float dy = y - lastY;
                translateX += dx;
                translateY += dy;
                invalidate();
                if(pinList != null){
                    for (MapPin mapPin: pinList) {
                        mapPin.movePinLocation(dx, dy);
                    }
                }
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
        for(Edge e: innerEdges) {
            canvas.drawLine(e.from_x, e.from_y, e.to_x ,e.to_y, paint);
        }
        for(Edge e: outerEdges) {
            canvas.drawLine(e.from_x, e.from_y, e.to_x ,e.to_y, paint);
        }
        if (navigationEdges != null) {
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            for (Edge e: navigationEdges) {
                canvas.drawLine(e.from_x, e.from_y, e.to_x, e.to_y, paint);
            }
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(5);
        }
        canvas.restore();
    }

    @Override
    public ArrayList<Edge> getEdges() {
        return innerEdges;
    }

    public void setPinsVisibility () {
        if (pinList != null) {
            for (MapPin mapPin: pinList) {
                mapPin.setVisibility(this.getVisibility());
            }
        }
    }

    //remove all the edges of the showCase.
    public void removeShowCaseEdges(ShowCase showCase) {
        ArrayList<Edge> removing = new ArrayList<Edge>();
        boolean e1 = true;
        boolean e2 = true;
        boolean e3 = true;
        boolean e4 = true;
        for (Edge edge: innerEdges) {   //find all the edges of this showCase.
            if (e1 && edge.equal(new Edge(showCase.getX(), showCase.getY(),
                    showCase.getX() + showCase.getLength(), showCase.getY()))) {
                removing.add(edge);
                e1 = false;
            }
            if (e2 && edge.equal(new Edge(showCase.getX() + showCase.getLength(), showCase.getY(),
                    showCase.getX() + showCase.getLength(), showCase.getY() + showCase.getWidth()))) {
                removing.add(edge);
                e2 = false;
            }
            if (e3 && edge.equal(new Edge(showCase.getX() + showCase.getLength(),
                    showCase.getY() + showCase.getWidth(),
                    showCase.getX(), showCase.getY() + showCase.getWidth()))) {
                removing.add(edge);
                e3 = false;
            }
            if (e4 && edge.equal(new Edge(showCase.getX(), showCase.getY() + showCase.getWidth(),
                    showCase.getX(), showCase.getY()))) {
                removing.add(edge);
                e4 = false;
            }
            if ((!e1) && (!e2) && (!e3) && (!e4)) {
                break;
            }
        }
        innerEdges.removeAll(removing);
    }

    public void deleteShowCaseFromMap (MapPin mapPin) {
        mapPin.setVisibility(View.INVISIBLE);
        pinList.remove(mapPin);
        ShowCase showCase = mapPin.getShowCase();
        removeShowCaseEdges(showCase);  //remove all the edges of this mapPin's showCase.
        //remove the pinView from the map.
        mapPin.delete((ViewGroup) this.getParent());
        invalidate();
    }

    //remove the caseEdges from the innerEdges.
    private void removeShowCaseEdges(ArrayList<Edge> caseEdges) {
        innerEdges.removeAll(caseEdges);
    }

    private void removePin(MapPin mapPin) {
        mapPin.delete((ViewGroup) this.getParent());    //remove the pin from the view.
        pinList.remove(mapPin);
    }

    public void remove(MapPin mapPin) {
        removeShowCaseEdges(mapPin.getShowCase());
        removePin(mapPin);
    }

    //update the mapPin of the selected showCase on the map.
    public void updatePin(MapPin mapPin, int floor, float x, float y, float length, float width) {
        mapPin.update(floor, x, y, length, width, translateX, translateY, mScaleFactor);
        addShowCaseEdges(mapPin.getShowCase());
    }

    public void addPin(MapPin mapPin) {
        mapPin.create((ViewGroup) this.getParent());    //add the pin to the view.
        pinList.add(mapPin);
    }

    //update the mapPin of the selected showCase on the map.
    public void updatePin(MapPin mapPin, int floor, float x, float y, float length, float width, ArrayList<Edge> caseEdges) {
        removeShowCaseEdges(caseEdges);
        mapPin.update(floor, x, y, length, width, translateX, translateY, mScaleFactor);
        addShowCaseEdges(mapPin.getShowCase());
    }

    public void setNavigationEdges(ArrayList<Edge> edges) {
        this.navigationEdges = edges;
    }

    public float getTranslateX() {
        return translateX;
    }

    public float getTranslateY() {
        return translateY;
    }

    public ArrayList<MapPin> getPinList() {
        return pinList;
    }
}
