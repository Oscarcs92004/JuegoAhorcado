package logica;


public class JuegoPalabraFija extends JuegoAhorcado {

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

    public void setPalabraElegida(String palabraElegida) {
        this.palabraElegida = palabraElegida;
    }

    public String getPalabraElegida() {
        return palabraElegida;
    }
}
