package com.ryanb3.ImageToPoly;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.ryanb3.ImageToPoly.Display.Display;
import com.ryanb3.ImageToPoly.Processors.Processor;
import com.ryanb3.SelfUpdatingJava.Update;

public class Main {

	private Image toUse;
	private int width;
	private int height;
	Display toDisplay;
	String currentVersion = "1.01";

	public Main() throws InvocationTargetException, InterruptedException {
		try {
			new Update("http://rbradford.thaumavor.io/jars/ImageToPoly/", "ImageToPoly", "index.txt", currentVersion);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				JFileChooser fileChoose = new JFileChooser();
				fileChoose.showOpenDialog(null);
				File imageLocation = fileChoose.getSelectedFile();
				try {
					toUse = ImageIO.read(imageLocation);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		width = toUse.getWidth(null);
		height = toUse.getHeight(null);
		// initDisplay();
		Processor toProcess = new Processor(width, height, toUse, toDisplay);
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
