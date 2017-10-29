import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Run {
	private static final int NUM_FRAMES = 180;
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public static void main(String[] args) {
		System.out.println("Start");
		long startTime = System.currentTimeMillis();
		Mat[] frames = Detection.getVideoInput(NUM_FRAMES);
		Mat[] eyes = new Mat[NUM_FRAMES];
		for (int x = 0; x < NUM_FRAMES; x++) {
			frames[x] = Detection.detectFace(frames[x]);
			eyes[x] = Detection.detectEye(frames[x], x);
		}
		BlinkCounter.main(args);
		long endTime = System.currentTimeMillis();
		System.out.println("End");
		System.out.println("Total time: " + (endTime - startTime)/1000.0);
	}
	public static int getNumFrames() {
		return NUM_FRAMES;
	}

}