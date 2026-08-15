package logica;

/**
 * Contrato mínimo que debe cumplir cualquier variante del juego del ahorcado.
 * Solo declara las acciones, no implementa comportamiento.
 */
public interface IJuegoAhorcado {

    /**
     * Define cuál es la palabra que el jugador tiene que adivinar en la partida.
     * Cada variante decide de dónde sale esa palabra (fija o al azar).
     */
    void establecerPalabraSecreta();

    /**
     * Conduce la partida: establece la palabra secreta y deja el juego listo
     * para que el jugador empiece a ingresar letras.
     */
    void jugar();
}
