package it.pasqualini.planetroid.engine;

import it.pasqualini.util.ListenerAdapter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

    public ListenerAdapter<KeyEvent> keyReleasedListenerAdapter = new ListenerAdapter<>();
    public ListenerAdapter<KeyEvent> keyPressedListenerAdapter = new ListenerAdapter<>();
    public boolean left, right, up, down, fullscreen, shooting, shift;

    @Override
    public void keyPressed(KeyEvent e) {

        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_B) {
            System.exit(0);
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            fullscreen = !fullscreen;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shooting = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyPressed(e);

        keyReleasedListenerAdapter.fire(e);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shooting = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = false;
        }
    }


}
