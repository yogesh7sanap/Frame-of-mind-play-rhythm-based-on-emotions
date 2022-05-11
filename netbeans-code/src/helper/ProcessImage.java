/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import com.constant.ServerConstants;
import static org.bytedeco.javacpp.opencv_core.FONT_HERSHEY_PLAIN;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
import test.getResult;

public class ProcessImage {

    public static double stdimgw = 640;
    public static double stdimgh = 480;
    public static double[][] feature;
    public static ArrayList result = new ArrayList();
    public static ArrayList featureList = new ArrayList();

    public static Mat drawSumMarks(double[] pt, Mat img, double[] hw) {
        // TODO Auto-generated method stub
        StringBuffer sb = FileHelper.getFileContent("D:/mp4 Facial Landmark/Happy/_emotiondataset.txt");
        int[] ip = new int[pt.length];
        result = new ArrayList();
        featureList = new ArrayList();
        feature = new double[pt.length / 136][7];
        for (int i = 0; i < pt.length; i++) {
            ip[i] = (int) pt[i];
        }
        int[] featurepoint = {36, 39, 38, 40, 42, 45, 44, 46, 21, 22, 54, 48, 57, 51, 31, 35};

        for (int i = 0; i < featurepoint.length; i++) {
//    		System.out.println(i+"-"+featurepoint[i]);	
        }
        StringBuffer sbb = new StringBuffer();
        sbb.append(ip);
        int count = 0;
        int ratio = 0;
        System.out.println("Mouth width,mouth height,right eye width,right eye heigth,left eye width,left eye heigth,nose width ");
        for (int i = 1; i <= pt.length / 136; i++) {
            for (int j = 0; j < featurepoint.length; j++) {
//                circle(img, getPoint(featurepoint[j], ip, i - 1), 1, AbstractScalar.RED);
//                putText(img, j + "", getPoint(featurepoint[j], ip, i - 1), FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
            }
            feature[i - 1][0] = (hw[ratio + 1]) / (getDistance(getPoint(featurepoint[11], ip, i - 1), getPoint(featurepoint[10], ip, i - 1)));
            feature[i - 1][1] = (hw[ratio]) / (getDistance(getPoint(featurepoint[13], ip, i - 1), getPoint(featurepoint[12], ip, i - 1)));
            feature[i - 1][2] = (hw[ratio] + 1) / (getDistance(getPoint(featurepoint[15], ip, i - 1), getPoint(featurepoint[14], ip, i - 1)));
            feature[i - 1][3] = (hw[ratio + 1]) / (getDistance(getPoint(featurepoint[1], ip, i - 1), getPoint(featurepoint[0], ip, i - 1)));
            feature[i - 1][4] = (hw[ratio]) / (getDistance(getPoint(featurepoint[3], ip, i - 1), getPoint(featurepoint[2], ip, i - 1)));
            feature[i - 1][5] = (hw[ratio + 1]) / (getDistance(getPoint(featurepoint[4], ip, i - 1), getPoint(featurepoint[5], ip, i - 1)));
            feature[i - 1][6] = (hw[ratio]) / (getDistance(getPoint(featurepoint[7], ip, i - 1), getPoint(featurepoint[6], ip, i - 1)));
          

//            System.out.println("***********************************************************************");
//            System.out.println("SBB =" + sbb.toString());
            System.out.print(" " + feature[i - 1][0] + " " + feature[i - 1][1] + " " + feature[i - 1][2] + " " + feature[i - 1][3] + " " + feature[i - 1][4] + " " + feature[i - 1][5] + " " + feature[i - 1][6]);
            sb.append(feature[i - 1][0] + "," + feature[i - 1][1] + "," + feature[i - 1][2] + "," + feature[i - 1][3] + "," + feature[i - 1][4] + "," + feature[i - 1][5] + "," + feature[i - 1][6] + "\n");
            try {
                writeFile("D:/mp4 Facial Landmark/Happy/_emotiondataset.txt", sb);
            } catch (IOException ex) {
                Logger.getLogger(ProcessImage.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
//            System.out.println("***********************************************************************");
//            result.add(SvmClassifier.applySvmClassifier(feature[i - 1]));
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
//  String detectedMood = getResult.getSVMOutput(ServerConstants.writefilepath, java.util.Arrays.toString(feature));
            putText(img, getEmotion(feature[i - 1][0], feature[i - 1][6]), getPoint(featurepoint[8], ip, i - 1), FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
//
            ratio = ratio + 4;
            count++;

        }

//        System.out.println(result.toString());
//        System.out.println(featureList.toString());
//    	Frame f = OpenCVHelper.mat2frame(img);
//        BufferedImage bufImage = OpenCVHelper.frame2buffered(f);
//        writeFile(bufImage);
//        try {
//            JFrame frame = new JFrame();
//            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
//            frame.pack();
//            frame.setVisible(true);
//            img.release();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return img;
    }

    public static Point getPoint(int index, int[] points, int faceIndex) {
        int x1 = points[136 * faceIndex + index];
        int y1 = points[136 * faceIndex + index + 136 / 2];
//    	System.out.println(x1+" x-y "+y1);
        return new Point(x1, y1);
    }

    public static double getDistance1(Point p1, Point p2) {
        return Math.sqrt(Math.abs((p1.x() - p2.x()) * (p1.x() - p2.x()) + (p1.y() - p2.y()) * (p1.y() - p2.y())));
    }

    public static void main(String[] args) {
        Point p1 = new Point(647, 452);
        Point p2 = new Point(622, 594);
        System.out.println("getD " + getDistance(p1, p2));
//        System.out.println("getD "+getDistance1(p1, p2));
    }

    public static double getDistance(Point p1, Point p2) {

        double xf = (stdimgw / ServerConstants.imgw);
        double yf = (stdimgh / ServerConstants.imgh);
//        System.out.print(xf + "+++" + yf);
//    	System.out.print(" x1 x2 "+p1.x()+","+p2.x() +" y1 y2 "+p1.y()+","+p2.y());
//    	System.out.print(Math.abs((p1.x()*xf-p2.x()*xf)*(p1.x()*xf-p2.x()*xf)+(p1.y()*yf-p2.y()*yf)*(p1.y()*yf-p2.y()*yf)));  
//    	System.out.println();
        return Math.sqrt(Math.abs((p1.x() * xf - p2.x() * xf) * (p1.x() * xf - p2.x() * xf) + (p1.y() * yf - p2.y() * yf) * (p1.y() * yf - p2.y() * yf)));
    }

    private static void drawLandmark(double[] pt, Mat img) {
        // TODO Auto-generated method stub
        int count = 1;
        for (int i = 1; i <= pt.length / 136; i++) {
            for (int j = count; j < (68 * i * 2) - 68; j++) {

                int cy = (int) pt[j + 68];
                int cx = (int) pt[j];
//	        	System.out.println(cx+"  "+cy);
                circle(img, new Point(cx, cy), 2, AbstractScalar.RED);
//	        	putText(img, i+"", new Point(cx,cy),FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
                count++;
            }
            count = count + 68;

        }
        Frame f = OpenCVHelper.mat2frame(img);
        BufferedImage bufImage = OpenCVHelper.frame2buffered(f);
        writeFile(bufImage);
        try {
            JFrame frame = new JFrame();
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
            img.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String writeFile(BufferedImage image) {
        // TODO Auto-generated method stub
        String path = null;
        try {
            File f = new File(ServerConstants.writefilepath);  //output file path
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

    public static void writeFile(String fileName, StringBuffer sbf) throws IOException {
        BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(fileName)));
        //write contents of StringBuffer to a file
        bwr.write(sbf.toString());
        bwr.flush();
        bwr.close();
    }

    public static String getEmotion(double mw, double nw) {

        if (mw > 5.7 && nw > 40 && nw < 58) {
            return "Neutral";
        } else if (mw < 6.7 && nw > 58) {
            return "Happy";
        } else if (mw > 6.7 && nw < 40) {
            return "Surprised";
        }

        return "Un Recognized";

    }
}
