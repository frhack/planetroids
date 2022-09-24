package it.pasqualini.planetroid.engine;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import it.pasqualini.util.GamePanel;

public class GameLoopBuilder {



    public GameLoop build(){
        GameLoop gameLoop;
        JFrame win = new JFrame();

        win.setLayout(new BorderLayout());
        win.setLayout(new FlowLayout());
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        win.setUndecorated(false);
        GamePanel panel = new GamePanel(win);
        //panel.setBackground(Color.black);
        //win.add(panel,new GridBagConstraints());
        win.setContentPane(panel);
        // panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        win.pack();
        win.setLocationRelativeTo(null);
        win.setExtendedState(JFrame.MAXIMIZED_BOTH);
        win.dispose();
        //win.setUndecorated(true);
        win.setVisible(true);
        panel.f = win;


        GameIntelligence gameIntelligence = new GameIntelligence(panel);
        GameGraphic gameGraphics = new GameGraphic(panel,gameIntelligence.gameScene);
        GamePhysic gamePhysic = new GamePhysic(panel);
        GameMusic gameMusic = new GameMusic(gameIntelligence.gameScene);
        gameLoop = new GameLoop(gameIntelligence,gamePhysic,gameIntelligence.gameScene,gameGraphics, gameMusic);
        return gameLoop;
    }





}
