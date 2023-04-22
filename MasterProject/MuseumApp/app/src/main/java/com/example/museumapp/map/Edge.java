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

    public float getFrom_x() {
        return from_x;
    }

    public float getFrom_y() {
        return from_y;
    }

    public float getTo_x() {
        return to_x;
    }

    public float getTo_y() {
        return to_y;
    }

    public boolean equal(Edge edge) {
        return (((edge.from_x == this.from_x) && (edge.from_y == this.from_y) &&
                (edge.to_x == this.to_x) && (edge.to_y == this.to_y)) ||
                ((edge.from_x == this.to_x) && (edge.from_y == this.to_y) &&
                        (edge.to_x == this.from_x) && (edge.to_y == this.from_y)));
    }
}
