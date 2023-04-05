package com.example.museumapp.map;

import android.view.ViewGroup;

import com.example.museumapp.objects.MapPin;

import java.util.ArrayList;
import java.util.List;

public interface Floor {
    ArrayList<Edge> getEdges();
    void createPins(List<MapPin> list, ViewGroup parentView);
    void pinInvisible();
    void pinVisible();
}
