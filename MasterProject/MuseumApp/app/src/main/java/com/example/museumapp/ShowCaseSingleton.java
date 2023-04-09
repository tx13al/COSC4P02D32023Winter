package com.example.museumapp;

import com.example.museumapp.objects.ShowCase;
import java.util.ArrayList;

public class ShowCaseSingleton {
    private static ShowCaseSingleton instance = null;
    private ArrayList<ShowCase> showCases = null;

    public static ShowCaseSingleton getInstance() {
        if (instance == null) {
            instance = new ShowCaseSingleton();
        }
        return instance;
    }

    public ArrayList<ShowCase> getShowCases() {
        return showCases;
    }

    public void setShowCases(ArrayList<ShowCase> showCases) {
        this.showCases = showCases;
    }
}
