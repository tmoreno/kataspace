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

		Game game;
		if (isBouncingBalls(args)) {
			game = new BouncingBalls(space, 50, isBreakOut(args));
		}
		else {
			game = new SolarSystem(space, 75);
		}

		JFrame frame = new FrameSwing(space, game);
		KeyListenerSwing keyListener = new KeyListenerSwing(space);
		frame.addKeyListener(keyListener);

		if (!isBouncingBalls(args)) {
			MouseListenerSwing mouseListener = new MouseListenerSwing(space,
					frame);
			frame.addMouseWheelListener(mouseListener);
			frame.addMouseMotionListener(mouseListener);
		}

		frame.setSize(800, 820);
		frame.setVisible(true);
		space.setWidth(frame.getWidth());
		game.init();

		while (true) {
			final long start = System.currentTimeMillis();

			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					game.collide();
					game.step();
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

	private static boolean isBreakOut(String[] args) {
		return Boolean.valueOf(args[1]);
	}
}
