package frame;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.GConstants.EColorButton;

public class GColorButton extends JButton {
	private static final long serialVersionUID = 1L;
	private Color selectedColor;

	public GColorButton(ImageIcon imageIcon, EColorButton button, Color color) {
		this.setIcon(imageIcon);
		this.setActionCommand(button.name());
		this.setBackground(color);
	}
	
	public Color getSelectedColor() {
		return this.selectedColor;
	}

	public void setSelectedColor(Color color) {
		this.selectedColor = color;
		this.setBackground(color);
	}
}