package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import main.GConstants.CFrame;

public class GFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private GPanel panel;
	private GShapeToolBar shapeToolBar;
	private GAttributeToolBar attributeToolBar;
	private GMenuBar menuBar;
	private WindowHandler windowHandler;

	public GFrame() {
		// initialize attributes
		this.setTitle(CFrame.title);
		this.setSize(CFrame.dimesion);
		this.setLocationRelativeTo(null);
		Image image = new ImageIcon(CFrame.icon).getImage();
		this.setIconImage(image);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		// initialize components
		this.windowHandler = new WindowHandler();
		this.addWindowListener(this.windowHandler);

		BorderLayout layoutManager = new BorderLayout();
		this.getContentPane().setLayout(layoutManager);
		
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);

		this.shapeToolBar = new GShapeToolBar();
		this.getContentPane().add(this.shapeToolBar, BorderLayout.NORTH);
		
		this.attributeToolBar = new GAttributeToolBar(SwingConstants.VERTICAL);
		this.attributeToolBar.setMaximumSize(new Dimension(100, 300));
		this.attributeToolBar.setPreferredSize(new Dimension(100, 300));
		this.getContentPane().add(this.attributeToolBar, BorderLayout.EAST);

		this.panel = new GPanel();
		this.getContentPane().add(this.panel, BorderLayout.CENTER);

		// set associations
		this.menuBar.setAssociation(this.panel);
		this.shapeToolBar.setAssociation(this.panel);
		this.attributeToolBar.setAssociation(this.panel);
		this.panel.setAssociation(this.attributeToolBar);
	}

	public void initialize() {
		this.shapeToolBar.initialize();
		this.panel.initialize();
	}

	private class WindowHandler implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) { }

		@Override
		public void windowClosing(WindowEvent e) {
			menuBar.getFileMenu().exitProgram();
		}

		@Override
		public void windowClosed(WindowEvent e) { }

		@Override
		public void windowIconified(WindowEvent e) { }

		@Override
		public void windowDeiconified(WindowEvent e) { }

		@Override
		public void windowActivated(WindowEvent e) { }

		@Override
		public void windowDeactivated(WindowEvent e) { }
	}
}
