package space;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import space.ui.swing.KeyListenerSwing;

public class Main {

	private static boolean IS_BOUNCING_BALLS = false;

	public static void main(String[] args) throws InterruptedException,
			InvocationTargetException {

		final Space space = new Space(IS_BOUNCING_BALLS);
		KeyListenerSwing keyListener = new KeyListenerSwing(space);
		space.addKeyListener(keyListener);
		space.setSize(800, 820);
		space.initGame();
		space.setFrameHeight(space.getSize().height);
		space.setFrameWidth(space.getSize().width);
		space.setVisible(true);

		while (true) {
			final long start = System.currentTimeMillis();

			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					space.collide();
					space.step();
				}
			});

			try {
				long ahead = 1000 / Space.getFrameRate()
						- (System.currentTimeMillis() - start);

				if (ahead > 50) {
					Thread.sleep(ahead);
					if (Space.getFrameRate() < 25) {
						Space.setFrameRate(Space.getFrameRate() + 1);
					}
				}
				else {
					Thread.sleep(50);
					Space.setFrameRate(Space.getFrameRate() - 1);
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
