package com.ryanb3.ImageToPoly.Processors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ColorAssigner {

	ArrayList<Color> colors;
	ArrayList<Polygon> polys;
	BufferedImage image;

	public ColorAssigner(ArrayList<Polygon> polys, Image image) {
		this.image = toBufferedImage(image);
		this.polys = polys;
		colors = new ArrayList<Color>();
		for(int i = 0; i < polys.size(); i++) {
			genColorForPoly(i);
		}
	}

	public ArrayList<Point> genPointsInPoly(Polygon poly) {
		ArrayList<Point> points = new ArrayList<Point>();
		Rectangle bounding = poly.getBoundingBox();
		for (int x = bounding.x; x < bounding.width + bounding.x; x++) {
			for (int y = bounding.y; y < bounding.height + bounding.y; y++) {
				points.add(new Point(x, y));
			}
		}
		return points;
	}

	public void genColorForPoly(int x) {
		ArrayList<Point> points = genPointsInPoly(polys.get(x));
		double totalRed = 0;
		double totalBlue = 0;
		double totalGreen = 0;
		for (Point point : points) {
			int[] colors = getPixelData(image, point.x, point.y);
			totalRed += colors[0];
			totalGreen += colors[1];
			totalBlue += colors[2];
		}
		totalRed /= points.size();
		totalGreen /= points.size();
		totalBlue /= points.size();
		colors.add(new Color(0,0,0));
		colors.set(x, new Color((int)totalRed, (int)totalGreen, (int)totalBlue));
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);
		int rgb[] = new int[] { (argb >> 16) & 0xff, // red
				(argb >> 8) & 0xff, // green
				(argb) & 0xff // blue
		};

		return rgb;
	}

}
