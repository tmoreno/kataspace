package space;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import space.bouncingballs.BouncingBalls;
import space.solarsystem.SolarSystem;
import space.ui.swing.GraphicsSwing;

public class Space extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1532817796535372081L;

	static boolean IS_BREAKOUT = false; // Opens bottom, only active if
										// IS_BOUNCING_BALLS is true

	private static double seconds = 1;
	private static List<PhysicalObject> objects = new ArrayList<PhysicalObject>();
	private static double centrex = 0.0;
	private static double centrey = 0.0;
	private static double scale = 10;
	private static boolean showWake = false;
	private static int step = 0;
	private static int frameRate = 25;

	static JFrame frame;

	private static Game game;

	public Space(boolean isBouncingBalls) {
		if (isBouncingBalls) {
			game = new BouncingBalls(this);
		}
		else {
			game = new SolarSystem(this);

			MouseListener mouseListener = new MouseListener(this);
			this.addMouseWheelListener(mouseListener);
			this.addMouseMotionListener(mouseListener);
		}

		setBackground(Color.BLACK);
		Space.frame = this;
	}

	@Override
	public void paint(Graphics original) {
		if (original != null) {
			BufferedImage buffer = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			GraphicsSwing graphics = new GraphicsSwing(buffer.createGraphics());

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

	public static double getScale() {
		return scale;
	}

	public static void setScale(double scale) {
		Space.scale = scale;
	}

	public static double getCentrex() {
		return centrex;
	}

	public static void setCentrex(double centrex) {
		Space.centrex = centrex;
	}

	public static double getCentrey() {
		return centrey;
	}

	public static void setCentrey(double centrey) {
		Space.centrey = centrey;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static boolean isBreackout() {
		return IS_BREAKOUT;
	}

	public static int getFrameRate() {
		return frameRate;
	}

	public static void setFrameRate(int frameRate) {
		Space.frameRate = frameRate;
	}

	public void initGame() {
		game.init();
	}

	public void collide() {
		game.collide();
	}

	public static double getSeconds() {
		return seconds;
	}

	public static void setSeconds(double seconds) {
		Space.seconds = seconds;
	}

}
