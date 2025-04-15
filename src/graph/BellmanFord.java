package graph;

import java.util.List;

public class BellmanFord {
    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");

        v1.edges = List.of(new Edge(v3, 9), new Edge(v2, 7), new Edge(v6, 14));
        v2.edges = List.of(new Edge(v4, 15));
        v3.edges = List.of(new Edge(v4, 11), new Edge(v6, 2));
        v4.edges = List.of(new Edge(v5, 6));
        v5.edges = List.of();
        v6.edges = List.of(new Edge(v5, 9));

        List<Vertex> graph = List.of(v1, v2, v3, v4, v5, v6);

        bellmanFord(graph, v1);

    }

    private static void bellmanFord(List<Vertex> graph, Vertex source) {
        source.dist = 0;

        int size = graph.size();
        for (int i = 0; i < size - 1; i++) {
            for (Vertex s : graph) {
                for (Edge edge : s.edges) {
                    // 处理每一条边
                    Vertex e = edge.linked;
                    if (s.dist != Integer.MAX_VALUE && s.dist + edge.weight < e.dist) {
                        e.dist = s.dist + edge.weight;
                        e.prev = s;
                    }
                }
            }
        }
        for (Vertex s : graph) {
            for (Edge edge : s.edges) {
                // 检测负环
                Vertex e = edge.linked;
                if (s.dist != Integer.MAX_VALUE && s.dist + edge.weight < e.dist) {
                    throw new RuntimeException("negative circle");
                }
                }
            }
        for (Vertex v : graph) {
            System.out.println(v.name + " " + (v.prev != null ? v.prev.name : "null"));
        }
    }
}
