package menu;

import java.util.Vector;

import shapeTools.GShapeTool;

public class GUndoStack {

	static final int maxIndex = 10;
	private int bottomIndex;
	private int currentIndex;
	private int topIndex;

	private Vector<Vector<GShapeTool>> elements;

	public GUndoStack() {
		this.bottomIndex = 0;
		this.topIndex = 0;
		this.elements = new Vector<Vector<GShapeTool>>();
		for (int i = 0; i < maxIndex; i++) {
			this.elements.add(new Vector<GShapeTool>());
		}
	}

	public void push(Vector<GShapeTool> shapes) {
		this.topIndex = ++this.currentIndex;
		this.elements.set(this.topIndex % maxIndex, shapes);
		if (this.topIndex - this.bottomIndex == maxIndex) {
			this.bottomIndex++;
		}
	}

	public Vector<GShapeTool> undo() {
		Vector<GShapeTool> undoElement = null;
		if(bottomIndex < currentIndex && currentIndex <= topIndex) {
			this.currentIndex--;
			undoElement = this.elements.get((this.currentIndex + maxIndex) % maxIndex);
		}
		return undoElement;
	}

	public Vector<GShapeTool> redo() {
		Vector<GShapeTool> redoElement = null;
		if(bottomIndex <= currentIndex && currentIndex < topIndex) {
			this.currentIndex++;
			redoElement = this.elements.get((this.currentIndex) % maxIndex);
		}
		return redoElement;
	}

}
