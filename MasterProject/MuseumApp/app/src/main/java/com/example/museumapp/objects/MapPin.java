package com.example.museumapp.objects;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.example.museumapp.MainActivity;
import com.example.museumapp.R;

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
            @Override
            public void onClick(View view) {
                if (context instanceof MainActivity) {  //if context is mainActivity
                    MainActivity mainActivity = (MainActivity) context;
                    HorizontalScrollView showCaseItemListScrollView =
                            mainActivity.findViewById(R.id.showCase_item_list_scrollView);
                    showCaseItemListScrollView.setVisibility(View.VISIBLE);
                    if (mainActivity.getDisplaying() != MapPin.this) {  //This Pin is displaying. (avoid duplicate adding items to scroll view.)
                        mainActivity.getShowCase(showCase, MapPin.this); //get Items for showCase.
                        LinearLayout showCaseItemListLayout =   //container for the items
                                mainActivity.findViewById(R.id.showCase_item_list_scrollView_linear);
                        for (Item item : showCase.getItems()) {
                            //Image setting
                            ImageView image = new ImageView(mainActivity);
                            Picasso.get()
                                    .load(item.getImageUrl())
                                    .resize(500, 500)
                                    .centerCrop()
                                    .into(image);
                            //Text setting
                            TextView text = new TextView(mainActivity);
                            text.setText(item.getName());
                            text.setGravity(Gravity.CENTER_HORIZONTAL);
                            //Linear layout for item.
                            LinearLayout itemLayout = new LinearLayout(mainActivity);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(10,10,10,10);
                            itemLayout.setLayoutParams(layoutParams);
                            itemLayout.setOrientation(LinearLayout.VERTICAL);
                            itemLayout.addView(image);
                            itemLayout.addView(text);
                            showCaseItemListLayout.addView(itemLayout);
                        }
                    }
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
