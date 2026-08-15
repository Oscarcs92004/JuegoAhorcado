package logica;

/**
 * Variante en la que la palabra secreta se le pasa directamente al juego
 * (por ejemplo, la escribe un jugador para que otro la adivine).
 */
public class JuegoPalabraFija extends JuegoAhorcado {

    /** La palabra que eligió el usuario para esta partida. */
    private String palabraElegida;

    public JuegoPalabraFija(String palabraElegida) {
        super();
        this.palabraElegida = palabraElegida;
    }

    @Override
    public void establecerPalabraSecreta() {
        asignarPalabraSecreta(palabraElegida);
    }

    @Override
    protected void actualizarPalabraMostrada(char letra) {
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                palabraMostrada.setCharAt(i, letra);
            }
        }
    }

    @Override
    public boolean verificarLetra(char letra) {
        return palabraSecreta.indexOf(letra) >= 0;
    }

    @Override
    public boolean gano() {
        return palabraMostrada.toString().equals(palabraSecreta);
    }

    @Override
    public boolean perdio() {
        return intentosRestantes <= 0 && !gano();
    }

    /** Permite cambiar la palabra antes de empezar una partida nueva. */
    public void setPalabraElegida(String palabraElegida) {
        this.palabraElegida = palabraElegida;
    }

    public String getPalabraElegida() {
        return palabraElegida;
    }
}
