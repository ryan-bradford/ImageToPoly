package io.thaumavor.rbradford.ImageToPoly.Display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel {

	Image toDisplay;
	double width;
	Point[] points;
	Polygon[] polys;
	Color[] colors;
	
	public MainPanel(Image toDisplay, int width, int height) {
		this.setLayout(null);
		this.toDisplay = toDisplay;
		this.width = width;
	}

	@Override
	protected void paintComponent(Graphics g) {
		//g.drawImage(toDisplay, 0, 0, null);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(4));
		if (points != null) {
			for (int i = 0; i < points.length; i++) {
				int x = (int) (points[i].x);
				int y = (int) (points[i].y);
				g2.drawLine(x, y, x, y);
			}
		}
		if(polys != null) {
			for(int i = 0; i < polys.length; i++) {
				if(colors != null) {
					g2.setColor(colors[i]);
				} else {
					g2.setColor(new Color((int)(256 * Math.random()), (int)(256 * Math.random()), (int)(256 * Math.random())));
				}
				//g2.setColor(Color.red);
				g2.fillPolygon(polys[i]);
			}
		}
	}

	public void displayPoints(Point[] points) {
		this.points = points;
		this.repaint();
	}
	
	public void displayPolys(Polygon[] polys) {
		this.polys = polys;
		this.repaint();
	}
	
	public void displayPolys(Polygon[] polys, Color[] colors) {
		this.polys = polys;
		this.colors = colors;
		this.repaint();
	}

}
