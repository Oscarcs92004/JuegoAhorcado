package logica;

import excepciones.PalabraDuplicadaException;

import java.util.ArrayList;
import java.util.Random;


public class AdministradorPalabras {

    private ArrayList<String> palabras;
    private Random random;

    public AdministradorPalabras() {
        this.palabras = new ArrayList<>();
        this.random = new Random();
        cargarPalabrasIniciales();
    }

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


    public void agregarPalabra(String palabra) throws PalabraDuplicadaException {
        String limpia = palabra.trim().toUpperCase();
        if (palabras.contains(limpia)) {
            throw new PalabraDuplicadaException("La palabra " + limpia + " ya existe en la lista.");
        }

        palabras.add(limpia);
    }

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
