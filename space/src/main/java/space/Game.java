package space;

import java.awt.Graphics2D;

public interface Game {

	void init();

	void step();

	void collide();

	void paintPhysicalObject(PhysicalObject physicalObject, Graphics2D graphics);
}