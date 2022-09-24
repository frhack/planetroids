package it.pasqualini.planetroid.audio;

import java.util.ArrayList;

public class SoundTrackSequence extends AbstractSoundTrack {

    ArrayList<AbstractSoundTrack> tracks;

    int index = 0;
    int played = 0;
    boolean playing;

    boolean stopping;


    public SoundTrackSequence(ArrayList<AbstractSoundTrack> tracks) {
        this.tracks = tracks;
    }

    @Override
    public void update() {
        if (stopping) {
            return;
        }
        if (playing && !tracks.get(index).isPlaying()) {
            playing = false;
            index++;
            if (index >= tracks.size()) index = 0;
            tracks.get(index).update();
            playing = true;
            return;

        }
        if (playing) {
            tracks.get(index).update();
        } else if (!playing) {
            tracks.get(index).update();
            playing = true;
        }
    }

    public void stop() {
        stopping = true;
        for (AbstractSoundTrack ac : tracks) {
            ac.stop();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
        tracks.clear();
        stopping = false;

    }

    @Override
    public Boolean isPlaying() {
        return playing;
    }
}
