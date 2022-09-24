package it.pasqualini.planetroid.audio;

abstract public class AbstractSoundTrack {


    public abstract void update();

    public abstract Boolean isPlaying();

    protected abstract void stop();
}
