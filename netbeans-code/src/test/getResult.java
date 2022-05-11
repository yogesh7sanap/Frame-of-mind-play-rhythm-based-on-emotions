/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.constant.ServerConstants;
import com.svm.SvmClassifier;
import helper.StringHelper;
import java.net.*;
import java.io.*;

/**
 *
 * @author technowings
 */
/**
 * This program is a socket client application that connects to a time server to
 * get the current date time.
 *
 * @author www.codejava.net
 */
public class getResult {

    public static void main(String[] args) {
//        getResult.main("C:/Python35/keras_model_face/00000.png");
//        getSVMOutput("2");
    }

    public static String main(String imagePath) {
        String hostname = ServerConstants.url;
        int port = 7812;
//        String path = "D:\\work\\project\\PlantDiseaseDetection\\python-cnn\\training_data\\Tomato___Bacterial_spot\\b168.jpg";
        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            byte[] data = imagePath.getBytes();
            output.write(data);

            InputStream input = socket.getInputStream();
            StringBuffer sb = new StringBuffer();
//               data = new byte[1024];
//            while(input.read()!=-1){
            int len = input.read(data);
            if (len != -1) {

//            }C
                System.out.println(new String(data, 0, len));
                return (new String(data, 0, len));

            } else {
                return "unRecognized";
            }

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
        return null;
    }

    public static String getSVMOutput(String imagePath, String featureArray) {

        featureArray = featureArray.replace("[", "");
        featureArray = featureArray.replace("]", "");
        featureArray = featureArray.replace(" ", "");
        System.out.println("Sending Features to SVM " + featureArray);

        if (ServerConstants.isJAVA_ML) {
            System.out.println("test.getResult.getSVMOutput()");
            int moodIndex = StringHelper.n2i(SvmClassifier.parseParameters(imagePath, featureArray));
            return ServerConstants.ML_MOODS_SEQUENCE.values()[moodIndex].toString().toLowerCase();
        }
   System.out.println("test.getResult.getSVMOutput()");
        featureArray = featureArray + "," + parseInfo(imagePath);
        String hostname = ServerConstants.url;
        int port = 7812;
//        String path = "D:\\work\\project\\PlantDiseaseDetection\\python-cnn\\training_data\\Tomato___Bacterial_spot\\b168.jpg";
        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();

            InputStream input = socket.getInputStream();

            System.out.println("Sending Features to SVM " + featureArray);

            byte[] data = featureArray.getBytes();
            output.write(data);

            int len = input.read(data);
            if (len != -1) {
                int moodIndex = StringHelper.n2i(new String(data, 0, len));
                System.out.println(moodIndex);

                return ServerConstants.ML_MOODS_SEQUENCE.values()[moodIndex].toString().toLowerCase();

            } else {
                return "unRecognized";
            }

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
        return null;
    }

    public static int parseInfo(String imagePath) {
        String op = main(imagePath);
        try {
            int mi = ServerConstants.moodIndexMapping.get(op);
            return mi;
        } catch (Exception e) {
            System.out.println("OP ON ERROR " + op);
//            e.printStackTrace();
        }
        return 0;
    }
}
