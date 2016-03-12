package space;

public class SpaceFactory {

	private static final double ASTRONOMICAL_UNIT = 149597870.7e3;
	private static final double EARTH_WEIGHT = 5.9736e24;

	public static Space createSpaceForSolarSystem(int nrOfObjects, int width) {
		Space space = new Space();

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

			space.add(weightKilos, x, y, vx, vy, 1);
		}

		space.setScale(outerLimit / width);

		space.add(EARTH_WEIGHT * 20000, 0, 0, 0, 0, 1);

		return space;
	}

	private static double randSquare() {
		double random = Math.random();
		return random * random;
	}

	public static Space createSpaceForBouncingBalls(int nrOfObjects) {
		Space space = new Space();

		space.setStepSize(1); // One second per iteration

		for (int i = 0; i < nrOfObjects; i++) {
			// radius,weight in [1,20]
			double radiusAndWeight = 1 + 19 * Math.random();

			// x,y in [max radius, width or height - max radius]
			space.add(radiusAndWeight, 20 + 760 * Math.random(),
					20 + 760 * Math.random(), 3 - 6 * Math.random(),
					3 - 6 * Math.random(), radiusAndWeight);
		}

		space.setScale(1);
		space.setCentrex(400);
		space.setCentrey(390); // Must compensate for title bar

		return space;
	}
}
