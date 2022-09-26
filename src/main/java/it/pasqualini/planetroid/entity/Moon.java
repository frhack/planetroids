package it.pasqualini.planetroid.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import it.pasqualini.planetroid.engine.GamePanel;
import it.pasqualini.planetroid.engine.KeyHandler;



public class Moon extends Entity {
    KeyHandler keyHandlerMove;


    GamePanel panel;


    public int radius = 100;

    int THRUST = 3;
    double FRICTION = 0.5;


    public BufferedImage moon;

    public Moon(GamePanel panel) {
        this.keyHandlerMove = keyHandlerMove;
        this.panel = panel;
        JFrame f = panel.frame;
        try {
            moon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/asteroid.png")));
            moon = resize(moon, 2*radius);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


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



    protected void clear(Graphics2D g) {
        final int w = this.panel.getWidth();
        final int h = this.panel.getHeight();

        // lets draw over everything with a white background
        //g.setColor(Color.WHITE);
        g.fillRect(-w / 2, -h / 2, w, h);
    }

    protected void transform(Graphics2D g) {
        final int w = this.panel.getWidth();
        final int h = this.panel.getHeight();

        // before we render everything im going to flip the y axis and move the
        // origin to the center (instead of it being in the top left corner)
        AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
        AffineTransform move = AffineTransform.getTranslateInstance(w / 2, -h / 2);
        g.transform(yFlip);
        g.transform(move);
    }


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
        JFrame f = panel.frame;
transform(graphics);
        graphics.setColor(Color.black);
        // reset the view
        this.clear(graphics);
        graphics.drawImage(moon, null, (int) getX(), (int) getY());
//        final double rads = Math.toRadians(angle);
//        final double sin = Math.abs(Math.sin(rads));
//        final double cos = Math.abs(Math.cos(rads));

        graphics.setColor(Color.red);
        //graphics.drawOval((int) x - moon.getWidth() / 2, (int) y - moon.getWidth() / 2, moon.getWidth(), moon.getWidth());
        graphics.setColor(Color.black);
        drawBounding(graphics);
    }


    public void drawBounding(Graphics2D graphics) {

    }

}



