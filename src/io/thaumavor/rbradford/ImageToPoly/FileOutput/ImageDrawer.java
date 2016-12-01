package io.thaumavor.rbradford.ImageToPoly.FileOutput;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class ImageDrawer {

	public ImageDrawer(Color[] colors, Polygon[] polys, int width, int heigth) {
		BufferedImage toDraw = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = toDraw.createGraphics();
		if (polys != null) {
			for (int i = 0; i < polys.length; i++) {
				if (colors != null) {
					g2.setColor(colors[i]);
				} else {
					g2.setColor(new Color((int) (256 * Math.random()), (int) (256 * Math.random()),
							(int) (256 * Math.random())));
				}
				g2.fillPolygon(polys[i]);
			}
		}
		// fileChoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		try {
			EventQueue.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					JFileChooser fileChoose = new JFileChooser();
					fileChoose.showSaveDialog(null);
					File location = fileChoose.getSelectedFile();
					try {
						ImageIO.write(toDraw, "PNG", location);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
