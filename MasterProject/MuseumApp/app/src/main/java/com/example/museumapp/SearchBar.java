package com.example.museumapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;

public class SearchBar {
    private final Object searchBar;

    public SearchBar() {
        //search bar
        this.searchBar = getResources().getStringArray(R.array.countries_array);;

        ArrayAdapter<String> completion=new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line,searchBar
        );

        AutoCompleteTextView actv=(AutoCompleteTextView)findViewById(R.id.finishmythought);
        actv.setThreshold(1);
        actv.setAdapter(completion);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position); // 获取选中的联想结果

                AlertDialog.Builder firstDialog=new AlertDialog.Builder(getApplicationContext());
                firstDialog.setTitle("Attention!");
                firstDialog.setIcon(R.mipmap.ic_launcher);
                firstDialog.setMessage("");
                firstDialog.show();
            }
        });
    }

}
