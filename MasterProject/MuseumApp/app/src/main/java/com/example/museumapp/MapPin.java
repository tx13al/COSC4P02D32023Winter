package com.example.museumapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class MapPin {
    private Drawable pin;
    private int pinWidth;
    private int pinHeight;
    private int x, y;
    private ShowCase showCase;      // Need to decide pin and showcase are one-to-one, or one-to-many

    public MapPin(Drawable icon, ShowCase sc) {
        pin = icon;
        pinWidth = 100;//pin.getIntrinsicWidth();
        pinHeight = 100;//pin.getIntrinsicHeight();
        showCase = sc;
        this.x = showCase.getX();
        this.y = showCase.getY();
    }

    public void setPinLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas) {
        if (pin == null) {
            return;
        }

        // Calculate the position of the pin based on the location on the SecondFloor view
        int pinX = 500;
        int pinY = 1500;

        // Draw the pin at the calculated position
        pin.setBounds(pinX, pinY, pinX+pinWidth, pinY+pinHeight);
        pin.draw(canvas);
    }
}
