package com.example.museumapp;
import com.example.museumapp.Search.SearchBar;

public class searchBarSingleton {
    private static searchBarSingleton instance = null;
    private SearchBar searchBar;

    public static searchBarSingleton getInstance() {
        if (instance == null) {
            instance = new searchBarSingleton();
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
