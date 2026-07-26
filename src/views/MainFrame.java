package views;

import javax.swing.*;
import java.awt.*;
import models.VisualizationMode;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private MapPanel mapPanel;
    private JComboBox<String> comboAlgoritmo;
    private JComboBox<VisualizationMode> comboModoVisual;
    private JButton btnEjecutar;
    private JButton btnLimpiar;
    private JButton btnCargarMapa;

    public MainFrame() {
        setTitle("Programa para la búsqueda de rutas en mapas utilizando BFS y DFS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());
        setResizable(false); // Evitar que la ventana sea redimensionable

        mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);

        // Panel de controles en la parte superior
        JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));

        btnCargarMapa = new JButton("Cargar Imagen Mapa");

        // ComboBox para seleccionar el algoritmo y el modo de visualización
        comboAlgoritmo = new JComboBox<>(new String[]{"BFS (Anchura)", "DFS (Profundidad)"});
        comboModoVisual = new JComboBox<>(VisualizationMode.values());
        
        btnEjecutar = new JButton("Buscar Ruta");
        btnLimpiar = new JButton("Limpiar Recorrido");

        panelControl.add(btnCargarMapa);
        panelControl.add(new JLabel("Algoritmo:"));
        panelControl.add(comboAlgoritmo);
        panelControl.add(new JLabel("Modo:"));
        panelControl.add(comboModoVisual);
        panelControl.add(btnEjecutar);
        panelControl.add(btnLimpiar);

        add(panelControl, BorderLayout.NORTH);
        setVisible(true);
    }

    // metodo para obtener el MapPanel 
    public MapPanel getMapPanel() {
        return mapPanel;
    }
    // metodo para obtener el algoritmo seleccionado
    public boolean esBFSSelected() {
        return comboAlgoritmo.getSelectedIndex() == 0;
    }
    // metodo para obtener el modo de visualización seleccionado 
    public VisualizationMode getModoSeleccionado() {
        return (VisualizationMode) comboModoVisual.getSelectedItem();
    }
    // listeners de los botones
    public void addListenerCargarMapa(ActionListener l) {
        btnCargarMapa.addActionListener(l);
    }
    public void addListenerEjecutar(ActionListener l) {
        btnEjecutar.addActionListener(l); 
    }
    public void addListenerLimpiar(ActionListener l) {
        btnLimpiar.addActionListener(l);
    }
    // metodo para mostrar mensaje de error
    public void mostrarError(String msj) {
        JOptionPane.showMessageDialog(this, msj, "Atención", JOptionPane.WARNING_MESSAGE);
    }
}