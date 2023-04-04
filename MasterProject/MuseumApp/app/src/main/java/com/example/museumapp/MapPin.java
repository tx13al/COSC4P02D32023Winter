package com.example.museumapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MapPin {
    private ImageView pinView;
    private float x, y;
    private ShowCase showCase;      // Need to decide pin and showcase are one-to-one, or one-to-many

    public MapPin(Drawable icon, ShowCase sc, Context context) {
        pinView = new ImageView(context);
        pinView.setImageDrawable(icon);
        pinView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        pinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked the icon! Good job! ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, ItemList.class);
                context.startActivity(intent);
            }
        });

        showCase = sc;
        this.x = showCase.getX();
        this.y = showCase.getY();
        pinView.setX(x);
        pinView.setY(y);
    }

    public void setPinLocation(int x, int y) {
        this.x = x;
        this.y = y;
        pinView.setX(x);
        pinView.setY(y);
    }

    public void create(ViewGroup parentView) {
        if (pinView == null) {
            return;
        }
        pinView.setVisibility(View.GONE);
        parentView.addView(pinView);
    }

    public void setInvisible() {
        pinView.setVisibility(View.GONE);
    }

    public void setVisible() {
        pinView.setVisibility(View.VISIBLE);
    }
}
