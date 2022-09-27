package it.pasqualini.planetroid.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.Iterator;

public class Frame extends JFrame {

    protected final Canvas canvas;




    public GamePanel panel;
    public GameIntelligence gameIntelligence;
    public GamePhysic gamePhysic;
    public GameScene gameScene;
    public GameGraphic gameGraphycs;
    public GameMusic gameMusic;

    public Frame(String name, double scale) {
        super(name);

        //setLayout(new BorderLayout());
        //setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // setup the JFrpanelame


        // add a window listener


        // create the size of the window
        //Dimension size = new Dimension(800, 600);

        int width = (int) (this.getToolkit().getScreenSize().width * 0.8);
        int height = (int) (this.getToolkit().getScreenSize().height * 0.8);

        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        // create a canvas to paint to
        this.canvas = new Canvas();
        this.canvas.setPreferredSize(size);
        this.canvas.setMinimumSize(size);
        this.canvas.setMaximumSize(size);
        canvas.setBackground(Color.black);


        this.panel = new GamePanel(canvas, this);
        panel.keyHandler = new KeyHandler();
        canvas.addKeyListener(panel.keyHandler);

        this.add(this.canvas);

        this.setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(false);
        // size everything
        this.requestFocus();
        canvas.setFocusable(true);
        this.canvas.requestFocus();

        this.pack();


    }

    private void start() {

        this.canvas.setIgnoreRepaint(true);
        this.canvas.createBufferStrategy(2);
        this.canvas.createBufferStrategy(2);
        Thread thread = new Thread() {


            public void run() {
                final int NANOSECONDS_PER_SEC = 1000000000;
                final double TARGET_FPS = 60;
                final double DRAW_INTERVAL = NANOSECONDS_PER_SEC / TARGET_FPS;

                long lastTime = System.nanoTime();
                long currentTime;
                int drawCount = 0;

                double nextDrawTime = System.nanoTime() + DRAW_INTERVAL;
                while (true) {
                    currentTime = System.nanoTime();

                    double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                    gameLoop();
                    ;

                    if (remainingTime < 0) remainingTime = 0;
                    try {
                        Thread.sleep((long) remainingTime);
                        nextDrawTime += DRAW_INTERVAL;
                    } catch (Exception e) {
                    }

                }
            }
//            public void run() {
//                while (true) {
//                    gameLoop();
//                    try {
//                        Thread.sleep(5);
//                    } catch (InterruptedException e) {
//                    }
//                }
//            }
        };
        thread.setDaemon(true);
        // start the game loop
        thread.start();
    }


    private void updateLoop(Graphics2D g) {
        gameIntelligence.processInput();
        gameIntelligence.update();
        gamePhysic.update();
        gameGraphycs.update(g);
        gameMusic.update();
    }

    private void gameLoop() {
        // get the graphics object to render to
        Graphics2D g = null;

        g = (Graphics2D) this.canvas.getBufferStrategy().getDrawGraphics();


        this.clear(g);



        updateLoop(g);

        // dispose of the graphics object
        g.dispose();

        // blit/flip the buffer
        BufferStrategy strategy = this.canvas.getBufferStrategy();

        if (!strategy.contentsLost()) {
            try{
                strategy.show();
            }catch(Exception e){}
        }

        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }


    protected void clear(Graphics2D g) {
        final int w = this.canvas.getWidth();
        final int h = this.canvas.getHeight();
        g.setColor(Color.BLACK);
        g.fillRect(-w / 2, -h / 2, w, h);
    }


    public void run() {
        // set the look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // show it
        this.setVisible(true);

        // start it
        this.start();
    }
}
