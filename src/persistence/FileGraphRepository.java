package persistence;

import java.io.*;
import java.util.*;

import models.MapPoint;
import structures.graphs.Graph;
import structures.node.Node;

public class FileGraphRepository implements GraphRepository {

    @Override
    public Graph<MapPoint> load(File file) throws IOException {

        Graph<MapPoint> graph = new Graph<>();

        if (!file.exists()) {
            return graph;
        }

        Map<String, MapPoint> points = new LinkedHashMap<>();
        List<String[]> edges = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;

        while ((line = reader.readLine()) != null) {

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] data = line.split(";");

            if (data[0].equals("NODE")) {

                if (data.length != 4){
                    continue;
                }

                String id = data[1];
                int x = Integer.parseInt(data[2]);
                int y = Integer.parseInt(data[3]);

                if (points.containsKey(id)) {
                    continue;
                }

                MapPoint point = new MapPoint(id, x, y);

                points.put(id, point);
                graph.addNode(point);

            } else if (data[0].equals("EDGE")) {

                if (data.length != 4)
                    continue;

                edges.add(data);
            }
        }

        reader.close();

        for (String[] edge : edges) {

            MapPoint from = points.get(edge[1]);
            MapPoint to = points.get(edge[2]);

            if (from == null || to == null) {
                continue;
            }

            boolean bidirectional = Boolean.parseBoolean(edge[3]);

            if (bidirectional) {
                graph.addEdge(from, to);
            } else {
                graph.addEdgeUni(from, to);
            }
        }

        return graph;
    }

    @Override
    public void save(Graph<MapPoint> graph, File file) throws IOException {

        PrintWriter writer = new PrintWriter(new FileWriter(file));

        for (Node<MapPoint> node : graph.getNodes()) {

            MapPoint p = node.getData();

            writer.println("NODE;"  + p.getId() + ";" + p.getX() + ";" + p.getY());
        }

        Set<String> writtenEdges = new HashSet<>();

        for (Map.Entry<Node<MapPoint>, Set<Node<MapPoint>>> entry : graph.getGraph().entrySet()) {

            MapPoint from = entry.getKey().getData();

            for (Node<MapPoint> neighbor : entry.getValue()) {

                MapPoint to = neighbor.getData();

                String edge1 = from.getId() + "-" + to.getId();
                String edge2 = to.getId() + "-" + from.getId();

                if (writtenEdges.contains(edge1) || writtenEdges.contains(edge2)) {
                    continue;
                }

                boolean bidirectional = graph.getNeighbors(to).contains(new Node<>(from));

                writer.println("EDGE;" + from.getId() + ";" + to.getId() + ";" + bidirectional);

                writtenEdges.add(edge1);
                writtenEdges.add(edge2);
            }
        }

        writer.close();
    }
}