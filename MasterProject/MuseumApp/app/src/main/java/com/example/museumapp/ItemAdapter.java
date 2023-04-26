package com.example.museumapp;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museumapp.objects.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arts_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item);
        if (holder.itemDescription.getLineCount() > 3) {
            holder.moreButton.setVisibility(View.VISIBLE);
        } else {
            holder.moreButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateItemList(List<Item> updatedItemList) {
        itemList = updatedItemList;
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Item> sublist) {
        int startPosition = itemList.size();
        itemList.addAll(sublist);
        notifyItemRangeInserted(startPosition, sublist.size());
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView itemName;
        private TextView itemDescription;
        private TextView moreButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_description);
            moreButton = itemView.findViewById(R.id.more_button);
        }

        public void bind(Item item) {
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            if (itemDescription.getLineCount() > 3) {
                moreButton.setVisibility(View.VISIBLE);
                moreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView descriptionView = itemView.findViewById(R.id.item_description);
                        int maxLines = descriptionView.getMaxLines();
                        if (maxLines == Integer.MAX_VALUE) {
                            descriptionView.setMaxLines(3);
                            moreButton.setText(R.string.more);
                        } else {
                            descriptionView.setMaxLines(Integer.MAX_VALUE);
                            moreButton.setText(R.string.less);
                        }
                    }
                });
            } else {
                moreButton.setVisibility(View.GONE);
            }
            Picasso.get()
                    .load(item.getImageUrl())
                    .resize(500,500)
                    .centerCrop()
                    .into(itemImage);
        }
    }

}
