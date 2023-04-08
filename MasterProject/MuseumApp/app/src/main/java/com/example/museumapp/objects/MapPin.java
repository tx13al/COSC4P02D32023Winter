package com.example.museumapp.objects;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.museumapp.MainActivity;

public class MapPin {
    Context context;
    private ImageView pinView;
    private int sid;

    public MapPin(Drawable icon, ShowCase showCase, Context context) {
        pinView = new ImageView(context);
        pinView.setImageDrawable(icon);
        pinView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        this.pinView.setX(showCase.getCenterX() - 50);
        this.pinView.setY(showCase.getCenterY() - 100);
        sid = showCase.getClosetID();
        this.context = context;
        pinView.setOnClickListener(new View.OnClickListener() {
            private MainActivity context;

            @Override
            public void onClick(View v) {
                ShowCase showcase = this.context.getShowCase(sid);
                Intent intent = new Intent(context, ItemList.class);
                context.startActivity(intent);
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
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate((pinView.getX()) * scale, (pinView.getY()) * scale);
        pinView.setImageMatrix(matrix);
    }


    public void create(ViewGroup parentView) {
        if (pinView == null) {
            return;
        }
        pinView.setVisibility(View.GONE);
        parentView.addView(pinView);
    }

    public void setVisibility(int visibility) {
        this.pinView.setVisibility(visibility);
    }
}
