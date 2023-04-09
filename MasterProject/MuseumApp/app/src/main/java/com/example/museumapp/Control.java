package com.example.museumapp;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.museumapp.map.FirstFloor;
import com.example.museumapp.map.SecondFloor;
import com.example.museumapp.objects.ShowCase;

import java.util.ArrayList;

public class Control extends AppCompatActivity implements View.OnClickListener{
    private View floor_1, floor_2;
    private FrameLayout control_mainContainer;
    ImageButton logout;
    private Button level_1, level_2, add, delete, change, more;
    private ArrayList<ShowCase> showCases;
    FirstFloor firstFloor;
    SecondFloor secondFloor;
    Dialog mydialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showCases = DatabaseHelper.getAllEmptyCases();  //get all cases from database and set empty,
        // this can also update the show cases for each start of mainActivity
        setContentView(R.layout.activity_control);
        mydialog= new Dialog(this);
        builder =new AlertDialog.Builder(this);

        //main container and two floor views
        control_mainContainer = findViewById(R.id.control_main_container);
        LayoutInflater inflater = LayoutInflater.from(this);

        floor_1 = inflater.inflate(R.layout.activity_floor_one,null);
        firstFloor = floor_1.findViewById(R.id.firstFloor);
        firstFloor.addShowCases(showCases);
        control_mainContainer.addView(floor_1);
        floor_1.setVisibility(View.VISIBLE);
        firstFloor.setPinsVisibility();

        floor_2 = inflater.inflate(R.layout.activity_floor_two, null);
        secondFloor = floor_2.findViewById(R.id.secondFloor);
        secondFloor.addShowCases(showCases);
        control_mainContainer.addView(floor_2);
        secondFloor.setPinsVisibility();

        //create floor buttons
        level_1 = findViewById(R.id.control_floorOneButton);
        level_2 = findViewById(R.id.control_floorTwoButton);
        level_1.setOnClickListener(this);
        level_1.setTextColor(getColor(R.color.red));//set the text color as red because level 1 is selected by default
        level_2.setOnClickListener(this);
        //create add button
        add = findViewById(R.id.control_add);
        add.setOnClickListener(this);
        //create delete button
        delete = findViewById(R.id.control_delete); //delete function: delete the closet that been choose
        delete.setOnClickListener(this);
        //create change button
        change = findViewById(R.id.control_change);
        change.setOnClickListener(this);
        //create more button
        more = findViewById(R.id.control_more);
        more.setOnClickListener(this);

        //logout
        logout=findViewById(R.id.control_logoutButton);
        logout.setOnClickListener(this);

        // Find the AutoCompleteTextView view
        AutoCompleteTextView actv = Control.this.findViewById(R.id.search_bar);
        // Create a new instance of SearchBar and pass the necessary arguments
      //  SearchBar searchBar = new SearchBar(this, actv);
    }

    //popup a dialog for logout function.
    private void logOut() {
        builder.setTitle("Logout Confirm").setMessage("Do you want to logout?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Control.this, "Logout Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Control.this, MainActivity.class);
                startActivity(intent);
                Control.this.finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }

    //popup a dialog for add function
    private void AddDialog(View v){
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
                String addXNumberString = addXNumber.getText().toString();
                String addYNumberString = addYNumber.getText().toString();
                String addLengthNumberString = addLengthNumber.getText().toString();
                String addWidthNumberString = addWidthNumber.getText().toString();
                if (addXNumberString.isEmpty() || addYNumberString.isEmpty() ||
                        addLengthNumberString.isEmpty() || addWidthNumberString.isEmpty()) {
                    Toast.makeText(Control.this.getApplicationContext(),
                            "Blank input!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                float x = Float.parseFloat(addXNumberString);
                float y = Float.parseFloat(addYNumberString);
                float length = Float.parseFloat(addLengthNumberString);
                float width = Float.parseFloat(addWidthNumberString);
                if (floor == 1) {
                    if (DatabaseHelper.addCase(floor, x, y, length, width, firstFloor.getEdges())) {
                        Toast.makeText(Control.this.getApplicationContext(),
                                "Adding successfully!", Toast.LENGTH_SHORT).show();
                        mydialog.dismiss();
                    }
                    else {
                        Toast.makeText(Control.this.getApplicationContext(),
                                "Invalid input!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (floor == 2) {
                    if (DatabaseHelper.addCase(floor, x, y, length, width, secondFloor.getEdges())) {
                        Toast.makeText(Control.this.getApplicationContext(),
                                "Adding successfully!", Toast.LENGTH_SHORT).show();
                        mydialog.dismiss();
                    } else {
                        Toast.makeText(Control.this.getApplicationContext(),
                                "Invalid input!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mydialog.show();
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void deleteShowCase() {
        if(false) {
            builder.setTitle("Deletion Confirm").setMessage("Are you sure to delete??").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //DatabaseHelper.deleteCase(sid);
                    dialogInterface.dismiss();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).show();
        }
        else{
            Toast.makeText(Control.this.getApplicationContext(), "No closet choose", Toast.LENGTH_SHORT).show();
        }
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
            //press the logout button to log out.
            case R.id.control_logoutButton:
                logOut();
                break;
            //press add button to add a showCase to the map.
            case R.id.control_add:
                add.setTextColor(getColor(R.color.red));
                delete.setTextColor(getColor(R.color.navy_blue));
                change.setTextColor(getColor(R.color.navy_blue));
                more.setTextColor(getColor(R.color.navy_blue));
                AddDialog(view);
                add.setTextColor(getColor(R.color.navy_blue));
                break;
            //press info button to show the information about the museum like operating hours and admission
            case R.id.control_delete:
                add.setTextColor(getColor(R.color.navy_blue));
                delete.setTextColor(getColor(R.color.red));
                change.setTextColor(getColor(R.color.navy_blue));
                more.setTextColor(getColor(R.color.navy_blue));
                deleteShowCase();
                delete.setTextColor(getColor(R.color.navy_blue));
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
}
