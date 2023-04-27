package com.example.museumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.museumapp.Search.SearchBar;
import com.example.museumapp.map.*;
import com.example.museumapp.objects.*;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View floor_1, floor_2;
    private FirstFloor firstFloor;
    private SecondFloor secondFloor;
    private ArrayList<Edge> stairs;
    private SearchBar searchBar;
    private FrameLayout mainContainer;
    private Button login, level_1, level_2, home, info, arts, setting;
    private ArrayList<ShowCase> showCases;
    private MapPin displayingMapPin = null;
    private MapPin from = null;
    private MapPin to = null;
    private ArrayList<POI> POIs;
    private boolean Connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showCases = ShowCaseSingleton.getInstance().getShowCases();  //get all cases from previous activity.
        POIs = createPOIs(); //get all POIs (toilets, exits, etc.)
        displayPOIs(); //display POIs on the map

        //Check internet state.
        Connected = checkConnection();
        System.out.println("Connection status: " + Connected);

        if ((Connected) && (showCases == null)) {
            showCases = DatabaseHelper.getAllEmptyCases();  //get all cases from database and set empty,
            //this can also update the show cases for each start of mainActivity
        }

        setContentView(R.layout.activity_main); //set the layout

        //main container and two floor views
        mainContainer = findViewById(R.id.main_container);
        LayoutInflater inflater = LayoutInflater.from(this);

        floor_1 = inflater.inflate(R.layout.activity_floor_one, null);
        firstFloor = floor_1.findViewById(R.id.firstFloor);
        mainContainer.addView(floor_1);
        floor_1.setVisibility(View.VISIBLE);

        floor_2 = inflater.inflate(R.layout.activity_floor_two, null);
        secondFloor = floor_2.findViewById(R.id.secondFloor);
        mainContainer.addView(floor_2);

        //initialize the stairs locations
        stairs = new ArrayList<Edge>();
        Edge stair1 = new Edge(82, 847, 1, 163);
        Edge stair2 = new Edge(496, 1260, 588, 296);
        stairs.add(stair1);

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

        if (Connected) {
            firstFloor.addShowCases(showCases);
            firstFloor.setPinsVisibility();
            secondFloor.addShowCases(showCases);
            secondFloor.setPinsVisibility();
            login.setOnClickListener(new Login(MainActivity.this));
            // Create a new instance of SearchBar and pass the necessary arguments
            searchBar = new SearchBar(this, actv);
        }
    }

    private boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
        if (capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))) {
            return true;
        } else {
            return false;
        }
    }

    //get the show case with items in it. (For efficiency, we will check if the showCase has been updated.)
    private void getShowCase(ShowCase showCase, MapPin mapPin) {
        this.displayingMapPin = mapPin;
        if (!showCase.getIsSet()) {
            showCase.setItems(DatabaseHelper.getAllItemsOfShowCase(showCase.getClosetID()));
        }
    }

    public ArrayList<ShowCase> getShowCases() {
        return showCases;
    }

    private void shutShowCaseItemList() {
        HorizontalScrollView showCaseItemListScrollView = findViewById(R.id.showCase_item_list_scrollView);
        showCaseItemListScrollView.setVisibility(View.INVISIBLE);
    }

    private void viewFirstFloor() {
        floor_1.setVisibility(View.VISIBLE);
        floor_2.setVisibility(View.GONE);
        level_1.setTextColor(getColor(R.color.red));
        level_2.setTextColor(getColor(R.color.navy_blue));
        firstFloor.setPinsVisibility();
        firstFloor.invalidate();
    }

    private void viewSecondFloor() {
        floor_1.setVisibility(View.GONE);
        floor_2.setVisibility(View.VISIBLE);
        level_1.setTextColor(getColor(R.color.navy_blue));
        level_2.setTextColor(getColor(R.color.red));
        secondFloor.setPinsVisibility();
        secondFloor.invalidate();
    }

    //display the detail of the item selected by a dialog.
    public void displayItemDialog(Item item) {
        Dialog itemDetailDialog = new Dialog(this);
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
        TextView startYear = itemDetailDialog.findViewById(R.id.start_year);
        startYear.setVisibility(View.GONE);
        if (item.getStartYear() != 0) {
            startYear.setVisibility(View.VISIBLE);
            startYear.setText("Start Year: " + item.getStartYear());
        }
        //if possible, display the name of end year.
        TextView endYear = itemDetailDialog.findViewById(R.id.end_year);
        endYear.setVisibility(View.GONE);
        if (item.getEndYear() != 0) {
            endYear.setVisibility(View.VISIBLE);
            endYear.setText("End Year: " + item.getEndYear());
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
        itemDetailDialog.show();
        itemDetailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void removeCaseEdges(ShowCase showCase) {
        if (showCase.getFloorNum() == 1) {
            firstFloor.removeShowCaseEdges(showCase);
        }
        if (showCase.getFloorNum() == 2) {
            secondFloor.removeShowCaseEdges(showCase);
        }
    }

    private void addCaseEdges(ShowCase showCase) {
        if (showCase.getFloorNum() == 1) {
            firstFloor.addShowCaseEdges(showCase);
        }
        if (showCase.getFloorNum() == 2) {
            secondFloor.addShowCaseEdges(showCase);
        }
    }

    private void navigate() {
        removeCaseEdges(from.getShowCase());
        removeCaseEdges(to.getShowCase());
        Navigation navigation = new Navigation(firstFloor.getEdges(), secondFloor.getEdges(), stairs,
                from.getShowCase().getX(), from.getShowCase().getY(), from.getShowCase().getFloorNum(),
                to.getShowCase().getX(), to.getShowCase().getY(), to.getShowCase().getFloorNum());
        addCaseEdges(from.getShowCase());
        addCaseEdges(to.getShowCase());
        firstFloor.setNavigationEdges(navigation.getPath1());
        secondFloor.setNavigationEdges(navigation.getPath2());
    }

    private void setNavigationStart(MapPin mapPin) {
        if (from != null) {
            from.setDefault();
        }
        from = mapPin;
        from.setStart();
        if (to != null) {
            navigate();
        }
    }

    private void setNavigationEnd(MapPin mapPin) {
        if (to != null) {
            to.setDefault();
        }
        to = mapPin;
        to.setEnd();
        if (from != null) {
            navigate();
        }
    }

    private void navigateDialog(MapPin mapPin) {
        Dialog navigateDialog = new Dialog(this);
        navigateDialog.setContentView(R.layout.main_dialog_navigation);
        Button start = navigateDialog.findViewById(R.id.navigation_start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNavigationStart(mapPin);
                navigateDialog.dismiss();
            }
        });
        Button end = navigateDialog.findViewById(R.id.navigation_end_button);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNavigationEnd(mapPin);
                navigateDialog.dismiss();
            }
        });
        navigateDialog.show();
    }

    private void removeAllViewsExcept(LinearLayout layout, View view) {
        int childCount = layout.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = layout.getChildAt(i);
            if (child != view) {
                layout.removeView(child);
            }
        }
    }

    public void displayMapPinItemList(MapPin mapPin) {
        HorizontalScrollView showCaseItemListScrollView =
                this.findViewById(R.id.showCase_item_list_scrollView);
        showCaseItemListScrollView.setVisibility(View.VISIBLE);
        if (displayingMapPin != mapPin) {
            //Make sure this Pin is not displaying. (avoid duplicate adding items to scroll view.)
            LinearLayout showCaseItemListLayout =   //container for the items
                    this.findViewById(R.id.showCase_item_list_scrollView_linear);
            this.getShowCase(mapPin.getShowCase(), mapPin); //get item list updated without duplicate.
            Button navigation = findViewById(R.id.showCase_item_list_navigation);
            navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigateDialog(mapPin);
                }
            });
            if (displayingMapPin != null) { //clear the container for another selected showCase.
                removeAllViewsExcept(showCaseItemListLayout, navigation);
            }
            //update the displaying showcase in main, and load the showcase items if not loaded.
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            for (Item item : mapPin.getShowCase().getItems()) {
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
                        displayItemDialog(item);
                    }
                });
                showCaseItemListLayout.addView(showCaseItemLayout);
            }
        }
    }

    //display the searched item, the dialog and the mapPin list.
    public void displaySearchedItem(Item item) {
        int sid = item.getClosetID();
        if (sid != 0) {
            ShowCase container = null;
            for (ShowCase showCase : showCases) {
                if (showCase.getClosetID() == sid) {
                    container = showCase;
                    break;
                }
            }
            MapPin display = null;
            if (container.getFloorNum() == 1) {
                viewFirstFloor();
                for (MapPin mapPin : firstFloor.getPinList()) {
                    if (mapPin.getShowCase() == container) {
                        display = mapPin;
                        break;
                    }
                }
            }
            if (container.getFloorNum() == 2) {
                viewSecondFloor();
                for (MapPin mapPin : secondFloor.getPinList()) {
                    if (mapPin.getShowCase() == container) {
                        display = mapPin;
                        break;
                    }
                }
            }
            displayMapPinItemList(display);
        }
        displayItemDialog(item);
    }

    @Override
    public void onClick(View view) {
        shutShowCaseItemList();
        switch (view.getId()) {
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
                home.setTextColor(getColor(R.color.selected));
                info.setTextColor(getColor(R.color.unselected));
                arts.setTextColor(getColor(R.color.unselected));
                setting.setTextColor(getColor(R.color.unselected));
                break;
            //press info button to show the information about the museum like operating hours and admission
            case R.id.info:
                info.setTextColor(getColor(R.color.selected));
                home.setTextColor(getColor(R.color.unselected));
                arts.setTextColor(getColor(R.color.unselected));
                setting.setTextColor(getColor(R.color.unselected));
                Intent infoIntent = new Intent(MainActivity.this, InfoPage.class);
                startActivity(infoIntent);
                info.setTextColor(getColor(R.color.unselected));
                home.setTextColor(getColor(R.color.selected));
                break;
            //press art button to browse all the art
            case R.id.art:
                info.setTextColor(getColor(R.color.unselected));
                home.setTextColor(getColor(R.color.unselected));
                arts.setTextColor(getColor(R.color.selected));
                setting.setTextColor(getColor(R.color.unselected));
                Intent artIntent = new Intent(MainActivity.this, ArtPage.class);
                startActivity(artIntent);
                arts.setTextColor(getColor(R.color.unselected));
                home.setTextColor(getColor(R.color.selected));
                break;
            //press setting button to show settings page. Change font size
            case R.id.settings:
                info.setTextColor(getColor(R.color.unselected));
                home.setTextColor(getColor(R.color.unselected));
                arts.setTextColor(getColor(R.color.unselected));
                setting.setTextColor(getColor(R.color.selected));
                break;
        }
    }

    //This method is used to generate all the bathrooms and exits on the map.
    public ArrayList<POI> createPOIs() {
        ArrayList<POI> POIs = new ArrayList<POI>();

        //Manually generate exits
        POIs.add(new POI(1, 1, 45, 60, 0));
        POIs.add(new POI(2, 1, 350, 270, 0));

        //Manually generate bathrooms
        POIs.add(new POI(3, 1, 120, 120, 1));
        POIs.add(new POI(4, 1, 220, 120, 1));
        POIs.add(new POI(5, 1, 270, 200, 1));

        return POIs;
    }

    public void displayPOIs() {

    }
}