package space.bouncingballs;

import java.util.ArrayList;
import java.util.List;

import space.Game;
import space.PhysicalObject;
import space.Space;
import space.ui.Graphics;

public class BouncingBalls implements Game {

	private Space space;
	private boolean isBreackout;
	private int step;

	public BouncingBalls(Space space, boolean isBreackout) {
		this.space = space;
		this.isBreackout = isBreackout;
	}

	@Override
	public void step() {
		for (PhysicalObject physicalObject : space.getObjects()) {
			physicalObject.x = physicalObject.x + physicalObject.vx
					* space.getSeconds();

			physicalObject.y = physicalObject.y + physicalObject.vy
					* space.getSeconds();
		}

		step++;
	}

	@Override
	public void collide() {
		List<PhysicalObject> remove = new ArrayList<PhysicalObject>();

		for (PhysicalObject one : space.getObjects()) {
			if (remove.contains(one))
				continue;

			for (PhysicalObject other : space.getObjects()) {
				if (one == other || remove.contains(other))
					continue;

				double distance = Math.sqrt(Math.pow(one.x - other.x, 2)
						+ Math.pow(one.y - other.y, 2));

				double collsionDistance = one.radius + other.radius;

				if (distance < collsionDistance) {
					one.hitBy(other, space.getSeconds());
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

			if (one.y + one.radius > 800 && !isBreackout) {
				one.vy = -one.vy;
			}
			else if (one.y - one.radius > 800) {
				remove.add(one);
			}
		}

		space.getObjects().removeAll(remove);
	}

	@Override
	public void paintPhysicalObject(PhysicalObject physicalObject,
			Graphics graphics, int frameWidth, int frameHeight) {
		graphics.setColorWhite();

		int xtmp = (int) ((physicalObject.x - space.getCentrex()) + frameWidth / 2);

		int ytmp = (int) ((physicalObject.y - space.getCentrey()) + frameHeight / 2);

		graphics.fillOval((int) (xtmp - physicalObject.radius),
				(int) (ytmp - physicalObject.radius),
				(int) (2 * physicalObject.radius),
				(int) (2 * physicalObject.radius));
	}

	@Override
	public int getStep() {
		return step;
	}
}
