package graph;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {
    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        v1.edges = List.of(new Edge(v2, 4), new Edge(v3, 2), new Edge(v4, 1));
        v2.edges = List.of(new Edge(v1, 2), new Edge(v4, 3), new Edge(v5, 10));
        v3.edges = List.of(new Edge(v1, 4), new Edge(v4, 2), new Edge(v6, 5));
        v4.edges = List.of(new Edge(v1, 1), new Edge(v2, 3), new Edge(v3, 2), new Edge(v5, 7), new Edge(v6, 8), new Edge(v7, 4));
        v5.edges = List.of(new Edge(v2, 10), new Edge(v4, 7), new Edge(v7, 6));
        v6.edges = List.of(new Edge(v3, 5), new Edge(v4, 8), new Edge(v7, 1));
        v7.edges = List.of(new Edge(v6, 1), new Edge(v4, 4), new Edge(v5, 6));

        var graph = List.of(v1,v2,v3,v4, v5,v6,v7);
        prim(graph, v1);
    }

    public static void prim(List<Vertex> graph, Vertex source) {
        // 默认的优先级队列是一个小顶堆
        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingInt(v -> v.dist));
        source.dist = 0;
        for (Vertex vertex : graph) {
            queue.offer(vertex);
        }

        while (!queue.isEmpty()) {
            // 3.选取当前节点
            Vertex curr = queue.peek();
            // 4.更新当前顶点邻居距离
            updateNeighboursDist(curr, queue);
            // 5.移除当前节点
            queue.poll();
            curr.visited = true;
        }
        for (Vertex vertex : graph) {
            System.out.println(vertex.name + " " + vertex.dist + " " + (vertex.prev != null ? vertex.prev.name : " null"));
        }
    }

    private static void updateNeighboursDist(Vertex curr, PriorityQueue<Vertex> queue) {
        for (Edge edge : curr.edges) {
            Vertex n = edge.linked;
            if (!n.visited) {
                // 用权重来进行更新
                int dist = edge.weight;
                if (dist < n.dist) {
                    n.dist = dist;
                    // 记录路径 -- 从何而来
                    n.prev = curr;
                    queue.offer(n);
                }
            }
        }
    }
}
