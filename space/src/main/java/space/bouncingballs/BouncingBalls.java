package space.bouncingballs;

import java.util.ArrayList;
import java.util.List;

import space.Game;
import space.PhysicalObject;
import space.Space;
import space.ui.Graphics;

public class BouncingBalls implements Game {

	private Space space;

	public BouncingBalls(Space space) {
		this.space = space;
	}

	@Override
	public void init() {
		space.setStepSize(1); // One second per iteration
		for (int i = 0; i < space.getNrOfObjects(); i++) {
			// radius,weight in [1,20]
			double radiusAndWeight = 1 + 19 * Math.random();
			// x,y in [max radius, width or height - max radius]
			Space.add(radiusAndWeight, 20 + 760 * Math.random(),
					20 + 760 * Math.random(), 3 - 6 * Math.random(),
					3 - 6 * Math.random(), radiusAndWeight);
		}
		Space.setScale(1);
		Space.setCentrex(400);
		Space.setCentrey(390); // Must compensate for title bar
	}

	@Override
	public void step() {
		for (PhysicalObject physicalObject : Space.getObjects()) {
			physicalObject.x = physicalObject.x + physicalObject.vx
					* Space.getSeconds();
			physicalObject.y = physicalObject.y + physicalObject.vy
					* Space.getSeconds();
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

				double distance = Math.sqrt(Math.pow(one.x - other.x, 2)
						+ Math.pow(one.y - other.y, 2));

				double collsionDistance = one.radius + other.radius;

				if (distance < collsionDistance) {
					one.hitBy(other, Space.getSeconds());
				}
			}

			if (one.x - one.radius < 0) {
				one.vx = -one.vx;
			}

			if (one.x + one.radius > 800) {
				one.vx = -one.vx;
			}

			if (one.y - one.radius < 0) {
				one.vy = -one.vy;
			}

			if (one.y + one.radius > 800 && !Space.isBreackout()) {
				one.vy = -one.vy;
			}
			else if (one.y - one.radius > 800) {
				remove.add(one);
			}
		}

		Space.getObjects().removeAll(remove);
	}

	@Override
	public void paintPhysicalObject(PhysicalObject physicalObject,
			Graphics graphics) {
		graphics.setColorWhite();

		int xtmp = (int) ((physicalObject.x - Space.getCentrex()) + space
				.getFrameWidth() / 2);

		int ytmp = (int) ((physicalObject.y - Space.getCentrey()) + space
				.getFrameHeight() / 2);

		graphics.fillOval((int) (xtmp - physicalObject.radius),
				(int) (ytmp - physicalObject.radius),
				(int) (2 * physicalObject.radius),
				(int) (2 * physicalObject.radius));
	}
}
