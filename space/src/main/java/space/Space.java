package space;

import java.util.ArrayList;
import java.util.List;

import space.bouncingballs.BouncingBalls;
import space.solarsystem.SolarSystem;

public class Space {

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

	private int nrOfObjects;
	private Game game;
	private int width;
	private int frameWidth;
	private int frameHeight;

	public Space(boolean isBouncingBalls) {
		if (isBouncingBalls) {
			game = new BouncingBalls(this);
			nrOfObjects = 50;
		}
		else {
			game = new SolarSystem(this);
			nrOfObjects = 75;
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

	public static boolean isShowWake() {
		return showWake;
	}

	public static void setShowWake(boolean showWake) {
		Space.showWake = showWake;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}

	public static int getStep() {
		return step;
	}

	public static void setStep(int step) {
		Space.step = step;
	}

	public Game getGame() {
		return game;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getNrOfObjects() {
		return nrOfObjects;
	}

	public void setNrOfObjects(int nrOfObjects) {
		this.nrOfObjects = nrOfObjects;
	}

}
