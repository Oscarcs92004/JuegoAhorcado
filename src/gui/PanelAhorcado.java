package gui;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Dibuja la figura del ahorcado. Cada vez que el jugador falla se agrega una
 * parte nueva del muñeco, y esa parte aparece animada (se va dibujando de a
 * poco) en lugar de aparecer de golpe.
 *
 * Solo se encarga de mostrar: no sabe nada de la lógica del juego, recibe
 * únicamente la cantidad de errores.
 *
 * ====================================================================
 * PARTE A COMPLETAR — ALEX Y LEANDRO
 * ====================================================================
 * La mecánica de la animación ya está hecha (el Timer, el progreso y el
 * método auxiliar dibujarLineaAnimada). Faltan las partes del muñeco:
 * ver los TODO en dibujarParte(). La horca y la cabeza quedan como ejemplo.
 *
 * Coordenadas sugeridas (el panel mide 260 x 320):
 *   cuerpo            de (170, 110) a (170, 190)
 *   brazo izquierdo   de (170, 130) a (140, 165)
 *   brazo derecho     de (170, 130) a (200, 165)
 *   pierna izquierda  de (170, 190) a (140, 235)
 *   pierna derecha    de (170, 190) a (200, 235)
 * ====================================================================
 */
public class PanelAhorcado extends JPanel {

    private static final Color COLOR_HORCA = new Color(120, 80, 40);
    private static final Color COLOR_MUNIECO = new Color(40, 40, 40);

    /** Cantidad de partes del muñeco que hay que mostrar (0 a 6). */
    private int partesDibujadas;

    /** Avance de la animación de la última parte: va de 0.0 a 1.0. */
    private double progreso;

    /** Hace avanzar el progreso para que la parte nueva se dibuje de a poco. */
    private Timer temporizador;

    public PanelAhorcado() {
        setPreferredSize(new Dimension(260, 320));
        setBackground(Color.WHITE);
        this.partesDibujadas = 0;
        this.progreso = 1.0;

        // Cada 20 milisegundos avanza un 5% hasta completar la parte.
        this.temporizador = new Timer(20, e -> {
            progreso = progreso + 0.05;
            if (progreso >= 1.0) {
                progreso = 1.0;
                temporizador.stop();
            }
            repaint();
        });
    }

    /**
     * Muestra la figura que corresponde a la cantidad de errores.
     * Si hay un error nuevo, arranca la animación de esa parte.
     */
    public void mostrarEtapa(int errores) {
        if (errores > partesDibujadas) {
            partesDibujadas = errores;
            progreso = 0.0;
            temporizador.restart();
        } else {
            partesDibujadas = errores;
            progreso = 1.0;
            temporizador.stop();
        }
        repaint();
    }

    /** Vuelve a dejar solo la horca, para empezar una partida nueva. */
    public void reiniciar() {
        temporizador.stop();
        partesDibujadas = 0;
        progreso = 1.0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        dibujarHorca(g2);

        g2.setColor(COLOR_MUNIECO);
        for (int parte = 1; parte <= partesDibujadas; parte++) {
            // Las partes viejas se dibujan enteras; la última, según el progreso.
            double avance = (parte == partesDibujadas) ? progreso : 1.0;
            dibujarParte(g2, parte, avance);
        }
    }

    /** La horca se dibuja siempre completa, desde el arranque de la partida. */
    private void dibujarHorca(Graphics2D g2) {
        g2.setColor(COLOR_HORCA);
        g2.drawLine(40, 290, 180, 290);   // base
        g2.drawLine(70, 290, 70, 40);     // poste
        g2.drawLine(70, 40, 170, 40);     // travesaño
        g2.drawLine(170, 40, 170, 70);    // cuerda
    }

    /**
     * Dibuja una parte del muñeco según su número.
     *
     * @param avance 0.0 = todavía no se ve, 1.0 = parte completa
     */
    private void dibujarParte(Graphics2D g2, int parte, double avance) {
        switch (parte) {
            case 1:
                dibujarCabeza(g2, avance);
                break;
            case 2:
                // TODO (Alex / Leandro): cuerpo.
                // dibujarLineaAnimada(g2, 170, 110, 170, 190, avance);
                break;
            case 3:
                // TODO (Alex / Leandro): brazo izquierdo.
                break;
            case 4:
                // TODO (Alex / Leandro): brazo derecho.
                break;
            case 5:
                // TODO (Alex / Leandro): pierna izquierda.
                break;
            case 6:
                // TODO (Alex / Leandro): pierna derecha.
                break;
        }
    }

    /** La cabeza se dibuja como un arco que se va cerrando hasta ser un círculo. */
    private void dibujarCabeza(Graphics2D g2, double avance) {
        int grados = (int) (360 * avance);
        g2.drawArc(150, 70, 40, 40, 90, -grados);
    }

    /**
     * Dibuja una línea que crece desde (x1, y1) hacia (x2, y2).
     * Con avance = 0.5 se ve la mitad de la línea, con 1.0 se ve entera.
     */
    private void dibujarLineaAnimada(Graphics2D g2, int x1, int y1, int x2, int y2, double avance) {
        int xFinal = (int) (x1 + (x2 - x1) * avance);
        int yFinal = (int) (y1 + (y2 - y1) * avance);
        g2.drawLine(x1, y1, xFinal, yFinal);
    }
}
