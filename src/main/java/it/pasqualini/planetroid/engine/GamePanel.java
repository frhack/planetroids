package it.pasqualini.planetroid.engine;

import it.pasqualini.planetroid.entity.Moon;
import it.pasqualini.planetroid.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    public int x = 0;
    public int y = 0;
    public int speed = 4;
    public JFrame f = null;
    public GameGraphic gameGraphycs;
    Thread thread;
    static long gameTime;

    //public KeyHandler keyHandler = new KeyHandler();
    public KeyHandler keyHandler = new KeyHandler();

    Player player = new Player(this, keyHandler);
    public int radius = 100;

    ArrayList<Moon> moons = new ArrayList<>();

    final double GAME_HERTZ = 60.0;
    //Calculate how many ns each frame should take for our target game hertz.
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    //If we are able to get as high as this FPS, don't render again.
    final double TARGET_FPS = 60;
    final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
    //At the very most we will update the game this many times before a new render.
    //If you're worried about visual hitches more than perfect timing, set this to 1.
    final int MAX_UPDATES_BEFORE_RENDER = 1;
    //We will need the last update time.
    double lastUpdateTime = System.nanoTime();
    //Store the last time we rendered.
    double lastRenderTime = System.nanoTime();

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
        thread = new Thread(this);
        moons.add(new Moon(this));
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

    public void Start() {
        thread.start();
    }


    @Override
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

            updateGame();
            repaint();
            if (remainingTime < 0) remainingTime = 0;
            try {
                Thread.sleep((long) remainingTime);
                nextDrawTime += DRAW_INTERVAL;
            } catch (Exception e) {
            }

        }

    }


    public void russn() {

        //This value would probably be stored elsewhere.

        float interpolation = 0;
        while (true) {
            double now = System.nanoTime();
            int updateCount = 0;

            //Do as many game updates as we need to, potentially playing catchup.
            while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                updateGame();
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            //If for some reason an update takes forever, we don't want to do an insane number of catchups.
            //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }

            //Render. To do so, we need to calculate interpolation for a smooth render.
            interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
            updateGame();
            this.repaint();
            lastRenderTime = now;

            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
            while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                //allow the threading system to play threads that are waiting to run.
                Thread.yield();

                //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
                //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
                //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
                //On my OS it does not unpuase the game if i take this away
                try {
                    //Thread.sleep(1);
                } catch (Exception e) {
                }

                now = System.nanoTime();
            }
        }
    }


    public void runx() {
        final int NANOSECONDS_PER_SEC = 10 ^ 9;
        final double FPS = 60;
        final double DRAW_INTERVAL = NANOSECONDS_PER_SEC / FPS;  // nanosecons per frame
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int drawCount = 0;
        long timer = 0;

        double nextDrawTime = System.nanoTime() + DRAW_INTERVAL;
        while (true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / DRAW_INTERVAL;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            //if (delta >= 1) {
            //updateGame(1f);
            repaint();
            delta--;
            drawCount++;
            //}
            if (timer >= NANOSECONDS_PER_SEC) {
                System.out.println("FPS" + drawCount);
                drawCount = 0;
                timer = 0;
            }

            try {
                Thread.sleep(1);
            } catch (Exception ignored) {
            }

/*        try {
            Thread.sleep((long) remainingTime);
            nextDrawTime += DRAW_INTERVAL
        } catch (Exception e) {
        }*/

        }

    }


    public void rund() {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFpsTime = 0;

        while (true) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);


//panel.repaint();
            lastFpsTime += updateLength;
            if (lastFpsTime >= 1000000000) {
                lastFpsTime = 0;
            }

            //panel.getRootPane().paintImmediately(0,0,100,100);
            // updateGame(delta);
            //updateGame(1f);

            if (delta < 1) {
                //panel.getRootPane().paintImmediately(0,0,1000,1000);
                //repaint();
                getRootPane().paintImmediately(0, 0, 300, 300);

            }
//panel.getRootPane().paintImmediately(0,0,200,200);
            //repaint(panel);

            try {
                gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;

                Thread.sleep(gameTime);
            } catch (Exception e) {
            }
        }
    }

    private void updateGame() {


        player.updateGame();
        moons.get(0).updateGame();

//        if(Math.sqrt( Math.pow(moons.get(0).x-player.x,2)+Math.pow(moons.get(0).y-player.y,2)) < player.radius+moons.get(0).radius ){
//            System.out.println("collision");
//        }


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
