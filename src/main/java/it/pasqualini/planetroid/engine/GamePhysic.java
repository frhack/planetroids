package it.pasqualini.planetroid.engine;

import it.pasqualini.planetroid.entity.Asteroid;
import it.pasqualini.planetroid.entity.Laser;
import it.pasqualini.planetroid.entity.Particle;
import it.pasqualini.planetroid.entity.Player;
import it.pasqualini.util.GamePanel;
import it.pasqualini.util.Vector2;

public class GamePhysic {
    public GamePhysic(GamePanel panel) {
        this.gamePanel = panel;
    }

    GamePanel gamePanel;

    public void update(GameScene gameScene) {
        Player player = gameScene.getPlayer();

        player.incrementX(gameScene.getSpeedX());
        player.incrementY(gameScene.getSpeedY());
        if(gameScene.isGaming()){
            player.angle += gameScene.getPlayer().angularSpeed;
            player.collision = (
                    !player.isExploding() && !player.isInvincible()
                            && !gameScene.isCheating())
                    && gameScene.getPlayer().collide(gameScene.getAsteroids()
            );
        }
        for (Laser l : gameScene.getLasers()) {
            for (Asteroid a : gameScene.getAsteroids()) {
                if (a.collide(l)) {
                    l.setHit(true);
                    a.setLaserHit(1 + a.getLaserHit());
                    a.hitPosition = new Vector2(l.getX(), l.getY());
                    break;
                }
            }
        }
        for (Asteroid a : gameScene.getAsteroids()) {
            a.incrementX(a.speed.getX());
            a.incrementY(a.speed.getY());
        }
        for (Laser a : gameScene.getLasers()) {
            a.incrementX(a.speed.getX());
            a.incrementY(a.speed.getY());
        }
        for (Particle a : gameScene.getParticles()) {
            a.incrementX(a.speed.getX());
            a.incrementY(a.speed.getY());
        }

    }


}
