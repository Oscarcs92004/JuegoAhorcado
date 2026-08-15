package gui;

import excepciones.LetraInvalidaException;
import excepciones.LetraRepetidaException;
import excepciones.PalabraDuplicadaException;
import logica.JuegoAhorcado;
import logica.JuegoPalabraAzar;
import logica.JuegoPalabraFija;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Ventana del juego. Se limita a mostrar información y a llamar a los métodos
 * de las clases de lógica; no calcula nada del juego por su cuenta.
 *
 * Tiene dos pantallas manejadas con CardLayout: el menú de modalidad y la
 * pantalla de partida.
 */
public class VentanaPrincipal extends JFrame {

    private static final Color COLOR_ACIERTO = new Color(0, 130, 60);
    private static final Color COLOR_ERROR = new Color(180, 30, 30);
    private static final Color COLOR_NEUTRO = new Color(50, 50, 50);

    /** Las dos modalidades, creadas en Main. */
    private JuegoPalabraFija juegoFijo;
    private JuegoPalabraAzar juegoAzar;

    /** La modalidad que se está jugando en este momento. */
    private JuegoAhorcado juegoActual;

    private CardLayout cardLayout;
    private JPanel panelPrincipal;

    private PanelAhorcado panelAhorcado;
    private JLabel etiquetaModalidad;
    private JLabel etiquetaPalabra;
    private JLabel etiquetaIntentos;
    private JLabel etiquetaAcertadas;
    private JLabel etiquetaErradas;
    private JLabel etiquetaMensaje;
    private JTextField campoLetra;
    private JButton botonProbar;

    public VentanaPrincipal(JuegoPalabraFija juegoFijo, JuegoPalabraAzar juegoAzar) {
        this.juegoFijo = juegoFijo;
        this.juegoAzar = juegoAzar;

        setTitle("Juego del Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 480);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.add(crearPanelMenu(), "MENU");
        panelPrincipal.add(crearPanelPartida(), "PARTIDA");

        add(panelPrincipal);
        cardLayout.show(panelPrincipal, "MENU");
    }

    // ------------------------------------------------------------------
    // Pantalla 1: menú de modalidad
    // ------------------------------------------------------------------

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        JLabel titulo = new JLabel("JUEGO DEL AHORCADO", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Elegí una modalidad", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

        JButton botonFija = new JButton("Jugar con palabra fija");
        botonFija.setAlignmentX(CENTER_ALIGNMENT);
        botonFija.setMaximumSize(new Dimension(280, 45));
        botonFija.addActionListener(e -> iniciarModalidadFija());

        JButton botonAzar = new JButton("Jugar con palabra al azar");
        botonAzar.setAlignmentX(CENTER_ALIGNMENT);
        botonAzar.setMaximumSize(new Dimension(280, 45));
        botonAzar.addActionListener(e -> iniciarModalidadAzar());

        JButton botonAgregar = new JButton("Agregar palabra a la lista");
        botonAgregar.setAlignmentX(CENTER_ALIGNMENT);
        botonAgregar.setMaximumSize(new Dimension(280, 35));
        botonAgregar.addActionListener(e -> agregarPalabra());

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitulo);
        panel.add(Box.createVerticalStrut(35));
        panel.add(botonFija);
        panel.add(Box.createVerticalStrut(15));
        panel.add(botonAzar);
        panel.add(Box.createVerticalStrut(30));
        panel.add(botonAgregar);

        return panel;
    }

    /** Le pide la palabra al usuario y arranca la partida con palabra fija. */
    private void iniciarModalidadFija() {
        String palabra = JOptionPane.showInputDialog(this,
                "Escribí la palabra secreta para que la adivine el otro jugador:",
                "Palabra fija", JOptionPane.QUESTION_MESSAGE);

        if (palabra == null) {
            return; // el usuario canceló
        }

        if (palabra.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tenés que escribir una palabra.",
                    "Palabra inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        juegoFijo.setPalabraElegida(palabra);
        empezarPartida(juegoFijo, "Modalidad: palabra fija");
    }

    /** Arranca la partida con palabra al azar. */
    private void iniciarModalidadAzar() {
        empezarPartida(juegoAzar, "Modalidad: palabra al azar");
    }

    /**
     * Agrega una palabra a la administradora. Acá se captura la tercera
     * excepción propia: PalabraDuplicadaException.
     */
    private void agregarPalabra() {
        String palabra = JOptionPane.showInputDialog(this,
                "Escribí la palabra que querés agregar a la lista:",
                "Agregar palabra", JOptionPane.QUESTION_MESSAGE);

        if (palabra == null || palabra.trim().isEmpty()) {
            return;
        }

        try {
            juegoAzar.getAdministrador().agregarPalabra(palabra);
            JOptionPane.showMessageDialog(this,
                    "Palabra agregada. Ahora hay " + juegoAzar.getAdministrador().cantidadPalabras() + " palabras.",
                    "Listo", JOptionPane.INFORMATION_MESSAGE);
        } catch (PalabraDuplicadaException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Palabra duplicada", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------------------------------------------------------
    // Pantalla 2: partida
    // ------------------------------------------------------------------

    private JPanel crearPanelPartida() {
        JPanel panel = new JPanel(new BorderLayout(20, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        panelAhorcado = new PanelAhorcado();
        panel.add(panelAhorcado, BorderLayout.WEST);

        // ---- Zona de información ----
        JPanel panelInfo = new JPanel(new GridLayout(5, 1, 0, 8));

        etiquetaModalidad = new JLabel("");
        etiquetaModalidad.setFont(new Font("SansSerif", Font.BOLD, 15));

        etiquetaPalabra = new JLabel("");
        etiquetaPalabra.setFont(new Font("Monospaced", Font.BOLD, 30));

        etiquetaIntentos = new JLabel("");
        etiquetaIntentos.setFont(new Font("SansSerif", Font.PLAIN, 15));

        etiquetaAcertadas = new JLabel("");
        etiquetaAcertadas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        etiquetaAcertadas.setForeground(COLOR_ACIERTO);

        etiquetaErradas = new JLabel("");
        etiquetaErradas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        etiquetaErradas.setForeground(COLOR_ERROR);

        panelInfo.add(etiquetaModalidad);
        panelInfo.add(etiquetaPalabra);
        panelInfo.add(etiquetaIntentos);
        panelInfo.add(etiquetaAcertadas);
        panelInfo.add(etiquetaErradas);

        panel.add(panelInfo, BorderLayout.CENTER);

        // ---- Zona de entrada y mensajes ----
        JPanel panelAbajo = new JPanel(new BorderLayout(0, 8));

        etiquetaMensaje = new JLabel("Ingresá una letra para empezar.", SwingConstants.CENTER);
        etiquetaMensaje.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        campoLetra = new JTextField(3);
        campoLetra.setFont(new Font("SansSerif", Font.BOLD, 20));
        campoLetra.setHorizontalAlignment(SwingConstants.CENTER);
        campoLetra.addActionListener(e -> probarLetra()); // permite jugar con Enter

        botonProbar = new JButton("Probar letra");
        botonProbar.addActionListener(e -> probarLetra());

        JButton botonReiniciar = new JButton("Reiniciar partida");
        botonReiniciar.addActionListener(e -> reiniciarPartida());

        JButton botonVolver = new JButton("Volver al menú");
        botonVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "MENU"));

        panelEntrada.add(campoLetra);
        panelEntrada.add(botonProbar);
        panelEntrada.add(botonReiniciar);
        panelEntrada.add(botonVolver);

        panelAbajo.add(etiquetaMensaje, BorderLayout.NORTH);
        panelAbajo.add(panelEntrada, BorderLayout.CENTER);

        panel.add(panelAbajo, BorderLayout.SOUTH);

        return panel;
    }

    // ------------------------------------------------------------------
    // Manejo de la partida
    // ------------------------------------------------------------------

    /** Deja lista la pantalla de partida para la modalidad elegida. */
    private void empezarPartida(JuegoAhorcado juego, String textoModalidad) {
        this.juegoActual = juego;
        this.juegoActual.jugar();

        etiquetaModalidad.setText(textoModalidad);
        panelAhorcado.reiniciar();
        habilitarEntrada(true);
        mostrarMensaje("Ingresá una letra para empezar.", COLOR_NEUTRO);
        actualizarPantalla();

        cardLayout.show(panelPrincipal, "PARTIDA");
        campoLetra.requestFocusInWindow();
    }

    /** Empieza otra partida con la misma modalidad. */
    private void reiniciarPartida() {
        if (juegoActual == null) {
            return;
        }
        juegoActual.jugar();
        panelAhorcado.reiniciar();
        habilitarEntrada(true);
        mostrarMensaje("Partida nueva. Ingresá una letra.", COLOR_NEUTRO);
        actualizarPantalla();
        campoLetra.requestFocusInWindow();
    }

    /**
     * Toma lo que escribió el jugador y se lo pasa a la lógica del juego.
     * Acá se capturan las excepciones propias que lanza procesarLetra().
     */
    private void probarLetra() {
        if (juegoActual == null || juegoActual.partidaTerminada()) {
            return;
        }

        String entrada = campoLetra.getText();

        try {
            boolean acerto = juegoActual.procesarLetra(entrada);
            char letra = entrada.trim().toUpperCase().charAt(0);

            if (acerto) {
                mostrarMensaje("¡Bien! La letra " + letra + " está en la palabra.", COLOR_ACIERTO);
            } else {
                mostrarMensaje("La letra " + letra + " no está en la palabra. Te quedan "
                        + juegoActual.getIntentosRestantes() + " intentos.", COLOR_ERROR);
            }
        } catch (LetraInvalidaException | LetraRepetidaException e) {
            // El juego sigue normalmente, solo le avisamos al jugador.
            mostrarMensaje(e.getMessage(), COLOR_ERROR);
        }

        campoLetra.setText("");
        campoLetra.requestFocusInWindow();
        actualizarPantalla();
        verificarFinDePartida();
    }

    /** Vuelca el estado del juego en las etiquetas y en la figura. */
    private void actualizarPantalla() {
        if (juegoActual == null) {
            return;
        }

        // Separamos las letras con espacios para que se lean mejor: P _ L _ B R A
        StringBuilder mostrada = new StringBuilder();
        String palabra = juegoActual.getPalabraMostrada();
        for (int i = 0; i < palabra.length(); i++) {
            mostrada.append(palabra.charAt(i));
            if (i < palabra.length() - 1) {
                mostrada.append(' ');
            }
        }

        etiquetaPalabra.setText(mostrada.toString());
        etiquetaIntentos.setText("Intentos restantes: " + juegoActual.getIntentosRestantes()
                + " de " + juegoActual.getMaxIntentos());
        etiquetaAcertadas.setText("Acertadas: " + formatearLetras(juegoActual.getLetrasAcertadas()));
        etiquetaErradas.setText("Erradas: " + formatearLetras(juegoActual.getLetrasErradas()));

        panelAhorcado.mostrarEtapa(juegoActual.getIntentosFallidos());
    }

    /** Avisa si el jugador ganó o perdió y bloquea la entrada. */
    private void verificarFinDePartida() {
        if (juegoActual.gano()) {
            habilitarEntrada(false);
            mostrarMensaje("¡Ganaste! La palabra era " + juegoActual.getPalabraSecreta() + ".", COLOR_ACIERTO);
            JOptionPane.showMessageDialog(this,
                    "¡Ganaste! La palabra era " + juegoActual.getPalabraSecreta() + ".",
                    "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
        } else if (juegoActual.perdio()) {
            habilitarEntrada(false);
            mostrarMensaje("Perdiste, se agotaron los 6 intentos. La palabra era "
                    + juegoActual.getPalabraSecreta() + ".", COLOR_ERROR);
            JOptionPane.showMessageDialog(this,
                    "Perdiste, se agotaron los 6 intentos.\nLa palabra era "
                            + juegoActual.getPalabraSecreta() + ".",
                    "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void habilitarEntrada(boolean habilitada) {
        campoLetra.setEnabled(habilitada);
        botonProbar.setEnabled(habilitada);
    }

    private void mostrarMensaje(String texto, Color color) {
        etiquetaMensaje.setText(texto);
        etiquetaMensaje.setForeground(color);
    }

    /** Arma un texto tipo "A, E, S" con la lista de letras. */
    private String formatearLetras(java.util.ArrayList<Character> letras) {
        if (letras.isEmpty()) {
            return "-";
        }
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < letras.size(); i++) {
            texto.append(letras.get(i));
            if (i < letras.size() - 1) {
                texto.append(", ");
            }
        }
        return texto.toString();
    }
}
