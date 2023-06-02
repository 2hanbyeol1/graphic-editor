package frame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import main.GConstants.EShapeTool;

public class GShapeToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = 1L;

	// associations
	private GPanel panel;
	
	public GShapeToolBar() {
		// initialize components
		ShapeActionHandler shapeActionHandler = new ShapeActionHandler();
		
		for (EShapeTool eButton: EShapeTool.values()) {
			JButton button = new JButton(toImageIcon(eButton.getNormalImage()));
			button.setSelectedIcon(toImageIcon(eButton.getSelectedImage()));
			button.setActionCommand(eButton.toString());
			button.addActionListener(shapeActionHandler);
			this.add(button);
		}
	}
	
	private ImageIcon toImageIcon(String src) {
		Image image = new ImageIcon(src).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
	
	public void initialize() {
		((JButton)(this.getComponent(EShapeTool.eRectangle.ordinal()))).doClick();
	}
	
	public void setAssociation(GPanel panel) {
		this.panel = panel;		
	}
	
	private class ShapeActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			EShapeTool eShapeTool = EShapeTool.valueOf(event.getActionCommand());
			for(int i = 0; i < EShapeTool.values().length; i++) {
				((JButton) getComponent(i)).setSelected(false);
			}
			JButton selectedBtn = (JButton) event.getSource();
			selectedBtn.setSelected(true);
			panel.setSelection(eShapeTool.getShapeTool());
		}		
	}
}
