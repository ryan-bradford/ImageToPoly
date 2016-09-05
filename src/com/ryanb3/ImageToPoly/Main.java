package com.ryanb3.ImageToPoly;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.ryanb3.ImageToPoly.Display.Display;
import com.ryanb3.ImageToPoly.Processors.Processor;

public class Main {

	private Image toUse;
	private int width;
	private int height;
	Display toDisplay;
	
	public Main() {
		JFileChooser fileChoose = new JFileChooser();
		fileChoose.showOpenDialog(null);
		File imageLocation = fileChoose.getSelectedFile();
		try {
			toUse = ImageIO.read(imageLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		width = toUse.getWidth(null);
		height = toUse.getHeight(null);
		//initDisplay();
		Processor toProcess = new Processor(width, height, toUse, toDisplay, fileChoose);
	}
	
	public void initDisplay() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		toDisplay = new Display(toUse, width, height, screenSize.width, screenSize.height);
		toDisplay.setLayout(null);
		toDisplay.setBounds(0, 0, width, height);
		toDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		toDisplay.setVisible(true);
	}
	
}
