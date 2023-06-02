package frame;

import javax.swing.JMenuBar;

import main.GConstants.EMenu;
import menu.GEditMenu;
import menu.GFileMenu;
import menu.GHelpMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GHelpMenu helpMenu;

	public GMenuBar() {
		for (EMenu eMenu : EMenu.values()) {
			String menuTitle = eMenu.getText() + " (" + eMenu.getMnemonic() + ")";
			if (eMenu.getText().equals("파일")) {
				this.fileMenu = new GFileMenu(menuTitle);
				this.add(this.fileMenu);
				this.fileMenu.setMnemonic(eMenu.getMnemonic());
			} else if (eMenu.getText().equals("편집")) {
				this.editMenu = new GEditMenu(menuTitle);
				this.add(this.editMenu);
				this.editMenu.setMnemonic(eMenu.getMnemonic());
			} else if (eMenu.getText().equals("도움말")) {
				this.helpMenu = new GHelpMenu(menuTitle);
				this.add(this.helpMenu);
				this.helpMenu.setMnemonic(eMenu.getMnemonic());
			}
		}
	}

	public void setAssociation(GPanel panel) {
		this.fileMenu.setAssociation(panel);
		this.editMenu.setAssociation(panel);
	}

	public GFileMenu getFileMenu() {
		return this.fileMenu;
	}
}
