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

	public FrameSwing() {
		setBackground(Color.BLACK);
	}

	@Override
	public void paint(Graphics original) {
		if (original != null) {
			BufferedImage buffer = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			GraphicsSwing graphics = new GraphicsSwing(buffer.createGraphics());

			if (!space.isShowWake()) {
				graphics.clearRect(0, 0, getWidth(), getHeight());
			}
			for (PhysicalObject po : space.getObjects()) {
				space.paintPhysicalObject(po, graphics, getSize().width,
						getSize().height);

				String string = "Objects:" + space.getObjects().size()
						+ " scale:" + space.getScale() + " steps:"
						+ space.getStep() + " frame rate: "
						+ space.getFrameRate();
				setTitle(string);
			}
			original.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
		}

	}

	public void setSpace(Space space) {
		this.space = space;
	}

}
