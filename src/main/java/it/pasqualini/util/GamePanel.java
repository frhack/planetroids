package it.pasqualini.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.pasqualini.planetroid.engine.GameGraphic;
import it.pasqualini.planetroid.engine.KeyHandler;

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


}
