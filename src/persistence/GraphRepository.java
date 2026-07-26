package persistence;

import java.io.File;
import java.io.IOException;
import models.MapPoint;
import structures.graphs.Graph;

public interface GraphRepository {

    Graph<MapPoint> load(File file) throws IOException;

    void save(Graph<MapPoint> graph, File file) throws IOException;

}