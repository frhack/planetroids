package it.pasqualini.planetroid.engine;

import java.awt.*;

import javax.swing.JFrame;

public class GameLoopBuilder {



    public Frame build(){
        GameLoop gameLoop;
//        JFrame win = new JFrame();
//
//        win.setLayout(new BorderLayout());
//        win.setLayout(new FlowLayout());
//        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        win.setResizable(false);
//        win.setLocationRelativeTo(null);
//        win.setUndecorated(false);

        //panel.setBackground(Color.black);
        //win.add(panel,new GridBagConstraints());


//        this.canvas.setPreferredSize(size);
//        this.canvas.setMinimumSize(size);
//        this.canvas.setMaximumSize(size);
        // add the canvas to the JFrame

        //win.setContentPane(panel);
        // panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

//        win.pack();
//        win.setLocationRelativeTo(null);
//        win.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        win.dispose();
//        //win.setUndecorated(true);
//        win.setVisible(true);

//
        Frame frame = new Frame("",100d);
        GamePanel panel = frame.panel;

        GameIntelligence gameIntelligence = new GameIntelligence(panel);
        GameGraphic gameGraphics = new GameGraphic(panel,gameIntelligence.gameScene);
        GamePhysic gamePhysic = new GamePhysic(gameIntelligence.gameScene);
        GameMusic gameMusic = new GameMusic(gameIntelligence.gameScene);
        gameLoop = new GameLoop(gameIntelligence,gamePhysic,gameIntelligence.gameScene,gameGraphics, gameMusic);


        frame.gameGraphycs = gameGraphics;
        frame.gameIntelligence = gameIntelligence;
        frame.gameMusic = gameMusic;
        frame.gamePhysic = gamePhysic;
        return frame;
    }





}
