package com.example.museumapp.Search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.museumapp.DatabaseHelper;
import com.example.museumapp.R;

import java.util.ArrayList;

public class SearchBar extends AppCompatActivity {

    private Activity DialogView;


    public SearchBar(Context context, AutoCompleteTextView view) {
        // Retrieve data from database using getAllNoDuplicateNames() method
        ArrayList<String> arr = DatabaseHelper.getAllNoDuplicateNames(); //Create ArrayAdapter
        ArrayAdapter<String> completion = new ArrayAdapter<>(
                context, android.R.layout.simple_dropdown_item_1line, arr);
        // Set the adapter to the AutoCompleteTextView
        AutoCompleteTextView actv = view;
        actv.setAdapter(completion);
        actv.setThreshold(1);
        actv.setInputType(InputType.TYPE_CLASS_TEXT);
        // Set an item click listener
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(context, "Item "+selectedItem+ " found. ", Toast.LENGTH_SHORT).show();
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
//

    }
}