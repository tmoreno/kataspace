package space;

import java.util.ArrayList;
import java.util.List;

import space.ui.swing.GraphicsSwing;

public class Space {

	private double seconds;
	private List<PhysicalObject> objects;
	private double centrex;
	private double centrey;
	private double scale;
	private boolean showWake;
	private int frameRate;
	private int step;

	private Game game;

	public Space() {
		seconds = 1;
		objects = new ArrayList<PhysicalObject>();
		centrex = 0.0;
		centrey = 0.0;
		scale = 10;
		showWake = false;
		frameRate = 25;
		step = 0;
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

	public void collide() {
		game.collide();
	}

	public void paintPhysicalObject(PhysicalObject po, GraphicsSwing graphics,
			int width, int height) {

		game.paintPhysicalObject(po, graphics, width, height);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getStep() {
		return step;
	}

	public void setStepSize(double seconds) {
		this.seconds = seconds;
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

	public int getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
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

}
