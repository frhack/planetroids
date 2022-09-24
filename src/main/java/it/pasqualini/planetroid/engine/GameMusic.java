package it.pasqualini.planetroid.engine;



import it.pasqualini.planetroid.audio.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import static it.pasqualini.util.Util.println;

public class GameMusic {
    private final GameScene gameScene;
    private final KeyHandler keyHandler;
    int ac = 0;

    ArrayList<AbstractSoundTrack> abstractSoundTracks = new ArrayList<>();
    public SoundTrackSequence soundTrackSequence;

    private ArrayList<AudioClip> audioClipsExplodeAsteroid = new ArrayList<>();
    private ArrayList<AudioClip> audioClipsExplodePlayer = new ArrayList<>();
    private ArrayList<AudioClip> audioClipsFireLaser = new ArrayList<>();
    private ArrayList<AudioClip> audioClipsHitAsteroid = new ArrayList<>();

    public GameMusic(GameScene gameScene) {
        this.gameScene = gameScene;
        this.keyHandler = gameScene.getPanel().keyHandler;
        initBrani();

        initAudioClips();
        initSequence();
        keyHandler.keyReleasedListenerAdapter.addEventListener(new SoundTrackManager.EventListener<KeyEvent>() {
            @Override
            public void consume(KeyEvent item) {
                handleKeyEvent(item);
            }
        });


    }


    private void handleKeyEvent(KeyEvent item) {
        if (item.getKeyCode() == KeyEvent.VK_S && Character.isLowerCase(item.getKeyChar())) {
            gameScene.setSoundVolume(gameScene.getSoundVolume() - 1);
            if (gameScene.getSoundVolume() < 0) gameScene.setSoundVolume(0);
            initAudioClips();
        } else if (item.getKeyCode() == KeyEvent.VK_S && Character.isUpperCase(item.getKeyChar())) {
            gameScene.setSoundVolume(gameScene.getSoundVolume() + 1);
            if (gameScene.getSoundVolume() > 10) gameScene.setSoundVolume(10);
            initAudioClips();
        } else if (item.getKeyCode() == KeyEvent.VK_M && Character.isLowerCase(item.getKeyChar())) {
            gameScene.setMusicVolume(gameScene.getMusicVolume() - 1);
            if (gameScene.getMusicVolume() < 0) gameScene.setMusicVolume(0);
            soundTrackSequence.stop();
            initBrani();
            initSequence();
        } else if (item.getKeyCode() == KeyEvent.VK_M && Character.isUpperCase(item.getKeyChar())) {
            gameScene.setMusicVolume(gameScene.getMusicVolume() + 1);
            if (gameScene.getMusicVolume() > 10) gameScene.setMusicVolume(10);
            soundTrackSequence.stop();
            initBrani();
            initSequence();
        }
    }

    private void initAudioClips() {
        //initAudioClipsExplodePlayer();
        initAudioClipsExplodeAsteroid();
        initAudioClipsLaserFire();
        initAudioHitAsteroid();
        initAudioClipsLaserFire();

    }

    public void update() {
        AudioClip audioClip = null;
        try {
            soundTrackSequence.update();
            if (gameScene.getAsteroidsExplosions().size() > 0) {
                audioClip = gameScene.getAsteroidsExplosions().get(0);
                gameScene.getAsteroidsExplosions().remove(0);
                //audioClip.setVolume((float) (1.0));
                audioClip.playSound();
            }
            if (gameScene.getSoundsQueue().size() > 0) {
                GameScene.Sounds s = gameScene.getSoundsQueue().get(0);
                gameScene.getSoundsQueue().remove(0);
                if(s == GameScene.Sounds.ASTEROID_EXPLOSION){
                    audioClip = audioClipsExplodeAsteroid.get(ac++ % 4);
                }
                if(s == GameScene.Sounds.LASER_FIRE){
                    audioClip = audioClipsFireLaser.get(ac++ % 4);
                }
                if(s == GameScene.Sounds.ASTEROID_HIT){
                    audioClip = audioClipsHitAsteroid.get(ac++ % 4);
                }
                if(s == GameScene.Sounds.PLAYER_EXPLOSION){
                    audioClip = audioClipsHitAsteroid.get(ac++ % 4);
                }
                audioClip.playSound();
            }
            //
        } catch (Exception e) {
            println("error " + e.getMessage());
        }
    }

    public void initBrani() {
        AudioClip scX0 = null;
        try {
            scX0 = new AudioClipBuilder().BuildFromWAVFile("sounds/soundtrack.wav", (float) (gameScene.getMusicVolume() * 0.10));
            int shift = 0;
            scX0.from = 38650 + shift;
            scX0.duration = 57600 - shift;
            //scX0.duration =  5000;
            AudioClip scX1 = new AudioClipBuilder().BuildFromWAVFile("sounds/soundtrack.wav", (float) (gameScene.getMusicVolume() * 0.10));
            scX1.from = 57600 + 38650 + 20000 + shift;
            scX1.duration = 102000 - shift - 250;

            AudioClip scX2 = new AudioClipBuilder().BuildFromWAVFile("sounds/soundtrack.wav", (float) (gameScene.getMusicVolume() * 0.10));
            scX2.from = 57600 + 38650 + 20000 + 102000 + shift;
            scX2.duration = 70000 - 2250 - shift;


            AudioClip scX3 = new AudioClipBuilder().BuildFromWAVFile("sounds/soundtrack.wav", (float) (gameScene.getMusicVolume() * 0.10));
            scX3.from = 57600 + 38650 + 20000 + 102000 + 70000 - 2250 + 33900;
            scX3.duration = 7900 + 60000;

            AudioClip scX4 = new AudioClipBuilder().BuildFromWAVFile("sounds/soundtrack.wav", (float) (gameScene.getMusicVolume() * 0.10));
            scX4.from = 57600 + 38650 + 20000 + 102000 + 70000 - 2250 + 33900 + 7900 + 60000;
            scX4.duration = 65000 + 2800;

            AudioClip scX5 = new AudioClipBuilder().BuildFromWAVFile("sounds/soundtrack.wav", (float) (gameScene.getMusicVolume() * 0.10));
            scX5.from = 57600 + 38650 + 20000 + 102000 + 70000 - 2250 + 33900 + 7900 + 60000 + 65000 + 2800;

            scX5.duration = 80000 + 4200;

//
//        AudioClip scX6 = new AudioClipBuilder().BuildFromWAVFile("sounds/soundtrack.wav",1f);
//        scX6.from = 57600 + 38650 + 20000 + 102000  + 70000 - 2250 + 33900 + 7900 + 60000 + 65000 + 2800+  80000+ 7000   ;
//        scX6.duration = 1570;

            AudioClip scX6 = new AudioClipBuilder().BuildFromWAVFile("sounds/soundtrack.wav", (float) (gameScene.getMusicVolume() * 0.10));
            scX6.from = 57600 + 38650 + 20000 + 102000 + 70000 - 2250 + 33900 + 7900 + 60000 + 65000 + 2800 + 80000 + 7000;
            scX6.duration = 1570;


            AbstractSoundTrack loop = new SoundTrackLoop(new SoundTrackClip(scX5), 3);


            ArrayList<AbstractSoundTrack> beet = new ArrayList<>();
            beet.add(new SoundTrackClip(scX6));
            beet.add(new SoundTrackClip(scX6));
            beet.add(new SoundTrackClip(scX6));
            beet.add(new SoundTrackClip(scX6));
            beet.add(new SoundTrackClip(scX6));


            abstractSoundTracks.clear();
            abstractSoundTracks.addAll(beet);
            abstractSoundTracks.add(new SoundTrackClip(scX0));
            abstractSoundTracks.addAll(beet);
            abstractSoundTracks.add(new SoundTrackClip(scX1));
            abstractSoundTracks.addAll(beet);
            abstractSoundTracks.add(new SoundTrackClip(scX2));
            abstractSoundTracks.addAll(beet);
            abstractSoundTracks.add(new SoundTrackClip(scX3));
            abstractSoundTracks.addAll(beet);
            abstractSoundTracks.add(new SoundTrackClip(scX4));
            abstractSoundTracks.addAll(beet);
            abstractSoundTracks.add(new SoundTrackClip(scX5));
            abstractSoundTracks.addAll(beet);
            abstractSoundTracks.add(new SoundTrackClip(scX6));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void initSequence() {
        soundTrackSequence = new SoundTrackSequence(abstractSoundTracks);
    }

    private void initAudioClipsExplodePlayer() {
        this.audioClipsExplodePlayer.clear();
        try {
            this.audioClipsExplodePlayer.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsExplodePlayer.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsExplodePlayer.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsExplodePlayer.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void initAudioClipsExplodeAsteroid() {
        this.audioClipsExplodeAsteroid.clear();
        try {
            this.audioClipsExplodeAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsExplodeAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsExplodeAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsExplodeAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsExplodeAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsExplodeAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/explode.wav", (float) (gameScene.getSoundVolume() * 0.10)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initAudioClipsLaserFire() {
        this.audioClipsFireLaser.clear();
        try {
            this.audioClipsFireLaser.add(new AudioClipBuilder().BuildFromWAVFile("sounds/laser.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsFireLaser.add(new AudioClipBuilder().BuildFromWAVFile("sounds/laser.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsFireLaser.add(new AudioClipBuilder().BuildFromWAVFile("sounds/laser.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsFireLaser.add(new AudioClipBuilder().BuildFromWAVFile("sounds/laser.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsFireLaser.add(new AudioClipBuilder().BuildFromWAVFile("sounds/laser.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsFireLaser.add(new AudioClipBuilder().BuildFromWAVFile("sounds/laser.wav", (float) (gameScene.getSoundVolume() * 0.10)));
        } catch (IOException e) {
            println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private void initAudioHitAsteroid() {
        this.audioClipsHitAsteroid.clear();
        try {
            this.audioClipsHitAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/hit.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsHitAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/hit.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsHitAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/hit.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsHitAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/hit.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsHitAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/hit.wav", (float) (gameScene.getSoundVolume() * 0.10)));
            this.audioClipsHitAsteroid.add(new AudioClipBuilder().BuildFromWAVFile("sounds/hit.wav", (float) (gameScene.getSoundVolume() * 0.10)));
        } catch (IOException e) {
            println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
