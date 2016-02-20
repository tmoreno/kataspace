package space;

public class BouncingBalls implements Game {

	private static int nrOfObjects = 50;

	private Space space;

	public BouncingBalls(Space space) {
		this.space = space;
	}

	@Override
	public void init() {
		space.setStepSize(1); // One second per iteration
		for (int i = 0; i < nrOfObjects; i++) {
			// radius,weight in [1,20]
			double radiusAndWeight = 1 + 19 * Math.random();
			// x,y in [max radius, width or height - max radius]
			Space.add(radiusAndWeight, 20 + 760 * Math.random(),
					20 + 760 * Math.random(), 3 - 6 * Math.random(),
					3 - 6 * Math.random(), radiusAndWeight);
		}
		Space.scale = 1;
		Space.centrex = 400;
		Space.centrey = 390; // Must compensate for title bar
	}

	@Override
	public void step() {
		for (PhysicalObject physicalObject : Space.getObjects()) {
			physicalObject.x = physicalObject.x + physicalObject.vx
					* Space.seconds;
			physicalObject.y = physicalObject.y + physicalObject.vy
					* Space.seconds;
		}
	}
}
