package com.example.museumapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Control extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private View floor_1, floor_2, dimView;
    private FrameLayout control_mainContainer;
    private ConstraintLayout control_mainScreen;
    private Button logout, level_1, level_2, home, info, arts, setting;

    AlertDialog.Builder abuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        control_mainScreen = findViewById(R.id.control);
        //main container and two floor views
        control_mainContainer = findViewById(R.id.control_main_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        floor_1 = inflater.inflate(R.layout.activity_floor_one,null);
        floor_1.setVisibility(View.VISIBLE);
        control_mainContainer.addView(floor_1);
        floor_2 = inflater.inflate(R.layout.activity_floor_two, null);
        control_mainContainer.addView(floor_2);
        //create floor buttons
        level_1 = findViewById(R.id.control_floorOneButton);
        level_2 = findViewById(R.id.control_floorTwoButton);
        level_1.setOnClickListener(this);
        level_1.setTextColor(getColor(R.color.red));//set the text color as red becuase level 1 is selected by default
        level_2.setOnClickListener(this);
        //create home button
        home = findViewById(R.id.control_add);
        home.setOnClickListener(this);
        home.setTextColor(getColor(R.color.red));//set the text color as red because home is selected by default
        //create info button
        info = findViewById(R.id.control_delete);
        info.setOnClickListener(this);
        //create arts button
        arts = findViewById(R.id.control_change);
        arts.setOnClickListener(this);
        //create setting button
        setting = findViewById(R.id.control_more);
        setting.setOnClickListener(this);

        //logout
        logout=findViewById(R.id.control_loginButton);
        abuilder=new AlertDialog.Builder(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abuilder.setTitle("Logout Confirm").setMessage("Do you want to logout?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Control.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        });


/*        //search bar
        String[] stringArray = getResources().getStringArray(R.array.countries_array);
        SearchView searchView = findViewById(R.id.control_search_bar);
        SearchBar searchBar = new SearchBar(this, stringArray, searchView);*/


    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //press Level 1 button change to level 1 floor plan
            case R.id.control_floorOneButton:
                floor_1.setVisibility(View.VISIBLE);
                floor_2.setVisibility(View.GONE);
                level_1.setTextColor(getColor(R.color.red));
                level_2.setTextColor(getColor(R.color.navy_blue));
                break;
            //press Level 2 button change to level 2 floor plan
            case R.id.control_floorTwoButton:
                floor_1.setVisibility(View.GONE);
                floor_2.setVisibility(View.VISIBLE);
                level_1.setTextColor(getColor(R.color.navy_blue));
                level_2.setTextColor(getColor(R.color.red));
                break;
            //press home button to back to home page
            case R.id.home:
                home.setTextColor(getColor(R.color.red));
                info.setTextColor(getColor(R.color.navy_blue));//TODO need to make it more efficient when the button is not clicked the text color back to default
                arts.setTextColor(getColor(R.color.navy_blue));
                setting.setTextColor(getColor(R.color.navy_blue));
                break;
            //press info button to show the information about the museum like operating hours and admission
            case R.id.info:
                info.setTextColor(getColor(R.color.red));
                home.setTextColor(getColor(R.color.navy_blue));
                arts.setTextColor(getColor(R.color.navy_blue));
                setting.setTextColor(getColor(R.color.navy_blue));
                break;
            //press art button to browse all the art
            case R.id.art:
                info.setTextColor(getColor(R.color.navy_blue));
                home.setTextColor(getColor(R.color.navy_blue));
                arts.setTextColor(getColor(R.color.red));
                setting.setTextColor(getColor(R.color.navy_blue));
                break;
            //press setting button to show settings page. Change font size
            case R.id.settings:
                info.setTextColor(getColor(R.color.navy_blue));
                home.setTextColor(getColor(R.color.navy_blue));
                arts.setTextColor(getColor(R.color.navy_blue));
                setting.setTextColor(getColor(R.color.red));
                break;
        }
    }

}
