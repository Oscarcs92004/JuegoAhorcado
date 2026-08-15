import gui.VentanaPrincipal;
import logica.AdministradorPalabras;
import logica.JuegoPalabraAzar;
import logica.JuegoPalabraFija;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdministradorPalabras administrador = new AdministradorPalabras();

            JuegoPalabraFija juegoFijo = new JuegoPalabraFija("PROGRAMACION");
            JuegoPalabraAzar juegoAzar = new JuegoPalabraAzar(administrador);

            VentanaPrincipal ventana = new VentanaPrincipal(juegoFijo, juegoAzar);
            ventana.setVisible(true);
        });
    }
}
