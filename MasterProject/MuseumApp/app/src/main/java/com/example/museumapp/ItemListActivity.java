package com.example.museumapp;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.museumapp.objects.Item;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {
    Context previous;
    ArrayList<Item> items;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = ItemListSingleton.getInstance().getItemList();
        previous = ContextSingleton.getInstance().getContext();
        setContentView(R.layout.search_bar_item_list_layout);
        Toolbar toolbar = findViewById(R.id.tool_bar_search_bar_item_list);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ScrollView scrollView = this.findViewById(R.id.search_bar_item_list_scroll_view);
        scrollView.setVisibility(View.VISIBLE);
        LinearLayout searchBarItemListLayout =
                this.findViewById(R.id.search_bar_item_list_scroll_view_linear_layout);
        //update the displaying showcase in main, and load the showcase items if not loaded.
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        //get the height of 75dp.
        int heightInDp = 75;
        int heightInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightInDp, getResources().getDisplayMetrics());
        for (Item item: items) {
            //Dynamically set linear layout for each item and add them to the scroll.
            View searchBarItemLayout = layoutInflater.inflate(R.layout.search_bar_item_layout,
                    searchBarItemListLayout, false);
            //Image setting
            ImageView imageView = searchBarItemLayout.findViewById(R.id.search_bar_item_image);
            Picasso.get()
                    .load(item.getImageUrl())
                    .resize(0, heightInPx)
                    .centerCrop()
                    .into(imageView);
            //Text setting
            TextView text = searchBarItemLayout.findViewById(R.id.search_bar_item_name);
            text.setText(item.getName());
            //when click the item, it will give an item back to the previous activity.
            searchBarItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (previous instanceof MainActivity) {
                        ((MainActivity) previous).displaySearchedItem(item);
                    }
                    if (previous instanceof Control) {
                        ((Control) previous).displaySearchedItem(item);
                    }
                    finish();
                }
            });
            searchBarItemListLayout.addView(searchBarItemLayout);
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

}
