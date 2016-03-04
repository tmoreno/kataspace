package space.solarsystem;

import java.util.ArrayList;
import java.util.List;

import space.Game;
import space.Graphics;
import space.PhysicalObject;
import space.Space;

public class SolarSystem implements Game {

	private static final double ASTRONOMICAL_UNIT = 149597870.7e3;
	private static final double EARTH_WEIGHT = 5.9736e24;
	private static final double G = 6.67428e-11; // m3/kgs2

	private static int nrOfObjects = 75;

	private Space space;

	public SolarSystem(Space space) {
		this.space = space;
	}

	@Override
	public void init() {
		space.setStepSize(3600 * 24 * 7);

		double outerLimit = ASTRONOMICAL_UNIT * 20;

		for (int i = 0; i < nrOfObjects; i++) {
			double angle = randSquare() * 2 * Math.PI;
			double radius = (0.1 + 0.9 * Math.sqrt(randSquare())) * outerLimit;
			double weightKilos = 1e3 * EARTH_WEIGHT
					* (Math.pow(0.00001 + 0.99999 * randSquare(), 12));
			double x = radius * Math.sin(angle);
			double y = radius * Math.cos(angle);
			double speedRandom = Math.sqrt(1 / radius) * 2978000 * 1500
					* (0.4 + 0.6 * randSquare());

			double vx = speedRandom * Math.sin(angle - Math.PI / 2);
			double vy = speedRandom * Math.cos(angle - Math.PI / 2);
			Space.add(weightKilos, x, y, vx, vy, 1);
		}

		Space.setScale(outerLimit / space.getWidth());

		Space.add(EARTH_WEIGHT * 20000, 0, 0, 0, 0, 1);
	}

	private double randSquare() {
		double random = Math.random();
		return random * random;
	}

	@Override
	public void step() {
		for (PhysicalObject aff : Space.getObjects()) {
			double fx = 0;
			double fy = 0;
			for (PhysicalObject oth : Space.getObjects()) {
				if (aff == oth)
					continue;
				double[] d = new double[] { aff.x - oth.x, aff.y - oth.y };
				double r2 = Math.pow(d[0], 2) + Math.pow(d[1], 2);
				double f = G * aff.mass * oth.mass / r2;
				double sqrtOfR2 = Math.sqrt(r2);
				fx += f * d[0] / sqrtOfR2;
				fy += f * d[1] / sqrtOfR2;
			}
			double ax = fx / aff.mass;
			double ay = fy / aff.mass;
			aff.x = aff.x - ax * Math.pow(Space.getSeconds(), 2) / 2 + aff.vx
					* Space.getSeconds();
			aff.y = aff.y - ay * Math.pow(Space.getSeconds(), 2) / 2 + aff.vy
					* Space.getSeconds();
			aff.vx = aff.vx - ax * Space.getSeconds();
			aff.vy = aff.vy - ay * Space.getSeconds();
		}
	}

	@Override
	public void collide() {
		List<PhysicalObject> remove = new ArrayList<PhysicalObject>();

		for (PhysicalObject one : Space.getObjects()) {
			if (remove.contains(one))
				continue;

			for (PhysicalObject other : Space.getObjects()) {
				if (one == other || remove.contains(other))
					continue;

				if (Math.sqrt(Math.pow(one.x - other.x, 2)
						+ Math.pow(one.y - other.y, 2)) < 5e9) {
					one.absorb(other);
					remove.add(other);
				}
			}
		}

		Space.getObjects().removeAll(remove);
	}

	@Override
	public void paintPhysicalObject(PhysicalObject physicalObject,
			Graphics graphics) {

		graphics.setColorByWeight(physicalObject.mass);

		int diameter = physicalObject.mass >= EARTH_WEIGHT * 10000 ? 7 : 2;

		int xtmp = (int) ((physicalObject.x - Space.getCentrex())
				/ Space.getScale() + Space.getFrame().getSize().width / 2);

		int ytmp = (int) ((physicalObject.y - Space.getCentrey())
				/ Space.getScale() + Space.getFrame().getSize().height / 2);

		graphics.fillOval(xtmp - diameter / 2, ytmp - diameter / 2, diameter,
				diameter);
	}

}
