package com.example.museumapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchBar extends AppCompatActivity {

    public SearchBar(Context context, String[] arr, SearchView View) {
        //search bar
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        //String[] stringArray = arr;

        ArrayAdapter<String> completion = new ArrayAdapter<>(
                context, android.R.layout.simple_dropdown_item_1line, list
        );

        SearchView searchView = View;

/*        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search action here
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MatrixCursor cursor = new MatrixCursor(new String[] {"_id", "text"});

                for (int i = 0; i < completion.getCount(); i++) {
                    if (completion.getItem(i).toLowerCase().startsWith(newText.toLowerCase())) {
                        cursor.addRow(new Object[] {i, completion.getItem(i)});
                    }
                }
                searchView.getSuggestionsAdapter().changeCursor(cursor);
                if (newText.isEmpty()) {
                    searchView.getSuggestionsAdapter().changeCursor(null);
                }
                return true;
            }
        });*/


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Override onQueryTextSubmit method which is call when submit query is searched
            @Override
            public boolean onQueryTextSubmit(String query) {
                // If the list contains the search query than filter the adapter
                // using the filter method with the query as its argument
                if (list.contains(query)) {
                    completion.getFilter().filter(query);
                } else {
                    // Search query not found in List View
                    Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            // This method is overridden to filter the adapter according
            // to a search query when the user is typing search
            @Override
            public boolean onQueryTextChange(String newText) {
                completion.getFilter().filter(newText);
                return false;
            }
        });

    }



    /*public class CustomSuggestionsAdapter extends CursorAdapter {

        public CustomSuggestionsAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(cursor.getString(cursor.getColumnIndex("text")));
        }
    }*/

}
