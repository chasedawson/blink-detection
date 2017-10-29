import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class Detection {
	private static int eyeCount = 0;

	public static void run() {
		Mat[] frames = getVideoInput(180);
	}
	public static void main(String[] args) {
		run();
	}
	public static void load() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public static Mat detectFace(Mat image) {
		CascadeClassifier face_cascade = new CascadeClassifier();
		face_cascade.load("/Users/Chase Dawson/Code/workspace/Blink Detection/src/cascades/haarcascade_frontalface_alt.xml");
		
		MatOfRect faceDetections = new MatOfRect();
		face_cascade.detectMultiScale(image, faceDetections);
		
		
		Rect crop = null;
		Rect rect = faceDetections.toArray()[0];
		
		crop = new Rect(rect.x, rect.y, rect.width/2, rect.height);
		        
		Mat croppedFace = new Mat(image, crop);
		return croppedFace;
	}
	public static Mat detectEye(Mat image, int currentFrame) {
		Mat eye = null;
		CascadeClassifier eye_cascade = new CascadeClassifier();
		eye_cascade.load("/Users/Chase Dawson/Code/workspace/Blink Detection/src/cascades/haarcascade_righteye_2splits.xml");

		MatOfRect eyeDetections = new MatOfRect();
	    eye_cascade.detectMultiScale(image, eyeDetections);
		 		
		if(eyeDetections.toArray().length == 0) 
			return eye;
		 
		Rect eyeCrop = null;
		Rect rect = eyeDetections.toArray()[0];
		
		eyeCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
		eye = new Mat(image, eyeCrop);
		
		if(rect.width > 60 && rect.height > 60) {
			Imgcodecs.imwrite("/Users/Chase Dawson/Desktop/pictures/eye" + currentFrame + ".jpg", eye);
			eyeCount++;
		}
		return eye;
	}
	
	public static int getEyeCount() {
		return eyeCount;
	}
	public static Mat[] getVideoInput(int numFrames) {
		System.out.println("Reading in video ... ");
		try {
			System.load("C:/Program Files/opencv/build/x64/vc14/bin/opencv_ffmpeg320_64.dll");
		} catch (Exception e) {
			e.printStackTrace();
		}
		VideoCapture capture = new VideoCapture("test_video.mp4");
		Mat[] frames = new Mat[numFrames];
		Mat frame;
		int currentFrame = 0;
		while(currentFrame < numFrames) {
				frame = new Mat();
				capture.read(frame);
				frames[currentFrame] = frame;
			currentFrame++;
		}
		capture.release();
		System.out.println("done");
		return frames;
	}

}