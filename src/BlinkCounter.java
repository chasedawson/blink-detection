import java.awt.image.BufferedImage;
import org.opencv.core.Mat;

public class BlinkCounter {
	private static final int NUM_SAVED_IMGS = Detection.getEyeCount();
	private static double[] averages = new double[Detection.getEyeCount()];
	private static double[] variances = new double[Detection.getEyeCount()];
	private static long[][] grayScaleEye;
	public static void main(String[] args) {
		System.out.println("Counting blinks ... ");
		int blinkCount = 0;
		for (int x = 0; x < NUM_SAVED_IMGS; x++) {
			BufferedImage eye = EyeAnalyzer.readInImage("/Users/Chase Dawson/Desktop/pictures/eye" + x + ".jpg");
			grayScaleEye = EyeAnalyzer.grayScaleArray(eye);
			averages[x] = EyeAnalyzer.calculateAverage(grayScaleEye);
			variances[x] = EyeAnalyzer.calculateVariance(grayScaleEye, averages[x]);
		}
		double sum = 0;
		double average_variance;
		int i = 0;
		for(double var: variances) {
			System.out.println(i + " " + var);
			sum += var;
			i++;
		}
		average_variance = sum/variances.length;
		System.out.println("Average Variance: " + average_variance);
		
		double sumofsquares = 0;
		for(double var: variances) {
			sumofsquares += Math.pow(var - average_variance, 2);
		}
		double varianceofvariances = sumofsquares/(variances.length);
		double sdofvariances = Math.sqrt(varianceofvariances);
		System.out.println("Standard Deviation of Variances: " + sdofvariances);
		for(int x = 20; x < variances.length; x++) {
			if(x != 0 && x != NUM_SAVED_IMGS) {
				if((variances[x] < average_variance - (1.75 * sdofvariances)) && (variances[x+1] < average_variance - (sdofvariances) && (variances[x-1] < average_variance - (sdofvariances)))) {
					System.out.println(variances[x] + " " + x);
					blinkCount++;
					x += 10;
				} 
			}
		}
		System.out.println("Blink Count: " + blinkCount);
	}
	public static int countBlinks(Mat[] eyes) {
		return 0;
		
	}
}