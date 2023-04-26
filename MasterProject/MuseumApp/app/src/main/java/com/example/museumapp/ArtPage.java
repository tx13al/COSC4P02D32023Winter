package com.example.museumapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museumapp.objects.Item;
import com.example.museumapp.objects.ItemList;

import java.util.ArrayList;

public class ArtPage extends AppCompatActivity {

    private Item item;
    private ArrayList<Item> itemList;
    private RecyclerView itemContainer;
    private ProgressBar loadingIndicator;
    private int currentPage = 0;
    private int pageSize = 5;
    private Button loadMoreBtn;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);
        itemList = DatabaseHelper.getItemList(0,5);
        adapter = new ItemAdapter(itemList); // assign adapter to the field
        itemContainer = findViewById(R.id.item_list);
        itemContainer.setAdapter(adapter);
        itemContainer.setLayoutManager(new LinearLayoutManager(this));
        loadingIndicator = findViewById(R.id.loading_indicator);
        loadMoreBtn = findViewById(R.id.load_more_btn);

        //initiate toolbar to handle back action
        Toolbar toolbar = findViewById(R.id.art_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //load page
        loadPage();
        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPage();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadPage() {
        loadingIndicator.setVisibility(View.VISIBLE);
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, itemList.size());
        ArrayList<Item> sublist = new ArrayList<>(itemList.subList(start, end));
        adapter.updateItemList(sublist);
        if (end == itemList.size()) {
            loadMoreBtn.setVisibility(View.GONE);
        }

        loadingIndicator.setVisibility(View.GONE);
        currentPage++;
    }
}
