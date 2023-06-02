package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import frame.GPanel;
import main.GConstants.EFileMenuItem;
import shapeTools.GShapeTool;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private File filePath;
	private File file;
	// components

	// associations
	private GPanel panel;

	public GFileMenu(String text) {
		super(text);
		
		ActionHandler actionHandler = new ActionHandler();
		
		for (EFileMenuItem eFileMenuItem : EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eFileMenuItem.getText());
			menuItem.setActionCommand(eFileMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
			menuItem.setAccelerator(KeyStroke.getKeyStroke 
	                (eFileMenuItem.getAccelerator(), eFileMenuItem.getCtrlMask()));
			if(eFileMenuItem.getSeparator()) {
				this.addSeparator();
			}
		}
		this.filePath = null;
		this.file = null;
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	@SuppressWarnings("unchecked")
	private void openFile() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(this.file)));
			Vector<GShapeTool> shapes = (Vector<GShapeTool>) objectInputStream.readObject();
			this.panel.setShapes(shapes);
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void saveFile() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(this.file)));
			objectOutputStream.writeObject(this.panel.getUnselectedShapes());
			objectOutputStream.close();
			this.panel.setModified(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkSaveOrNot() {
		boolean bCancel = true;
		if (this.panel.isModified()) {
			//save
			int reply = JOptionPane.showConfirmDialog(this.panel, "변경내용을 저장 할까요?");
			if (reply == JOptionPane.OK_OPTION) {
				this.panel.setModified(false);
				this.save();
				bCancel = false;
			} else if (reply == JOptionPane.NO_OPTION) {
				this.panel.setModified(false);
				bCancel = false;
			} // else if (CANCEL_OPTION) // else : x버튼
		} else {
			bCancel = false;
		}
		return bCancel;
	}

	private void nnew() {
		if(!checkSaveOrNot()) {
			this.panel.clearScreen();
			this.file = null;
		}
	}

	private void open() {
		if(!checkSaveOrNot()) { // if not cancel
			JFileChooser chooser = new JFileChooser(this.filePath);
			chooser.setSelectedFile(file);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics", "gra");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this.panel);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				this.filePath = chooser.getCurrentDirectory();
				this.file = chooser.getSelectedFile();
				String fileName = this.file.getName();
				if (fileName.toLowerCase().endsWith(".gra")) {
					this.openFile();
				} else {
					JOptionPane.showMessageDialog(this.panel, "지원되지 않는 파일 형식입니다.","ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
			} // else {cancel}
		}
	}

	private void save() {
		if (this.panel.isModified()) {
			if(this.file == null) {
				this.saveAs();
			} else {
				this.saveFile();
			}
		}
	}

	private void saveAs() {
		JFileChooser chooser = new JFileChooser(this.filePath);
		int returnVal = chooser.showSaveDialog(this.panel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.filePath = chooser.getCurrentDirectory();
			this.file = chooser.getSelectedFile();
			String fileName = this.file.getName();
			if (!fileName.toLowerCase().endsWith(".gra")) {
				this.file = new File(this.file.getParent(), fileName + ".gra");
			}
			this.saveFile();
		} // else {cancel}
	}
	
	private void print() {
		if(!checkSaveOrNot()) {
			PrinterJob printJob = PrinterJob.getPrinterJob();
		    printJob.setPrintable(this.panel);
		    if (printJob.printDialog()) {
		        try {
		            printJob.print();
		        } catch (Exception ex) {
		            throw new RuntimeException(ex);
		        }
		    }
		}
	}
	
	public void exitProgram() {
		if(!checkSaveOrNot()) {
			System.exit(0);
		}
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenuItem eMenuItem = EFileMenuItem.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eNew:
				nnew();
				break;
			case eOpen:
				open();
				break;
			case eSave:
				save();
				break;
			case eSaveAs:
				saveAs();
				break;
			case ePrint:
				print();
				break;
			case eExit:
				exitProgram();
				break;
			default:
				break;
			}
		}
	}
}
