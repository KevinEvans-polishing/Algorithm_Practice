package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DFS {
    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");

        v1.edges = List.of(
                new Edge(v3, 9),
                new Edge(v2, 7),
                new Edge(v6, 14)
        );
        v2.edges = List.of(
                new Edge(v4, 15)
        );
        v3.edges = List.of(
                new Edge(v4, 11),
                new Edge(v6, 2)
        );
        v4.edges = List.of(new Edge(v5, 6));
        v5.edges = List.of();
        v6.edges = List.of(new Edge(v5, 9));

        List<Vertex> graph = new ArrayList<>(List.of(v1, v2, v3, v4, v5, v6));

        LinkedList<String> stack = new LinkedList<>();
        for (Vertex v : graph) {
            dfs(v, stack);
        }
        System.out.println(stack);
    }

    private static void dfs(Vertex v, LinkedList<String> stack) {
        if (v.status == 2) {
            return;
        }
        // 检测环 -- status 取值为1时表示正在处理这个节点
        // 但是这时我们的本次dfs也在处理这个节点
        // 说明这个节点重复处理，即出现环
        if (v.status == 1){
            throw new RuntimeException("circle");
        }
        v.status = 1;
        for (Edge edge : v.edges) {
            dfs(edge.linked, stack);
        }
        v.status = 2;
        // 相邻顶点处理完之后再压栈
        stack.push(v.name);
    }

    public static void dfs(Vertex v) {
        v.visited = true;
        System.out.println(v.name);
        for (Edge edge : v.edges) {
            if (!edge.linked.visited) {
                dfs(edge.linked);
            }
        }
    }

    // 不使用递归 -- 自定义栈
    public static void dfs1(Vertex v) {
        LinkedList<Vertex> stack = new LinkedList<>();
        stack.push(v);
        while (!stack.isEmpty()) {
            Vertex pop = stack.pop();
            pop.visited = true;
            System.out.println(pop.name);
            for (Edge edge : pop.edges) {
                if (!edge.linked.visited) {
                    stack.push(edge.linked);
                }
            }
        }
    }

    public static void bfs(Vertex v) {
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.offer(v);
        v.visited = true;
        while (!queue.isEmpty()) {
            Vertex poll = queue.poll();
            System.out.println(poll.name);
            for (Edge edge : poll.edges) {
                if (!edge.linked.visited) {
                    edge.linked.visited = true;
                    queue.offer(edge.linked);
                }
            }
        }
    }


}
