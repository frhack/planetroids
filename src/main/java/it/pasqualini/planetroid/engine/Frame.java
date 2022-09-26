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

public  class Frame extends JFrame {
    /** The serial version id */
    private static final long serialVersionUID = 7659608187025022915L;

    /** The conversion factor from nano to base */
    public static final double NANO_TO_BASE = 1.0e9;

    /** The canvas to draw to */
    protected final Canvas canvas;

    /** The dynamics engine */


    // stop/pause

    /** True if the simulation is exited */
    private boolean stopped;

    /** The time stamp for the last iteration */
    private long last;

    /** Tracking for the step number when in manual stepping mode */
    private long stepNumber;

    // camera


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
        panel.keyHandler  = new KeyHandler();
        canvas.addKeyListener(panel.keyHandler);

        this.add(this.canvas);

        this.setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(false);
        // size everything
        this.canvas.requestFocus();

        this.pack();




    }

    private void start() {
        this.last = System.nanoTime();
        this.canvas.setIgnoreRepaint(true);
        this.canvas.createBufferStrategy(2);
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    gameLoop();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {}
                }
            }
        };
        thread.setDaemon(true);
        // start the game loop
        thread.start();
    }


    private void  updateLoop(Graphics2D g){

        gameIntelligence.processInput();
        gameIntelligence.update();
        gamePhysic.update();
        gameGraphycs.update(g);
        gameMusic.update();
    }

    private void gameLoop() {
        // get the graphics object to render to


        Graphics2D g = (Graphics2D)this.canvas.getBufferStrategy().getDrawGraphics();

        // by default, set (0, 0) to be the center of the screen with the positive x axis
        // pointing right and the positive y axis pointing up
        //this.transform(g);

        // reset the view
        this.clear(g);

        // get the current time
        long time = System.nanoTime();
        // get the elapsed time from the last iteration
        long diff = time - this.last;
        // set the last time
        this.last = time;
        // convert from nanoseconds to seconds
        double elapsedTime = (double)diff / NANO_TO_BASE;

updateLoop(g);

        // dispose of the graphics object
        g.dispose();

        // blit/flip the buffer
        BufferStrategy strategy = this.canvas.getBufferStrategy();
        if (!strategy.contentsLost()) {
            strategy.show();
        }

        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Performs any transformations to the graphics.
     * <p>
     * By default, this method puts the origin (0,0) in the center of the window
     * and points the positive y-axis pointing up.
     * @param g the graphics object to render to
     */
    protected void transform(Graphics2D g) {
        final int w = this.canvas.getWidth();
        final int h = this.canvas.getHeight();

        // before we render everything im going to flip the y axis and move the
        // origin to the center (instead of it being in the top left corner)
        AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
        AffineTransform move = AffineTransform.getTranslateInstance(w / 2, -h / 2);
        g.transform(yFlip);
        g.transform(move);
    }


    protected void clear(Graphics2D g) {
        final int w = this.canvas.getWidth();
        final int h = this.canvas.getHeight();

        // lets draw over everything with a white background
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
