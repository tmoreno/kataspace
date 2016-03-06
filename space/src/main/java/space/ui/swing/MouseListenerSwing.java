package space.ui.swing;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

import space.Space;

public class MouseListenerSwing implements MouseWheelListener,
		MouseMotionListener {

	private Space space;
	private JFrame frame;
	private Point lastDrag;

	public MouseListenerSwing(Space space, JFrame frame) {
		this.space = space;
		this.frame = frame;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Space.setScale(Space.getScale() + Space.getScale()
				* (Math.min(9, e.getWheelRotation())) / 10 + 0.0001);

		frame.getGraphics()
				.clearRect(0, 0, frame.getWidth(), frame.getHeight());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (lastDrag == null) {
			lastDrag = e.getPoint();
		}

		space.setCentrex(space.getCentrex()
				- ((e.getX() - lastDrag.x) * Space.getScale()));

		space.setCentrey(space.getCentrey()
				- ((e.getY() - lastDrag.y) * Space.getScale()));

		lastDrag = e.getPoint();

		frame.getGraphics()
				.clearRect(0, 0, frame.getWidth(), frame.getHeight());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastDrag = null;
	}
}
