package frame;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import main.GConstants.CHelpFrame;
import main.GConstants.EHelpMenuItem;

public class GHelpFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private GHelpPanel helpPanel;
	
	public GHelpFrame(EHelpMenuItem eHelpMenuItem) {
		// initialize attributes
		this.setTitle(CHelpFrame.title);
		this.setSize(CHelpFrame.dimesion);
		this.setLocationRelativeTo(null);
		Image image = new ImageIcon(CHelpFrame.icon).getImage();
		this.setIconImage(image);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		BorderLayout layoutManager = new BorderLayout();
		this.getContentPane().setLayout(layoutManager);

		this.helpPanel = new GHelpPanel(eHelpMenuItem);
		this.getContentPane().add(this.helpPanel, BorderLayout.CENTER);
	}
}
