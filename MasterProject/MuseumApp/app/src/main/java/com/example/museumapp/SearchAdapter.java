package com.example.museumapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SearchAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<SearchItem> searchItems;

    public SearchAdapter(List<SearchItem> searchItems) {
        this.searchItems = searchItems;
    }

    public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.search_bar_edit_text);
            descriptionTextView = view.findViewById(R.id.search_bar_edit_text);
        }

        public void bind(SearchItem searchItem) {
            titleTextView.setText(searchItem.getTitle());
            descriptionTextView.setText(searchItem.getDescription());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_bar_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(searchItems.get(position));
    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    public void setSearchItems(List<SearchItem> searchItems) {
        this.searchItems = searchItems;
        notifyDataSetChanged();
    }
}

