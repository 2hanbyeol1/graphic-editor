package frame;

import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.GConstants.CHelpPanel;
import main.GConstants.EHelpMenuItem;

public class GHelpPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;
	private JLabel image;

	public GHelpPanel(EHelpMenuItem eHelpMenuItem) {
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
		ImageIcon imageIcon = this.toImageIcon(eHelpMenuItem.getImage());
		this.image = new JLabel(imageIcon);
		this.add(image);
		
		this.textArea = new JTextArea(eHelpMenuItem.getText());
		this.textArea.setSize(CHelpPanel.textDimesion);
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.textArea.setOpaque(false);
		this.textArea.setEditable(false);
		this.add(this.textArea);
	}
	
	private ImageIcon toImageIcon(String src) {
		return new ImageIcon(new ImageIcon(src).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
	}
}
