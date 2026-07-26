import controllers.MapController;
import views.MapPanel;
import views.MainFrame;
import com.formdev.flatlaf.FlatLightLaf;

public class App {
    public static void main(String[] args) throws Exception {

        // configura el tema de la interfaz con flatlaf
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
        }

        // se crea la ventana principal y el panel del mapa y se inicializa el controlador
        MainFrame mainFrame = new MainFrame();
        MapPanel mapPanel = mainFrame.getMapPanel();
        new MapController(mainFrame, mapPanel);

    }
}
