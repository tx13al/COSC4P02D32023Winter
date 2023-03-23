package com.example.museumapp;

import java.util.*;

public class Navigation {
    private static final int NO_PARENT = -1;
    static ArrayList<Integer> pathPoints = new ArrayList<Integer>();
    public  Navigation(double[][] adjMatrix,int startVertex, int endVertex) {
        dijkstra(adjMatrix, startVertex,endVertex);
    }

    private static void dijkstra(double[][] adjacencyMatrix, int startVertex,int endVertex) {
        int nVertices = adjacencyMatrix[0].length;

        double[] shortestDistances = new double[nVertices];
        boolean[] visited = new boolean[nVertices];
        int[] parents = new int[nVertices];

        for (int vertex = 0; vertex < nVertices; vertex++) {
            shortestDistances[vertex] = Integer.MAX_VALUE;
            visited[vertex] = false;
            parents[vertex] = NO_PARENT;
        }

        shortestDistances[startVertex] = 0;

        for (int i = 1; i < nVertices; i++) {
            int nearestVertex = -1;
            double shortestDistance = Integer.MAX_VALUE;
            for (int vertex = 0; vertex < nVertices; vertex++) {
                if (!visited[vertex] && shortestDistances[vertex] < shortestDistance) {
                    nearestVertex = vertex;
                    shortestDistance = shortestDistances[vertex];
                }
            }

            visited[nearestVertex] = true;

            for (int vertex = 0; vertex < nVertices; vertex++) {
                double edgeDistance = adjacencyMatrix[nearestVertex][vertex];
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertex])) {
                    parents[vertex] = nearestVertex;
                    shortestDistances[vertex] = shortestDistance + edgeDistance;
                }
            }
        }
        printPath(endVertex,parents);
    }


    private static void printPath(int endVertex, int[] parents) {
        if (endVertex == NO_PARENT) {
            return;
        }
        printPath(parents[endVertex], parents);
        //System.out.print(endVertex + " ");
        pathPoints.add(endVertex);
    }

    public ArrayList<Integer> getPathPoints(){
        return pathPoints;
    }
}

