package com.example.museumapp;

import com.example.museumapp.objects.ShowCase;

import java.lang.reflect.Array;
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
        ArrayList<ShowCase> tmp;
        tmp = showCases;
        showCases = null;
        return tmp;

    }

    public void setShowCases(ArrayList<ShowCase> showCases) {
        this.showCases = showCases;
    }
}
