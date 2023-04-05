package com.example.museumapp.map;

import android.view.ViewGroup;

import com.example.museumapp.objects.MapPin;

import java.util.List;

public interface Floor {
    List<Edge> getEdges();
    void createPins(List<MapPin> list, ViewGroup parentView);
    void pinInvisible();
    void pinVisible();
}
