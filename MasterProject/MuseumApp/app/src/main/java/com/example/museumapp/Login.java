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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int x = width / 5;
        int y = height / 5;
        PopupWindow popupWindow = new PopupWindow(context);
        View popView = LayoutInflater.from(context).inflate(R.layout.activity_login, null);
        popupWindow.setContentView(popView);
        popupWindow.setWidth(width * 2 / 3);
        popupWindow.setHeight(height * 2 / 5);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CFCFC1")));
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
        dimView.setVisibility(View.VISIBLE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
        ImageView popup_back_button = popView.findViewById(R.id.loginCancel);
        popup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
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