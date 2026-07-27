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
    private JButton btnCargarGrafo;
    private JButton btnGuardarGrafo;
    private JButton btnGuia;
    private JLabel lblTiempo;

    public MainFrame() {
        setTitle("Programa para la búsqueda de rutas en mapas utilizando BFS y DFS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());
        setResizable(false); // Evitar que la ventana sea redimensionable

        mapPanel = new MapPanel();
        add(mapPanel, BorderLayout.CENTER);

        // Panel lateral izquierdo con GridBagLayout para los controles
        JPanel panelControl = new JPanel(new GridBagLayout());
        panelControl.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        btnCargarMapa = new JButton("Cargar Imagen Mapa");
        btnCargarGrafo = new JButton("Cargar Grafo");
        btnGuardarGrafo = new JButton("Guardar Grafo");
        btnGuia = new JButton("Guia");
        lblTiempo = new JLabel("Tiempo: 0.00 ms");

        // ComboBox para seleccionar el algoritmo y el modo de visualización
        comboAlgoritmo = new JComboBox<>(new String[]{"BFS", "DFS"});
        comboModoVisual = new JComboBox<>(VisualizationMode.values());
        
        btnEjecutar = new JButton("Buscar Ruta");
        btnLimpiar = new JButton("Limpiar Recorrido");

        // Agregando componentes al panel de control con GridBagLayout
        panelControl.add(new JLabel("Archivos"), gbc);
        
        gbc.gridy++;
        panelControl.add(btnCargarMapa, gbc);

        gbc.gridy++;
        panelControl.add(btnCargarGrafo, gbc);

        gbc.gridy++;
        panelControl.add(btnGuardarGrafo, gbc);

        gbc.gridy++;
        panelControl.add(new JLabel("Algoritmos"), gbc);

        gbc.gridy++;
        panelControl.add(comboAlgoritmo, gbc);

        gbc.gridy++;
        panelControl.add(new JLabel("Modos"), gbc);

        gbc.gridy++;
        panelControl.add(comboModoVisual, gbc);

        gbc.gridy++;
        panelControl.add(btnEjecutar, gbc);

        gbc.gridy++;
        panelControl.add(btnLimpiar, gbc);

        gbc.gridy++;
        panelControl.add(lblTiempo, gbc);

        gbc.gridy++;
        panelControl.add(new JLabel("Guia"), gbc);

        gbc.gridy++;
        panelControl.add(btnGuia, gbc);

        add(panelControl, BorderLayout.WEST);
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
    public void addListenerCargarGrafo(ActionListener l) {
        btnCargarGrafo.addActionListener(l);
    }
    public void addListenerGuardarGrafo(ActionListener l) {
        btnGuardarGrafo.addActionListener(l);
    }
    public void addListenerEjecutar(ActionListener l) {
        btnEjecutar.addActionListener(l); 
    }
    public void addListenerLimpiar(ActionListener l) {
        btnLimpiar.addActionListener(l);
    }
    public void addListenerGuia(ActionListener l) {
        btnGuia.addActionListener(l);
    }

    // metodo para mostrar mensaje de error
    public void mostrarError(String msj) {
        JOptionPane.showMessageDialog(this, msj, "Atención", JOptionPane.WARNING_MESSAGE);
    }

    // metodo para mostrar mensaje de informacion
    public void mostrarInfo(String msj) {
        JOptionPane.showMessageDialog(this, msj, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    // metodo para actualizar el tiempo de ejecucion del algoritmo
    public void setTiempoEjecucion(double milisegundos) {
        lblTiempo.setText(String.format("Tiempo: %.3f ms", milisegundos));
    }
}