package com.example.museumapp;

public class SearchItem {
    private String title;
    private String description;

    public SearchItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
