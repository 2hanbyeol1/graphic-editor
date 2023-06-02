package frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.GConstants.CToolBar;
import main.GConstants.EColorButton;

public class GAttributeToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	private GPanel panel;
	
	private GColorButton outlineColorButton;
	private JSlider slider;
	private GColorButton fillColorButton;
	private GColorButton backgroundColorButton;
	
	private boolean setValue;

	public void setAssociation(GPanel panel) {
		this.panel = panel;		
	}
	
	public void setToolBar(Color outlineColor, int thickness, Color fillColor) {
		this.setValue = true;
		this.outlineColorButton.setSelectedColor(outlineColor);
		this.slider.setValue(thickness);
		this.fillColorButton.setSelectedColor(fillColor);
	}
	
	public GAttributeToolBar(int vertical) {
		super(vertical);
		
		this.setValue = false;
		
		ColorActionHandler colorActionHandler = new ColorActionHandler();
	
		JLabel outline = new JLabel(EColorButton.eOutline.getText());
		this.add(outline);
		this.outlineColorButton = new GColorButton(toImageIcon(CToolBar.colorImage), EColorButton.eOutline, Color.BLACK);
		this.outlineColorButton.addActionListener(colorActionHandler);
		this.add(this.outlineColorButton);

		this.addSeparator();
		
		ChangeHandler changeHandler = new ChangeHandler();

		JLabel thickness = new JLabel(CToolBar.thickness);
		this.add(thickness);
		this.slider = new JSlider(1, 20, 1);
		this.slider.setMinorTickSpacing(1);
		this.slider.setMaximumSize(new Dimension(130, 30));
		this.slider.addChangeListener(changeHandler);
		this.add(this.slider);
		
		this.addSeparator();

		JLabel fill = new JLabel(EColorButton.eFill.getText());
		this.add(fill);
		this.fillColorButton = new GColorButton(toImageIcon(CToolBar.colorImage), EColorButton.eFill, Color.WHITE);
		this.fillColorButton.addActionListener(colorActionHandler);
		this.add(this.fillColorButton);

		this.addSeparator();
		
		JLabel background = new JLabel(EColorButton.eBackground.getText());
		this.add(background);
		this.backgroundColorButton = new GColorButton(toImageIcon(CToolBar.colorImage), EColorButton.eBackground, Color.WHITE);
		this.backgroundColorButton.addActionListener(colorActionHandler);
		this.add(this.backgroundColorButton);
	}
	
	private ImageIcon toImageIcon(String src) {
		Image image = new ImageIcon(src).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
	
	public void initialize() { }
	
	private class ChangeHandler implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider) e.getSource();
			int thickness = (int) slider.getValue();
			panel.setThickness(thickness, !slider.getValueIsAdjusting() && !setValue);
			setValue = false;
		}
	}
	
	private class ColorActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			EColorButton eColorButton = EColorButton.valueOf(event.getActionCommand());
			switch (eColorButton) {
			case eOutline:
				setOutLineColor();
				break;
			case eFill:
				setFillColor();
				break;
			case eBackground:
				setBackgroundColor();
				break;
			default:
				break;
			}
		}

		private void setOutLineColor() {
			Color color = JColorChooser.showDialog(panel, "Outline Color", outlineColorButton.getSelectedColor());
			if(color != null) {
				outlineColorButton.setSelectedColor(color);
				panel.setOutLineColor(color);
			}
		}

		private void setFillColor() {
			Color color = JColorChooser.showDialog(panel, "Fill Color", fillColorButton.getSelectedColor());
			if(color != null) {
				fillColorButton.setSelectedColor(color);
				panel.setFillColor(color);
			}
		}

		private void setBackgroundColor() {
			Color color = JColorChooser.showDialog(panel, "Background Color", backgroundColorButton.getSelectedColor());
			if(color != null) {
				backgroundColorButton.setSelectedColor(color);
				panel.setBackground(color);
			}
		}
	}
}
