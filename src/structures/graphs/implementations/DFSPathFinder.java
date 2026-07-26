package structures.graphs.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import models.MapPoint;
import structures.graphs.Graph;
import structures.graphs.PathFinder;
import structures.graphs.PathResult;
import structures.node.Node;

public class DFSPathFinder implements PathFinder{

    @Override
    public PathResult findPath(Graph<MapPoint> graph, Node<MapPoint> start, Node<MapPoint> end) {
        Stack<Node<MapPoint>> stack = new Stack<>();
        Set<Node<MapPoint>> visited = new HashSet<>();
        Map<Node<MapPoint>, Node<MapPoint>> parents = new HashMap<>();
        
        Set<MapPoint> animatedVisited = new LinkedHashSet<>();

        stack.push(start);

        boolean found = false;

        while (!stack.isEmpty()) {
            Node<MapPoint> current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                animatedVisited.add(current.getData());

                if (current.equals(end)) {
                    found = true;
                    break;
                }

                
                for (Node<MapPoint> neighbor : graph.getNeighbors(current.getData())) {
                    if (!visited.contains(neighbor)) {
                        parents.putIfAbsent(neighbor, current); 
                        stack.push(neighbor);
                    }
                }
            }
        }

        Set<MapPoint> finalPath = buildPath(parents, end, found);
        return new PathResult(finalPath, animatedVisited);
    }

    private Set<MapPoint> buildPath(Map<Node<MapPoint>, Node<MapPoint>> parents, Node<MapPoint> end, boolean found) {
        List<MapPoint> tempPath = new ArrayList<>();
        if (found) {
            Node<MapPoint> current = end;
            while (current != null) {
                tempPath.add(current.getData());
                current = parents.get(current);
            }
            Collections.reverse(tempPath);
        }
        return new LinkedHashSet<>(tempPath);
    }
    
}
