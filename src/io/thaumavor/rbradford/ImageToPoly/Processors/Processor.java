package io.thaumavor.rbradford.ImageToPoly.Processors;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JFileChooser;

import io.thaumavor.rbradford.ImageToPoly.Display.Display;
import io.thaumavor.rbradford.ImageToPoly.FileOutput.ImageDrawer;

public class Processor {

	Point[] points;
	Color[] colors;
	Polygon[] polys;
	int radius = 15;
	
	public Processor(int width, int height, Image toUse, Display testDisplay) {
		RandomPoints pointGen = new RandomPoints(width, height, radius, toUse);
		PolyChooser choose = new PolyChooser(pointGen.points, radius, width, height);
		ColorAssigner colorAssign = new ColorAssigner(choose.polys, toUse);
		points = (Point[]) pointGen.points.toArray(new Point[0]);
		polys = (Polygon[]) choose.polys.toArray(new Polygon[0]);
		colors = (Color[]) colorAssign.colors.toArray(new Color[0]);
		if(testDisplay != null) {
			testDisplay.displayPoints(points);
			testDisplay.displayPolys(polys, colors);
			testDisplay.repaint();
		}
		ImageDrawer draw = new ImageDrawer(colors, polys, width, height);
	}
	
	
	
}
