package com.example.museumapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museumapp.objects.Item;

import java.util.ArrayList;

public class ArtPage extends AppCompatActivity {

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
        setTitle("");
        itemList = DatabaseHelper.getItemList(0,100);
        adapter = new ItemAdapter(itemList);
        itemContainer = findViewById(R.id.item_list);
        itemContainer.setAdapter(adapter);
        itemContainer.setLayoutManager(new LinearLayoutManager(this));
        itemContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (loadingIndicator.getVisibility() != View.VISIBLE && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadPage();
                }
            }
        });

        loadingIndicator = findViewById(R.id.loading_indicator);

        //initiate toolbar to handle back action
        Toolbar toolbar = findViewById(R.id.art_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //load page
        loadPage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        finish();
    }


    private void loadPage() {
        loadingIndicator.setVisibility(View.VISIBLE);

        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, itemList.size());

        ArrayList<Item> sublist = new ArrayList<>(itemList.subList(start, end));

        if (currentPage == 0) {
            adapter.updateItemList(new ArrayList<>(itemList.subList(0, 5)));
        } else {
            adapter.addItems(sublist);
        }

        if (end == itemList.size()) {
            loadMoreBtn.setVisibility(View.GONE);
        }

        currentPage++;

        loadingIndicator.setVisibility(View.GONE);
    }

}
