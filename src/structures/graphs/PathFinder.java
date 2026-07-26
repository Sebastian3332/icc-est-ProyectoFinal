package structures.graphs;

import models.MapPoint;
import structures.node.Node;

public interface PathFinder {
    PathResult findPath(Graph<MapPoint> graph, Node<MapPoint> start, Node<MapPoint> end);
}
