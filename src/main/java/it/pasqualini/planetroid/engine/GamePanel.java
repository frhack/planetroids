package it.pasqualini.planetroid.engine;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel  {


    public GameGraphic gameGraphics;
    public KeyHandler keyHandler = new KeyHandler();
    public JFrame frame = null;
    boolean start = false;

    Canvas canvas;
    public  int getWidth(){
        return canvas.getWidth();
    }
    public  int getHeight(){
        return canvas.getHeight();
    }

    public GamePanel(Canvas canvas, JFrame frame) {
        this.canvas = canvas;
        int width = (int) (frame.getToolkit().getScreenSize().width * 0.8);
        int height = (int) (frame.getToolkit().getScreenSize().height * 0.8);

        Dimension size = new Dimension(width, height);
        //setPreferredSize(size);
        //setMinimumSize(size);
        //setMaximumSize(size);

        //setIgnoreRepaint(true);
        // enable double buffering (the JFrame has to be
        // visible before this can be done)
canvas.setBackground(Color.black);

        //setDoubleBuffered(true);
        //setOpaque(true);
        //setFocusable(true);
        //addKeyListener(keyHandler);
        //setBackground(Color.black);
        this.frame = frame;
        //thread = new Thread(this);
        //moons.add(new Moon(this));
    }


//    @Override
//
//    public void paint(Graphics graphics) {
//        //super.paintComponent(graphics);
//        if (!start) {
//            setIgnoreRepaint(true);
//
//            createBufferStrategy(2);
//            start = true;
//        }
//        Graphics2D graphics2D = (Graphics2D) getBufferStrategy().getDrawGraphics();
//        ;
//
//        if (gameGraphics != null)
//            gameGraphics.draw(graphics2D);
//        //this.clear(graphics);
//        //moons.get(0).draw(graphics2D);
//        //player.draw(graphics2D);
//
//        graphics2D.dispose();
//    }


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
