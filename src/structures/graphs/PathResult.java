package structures.graphs;

import java.util.Set;
import models.MapPoint;

public class PathResult {
    private Set<MapPoint> path;            
    private Set<MapPoint> exploredNodes;   

    public PathResult(Set<MapPoint> path, Set<MapPoint> exploredNodes) {
        this.path = path;
        this.exploredNodes = exploredNodes;
    }

    public Set<MapPoint> getPath() {
        return path;
    }

    public Set<MapPoint> getExploredNodes() {
        return exploredNodes;
    }
}
