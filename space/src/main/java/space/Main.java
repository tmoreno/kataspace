package space;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import space.ui.swing.FrameSwing;
import space.ui.swing.KeyListenerSwing;
import space.ui.swing.MouseListenerSwing;

public class Main {

	private static boolean IS_BOUNCING_BALLS = false;

	public static void main(String[] args) throws InterruptedException,
			InvocationTargetException {

		Space space = new Space(IS_BOUNCING_BALLS);
		JFrame frame = new FrameSwing();

		if (!IS_BOUNCING_BALLS) {
			MouseListenerSwing mouseListener = new MouseListenerSwing(space,
					frame);
			frame.addMouseWheelListener(mouseListener);
			frame.addMouseMotionListener(mouseListener);
		}

		KeyListenerSwing keyListener = new KeyListenerSwing(space);
		frame.addKeyListener(keyListener);
		frame.setSize(800, 820);
		frame.setVisible(true);
		space.setWidth(frame.getWidth());
		space.setFrameHeight(frame.getSize().height);
		space.setFrameWidth(frame.getSize().width);
		space.initGame();

		while (true) {
			final long start = System.currentTimeMillis();

			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					space.collide();
					space.step();
					frame.paint(frame.getGraphics());
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
