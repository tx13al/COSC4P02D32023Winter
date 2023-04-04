package com.example.museumapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Control extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private View floor_1, floor_2, dimView;
    private FrameLayout control_mainContainer;
    private ConstraintLayout control_mainScreen;
    ImageButton logout;
    private Button level_1, level_2, add, delete, change, more;

    Dialog mydialog;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        mydialog= new Dialog(this);


        control_mainScreen = findViewById(R.id.control);
        //main container and two floor views
        control_mainContainer = findViewById(R.id.control_staff_control_container);
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
        level_1.setTextColor(getColor(R.color.red));//set the text color as red because level 1 is selected by default
        level_2.setOnClickListener(this);
        //create add button
        add = findViewById(R.id.control_add);
        add.setOnClickListener(this);
        //create info button
        delete = findViewById(R.id.control_delete);
        delete.setOnClickListener(this);
        //create arts button
        change = findViewById(R.id.control_change);
        change.setOnClickListener(this);
        //create setting button
        more = findViewById(R.id.control_more);
        more.setOnClickListener(this);

        //logout
        logout=findViewById(R.id.control_logoutButton);
        builder =new AlertDialog.Builder(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Logout Confirm").setMessage("Do you want to logout?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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
            case R.id.control_add:
                add.setTextColor(getColor(R.color.red));
                delete.setTextColor(getColor(R.color.navy_blue));
                change.setTextColor(getColor(R.color.navy_blue));
                more.setTextColor(getColor(R.color.navy_blue));
                AddDialog(view);
                break;
            //press info button to show the information about the museum like operating hours and admission
            case R.id.control_delete:
                add.setTextColor(getColor(R.color.navy_blue));
                delete.setTextColor(getColor(R.color.red));
                change.setTextColor(getColor(R.color.navy_blue));
                more.setTextColor(getColor(R.color.navy_blue));
                break;
            //press art button to browse all the art
            case R.id.control_change:
                delete.setTextColor(getColor(R.color.navy_blue));
                add.setTextColor(getColor(R.color.navy_blue));
                change.setTextColor(getColor(R.color.red));
                more.setTextColor(getColor(R.color.navy_blue));
                break;
            //press setting button to show settings page. Change font size
            case R.id.control_more:
                delete.setTextColor(getColor(R.color.navy_blue));
                add.setTextColor(getColor(R.color.navy_blue));
                change.setTextColor(getColor(R.color.navy_blue));
                more.setTextColor(getColor(R.color.red));
                break;
        }
    }

    //popup window for add function
    public void AddDialog(View v){
        mydialog.setContentView(R.layout.adding_closet);
        ImageView txtClose =(ImageView) mydialog.findViewById(R.id.add_Cancel);
        Button OK = mydialog.findViewById(R.id.add_OK);
        Spinner addFloorSelection = mydialog.findViewById(R.id.add_floor_selection);
        EditText addXNumber = mydialog.findViewById(R.id.add_X_number);
        EditText addYNumber = mydialog.findViewById(R.id.add_Y_number);
        EditText addLengthNumber = mydialog.findViewById(R.id.add_length_number);
        EditText addWidthNumber = mydialog.findViewById(R.id.add_width_number);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addFloorSelectionString = addFloorSelection.getSelectedItem().toString();
                int floor = Integer.parseInt(addFloorSelectionString.substring(6));
                float x = Float.parseFloat(addXNumber.getText().toString());
                float y = Float.parseFloat(addYNumber.getText().toString());
                float length = Float.parseFloat(addLengthNumber.getText().toString());
                float width = Float.parseFloat(addWidthNumber.getText().toString());
                DatabaseHelper.addCase(floor, x, y, length, width);
                mydialog.dismiss();
            }
        });
        mydialog.show();
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }






}
