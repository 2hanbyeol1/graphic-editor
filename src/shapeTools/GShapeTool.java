package shapeTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

import main.GConstants;
import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;

abstract public class GShapeTool implements Serializable, Cloneable {
	// attributes
	private static final long serialVersionUID = 1L;

	public enum EAnchors {
		x0y0, x0y1, x0y2, x1y0, x1y2, x2y0, x2y1, x2y2, RR
	}

	private EDrawingStyle eDrawingStyle;
	protected Shape shape;
	private Ellipse2D[] anchors;
	private boolean isSelected;
	private EAnchors selectedAnchor;
	private EAction eAction;
	private AffineTransform moveTransform;
	private AffineTransform resizeTransform;
	private AffineTransform rotateTransform;
	private Color outlineColor;
	private int thickness;
	private Color fillColor;

	// working variables

	// constructors
	public GShapeTool(EDrawingStyle eDrawingState) {
		this.anchors = new Ellipse2D.Double[EAnchors.values().length];
		for (EAnchors eAnchor : EAnchors.values()) {
			this.anchors[eAnchor.ordinal()] = new Ellipse2D.Double();
		}
		this.isSelected = false;
		this.eDrawingStyle = eDrawingState;
		this.selectedAnchor = null;

		this.moveTransform = new AffineTransform();
		this.moveTransform.setToIdentity();
		this.resizeTransform = new AffineTransform();
		this.resizeTransform.setToIdentity();
		this.rotateTransform = new AffineTransform();
		this.rotateTransform.setToIdentity();
		
		this.outlineColor = Color.BLACK;
		this.thickness = 1;
		this.fillColor = null;
	}

	public Object clone() {
		GShapeTool cloned = null;
		try {
			cloned = (GShapeTool) super.clone();
			for (EAnchors eAnchor : EAnchors.values()) {
				cloned.anchors[eAnchor.ordinal()] = (Ellipse2D) this.anchors[eAnchor.ordinal()].clone();
			}
			cloned.moveTransform = (AffineTransform) this.moveTransform.clone();
			cloned.resizeTransform = (AffineTransform) this.resizeTransform.clone();
			cloned.rotateTransform = (AffineTransform) this.rotateTransform.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return cloned;
	}

	// getters & setters
	public EDrawingStyle getDrawingStyle() {
		return this.eDrawingStyle;
	}

	public EAction getAction() {
		return this.eAction;
	}

	public EAnchors getEAnchors() {
		return this.selectedAnchor;
	}

	public double getWidth() {
		return this.resizeTransform.createTransformedShape(this.shape).getBounds().getWidth();
	}

	public double getHeight() {
		return this.resizeTransform.createTransformedShape(this.shape).getBounds().getHeight();
	}

	public Ellipse2D[] getAnchors() {
		return anchors;
	}

	public boolean getSelected() {
		return this.isSelected;
	}
	
	public Color getOutLineColor() {
		return this.outlineColor;
	}
	
	public int getThickness() {
		return this.thickness;
	}
	
	public Color getFillColor() {
		return this.fillColor;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public void setOutLineColor(Color color) {
		this.outlineColor = color;
	}
	
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	
	public void setFillColor(Color color) {
		this.fillColor = color;
	}
	
	public Shape getTransformedShape() {
		return this.moveTransform.createTransformedShape(
				this.rotateTransform.createTransformedShape(
						this.resizeTransform.createTransformedShape(this.shape)));
	}
	
	public Shape getMoveRotateAnchor(Ellipse2D anchor) {
		return this.moveTransform.createTransformedShape(
				this.rotateTransform.createTransformedShape(anchor));
	}

	// methods
	public EAction contains(int x, int y) {
		this.eAction = null;
		if (this.isSelected) {
			for (int i = 0; i < this.anchors.length - 1; i++) {
				if (this.getMoveRotateAnchor(anchors[i]).contains(x, y)) {
					this.selectedAnchor = EAnchors.values()[i];
					this.eAction = EAction.eResize;
				}
			}
			if (this.getMoveRotateAnchor(this.anchors[EAnchors.RR.ordinal()]).contains(x, y)) {
				this.eAction = EAction.eRotate;
			}
		}
		if (getTransformedShape().contains(x, y)) {
			this.eAction = EAction.eMove;
		}
		return this.eAction;
	}

	public void move(Graphics2D graphics2d, int dx, int dy) {
		int tempX = dx;
		int tempY = dy;

		this.draw(graphics2d);
		this.moveTransform.translate(tempX, tempY);
		this.draw(graphics2d);
	}
	
	public void resize(Point2D resizeRatio, Point2D resizeOrigin, Graphics2D graphics2d, int x, int y) {
		this.draw(graphics2d);

		this.resizeTransform.setToTranslation(resizeOrigin.getX(), resizeOrigin.getY());
		this.resizeTransform.scale(resizeRatio.getX(), resizeRatio.getY());
		this.resizeTransform.translate(-resizeOrigin.getX(), -resizeOrigin.getY());
		
		this.draw(graphics2d);
	}

	public void rotate(Graphics2D graphics2d, Point pStart, Point pEnd) {
		this.draw(graphics2d);
		double centerX = this.resizeTransform.createTransformedShape(this.shape).getBounds().getCenterX();
		double centerY = this.resizeTransform.createTransformedShape(this.shape).getBounds().getCenterY();
		double transformedCenterX = this.moveTransform.createTransformedShape(
				this.resizeTransform.createTransformedShape(this.shape)).getBounds().getCenterX();
		double transformedCenterY = this.moveTransform.createTransformedShape(
				this.resizeTransform.createTransformedShape(this.shape)).getBounds().getCenterY();

		double startAngle = Math.toDegrees(Math.atan2(transformedCenterX - pStart.x, transformedCenterY - pStart.y));
		double endAngle = Math.toDegrees(Math.atan2(transformedCenterX - pEnd.x, transformedCenterY - pEnd.y));

		double rotationAngle = startAngle - endAngle;
		if (rotationAngle < 0) {
			rotationAngle += 360;
		}
		this.rotateTransform.rotate(
				Math.toRadians(rotationAngle), centerX, centerY);
		
		this.draw(graphics2d);
	}

	public void drawAnchors(Graphics2D graphics2d) {
		int wAnchor = GConstants.wAnchor;
		int hAnchor = GConstants.hAnchor;

		Rectangle rectangle = this.resizeTransform.createTransformedShape(this.shape).getBounds();
		int x0 = rectangle.x - wAnchor / 2;
		int x1 = rectangle.x - wAnchor / 2 + (rectangle.width) / 2;
		int x2 = rectangle.x - wAnchor / 2 + rectangle.width;
		int y0 = rectangle.y - hAnchor / 2;
		int y1 = rectangle.y - hAnchor / 2 + (rectangle.height) / 2;
		int y2 = rectangle.y - hAnchor / 2 + rectangle.height;

		this.anchors[EAnchors.x0y0.ordinal()].setFrame(x0, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x0y1.ordinal()].setFrame(x0, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.x0y2.ordinal()].setFrame(x0, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.x1y0.ordinal()].setFrame(x1, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x1y2.ordinal()].setFrame(x1, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y0.ordinal()].setFrame(x2, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y1.ordinal()].setFrame(x2, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y2.ordinal()].setFrame(x2, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.RR.ordinal()].setFrame(x1, y0 - 40, wAnchor, hAnchor);

		for (EAnchors eAnchor : EAnchors.values()) {
			graphics2d.setStroke(new BasicStroke(1));
			graphics2d.setColor(Color.WHITE);
			graphics2d.fill(this.getMoveRotateAnchor(this.anchors[eAnchor.ordinal()]));
			graphics2d.setColor(Color.BLACK);
			graphics2d.draw(this.getMoveRotateAnchor(this.anchors[eAnchor.ordinal()]));
		}
	}

	public void draw(Graphics2D graphics2d) {
		graphics2d.setStroke(new BasicStroke(this.thickness));
		if(this.fillColor != null) {
			graphics2d.setColor(this.fillColor);
			graphics2d.fill(this.getTransformedShape());
		}
		graphics2d.setColor(this.outlineColor);
		graphics2d.draw(this.getTransformedShape());
		if (isSelected) {
			this.drawAnchors(graphics2d);
		}
	}

	public void animate(Graphics2D graphics2d, int x, int y) {
		this.draw(graphics2d);
		this.movePoint(x, y);
		this.draw(graphics2d);
	}

	// interface
	public abstract void setInitPoint(int x, int y);
	public void setIntermediatePoint(int x, int y) { }
	public abstract void setFinalPoint(int x, int y);
	public abstract void movePoint(int x, int y);

}
