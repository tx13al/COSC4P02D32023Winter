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
                if (context instanceof MainActivity) {
                    mainItemList();
                }
                if (context instanceof Control) {
                    controlItemList();
                }
            }
        });
    }

    //display the detail of the item selected by a dialog.
    private void mainDisplayDetail(Context mainActivity, Item item) {
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
        ImageView imageView = itemDetailDialog.findViewById(R.id.item_detail_image_view);
        Picasso.get()
                .load(item.getImageUrl())
                .resize(600, 0)
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

    private void mainItemList() {
        MainActivity mainActivity = (MainActivity) context;
        HorizontalScrollView showCaseItemListScrollView =
                mainActivity.findViewById(R.id.showCase_item_list_scrollView);
        showCaseItemListScrollView.setVisibility(View.VISIBLE);
        if (mainActivity.getDisplaying() != MapPin.this) {
            //Make sure this Pin is not displaying. (avoid duplicate adding items to scroll view.)
            LinearLayout showCaseItemListLayout =   //container for the items
                    mainActivity.findViewById(R.id.showCase_item_list_scrollView_linear);
            if (mainActivity.getDisplaying() != null) { //clear the container for another selected showCase.
                showCaseItemListLayout.removeAllViews();
            }
            mainActivity.getShowCase(thisCase, MapPin.this);
            //update the displaying showcase in main, and load the showcase items if not loaded.
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            for (Item item : thisCase.getItems()) {
                //Dynamically set linear layout for each item and add them to the scroll.
                View showCaseItemLayout = layoutInflater.inflate(R.layout.item_display,
                        showCaseItemListLayout, false);
                //Image setting
                ImageView imageView = showCaseItemLayout.findViewById(R.id.showCase_item_image_view);
                Picasso.get()
                        .load(item.getImageUrl())
                        .resize(0, 350)
                        .centerCrop()
                        .into(imageView);
                //Text setting
                TextView text = showCaseItemLayout.findViewById(R.id.showCase_item_name_text_view);
                text.setText(item.getName());
                //when click the item, it displays the detail of the item by a dialog
                showCaseItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        mainDisplayDetail(mainActivity, item);
                    }
                });
                showCaseItemListLayout.addView(showCaseItemLayout);
            }
        }
    }

    //addItem to the selected showCase.
    //TODO
    private void addItem() {
        Intent intent = new Intent(context, ItemListActivity.class);
    }

    //display the detail of the item selected by a dialog and set everything editing.
    private void controlDisplayItem() {
        //TODO
    }

    private void controlItemList() {
        Control control = (Control) context;
        HorizontalScrollView showCaseItemEditListScrollView =
                control.findViewById(R.id.showCase_item_edit_list_scrollView);
        showCaseItemEditListScrollView.setVisibility(View.VISIBLE);
        if (control.getDisplaying() != MapPin.this) {
            //Make sure this Pin is not displaying. (avoid duplicate adding items to scroll view.)
            LinearLayout showCaseItemEditListLayout =   //container for the items
                    control.findViewById(R.id.showCase_item_edit_list_scrollView_linear);
            if (control.getDisplaying() != null) {  //list is not empty.
                showCaseItemEditListLayout.removeAllViews();
            }
            control.getShowCase(thisCase, MapPin.this);
            //update the displaying showcase in control, and load the showcase items if not loaded.
            LayoutInflater layoutInflater = LayoutInflater.from(control);
            //set the add button for the showCase.
            Button addItem = new Button(control);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(128, 128);
            params.setMargins(5, 0, 0, 0);
            params.gravity = Gravity.CENTER_VERTICAL;
            addItem.setLayoutParams(params);
            addItem.setBackgroundResource(R.drawable.add_icon_with_circle);
            showCaseItemEditListLayout.addView(addItem);
            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItem();
                }
            });
            for (Item item: thisCase.getItems()) {
                View showCaseItemEditLayout = layoutInflater.inflate(
                        R.layout.item_edit_display,showCaseItemEditListLayout,false);
                //set image
                ImageView imageView = showCaseItemEditLayout.findViewById(R.id.showCase_item_edit_image_view);
                Picasso.get()
                        .load(item.getImageUrl())
                        .resize(0, 500)
                        .centerCrop()
                        .into(imageView);
                //set name
                TextView textView = showCaseItemEditLayout.findViewById(R.id.showCase_item_edit_name_text_view);
                textView.setText(item.getName());
                ImageButton editButton = showCaseItemEditLayout.findViewById(R.id.showCase_item_edit_button);
                ImageButton deleteButton = showCaseItemEditLayout.findViewById(R.id.showCase_item_delete_button);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        controlDisplayItem();
                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseHelper.deleteItemInShowCase(item,thisCase);
                        thisCase.getItems().remove(item);
                        showCaseItemEditListLayout.removeView(showCaseItemEditLayout);
                    }
                });
                showCaseItemEditListLayout.addView(showCaseItemEditLayout);
            }
        }
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

    //create the pinView from the parent view with the translateX and translateY.
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
}
