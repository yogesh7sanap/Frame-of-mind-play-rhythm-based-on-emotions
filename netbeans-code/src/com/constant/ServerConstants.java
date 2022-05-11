package com.constant;

import com.svm.SvmClassifier;
import helper.OsHelper;
import java.util.HashMap;

public class ServerConstants {

//    public static enum MOOD_STRINGS {
//         ANGRY, DISGUST, FEAR, HAPPY, SAD, SURPRISE, NEUTRAL
//    };
//
//    public static enum SONG_GENRE {
//        PARTY, INSTRUMENTAL, SILENT
//    };
//
    public static enum ML_MOODS_SEQUENCE {
        ANGRY, DISGUST, FEAR, HAPPY, SAD, SURPRISE, NEUTRAL
    }

    public static final HashMap<String, Integer> moodIndexMapping = new HashMap<>();
    public static final String db_driver = "com.mysql.jdbc.Driver";
    public static final String db_user = "root";
    public static final String db_pwd = "";

    public static boolean isJAVA_ML = true;

    public static final String PROJECT_BASE_PATH = "D:\\work\\project\\FacePlayer_python\\netbeans-code\\";
    public static final String project_url = PROJECT_BASE_PATH;
    public static final String writefilepath = PROJECT_BASE_PATH + "00000.png";
    public static final String trainddatafilepath = PROJECT_BASE_PATH + "/trainingset.txt";

    public static final SvmClassifier svm = new SvmClassifier();
    public static final String PARSING_FILE = "";
    public static final String NEURAL_DIR = PROJECT_BASE_PATH;
    public static final String NEURAL_MODEL_FILE = NEURAL_DIR + "neural.model";
    public static final String NEURAL_TRAINING_SET = NEURAL_DIR + "meoji2.arff";

    public static double imgh;
    public static double imgw;
    public static String db_url = "jdbc:mysql://localhost/faceplayer";
    public static boolean emotion = false;
    public static final String url = "localhost";
//    public static String angryMusic = "D:\\work\\project\\FacePlayer\\music\\angry.wav";
//    public static String sadMusic = "D:\\work\\project\\FacePlayer\\music\\sad.wav";
//    public static String happyMusic = "D:\\work\\project\\FacePlayer\\music\\happy.wav";

//    public static String angryMusic = "D:\\work\\project\\FacePlayer\\music\\angry\\";
//    public static String sadMusic = "D:\\work\\project\\FacePlayer\\music\\sad\\";
//    public static String happyMusic = "D:\\work\\project\\FacePlayer\\music\\happy\\";
    public static String music ="D:\\work\\project\\FacePlayer_python\\netbeans-code\\music\\genre-songs";

//    static {
//        for (int i = 0; i < ML_MOODS_SEQUENCE.values().length; i++) {
//            moodIndexMapping.put(ML_MOODS_SEQUENCE.values()[i].toString().toLowerCase(), i);
//        }
//    }

    public static void init() {
        OsHelper ph = new OsHelper();

        StringBuffer sb = ph.getCommandOutput("cmd /c python standalone_server_face.py ", "C:\\Python37\\keras_model_face\\", 0);

    }

    public static void main(String[] args) {
    }
}
