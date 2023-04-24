package com.example.museumapp;
import com.example.museumapp.Search.SearchBar;

public class SearchBarSingleton {
    private static SearchBarSingleton instance = null;
    private SearchBar searchBar = null;

    public static SearchBarSingleton getInstance() {
        if (instance == null) {
            instance = new SearchBarSingleton();
        }
        return instance;
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }

    public void setSearchBar(SearchBar searchBar) {
        this.searchBar = searchBar;
    }

}
