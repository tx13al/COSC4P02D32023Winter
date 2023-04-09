package com.example.museumapp.Search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.museumapp.R;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get the data for the item from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int imageResource = intent.getIntExtra("imageResource", 0);

        // set the item name and image on the detail view
        imageView.setImageResource(imageResource);
        textView.setText(name);
    }
}

