package it.pasqualini.planetroid.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import it.pasqualini.util.ListenerAdapter;

public class KeyHandler extends KeyAdapter {

    public final ListenerAdapter<KeyEvent> keyReleasedListenerAdapter = new ListenerAdapter<>();
    public final ListenerAdapter<KeyEvent> keyPressedListenerAdapter = new ListenerAdapter<>();
    private boolean left, right, up, down, fullscreen, shooting, shift;

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

    public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	public ListenerAdapter<KeyEvent> getKeyReleasedListenerAdapter() {
		return keyReleasedListenerAdapter;
	}

	public ListenerAdapter<KeyEvent> getKeyPressedListenerAdapter() {
		return keyPressedListenerAdapter;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public boolean isShooting() {
		return shooting;
	}

	public boolean isShift() {
		return shift;
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
