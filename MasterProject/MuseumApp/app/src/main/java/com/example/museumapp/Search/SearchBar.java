package com.example.museumapp.Search;
import static com.example.museumapp.DatabaseHelper.searchItemByName;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.museumapp.Control;
import com.example.museumapp.DatabaseHelper;
import com.example.museumapp.ItemListActivity;
import com.example.museumapp.ItemListSingleton;
import com.example.museumapp.ItemSingleton;
import com.example.museumapp.MainActivity;
import com.example.museumapp.R;
import com.example.museumapp.map.FirstFloor;
import com.example.museumapp.map.SecondFloor;
import com.example.museumapp.objects.Item;
import com.example.museumapp.objects.MapPin;
import com.example.museumapp.objects.ShowCase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import android.widget.FrameLayout;

public class SearchBar extends FrameLayout {
    Context context;
    public SearchBar(Context context, AutoCompleteTextView view) {
        super(context);
        this.context = context;
        // Retrieve data from database using getAllNoDuplicateNames() method
        ArrayList<String> arr = DatabaseHelper.getAllNoDuplicateNames(); //Create ArrayAdapter

        ArrayAdapter<String> completion = new ArrayAdapter<>(
                this.context, android.R.layout.simple_dropdown_item_1line, arr);

        // Set the adapter to the AutoCompleteTextView
        AutoCompleteTextView actv = view;
        actv.setAdapter(completion);
        actv.setThreshold(1);
        actv.setInputType(InputType.TYPE_CLASS_TEXT);

        // Set the dropdown height
        //actv.setDropDownHeight(300);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(context, "Item "+selectedItem+ " found. ", Toast.LENGTH_SHORT).show();
                ArrayList<Item> items = DatabaseHelper.searchItemByName(selectedItem);
                displayItems(items);
            }
        });

        // Set an editor action listener
        actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    String enteredText = textView.getText().toString();
                    int position = ((ArrayAdapter<String>) actv.getAdapter()).getPosition(enteredText);
                    if (position >= 0) {
                        // The entered text is in the adapter
                        Toast.makeText(context, "Item "+enteredText+ " found. ", Toast.LENGTH_SHORT).show();
                    } else {
                        // The entered text is not in the adapter
                        Toast.makeText(context, enteredText+" is NOT found", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void mainItemList(int sid) {    //display the showCase item list.
        MainActivity mainActivity = (MainActivity) context;
        ArrayList<ShowCase> showCases = mainActivity.getShowCases();
        ShowCase itemShowCase = null;
        for (ShowCase showCase: showCases) {    //find the showCase of the item.
            if (showCase.getClosetID() == sid) {
                itemShowCase = showCase;
                break;
            }
        }
        ArrayList<MapPin> pinList = null;
        if (itemShowCase.getFloorNum() == 1) {
            mainActivity.viewFirstFloor();
            pinList = mainActivity.getFirstFloor().getPinList();
        }
        if (itemShowCase.getFloorNum() == 2) {
            mainActivity.viewSecondFloor();
            pinList = mainActivity.getSecondFloor().getPinList();
        }
        //find the mapPin for the showCase and display the showCase item list.
        for (MapPin mapPin: pinList) {
            if (mapPin.getShowCase() == itemShowCase) {
                mainActivity.setDisplayingMapPin(mapPin);
                mainActivity.getDisplaying().mainItemList();
                break;
            }
        }
    }

    private void controlItemList(int sid) {    //display the showCase item list.
        Control control = (Control) context;
        ArrayList<ShowCase> showCases = control.getShowCases();
        ShowCase itemShowCase = null;
        for (ShowCase showCase: showCases) {    //find the showCase of the item.
            if (showCase.getClosetID() == sid) {
                itemShowCase = showCase;
                break;
            }
        }
        ArrayList<MapPin> pinList = null;
        if (itemShowCase.getFloorNum() == 1) {
            control.viewFirstFloor();
            pinList = control.getFirstFloor().getPinList();
        }
        if (itemShowCase.getFloorNum() == 2) {
            control.viewSecondFloor();
            pinList = control.getSecondFloor().getPinList();
        }
        //find the mapPin for the showCase and display the showCase item list.
        for (MapPin mapPin: pinList) {
            if (mapPin.getShowCase() == itemShowCase) {
                control.setDisplayingMapPin(mapPin);
                control.getDisplaying().controlItemList();
                break;
            }
        }
    }

    private void displayItemDialog(Item item) {
        Dialog itemDetailDialog = new Dialog(context);
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

    private void displayItems (ArrayList<Item> items) { //display the items in another activity.
        Item searched = null;
        if (items.size() > 1) { //have over 1 items. (display the list to make future selection.)
            Intent intent = new Intent(context, ItemListActivity.class);
            ItemListSingleton.getInstance().setItemList(items);
            context.startActivity(intent);
            searched = ItemSingleton.getInstance().getItem();  //get the selected item back.
            if (searched.getClosetID() != 0) {  //if the item is in a showCase, display the showCase.
                if (context instanceof MainActivity) {
                    mainItemList(searched.getClosetID());
                }
                if (context instanceof Control) {
                    controlItemList(searched.getClosetID());
                }
            }
        }
        displayItemDialog(searched);
    }
}
