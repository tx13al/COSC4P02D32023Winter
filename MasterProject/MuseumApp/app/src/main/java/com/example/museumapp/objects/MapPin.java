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
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.squareup.picasso.Picasso;
import com.example.museumapp.MainActivity;
import com.example.museumapp.R;

import org.w3c.dom.Text;

public class MapPin {
    Context context;
    private ImageView pinView;
    private ShowCase thisCase;

    public MapPin(Drawable icon, ShowCase showCase, Context context) {
        pinView = new ImageView(context);
        pinView.setImageDrawable(icon);
        pinView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        this.pinView.setX(showCase.getCenterX() - 50);
        this.pinView.setY(showCase.getCenterY() - 100);
        this.thisCase = showCase;
        this.context = context;
        pinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MainActivity) {  //if context is mainActivity
                    MainActivity mainActivity = (MainActivity) context;
                    HorizontalScrollView showCaseItemListScrollView =
                            mainActivity.findViewById(R.id.showCase_item_list_scrollView);
                    showCaseItemListScrollView.setVisibility(View.VISIBLE);
                    if (mainActivity.getDisplaying() != MapPin.this) {
                        //This Pin is not displaying. (avoid duplicate adding items to scroll view.)
                        mainActivity.getShowCase(showCase, MapPin.this);
                        //get Items from database for selected showCase if necessary (Maybe loaded already)
                        LinearLayout showCaseItemListLayout =   //container for the items
                                mainActivity.findViewById(R.id.showCase_item_list_scrollView_linear);
                        if (mainActivity.getDisplaying() != null) { //clear the container for another selected showCase.
                            showCaseItemListLayout.removeAllViews();
                        }
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
                            //Dynamic linear layout for each item.
                            LinearLayout itemLayout = new LinearLayout(mainActivity);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);    //set layout params.
                            layoutParams.setMargins(10,10,10,10);
                            itemLayout.setLayoutParams(layoutParams);
                            itemLayout.setOrientation(LinearLayout.VERTICAL);
                            itemLayout.addView(image);
                            itemLayout.addView(text);
                            showCaseItemListLayout.addView(itemLayout);
                            //when click the item, it displays the detail of the item by a dialog
                            itemLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view1) {
                                    Dialog itemDetailDialog = new Dialog(mainActivity);
                                    itemDetailDialog.setContentView(R.layout.main_dialog_item_detail);
                                    ImageView dialogDismiss = itemDetailDialog.findViewById(R.id.main_dialog_item_detail_dismiss);
                                    dialogDismiss.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view2) {
                                            itemDetailDialog.dismiss();
                                        }
                                    });
                                    //display the name of the item.
                                    TextView itemDetailNameTextView = itemDetailDialog.findViewById(R.id.item_detail_name_text_view);
                                    itemDetailNameTextView.setText(item.getName());
                                    //if possible, display the name of start year.
                                    int endYearPosition = 1;
                                    if (item.getStartYear() != 0) {
                                        endYearPosition += 1;
                                        LinearLayoutCompat mainDialogItemDetailScrollViewLinearLayout =
                                                itemDetailDialog.findViewById(R.id.main_dialog_item_detail_scroll_view_linear_layout);
                                        TextView startYear = new TextView(mainActivity);
                                        startYear.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                        startYear.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
                                        startYear.setText("Start year:  " + item.getStartYear());
                                        mainDialogItemDetailScrollViewLinearLayout.addView(startYear, 1);
                                    }
                                    //if possible, display the name of end year.
                                    if (item.getEndYear() != 0) {
                                        LinearLayoutCompat mainDialogItemDetailScrollViewLinearLayout =
                                                itemDetailDialog.findViewById(R.id.main_dialog_item_detail_scroll_view_linear_layout);
                                        TextView endYear = new TextView(mainActivity);
                                        endYear.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                        endYear.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
                                        endYear.setText("End Year:  " + item.getEndYear());
                                        mainDialogItemDetailScrollViewLinearLayout.addView(endYear, endYearPosition);
                                    }
                                    //display the image of the item.
                                    ImageView imageView= itemDetailDialog.findViewById(R.id.item_detail_image_view);
                                    Picasso.get()
                                            .load(item.getImageUrl())
                                            .resize(750, 750)
                                            .centerCrop()
                                            .into(imageView);
                                    //display the description.
                                    TextView itemDetailDescriptionTextView =
                                            itemDetailDialog.findViewById(R.id.item_detail_description_text_view);
                                    itemDetailDescriptionTextView.setText(item.getDescription());
                                    //display the URL and set a onclick for the browser.
                                    TextView itemDetailURLTextView = itemDetailDialog.findViewById(R.id.item_detail_url_text_view);
                                    itemDetailURLTextView.setText(item.getItemUrl());
                                    itemDetailURLTextView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view2) {
                                            String URL = itemDetailURLTextView.getText().toString();
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                                            mainActivity.startActivity(intent);
                                        }
                                    });
                                    itemDetailDialog.show();
                                    itemDetailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                }
                            });
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
        pinView.setX(thisCase.getCenterX() * scale + dx - 50);
        pinView.setY(thisCase.getCenterY() * scale + dy - 100);
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
