package shapeTools;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import main.GConstants.EDrawingStyle;

public class GOval extends GShapeTool {
	private int x0, y0;
	// attributes
	private static final long serialVersionUID = 1L;

	public GOval() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Ellipse2D.Float();
	}

	public Object clone() {
		GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((Ellipse2D.Float) (this.shape)).clone();
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
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		int locationX = x > this.x0 ? this.x0 : x;
		int locationY = y > this.y0 ? this.y0 : y;
		ellipse.setFrame(locationX, locationY, Math.abs(x - this.x0), Math.abs(y - this.y0));
	}
}
