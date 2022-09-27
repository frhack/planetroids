package it.pasqualini.planetroid.engine;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel {


    public GameGraphic gameGraphics;
    public KeyHandler keyHandler = new KeyHandler();
    public JFrame frame = null;


    Canvas canvas;

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }

    public GamePanel(Canvas canvas, JFrame frame) {
        this.canvas = canvas;
        int width = (int) (frame.getToolkit().getScreenSize().width * 0.8);
        int height = (int) (frame.getToolkit().getScreenSize().height * 0.8);

        Dimension size = new Dimension(width, height);

        canvas.setBackground(Color.black);

        this.frame = frame;
    }


    public void enterFullScreen() {
        frame.setVisible(false);
        frame.dispose();
        frame.setUndecorated(true);
        frame.setBounds(0, 0, frame.getToolkit().getScreenSize().width, frame.getToolkit().getScreenSize().height);
        frame.setVisible(true);
    }

    public void exitFullScreen() {
        int width = (int) (frame.getToolkit().getScreenSize().width * 0.8);
        int height = (int) (frame.getToolkit().getScreenSize().height * 0.8);
        frame.setVisible(false);
        frame.dispose();
        frame.setUndecorated(false);
        frame.setBounds(0, 0, width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


}
