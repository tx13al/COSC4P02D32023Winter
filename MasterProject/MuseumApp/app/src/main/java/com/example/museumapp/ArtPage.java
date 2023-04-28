package com.example.museumapp;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museumapp.objects.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtPage extends AppCompatActivity {

    private ArrayList<Item> itemList;
    private RecyclerView itemContainer;
    private ProgressBar loadingIndicator;
    private int currentPage = 0;
    private int pageSize = 5;
    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemList = DatabaseHelper.getItemList(0,1760);
        adapter = new ItemAdapter(itemList, this::showItemDialog);
        setContentView(R.layout.activity_art);
        setTitle("");
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

        if (end > start) {
            ArrayList<Item> sublist = new ArrayList<>(itemList.subList(start, end));

            if (currentPage == 0) {
                adapter.updateItemList(sublist);
            } else {
                adapter.addItems(sublist);
            }
        }

        loadingIndicator.setVisibility(View.GONE);
        currentPage++;
    }


    private void showItemDialog(Item item) {
        Dialog itemDialog = new Dialog(this);
        itemDialog.setContentView(R.layout.main_dialog_item_detail);
        ImageView dialogDismiss = itemDialog.findViewById(R.id.main_dialog_item_detail_dismiss);
        dialogDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                itemDialog.dismiss();
            }
        });
        ImageView itemImageView = itemDialog.findViewById(R.id.item_detail_image_view);
        TextView itemNameTextView = itemDialog.findViewById(R.id.item_detail_name_text_view);
        TextView itemDescriptionTextView = itemDialog.findViewById(R.id.item_detail_description_text_view);
        TextView itemUrlTextView = itemDialog.findViewById(R.id.item_detail_url_text_view);
        Picasso.get()
                .load(item.getImageUrl())
                .resize(600, 0)
                .centerCrop()
                .into(itemImageView);
        itemNameTextView.setText(item.getName());

        TextView startYear = itemDialog.findViewById(R.id.start_year);
        startYear.setVisibility(View.GONE);
        if (item.getStartYear() != 0) {
            startYear.setVisibility(View.VISIBLE);
            startYear.setText("Start Year: " + item.getStartYear());
        }
        //if possible, display the name of end year.
        TextView endYear = itemDialog.findViewById(R.id.end_year);
        endYear.setVisibility(View.GONE);
        if (item.getEndYear() != 0) {
            endYear.setVisibility(View.VISIBLE);
            endYear.setText("End Year: " + item.getEndYear());
        }
        itemDescriptionTextView.setText(item.getDescription());
        itemUrlTextView.setText(item.getItemUrl());
        itemDialog.show();
        itemDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
