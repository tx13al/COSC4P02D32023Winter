package com.example.museumapp;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.museumapp.objects.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {
    ArrayList<Item> items;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = ItemListSingleton.getInstance().getItemList();
        Button back = findViewById(R.id.search_bar_item_list_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setContentView(R.layout.search_bar_item_list_layout);
        LinearLayout searchBarItemListLayout =
                this.findViewById(R.id.search_bar_item_list_scroll_view_linear_layout);
        //update the displaying showcase in main, and load the showcase items if not loaded.
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (Item item: items) {
            //Dynamically set linear layout for each item and add them to the scroll.
            View searchBarItemLayout = layoutInflater.inflate(R.layout.search_bar_item_layout,
                    searchBarItemListLayout, false);
            //Image setting
            ImageView imageView = searchBarItemLayout.findViewById(R.id.search_bar_item_image);
            Picasso.get()
                    .load(item.getImageUrl())
                    .resize(0, 350)
                    .centerCrop()
                    .into(imageView);
            //Text setting
            TextView text = searchBarItemLayout.findViewById(R.id.search_bar_item_name);
            text.setText(item.getName());
            //when click the item, it will give an item back to the previous activity.
            searchBarItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemSingleton.getInstance().setItem(item);
                    finish();
                }
            });
        }
    }
}
