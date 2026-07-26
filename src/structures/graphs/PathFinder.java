package structures.graphs;

public class PathFinder {
    public interface PathFinder {
        PathResult findPath(Graph graph, Node start, Node end);
    }
}
