public interface Graph {

    void SaveVertex(String vertexID, float x, float b);
    float[] getVertex(String vertexID);
    void SaveEdge(String edgeID, float startX, float startY);
    float[] getEdge(String edgeID);
}
