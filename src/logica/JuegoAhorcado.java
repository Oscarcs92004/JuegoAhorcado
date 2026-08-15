package logica;

import excepciones.LetraInvalidaException;
import excepciones.LetraRepetidaException;

import java.util.ArrayList;

/**
 * Clase abstracta base del juego. Concentra los datos y el comportamiento que
 * comparten las dos variantes (palabra fija y palabra al azar).
 *
 * No depende de ninguna clase de Swing: toda la lógica del juego vive acá.
 */
public abstract class JuegoAhorcado implements IJuegoAhorcado {

    /** Máximo de intentos permitidos en toda la partida. */
    protected static final int MAX_INTENTOS = 6;

    /** La palabra secreta completa que el jugador debe adivinar. */
    protected String palabraSecreta;

    /** Representación parcial: guiones bajos y las letras ya acertadas. */
    protected StringBuilder palabraMostrada;

    /** Intentos que le quedan al jugador antes de perder. */
    protected int intentosRestantes;

    /** Letras que el jugador ya ingresó, para detectar repetidas. */
    protected ArrayList<Character> letrasIngresadas;

    /** Etapas de la figura del ahorcado, una por cada cantidad de errores. */
    protected ArrayList<String> figuraAhorcado;

    public JuegoAhorcado() {
        this.palabraSecreta = "";
        this.palabraMostrada = new StringBuilder();
        this.intentosRestantes = MAX_INTENTOS;
        this.letrasIngresadas = new ArrayList<>();
        this.figuraAhorcado = new ArrayList<>();
        // La posición de la lista coincide con la cantidad de intentos fallidos:
        // con 0 errores se ve solo la horca, con 6 el muñeco completo.
        this.figuraAhorcado.add("HORCA");
        this.figuraAhorcado.add("CABEZA");
        this.figuraAhorcado.add("CUERPO");
        this.figuraAhorcado.add("BRAZO_IZQUIERDO");
        this.figuraAhorcado.add("BRAZO_DERECHO");
        this.figuraAhorcado.add("PIERNA_IZQUIERDA");
        this.figuraAhorcado.add("PIERNA_DERECHA");
    }

    // ------------------------------------------------------------------
    // Métodos de la interfaz
    // ------------------------------------------------------------------

    /**
     * Inicia (o reinicia) la partida: pide la palabra secreta a la variante
     * concreta y deja todo el estado en cero.
     *
     * En una aplicación de consola este método sería un bucle que pide letras
     * hasta terminar. Como el juego es gráfico, un bucle así congelaría la
     * ventana, así que el turno a turno se resuelve en procesarLetra().
     */
    @Override
    public void jugar() {
        establecerPalabraSecreta();
        this.palabraMostrada = new StringBuilder();
        for (int i = 0; i < palabraSecreta.length(); i++) {
            this.palabraMostrada.append('_');
        }
        this.intentosRestantes = MAX_INTENTOS;
        this.letrasIngresadas.clear();
    }

    // ------------------------------------------------------------------
    // Métodos abstractos: los implementa cada variante concreta
    // ------------------------------------------------------------------

    /** Revela la letra acertada en todas las posiciones donde aparece. */
    protected abstract void actualizarPalabraMostrada(char letra);

    /** Indica si la letra forma parte de la palabra secreta. */
    public abstract boolean verificarLetra(char letra);

    /** Indica si el jugador ya completó toda la palabra. */
    public abstract boolean gano();

    /** Indica si el jugador se quedó sin intentos. */
    public abstract boolean perdio();

    // ------------------------------------------------------------------
    // Lógica de la partida
    // ------------------------------------------------------------------

    /**
     * Procesa la letra que ingresó el jugador: la valida, la registra y
     * actualiza el estado de la partida.
     *
     * @param entrada texto tal como lo escribió el jugador
     * @return true si la letra estaba en la palabra, false si fue un error
     * @throws LetraInvalidaException  si no ingresó una única letra
     * @throws LetraRepetidaException  si ya había ingresado esa letra
     */
    public boolean procesarLetra(String entrada) throws LetraInvalidaException, LetraRepetidaException {
        if (entrada == null || entrada.trim().length() != 1) {
            throw new LetraInvalidaException("Entrada inválida: ingresá una sola letra (A-Z o Ñ).");
        }

        char letra = normalizar(entrada.trim().charAt(0));

        if (!esLetra(letra)) {
            throw new LetraInvalidaException("Entrada inválida: ingresá una sola letra (A-Z o Ñ).");
        }

        if (letrasIngresadas.contains(letra)) {
            throw new LetraRepetidaException("Ya ingresaste la letra " + letra + ". Probá con otra.");
        }

        letrasIngresadas.add(letra);

        if (verificarLetra(letra)) {
            actualizarPalabraMostrada(letra);
            return true;
        }

        intentosRestantes--;
        return false;
    }

    /** La partida terminó cuando el jugador ganó o se quedó sin intentos. */
    public boolean partidaTerminada() {
        return gano() || perdio();
    }

    // ------------------------------------------------------------------
    // Normalización de letras (ver decisiones de validación en el README)
    // ------------------------------------------------------------------

    /** Pasa la letra a mayúscula y le saca la tilde. La Ñ se mantiene. */
    protected char normalizar(char letra) {
        char mayuscula = Character.toUpperCase(letra);
        switch (mayuscula) {
            case 'Á': return 'A';
            case 'É': return 'E';
            case 'Í': return 'I';
            case 'Ó': return 'O';
            case 'Ú': return 'U';
            case 'Ü': return 'U';
            default:  return mayuscula;
        }
    }

    /** Aplica normalizar() a cada carácter de una palabra. */
    protected String normalizarPalabra(String palabra) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < palabra.length(); i++) {
            resultado.append(normalizar(palabra.charAt(i)));
        }
        return resultado.toString();
    }

    /** Solo son letras válidas la A-Z y la Ñ. */
    protected boolean esLetra(char letra) {
        return (letra >= 'A' && letra <= 'Z') || letra == 'Ñ';
    }

    /** Guarda la palabra secreta ya normalizada. La usan las variantes. */
    protected void asignarPalabraSecreta(String palabra) {
        this.palabraSecreta = normalizarPalabra(palabra.trim());
    }

    // ------------------------------------------------------------------
    // Getters para que la interfaz gráfica muestre el estado
    // ------------------------------------------------------------------

    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    public String getPalabraMostrada() {
        return palabraMostrada.toString();
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public int getMaxIntentos() {
        return MAX_INTENTOS;
    }

    public int getIntentosFallidos() {
        return MAX_INTENTOS - intentosRestantes;
    }

    public ArrayList<Character> getLetrasIngresadas() {
        return letrasIngresadas;
    }

    public ArrayList<String> getFiguraAhorcado() {
        return figuraAhorcado;
    }

    /** Etapa de la figura que corresponde a los errores cometidos. */
    public String getEtapaActual() {
        return figuraAhorcado.get(getIntentosFallidos());
    }

    /** Letras ingresadas que sí estaban en la palabra. */
    public ArrayList<Character> getLetrasAcertadas() {
        ArrayList<Character> acertadas = new ArrayList<>();
        for (Character letra : letrasIngresadas) {
            if (palabraSecreta.indexOf(letra) >= 0) {
                acertadas.add(letra);
            }
        }
        return acertadas;
    }

    /** Letras ingresadas que no estaban en la palabra. */
    public ArrayList<Character> getLetrasErradas() {
        ArrayList<Character> erradas = new ArrayList<>();
        for (Character letra : letrasIngresadas) {
            if (palabraSecreta.indexOf(letra) < 0) {
                erradas.add(letra);
            }
        }
        return erradas;
    }
}
