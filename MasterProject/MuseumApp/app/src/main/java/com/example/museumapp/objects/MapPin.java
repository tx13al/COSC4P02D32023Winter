package com.example.museumapp.objects;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.museumapp.Control;
import com.example.museumapp.DatabaseHelper;
import com.example.museumapp.ItemListActivity;
import com.squareup.picasso.Picasso;
import com.example.museumapp.MainActivity;
import com.example.museumapp.R;

import org.w3c.dom.Text;

public class MapPin {
    Drawable icon;
    Context context;
    private ImageView pinView;
    private ShowCase thisCase;

    public MapPin(Drawable icon, ShowCase showCase, Context context) {
        this.icon = icon;
        pinView = new ImageView(context);
        pinView.setImageDrawable(icon);
        pinView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        if ((showCase.getType() == 0) || (showCase.getType() == 2)) {
            this.pinView.setX(showCase.getCenterX() - 50);
            this.pinView.setY(showCase.getCenterY() - 100);
        }
        if ((showCase.getType() == 1) || (showCase.getType() == 3)) {
            this.pinView.setX(showCase.getCenterX() - 50);
            this.pinView.setY(showCase.getCenterY() - 50);
        }
        this.thisCase = showCase;
        this.context = context;
        pinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).displayMapPinItemList(MapPin.this);
                }
                if (context instanceof Control) {
                    ((Control) context).displayMapPinItemList(MapPin.this);
                }
            }
        });
    }

    public void movePinLocation(float x, float y) {
        float newX = pinView.getX() + x;
        float newY = pinView.getY() + y;
        pinView.setX(newX);
        pinView.setY(newY);
    }

    public void scalePinLocation(float scale, float dx, float dy) {
        pinView.setX(thisCase.getCenterX() * scale + dx - 50);
        pinView.setY(thisCase.getCenterY() * scale + dy - 100);
    }

    //create the pinView from the parent view with the translateX and translateY.
    public void create(ViewGroup parentView, float translateX, float translateY) {
        if (pinView == null) {
            return;
        }
        pinView.setVisibility(View.GONE);
        parentView.addView(pinView);
        this.movePinLocation(translateX, translateY);
    }

    //delete the pinView from the parent view
    public void delete(ViewGroup parentView) {
        parentView.removeView(pinView);
    }

    public void setVisibility(int visibility) {
        this.pinView.setVisibility(visibility);
    }

    //create the pinView from the parent view.
    public void create(ViewGroup parentView) {
        if (pinView == null) {
            return;
        }
        pinView.setVisibility(View.GONE);
        parentView.addView(pinView);
    }

    public void update(int floorNum, float x, float y, float length, float width,
                       float translateX, float translateY, float mScaleFactor) {
        thisCase.update(floorNum, x, y, length, width);
        this.pinView.setX(thisCase.getCenterX() - 50);
        this.pinView.setY(thisCase.getCenterY() - 100);
        this.scalePinLocation(mScaleFactor, translateX, translateY);
    }

    public ShowCase getShowCase() {
        return thisCase;
    }

    public void setDefault() {
        pinView.setImageDrawable(icon);
        pinView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
    }

    public void setStart() {
        Drawable startIcon = context.getResources().getDrawable(R.drawable.start_location);
        pinView.setImageDrawable(startIcon);
        pinView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
    }

    public void setEnd() {
        Drawable endIcon = context.getResources().getDrawable(R.drawable.end_location);
        pinView.setImageDrawable(endIcon);
        pinView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
    }
}
