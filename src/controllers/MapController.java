package controllers;

import javax.swing.*;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.Timer;

import models.MapPoint;
import models.VisualizationMode;
import persistence.FileGraphRepository;
import persistence.GraphRepository;
import structures.graphs.Graph;
import structures.graphs.PathFinder;
import structures.graphs.PathResult;
import structures.graphs.implementations.BFSPathFinder;
import structures.graphs.implementations.DFSPathFinder;
import structures.node.Node;
import views.MainFrame;
import views.MapPanel;

public class MapController {

    private MainFrame mainFrame;
    private MapPanel mapPanel;
    private Graph<MapPoint> mapGraph;
    private GraphRepository repository;

    private int nodeCounter = 1;
    private MapPoint startPoint = null;
    private MapPoint endPoint = null;

    public MapController(MainFrame mainFrame, MapPanel mapPanel) {
        this.mainFrame = mainFrame;
        this.mapPanel = mapPanel;
        this.mapGraph = new Graph<>();
        this.repository = new FileGraphRepository();

        this.mapPanel.setMapGraph(mapGraph);

        // Listeners de los botones
        this.mainFrame.addListenerCargarMapa(e -> cargarImagenMapa());
        this.mainFrame.addListenerCargarGrafo(e -> cargarGrafo());
        this.mainFrame.addListenerGuardarGrafo(e -> guardarGrafo());
        this.mainFrame.addListenerEjecutar(e -> ejecutarAlgoritmo());
        this.mainFrame.addListenerLimpiar(e -> limpiarTodo());
        this.mainFrame.addListenerGuia(e -> mostrarGuia());

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

    // metodo para ejecutar el algoritmo de busqueda de ruta seleccionada (BFS o DFS)
    private void ejecutarAlgoritmo() {
        if (startPoint == null || endPoint == null) {
            mainFrame.mostrarError("Debes seleccionar un punto de Inicio y uno de Fin.");
            return;
        }

        limpiarAnimacion();

        PathFinder finder = mainFrame.esBFSSelected() ? new BFSPathFinder() : new DFSPathFinder();
        // inicia el cronometro para medir el tiempo
        long startTime = System.nanoTime();
        PathResult result = finder.findPath(mapGraph, new Node<>(startPoint), new Node<>(endPoint));
        // termina el cronometro y calcula la duracion en milisegundos
        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;
        // muestra el tiempo de ejecucion en la interfaz
        mainFrame.setTiempoEjecucion(durationMs);

        if (result.getPath().isEmpty()) {
            mainFrame.mostrarError("No se encontro una ruta entre los puntos seleccionados.");
            return;
        }

        VisualizationMode modo = mainFrame.getModoSeleccionado();
        if (modo == VisualizationMode.EXPLORATION) {
            animarExploracionYRuta(result.getExploredNodes(), result.getPath());
        } else {
            animarSoloRuta(result.getPath());
        }
    }

    

    // metodo para animar la exploracion de nodos y la ruta final
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

    // metodo para limpiar las animaciones
    private void limpiarAnimacion() {
        mapPanel.setAnimatedVisited(new LinkedHashSet<>());
        mapPanel.setAnimatedPath(new LinkedHashSet<>());
    }

    // metodo para limpiar el mapa
    private void limpiarTodo() {
        nodeCounter = 1;
        startPoint = null;
        endPoint = null;
        mapGraph.clear();
        
        mapPanel.setStartPoint(null);
        mapPanel.setEndPoint(null);
        limpiarAnimacion();
        mapPanel.setMapGraph(mapGraph);
    }

    // metodo para cargar la imagen del mapa
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

    // metodo para guardar el grafo en un archivo
    private void guardarGrafo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Texto (*.txt)", "txt"));
        if (fileChooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (!archivo.getName().endsWith(".txt")) {
                archivo = new File(archivo.getAbsolutePath() + ".txt");
            }
            try {
                repository.save(mapGraph, archivo);
            } catch (Exception ex) {
                mainFrame.mostrarError("Error al guardar el grafo.");
            }
        }
    }

    // metodo para cargar el grafo desde un archivo
    private void cargarGrafo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Texto (*.txt)", "txt"));
        if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            try {
                this.mapGraph = repository.load(fileChooser.getSelectedFile());
                this.nodeCounter = mapGraph.getNodes().size() + 1;
                this.startPoint = null;
                this.endPoint = null;
                mapPanel.setStartPoint(null);
                mapPanel.setEndPoint(null);
                limpiarAnimacion();
                mapPanel.setMapGraph(mapGraph);
            } catch (Exception ex) {
                mainFrame.mostrarError("Error al cargar el archivo del grafo.");
            }
        }
    }

    // metodo para mostrar la guia de uso del programa
    private void mostrarGuia() {
        String guia = "Guía de Uso del Programa:\n\n" +
                "- Para crear nodos, haz clic derecho en el mapa y selecciona 'Crear Nodo'.\n" +
                "- Para establecer el punto de inicio o fin, haz clic derecho sobre un nodo y selecciona 'Inicio' o 'Fin'.\n" +
                "- El nodo de color verde representa el punto de inicio y el nodo de color rojo representa el punto de fin.\n" +
                "- Para crear aristas entre nodos, solamente se debe dar click en un nodo y llevarlo arrastrado hacia otro nodo.\n" +
                "- A la hora de ejecutar el algoritmo de busqueda en exploración, los nodos de color amarillo representan los nodos explorados; mientras que los nodos y aristas de color morado representan la ruta encontrada entre el nodo de inicio y el nodo de fin.\n" +
                "- A la hora de ejecutar el algoritmo de busqueda en ruta, los nodos y aristas de color morado representan la ruta encontrada entre el nodo de inicio y el nodo de fin.\n\n";
        mainFrame.mostrarInfo(guia);
    }
}