package com.example.museumapp;

import android.content.Context;

public class ContextSingleton {
    private static ContextSingleton instance = null;
    private Context context = null;

    public static ContextSingleton getInstance() {
        if (instance == null) {
            instance = new ContextSingleton();
        }
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
