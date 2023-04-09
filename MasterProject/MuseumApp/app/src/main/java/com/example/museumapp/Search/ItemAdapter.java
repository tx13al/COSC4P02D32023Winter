package com.example.museumapp.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.museumapp.R;
import com.example.museumapp.objects.Item;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private int mResource;

    public ItemAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        ImageView itemImage = convertView.findViewById(R.id.item_image_view);
        TextView itemName = convertView.findViewById(R.id.item_name_view);

        itemImage.setImageResource(item.getImageResource());
        itemName.setText(item.getName());

        return convertView;
    }

}

