package space.ui.swing;

import java.awt.Color;
import java.awt.Graphics2D;

import space.ui.Graphics;

public class GraphicsSwing implements Graphics {

	private Graphics2D graphics;

	public GraphicsSwing(Graphics2D graphics) {
		this.graphics = graphics;
	}

	@Override
	public void setColorWhite() {
		graphics.setColor(Color.WHITE);
	}

	@Override
	public void setColorByWeight(double weight) {
		graphics.setColor(weightToColor(weight));
	}

	private Color weightToColor(double weight) {
		if (weight < 1e10)
			return Color.GREEN;
		if (weight < 1e12)
			return Color.CYAN;
		if (weight < 1e14)
			return Color.MAGENTA;
		if (weight < 1e16)
			return Color.BLUE;
		if (weight < 1e18)
			return Color.GRAY;
		if (weight < 1e20)
			return Color.RED;
		if (weight < 1e22)
			return Color.ORANGE;
		if (weight < 1e25)
			return Color.PINK;
		if (weight < 1e28)
			return Color.YELLOW;
		return Color.WHITE;
	}

	@Override
	public void fillOval(int x, int y, int width, int height) {
		graphics.fillOval(x, y, width, height);
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		graphics.clearRect(x, y, width, height);
	}

}
