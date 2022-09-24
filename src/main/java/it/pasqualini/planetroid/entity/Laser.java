package it.pasqualini.planetroid.entity;

import it.pasqualini.planetroid.engine.GamePanel;
import it.pasqualini.planetroid.engine.KeyHandler;
import it.pasqualini.util.Vector2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static it.pasqualini.util.Util.asInt;


public class Laser extends Entity {
    KeyHandler keyHandlerMove;


    GamePanel gamePanel;


    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean hit = false;
    public final Vector2 speed;


    public BufferedImage image;
private Double angle;
//    public Laser(GamePanel panel, int x, int y, Vector2 speed, int LASER_RADIUS) {
//        this.keyHandlerMove = keyHandlerMove;
//        this.gamePanel = panel;
//
//        JFrame f = panel.f;
//        try {
//            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/laserGreen11.png")));
//            image = resize(image, 2 * getRadius());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Laser(GamePanel panel, int x, int y, Vector2 speed, int radius) {
        //radius=10;
        this.angle = speed.getAngle();//+ Math.PI;
        this.setRadius(radius);
        this.keyHandlerMove = keyHandlerMove;
        this.gamePanel = panel;
        this.setX(x);
        this.setY(y);
        this.speed = speed;
        JFrame f = panel.f;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/laserGreen11.png")));
            image = resize(image, 2 * radius);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public boolean collideWithShip(Player player) {
//        this.collide(player);
//        return Math.sqrt(Math.pow(getX() - player.getX(), 2) + Math.pow(getY() - player.getY(), 2)) < (player.getRadius() + getRadius());
//    }
//
//    public boolean collideWithAsteroids(List<Laser> asteroids) {
//        return asteroids.stream().anyMatch(new Predicate<Laser>() {
//            @Override
//            public boolean test(Laser asteroid) {
//                return Math.sqrt(Math.pow(getX() - asteroid.getX(), 2) + Math.pow(getY() - asteroid.getY(), 2)) < (asteroid.getRadius() + getRadius());
//            }
//        });
//    }


//    public double distanceWithShip(Player player) {
//        return Math.sqrt(Math.pow(getX() - player.getX(), 2) + Math.pow(getY() - player.getY(), 2));
//    }
//
//    public double distanceWithAsteroid(Laser asteroid) {
//        return Math.sqrt(Math.pow(getX() - asteroid.getX(), 2) + Math.pow(getY() - asteroid.getY(), 2));
//    }

    private BufferedImage resize(BufferedImage src, int targetSize) {
        if (targetSize <= 0) {
            return src; //this can't be resized
        }
        int targetWidth = targetSize;
        int targetHeight = targetSize;
        float ratio = ((float) src.getHeight() / (float) src.getWidth());
        if (ratio <= 1) { //square or landscape-oriented image
            targetHeight = (int) Math.ceil((float) targetWidth * ratio);
        } else { //portrait image
            targetWidth = Math.round((float) targetHeight / ratio);
        }
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
        g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return bi;
    }


    public void updateGame() {


    }


//    protected void clear(Graphics2D g) {
//        final int w = this.gamePanel.getWidth();
//        final int h = this.gamePanel.getHeight();
//
//        // lets draw over everything with a white background
//        //g.setColor(Color.WHITE);
//        g.fillRect(-w / 2, -h / 2, w, h);
//    }

//    protected void transform(Graphics2D g) {
//        final int w = this.gamePanel.getWidth();
//        final int h = this.gamePanel.getHeight();
//

//        AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
//        AffineTransform move = AffineTransform.getTranslateInstance(w / 2, -h / 2);
//        g.transform(yFlip);
//        g.transform(move);
//    }


    static void rotate(BufferedImage from, BufferedImage to, double rotate) {
        // rotate around the center
        AffineTransform trans
                = AffineTransform.getRotateInstance(rotate,
                from.getWidth() / 2, from.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(trans,
                AffineTransformOp.TYPE_BILINEAR);
        op.filter(from, to);
    }


    public void draw(Graphics2D graphics) {

        //transform(graphics);
        graphics.setColor(Color.black);
        // reset the view
        //this.clear(graphics);


        final AffineTransform at = new AffineTransform();
        at.rotate(angle + Math.PI /2, 0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        //rotateOp.filter(buffImage, rotatedImage);

        graphics.drawImage(image, rotateOp, asInt(getX()), asInt(getY()));


        graphics.setColor(Color.red);
        //graphics.drawOval((int) x -moon.getWidth() / 2, (int) y -moon.getHeight() / 2, moon.getWidth(), moon.getWidth());
        graphics.setColor(Color.black);

    }


}



