package gui;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PanelAhorcado extends JPanel {

    private static final Color COLOR_HORCA = new Color(120, 80, 40);
    private static final Color COLOR_MUNIECO = new Color(40, 40, 40);


    private int partesDibujadas;

    private double progreso;


    private Timer temporizador;

    public PanelAhorcado() {
        setPreferredSize(new Dimension(260, 320));
        setBackground(Color.WHITE);
        this.partesDibujadas = 0;
        this.progreso = 1.0;

        this.temporizador = new Timer(20, e -> {
            progreso = progreso + 0.05;
            if (progreso >= 1.0) {
                progreso = 1.0;
                temporizador.stop();
            }
            repaint();
        });
    }


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

            double avance = (parte == partesDibujadas) ? progreso : 1.0;
            dibujarParte(g2, parte, avance);
        }
    }

    private void dibujarHorca(Graphics2D g2) {
        g2.setColor(COLOR_HORCA);
        g2.drawLine(40, 290, 180, 290);   // base
        g2.drawLine(70, 290, 70, 40);     // poste
        g2.drawLine(70, 40, 170, 40);     // travesaño
        g2.drawLine(170, 40, 170, 70);    // cuerda
    }


    private void dibujarParte(Graphics2D g2, int parte, double avance) {
        switch (parte) {
            case 1:
                dibujarCabeza(g2, avance);
                break;
            case 2:
                dibujarLineaAnimada(g2, 170, 110, 170, 190, avance);
                break;
            case 3:
                dibujarBrazoIzquierdo(g2, 170, 120, 210, 140, avance);
                break;
            case 4:
                dibujarBrazoDerecho(g2,170,120,130, 140, avance);
                break;
            case 5:
                dibujarLineaAnimada(g2, 170, 190, 140, 235, avance);
                break;
            case 6:
                dibujarLineaAnimada(g2, 170, 190, 200, 235, avance);
                break;
        }
    }

    private void dibujarCabeza(Graphics2D g2, double avance) {
        int grados = (int) (360 * avance);
        g2.drawArc(150, 70, 40, 40, 90, -grados);
    }

    private void dibujarLineaAnimada(Graphics2D g2, int x1, int y1, int x2, int y2, double avance) {
        int xFinal = (int) (x1 + (x2 - x1) * avance);
        int yFinal = (int) (y1 + (y2 - y1) * avance);
        g2.drawLine(x1, y1, xFinal, yFinal);
    }

    private void dibujarBrazoIzquierdo(Graphics2D g2, int x1, int y1, int x2, int y2, double avance){
        int xFinal = (int) (x1 + (x2-x1) * avance);
        int yFinal = (int) (y1 + (y2-y1) * avance);
        g2.drawLine(x1, y1, xFinal, yFinal);
    }

    private void dibujarBrazoDerecho(Graphics2D g2, int x1, int y1, int x2, int y2, double avance){
        int xFinal = (int) (x1 + (x2-x1) * avance);
        int yFinal = (int) (y1 + (y2-y1) * avance);
        g2.drawLine(x1, y1, xFinal, yFinal);
    }
}
