package logica;

/**
 * Variante en la que la palabra secreta se elige al azar de la colección
 * que mantiene AdministradorPalabras.
 *
 * ====================================================================
 * PARTE A COMPLETAR — OSCAR
 * ====================================================================
 * Los métodos de abajo están declarados pero sin terminar. Hay que
 * implementar los cuatro métodos abstractos que dejó pendientes
 * JuegoAhorcado, más establecerPalabraSecreta() de la interfaz.
 *
 * Podés mirar JuegoPalabraFija como referencia: la lógica de las letras
 * es prácticamente la misma, lo único que cambia de verdad es de dónde
 * sale la palabra secreta (acá la pide el administrador, no la elige el
 * usuario).
 *
 * Atributos heredados que vas a necesitar (son protected, se usan directo):
 *   palabraSecreta      String con la palabra completa, ya en mayúsculas
 *   palabraMostrada     StringBuilder con los guiones bajos y los aciertos
 *   intentosRestantes   int
 *
 * Métodos heredados útiles:
 *   asignarPalabraSecreta(String)   guarda la palabra ya normalizada
 * ====================================================================
 */
public class JuegoPalabraAzar extends JuegoAhorcado {

    /** De acá sale la palabra al azar. */
    private AdministradorPalabras administrador;

    public JuegoPalabraAzar(AdministradorPalabras administrador) {
        super();
        this.administrador = administrador;
    }

    @Override
    public void establecerPalabraSecreta() {
        // TODO (Oscar): pedirle una palabra al azar al administrador con
        // obtenerPalabraAleatoria() y guardarla con asignarPalabraSecreta(...).
        asignarPalabraSecreta("PENDIENTE");
    }

    @Override
    protected void actualizarPalabraMostrada(char letra) {
        // TODO (Oscar): recorrer palabraSecreta y, en cada posición donde esté
        // esta letra, reemplazar el guión bajo de palabraMostrada usando
        // palabraMostrada.setCharAt(posicion, letra).
    }

    @Override
    public boolean verificarLetra(char letra) {
        // TODO (Oscar): devolver true si la letra está dentro de palabraSecreta.
        return false;
    }

    @Override
    public boolean gano() {
        // TODO (Oscar): devolver true si palabraMostrada ya es igual a palabraSecreta.
        // Ojo: palabraMostrada es un StringBuilder, hay que pasarlo a String
        // con toString() antes de compararlo.
        return false;
    }

    @Override
    public boolean perdio() {
        // TODO (Oscar): devolver true si no quedan intentos y además no ganó.
        return false;
    }

    public AdministradorPalabras getAdministrador() {
        return administrador;
    }
}
