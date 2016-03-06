package space.ui.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import space.Space;

public class KeyListenerSwing implements KeyListener {

	private Space space;

	public KeyListenerSwing(Space space) {
		this.space = space;
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			space.setShowWake(!space.isShowWake());
		}
	}

}
