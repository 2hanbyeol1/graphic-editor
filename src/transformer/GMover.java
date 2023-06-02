package transformer;

import java.awt.Graphics2D;

import shapeTools.GShapeTool;

public class GMover extends GTransformer {

	public GMover(GShapeTool selectedShape) {
		super(selectedShape);
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.px = x;
		this.py = y;

	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.selectedShape.move(graphics2d, x - px, y - py);
		this.px = x;
		this.py = y;
	}
}
