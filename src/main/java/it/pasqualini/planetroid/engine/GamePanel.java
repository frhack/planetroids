package it.pasqualini.planetroid.engine;

import it.pasqualini.planetroid.entity.Moon;
import it.pasqualini.planetroid.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel{

	
    public GameGraphic gameGraphycs;
    public KeyHandler keyHandler = new KeyHandler();
    public JFrame f = null;

    public GamePanel(JFrame frame) {
        int width = (int) (frame.getToolkit().getScreenSize().width * 0.8);
        int height = (int) (frame.getToolkit().getScreenSize().height * 0.8);
        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);
        setOpaque(true);
        setFocusable(true);
        addKeyListener(keyHandler);
        setBackground(Color.black);
        f = frame;
        //thread = new Thread(this);
        //moons.add(new Moon(this));
    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        if (gameGraphycs != null)
            gameGraphycs.draw(graphics2D);
        //this.clear(graphics);
        //moons.get(0).draw(graphics2D);
        //player.draw(graphics2D);

        graphics2D.dispose();
    }

    

    public void enterFullScreen() {
        f.dispose();
        f.setUndecorated(true);
        f.setBounds(0, 0, f.getToolkit().getScreenSize().width, f.getToolkit().getScreenSize().height);
        f.setVisible(true);
    }

    public void exitFullScreen() {
        int width = (int) (f.getToolkit().getScreenSize().width * 0.8);
        int height = (int) (f.getToolkit().getScreenSize().height * 0.8);
        f.dispose();
        f.setUndecorated(false);
        f.setBounds(0, 0, width, height);
        f.setVisible(true);
    }


    public static class Normal {
        double average;

        public double getAverage() {
            return average;
        }

        public double getStandardDeviation() {
            return standardDeviation;
        }

        float standardDeviation;


        public Normal(float average, float standardDeviation) {
            this.average = average;
            this.standardDeviation = standardDeviation;
        }


        public double sample() {
            double r = new Random().nextGaussian();
            return r * standardDeviation + average;
        }

    }
    

}
