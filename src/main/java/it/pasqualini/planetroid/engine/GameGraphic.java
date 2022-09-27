package it.pasqualini.planetroid.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.text.DecimalFormat;
import java.util.List;

import it.pasqualini.planetroid.entity.Asteroid;
import it.pasqualini.planetroid.entity.Laser;
import it.pasqualini.planetroid.entity.Particle;

import static it.pasqualini.util.Util.println;

public class GameGraphic {


    DecimalFormat df = new DecimalFormat("0.0000");
    public GamePanel gamePanel;
    GameScene gameScene;
    public Boolean fullScreen = true;




    public GameGraphic(GamePanel panel, GameScene gameScene) {

        this.gamePanel = panel;
        this.gameScene = gameScene;
        gamePanel.gameGraphics = this;
    }




    public void update(Graphics2D graphics2D) {
        if(gameScene.changingFullScreen){
            gameScene.setFullscreen(!gameScene.isFullscreen());
            if (gameScene.isFullscreen()) {
                gamePanel.enterFullScreen();

            } else {
                gamePanel.exitFullScreen();
            }
            gameScene.changingFullScreen = false;
        }


        //gamePanel.repaint();

try{
    //Graphics2D graphics2D = (Graphics2D) gamePanel.getBufferStrategy().getDrawGraphics();
    this.draw(graphics2D);

}catch (Exception e){}
    }

    // sposta l'origine al centro del panel
    protected void transform(Graphics2D g) {
        final int w = gamePanel.getWidth();
        final int h = gamePanel.getHeight();

        AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
        AffineTransform move = AffineTransform.getTranslateInstance(w / 2, -h / 2);
        g.transform(yFlip);
        g.transform(move);
    }


    protected void clear(Graphics2D g) {
        final int w = gamePanel.canvas.getWidth();
        final int h = gamePanel.canvas.getHeight();

        // lets draw over everything with a white background
        g.setColor(Color.BLACK);
        g.fillRect(-w / 2, -h / 2,2* w,2* h);
    }
    public void draw(Graphics2D graphics2D) {
clear(graphics2D);

        drawInfo(graphics2D);
        List<Laser> lasers = gameScene.getLasers();
        List<Particle> particles = gameScene.getParticles();
        transform(graphics2D);

        for (Asteroid a : gameScene.getAsteroids()) {
            a.draw(graphics2D);
        }
        for (int i = 0; i < lasers.size(); i++) {
            lasers.get(i).draw(graphics2D);
        }
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).draw(graphics2D);
        }
        gameScene.getPlayer().draw(graphics2D);

        BufferStrategy strategy = this.gamePanel.frame.getBufferStrategy();
        if (!strategy.contentsLost()) {
            strategy.show();
        }

        Toolkit.getDefaultToolkit().sync();


    }


    public void drawInfo(Graphics2D graphics2D) {
        Font font = new Font("Verdana", Font.BOLD, 14);
        graphics2D.setRenderingHint


                (RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.green);


          int rows = 7;
        int vspace = 30;
        int h = gamePanel.getHeight();
        int r = 0;
        int ystart = gamePanel.getHeight() - rows * vspace;
        int x = gamePanel.getWidth() - 300;
        graphics2D.drawString("mass destroyed: " + df.format(gameScene.getTotVolumeDestroyed()), x, ystart - r-- * vspace);
        graphics2D.drawString("missions completed: " + gameScene.getLevel(), x, ystart - r-- * vspace);
        if (gameScene.getLives() == 0) {
            graphics2D.drawString("G A M E O V E R", x, ystart - r-- * vspace);
        } else if (!gameScene.isGaming()) {
            graphics2D.drawString("PRESS ENTER TO START " , x, ystart - r-- * vspace);
        } else {
            graphics2D.drawString("lives remaining: " + gameScene.getLives(), x, ystart - r-- * vspace);
        }
        graphics2D.drawString("X speed: " + df.format( gameScene.getSpeedX()), x, ystart - r-- * vspace);
        graphics2D.drawString("Y speed: " + df.format(gameScene.getSpeedY()), x, ystart - r-- * vspace);
        graphics2D.drawString("debris: " + gameScene.getParticles().size(), x, ystart - r-- * vspace);

    }


}
