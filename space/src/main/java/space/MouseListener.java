package space;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListener implements MouseWheelListener, MouseMotionListener {

	private Space space;
	private Point lastDrag;

	public MouseListener(Space space) {
		this.space = space;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Space.setScale(Space.getScale() + Space.getScale()
				* (Math.min(9, e.getWheelRotation())) / 10 + 0.0001);

		space.getGraphics()
				.clearRect(0, 0, space.getWidth(), space.getHeight());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (lastDrag == null) {
			lastDrag = e.getPoint();
		}

		Space.setCentrex(Space.getCentrex()
				- ((e.getX() - lastDrag.x) * Space.getScale()));

		Space.setCentrey(Space.getCentrey()
				- ((e.getY() - lastDrag.y) * Space.getScale()));

		lastDrag = e.getPoint();

		space.getGraphics()
				.clearRect(0, 0, space.getWidth(), space.getHeight());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastDrag = null;
	}
}
