package logica;

import excepciones.LetraInvalidaException;
import excepciones.LetraRepetidaException;

import java.util.ArrayList;


public abstract class JuegoAhorcado implements IJuegoAhorcado {

    protected static final int MAX_INTENTOS = 6;

    protected String palabraSecreta;

    protected StringBuilder palabraMostrada;

    protected int intentosRestantes;

    protected ArrayList<Character> letrasIngresadas;

    protected ArrayList<String> figuraAhorcado;

    public JuegoAhorcado() {
        this.palabraSecreta = "";
        this.palabraMostrada = new StringBuilder();
        this.intentosRestantes = MAX_INTENTOS;
        this.letrasIngresadas = new ArrayList<>();
        this.figuraAhorcado = new ArrayList<>();
        this.figuraAhorcado.add("HORCA");
        this.figuraAhorcado.add("CABEZA");
        this.figuraAhorcado.add("CUERPO");
        this.figuraAhorcado.add("BRAZO_IZQUIERDO");
        this.figuraAhorcado.add("BRAZO_DERECHO");
        this.figuraAhorcado.add("PIERNA_IZQUIERDA");
        this.figuraAhorcado.add("PIERNA_DERECHA");
    }

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

    protected abstract void actualizarPalabraMostrada(char letra);

    public abstract boolean verificarLetra(char letra);

    public abstract boolean gano();

    public abstract boolean perdio();

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

    public boolean partidaTerminada() {
        return gano() || perdio();
    }

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

    protected String normalizarPalabra(String palabra) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < palabra.length(); i++) {
            resultado.append(normalizar(palabra.charAt(i)));
        }
        return resultado.toString();
    }

    protected boolean esLetra(char letra) {
        return (letra >= 'A' && letra <= 'Z') || letra == 'Ñ';
    }

    protected void asignarPalabraSecreta(String palabra) {
        this.palabraSecreta = normalizarPalabra(palabra.trim());
    }

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

    public String getEtapaActual() {
        return figuraAhorcado.get(getIntentosFallidos());
    }

    public ArrayList<Character> getLetrasAcertadas() {
        ArrayList<Character> acertadas = new ArrayList<>();
        for (Character letra : letrasIngresadas) {
            if (palabraSecreta.indexOf(letra) >= 0) {
                acertadas.add(letra);
            }
        }
        return acertadas;
    }

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
