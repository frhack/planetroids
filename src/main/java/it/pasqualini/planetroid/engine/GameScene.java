package it.pasqualini.planetroid.engine;

import java.util.ArrayList;

import it.pasqualini.planetroid.audio.AudioClip;
import it.pasqualini.planetroid.entity.Asteroid;
import it.pasqualini.planetroid.entity.Laser;
import it.pasqualini.planetroid.entity.Moon;
import it.pasqualini.planetroid.entity.Particle;
import it.pasqualini.planetroid.entity.Player;
import it.pasqualini.util.GamePanel;

public class GameScene {


    private float totVolumeDestroyed = 0f;

    public boolean isCheating() {
        return cheating;
    }

    private boolean gaming = false;

    public void setCheating(boolean cheating) {
        this.cheating = cheating;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public boolean isThrusting() {
        return thrusting;
    }

    public void setThrusting(boolean thrusting) {
        this.thrusting = thrusting;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public GamePanel getPanel() {
        return panel;
    }

    public void setPanel(GamePanel panel) {
        this.panel = panel;
    }

    public ArrayList<AudioClip> getAsteroidsExplosions() {
        return asteroidsExplosions;
    }

    public void setAsteroidsExplosions(ArrayList<AudioClip> asteroidsExplosions) {
        this.asteroidsExplosions = asteroidsExplosions;
    }

    public ArrayList<Sounds> getSoundsQueue() {
        return soundsQueue;
    }

    public void setSoundsQueue(ArrayList<Sounds> soundsQueue) {
        this.soundsQueue = soundsQueue;
    }

    public int getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        if(soundVolume<0) return;
        this.soundVolume = soundVolume;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        if(musicVolume<0) return;
        this.musicVolume = musicVolume;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public float getTotVolumeDestroyed() {
        return totVolumeDestroyed;
    }

    public void setTotVolumeDestroyed(float totVolumeDestroyed) {
        this.totVolumeDestroyed = totVolumeDestroyed;
    }

    public boolean isGaming() {
        return gaming;
    }

    public void setGaming(boolean gaming) {
        this.gaming = gaming;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particles = particles;
    }

    public enum Sounds{
        ASTEROID_EXPLOSION,
        PLAYER_EXPLOSION,
        ASTEROID_HIT,
        LASER_FIRE,
    }

    private GamePanel panel;

    private ArrayList<Particle> particles = new ArrayList<>();
    public GameScene(GamePanel panel) {
        this.setPanel(panel);
        //player = new Player(panel, new KeyHandler()); //FIXME
        //moon = new Moon(panel); //FIXME
        asteroids = new ArrayList<>();
    }

    private boolean fullscreen = false;
    private boolean thrusting = false;

    private boolean shooting = false;
    private double speedX = 0;
    private double speedY = 0;




    private int level = 0;  // 0 = intro 1



    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    private ArrayList<Asteroid> asteroids;

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    private ArrayList<Laser> lasers = new ArrayList<>();
    private Player player = null;
    private Moon moon = null;
    private int soundVolume = 1;
    private int musicVolume = 8;
    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    private int lives = 0;

    private boolean cheating = false;

    private ArrayList<Sounds> soundsQueue = new ArrayList<>();
    private ArrayList<AudioClip> asteroidsExplosions = new ArrayList<>();

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    private  boolean exploding;





    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Moon getMoon() {
        return moon;
    }

    public void setMoon(Moon moon) {
        this.moon = moon;
    }



    public void addAsteroid(Asteroid asteroid){
        asteroids.add(asteroid);
    }
    public void addLasers(Laser laser){
        lasers.add(laser);
    }

}
