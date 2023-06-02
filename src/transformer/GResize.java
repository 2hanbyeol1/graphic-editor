package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import shapeTools.GShapeTool;
import shapeTools.GShapeTool.EAnchors;

public class GResize extends GTransformer {

	private double cx, cy;
	private double width, height;

	public GResize(GShapeTool selectedShape) {
		super(selectedShape);
	}
	
	private Point2D getResizerOrigin() {
		Point2D p = new Point2D.Double();
		Ellipse2D[] anchors = this.selectedShape.getAnchors();
		switch (this.selectedShape.getEAnchors()) {
			// NW
			case x0y0:
				p.setLocation(anchors[EAnchors.x2y2.ordinal()].getCenterX(), anchors[EAnchors.x2y2.ordinal()].getCenterY());
				break;
			// N
			case x1y0:
				p.setLocation(0, anchors[EAnchors.x1y2.ordinal()].getCenterY());
				break;
			// NE
			case x2y0:
				p.setLocation(anchors[EAnchors.x0y2.ordinal()].getCenterX(), anchors[EAnchors.x0y2.ordinal()].getCenterY());
				break;
			// W
			case x0y1:
				p.setLocation(anchors[EAnchors.x2y1.ordinal()].getCenterX(), 0);
				break;
			// E
			case x2y1:
				p.setLocation(anchors[EAnchors.x0y1.ordinal()].getCenterX(), 0);
				break;
			// SW
			case x0y2:
				p.setLocation(anchors[EAnchors.x2y0.ordinal()].getCenterX(),
						anchors[EAnchors.x2y0.ordinal()].getCenterY());
				break;
			// S
			case x1y2:
				p.setLocation(0, anchors[EAnchors.x1y0.ordinal()].getCenterY());
				break;
			// SE
			case x2y2:
				p.setLocation(anchors[EAnchors.x0y0.ordinal()].getCenterX(),
						anchors[EAnchors.x0y0.ordinal()].getCenterY());
				break;
			default:
				break;
			}
		return p;
	}
	
	private Point2D computeResizeMethod() {

		double deltaW = 0;
		double deltaH = 0;
		
		switch (this.selectedShape.getEAnchors()) {
		// NW
		case x0y0:
			deltaW = -(cx - px);
			deltaH = -(cy - py);
			break;
		// N
		case x1y0:
			deltaW = 0;
			deltaH = -(cy - py);
			break;
		// NE
		case x2y0:
			deltaW = cx - px;
			deltaH = -(cy - py);
			break;
		// W
		case x0y1:
			deltaW = -(cx - px);
			deltaH = 0;
			break;
		// E
		case x2y1:
			deltaW = cx - px;
			deltaH = 0;
			break;
		// SW
		case x0y2:
			deltaW = -(cx - px);
			deltaH = cy - py;
			break;
		// S
		case x1y2:
			deltaW = 0;
			deltaH = cy - py;
			break;
		// SE
		case x2y2:
			deltaW = cx - px;
			deltaH = cy - py;
			break;
		default:
			break;
		}

		double xRatio = 1.0;
		double yRatio = 1.0;

		if (width > 0) {
			xRatio = (xRatio + (deltaW / width));
		}

		if (height > 0) {
			yRatio = (yRatio + (deltaH / height));
		}

		return new Point.Double(xRatio, yRatio);
	}

	public void transform(Graphics2D graphics2d, int x, int y) {
		this.cx = x;
		this.cy = y;

		Point2D resizeOrigin = getResizerOrigin();
		Point2D resizeRatio = computeResizeMethod();
		this.selectedShape.resize(resizeRatio, resizeOrigin, graphics2d, x, y);
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.px = x;
		this.py = y;

		this.width = this.selectedShape.getWidth();
		this.height = this.selectedShape.getHeight();
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.transform(graphics2d, x, y);
	}
}
