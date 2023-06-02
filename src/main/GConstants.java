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
		public final static String title = "한별 그림판";
		public final static String icon = "media/color-palette.png";
		public final static Dimension dimesion = new Dimension(610, 1000);
	}

	public static class CHelpFrame {
		public final static String title = "도움말";
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
		eFile("파일", 'F'),
		eEdit("편집", 'E'),
		eHelp("도움말", 'H');
		
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
		eNew("새 파일", false, 'N', InputEvent.CTRL_DOWN_MASK),
		eOpen("열기", true, 'O', InputEvent.CTRL_DOWN_MASK),
		eSave("저장", false, 'S', InputEvent.CTRL_DOWN_MASK),
		eSaveAs("다른 이름으로 저장", true, 'S', InputEvent.CTRL_DOWN_MASK ^ InputEvent.SHIFT_DOWN_MASK),
		ePrint("프린트", true, 'P', InputEvent.CTRL_DOWN_MASK),
		eExit("나가기", false, 'E', InputEvent.CTRL_DOWN_MASK);
		
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
		eUndo("뒤로 가기", false, 'Z', InputEvent.CTRL_DOWN_MASK),
		eRedo("앞으로 가기", true, 'Y', InputEvent.CTRL_DOWN_MASK),
		eCut("잘라내기", false, 'X', InputEvent.CTRL_DOWN_MASK),
		eCopy("복사", false, 'C', InputEvent.CTRL_DOWN_MASK),
		ePaste("붙여넣기", true, 'V', InputEvent.CTRL_DOWN_MASK),
		eDelete("삭제", true, 'D', InputEvent.CTRL_DOWN_MASK),
		eFront("앞으로 이동", false, 'F', InputEvent.SHIFT_DOWN_MASK),
		eBack("뒤로 이동", false, 'B', InputEvent.SHIFT_DOWN_MASK),
		eFFront("맨 앞으로 가져오기", false, 'G', InputEvent.SHIFT_DOWN_MASK),
		eBBack("맨 뒤로 보내기", false, 'N', InputEvent.SHIFT_DOWN_MASK);
		
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
		eWhat("한별 그림판은?"
				, "한별 그림판은 누구나 쉽게 도형을 그리고, 변형할 수 있도록 돕는 프로그램입니다.\n나만의 그림을 그리고 저장해보세요!"
				, "media/color-palette.png"
				, true),
		eShape("도형 그리기"
				, "사각형, 타원, 선분, 다각형 도구를 이용하여 깔끔한 도형을 그려보세요!\n사각형, 타원, 선분은 드래그를 통해 다각형은 클릭과 더블클릭을 통해 그릴 수 있습니다."
				, "media/shapes.png"
				, false),
		eAttribute("도형 속성"
				, "속성 툴바에서 윤곽선, 채우기, 배경 색상과 윤곽선 굵기를 조정해보세요!\n언제나 수정할 수 있습니다."
				, "media/color-wheel.png"
				, false),
		eTransform("도형 변형"
				, "그려진 도형을 클릭하여 선택한 후 마음껏 변형시켜보세요!\n움직이고, 크기를 조절하고, 회전시킬 수 있습니다."
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
		public final static String thickness = "두께 ";
		public final static String colorImage = "media/color.png";
	}
	
	public enum EColorButton{
		eOutline("윤곽선 "),
		eFill("채우기 "),
		eBackground("배경 ");

		private String text;
		
		private EColorButton(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
}
