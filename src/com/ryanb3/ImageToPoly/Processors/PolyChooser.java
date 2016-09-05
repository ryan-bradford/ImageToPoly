package com.ryanb3.ImageToPoly.Processors;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

public class PolyChooser {

	ArrayList<ArrayList<Point>> collumns;
	ArrayList<Point> points;
	int radius;
	int width;
	int height;
	ArrayList<Polygon> polys;
	int coverWidth = 50;

	public PolyChooser(ArrayList<Point> points, int radius, int width, int height) {
		this.points = points;
		this.radius = radius;
		this.width = width;
		this.height = height;
		polys = new ArrayList<Polygon>();
		collumns = new ArrayList<ArrayList<Point>>();
		int numCols = (int) Math.ceil((double) width / (double) radius);
		for (int i = 0; i < numCols; i++) {
			collumns.add(new ArrayList<Point>());
		}
		sortIntoCollums();
		genBasePolys();
		for(int i = 0; i < collumns.size(); i++) {
			generatePolys(i);
		}
	}

	public void sortIntoCollums() {
		for (Point x : points) {
			int index = (int) Math.floor(x.x / radius);
			boolean added = false;
			for (int y = 0; y < collumns.get(index).size(); y++) {
				if (collumns.get(index).get(y).y > x.y) {
					collumns.get(index).add(y, x);
					added = true;
					break;
				}
			}
			if (!added) {
				collumns.get(index).add(x);
			}
		}
	}

	public void generatePolys(int collumnIndex) {
		if (collumnIndex == 0) {
			generatePolysForColZero();
		} else if (collumnIndex == collumns.size() - 1) {
			generatePolysForColX(collumnIndex);
		} else {
			generatePolysForColX(collumnIndex);
		}
	}

	public void generatePolysForColX(int x) {
		ArrayList<Point> current = collumns.get(x);
		ArrayList<Point> last = collumns.get(x - 1);
		for (int i = 0; i < current.size() + 1; i++) {
			Polygon toAdd = new Polygon();
			try {
				if (i == 0) {
					toAdd.addPoint(last.get(0).x, 0);
					toAdd.addPoint(last.get(0).x, last.get(0).y);
					toAdd.addPoint(current.get(i).x, current.get(i).y);
					toAdd.addPoint(current.get(i).x, 0);
					polys.add(toAdd);
				}else {
					toAdd.addPoint(last.get(i).x, last.get(i).y);
					toAdd.addPoint(current.get(i).x, current.get(i).y);
					toAdd.addPoint(current.get(i - 1).x, current.get(i - 1).y);
					toAdd.addPoint(last.get(i - 1).x, last.get(i - 1).y);
					polys.add(toAdd);
				}
			} catch (IndexOutOfBoundsException ex) {
				toAdd.addPoint(current.get(i - 1).x, height);
				toAdd.addPoint(current.get(i - 1).x, current.get(i - 1).y);
				for(int z = current.size() - 2; z >= last.size(); z--) {
					toAdd.addPoint(current.get(z).x, current.get(z).y);
				}
				toAdd.addPoint(last.get(last.size() - 1).x, current.get(i - 1).y);
				toAdd.addPoint(last.get(last.size() - 1).x, last.get(last.size() - 1).y);
				toAdd.addPoint(last.get(last.size() - 1).x, height);
				polys.add(toAdd);
				break;
			}
		}
	}

	public void generatePolysForColZero() {
		ArrayList<Point> current = collumns.get(0);
		for (int i = 0; i < current.size() + 1; i++) {
			Polygon toAdd = new Polygon();
			if (i == 0 && i != current.size()) {
				toAdd.addPoint(0, 0);
				toAdd.addPoint(0, current.get(i).y);
				toAdd.addPoint(current.get(i).x, current.get(i).y);
				toAdd.addPoint(current.get(i).x, 0);
				polys.add(toAdd);
			} else if (i == current.size()) {
				toAdd.addPoint(0, height);
				toAdd.addPoint(0, current.get(i-1).y);
				toAdd.addPoint(current.get(i-1).x, current.get(i-1).y);
				toAdd.addPoint(current.get(i-1).x, height);
				polys.add(toAdd);
			} else {
				toAdd.addPoint(0, current.get(i).y);
				toAdd.addPoint(current.get(i).x, current.get(i).y);
				toAdd.addPoint(current.get(i - 1).x, current.get(i - 1).y);
				toAdd.addPoint(0, current.get(i - 1).y);
				polys.add(toAdd);
			}
		}
	}
	
	public void genBasePolys() {
		int heightCount = (int) Math.ceil(new Double(height) / new Double(coverWidth));
		int widthCount = (int) Math.ceil(new Double(width) / new Double(coverWidth));
		for(int x = 0; x < widthCount; x++) {
			for(int y = 0; y < heightCount; y++) {
				Polygon toAdd = new Polygon();
				if(x == widthCount - 1 && y == heightCount - 1) {
					toAdd.addPoint(x * coverWidth, y * coverWidth);
					toAdd.addPoint(width, y * coverWidth);
					toAdd.addPoint(width, height);
					toAdd.addPoint(x * coverWidth, height);
					polys.add(toAdd);
				} else if(y == heightCount - 1) {
					toAdd.addPoint(x * coverWidth, y * coverWidth);
					toAdd.addPoint((x + 1) * coverWidth, y * coverWidth);
					toAdd.addPoint((x + 1) * coverWidth, height);
					toAdd.addPoint(x * coverWidth, height);
					polys.add(toAdd);
				} else if(x == widthCount - 1) {
					toAdd.addPoint(x * coverWidth, y * coverWidth);
					toAdd.addPoint(width, y * coverWidth);
					toAdd.addPoint(width, (y+1) * coverWidth);
					toAdd.addPoint(x * coverWidth, (y+1) * coverWidth);
					polys.add(toAdd);
				} else {
					toAdd.addPoint(x * coverWidth, y * coverWidth);
					toAdd.addPoint((x + 1) * coverWidth, y * coverWidth);
					toAdd.addPoint((x + 1) * coverWidth, (y+1) * coverWidth);
					toAdd.addPoint(x * coverWidth, (y+1) * coverWidth);
					polys.add(toAdd);
				}
			}
		}
	}

}
