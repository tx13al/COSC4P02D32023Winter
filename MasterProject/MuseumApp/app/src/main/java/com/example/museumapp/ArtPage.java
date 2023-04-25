package com.example.museumapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.museumapp.objects.Item;
import com.example.museumapp.objects.ItemList;

import java.util.ArrayList;

public class ArtPage extends AppCompatActivity {

    private Item item;
    private ArrayList<Item> itemList;
    private LinearLayout itemContainer;
    private ProgressBar loadingIndicator;
    private int currentPage = 0;
    private int pageSize = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);
        itemContainer = findViewById(R.id.item_container);
        loadingIndicator = findViewById(R.id.loading_indicator);
        //initiate toolbar to handle back action
        Toolbar toolbar = findViewById(R.id.art_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        itemList = DatabaseHelper.getItemList();
        //load page
        loadPage(itemList);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadPage(ArrayList<Item> itemList) {
        loadingIndicator.setVisibility(View.VISIBLE);
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, itemList.size());
        for (int i = start; i < end; i++) {
            Item item = itemList.get(i);
            View itemView = createItemView(item);
            itemContainer.addView(itemView);
        }

        loadingIndicator.setVisibility(View.GONE);
        currentPage++;
    }

    private View createItemView(Item item) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View itemView = inflater.inflate(R.layout.arts_layout, itemContainer, false);
        ImageView itemImage = itemView.findViewById(R.id.item_image);
        TextView itemName = itemView.findViewById(R.id.item_name);
        TextView itemDescription = itemView.findViewById(R.id.item_description);
        //itemImage.setImageResource(item.getImageResourceId());
        itemName.setText(item.getName());
        itemDescription.setText(item.getDescription());
        return itemView;
    }

}
