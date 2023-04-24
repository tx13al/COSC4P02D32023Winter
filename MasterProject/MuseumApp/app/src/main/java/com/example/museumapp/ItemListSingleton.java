package com.example.museumapp;
import com.example.museumapp.objects.Item;
import java.util.ArrayList;

public class ItemListSingleton {
    private static ItemListSingleton instance = null;
    private ArrayList<Item> items = null;

    public static ItemListSingleton getInstance() {
        if (instance == null) {
            instance = new ItemListSingleton();
        }
        return instance;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> tmp = items;
        items = null;
        return tmp;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
