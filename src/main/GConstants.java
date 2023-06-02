package main;

import java.awt.Dimension;
import java.awt.event.InputEvent;

import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GRectangle;
import shapeTools.GPolygon;
import shapeTools.GShapeTool;

public class GConstants {
	public static class CFrame {
		public final static String title = "�Ѻ� �׸���";
		public final static String icon = "media/color-palette.png";
		public final static Dimension dimesion = new Dimension(610, 1000);
	}

	public static class CHelpFrame {
		public final static String title = "����";
		public final static String icon = "media/information.png";
		public final static Dimension dimesion = new Dimension(310, 310);
	}
	
	public static class CHelpPanel {
		public final static Dimension textDimesion = new Dimension(260, 260);
	}
	
	public enum EDrawingStyle {
		e2PointDrawing,
		eNPointDrawing
	};	
	public final static int wAnchor = 10;	
	public final static int hAnchor = 10;
	
	public enum EAction {
		eDraw,
		eMove,
		eResize,
		eRotate,
		eShear
	}
	
	public enum EShapeTool {
		eRectangle(new GRectangle(), "media/rectangle.png", "media/rectangle2.png"),
		eOval(new GOval(), "media/oval.png", "media/oval2.png"),
		eLine(new GLine(), "media/line.png", "media/line2.png"),
		ePolygon(new GPolygon(), "media/polygon.png", "media/polygon2.png");
		
		private GShapeTool shapeTool;
		private String normalImage;
		private String selectedImage;

		private EShapeTool(GShapeTool shapeTool, String normalImage, String selectedImage) {
			this.shapeTool = shapeTool;
			this.normalImage = normalImage;
			this.selectedImage = selectedImage;
		}
		public GShapeTool getShapeTool() {
			return this.shapeTool;
		}
		public String getNormalImage() {
			return this.normalImage;
		}
		public String getSelectedImage() {
			return this.selectedImage;
		}
	}
	
	public enum EMenu {
		eFile("����", 'F'),
		eEdit("����", 'E'),
		eHelp("����", 'H');
		
		private String text;
		private char mnemonic;
		
		private EMenu(String text, char mnemonic) {
			this.text = text;
			this.mnemonic = mnemonic;
		}
		public String getText() {
			return this.text;
		}
		public char getMnemonic() {
			return this.mnemonic;
		}
	}
	
	public enum EFileMenuItem {
		eNew("�� ����", false, 'N', InputEvent.CTRL_DOWN_MASK),
		eOpen("����", true, 'O', InputEvent.CTRL_DOWN_MASK),
		eSave("����", false, 'S', InputEvent.CTRL_DOWN_MASK),
		eSaveAs("�ٸ� �̸����� ����", true, 'S', InputEvent.CTRL_DOWN_MASK ^ InputEvent.SHIFT_DOWN_MASK),
		ePrint("����Ʈ", true, 'P', InputEvent.CTRL_DOWN_MASK),
		eExit("������", false, 'E', InputEvent.CTRL_DOWN_MASK);
		
		private String text;
		private boolean separator;
		private char accelerator;
		private int ctrlMask;
		
		private EFileMenuItem(String text, boolean separator, char accelerator, int ctrlMask) {
			this.text = text;
			this.separator = separator;
			this.accelerator = accelerator;
			this.ctrlMask = ctrlMask;
		}
		public String getText() {
			return this.text;
		}
		public boolean getSeparator() {
			return this.separator;
		}
		public char getAccelerator() {
			return this.accelerator;
		}
		public int getCtrlMask() {
			return this.ctrlMask;
		}
	}
	
	public enum EEditMenuItem {
		eUndo("�ڷ� ����", false, 'Z', InputEvent.CTRL_DOWN_MASK),
		eRedo("������ ����", true, 'Y', InputEvent.CTRL_DOWN_MASK),
		eCut("�߶󳻱�", false, 'X', InputEvent.CTRL_DOWN_MASK),
		eCopy("����", false, 'C', InputEvent.CTRL_DOWN_MASK),
		ePaste("�ٿ��ֱ�", true, 'V', InputEvent.CTRL_DOWN_MASK),
		eDelete("����", true, 'D', InputEvent.CTRL_DOWN_MASK),
		eFront("������ �̵�", false, 'F', InputEvent.SHIFT_DOWN_MASK),
		eBack("�ڷ� �̵�", false, 'B', InputEvent.SHIFT_DOWN_MASK),
		eFFront("�� ������ ��������", false, 'G', InputEvent.SHIFT_DOWN_MASK),
		eBBack("�� �ڷ� ������", false, 'N', InputEvent.SHIFT_DOWN_MASK);
		
		private String text;
		private boolean separator;
		private char accelerator;
		private int ctrlMask;
		
		private EEditMenuItem(String text, boolean separator, char accelerator, int ctrlMask) {
			this.text = text;
			this.separator = separator;
			this.accelerator = accelerator;
			this.ctrlMask = ctrlMask;
		}
		public String getText() {
			return this.text;
		}
		public boolean getSeparator() {
			return this.separator;
		}
		public char getAccelerator() {
			return this.accelerator;
		}
		public int getCtrlMask() {
			return this.ctrlMask;
		}
	}
	
	public enum EHelpMenuItem {
		eWhat("�Ѻ� �׸�����?"
				, "�Ѻ� �׸����� ������ ���� ������ �׸���, ������ �� �ֵ��� ���� ���α׷��Դϴ�.\n������ �׸��� �׸��� �����غ�����!"
				, "media/color-palette.png"
				, true),
		eShape("���� �׸���"
				, "�簢��, Ÿ��, ����, �ٰ��� ������ �̿��Ͽ� ����� ������ �׷�������!\n�簢��, Ÿ��, ������ �巡�׸� ���� �ٰ����� Ŭ���� ����Ŭ���� ���� �׸� �� �ֽ��ϴ�."
				, "media/shapes.png"
				, false),
		eAttribute("���� �Ӽ�"
				, "�Ӽ� ���ٿ��� ������, ä���, ��� ����� ������ ���⸦ �����غ�����!\n������ ������ �� �ֽ��ϴ�."
				, "media/color-wheel.png"
				, false),
		eTransform("���� ����"
				, "�׷��� ������ Ŭ���Ͽ� ������ �� ������ �������Ѻ�����!\n�����̰�, ũ�⸦ �����ϰ�, ȸ����ų �� �ֽ��ϴ�."
				, "media/transform.png"
				, false);
		
		private String title;
		private String text;
		private String image;
		private boolean separator;
		
		private EHelpMenuItem(String title, String text, String image, boolean separator) {
			this.title = title;
			this.text = text;
			this.image = image;
			this.separator = separator;
		}
		public String getTitle() {
			return this.title;
		}
		public String getText() {
			return this.text;
		}
		public String getImage() {
			return this.image;
		}
		public boolean getSeparator() {
			return this.separator;
		}
	}

	public static class CToolBar {
		public final static String thickness = "�β� ";
		public final static String colorImage = "media/color.png";
	}
	
	public enum EColorButton{
		eOutline("������ "),
		eFill("ä��� "),
		eBackground("��� ");

		private String text;
		
		private EColorButton(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
}
