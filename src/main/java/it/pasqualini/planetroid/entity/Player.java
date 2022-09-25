package it.pasqualini.planetroid.entity;

import static it.pasqualini.util.Util.asInt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import it.pasqualini.planetroid.engine.GamePanel;
import it.pasqualini.planetroid.engine.KeyHandler;

public class Player extends Entity {
    KeyHandler keyHandlerMove;

    public boolean collision = false;

    GamePanel gamePanel;

    boolean thrusting = false;
    double thrustX = 0;
    double thrustY = 0;


    int THRUST = 3;
    double FRICTION = 0.5;
    boolean fs;

    public double angle = Math.PI;

    public float angularSpeed = 0;

    public ArrayList<BufferedImage> getParticlesImages() {
        return particlesImages;
    }

    ArrayList<BufferedImage> particlesImages = new ArrayList<>();
    public BufferedImage buffImage;
    public BufferedImage moon;


    private boolean invincible = false;

    public int getExplodingRemainingTime() {
        return explodingRemainingTime;
    }

    public void setExplodingRemainingTime(int explodingRemainingTime) {
        this.explodingRemainingTime = explodingRemainingTime;
    }

    private int explodingRemainingTime = 0;

    public int getInvincibleRemainingTime() {
        return invincibleRemainingTime;
    }

    public void setInvincibleRemainingTime(int invincibleRemainingTime) {
        this.invincibleRemainingTime = invincibleRemainingTime;
    }

    private int invincibleRemainingTime = 0;


    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;

    }

    private boolean exploding = false;


    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public Player(GamePanel panel, KeyHandler keyHandlerMove) {
        this.setRadius(20);
        this.keyHandlerMove = keyHandlerMove;
        this.gamePanel = panel;
        JFrame f = panel.frame;
        try {
            buffImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/playerShip3_red_centered_mod.png")));
            buffImage = resize(buffImage, 2 * getRadius());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initParticlesImages();
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


    public static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                //img.setRGB(x, y, col);
            }
        }
        return img;
    }

    public void updateGame() {


    }


    protected void clear(Graphics2D g) {
        final int w = this.gamePanel.getWidth();
        final int h = this.gamePanel.getHeight();


        g.fillRect(-w / 2, -h / 2, w, h);
    }

    protected void transform(Graphics2D g) {
        final int w = this.gamePanel.getWidth();
        final int h = this.gamePanel.getHeight();

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

    @Override
    public void draw(Graphics2D graphics) {
        if(exploding) return;
        graphics.setColor(Color.black);
        JFrame f = gamePanel.frame;
        //transform(graphics);

        // reset the view


        final double sin = Math.abs(Math.sin(angle));
        final double cos = Math.abs(Math.cos(angle));
        final int w = (int) Math.floor(buffImage.getWidth() * cos + buffImage.getHeight() * sin);
        final int h = (int) Math.floor(buffImage.getHeight() * cos + buffImage.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, buffImage.getType());
        final AffineTransform at = new AffineTransform();
        //at.translate(w / 2, h / 2);
        at.rotate(angle, 0, 0);
        at.translate(-buffImage.getWidth() / 2, -buffImage.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(buffImage, rotatedImage);
        graphics.drawImage(buffImage, rotateOp, asInt(getX()), asInt(getY()));

        if (invincibleRemainingTime > 0) {
            graphics.setColor(Color.green);
            graphics.drawOval(asInt(getX()) - getRadius(), asInt(getY()) - getRadius(), 2 * getRadius(), buffImage.getWidth());

        }
        if (explodingRemainingTime > 0) {
            graphics.setColor(Color.red);
            graphics.drawOval(asInt(getX()) - getRadius(), asInt(getY()) - getRadius(), 2 * getRadius(), buffImage.getWidth());

        }
    }

    private void initParticlesImages() {
        int rows = 8;
        int cols = 8;
        int s = buffImage.getWidth() / rows;
        int xx, yy;
        buffImage.getSubimage(1, 2, 3, 4);
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                particlesImages.add(buffImage.getSubimage(x * s, y * s, s, s));
            }
        }
    }


    public boolean collideWithAsteroids(ArrayList<Asteroid> asteroids) {
        return asteroids.stream().anyMatch(new Predicate<Asteroid>() {
            @Override
            public boolean test(Asteroid asteroid) {
                //System.out.println(" a(x,y) " +  asteroid.x + "  " + asteroid.y +  "  " + asteroid.radius + " -- " + x + "  " + y  + "  " + radius );
                return Math.sqrt(Math.pow(getX() - asteroid.getX(), 2) + Math.pow(getY() - asteroid.getY(), 2)) < (asteroid.getRadius() + getRadius());
            }
        });
    }
}
