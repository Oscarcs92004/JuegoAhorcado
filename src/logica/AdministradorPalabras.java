package logica;

import excepciones.PalabraDuplicadaException;

import java.util.ArrayList;
import java.util.Random;

/**
 * Administra el conjunto de palabras que se pueden usar en la modalidad al azar.
 * Es independiente de la jerarquía del juego: no hereda ni implementa nada de ella.
 */
public class AdministradorPalabras {

    private ArrayList<String> palabras;
    private Random random;

    public AdministradorPalabras() {
        this.palabras = new ArrayList<>();
        this.random = new Random();
        cargarPalabrasIniciales();
    }

    /** Palabras con las que arranca el juego. */
    private void cargarPalabrasIniciales() {
        String[] iniciales = {
                "PROGRAMACION", "COMPUTADORA", "TECLADO", "JAVA", "HERENCIA",
                "POLIMORFISMO", "EXCEPCION", "INTERFAZ", "ALGORITMO", "VENTANA",
                "MONTAÑA", "CAMION"
        };
        for (String palabra : iniciales) {
            try {
                agregarPalabra(palabra);
            } catch (PalabraDuplicadaException e) {
                // No debería pasar con la lista inicial, pero si pasa la salteamos.
            }
        }
    }

    /**
     * Agrega una palabra nueva a la colección.
     *
     * @throws PalabraDuplicadaException si la palabra ya está en la lista
     */
    public void agregarPalabra(String palabra) throws PalabraDuplicadaException {
        String limpia = palabra.trim().toUpperCase();

        if (palabras.contains(limpia)) {
            throw new PalabraDuplicadaException("La palabra " + limpia + " ya existe en la lista.");
        }

        palabras.add(limpia);
    }

    /** Devuelve una palabra elegida al azar de la colección. */
    public String obtenerPalabraAleatoria() {
        int posicion = random.nextInt(palabras.size());
        return palabras.get(posicion);
    }

    public ArrayList<String> getPalabras() {
        return palabras;
    }

    public int cantidadPalabras() {
        return palabras.size();
    }
}
