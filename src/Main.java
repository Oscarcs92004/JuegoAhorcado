import gui.VentanaPrincipal;
import logica.AdministradorPalabras;
import logica.JuegoPalabraAzar;
import logica.JuegoPalabraFija;

import javax.swing.SwingUtilities;

/**
 * Juego del Ahorcado - Laboratorio de Programación II
 *
 * Integrantes: Marcelo, Oscar, Alex, Leandro
 *
 * Punto de entrada: crea las dos variantes del juego y abre la ventana.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdministradorPalabras administrador = new AdministradorPalabras();

            // Las dos modalidades quedan disponibles desde el arranque.
            // La palabra de la variante fija se reemplaza cuando el jugador
            // elige esa modalidad y escribe la suya.
            JuegoPalabraFija juegoFijo = new JuegoPalabraFija("PROGRAMACION");
            JuegoPalabraAzar juegoAzar = new JuegoPalabraAzar(administrador);

            VentanaPrincipal ventana = new VentanaPrincipal(juegoFijo, juegoAzar);
            ventana.setVisible(true);
        });
    }
}
