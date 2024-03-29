package com.example.museumapp.Search;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.museumapp.ContextSingleton;
import com.example.museumapp.Control;
import com.example.museumapp.DatabaseHelper;
import com.example.museumapp.ItemListActivity;
import com.example.museumapp.ItemListSingleton;
import com.example.museumapp.MainActivity;
import com.example.museumapp.objects.Item;
import java.util.ArrayList;
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
                    if(!enteredText.isEmpty()) {
                        ArrayList<Item> items = DatabaseHelper.searchItemByName(enteredText);
                        displayItems(items);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void displayItems (ArrayList<Item> items) { //display the items in another activity.
        if (items.size() == 0) {
            Toast.makeText(context, "No item found!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (items.size() == 1) {
            Toast.makeText(context, "Item found.", Toast.LENGTH_SHORT).show();
            if (context instanceof MainActivity) {
                ((MainActivity) context).displayItemDialog(items.get(0));
                return;
            }
            if (context instanceof Control) {
                ((Control) context).displayItemDialog(items.get(0));
                return;
            }
        }
        else { //have over 1 items. (display the list to make future selection.)
            Toast.makeText(context, "Item found.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ItemListActivity.class);
            ContextSingleton.getInstance().setContext(context);
            ItemListSingleton.getInstance().setItemList(items);
            context.startActivity(intent);
        }
    }

    public void SetContext(Context context){
        this.context = context;
    }
}
