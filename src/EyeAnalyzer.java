import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EyeAnalyzer {
	public static void run(String filename) {
		long[][] pixels = grayScaleArray(readInImage(filename));
		System.out.println(calculateVariance(pixels, calculateAverage(pixels)));
		
	}
	public static BufferedImage readInImage(String name) {
		BufferedImage image = null;
		File file = new File(name);
		int count = 0;
		while (!file.exists()) {
			try {
				Thread.sleep(100);
				count++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(count == 10)
				break;
		}
		 try {
			 image = ImageIO.read(file);
		 } catch (IOException e) {
			 System.out.println(e);
		 }
		 return image;
	}
	public static long[][] grayScaleArray(BufferedImage image) {
		if(image == null)
			return null;
		int h = image.getHeight();
		int w = image.getWidth();
		Color color = null;
		long[][] pixels = new long[h][w];
		for (int r = 0; r < h; r++) { 
			for (int c = 0; c < w; c++) {
				color = new Color(image.getRGB(c, r));
				pixels[r][c] = (color.getRed() + color.getGreen() + color.getBlue())/3;
			}
		 }
		
		return pixels;
	}
	public static double calculateAverage(long[][] pixels) {
		double average = 0;
		for (long[] row : pixels) {
			for (long pixel : row) {
				average += pixel;
			}
		}
		average = average/(pixels.length * pixels[0].length);
		return average;
	}
	public static double calculateVariance(long[][] pixels, double average) {
		double sumofsquares = 0;
		for(long[] row: pixels) {
			for(long pixel: row) {
				sumofsquares += Math.pow(pixel - average, 2);
			}
		}
		double variance = sumofsquares/((pixels.length * pixels[0].length) - 1);
		return variance;
	}

}