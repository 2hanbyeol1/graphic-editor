package shapeTools;

import java.awt.Shape;
import java.awt.geom.Line2D;

import main.GConstants.EDrawingStyle;

public class GLine extends GShapeTool {
	private int x0, y0;
	
	private static final long serialVersionUID = 1L;

	public GLine() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Line2D.Float();
	}

	public Object clone() {
		GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((Line2D.Float) (this.shape)).clone();
		return cloned;
	}

	@Override
	public void setInitPoint(int x, int y) {
		this.x0 = x;
		this.y0 = y;
	}

	@Override
	public void setFinalPoint(int x, int y) { }

	@Override
	public void movePoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(this.x0, this.y0, x, y);
	}
}
