package structures.graphs;

import java.util.List;
import structures.node.Node;

public class PathResult {
    private List<Node> finalPath;
    private List<Node> exploredNodes;

    public PathResult(List<Node> finalPath, List<Node> exploredNodes) {
        this.finalPath = finalPath;
        this.exploredNodes = exploredNodes;
    }

    public List<Node> getFinalPath() { return finalPath; }
    public List<Node> getExploredNodes() { return exploredNodes; }
}
