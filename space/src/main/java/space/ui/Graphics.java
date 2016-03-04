package space.ui;

public interface Graphics {

	void setColorWhite();

	void setColorByWeight(double weight);

	void fillOval(int x, int y, int width, int height);

	void clearRect(int x, int y, int width, int height);

}
