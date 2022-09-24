package it.pasqualini.planetroid.audio;


import static it.pasqualini.util.Util.println;

import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

import it.pasqualini.util.ListenerAdapter;


public class AudioClip {

    ListenerAdapter<SoundTrackManager.SoundTrackEvent> listenerAdapter = new ListenerAdapter<>();
    private DataLine.Info info;
    private AudioFormat audioFormat;
    byte[] audio;
    private Integer size;
    private float volume;

    public int from = 0;
    public int duration = Integer.MAX_VALUE;
    public Clip clip = null;

    public Boolean isPlaying() {
        return playing;
    }

private FloatControl floatControl;

    private  Boolean playing = false;


    public AudioClip(DataLine.Info info, AudioFormat audioFormat, byte[] audio, float volume) {
        this.info = info;
        this.audioFormat = audioFormat;
        this.audio = audio;
        this.size = audio.length;
        this.volume = volume;

        try {
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    //listenerAdapter.fire(new SoundTrackEvent(AudioClip.this,SoundTrack.Type.START));
                }
            });
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }


    public AudioClip(DataLine.Info info, AudioFormat audioFormat, byte[] audio, float volume, int from) {
        this.info = info;
        this.audioFormat = audioFormat;
        this.audio = audio;
        this.size = audio.length;
        this.volume = volume;
        try {
            clip = (Clip) AudioSystem.getLine(info);
            //this.floatControl =   (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    //listenerAdapter.fire(new SoundTrackEvent(AudioClip.this,SoundTrack.Type.START));
                }
            });
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void playSound() throws LineUnavailableException {
       // this.floatControl =   (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        playing = true;
        if (clip.isActive()) {
            //skip
            return;
        }
        if (clip.isOpen()) {
            //skip
            clip.close();
        }
        clip.open(audioFormat, audio, 0, size);

        //setVolume(0.1f   );
        //clip.setLoopPoints(1000000,-1);

        //clip.start();
        //clip.stop();

        clip.setMicrosecondPosition(from*1000);
        setVolume(0);
        clip.start();
        setVolume(volume);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    println("fine");
                    setVolume(0);
                    clip.stop();
                    clip.close();
                    playing = false;
                }
            },  duration);

    }

    public void playSound(int milliseconds) throws LineUnavailableException {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                clip.stop();
                listenerAdapter.fire(new SoundTrackManager.SoundTrackEvent(AudioClip.this, SoundTrackClip.SoundTrack.Type.STOP));

            }
        }, milliseconds);

        playSound();


        if (clip.isOpen()) {
            //skip
            //clip.close();
        }
        clip.open(audioFormat, audio, 0, size);

        //setVolume(0);
        //clip.setLoopPoints(1000000,-1);

        //clip.start();
        //clip.stop();

        clip.setMicrosecondPosition(100);
        setVolume(volume);
        clip.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                clip.stop();

                clip.close();
            }
        }, milliseconds);

    }


    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        // volume = 5f;


        //FloatControl f = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
       // f.setValue((float)0.5);
//        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        float range = gainControl.getMaximum() - gainControl.getMinimum();
//        float gain = (range * volume) + gainControl.getMinimum();
//        gainControl.setValue(gain);

        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }


    public void stop() {
        try {

            clip.stop();
            clip.close();
        } catch (Exception e) {
            // ignore errors
        }
    }
}
