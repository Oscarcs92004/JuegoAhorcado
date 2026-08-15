package logica;

/**
 * Contrato mínimo que debe cumplir cualquier variante del juego del ahorcado.
 * Solo declara las acciones, no implementa comportamiento.
 */
public interface IJuegoAhorcado {

    void establecerPalabraSecreta();

    void jugar();
}
