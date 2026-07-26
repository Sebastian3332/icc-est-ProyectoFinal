package structures.graphs.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import models.MapPoint;
import structures.graphs.Graph;
import structures.graphs.PathFinder;
import structures.graphs.PathResult;
import structures.node.Node;

public class BFSPathFinder implements PathFinder {

    @Override
    public PathResult findPath(Graph<MapPoint> graph, Node<MapPoint> start, Node<MapPoint> end) {
        Queue<Node<MapPoint>> queue = new LinkedList<>();
        Set<Node<MapPoint>> visited = new HashSet<>();
        Map<Node<MapPoint>, Node<MapPoint>> parents = new HashMap<>();
        
        
        Set<MapPoint> animatedVisited = new LinkedHashSet<>();

        queue.add(start);
        visited.add(start);
        animatedVisited.add(start.getData()); 

        boolean found = false;

        while (!queue.isEmpty()) {
            Node<MapPoint> current = queue.poll();

            if (current.equals(end)) {
                found = true;
                break;
            }

            
            for (Node<MapPoint> neighbor : graph.getNeighbors(current.getData())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    animatedVisited.add(neighbor.getData()); 
                    parents.put(neighbor, current); 
                    queue.add(neighbor);
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
