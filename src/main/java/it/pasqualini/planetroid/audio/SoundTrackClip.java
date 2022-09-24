package it.pasqualini.planetroid.audio;

import javax.sound.sampled.LineUnavailableException;

public class SoundTrackClip extends AbstractSoundTrack {

    AudioClip audioClip;

    public SoundTrackClip(AudioClip audioClip) {
        this.audioClip = audioClip;
    }

    @Override
    public void update() {
        if(! audioClip.isPlaying()){
            try {
                audioClip.playSound();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public Boolean isPlaying() {
        return audioClip.isPlaying();
    }

    @Override
    protected void stop() {

        audioClip.clip.stop();
        audioClip.clip.close();
    }

    public static interface SoundTrack {
        public void Play();
        float getVolume();
        void setVolume(float volume);

        public class Type {

            public static final Type START = new Type("Start");
            public static final Type END = new Type("End");
            public static final Type STOP = new Type("Stop");
            String type;
            public Type(String type) {
                this.type = type;
            }
            }
    }
}
