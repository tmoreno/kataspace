package space;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import space.bouncingballs.BouncingBalls;
import space.solarsystem.SolarSystem;
import space.ui.swing.FrameSwing;
import space.ui.swing.KeyListenerSwing;
import space.ui.swing.MouseListenerSwing;

public class Main {

	public static void main(String[] args) throws InterruptedException,
			InvocationTargetException {

		Space space = new Space();
		JFrame frame = new FrameSwing(space);

		Game game;
		if (isBouncingBalls(args)) {
			game = new BouncingBalls(space);
			space.setNrOfObjects(50);
		}
		else {
			game = new SolarSystem(space);
			space.setNrOfObjects(75);

			MouseListenerSwing mouseListener = new MouseListenerSwing(space,
					frame);
			frame.addMouseWheelListener(mouseListener);
			frame.addMouseMotionListener(mouseListener);
		}

		space.setGame(game);

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
				long ahead = 1000 / space.getFrameRate()
						- (System.currentTimeMillis() - start);

				if (ahead > 50) {
					Thread.sleep(ahead);
					if (space.getFrameRate() < 25) {
						space.setFrameRate(space.getFrameRate() + 1);
					}
				}
				else {
					Thread.sleep(50);
					space.setFrameRate(space.getFrameRate() - 1);
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static Boolean isBouncingBalls(String[] args) {
		return Boolean.valueOf(args[0]);
	}
}
