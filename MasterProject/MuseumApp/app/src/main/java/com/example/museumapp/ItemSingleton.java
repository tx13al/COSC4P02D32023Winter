package com.example.museumapp;

import com.example.museumapp.objects.Item;

public class ItemSingleton {
    private static ItemSingleton instance = null;
    private Item item = null;

    public static ItemSingleton getInstance() {
        if (instance == null) {
            instance = new ItemSingleton();
        }
        return instance;
    }

    public Item getItem() {
        Item tmp = item;
        item = null;
        return tmp;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
