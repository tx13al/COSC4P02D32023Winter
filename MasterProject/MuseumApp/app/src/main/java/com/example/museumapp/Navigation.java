package com.example.museumapp;

import java.util.*;
import com.example.museumapp.map.*;

public class Navigation {
    public static final int NO_PARENT = -1;
    public ArrayList<Integer> pathPoints;
    public ArrayList<Edge> firstFloor;
    public ArrayList<Edge> secondFloor;
    public ArrayList<Edge> path1;
    public ArrayList<Edge> path2;
    public float x1;
    public float y1;
    public float x2;
    public float y2;
    public float pathDistance;
    public int floor1,floor2;

    public Navigation(ArrayList<Edge> firstFloor,ArrayList<Edge> secondFloor, ArrayList<Edge> stair,float x1, float y1, int floor1, float x2, float y2,int floor2) {
        this.firstFloor = firstFloor;
        this.secondFloor = secondFloor;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.floor1 = floor1;
        this.floor2 = floor2;
        if(floor1 == floor2 & floor1==1) {
            HashSet<Edge> vertices = getVertices(this.firstFloor, x1, y1, x2, y2);
            int startIndex = indexOfVertex(vertices, x1, y1);
            int endIndex = indexOfVertex(vertices, x2, y2);
            double[][] adjMatrix = toAdjacencyMatrix(vertices, this.firstFloor);
            dijkstra(adjMatrix, startIndex, endIndex);
            path1 = convertPathPointsToEdges(pathPoints, vertices);
        }

        else if(floor1 == floor2 & floor2 == 2){
            HashSet<Edge> vertices = getVertices(this.secondFloor, x1, y1, x2, y2);
            int startIndex = indexOfVertex(vertices, x1, y1);
            int endIndex = indexOfVertex(vertices, x2, y2);
            double[][] adjMatrix = toAdjacencyMatrix(vertices, this.secondFloor);
            dijkstra(adjMatrix, startIndex, endIndex);
            path2 = convertPathPointsToEdges(pathPoints, vertices);
        }
        else if(floor1 == 1 & floor2 == 2){
            float d1=0f;
            float d2=0f;
            float d3=0f;
            float d4=0f;
            ArrayList<Edge> temp1 =new ArrayList<Edge>();
            ArrayList<Edge> temp2 =new ArrayList<Edge>();
            ArrayList<Edge> temp3 =new ArrayList<Edge>();
            ArrayList<Edge> temp4 =new ArrayList<Edge>();

            HashSet<Edge> vertices = getVertices(this.firstFloor, x1, y1, stair.get(0).from_x, stair.get(0).from_y);
            int startIndex = indexOfVertex(vertices, x1, y1);
            int endIndex = indexOfVertex(vertices, stair.get(0).from_x, stair.get(0).from_y);
            double[][] adjMatrix = toAdjacencyMatrix(vertices, this.firstFloor);
            dijkstra(adjMatrix, startIndex, endIndex);
            temp1 = convertPathPointsToEdges(pathPoints, vertices);
            d1 = pathDistance;

            HashSet<Edge> vertices2 = getVertices(this.secondFloor, stair.get(0).to_x, stair.get(0).to_y,x2,y2);
            int startIndex2 = indexOfVertex(vertices2, stair.get(0).to_x, stair.get(0).to_y);
            int endIndex2 = indexOfVertex(vertices2, x2,y2);
            double[][] adjMatrix2 = toAdjacencyMatrix(vertices2, this.secondFloor);
            dijkstra(adjMatrix2, startIndex2, endIndex2);
            d2 = pathDistance;
            temp2 = convertPathPointsToEdges(pathPoints, vertices2);
            d1= d1+d2;

            //second choose from different stair
            HashSet<Edge> vertices3 = getVertices(this.firstFloor, x1, y1, stair.get(1).from_x, stair.get(1).from_y);
            int startIndex3 = indexOfVertex(vertices3, x1, y1);
            int endIndex3 = indexOfVertex(vertices3, stair.get(1).from_x, stair.get(1).from_y);
            double[][] adjMatrix3 = toAdjacencyMatrix(vertices3, this.firstFloor);
            dijkstra(adjMatrix3, startIndex3, endIndex3);
            temp3 = convertPathPointsToEdges(pathPoints, vertices3);
            d3 = pathDistance;

            HashSet<Edge> vertices4 = getVertices(this.secondFloor,stair.get(1).to_x, stair.get(1).to_y,x2,y2);
            int startIndex4 = indexOfVertex(vertices4, stair.get(1).to_x, stair.get(1).to_y);
            int endIndex4 = indexOfVertex(vertices4, x2,y2);
            double[][] adjMatrix4 = toAdjacencyMatrix(vertices4, this.secondFloor);
            dijkstra(adjMatrix4, startIndex4, endIndex4);
            d4 = pathDistance;
            temp4 = convertPathPointsToEdges(pathPoints, vertices4);
            d3= d3+d4;

            if(d3>d1){
                path1 = new ArrayList<Edge>(temp1);
                path2 = new ArrayList<Edge>(temp2);
            }
            else {
                path1 = new ArrayList<Edge>(temp3);
                path2 = new ArrayList<Edge>(temp4);
            }

        }

        else if(floor1 == 2 & floor2 == 1){
            float d1=0f;
            float d2=0f;
            float d3=0f;
            float d4=0f;
            ArrayList<Edge> temp1,temp2,temp3,temp4;
            HashSet<Edge> vertices = getVertices(this.secondFloor, x1, y1, stair.get(0).to_x, stair.get(0).to_y);
            int startIndex = indexOfVertex(vertices, x1, y1);
            int endIndex = indexOfVertex(vertices, stair.get(0).to_x, stair.get(0).to_y);
            double[][] adjMatrix = toAdjacencyMatrix(vertices, this.secondFloor);
            dijkstra(adjMatrix, startIndex, endIndex);
            temp1 = convertPathPointsToEdges(pathPoints, vertices);
            d1 = pathDistance;

            HashSet<Edge> vertices2 = getVertices(this.firstFloor, stair.get(0).from_x, stair.get(0).from_y,x2,y2);
            int startIndex2 = indexOfVertex(vertices2, stair.get(0).from_x, stair.get(0).from_y);
            int endIndex2 = indexOfVertex(vertices2, x2,y2);
            double[][] adjMatrix2 = toAdjacencyMatrix(vertices2, this.firstFloor);
            dijkstra(adjMatrix2, startIndex2, endIndex2);
            d2 = pathDistance;
            temp2 = convertPathPointsToEdges(pathPoints, vertices2);
            d1= d1+d2;
            //second choose from different stair
            HashSet<Edge> vertices3 = getVertices(this.secondFloor, x1, y1, stair.get(1).to_x, stair.get(1).to_y);
            int startIndex3 = indexOfVertex(vertices3, x1, y1);
            int endIndex3 = indexOfVertex(vertices3, stair.get(1).to_x, stair.get(1).to_y);
            double[][] adjMatrix3 = toAdjacencyMatrix(vertices3, this.secondFloor);
            dijkstra(adjMatrix3, startIndex3, endIndex3);
            temp3 = convertPathPointsToEdges(pathPoints, vertices3);
            d3 = pathDistance;

            HashSet<Edge> vertices4 = getVertices(this.firstFloor,stair.get(1).from_x, stair.get(1).from_y,x2,y2);
            int startIndex4 = indexOfVertex(vertices4, stair.get(1).from_x, stair.get(1).from_y);
            int endIndex4 = indexOfVertex(vertices4, x2,y2);
            double[][] adjMatrix4 = toAdjacencyMatrix(vertices4, this.firstFloor);
            dijkstra(adjMatrix4, startIndex4, endIndex4);
            d4 = pathDistance;
            temp4 = convertPathPointsToEdges(pathPoints, vertices4);
            d3= d3+d4;

            if(d3>=d1){
                path1 = new ArrayList<Edge>(temp2);
                path2 = new ArrayList<Edge>(temp1);
            }
            else {
                path1 = new ArrayList<Edge>(temp4);
                path2 = new ArrayList<Edge>(temp3);
            }
        }

    }
    public float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private boolean isPointReachable(ArrayList<Edge> edges,float fromX, float fromY, float toX, float toY) {
        Segment segment = new Segment(fromX, fromY, toX, toY);
        for (Edge edge : edges) {
            Segment otherSegment = new Segment(edge.from_x, edge.from_y, edge.to_x, edge.to_y);
            if (segment.intersects(otherSegment)) {
                // An intersecting edge was found, so the points are not directly reachable
                return false;
            }
        }
        // No intersecting edges were found, so the points are directly reachable
        return true;
    }

    // Get all vertices from the list of edges
    private HashSet<Edge> getVertices(ArrayList<Edge> edges, float x1, float y1, float x2, float y2) {
        HashSet<Edge> vertices = new HashSet<>();
        for (Edge edge : edges) {
            vertices.add(new Edge(edge.from_x, edge.from_y, 0, 0));
            vertices.add(new Edge(edge.to_x, edge.to_y, 0, 0));
        }
        vertices.add(new Edge(x1, y1, 0, 0));
        vertices.add(new Edge(x2, y2, 0, 0));


        return vertices;
    }


    public double[][] toAdjacencyMatrix(HashSet<Edge> vertices, ArrayList<Edge> edges) {
        int n = vertices.size();
        double[][] adjacencyMatrix = new double[n][n];
        int count=0;
        // Convert the HashSet of vertices to an ArrayList
        ArrayList<Edge> vertexList = new ArrayList<>(vertices);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Edge from = vertexList.get(i);
                Edge to = vertexList.get(j);

                // Check if the points can be reached directly without intersection
                if (isPointReachable(edges,from.from_x, from.from_y, to.from_x, to.from_y)) {
                    adjacencyMatrix[i][j] = (int)distance(from.from_x, from.from_y, to.from_x, to.from_y);
                } else {
                    adjacencyMatrix[i][j] = 0;
                }
            }
        }
//        for (int i = 0; i < adjacencyMatrix.length; i++) {
//            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
//                System.out.print(adjacencyMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }

        return adjacencyMatrix;
    }

    // Find the index of a vertex in the list of vertices
    private int indexOfVertex(HashSet<Edge> vertices, float x, float y) {
        int index = 0;
        for (Edge vertex : vertices) {
            if (vertex.from_x == x && vertex.from_y == y) {
                return index;
            }
            index++;
        }
        return -1; // Not found
    }
    public void dijkstra(double[][] adjacencyMatrix, int startVertex, int endVertex) {
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

            if (nearestVertex == endVertex) {
                break;
            }

            for (int vertex = 0; vertex < nVertices; vertex++) {
                double edgeDistance = adjacencyMatrix[nearestVertex][vertex];
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertex])) {
                    parents[vertex] = nearestVertex;
                    shortestDistances[vertex] = shortestDistance + edgeDistance;
                }
            }
        }
        pathDistance = 0;
        printPath(endVertex, parents);
        pathDistance = (float) shortestDistances[endVertex];
    }

    private void printPath(int endVertex, int[] parents) {
        pathPoints = new ArrayList<Integer>();
        if (endVertex == NO_PARENT) {
            return;
        }
        printPath(parents[endVertex], parents);
        pathPoints.add(endVertex);
    }

    public ArrayList<Integer> getPathPoints() {
        return pathPoints;
    }

    private ArrayList<Edge> convertPathPointsToEdges(ArrayList<Integer> pathPoints,  HashSet<Edge> vertices) {
        ArrayList<Edge> vertexList = new ArrayList<>(vertices);
        ArrayList<Edge> vertexEdgeList = new ArrayList<>();
        if(pathPoints.size() == 2){
            for (int i =0;i < pathPoints.size()-1; i++) {
                Edge vertex1 = vertexList.get(pathPoints.get(i));
                Edge vertex2 = vertexList.get(pathPoints.get(i+1));
                vertexEdgeList.add(new Edge(vertex1.from_x, vertex1.from_y, vertex2.from_x, vertex2.from_y));
            }
        }
        else {
            for (int j =0;j < 1; j++) {
                Edge vertex1 = vertexList.get(pathPoints.get(j));
                Edge vertex2 = vertexList.get(pathPoints.get(j+1));
                vertexEdgeList.add(new Edge(vertex1.from_x, vertex1.from_y, vertex2.from_x, vertex2.from_y));
            }
            for (int k=2;k<pathPoints.size();k++){
                Edge vertex1 = vertexList.get(pathPoints.get(k-1));
                Edge vertex2 = vertexList.get(pathPoints.get(k));
                vertexEdgeList.add(new Edge(vertex1.from_x, vertex1.from_y, vertex2.from_x, vertex2.from_y));
            }

        }


        return vertexEdgeList;
    }

    public ArrayList<Edge> getPath1() {
        return path1;
    }
    public ArrayList<Edge> getPath2() {
        return path2;
    }

}
