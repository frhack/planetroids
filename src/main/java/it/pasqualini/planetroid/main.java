package it.pasqualini.planetroid;


import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import it.pasqualini.planetroid.engine.Frame;
import it.pasqualini.planetroid.engine.GameLoop;
import it.pasqualini.planetroid.engine.GameLoopBuilder;
import it.pasqualini.util.Util;

public class main {


    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        GameLoopBuilder gameBuilder = new GameLoopBuilder();

        Frame gameLoop = gameBuilder.build();



        gameLoop.run();
//        gameLoop.panel.setIgnoreRepaint(true);

        //        gameLoop.panel.createBufferStrategy(2);
        Util.wait(5000000);

    }
}


