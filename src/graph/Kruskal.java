package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Kruskal {
    static class Edge implements Comparable<Edge> {
        List<Vertex> vertices;
        // start 和 end 是相应的顶点索引
        // 从0开始
        int start;
        int end;
        int weight;

        public Edge(int start, List<Vertex> vertices, int end, int weight) {
            this.start = start;
            this.vertices = vertices;
            this.end = end;
            this.weight = weight;
        }


        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    static void kruskal(int size, PriorityQueue<Edge> queue) {
        List<Edge> list = new ArrayList<>();
        DisjointSet set = new DisjointSet(size);
        while (list.size() < size - 1) {
            // 获得权重最小的边
            Edge poll = queue.poll();
            if (set.find(poll.start) != set.find(poll.end)) {
                // 还没有连通
                list.add(poll);
                set.union(poll.start, poll.end);
            }
        }
    }
}
