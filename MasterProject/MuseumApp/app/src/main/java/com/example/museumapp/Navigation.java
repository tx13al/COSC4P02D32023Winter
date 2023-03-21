import java.util.*;

public class Navigation {
    private static final int NO_PARENT = -1;
    public  Navigation(int[][] adjMatrix,int startVertex) {
        dijkstra(adjMatrix, startVertex);
    }

    private static void dijkstra(int[][] adjacencyMatrix, int startVertex) {
        int nVertices = adjacencyMatrix[0].length;

        int[] shortestDistances = new int[nVertices];
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
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertex = 0; vertex < nVertices; vertex++) {
                if (!visited[vertex] && shortestDistances[vertex] < shortestDistance) {
                    nearestVertex = vertex;
                    shortestDistance = shortestDistances[vertex];
                }
            }

            visited[nearestVertex] = true;

            for (int vertex = 0; vertex < nVertices; vertex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertex];
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertex])) {
                    parents[vertex] = nearestVertex;
                    shortestDistances[vertex] = shortestDistance + edgeDistance;
                }
            }
        }

        //printShortestPaths(startVertex, shortestDistances, parents);
    }

    private static void printShortestPaths(int startVertex, int[] distances, int[] parents) {
        int nVertices = distances.length;
        System.out.print("Vertex\t Distance\tPath");

        for (int vertex = 0; vertex < nVertices; vertex++) {
            if (vertex != startVertex) {
                System.out.print("\n" + startVertex + " -> " + vertex + " \t\t " + distances[vertex] + "\t\t");
                printPath(vertex, parents);
            }
        }
    }

    private static void printPath(int currentVertex, int[] parents) {
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print(currentVertex + " ");
    }
}

