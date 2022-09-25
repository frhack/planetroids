package it.pasqualini.planetroid.engine;

import it.pasqualini.util.GamePanel;

public class GameLoop implements Runnable {

    public GamePanel panel;
    public GameIntelligence gameIntelligence;
    public GamePhysic gamePhysic;
    public GameScene gameScene;
    public GameGraphic gameGraphycs;
    public GameMusic gameMusic;


    public GameLoop(GameIntelligence gameIntelligence, GamePhysic gamePhysic, GameScene gameScene, GameGraphic gameGraphycs, GameMusic gameMusic) {
        this.gameIntelligence = gameIntelligence;
        this.gamePhysic = gamePhysic;
        this.gameScene = gameScene;
        this.gameGraphycs = gameGraphycs;
        this.gameMusic = gameMusic;
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
            update();

            if (remainingTime < 0) remainingTime = 0;
            try {
                Thread.sleep((long) remainingTime);
                nextDrawTime += DRAW_INTERVAL;
            } catch (Exception e) {
            }

        }
    }

    public void update() {
        gameIntelligence.processInput();
        gameIntelligence.update();
        gamePhysic.update();
        gameGraphycs.update();
        gameMusic.update();
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }


}
