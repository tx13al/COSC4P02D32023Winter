package com.example.museumapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import com.example.museumapp.Search.SearchAdapter;
import com.example.museumapp.Search.SearchBar;
import com.example.museumapp.Search.SearchItem;
import com.example.museumapp.map.*;
import com.example.museumapp.objects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View floor_1, floor_2;
    private FrameLayout mainContainer;
    private Button login, level_1, level_2, home, info, arts, setting;
    private ArrayList<ShowCase> showCases;
    private FirstFloor firstFloor;
    private SecondFloor secondFloor;
    private MapPin displayingMapPin = null;
    private SearchAdapter searchAdapter;
    private boolean Connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check internet state.
        showCases = ShowCaseSingleton.getInstance().getShowCases();  //get all cases from previous activity.
        Connected = checkConnection();
        System.out.println("Connection status: " + Connected);

        if ((Connected) && (showCases.isEmpty())){
            showCases = DatabaseHelper.getAllEmptyCases();  //get all cases from database and set empty,
            //this can also update the show cases for each start of mainActivity
        }

        setContentView(R.layout.activity_main); //set the layout

        //main container and two floor views
        mainContainer = findViewById(R.id.main_container);
        LayoutInflater inflater = LayoutInflater.from(this);

        floor_1 = inflater.inflate(R.layout.activity_floor_one,null);
        firstFloor = floor_1.findViewById(R.id.firstFloor);
        mainContainer.addView(floor_1);
        floor_1.setVisibility(View.VISIBLE);

        floor_2 = inflater.inflate(R.layout.activity_floor_two, null);
        secondFloor = floor_2.findViewById(R.id.secondFloor);
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

        // Find the AutoCompleteTextView view
        AutoCompleteTextView actv = findViewById(R.id.search_bar);

        if(Connected) {
            firstFloor.addShowCases(showCases);
            firstFloor.setPinsVisibility();
            secondFloor.addShowCases(showCases);
            secondFloor.setPinsVisibility();
            login.setOnClickListener(new Login(MainActivity.this));
            // Create a new instance of SearchBar and pass the necessary arguments
            SearchBar searchBar = new SearchBar(this, actv);
        }
    }

    public boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
        if (capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))) {
            return true;
        } else {
            return false;
        }
    }

    //get the show case with items in it. (For efficiency, we will check if the showCase has been updated.)
    public void getShowCase(ShowCase showCase, MapPin mapPin) {
        this.displayingMapPin = mapPin;
        if (!showCase.getIsSet()) {
            showCase.setItems(DatabaseHelper.getAllItemsOfShowCase(showCase.getClosetID()));
        }
    }

    public ArrayList<ShowCase> getShowCases() {return showCases;}

    public MapPin getDisplaying() {
        return displayingMapPin;
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

    private void shutShowCaseItemList() {
        HorizontalScrollView showCaseItemListScrollView = findViewById(R.id.showCase_item_list_scrollView);
        showCaseItemListScrollView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view){
        shutShowCaseItemList();
        switch (view.getId()){
            //press Level 1 button change to level 1 floor plan
            case R.id.floorOneButton:
                floor_1.setVisibility(View.VISIBLE);
                floor_2.setVisibility(View.GONE);
                if (Connected) {
                    firstFloor.setPinsVisibility();
                    secondFloor.setPinsVisibility();
                }
                level_1.setTextColor(getColor(R.color.red));
                level_2.setTextColor(getColor(R.color.navy_blue));
                break;
            //press Level 2 button change to level 2 floor plan
            case R.id.floorTwoButton:
                floor_1.setVisibility(View.GONE);
                floor_2.setVisibility(View.VISIBLE);
                if (Connected) {
                    firstFloor.setPinsVisibility();
                    secondFloor.setPinsVisibility();
                }
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
}