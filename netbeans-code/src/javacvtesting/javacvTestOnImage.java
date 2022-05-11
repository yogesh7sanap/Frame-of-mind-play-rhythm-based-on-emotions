/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javacvtesting;

import helper.FileHelper;
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
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacv.FrameRecorder;

/**
 *
 * @author Technowings
 */
public class javacvTestOnImage {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        // Load Face Detector
        CascadeClassifier faceDetector = new CascadeClassifier(".\\haarcascade_frontalface_alt2.xml");

        // Create an instance of Facemark
        Facemark facemark = FacemarkLBF.create();
//        Facemark facemark = FacemarkAAM.create();
        System.out.println((int) 18.75);
       
        // Load landmark detector 
        facemark.loadModel(".\\GSOC2017-master\\data\\lbfmodel.yaml");
//        facemark.loadModel("D:\\work\\project\\JavaCVTesting-FaceEmotions\\GSOC2017-master\\data\\LBF.model");
//        VideoCapture cam = new VideoCapture("D:/stream_name.mp4");+
        int count = 1;
        File[] folderFiles = FileHelper.getFileList("D:\\temp\\Surprise", "png");
        for (int k = 0; k < folderFiles.length; k++) {
            File file = folderFiles[k];
             Mat frame = new Mat(); 
            frame = imread(file.getAbsolutePath());
//           FrameRecorder recorder = FrameRecorder.createDefault("output.avi", width, height);
//        recorder.start();
            // Set up webcam for video capture
//        VideoCapture cam = new VideoCapture(1);
//          cam.set(CV_CAP_PROP_FRAME_WIDTH, 800);
//        cam.set(CV_CAP_PROP_FRAME_HEIGHT, 600);
            //        VideoCapture cam = new VideoCapture ("D:/stream_name.mp4");
            // Variable to store a video frame and its grayscale 

//        int in = 0;
//        // Read a frame
//        while (cam.read(frame)) {
//            in++;
//
//            if (in % 10 != 0) {
//                continue;
//            }
StringBuffer xsb = new StringBuffer();
        StringBuffer ysb = new StringBuffer();
        StringBuffer hwsb = new StringBuffer();
        hwsb = FileHelper.getFileContent("D:/landhw.txt");
        StringBuffer n = new StringBuffer();
        n = FileHelper.getFileContent("D:/land.txt");
            // convert to grayscale and equalize histograe for better detection
            // + use of transparent API
            UMat gray = new UMat();
            System.out.println(frame);
            frame.copyTo(gray);
            cvtColor(gray, gray, COLOR_BGR2GRAY);
            equalizeHist(gray, gray);


            // Find faces on the image
            RectVector faces = new RectVector();
            faceDetector.detectMultiScale(gray, faces);

//            System.out.println ("Faces detected: "+faces.size());
            // Verify is at least one face is detected
            // With some Facemark algorithms it crashes if there is no faces
            if (!faces.empty()) {
                for (int i = 0; i < faces.size(); i++) {
                    Rect rect = faces.get(i);
                    hwsb.append(rect.height() + "," + rect.width() + "," + rect.x() + "," + rect.y());
                    if (i != faces.size()) {
                        hwsb.append(",");
                    }
                }

//                System.out.println("Face HW " + hwsb.toString());

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
                            xsb.append(point2f.x() + ",");
                            ysb.append(point2f.y() + ",");

                            int x = (int) Math.ceil(point2f.x());
                            int y = (int) Math.ceil(point2f.y());

//                            System.out.println(j + " => " + x + " " + y);
//                            circle(frame, new Point(x, y),
//                                    10, Scalar.RED);
//                            putText(frame, j + "", new Point(x, y), 0, 0.6, Scalar.GREEN);
                        }

                        drawFacemarks(frame, v, Scalar.YELLOW);
//                        System.out.println(v.get());

                    }
//                    System.out.println("x " + xsb.toString());
//                    System.out.println("y " + ysb.toString());

                    n.append(xsb.toString() + "," + ysb.toString());
//                    System.out.println("n " + n.toString());


                }
                imwrite("D:/temp11/"+file.getName(), frame);
                 writeFile("land.txt", n);
        writeFile("landhw.txt", hwsb);
                System.out.println("count "+count);
                count++;
            }
            // Display results 
//            imshow("LBF Facial Landmark", frame);
            

            // Exit loop if ESC is pressed
//            if (waitKey(1) == 27) {
////                break; 
//                System.exit(0);
//            }
            frame.release();
            gray.release();
            
        }
       
        
    }

    public static void writeFile(String fileName, StringBuffer sbf) throws IOException {
        BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("D:/" + fileName)));
        //write contents of StringBuffer to a file
        bwr.write(sbf.toString());
        bwr.flush();
        bwr.close();
    }
}
