package structures.graphs;

import java.util.*;
import structures.node.Node;

public class Graph<T> {

    private Map<Node<T>, Set<Node<T>>> graph;

    public Graph() {
        this.graph = new LinkedHashMap<>();
    }

    public void addNode(T data) {
        if (data == null) 
            return;
        graph.putIfAbsent(new Node<>(data), new LinkedHashSet<>());
    }

    // bidireccional
    public void addEdge(T source, T destination) {
        addEdgeUni(source, destination);
        addEdgeUni(destination, source);
    }

    // unidireccional
    public void addEdgeUni(T source, T destination) {
        addNode(source);
        addNode(destination);
        graph.get(new Node<>(source)).add(new Node<>(destination));
    }

    public void remove(T data) {
        Node<T> target = new Node<>(data);
        graph.remove(target);
        for (Set<Node<T>> neighbors : graph.values()) {
            neighbors.remove(target);
        }
    }

    public void removeEdge(T v1, T v2) {
        removeEdgeUni(v1, v2);
        removeEdgeUni(v2, v1);
    }

    public void removeEdgeUni(T v1, T v2) {
        Node<T> n1 = new Node<>(v1);
        Node<T> n2 = new Node<>(v2);
        if (graph.containsKey(n1)) {
            graph.get(n1).remove(n2);
        }
    }

    public Set<Node<T>> getNodes() {
        return graph.keySet();
    }

    public Map<Node<T>, Set<Node<T>>> getGraph() {
        return graph;
    }

    public Set<Node<T>> getNeighbors(T data) {
        return graph.getOrDefault(new Node<>(data), Collections.emptySet());
    }

    public boolean contains(T data) {
        return graph.containsKey(new Node<>(data));
    }

    public void clear() {
        graph.clear();
    }
}