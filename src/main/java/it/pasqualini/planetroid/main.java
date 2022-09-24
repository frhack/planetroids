package it.pasqualini.planetroid;


import it.pasqualini.planetroid.engine.GameLoopBuilder;
import it.pasqualini.planetroid.engine.GameLoop;
import it.pasqualini.util.Util;

import javax.sound.sampled.*;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class main {


    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        GameLoopBuilder gameBuilder = new GameLoopBuilder();

        GameLoop gameLoop = gameBuilder.build();
        gameLoop.start();


        Util.wait(5000000);

    }
}


