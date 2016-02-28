package space;

public interface Game {

	void init();

	void step();

	void collide();

	void paintPhysicalObject(PhysicalObject physicalObject, Graphics graphics);
}