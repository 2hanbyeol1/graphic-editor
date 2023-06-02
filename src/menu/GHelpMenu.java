package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frame.GHelpFrame;
import main.GConstants.EHelpMenuItem;

public class GHelpMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private GHelpFrame[] helpFrames;

	public GHelpMenu(String text) {
		super(text);

		ActionHandler actionHandler = new ActionHandler();
		this.helpFrames = new GHelpFrame[EHelpMenuItem.values().length];

		for (EHelpMenuItem eHelpMenuItem : EHelpMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eHelpMenuItem.getTitle());
			menuItem.setActionCommand(eHelpMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
			if(eHelpMenuItem.getSeparator()) {
				this.addSeparator();
			}
			this.helpFrames[eHelpMenuItem.ordinal()] = new GHelpFrame(eHelpMenuItem);
		}
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int menuItem = EHelpMenuItem.valueOf(e.getActionCommand()).ordinal();
			helpFrames[menuItem].setVisible(true);
		}
	}
}
