package main;

import frame.GFrame;

public class GMain {

	private static GFrame frame;
	
	public static void main(String[] args) {
		frame = new GFrame();
		frame.initialize();
		
		frame.setVisible(true);
	}
}
