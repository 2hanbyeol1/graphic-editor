package transformer;

import java.awt.Graphics2D;
import shapeTools.GShapeTool;

public abstract class GTransformer {

	protected GShapeTool selectedShape;
	protected int px, py;

	public GTransformer(GShapeTool selectedShape) {
		this.selectedShape = selectedShape;
	}

	abstract public void initTransforming(Graphics2D graphics2d, int x, int y);

	abstract public void keepTransforming(Graphics2D graphics2d, int x, int y);

	public void finishTransforming(Graphics2D graphics2d, int x, int y) { }
}
