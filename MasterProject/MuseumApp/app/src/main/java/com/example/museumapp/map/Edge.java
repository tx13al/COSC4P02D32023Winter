package com.example.museumapp.map;

public class Edge {
    public float from_x;
    public float from_y;
    public float to_x;
    public float to_y;

    public Edge() {
    }

    public Edge(float from_x, float from_y, float to_x, float to_y) {
        this.from_x = from_x;
        this.from_y = from_y;
        this.to_x = to_x;
        this.to_y = to_y;
    }
}
