package excepciones;

/**
 * Se lanza cuando se intenta agregar a la administradora una palabra
 * que ya existe en la colección.
 */
public class PalabraDuplicadaException extends Exception {

    public PalabraDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
