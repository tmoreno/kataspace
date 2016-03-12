package space;

import space.ui.Graphics;

public interface Game {

	void step();

	void collide();

	void paintPhysicalObject(PhysicalObject physicalObject, Graphics graphics,
			int frameWidth, int frameHeight);

	int getStep();

}