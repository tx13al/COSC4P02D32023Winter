package com.example.museumapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View dimView, floor_1, floor_2;
    private FrameLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        //main container and two floor views
        mainContainer = findViewById(R.id.main_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        floor_1 = inflater.inflate(R.layout.activity_floor_one,null);
        floor_1.setVisibility(View.VISIBLE);
        mainContainer.addView(floor_1);
        floor_2 = inflater.inflate(R.layout.activity_floor_two, null);
        mainContainer.addView(floor_2);
        //create floor buttons
        Button level_1 = findViewById(R.id.floorOneButton);
        Button level_2 = findViewById(R.id.floorTwoButton);
        level_1.setOnClickListener(this);
        level_2.setOnClickListener(this);
        //create Login button
        Button login = findViewById(R.id.loginButton);
        dimView = inflater.inflate(R.layout.dim_layout, null);
        mainContainer.addView(dimView);
        login.setOnClickListener(new Login(MainActivity.this, dimView));
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //press Level 1 button change to level 1 floor plan
            case R.id.floorOneButton:
                floor_1.setVisibility(View.VISIBLE);
                floor_2.setVisibility(View.GONE);
                break;
            //press Level 2 button change to level 2 floor plan
            case R.id.floorTwoButton:
                floor_1.setVisibility(View.GONE);
                floor_2.setVisibility(View.VISIBLE);
                break;
        }

    }

}