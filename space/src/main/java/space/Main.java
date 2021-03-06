package space;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import space.ui.swing.FrameSwing;
import space.ui.swing.KeyListenerSwing;
import space.ui.swing.MouseListenerSwing;

public class Main {

	public static void main(String[] args) throws InterruptedException,
			InvocationTargetException {

		FrameSwing frame = new FrameSwing();
		frame.setSize(800, 820);
		frame.setVisible(true);

		Space space;
		if (isBouncingBalls(args)) {
			space = SpaceFactory.createSpaceForBouncingBalls(50,
					isBreakOut(args));
		}
		else {
			space = SpaceFactory
					.createSpaceForSolarSystem(75, frame.getWidth());
		}

		frame.setSpace(space);

		KeyListenerSwing keyListener = new KeyListenerSwing(space);
		frame.addKeyListener(keyListener);

		if (!isBouncingBalls(args)) {
			MouseListenerSwing mouseListener = new MouseListenerSwing(space,
					frame);
			frame.addMouseWheelListener(mouseListener);
			frame.addMouseMotionListener(mouseListener);
		}

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
		String[] argument = args[0].split("=");

		return Boolean.valueOf(argument[1]);
	}

	private static boolean isBreakOut(String[] args) {
		if (args.length > 1) {
			String[] argument = args[1].split("=");

			return Boolean.valueOf(argument[1]);
		}
		else {
			return false;
		}
	}
}
