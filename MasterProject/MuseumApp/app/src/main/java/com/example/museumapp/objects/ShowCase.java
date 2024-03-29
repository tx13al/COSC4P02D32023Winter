package com.example.museumapp.objects;

import java.util.List;

public class ShowCase {
    private int type;   //0 = showCase, 1 = washroom, 2 = staff, 3 = exit
    private int closetID;
    private float length;
    private float width;
    private float x, y;
    private int floorNum;
    private List<Item> items;
    private boolean isSet = false;

    public ShowCase(int type, int closetID, int floorNum, float x, float y, float length, float width, List<Item> items) {
        this.type = type;
        this.closetID = closetID;
        this.length = length;
        this.width = width;
        this.x = x;
        this.y = y;
        this.floorNum = floorNum;
        this.items = items;
    }

    public void update(int floorNum, float x, float y, float length, float width) {
        this.floorNum = floorNum;
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
    }

    public int getType() {
        return this.type;
    }

    public int getClosetID() {
        return closetID;
    }

    public void setClosetID(int closetID) {
        this.closetID = closetID;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public float getCenterX() {
        return x + length / 2;
    }

    public float getCenterY() {
        return y + width / 2;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.isSet = true;
    }

    public void setIsSet(boolean isSet) {
        this.isSet = isSet;
    }

    public boolean getIsSet() {
        return this.isSet;
    }
}
