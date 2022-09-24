package it.pasqualini.planetroid;


import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import it.pasqualini.planetroid.engine.GameLoop;
import it.pasqualini.planetroid.engine.GameLoopBuilder;
import it.pasqualini.util.Util;

public class main {


    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        GameLoopBuilder gameBuilder = new GameLoopBuilder();

        GameLoop gameLoop = gameBuilder.build();
        gameLoop.start();


        Util.wait(5000000);

    }
}


