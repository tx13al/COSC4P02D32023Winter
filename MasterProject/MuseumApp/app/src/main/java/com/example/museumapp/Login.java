package com.example.museumapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class Login  implements View.OnClickListener {
    private Context context;
    View dimView;

    public Login(Context context, View dimView) {
        this.context = context;
        this.dimView = dimView;
    }

    @Override
    public void onClick(View view) {
        //popup window settings
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int x = width / 15;
        int y = height / 5;
        PopupWindow popupWindow = new PopupWindow(context);
        View popView = LayoutInflater.from(context).inflate(R.layout.activity_login, null);
        popupWindow.setContentView(popView);
        popupWindow.setWidth(width * 7 / 8);
        popupWindow.setHeight(height * 2 / 3);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_popup));
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
        //add a dimmed view to darken the background while the popup window showing
        dimView.setVisibility(View.VISIBLE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }//dimView gone when close the popup window
        });
        ImageView popup_back_button = popView.findViewById(R.id.loginCancel);
        popup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }//click on close button, popup window closes
        });
        Button login = popView.findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
    }
}