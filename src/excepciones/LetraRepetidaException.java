package excepciones;

/**
 * Se lanza cuando el jugador ingresa una letra que ya había probado
 * antes en la misma partida.
 */
public class LetraRepetidaException extends Exception {

    public LetraRepetidaException(String mensaje) {
        super(mensaje);
    }
}
