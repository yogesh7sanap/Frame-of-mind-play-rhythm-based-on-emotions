/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javacvtesting;

import com.constant.ServerConstants;
import com.svm.SvmClassifier;
import static org.bytedeco.javacpp.opencv_core.FONT_HERSHEY_PLAIN;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.helper.opencv_core.AbstractScalar;
import org.bytedeco.javacv.Frame;

import helper.FileHelper;
import helper.OpenCVHelper;
import helper.StringHelper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static org.bytedeco.javacpp.opencv_face.drawFacemarks;
import org.bytedeco.javacpp.helper.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.helper.opencv_core.AbstractScalar;
import org.bytedeco.javacv.Frame;
import helper.FileHelper;
import helper.OpenCVHelper;
import helper.ProcessImage;
import helper.StringHelper;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_face;
import org.bytedeco.javacpp.opencv_objdetect;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 *
 * @author Technowings
 */
public class SingleImagePoints {

    public static double stdimgw = 640;
    public static double stdimgh = 480;
    public static final String PROJECT_BASE_PATH = "D:\\Emotion Dataset/Happy";
    static String writefilepath = "D:\\mp4 Facial Landmark\\happy\\";
    static String filename = "1.png";
    public static double imgh;
    public static double imgw;
    public static final String landfilepath = PROJECT_BASE_PATH + "";
    public static final String landhwfilepath = PROJECT_BASE_PATH + "";
    public static ArrayList result = new ArrayList();
    public static ArrayList featureList = new ArrayList();
//    public static final String batfilepath = PROJECT_BASE_PATH + "facialexpression\\Release\\findLandmark.bat";

    private static String writeFile(BufferedImage image, String fileName) {
        // TODO Auto-generated method stub
        String path = null;
        try {
            File f = new File(writefilepath + fileName);  //output file path
//		      Mat img = OpenCVHelper.file2mat("C:\\Users\\technowings\\Pictures\\00000.png");
//		      img = new Mat(img, 64.0, 62.0);
            ImageIO.write(image, "png", f);
            System.out.println("Writing complete." + f.getAbsolutePath().toString());
            path = f.getAbsolutePath().toString();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return path;
    }

    public static void main(String[] args) throws InterruptedException {

//        getFacielLandmarks("C:\\Users\\Technowings\\Pictures\\2.jpg");
        opencv_objdetect.CascadeClassifier faceDetector = new opencv_objdetect.CascadeClassifier("D:\\work\\setup\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml");
        opencv_face.Facemark facemark = opencv_face.FacemarkLBF.create();
        facemark.loadModel(".\\GSOC2017-master\\data\\lbfmodel.yaml");
        opencv_core.RectVector faces = new opencv_core.RectVector();
//        for (int k = 0; k < 10; k++) {


        Mat frame = imread("D:\\mp4 Facial Landmark\\image (110).png");
//        System.out.println("D:\\mp4 Facial Landmark\\surprise\\image (1).png");
        ServerConstants.imgw = frame.size().width();
        ServerConstants.imgh = frame.size().height();
        Mat gray = new Mat(480, 600, 1);
        cvtColor(frame, gray, COLOR_BGR2GRAY);
        equalizeHist(gray, gray);
        faceDetector.detectMultiScale(gray, faces);
        StringBuffer xy = new StringBuffer();
        StringBuffer hw = new StringBuffer();
        StringBuffer xx = new StringBuffer();
        StringBuffer yy = new StringBuffer();
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
            opencv_core.Point2fVectorVector landmarks = new opencv_core.Point2fVectorVector();
            boolean success = facemark.fit(frame, faces, landmarks);

            if (success) {
                for (long i = 0; i < landmarks.size(); i++) {
                    opencv_core.Point2fVector v = landmarks.get(i);
                    opencv_core.Point2f[] p = v.get();
                    for (int j = 0; j < p.length; j++) {
                        opencv_core.Point2f point2f = p[j];
                        int x = (int) Math.ceil(point2f.x());
                        int y = (int) Math.ceil(point2f.y());
                        xx.append(x);
                        yy.append(y);

                        if (j != p.length - 1) {
                            xx.append(",");
                            yy.append(",");

                        }
//                        circle(frame, new Point(x, y),
//                                10, Scalar.RED);
//                        putText(frame, j + "", new Point(x, y), 0, 0.3, Scalar.GREEN);
                    }
                    xy.append(xx + "," + yy);
                    drawFacemarks(frame, v, Scalar.YELLOW);
//                    drawLandmark(getPoint(p), frame, filename);
                    double[] pt = StringBufferTODoubleArray(xy);
                    double[] hwp = StringBufferTODoubleArray(hw);
                    Mat img1 = drawSumMarks(pt, frame, hwp);
                    imshow("Facial Emotions", img1);
                    if (waitKey(1) == 27) {
                        break;
                    }
                }
            }

//            System.out.println("HW " + hw.toString());
//            System.out.println("landmark " + xy.toString());

        }
//        }
    }

    public static double[] getPoint(opencv_core.Point2f[] p) {
        double d[] = new double[p.length];
        int land = p.length / 2;
        for (int i = 0; i < land; i++) {
            opencv_core.Point2f pp = p[i];
            d[i] = pp.x();
            d[i + land] = pp.y();
        }
        return d;
    }

    private static double[] StringBufferTODoubleArray(StringBuffer xy) {
        String[] arr = xy.toString().split(",");
        double[] d = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            d[i] = StringHelper.n2d(arr[i]);
//            System.out.println("d[]"+d[i]);
        }
        return d;
    }
    public static double[][] feature;

    public static Mat drawSumMarks(double[] pt, Mat img, double[] hw) {
        // TODO Auto-generated method stub
        try {


//        StringBuffer sb = FileHelper.getFileContent("D:/mp4 Facial Landmark/Happy/_emotiondataset.txt");
            int[] ip = new int[pt.length];
            result = new ArrayList();
            featureList = new ArrayList();
            feature = new double[pt.length / 136][7];
            for (int i = 0;
                    i < pt.length;
                    i++) {
                ip[i] = (int) pt[i];
            }
            System.out.println("ip " + ip.length);
            int[] featurepoint = {36, 39, 38, 40, 42, 45, 44, 46, 21, 22, 54, 48, 57, 51, 31, 35};
            for (int i = 0;
                    i < featurepoint.length;
                    i++) {
//    		System.out.println(i+"-"+featurepoint[i]);	
            }
            StringBuffer sbb = new StringBuffer();

            sbb.append(ip);
            int count = 0;
            int ratio = 0;

            System.out.println(
                    "Mouth width,mouth height,right eye width,right eye heigth,left eye width,left eye heigth,nose width ");
            System.out.println("Faces Found: " + pt.length / 136 + "hw size " + hw.length);
            for (int i = 1;
                    i <= pt.length / 136; i++) {

                for (int j = 0; j < featurepoint.length; j++) {
                    circle(img, getPoint(featurepoint[j], ip, i - 1), 1, AbstractScalar.RED);
//                putText(img, j + "", getPoint(featurepoint[j], ip, i - 1), FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
                }
                System.out.println("hw i " + ratio + 1);
                if ((ratio + 1)<= hw.length) {

                    feature[i - 1][0] = (hw[ratio + 1]) / (getDistance(getPoint(featurepoint[11], ip, i - 1), getPoint(featurepoint[10], ip, i - 1)));
                    feature[i - 1][1] = (hw[ratio]) / (getDistance(getPoint(featurepoint[13], ip, i - 1), getPoint(featurepoint[12], ip, i - 1)));
                    feature[i - 1][2] = (hw[ratio] + 1) / (getDistance(getPoint(featurepoint[15], ip, i - 1), getPoint(featurepoint[14], ip, i - 1)));
                    feature[i - 1][3] = (hw[ratio + 1]) / (getDistance(getPoint(featurepoint[1], ip, i - 1), getPoint(featurepoint[0], ip, i - 1)));
                    feature[i - 1][4] = (hw[ratio]) / (getDistance(getPoint(featurepoint[3], ip, i - 1), getPoint(featurepoint[2], ip, i - 1)));
                    feature[i - 1][5] = (hw[ratio + 1]) / (getDistance(getPoint(featurepoint[4], ip, i - 1), getPoint(featurepoint[5], ip, i - 1)));
                    feature[i - 1][6] = (hw[ratio]) / (getDistance(getPoint(featurepoint[7], ip, i - 1), getPoint(featurepoint[6], ip, i - 1)));

//            System.out.println("***********************************************************************");
//            System.out.println("SBB =" + sbb.toString());
                    System.out.println(" " + feature[i - 1][0] + " " + feature[i - 1][1] + " " + feature[i - 1][2] + " " + feature[i - 1][3] + " " + feature[i - 1][4] + " " + feature[i - 1][5] + " " + feature[i - 1][6]);
//            sb.append(feature[i - 1][0] + "," + feature[i - 1][1] + "," + feature[i - 1][2] + "," + feature[i - 1][3] + "," + feature[i - 1][4] + "," + feature[i - 1][5] + "," + feature[i - 1][6] + "\n");

//            System.out.println("***********************************************************************");

//            System.out.println(result.get(i - 1));
                    Rect rect = new Rect();
                    rect.x(StringHelper.n2i(hw[ratio + 2]));
                    rect.y(StringHelper.n2i(hw[ratio + 3]));
                    rect.width(StringHelper.n2i(hw[ratio + 1]));
                    rect.height(StringHelper.n2i(hw[ratio]));
                    rectangle(
                            img,
                            new org.bytedeco.javacpp.opencv_core.Point(rect.x(), rect.y()),
                            new org.bytedeco.javacpp.opencv_core.Point(rect.x() + rect.width(), rect.y() + rect.height()), Scalar.GREEN, 1, 8, 0);
//           
                    putText(img, ServerConstants.emotion == true ? getEmotion(feature[i - 1][0], feature[i - 1][6]) : getEmotion(feature[i - 1]), getPoint(featurepoint[8], ip, i - 1), FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
                    System.out.println("Emotion " + getEmotion(feature[i - 1][0], feature[i - 1][6]) + " Data " + feature[i - 1][0] + " " + feature[i - 1][6]);
                    ratio = ratio + 4;
                    count++;
                    result.add(ServerConstants.emotion == true ? getEmotion(feature[i - 1][0], feature[i - 1][6]) : getEmotion(feature[i - 1]));
                }
            }
//        
            return img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Point getPoint(int index, int[] points, int faceIndex) {
        System.out.println(" X "+(136 * faceIndex + index)+" Y "+(136 * faceIndex + index+136/2));
        int x1 = points[136 * faceIndex + index];
        int y1 = points[136 * faceIndex + index + 136 / 2];
//    	System.out.println(x1+" x-y "+y1);
        return new Point(x1, y1);
    }

    public static String getEmotion(double mw, double nw) {
        String emoji = "";
        if (mw > 6.7 && nw > 41.0 && nw < 58.0) {
            emoji = "Neutral";
        }
//        3.583800771948818 22.339234080656052
        if (mw < 7.1 && nw > 52.0) {
            emoji = "Happy";
        }
//         2.709606192254596 24.4
        if (mw > 7.0 && nw < 46.0) {
            emoji = "Surprised";
        }
//4.272027096969468 26.454545454545453

        return emoji;

    }

    public static String getEmotion(double[] featureArray) {
        String emoji = "";

        emoji = SvmClassifier.getSVMPredication(featureArray)+"";

        return StringHelper.n2s(getEmotion(emoji));
    }


    public static double getDistance(Point p1, Point p2) {
        double xf = (stdimgw / ServerConstants.imgw);
        double yf = (stdimgh / ServerConstants.imgh);
//        
        return Math.sqrt(Math.abs((p1.x() * xf - p2.x() * xf) * (p1.x() * xf - p2.x() * xf) + (p1.y() * yf - p2.y() * yf) * (p1.y() * yf - p2.y() * yf)));
    }

    public static String getEmotion(String args) {

        HashMap<String, String> emojiMap = new HashMap();

        emojiMap.put(
                "0", "Nuetral");
        emojiMap.put(
                "1", "Happy");
        emojiMap.put(
                "2", "Surprise");
        return emojiMap.get(args);
    }
}
