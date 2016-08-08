package test;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestShowImage {

	public static void main(String avg[]) throws IOException {
		File imageFile = new File("for_the_glory_of_satan.jpg");
		showImage(imageFile);
	}

	public static void showImage(File imageFile) throws IOException {
		BufferedImage image = ImageIO.read(imageFile);
		ImageIcon icon = new ImageIcon(image);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setLayout(new FlowLayout());
		JLabel label = new JLabel();
		label.setIcon(icon);
		frame.add(label);
		frame.setVisible(true);
	}

}
