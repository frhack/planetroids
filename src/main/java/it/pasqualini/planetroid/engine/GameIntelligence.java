package it.pasqualini.planetroid.engine;

import static it.pasqualini.util.Util.asInt;
import static it.pasqualini.util.Util.println;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import it.pasqualini.planetroid.entity.Asteroid;
import it.pasqualini.planetroid.entity.Entity;
import it.pasqualini.planetroid.entity.Laser;
import it.pasqualini.planetroid.entity.Particle;
import it.pasqualini.planetroid.entity.Player;
import it.pasqualini.util.EventListener;
import it.pasqualini.util.Normal;
import it.pasqualini.util.Vector2;

public class GameIntelligence {

    final Double ANGULAR_SPEED = 3d * Math.PI / 180; // velocita' di rotazione della navicella

    final Normal ASTEROID_SIZE = new Normal(100, 40);
    final Double ASTEROID_SPEED = 0.6d; // nmodulo velocita' asteoid
    final Double PLAYER_PARTICLE_SPEEDS = 5d; // nmodulo velocita' asteoid


    final double ASTEROID_MIN_RADIUS = 10;
    final double MIN_FRAGMENT_RADIUS = 60;
    final int LASER_RADIUS = 10;

    final int LIVES = 3;
    final int THRUST = 3;
    final double FRICTION = 0.5;

    final Double LASER_SPEED = 10d;
    private final int EXPLODING_TIME = 2; // seconds
    private final int INVINCIBLE_TIME = 3; // seconds


    GameScene gameScene;


    boolean fullScreen = false;
    GamePanel gamePanel;
    private KeyHandler keyHandler;

    //private int[] NUMBER_ASTEROIDS = new int[]{5, 7, 10, 15, 20, 25, 30, 50, 51,52,53,54,55};
    private int[] NUMBER_ASTEROIDS = new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,16,17,18,19,20};

    public GameIntelligence(GamePanel gamePanel) {


        this.gamePanel = gamePanel;
        gameScene = new GameScene(gamePanel);

        initScene();


        keyHandler = gamePanel.keyHandler;
        keyHandler.keyReleasedListenerAdapter.addEventListener(new EventListener<KeyEvent>() {
            @Override
            public void consume(KeyEvent item) {
                handleKeyEvent(item);
            }
        });
    }


    private void handleKeyEvent(KeyEvent item) {
        if (item.getKeyCode() == KeyEvent.VK_F11) {
            gameScene.setFullscreen(!gameScene.isFullscreen());
            if (gameScene.isFullscreen()) {
                gamePanel.enterFullScreen();

            } else {
                gamePanel.exitFullScreen();
            }
        } else if (item.getKeyCode() == KeyEvent.VK_B || item.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (item.getKeyCode() == KeyEvent.VK_ENTER  && ! gameScene.isGaming()) {
            initScene();
            start();
            //incrementLevel();
        } else if (item.getKeyCode() == KeyEvent.VK_P) {
            decremenentLevel();
        } else if (item.getKeyCode() == KeyEvent.VK_N) {
            incrementLevel();
        } else if (item.getKeyCode() == KeyEvent.VK_C) {
            gameScene.setCheating(!gameScene.isCheating());
            if (gameScene.isCheating()) {
                gameScene.getPlayer().setInvincibleRemainingTime(Integer.MAX_VALUE);
                gameScene.getPlayer().setInvincible(true);
            } else {
                gameScene.getPlayer().setInvincible(false);
                gameScene.getPlayer().setInvincibleRemainingTime(0);
            }
        }
    }


    private void start() {
        gameScene.setGaming(true);
        Player player = getPlayer();
        player.setInvincibleRemainingTime(INVINCIBLE_TIME * 60);
        player.setExploding(false);
        player.setInvincible(true);
        player.setX(0);
        player.setY(0);
        gameScene.setSpeedX(0);
        gameScene.setSpeedY(0);
    }


    private Double getAsteroidMinRadius() {
        return ASTEROID_MIN_RADIUS * Math.pow((1 - 5d / 100d * gameScene.getLevel()), gameScene.getLevel());
    }

    private Double getAsteroidMinFragmentRadius() {
        return MIN_FRAGMENT_RADIUS * Math.pow((1 - 2d / 100d * gameScene.getLevel()), gameScene.getLevel());
    }

    private Double getAsteroidSpeed() {
        return ASTEROID_SPEED * Math.pow((1 + 3d / 100d * gameScene.getLevel()), gameScene.getLevel());
    }


    public void initScene() {
        Player newPlayer = new Player(gamePanel, new KeyHandler());
        gameScene.setPlayer(newPlayer); //FIXME
        gameScene.setLives(LIVES);
        gameScene.setGaming(false);
        
        gameScene.getAsteroids().clear();
       

        int n = NUMBER_ASTEROIDS[gameScene.getLevel()];
     
        gameScene.getAsteroids().addAll(placeAsteroids(n, newPlayer));

    }


    private ArrayList<Asteroid> placeAsteroids(int n, Player player) {
        int i = 0;
        ArrayList<Asteroid> ret = new ArrayList<>();
        while (true) {

            int x = (int) (Math.random() * gamePanel.getWidth() - gamePanel.getWidth() / 2);
            int y = (int) (Math.random() * gamePanel.getHeight() - gamePanel.getHeight() / 2);
            Vector2 position = new Vector2((double) x, (double) y);
            int radius = (int) ASTEROID_SIZE.sample();
            if (radius < getAsteroidMinRadius()) continue;
            if (radius < player.getRadius()) continue;
            if (position.distance(player.getPosition()) < 2 * radius) continue;
            Asteroid asteroid = buildAsteroid(x, y, radius);

            ret.add(asteroid);

            i++;
            if (i == n) break;
        }
        return ret;
    }

    public void incrementLevel() {
        gameScene.setLevel(gameScene.getLevel() + 1);
        enterLevel();
    }

    private void enterLevel() {
        int n = NUMBER_ASTEROIDS[gameScene.getLevel()];
        gameScene.getAsteroids().clear();
        new Thread(new Runnable() { // FIXME gli update grafici possono sovrapporsi con la rimozione/aggiunta di asteroidi in background
            @Override
            public void run() {
                gameScene.getAsteroids().addAll(placeAsteroids(n, gameScene.getPlayer()));
            }
        }).start();
        start();
    }

    public void decremenentLevel() {
        gameScene.setLevel(gameScene.getLevel() - 1);
        if(gameScene.getLevel() <0) gameScene.setLevel(0);
        enterLevel();
    }

    Asteroid buildAsteroid(int x, int y, int radius) {
        Double direction = 2 * Math.PI * Math.random();
        Vector2 speed = Vector2.fromDirectionAndMagnitude(direction, getAsteroidSpeed());
        Asteroid asteroid = new Asteroid(gamePanel, x, y, speed, radius);
        return asteroid;
    }


    ArrayList<Asteroid> buildAsteroidFragments(Asteroid asteroid) {
        int x = asInt(asteroid.getX());
        int y = asInt(asteroid.getY());
        Double random = 0.5d;
        double v1, v2;
        v1 = asteroid.getVolume() * random;
        v2 = asteroid.getVolume() * (1 - random);

        double direction = 2 * Math.PI * Math.random();
        Vector2 speed1 = Vector2.fromDirectionAndMagnitude(direction, getAsteroidSpeed());
        Vector2 speed2 = Vector2.fromDirectionAndMagnitude(direction + Math.PI, getAsteroidSpeed());
        Asteroid a1 = new Asteroid(gamePanel, x, y, speed1, asInt(getRadiusFromVolume(v1)));
        Asteroid a2 = new Asteroid(gamePanel, x, y, speed2, asInt(getRadiusFromVolume(v2)));
        ArrayList<Asteroid> ret = new ArrayList<>();
        ret.add(a1);
        ret.add(a2);
        return ret;
    }

    static Double getRadiusFromVolume(Double v) {
        return Math.cbrt(3d / 4d * v / Math.PI);
    }

    Laser buildLaser() {

        Double direction = getPlayer().angle;
        int x = (int) (asInt(gameScene.getPlayer().getX()) + getPlayer().getRadius() * Math.cos(direction));
        int y = (int) (asInt(gameScene.getPlayer().getY()) + getPlayer().getRadius() * Math.sin(direction));
        Vector2 pos = Vector2.fromDirectionAndMagnitude(direction, (double) getPlayer().getRadius());
        double playerSpeed = Math.sqrt(Math.pow(gameScene.getSpeedX(), 2) + Math.pow(gameScene.getSpeedY(), 2));
        Vector2 speed = Vector2.fromDirectionAndMagnitude(direction, LASER_SPEED + playerSpeed);
        return new Laser(gamePanel, x, y, speed, LASER_RADIUS);
    }


    public Player getPlayer() {
        return gameScene.getPlayer();
    }

    public List<Laser> getLasers() {
        return gameScene.getLasers();
    }

    public ArrayList<Asteroid> getAsteroids() {
        return gameScene.getAsteroids();
    }


    public ArrayList<Particle> getParticles() {
        return gameScene.getParticles();
    }


    public void processInput() {
        if (!gameScene.isGaming()) return;
        gameScene.setThrusting(keyHandler.isUp());
        gameScene.setShooting(keyHandler.isShooting());
    }

    public void update() {

        handleCollision();

        handleExplosion();
        handleLasers();
        cleanParticles();


        handleAsteroidsHits();

        handleRotate();


        handleShooting();
        handleThrusting();
        handleBorders();

    }

    private void handleExplosion() {
        Player player = getPlayer();

        if (gameScene.getPlayer().getExplodingRemainingTime() > 0) {
            gameScene.getPlayer().setExplodingRemainingTime(gameScene.getPlayer().getExplodingRemainingTime() - 1); // FIXME
            if (gameScene.getPlayer().getExplodingRemainingTime() == 0) {

                if (gameScene.getLives() > 0) {
                    //gameScene.setLives(gameScene.getLives()-1);
                    player.setInvincibleRemainingTime(INVINCIBLE_TIME * 60);
                    player.setExploding(false);
                    player.setInvincible(true);
                    player.setX(0);
                    player.setY(0);
                    gameScene.setSpeedX(0);
                    gameScene.setSpeedY(0);
                }else {
                	gameScene.setGaming(false);
                }
            }
        }
        if (gameScene.getPlayer().getInvincibleRemainingTime() > 0) {
            gameScene.getPlayer().setInvincibleRemainingTime(gameScene.getPlayer().getInvincibleRemainingTime() - 1); // FIXME
        } else {
            player.setInvincible(gameScene.isCheating());
        }
    }

    private void handleCollision() {
        if (gameScene.getPlayer().collision && !gameScene.isExploding()) {
            gameScene.getSoundsQueue().add(GameScene.Sounds.PLAYER_EXPLOSION);

            gameScene.setLives(gameScene.getLives() - 1);
            if (gameScene.getLives() == 0) {
                //
            }
            gameScene.getPlayer().setExploding(true);
            addParticlesPlayer();

            gameScene.getPlayer().setExplodingRemainingTime(EXPLODING_TIME * 60); // FIXME
        }
    }

    private void addParticlesPlayer() {
        ArrayList<BufferedImage> particlesImages = getPlayer().getParticlesImages();
        ArrayList<Particle> particles = new ArrayList<>();
        for (BufferedImage image : particlesImages) {
            Double direction = 2 * Math.PI * Math.random();
            Vector2 speed = Vector2.fromDirectionAndMagnitude(direction, PLAYER_PARTICLE_SPEEDS * Math.random());

            Particle p = new Particle(gamePanel, (int) getPlayer().getX(), (int) getPlayer().getY(), speed);
            p.image = image;
            particles.add(p);
            gameScene.getParticles().addAll(particles);
        }
    }

    private void addParticlesAsteroid(Asteroid asteroid) {
        ArrayList<BufferedImage> particlesImages = asteroid.getParticlesImages();

        for (BufferedImage image : particlesImages) {
            Double direction = 2 * Math.PI * Math.random();
            Vector2 speed = Vector2.fromDirectionAndMagnitude(direction, ASTEROID_SPEED * 10 * Math.random()); // FIXME

            Particle p = new Particle(gamePanel, asInt(asteroid.hitPosition.getX()), asInt(asteroid.hitPosition.getY()), speed);
            p.image = image;
            gameScene.getParticles().add(p);
        }

    }

    private void handleShooting() {
        if (gameScene.isShooting() && !gameScene.getPlayer().isExploding()) {
            gameScene.addLasers(buildLaser());
            gameScene.getSoundsQueue().add(GameScene.Sounds.LASER_FIRE);
            keyHandler.setShooting(false);
            gameScene.setShooting(false);
        }
    }

    private void handleThrusting() {
        if (gameScene.isThrusting() && !gameScene.getPlayer().isExploding()) {
            gameScene.setSpeedX(gameScene.getSpeedX() + THRUST * Math.cos(gameScene.getPlayer().angle) / 60);
            gameScene.setSpeedY(gameScene.getSpeedY() + THRUST * Math.sin(gameScene.getPlayer().angle) / 60);
        } else {
            gameScene.setSpeedX(gameScene.getSpeedX() - FRICTION * gameScene.getSpeedX() / 60);
            gameScene.setSpeedY(gameScene.getSpeedY() - FRICTION * gameScene.getSpeedY() / 60);
        }
    }

    private void handleLasers() {
        List<Laser> lasers = getLasers();
        for (int i = lasers.size() - 1; i >= 0; i--) {
            if (lasers.get(i).hit || isOutOfPanel(lasers.get(i))) {
                lasers.remove(i);
            }
        }
    }

    private void cleanParticles() {
        ArrayList<Particle> particles = getParticles();
        for (int i = particles.size() - 1; i >= 0; i--) {
            if (isOutOfPanel(particles.get(i))) {
                particles.remove(i);
            }
        }
    }


    private void handleAsteroidsHits() {
        Asteroid asteroid;
        ArrayList<Asteroid> asteroids = getAsteroids();
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            if (asteroids.get(i).getLaserHit() > 0) {
                asteroid = asteroids.get(i);
                if (asteroid.getRadius() > getAsteroidMinFragmentRadius()) {
                    for (Asteroid ast : buildAsteroidFragments(asteroids.get(i))) {
                        //if (ast.getRadius() > getAsteroidMinFragmentRadius() && ast.getRadius() > gameScene.player.getRadius())
                        asteroids.add(ast);
                        gameScene.setTotVolumeDestroyed((float) (gameScene.getTotVolumeDestroyed() - ast.getVolume() / 1000));
                    }
                    gameScene.getSoundsQueue().add(GameScene.Sounds.ASTEROID_HIT);
                }else{
                    gameScene.getSoundsQueue().add(GameScene.Sounds.ASTEROID_EXPLOSION);
                }
                gameScene.setTotVolumeDestroyed((float) (gameScene.getTotVolumeDestroyed() + asteroids.get(i).getVolume() / 1000));
                if(gameScene.getTotVolumeDestroyed() <0) gameScene.setTotVolumeDestroyed(0f);
                asteroids.remove(i);
                addParticlesAsteroid(asteroid);

                if (asteroids.size() == 0) {
                    incrementLevel();
                    break;
                }
                break;
            }
        }
    }


    private void handleRotate() {
        if (keyHandler.isRight()) {
            getPlayer().angularSpeed = (float) -ANGULAR_SPEED;
        } else if (keyHandler.isLeft()) {
            getPlayer().angularSpeed = (float) +ANGULAR_SPEED;
        } else {
            getPlayer().angularSpeed = 0f;
        }
    }

    private void handleBorders() {
        handleBorders(getPlayer());
        for (Asteroid a : getAsteroids()) {
            handleBorders(a);
        }
    }

    void handleBorders(Entity entity) {
        if (entity.getX() - entity.getRadius() > gamePanel.getWidth() / 2d) {
            entity.setX((entity.getX() - gamePanel.getWidth()) - 2 * entity.getRadius());
        }
        if (entity.getY() - entity.getRadius() > gamePanel.getHeight() / 2d) {
            entity.setY(entity.getY() - gamePanel.getHeight() - 2 * entity.getRadius());
        }
        if (entity.getX() + entity.getRadius() < -gamePanel.getWidth() / 2d) {
            entity.setX(entity.getX() + gamePanel.getWidth() + 2 * entity.getRadius());
        }
        if (entity.getY() + entity.getRadius() < -gamePanel.getHeight() / 2d) {
            entity.setY(entity.getY() + gamePanel.getHeight() + 2 * entity.getRadius());
        }
    }


    Boolean isOutOfPanel(Entity entity) {
        if (entity.getX() - entity.getRadius() > gamePanel.getWidth() / 2d) {
            return true;
        }
        if (entity.getY() - entity.getRadius() > gamePanel.getHeight() / 2d) {
            return true;
        }
        if (entity.getX() + entity.getRadius() < -gamePanel.getWidth() / 2d) {
            return true;
        }
        if (entity.getY() + entity.getRadius() < -gamePanel.getHeight() / 2d) {
            return true;
        }
        return false;
    }


}
