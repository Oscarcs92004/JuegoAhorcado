package logica;


public class JuegoPalabraAzar extends JuegoAhorcado {

    private AdministradorPalabras administrador;

    public JuegoPalabraAzar(AdministradorPalabras administrador) {
        super();
        this.administrador = administrador;
    }

    @Override
    public void establecerPalabraSecreta() {
        String palabraSecreta;
        palabraSecreta = administrador.obtenerPalabraAleatoria();
        asignarPalabraSecreta(palabraSecreta);
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
        for(int i = 0; i < palabraSecreta.length(); i++){
            if(letra == palabraSecreta.charAt(i)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean gano() {
        if(palabraMostrada.toString().equals(palabraSecreta)) return true;
        return false;
    }

    @Override
    public boolean perdio() {
        if(intentosRestantes <= 0 && !palabraMostrada.toString().equals(palabraSecreta)) return true;
        return false;
    }

    public AdministradorPalabras getAdministrador() {
        return administrador;
    }
}
