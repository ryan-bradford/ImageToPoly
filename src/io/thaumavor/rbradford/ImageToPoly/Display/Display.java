package io.thaumavor.rbradford.ImageToPoly.Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Display extends JFrame {

	MainPanel mainPanel;
	JScrollPane scroll;
	
	public Display(Image toDisplay, int width, int height, int screenWidth, int screenHeight) {
		//this.setLayout(null);
		mainPanel = new MainPanel(toDisplay, width, height);
		mainPanel.setBounds(0, 0, screenWidth, screenHeight);
		mainPanel.setVisible(true);
		scroll = new JScrollPane(mainPanel);
		scroll.setAutoscrolls(true);
		scroll.setPreferredSize(new Dimension(width, height));
		scroll.setBounds(0, 0, screenWidth, screenHeight);
		scroll.setVisible(true);
		this.add(scroll);
	}
	
	public void displayPoints(Point[] points) {
		mainPanel.displayPoints(points);
	}
	
	public void displayPolys(Polygon[] polys) {
		mainPanel.displayPolys(polys);
	}
	
	public void displayPolys(Polygon[] polys, Color[] colors) {
		mainPanel.displayPolys(polys, colors);
	}
	
}
