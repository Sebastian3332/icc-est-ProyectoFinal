package structures.node;
import java.util.Objects;
import models.MapPoint;

public class Node {
    private MapPoint data;

    public Node(MapPoint data){
        this.data = data;
    }

    public MapPoint getData(){
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return Objects.equals(data, node.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
