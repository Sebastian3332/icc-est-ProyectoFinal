package controllers;

import javax.swing.*;
import java.awt.Image;
import java.awt.Point;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.Timer;

import models.MapPoint;
import structures.graphs.Graph;
import views.MainFrame;
import views.MapPanel;

public class MapController {

    private MainFrame mainFrame;
    private MapPanel mapPanel;
    private Graph<MapPoint> mapGraph;

    private int nodeCounter = 1;
    private MapPoint startPoint = null;
    private MapPoint endPoint = null;

    public MapController(MainFrame mainFrame, MapPanel mapPanel) {
        this.mainFrame = mainFrame;
        this.mapPanel = mapPanel;
        this.mapGraph = new Graph<>();

        this.mapPanel.setMapGraph(mapGraph);

        // Listeners de los botones
        this.mainFrame.addListenerCargarMapa(e -> cargarImagenMapa());
        this.mainFrame.addListenerEjecutar(e -> ejecutarAlgoritmo());
        this.mainFrame.addListenerLimpiar(e -> limpiarTodo());

        // listeners para la gestion de los nodos
        this.mapPanel.addListenerCrearNodo(e -> crearNodo());
        this.mapPanel.addListenerSetStart(e -> establecerInicio());
        this.mapPanel.addListenerSetEnd(e -> establecerDestino());
    }

    // metodo para crear un nodo en el mapa
    private void crearNodo() {
        Point p = mapPanel.getLastClickPoint();
        String id = "N" + nodeCounter++;
        MapPoint nuevoPunto = new MapPoint(id, p.x, p.y);

        mapGraph.addNode(nuevoPunto);
        mapPanel.setMapGraph(mapGraph);
    }

    // metodo para establecer el punto inicial
    private void establecerInicio() {
        MapPoint sel = mapPanel.getLastSelectedNode();
        if (sel != null) {
            this.startPoint = sel;
            mapPanel.setStartPoint(startPoint);
        }
    }

    // metodo para establecer el punto final
    private void establecerDestino() {
        MapPoint sel = mapPanel.getLastSelectedNode();
        if (sel != null) {
            this.endPoint = sel;
            mapPanel.setEndPoint(endPoint);
        }
    }

    // metodo vacio para ejecutar el algoritmo de busqueda de ruta
    private void ejecutarAlgoritmo() {

    }

    // metodo para animar la exploración de nodos y la ruta final
    private void animarExploracionYRuta(Set<MapPoint> visited, Set<MapPoint> path) {
        Set<MapPoint> exploradosTemp = new LinkedHashSet<>();
        Object[] arrayVisited = visited.toArray();

        Timer timer = new Timer(150, null);
        final int[] idx = {0};

        timer.addActionListener(e -> {
            if (idx[0] < arrayVisited.length) {
                exploradosTemp.add((MapPoint) arrayVisited[idx[0]]);
                mapPanel.setAnimatedVisited(exploradosTemp);
                idx[0]++;
            } else {
                ((Timer) e.getSource()).stop();
                animarSoloRuta(path);
            }
        });
        timer.start();
    }

    // metodo para animar la ruta
    private void animarSoloRuta(Set<MapPoint> path) {
        Set<MapPoint> rutaTemp = new LinkedHashSet<>();
        Object[] arrayPath = path.toArray();

        Timer timer = new Timer(180, null);
        final int[] idx = {0};

        timer.addActionListener(e -> {
            if (idx[0] < arrayPath.length) {
                rutaTemp.add((MapPoint) arrayPath[idx[0]]);
                mapPanel.setAnimatedPath(rutaTemp);
                idx[0]++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    // metodo para limpiar el mapa
    private void limpiarTodo() {
        nodeCounter = 1;
        startPoint = null;
        endPoint = null;
        mapGraph.clear();
        
        mapPanel.setStartPoint(null);
        mapPanel.setEndPoint(null);
        mapPanel.setAnimatedVisited(new LinkedHashSet<>());
        mapPanel.setAnimatedPath(new LinkedHashSet<>());
        mapPanel.setMapGraph(mapGraph);
    }

    // metodo para cargar el mapa
    private void cargarImagenMapa() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));
        if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            try {
                Image img = ImageIO.read(fileChooser.getSelectedFile());
                mapPanel.setMapImage(img);
            } catch (Exception ex) {
                mainFrame.mostrarError("Error al abrir la imagen.");
            }
        }
    }
}