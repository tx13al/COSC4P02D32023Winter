package com.example.museumapp;

import java.util.List;

public class ShowCase {
    private int closetID;
    private float length;
    private float width;
    private int x, y;
    private int floorNum;
    private List<Item> items;

    public ShowCase(int closetID, float length, float width, int x, int y, int floorNum, List<Item> items) {
        this.closetID = closetID;
        this.length = length;
        this.width = width;
        this.x = x;
        this.y = y;
        this.floorNum = floorNum;
        this.items = items;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
