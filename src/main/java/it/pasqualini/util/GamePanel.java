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

	
    public GameGraphic gameGraphics;
    public KeyHandler keyHandler = new KeyHandler();
    public JFrame frame = null;

    public GamePanel(JFrame frame) {
        int width = (int) (frame.getToolkit().getScreenSize().width * 0.8);
        int height = (int) (frame.getToolkit().getScreenSize().height * 0.8);
        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);
        setOpaque(true);
        setFocusable(true);
        addKeyListener(keyHandler);
        setBackground(Color.black);
        this.frame = frame;
        //thread = new Thread(this);
        //moons.add(new Moon(this));
    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        if (gameGraphics != null)
            gameGraphics.draw(graphics2D);
        //this.clear(graphics);
        //moons.get(0).draw(graphics2D);
        //player.draw(graphics2D);

        graphics2D.dispose();
    }

    

    public void enterFullScreen() {
        frame.dispose();
        frame.setUndecorated(true);
        frame.setBounds(0, 0, frame.getToolkit().getScreenSize().width, frame.getToolkit().getScreenSize().height);
        frame.setVisible(true);
    }

    public void exitFullScreen() {
        int width = (int) (frame.getToolkit().getScreenSize().width * 0.8);
        int height = (int) (frame.getToolkit().getScreenSize().height * 0.8);
        frame.dispose();
        frame.setUndecorated(false);
        frame.setBounds(0, 0, width, height);
        frame.setVisible(true);
    }


}
