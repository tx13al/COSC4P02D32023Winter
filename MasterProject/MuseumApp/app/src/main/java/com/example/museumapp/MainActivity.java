package com.example.museumapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View floor_1, floor_2, dimView;
    private FrameLayout mainContainer;
    private ConstraintLayout mainScreen;
    private Button login, level_1, level_2, home, info, arts, setting;
    private FirstFloor firstFloor;
    private SecondFloor secondFloor;

    List<MapPin> floor2PinList = new ArrayList<>();    // storing the map pin objects
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainScreen = findViewById(R.id.main_screen);
        //main container and two floor views
        mainContainer = findViewById(R.id.main_container);
        LayoutInflater inflater = LayoutInflater.from(this);

        floor_1 = inflater.inflate(R.layout.activity_floor_one,null);
        floor_1.setVisibility(View.VISIBLE);
        mainContainer.addView(floor_1);

        floor_2 = inflater.inflate(R.layout.activity_floor_two, null);
        mainContainer.addView(floor_2);

        //create floor buttons
        level_1 = findViewById(R.id.floorOneButton);
        level_2 = findViewById(R.id.floorTwoButton);
        level_1.setOnClickListener(this);
        level_1.setTextColor(getColor(R.color.red));//set the text color as red because level 1 is selected by default
        level_2.setOnClickListener(this);
        //create home button
        home = findViewById(R.id.home);
        home.setOnClickListener(this);
        home.setTextColor(getColor(R.color.red));//set the text color as red because home is selected by default
        //create info button
        info = findViewById(R.id.info);
        info.setOnClickListener(this);
        //create arts button
        arts = findViewById(R.id.art);
        arts.setOnClickListener(this);
        //create setting button
        setting = findViewById(R.id.settings);
        setting.setOnClickListener(this);
        //create Login button
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(new Login(MainActivity.this));

        // Find the AutoCompleteTextView view
        AutoCompleteTextView actv = findViewById(R.id.search_bar);
        // Create a new instance of SearchBar and pass the necessary arguments
        SearchBar searchBar = new SearchBar(this, actv);


// String.xml using below

    //    String[] stringArray = getResources().getStringArray(R.array.countries_array);
 //       AutoCompleteTextView actv = findViewById(R.id.search_bar);
  //      SearchBar searchBar = new SearchBar(this, stringArray, actv);


        // assuming loading data from database here
        Item item1 = new Item("123",
                               "item",
                           "this is an item. ",
                             1999,
                              2000,
                              "www",
                             "www.",
                              1);
        List<Item> list = new ArrayList<>();
        list.add(item1);
        ShowCase showCase1 = new ShowCase(1, 5, 5, 500, 500, 2, list);

        // Initiate pins
        Drawable pinIcon = getResources().getDrawable(R.drawable.location);
        MapPin pin = new MapPin(pinIcon, showCase1, this);
        floor2PinList.add(pin);

        secondFloor = floor_2.findViewById(R.id.secondFloor);
        secondFloor.createPins(floor2PinList, mainContainer);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //press Level 1 button change to level 1 floor plan
            case R.id.floorOneButton:
                floor_1.setVisibility(View.VISIBLE);
                floor_2.setVisibility(View.GONE);

                secondFloor.pinInvisible();

                level_1.setTextColor(getColor(R.color.red));
                level_2.setTextColor(getColor(R.color.navy_blue));
                break;
            //press Level 2 button change to level 2 floor plan
            case R.id.floorTwoButton:
                floor_1.setVisibility(View.GONE);
                floor_2.setVisibility(View.VISIBLE);

                secondFloor.pinVisible();

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
                Intent infoIntent = new Intent(MainActivity.this, InfoPage.class);
                startActivity(infoIntent);
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
   // search filter
    private void filterSearchItems(String query) {
        List<SearchItem> filteredSearchItems = new ArrayList<>();
        SearchItem[] allSearchItems = new SearchItem[0];

        for (SearchItem searchItem : allSearchItems) {
            if (searchItem.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredSearchItems.add(searchItem);
            }
        }
        searchAdapter.setSearchItems(filteredSearchItems);
    }

}