package space;

import java.util.ArrayList;
import java.util.List;

import space.bouncingballs.BouncingBalls;
import space.solarsystem.SolarSystem;

public class Space {

	static boolean IS_BREAKOUT = false; // Opens bottom, only active if
										// IS_BOUNCING_BALLS is true

	private double seconds;
	private List<PhysicalObject> objects;
	private double centrex;
	private double centrey;
	private double scale;
	private boolean showWake;
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

		seconds = 1;
		objects = new ArrayList<PhysicalObject>();
		centrex = 0.0;
		centrey = 0.0;
		scale = 10;
		showWake = false;
	}

	public void setStepSize(double seconds) {
		this.seconds = seconds;
	}

	public PhysicalObject add(double weightKilos, double x, double y,
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

	public List<PhysicalObject> getObjects() {
		return objects;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getCentrex() {
		return centrex;
	}

	public void setCentrex(double centrex) {
		this.centrex = centrex;
	}

	public double getCentrey() {
		return centrey;
	}

	public void setCentrey(double centrey) {
		this.centrey = centrey;
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

	public double getSeconds() {
		return seconds;
	}

	public void setSeconds(double seconds) {
		this.seconds = seconds;
	}

	public boolean isShowWake() {
		return showWake;
	}

	public void setShowWake(boolean showWake) {
		this.showWake = showWake;
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
