package excepciones;

/**
 * Se lanza cuando el jugador no ingresa una única letra: campo vacío,
 * un número, un símbolo, o más de un carácter.
 */
public class LetraInvalidaException extends Exception {

    public LetraInvalidaException(String mensaje) {
        super(mensaje);
    }
}
