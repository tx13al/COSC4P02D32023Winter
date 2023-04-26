package com.example.museumapp.objects;

public class POI {

    private int POI_ID;
    private float x, y;
    private int floorNum;

    private int type; //0 = exit, 1 = bathroom

    public POI(int POI_ID, int floorNum, float x, float y, int type) {
        this.POI_ID = POI_ID;
        this.floorNum = floorNum;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update(int floorNum, float x, float y, int type) {
        this.floorNum = floorNum;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getPOI_ID() {
        return POI_ID;
    }

    public void setPOI_ID(int POI_ID) {
        this.POI_ID = POI_ID;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public float getCenterX() {
        return x;
    }

    public float getCenterY() {
        return y;
    }

}
