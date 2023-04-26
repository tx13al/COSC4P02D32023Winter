package com.example.museumapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museumapp.objects.Item;
import com.squareup.picasso.Picasso;

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
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateItemList(List<Item> updatedItemList) {
        itemList = updatedItemList;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView itemName;
        private TextView itemDescription;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_description);
        }

        public void bind(Item item) {
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            // Set itemImage using an image loading library, such as Picasso or Glide
            // Picasso.get().load(item.getImageUrl()).into(itemImage);
            Picasso.get()
                    .load(item.getImageUrl())
                    .resize(500,500)
                    .centerCrop()
                    .into(itemImage);
        }
    }
}

