package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
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

        dijikstra(graph, v1);
    }

    public static void dijikstra(List<Vertex> graph, Vertex source) {
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
                int dist = curr.dist + edge.weight;
                if (dist < n.dist) {
                    n.dist = dist;
                    // 记录路径 -- 从何而来
                    n.prev = curr;
                    queue.offer(n);
                }
            }
        }
    }

    private static Vertex chooseMindistVertex(ArrayList<Vertex> list) {
        Vertex min = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).dist < min.dist) {
                min = list.get(i);
            }
        }
        return min;
    }

}
