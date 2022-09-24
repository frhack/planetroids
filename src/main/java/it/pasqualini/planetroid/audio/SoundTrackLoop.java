package it.pasqualini.planetroid.audio;


public class SoundTrackLoop extends AbstractSoundTrack {

    AbstractSoundTrack audioClip;
    int count;
boolean stopping;
    boolean playing = false;
    public SoundTrackLoop(AbstractSoundTrack audioClip, int count) {
        this.count = count;
        this.audioClip = audioClip;
    }

    @Override
    public void update() {
        if(! audioClip.isPlaying() && count >0){
                audioClip.update();
                playing = true;
                count--;
        }
        if(! audioClip.isPlaying() && count ==0) {
            playing = false;
        }

    }

    @Override
    public Boolean isPlaying() {
        return playing;
    }

    @Override
    protected void stop() {
stopping = true;
audioClip.stop();
stopping = false;
    }
}
