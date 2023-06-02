package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Vector;

import javax.swing.JPanel;

import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;
import menu.GUndoStack;
import shapeTools.GShapeTool;
import transformer.GMover;
import transformer.GResize;
import transformer.GRotator;
import transformer.GTransformer;

public class GPanel extends JPanel implements Printable{
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private Vector<GShapeTool> shapes;
	private GMouseHandler mouseHandler;

	private GUndoStack undoStack;
	
	// associations
	private GAttributeToolBar attributeToolBar;

	// working objects
	private GShapeTool shapeTool;
	private GShapeTool selectedShape;
	private GShapeTool drawingShape;
	private GTransformer transformer;
	private boolean bModified;
	private Color selectedOutlineColor;
	private int selectedThickness;
	private Color selectedFillColor;
	private GShapeTool copiedShape;

	///////////////////////////////////////////////////////
	// getters and setters
	public Vector<GShapeTool> getShapes() {
		return this.shapes;
	}

	public Vector<GShapeTool> getUnselectedShapes() {
		for(GShapeTool shape : this.shapes) {
			shape.setSelected(false);
		}
		return this.shapes;
	}

	public void setAssociation(GAttributeToolBar attributeToolBar) {
		this.attributeToolBar = attributeToolBar;
	}

	public void setShapes(Vector<GShapeTool> shapes) {
		this.shapes = shapes;
		this.repaint();
	}

	public void setSelection(GShapeTool shapeTool) {
		this.shapeTool = shapeTool;
	}

	public boolean isModified() {
		return this.bModified;
	}

	public void setModified(boolean bModified) {
		this.bModified = bModified;
	}

	public void setOutLineColor(Color color) {
		this.selectedOutlineColor = color;
		if(selectedShape != null) {
			this.selectedShape.setOutLineColor(this.selectedOutlineColor);
			this.bModified = true;
			repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}
	
	public void setThickness(int thickness, boolean pushable) {
		this.selectedThickness = thickness;
		if(selectedShape != null) {
			this.selectedShape.setThickness(this.selectedThickness);
			this.bModified = true;
			repaint();
			if(pushable) {
				this.undoStack.push(this.deepCopy(this.shapes));
			}
		}
	}

	public void setFillColor(Color color) {
		this.selectedFillColor = color;
		if(selectedShape != null) {
			this.selectedShape.setFillColor(this.selectedFillColor);
			this.bModified = true;
			repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}

	// constructors
	public GPanel() {
		this.shapes = new Vector<GShapeTool>();

		this.mouseHandler = new GMouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.addMouseWheelListener(this.mouseHandler);

		this.undoStack = new GUndoStack();

		this.bModified = false;
		this.selectedOutlineColor = Color.BLACK;
		this.selectedThickness = 1;
		this.selectedFillColor = null;
	}

	public void initialize() {
		this.setBackground(Color.WHITE);
	}

	public void clearScreen() {
		this.shapes.clear();
		this.repaint();
		
		this.bModified = true;
		this.undoStack.push(this.deepCopy(this.shapes));
	}

	// methods

	@SuppressWarnings("unchecked")
	public Vector<GShapeTool> deepCopy(Vector<GShapeTool> original) {
		Vector<GShapeTool> clonedShapes = (Vector<GShapeTool>) original.clone();
		for (int i = 0; i < original.size(); i++) {
			GShapeTool clonedShape = (GShapeTool) original.get(i).clone();
			clonedShape.setSelected(false);
			clonedShapes.set(i, clonedShape);
		}
		return clonedShapes;
	}

	public void undo() {
		this.bModified = true;
		Vector<GShapeTool> undoShapes = this.undoStack.undo();
		if(undoShapes != null) {
			this.shapes = this.deepCopy(undoShapes);
			this.repaint();
		}
	}

	public void redo() {
		this.bModified = true;
		Vector<GShapeTool> redoShapes = this.undoStack.redo();
		if(redoShapes != null) {
			this.shapes = this.deepCopy(redoShapes);
			this.repaint();
		}
	}

	public void cut() {
		if(this.selectedShape != null) {
			this.bModified = true;
			this.selectedShape.setSelected(false);
			this.copiedShape = (GShapeTool) this.selectedShape.clone();
			this.shapes.removeElement(this.selectedShape);
			this.selectedShape = null;
			this.repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}

	public void copy() {
		if(this.selectedShape != null) {	
			this.selectedShape.setSelected(false);
			this.copiedShape = (GShapeTool) this.selectedShape.clone();
			this.selectedShape = null;
		}
	}

	public void paste() {
		if(this.copiedShape != null) {
			this.bModified = true;
			GShapeTool copiedShape = (GShapeTool) this.copiedShape.clone();
			this.setSelected(copiedShape);
			this.shapes.add(copiedShape);
			this.repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}

	public void delete() {
		if(this.selectedShape != null) {
			this.bModified = true;
			this.shapes.removeElement(this.selectedShape);
			this.setSelected(null);
			this.repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}

	public void front() {
		if(this.selectedShape != null && this.shapes.lastElement() != this.selectedShape) {
			this.bModified = true;
			int index1 = this.shapes.indexOf(this.selectedShape);
			this.swapShapes(index1, index1+1);
			this.repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}

	public void back() {
		if(this.selectedShape != null && this.shapes.firstElement() != this.selectedShape) {
			this.bModified = true;
			int index1 = this.shapes.indexOf(this.selectedShape);
			this.swapShapes(index1, index1-1);
			this.repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}
	
	public void swapShapes(int index1, int index2) {
		GShapeTool temp = this.shapes.get(index1);
		this.shapes.set(index1, this.shapes.get(index2));
		this.shapes.set(index2, temp);
	}

	public void ffront() {
		if(this.selectedShape != null && this.shapes.lastElement() != this.selectedShape) {
			this.bModified = true;
			this.shapes.removeElement(this.selectedShape);
			this.shapes.add(this.selectedShape);
			this.repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}

	public void bback() {
		if(this.selectedShape != null && this.shapes.firstElement() != this.selectedShape) {
			this.bModified = true;
			this.shapes.removeElement(this.selectedShape);
			this.shapes.add(0, this.selectedShape);
			this.repaint();
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);

		for (GShapeTool shape : this.shapes) {
			shape.draw((Graphics2D) graphics);
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		this.setSelected(null);
		this.repaint();
		if(pageIndex > 0) {
			return Printable.NO_SUCH_PAGE;
		}
		Graphics2D graphics2d = (Graphics2D) graphics;
		this.print(graphics2d);
        return Printable.PAGE_EXISTS;
	}

	private void setSelected(GShapeTool selectedShape) {
		for (GShapeTool shape : this.shapes) {
			shape.setSelected(false);
		}
		this.selectedShape = selectedShape;
		if(selectedShape != null) {
			this.selectedShape.setSelected(true);
			this.repaint();
			//Toolbar setting
			this.selectedOutlineColor = selectedShape.getOutLineColor();
			this.selectedThickness = selectedShape.getThickness();
			this.selectedFillColor = selectedShape.getFillColor();
			this.attributeToolBar.setToolBar(this.selectedOutlineColor, this.selectedThickness, this.selectedFillColor);
		}
	}

	private GShapeTool onShape(int x, int y) {
		for(int i = shapes.size() - 1; i >= 0; i--) {
			GShapeTool shape = shapes.get(i);
			EAction eAction = shape.contains(x, y);
			if (eAction != null) {
				return shape;
			}
		}
		return null;
	}

	private void initDrawing(int x, int y) {
		if(selectedShape != null) {
			this.setSelected(null);
		}
		this.drawingShape = (GShapeTool) this.shapeTool.clone();
		this.drawingShape.setOutLineColor(this.selectedOutlineColor);
		this.drawingShape.setThickness(this.selectedThickness);
		this.drawingShape.setFillColor(this.selectedFillColor);
		this.drawingShape.setInitPoint(x, y);
	}

	private void setIntermediatePoint(int x, int y) {
		this.drawingShape.setIntermediatePoint(x, y);
	}

	private void keepDrawing(int x, int y) {
		// exclusive or mode
		Graphics2D graphics2D = (Graphics2D) getGraphics();
		graphics2D.setXORMode(getBackground());
		// erase
		drawingShape.animate(graphics2D, x, y);
	}

	private void finishDrawing(int x, int y, boolean isDragged) {
		this.drawingShape.setFinalPoint(x, y);
		this.repaint();
		if(isDragged) {
			this.bModified = true;
			this.shapes.add(this.drawingShape);
			this.undoStack.push(this.deepCopy(this.shapes));
		}
	}

	private void initTransforming(GShapeTool selectedShape, int x, int y) {
		this.selectedShape = selectedShape;
		EAction eAction = this.selectedShape.getAction();
		switch (eAction) {
		case eMove:
			this.transformer = new GMover(this.selectedShape);
			break;
		case eResize:
			this.transformer = new GResize(this.selectedShape);
			break;
		case eRotate:
			this.transformer = new GRotator(this.selectedShape);
			break;
		default:
			break;
		}
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.initTransforming(graphics2d, x, y);
	}

	private void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.keepTransforming(graphics2d, x, y);
		this.repaint();
	}

	private void finishTransforming(int x, int y, boolean isDragged) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.finishTransforming(graphics2d, x, y);
		this.setSelected(this.selectedShape);
		if(isDragged) {
			this.undoStack.push(this.deepCopy(this.shapes));
			this.bModified = true;
		}
	}

	///////////////////////////////////////////////////////
	// inner classes
	private class GMouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

		private boolean isDrawing;
		private boolean isTransforming;
		private boolean saveStack;

		public GMouseHandler() {
			this.isDrawing = false;
			this.isTransforming = false;
			this.saveStack = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
				} else {
					initTransforming(selectedShape, e.getX(), e.getY());
					this.isTransforming = true;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			this.saveStack = true;
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					keepDrawing(e.getX(), e.getY());
				}
			} else if (this.isTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					finishDrawing(e.getX(), e.getY(), this.saveStack);
					this.isDrawing = false;
				}
			} else if (this.isTransforming) {
				finishTransforming(e.getX(), e.getY(), this.saveStack);
				this.isTransforming = false;
			}
			this.saveStack = false;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					keepDrawing(e.getX(), e.getY());
				}
			}
		}

		private void mouseLButton1Clicked(MouseEvent e) {
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
				} else {
					setSelected(selectedShape);
				}
			} else {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					setIntermediatePoint(e.getX(), e.getY());
				}
			}
		}

		private void mouseLButton2Clicked(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					finishDrawing(e.getX(), e.getY(), true);
					this.isDrawing = false;
				}
			}
		}

		private void mouseRButton1Clicked(MouseEvent e) {
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.mouseLButton1Clicked(e);
				} else if (e.getClickCount() == 2) {
					this.mouseLButton2Clicked(e);
				}
			} else if (e.getButton() == MouseEvent.BUTTON2) {
				if (e.getClickCount() == 1) {
					this.mouseRButton1Clicked(e);
				}
			}
		}
	}
}
