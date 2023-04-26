package com.example.museumapp;

import java.util.*;

public class Segment {
    float x1, y1, x2, y2;

    public Segment(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean intersects(Segment other) {
        float x3 = other.x1;
        float y3 = other.y1;
        float x4 = other.x2;
        float y4 = other.y2;
        //vertor = (x2-x1,y2-y1)
        //x1*y2-y1*x2
        float denominator = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));

        if (denominator == 0) {
            // Lines are parallel or coincident
            return false;
        }

        float ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / denominator;
        float ub = (((x2 - x1) * (y1 - y3)) - ((y2 - y1) * (x1 - x3))) / denominator;

        if (ua < 0 || ua > 1 || ub < 0 || ub > 1) {
            // Lines do not intersect within their segments
            return false;
        }

        // Lines intersect at point (x, y)
        float x = x1 + (ua * (x2 - x1));
        float y = y1 + (ua * (y2 - y1));

        // Check if the intersection point is on one of the endpoints
        if ((x == x1 && y == y1) || (x == x2 && y == y2) || (x == x3 && y == y3) || (x == x4 && y == y4)) {
            return false;
        }

        return true;
    }
}
