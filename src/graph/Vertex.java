package graph;

import java.util.List;
import java.util.Objects;

public class Vertex {
    String name;
    List<Edge> edges;

    boolean visited = false; // 是否被访问过
    int inDegree; // 入度
    int status; // 初始值为0，表示未访问，1表示访问中，2表示访问过
    Vertex prev = null;
    int dist = INF;
    static final Integer INF = Integer.MAX_VALUE;

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
