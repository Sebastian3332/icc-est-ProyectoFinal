package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import models.MapPoint;
import structures.graphs.Graph;
import structures.node.Node;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;

public class MapPanel extends JPanel {

    private Image mapImage;
    private Graph<MapPoint> mapGraph = new Graph<>();

    // Puntos seleccionados visualmente
    private MapPoint startPoint = null;
    private MapPoint endPoint = null;

    // Conjuntos para animar el recorrido
    private Set<MapPoint> animatedVisited = new LinkedHashSet<>();
    private Set<MapPoint> animatedPath = new LinkedHashSet<>();

    // Variables para la conexión manual de nodos (Drag & Drop)
    private MapPoint nodoOrigenDrag = null;
    private Point puntoActualDrag = null;

    // menu para crear nodo
    private JPopupMenu popupLienzo = new JPopupMenu();
    private JMenuItem menuItemCrearNodo = new JMenuItem("Crear Nodo");

    // menu para establecer inicio y fin
    private JPopupMenu popupNodo = new JPopupMenu();
    private JMenuItem menuItemSetStart = new JMenuItem("Inicio");
    private JMenuItem menuItemSetEnd = new JMenuItem("Fin");

    // metodo para obtener el ultimo punto clickeado y el ultimo nodo seleccionado
    private Point lastClickPoint = new Point(0, 0);
    private MapPoint lastSelectedNode = null;

    public MapPanel() {
        setBackground(Color.WHITE);

        popupLienzo.add(menuItemCrearNodo);
        popupNodo.add(menuItemSetStart);
        popupNodo.add(menuItemSetEnd);

        // Agregar un MouseAdapter para manejar los eventos del mouse y el arrastre de aristas
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    evaluarPopup(e);
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    // Si hace clic izquierdo sobre un nodo, se inicia el arrastre para conectar
                    MapPoint nodoHit = getNodeAt(e.getX(), e.getY());
                    if (nodoHit != null) {
                        nodoOrigenDrag = nodoHit;
                        puntoActualDrag = e.getPoint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    evaluarPopup(e);
                } else if (SwingUtilities.isLeftMouseButton(e) && nodoOrigenDrag != null) {
                    // Al soltar el clic izquierdo sobre otro nodo, se crea la arista
                    MapPoint nodoDestino = getNodeAt(e.getX(), e.getY());
                    if (nodoDestino != null && !nodoDestino.equals(nodoOrigenDrag)) {
                        mapGraph.addEdge(nodoOrigenDrag, nodoDestino);
                    }
                    nodoOrigenDrag = null;
                    puntoActualDrag = null;
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Actualiza la punta de la línea mientras se arrastra el mouse
                if (nodoOrigenDrag != null) {
                    puntoActualDrag = e.getPoint();
                    repaint();
                }
            }

            // evalua si se debe mostrar el popup de crear nodo o inicio/fin
            private void evaluarPopup(MouseEvent e) { 
                if (e.isPopupTrigger()) {
                    lastClickPoint = e.getPoint();
                    lastSelectedNode = getNodeAt(e.getX(), e.getY());

                    if (lastSelectedNode != null) {
                        popupNodo.show(e.getComponent(), e.getX(), e.getY());
                    } else {
                        popupLienzo.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    // setter para obtener el nodo de una posicion especifica
    public MapPoint getNodeAt(int x, int y) {
        for (Node<MapPoint> node : mapGraph.getNodes()) {
            if (node.getData().containsPoint(x, y, 14)) {
                return node.getData();
            }
        }
        return null;
    }

    // setters para repintar el panel con los datos actualizados
    public void setMapImage(Image img) { 
        this.mapImage = img; 
        repaint(); 
    }

    // setter para el grafo del mapa
    public void setMapGraph(Graph<MapPoint> graph) {
        this.mapGraph = graph; 
        repaint(); 
    }

    // setter para el punto de inicio y fin
    public void setStartPoint(MapPoint p) { 
        this.startPoint = p; 
        repaint();
    }
    public void setEndPoint(MapPoint p) { 
        this.endPoint = p; 
        repaint();
    }

    // setter para los conjuntos de nodos visitados y ruta final
    public void setAnimatedVisited(Set<MapPoint> visited) {
        this.animatedVisited = visited; 
        repaint();
    }
    public void setAnimatedPath(Set<MapPoint> path) {
        this.animatedPath = path; 
        repaint(); 
    }

    // getters para obtener el ultimo punto clickeado y el ultimo nodo seleccionado
    public Point getLastClickPoint() {
        return lastClickPoint;
    }
    public MapPoint getLastSelectedNode() {
        return lastSelectedNode;
    }

    // listeneres para crear nodo y establecer inicio/fin
    public void addListenerCrearNodo(ActionListener l) {
        menuItemCrearNodo.addActionListener(l); 
    }
    public void addListenerSetStart(ActionListener l) { 
        menuItemSetStart.addActionListener(l); 
    }
    public void addListenerSetEnd(ActionListener l) { 
        menuItemSetEnd.addActionListener(l); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // se carga el mapa 
        if (mapImage != null) {
            g2d.drawImage(mapImage, 0, 0, getWidth(), getHeight(), this);
        }

        // dibuja las aristas generales
        g2d.setColor(Color.BLACK);
        for (Map.Entry<Node<MapPoint>, Set<Node<MapPoint>>> entry : mapGraph.getGraph().entrySet()) {

            MapPoint u = entry.getKey().getData();

            for (Node<MapPoint> vNode : entry.getValue()) {
                MapPoint v = vNode.getData();
                g2d.drawLine(u.getX(), u.getY(), v.getX(), v.getY());
            }
        }

        // dibuja la linea temporal mientras se arrastra el raton para conectar nodos
        if (nodoOrigenDrag != null && puntoActualDrag != null) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(nodoOrigenDrag.getX(), nodoOrigenDrag.getY(), puntoActualDrag.x, puntoActualDrag.y);
        }

        // dibuja la ruta final
        if (animatedPath.size() > 1) {
            g2d.setColor(Color.MAGENTA); // magenta la ruta final
            MapPoint prev = null;
            for (MapPoint curr : animatedPath) {
                if (prev != null) {
                    g2d.drawLine(prev.getX(), prev.getY(), curr.getX(), curr.getY());
                }
                prev = curr;
            }

            // hace que todos los nodos de la ruta final se dibujen en magenta
            for (MapPoint p : animatedPath) {
                g2d.setColor(Color.MAGENTA);
                g2d.fillOval(p.getX() - 13, p.getY() - 13, 26, 26);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(p.getX() - 13, p.getY() - 13, 26, 26);
                int pixelesNodo = 3 * p.getId().length();
                g2d.drawString(p.getId(), p.getX() - pixelesNodo, p.getY() + 4);
            }

        }

        // dibuja los nodos
        int radius = 13;
        for (Node<MapPoint> node : mapGraph.getNodes()) {
            MapPoint p = node.getData();

            if (p.equals(startPoint)) {
                g2d.setColor(Color.green); // Verde Inicio
            } else if (p.equals(endPoint)) {
                g2d.setColor(Color.red);   // Rojo Fin
            } else if (animatedPath.contains(p)) {
                g2d.setColor(Color.MAGENTA);  // Amarillo en ruta
            } else if (animatedVisited.contains(p)) {
                g2d.setColor(Color.YELLOW);  // morado estado visitado
            } else {
                g2d.setColor(Color.white);         // Neutro
            }

            // Relleno del nodo
            g2d.fillOval(p.getX() - radius, p.getY() - radius, radius * 2, radius * 2);
            
            // Borde negro del nodo
            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.getX() - radius, p.getY() - radius, radius * 2, radius * 2);

            // Texto del ID del nodo
            int pixelesNodo = 3 * p.getId().length();
            g2d.drawString(p.getId(), p.getX() - pixelesNodo, p.getY() + 4);
        }
    }  
}