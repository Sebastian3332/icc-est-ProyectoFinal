package structures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import structures.node.Node;

public class Graph {
    // El mapa ahora conecta Nodos
    private Map<Node, List<Node>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addNode(Node node) {
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node destination) {
        addNode(source);
        addNode(destination);
        
        
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    public List<Node> getNeighbors(Node node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }
}