package java.awt.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import jreframeworker.annotations.fields.DefineField;
import jreframeworker.annotations.methods.DefineMethod;
import jreframeworker.annotations.methods.MergeMethod;
import jreframeworker.annotations.types.MergeType;

@MergeType
public class PixelatedBufferedImage extends BufferedImage {

	public PixelatedBufferedImage(int arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
	}
	
	@DefineField
	boolean pixelated = false;
	
	@MergeMethod
	@Override
	public Graphics getGraphics() {
		if(!pixelated) pixelate();
		return super.getGraphics();
	}

	@MergeMethod
	@Override
	public Object getProperty(String property, ImageObserver observer) {
		if(!pixelated) pixelate();
		return super.getProperty(property, observer);
	}

	@MergeMethod
	@Override
	public ImageProducer getSource() {
		if(!pixelated) pixelate();
		return super.getSource();
	}

	@MergeMethod
	@Override
	public int getWidth(ImageObserver observer) {
		if(!pixelated) pixelate();
		return super.getWidth(observer);
	}
	
	@MergeMethod
	@Override
	public int getHeight(ImageObserver observer) {
		if(!pixelated) pixelate();
		return super.getHeight(observer);
	}

	@DefineMethod
	private void pixelate() {
		// pixleation algorithm credit:
		// https://stackoverflow.com/questions/15777821/how-can-i-pixelate-a-jpg-with-java
		
		// set the pixel size
		final int PIXEL_SIZE = 5;
		
		Raster data = super.getData();

		// create an identically-sized output raster
		WritableRaster pixelatedRaster = data.createCompatibleWritableRaster();

		// loop through every PIXEL_SIZE pixels, in both x and y directions
		for(int y = 0; y < data.getHeight(); y += PIXEL_SIZE) {
		    for(int x = 0; x < data.getWidth(); x += PIXEL_SIZE) {
		        // copy the pixel
		        double[] pixel = new double[3];
		        pixel = data.getPixel(x, y, pixel);
		        // "paste" the pixel onto the surrounding PIXEL_SIZE by PIXEL_SIZE neighbors
		        // and make sure that our loop never goes outside the bounds of the image
		        for(int yd = y; (yd < y + PIXEL_SIZE) && (yd < pixelatedRaster.getHeight()); yd++) {
		            for(int xd = x; (xd < x + PIXEL_SIZE) && (xd < pixelatedRaster.getWidth()); xd++) {
		            	pixelatedRaster.setPixel(xd, yd, pixel);
		            }
		        }
		    }
		}

		// overwrite the image data with the pixelated image
		super.setData(pixelatedRaster);
		pixelated = true;
	}

}
