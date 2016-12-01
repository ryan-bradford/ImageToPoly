package io.thaumavor.rbradford.ImageToPoly.Processors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RandomPoints {

	ArrayList<Point> points; 
	double width;
	double height;
	double radius;
	double possibility = 1;
	double lowPossibility = .5;
	int colorDifference = 10;
	double toSubtract;
	BufferedImage image;
	
	public RandomPoints(int width, int height, int radius, Image image) {
		points = new ArrayList<Point>();
		this.width = width;
		this.height = height;
		this.radius = radius;
		this.image = toBufferedImage(image);
		toSubtract = (radius * radius) / (this.width * this.height);
		getColorPoints();
		//genRandomPoints();
	}
	
	public void getColorPoints() {
		int currentY = (int) (radius * Math.random());
		while(currentY < height) {
			genPointsForY(currentY);
			currentY += (int) (radius * Math.random());
		}
	}
	
	public void genPointsForY(int y) {
		Color lastColor = new Color(image.getRGB(0, y));
		for(int i = 1; i < width; i += radius * Math.random()) {
			Color thisColor = new Color(image.getRGB(i, y));
			if(colorDiff(lastColor, thisColor) > colorDifference) {
				Point toAdd = new Point(i, y);
				points.add(toAdd);
			}
			lastColor = thisColor;
		}
	}
	
	public void genRandomPoints() {
		while(possibility > lowPossibility) {
			addPoint();
		}
	}
	
	public void addPoint() {
		boolean added = false;
		int failedAttempts = 0;
		while(!added) {
			Point toAdd = new Point((int)(width * Math.random()), (int)(height * Math.random()));
			if(isPointValid(toAdd)) {
				points.add(toAdd);
				added = true;
				possibility -= toSubtract;
			} else {
				failedAttempts++;
			}
		}
	}
	
	public boolean isPointValid(Point toCheck) {
		for(int i = 0; i < points.size(); i++) {
			if(getDistanceFromPointToPoint(toCheck, points.get(i)) < radius) {
				return false;
			}
		}
		return true;
	}
	
	public double getDistanceFromPointToPoint(Point one, Point two) {
		double xDist = two.x - one.x;
		double yDist = two.y - one.y;
		double totalDist = Math.sqrt(xDist * xDist + yDist * yDist); 
		return totalDist;
		
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
	
	public int colorDiff(Color colorOne, Color colorTwo) {
		int diff = Math.abs(colorOne.getBlue() - colorTwo.getBlue());
		diff += Math.abs(colorOne.getGreen() - colorTwo.getGreen());
		diff += Math.abs(colorOne.getRed() - colorTwo.getRed());
		return diff;
		
	}
	
}
