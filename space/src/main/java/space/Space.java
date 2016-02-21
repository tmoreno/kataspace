package space;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Space extends JFrame implements KeyListener {
	public static final double EARTH_WEIGHT = 5.9736e24;
	private static boolean IS_BOUNCING_BALLS = false;
	static boolean IS_BREAKOUT = false; // Opens bottom, only active if
										// IS_BOUNCING_BALLS is true

	private static final long serialVersionUID = 1532817796535372081L;

	public static double seconds = 1;
	private static List<PhysicalObject> objects = new ArrayList<PhysicalObject>();
	static double centrex = 0.0;
	static double centrey = 0.0;
	static double scale = 10;
	private static boolean showWake = false;
	private static int step = 0;
	private static int frameRate = 25;

	static JFrame frame;

	private static Game game;

	public Space(boolean isBouncingBalls) {
		IS_BOUNCING_BALLS = isBouncingBalls;

		if (IS_BOUNCING_BALLS) {
			game = new BouncingBalls(this);
		}
		else {
			game = new SolarSystem(this);
		}

		setBackground(Color.BLACK);
		Space.frame = this;
	}

	@Override
	public void paint(Graphics original) {
		if (original != null) {
			BufferedImage buffer = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = buffer.createGraphics();

			if (!showWake) {
				graphics.clearRect(0, 0, getWidth(), getHeight());
			}
			for (PhysicalObject po : objects) {
				game.paintPhysicalObject(po, graphics);

				String string = "Objects:" + objects.size() + " scale:" + scale
						+ " steps:" + step + " frame rate: " + frameRate;
				setTitle(string);
			}
			original.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
		}

	}

	public static Color weightToColor(double weight) {
		if (weight < 1e10)
			return Color.GREEN;
		if (weight < 1e12)
			return Color.CYAN;
		if (weight < 1e14)
			return Color.MAGENTA;
		if (weight < 1e16)
			return Color.BLUE;
		if (weight < 1e18)
			return Color.GRAY;
		if (weight < 1e20)
			return Color.RED;
		if (weight < 1e22)
			return Color.ORANGE;
		if (weight < 1e25)
			return Color.PINK;
		if (weight < 1e28)
			return Color.YELLOW;
		return Color.WHITE;
	}

	public static void main(String[] args) throws InterruptedException,
			InvocationTargetException {
		final Space space = new Space(IS_BOUNCING_BALLS);
		space.addKeyListener(space);
		space.setSize(800, 820);

		game.init();

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
				long ahead = 1000 / frameRate
						- (System.currentTimeMillis() - start);
				if (ahead > 50) {
					Thread.sleep(ahead);
					if (frameRate < 25)
						frameRate++;
				}
				else {
					Thread.sleep(50);
					frameRate--;
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setStepSize(double seconds) {
		Space.seconds = seconds;
	}

	public static PhysicalObject add(double weightKilos, double x, double y,
			double vx, double vy, double radius) {
		PhysicalObject physicalObject = new PhysicalObject(weightKilos, x, y,
				vx, vy, radius);
		objects.add(physicalObject);
		return physicalObject;
	}

	public void step() {
		game.step();

		step++;

		paint(getGraphics());
	}

	private void collide() {
		List<PhysicalObject> remove = new ArrayList<PhysicalObject>();
		for (PhysicalObject one : objects) {
			if (remove.contains(one))
				continue;
			for (PhysicalObject other : objects) {
				if (one == other || remove.contains(other))
					continue;
				if (!IS_BOUNCING_BALLS) {
					if (Math.sqrt(Math.pow(one.x - other.x, 2)
							+ Math.pow(one.y - other.y, 2)) < 5e9) {
						one.absorb(other);
						remove.add(other);
					}
				}
				else {
					double distance = Math.sqrt(Math.pow(one.x - other.x, 2)
							+ Math.pow(one.y - other.y, 2));
					double collsionDistance = one.radius + other.radius;
					if (distance < collsionDistance) {
						one.hitBy(other, Space.seconds);
					}
				}
			}
			// Wall collision reverses speed in that direction
			if (IS_BOUNCING_BALLS) {
				if (one.x - one.radius < 0) {
					one.vx = -one.vx;
				}
				if (one.x + one.radius > 800) {
					one.vx = -one.vx;
				}
				if (one.y - one.radius < 0) {
					one.vy = -one.vy;
				}
				if (one.y + one.radius > 800 && !IS_BREAKOUT) {
					one.vy = -one.vy;
				}
				else if (one.y - one.radius > 800) {
					remove.add(one);
				}
			}
		}
		objects.removeAll(remove);
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'w')
			showWake = !showWake;
	}

	public static List<PhysicalObject> getObjects() {
		return Space.objects;
	}

}
