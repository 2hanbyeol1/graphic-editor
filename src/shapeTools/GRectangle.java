package shapeTools;

import java.awt.Rectangle;
import java.awt.Shape;

import main.GConstants.EDrawingStyle;

public class GRectangle extends GShapeTool {
	private int x0, y0;
	// attributes
	private static final long serialVersionUID = 1L;

	// components

	// constructors
	public GRectangle() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Rectangle();
	}

	public Object clone() {
		GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) (((Rectangle) this.shape)).clone();
		return cloned;
	}

	// methods
	@Override
	public void setInitPoint(int x, int y) {
		this.x0 = x;
		this.y0 = y;
	}

	@Override
	public void setFinalPoint(int x, int y) { }

	@Override
	public void movePoint(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		int locationX = x > this.x0 ? this.x0 : x;
		int locationY = y > this.y0 ? this.y0 : y;
		rectangle.setLocation(locationX, locationY);
		rectangle.setSize(Math.abs(x - this.x0), Math.abs(y - this.y0));
	}
}
