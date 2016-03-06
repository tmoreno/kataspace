package space.ui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import space.PhysicalObject;
import space.Space;

public class FrameSwing extends JFrame {

	private static final long serialVersionUID = -184389870166680868L;

	private Space space;

	public FrameSwing(Space space) {
		this.space = space;

		setBackground(Color.BLACK);
	}

	@Override
	public void paint(Graphics original) {
		if (original != null) {
			BufferedImage buffer = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			GraphicsSwing graphics = new GraphicsSwing(buffer.createGraphics());

			if (!Space.isShowWake()) {
				graphics.clearRect(0, 0, getWidth(), getHeight());
			}
			for (PhysicalObject po : space.getObjects()) {
				space.getGame().paintPhysicalObject(po, graphics);

				String string = "Objects:" + space.getObjects().size()
						+ " scale:" + Space.getScale() + " steps:"
						+ Space.getStep() + " frame rate: "
						+ Space.getFrameRate();
				setTitle(string);
			}
			original.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
		}

	}

}
