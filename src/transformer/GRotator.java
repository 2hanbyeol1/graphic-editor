package transformer;

import java.awt.Graphics2D;
import java.awt.Point;

import shapeTools.GShapeTool;

public class GRotator extends GTransformer {

	public GRotator(GShapeTool selectedShape) {
		super(selectedShape);
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.selectedShape.rotate(graphics2d, new Point(px, py), new Point(x, y));
		this.px = x;
		this.py = y;
	}
}
