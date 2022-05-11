/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javacvtesting;

import com.constant.ServerConstants;
import helper.OpenCVHelper;
import helper.StringHelper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import org.bytedeco.javacpp.helper.opencv_core;
import org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.bytedeco.javacpp.opencv_core.Point2fVector;
import org.bytedeco.javacpp.opencv_core.Point2fVectorVector;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.UMat;
import org.bytedeco.javacpp.opencv_face.Facemark;
import org.bytedeco.javacpp.opencv_face.FacemarkLBF;
import org.bytedeco.javacpp.opencv_face.FacemarkAAM;
import static org.bytedeco.javacpp.opencv_face.drawFacemarks;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_highgui.waitKey;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import static org.bytedeco.javacpp.opencv_videoio.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import org.bytedeco.javacv.FrameRecorder;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 *
 * @author technowings
 */
public class FaceEmotions {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        // Load Face Detector
        CascadeClassifier faceDetector = new CascadeClassifier("D:\\work\\setup\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml");

        // Create an instance of Facemark
        Facemark facemark = FacemarkLBF.create();

//        Facemark facemark = FacemarkAAM.create();
        System.out.println((int) 18.75);
        // Load landmark detector 
        facemark.loadModel(".\\GSOC2017-master\\data\\lbfmodel.yaml");
//        facemark.loadModel("D:\\work\\project\\JavaCVTesting-FaceEmotions\\GSOC2017-master\\data\\LBF.model");
//        VideoCapture cam = new VideoCapture("");
//           FrameRecorder recorder = FrameRecorder.createDefault("output.avi", width, height);
//        recorder.start();
        // Set up webcam for video capture
//        VideoCapture cam = new VideoCapture(1);
//          cam.set(CV_CAP_PROP_FRAME_WIDTH, 800);
//        cam.set(CV_CAP_PROP_FRAME_HEIGHT, 600);
//        VideoCapture cam = new VideoCapture("D:/mp4 Facial Landmark/stream_name.mp4");
        VideoCapture cam = new VideoCapture(0);

        // Variable to store a video frame and its grayscale 
        Mat frame = new Mat();
        int in = 0;
        // Read a frame
        while (cam.read(frame)) {
            in++;

            if (in % 10 != 0) {
                continue;
            }
            Mat img1 = new Mat();
            // convert to grayscale and equalize histograe for better detection
            // + use of transparent API
//            UMat gray = new UMat();
//            System.out.println(frame);
//            frame.copyTo(gray);
            Mat gray = new Mat(480, 600, 1);
//            frame = imread("D:\\ReceivedFiles\\yalefaces\\emotion_train\\normal\\subject05.normal.png");
//            frame = OpenCVHelper.file2mat("D:\\ReceivedFiles\\yalefaces\\emotion_train\\normal\\subject01.normal.png");
            if (frame != null) {
                ServerConstants.imgw = frame.size().width();
                ServerConstants.imgh = frame.size().height();
//                frame.copyTo(gray);
                cvtColor(frame, gray, COLOR_BGR2GRAY);
//             cvtColor(frame,gray, COLOR_BGR2GRAY);
                equalizeHist(gray, gray);

                // Find faces on the image
                RectVector faces = new RectVector();
                faceDetector.detectMultiScale(gray, faces);
                StringBuffer xy = new StringBuffer();
                StringBuffer hw = new StringBuffer();
                StringBuffer xx = new StringBuffer();
                StringBuffer yy = new StringBuffer();
//            System.out.println ("Faces detected: "+faces.size());
                // Verify is at least one face is detected
                // With some Facemark algorithms it crashes if there is no faces
                if (!faces.empty()) {
                    for (int i = 0; i < faces.size(); i++) {
                        Rect object = faces.get(i);
                        int h = object.height();
                        int w = object.width();
                        int x = object.x();
                        int y = object.y();
                        hw.append(h + "," + w + "," + x + "," + y);
                        if (i != faces.size() - 1) {
                            hw.append(",");
                        }
                    }
                    // Variable for landmarks. 
                    // Landmarks for one face is a vector of points
                    // There can be more than one face in the image.
                    Point2fVectorVector landmarks = new Point2fVectorVector();

                    // Run landmark detector
                    boolean success = facemark.fit(frame, faces, landmarks);

                    if (success) {
                        // If successful, render the landmarks on the face
                        for (long i = 0; i < landmarks.size(); i++) {
                            Point2fVector v = landmarks.get(i);
                            Point2f[] p = v.get();
                            for (int j = 0; j < p.length; j++) {
                                Point2f point2f = p[j];
                                int x = (int) Math.ceil(point2f.x());
                                int y = (int) Math.ceil(point2f.y());
                                xx.append(x);
                                yy.append(y);
                                if (j != p.length - 1) {
                                    xx.append(",");
                                    yy.append(",");

                                }
//                                System.out.println(j + " => " + x + " " + y);
//                                circle(frame, new Point(x, y),
//                                        10, Scalar.RED);
//                                putText(frame, j + "", new Point(x, y), 0, 0.6, Scalar.GREEN);
//                                Point p1 = new Point(x, y);

//                                point2f = p[j + 1];
//                                x = (int) Math.ceil(point2f.x());
//                                y = (int) Math.ceil(point2f.y());
//                                Point p2 = new Point(x, y);
//                                line(gray, p1, p2, AbstractScalar.GREEN, 1, 8, 0);
                            }
                            xy.append(xx + "," + yy);
//                            drawFacemarks(frame, v, Scalar.YELLOW);
//                            System.out.println(v.get());
//                            System.out.println("HW " + hw);
//                            System.out.println("XY " + xy);
                            double[] pt = StringBufferTODoubleArray(xy);
                            double[] hwp = StringBufferTODoubleArray(hw);
//                            System.out.println("pt = "+pt.length);
//                            System.out.println("hwp "+hwp.length);

                            img1 = SingleImagePoints.drawSumMarks(pt, frame, hwp);
                            if (img1 != null) // Display results 
                            {
                                imshow("LBF Facial Landmark", img1);
                            }
                        }

                    }

                }

                // Exit loop if ESC is pressed
                if (waitKey(1) == 27) {
                    break;
                }
            }
        }
    }

    public static void writeFile(String fileName, StringBuffer sbf) throws IOException {
        BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("D:/" + fileName)));
        //write contents of StringBuffer to a file
        bwr.write(sbf.toString());
    }

    public static double[] StringBufferTODoubleArray(StringBuffer xy) {
        String[] arr = xy.toString().split(",");
        double[] d = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            d[i] = StringHelper.n2d(arr[i]);
//            System.out.println("d[]"+d[i]);
        }
        return d;
    }
    public static int[] StringBufferToIntegerArray(StringBuffer xy) {
        String[] arr = xy.toString().split(",");
        int[] d = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            d[i] = StringHelper.n2i(arr[i]);
//            System.out.println("d[]"+d[i]);
        }
        return d;
    }
}
