package space;

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

		Space.scale = outerLimit / space.getWidth();

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
			aff.x = aff.x - ax * Math.pow(Space.seconds, 2) / 2 + aff.vx
					* Space.seconds;
			aff.y = aff.y - ay * Math.pow(Space.seconds, 2) / 2 + aff.vy
					* Space.seconds;
			aff.vx = aff.vx - ax * Space.seconds;
			aff.vy = aff.vy - ay * Space.seconds;
		}
	}
}
