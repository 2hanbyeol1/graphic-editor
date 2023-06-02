package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.EEditMenuItem;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	// component
	
	// association
	private GPanel panel;

	public GEditMenu(String text) {
		super(text);

		ActionHandler actionHandler = new ActionHandler();

		for (EEditMenuItem eEditMenuItem : EEditMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eEditMenuItem.getText());
			menuItem.setActionCommand(eEditMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
			char accelerator = eEditMenuItem.getAccelerator();
			menuItem.setAccelerator(KeyStroke.getKeyStroke 
	                (accelerator, eEditMenuItem.getCtrlMask()));
			if(eEditMenuItem.getSeparator()) {
				this.addSeparator();
			}
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EEditMenuItem eEditMenuItem = EEditMenuItem.valueOf(e.getActionCommand());
			switch (eEditMenuItem) {
			case eUndo:
				panel.undo();
				break;
			case eRedo:
				panel.redo();
				break;
			case eCut:
				panel.cut();
				break;
			case eCopy:
				panel.copy();
				break;
			case ePaste:
				panel.paste();
				break;
			case eDelete:
				panel.delete();
				break;
			case eFront:
				panel.front();
				break;
			case eBack:
				panel.back();
				break;
			case eFFront:
				panel.ffront();
				break;
			case eBBack:
				panel.bback();
				break;
			default:
				break;
			}
		}
	}
}
