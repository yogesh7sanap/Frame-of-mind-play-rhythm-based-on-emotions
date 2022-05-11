/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 */
package com.gui;
//DatabaseHelper();
import helper.ConnectionManager;
import helper.ObjectFileHelper;
import com.constant.ServerConstants;
import faceplayer.MP3PlayerThreaded;
import helper.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javacvtesting.FaceEmotions;
import javacvtesting.SingleImagePoints;
import static javacvtesting.SingleImagePoints.feature;
import static javacvtesting.SingleImagePoints.getDistance;
import static javacvtesting.SingleImagePoints.getPoint;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
//import org.apache.commons.net.DatabaseHelper;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.bytedeco.javacpp.opencv_core.Point2fVector;
import org.bytedeco.javacpp.opencv_core.Point2fVectorVector;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_face.Facemark;
import org.bytedeco.javacpp.opencv_face.FacemarkLBF;
import static org.bytedeco.javacpp.opencv_face.drawFacemarks;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import static org.bytedeco.javacpp.opencv_videoio.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacv.Frame;
import test.getResult;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

public class Main extends javax.swing.JFrame {

    MP3PlayerThreaded mp3 = null;
    static String music = "";
    DefaultTableModel model1 = null, model2 = null, model3 = null, model4 = null;
    DefaultComboBoxModel combo1 = null;
//    ArrayList<Object>
    Facemark facemark = FacemarkLBF.create();
    ArrayList<ArrayList<String[]>> allData = null;
    ArrayList<ArrayList<String[]>> allDataModified = null;
    ArrayList<ArrayList<String[]>> allDataRatingsEmoticons = null;
    ArrayList allDataRatingsWord = new ArrayList();
    ArrayList<Object[]> totalRatings = new ArrayList<Object[]>();
    private int ASCII_SIZE = 256;
    public static boolean isChecked = true;

    public Main() {

        initComponents();

        DefaultComboBoxModel dm = (DefaultComboBoxModel) jMoodList.getModel();
        dm.removeAllElements();
        mp3 = new MP3PlayerThreaded();
        HashMap hm = ConnectionManager.getMoodList();
        for (int i = 0; i < hm.size(); i++) {
            dm.addElement(hm.get(i));
        }
        model1 = (DefaultTableModel) jTable5.getModel();
        model1.setRowCount(0);
        DefaultComboBoxModel dml = (DefaultComboBoxModel) jGenreList.getModel();
        dml.removeAllElements();
        Object object = ObjectFileHelper.readObjectFromFile("moodGenreMapping.bin");
        moodGenreMapping = object != null ? (HashMap<String, String>) object : (new HashMap<String, String>());

        for (Map.Entry<String, String> entry : moodGenreMapping.entrySet()) {
         //   System.out.println(entry.getKey() + " = " + entry.getValue());
            model1.addRow(new Object[]{(model1.getRowCount() + 1), entry.getKey(), entry.getValue()});
        }

        HashMap map = ConnectionManager.getGenreList();
        for (int i = 0; i < map.size(); i++) {
            dml.addElement(map.get(i));
        }

        ServerConstants.init();
        refreshMusicList();
        facemark.loadModel(".\\GSOC2017-master\\data\\lbfmodel.yaml");
        new Thread() {

            @Override
            public void run() {
                super.run();
                ServerConstants.init();
            }
        }.start();
        jPanel6.setVisible(false);

    }
    HashMap<String, File[]> genereWiseMapping = new HashMap<>();
    HashMap<String, File> dropDownMusicFiles = new HashMap<>();

    public void refreshMusicList() {
        //Music List
        DefaultListModel dlm = (DefaultListModel) jMusicList.getModel();
        dlm.removeAllElements();

        File[] files = new File(ServerConstants.music).listFiles();
        for (File genreDir : files) {
            System.out.println(genreDir.getAbsolutePath());
            if (genreDir.listFiles() != null) {
                File[] musicFilesArray = genreDir.listFiles();
                genereWiseMapping.put(genreDir.getName(), musicFilesArray);
                for (int j1 = 0; j1 < musicFilesArray.length; j1++) {
                    File musicFile = musicFilesArray[j1];
                    dropDownMusicFiles.put(genreDir.getName() + "-" + musicFile.getName(), musicFile);

                    dlm.addElement(genreDir.getName() + "-" + musicFile.getName());
                }
            }
        }


    }
    boolean breakLoop = false;
    CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt2.xml");
    VideoCapture cap;
    Mat captured;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jpanelbody = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnStop = new javax.swing.JButton();
        ischecked = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnStart1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabelSongTitle = new javax.swing.JLabel();
        jStopMusic = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        liveCamera = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        result = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jMusicList = new javax.swing.JList<>();
        jLabelGenre = new javax.swing.JLabel();
        jLabelMood = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        ProcessImageLable = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        pathField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabelFinal = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jbtnAddGenre = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jMoodList = new javax.swing.JComboBox<>();
        jGenreList = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Face Player ");
        setBackground(new java.awt.Color(102, 102, 102));
        setMinimumSize(new java.awt.Dimension(1366, 768));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jTabbedPane1.setBackground(new java.awt.Color(212, 35, 122));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setAutoscrolls(true);
        jTabbedPane1.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 50, 100));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
       // new DatabaseHelper();

        jpanelbody.setBackground(new java.awt.Color(0, 50, 100));
        jpanelbody.setName("jpanelbody"); // NOI18N

        jPanel5.setBackground(new java.awt.Color(0, 50, 100));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnStop.setBackground(new java.awt.Color(212, 35, 122));
        btnStop.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnStop.setText("Stop");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        ischecked.setBackground(new java.awt.Color(212, 35, 122));
        ischecked.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ischecked.setSelected(true);
        ischecked.setText("Face Tagging");
        ischecked.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ischeckedItemStateChanged(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/play-91-160092.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stopmusic.png"))); // NOI18N

        btnStart1.setBackground(new java.awt.Color(212, 35, 122));
        btnStart1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnStart1.setText("Start Camera");
        btnStart1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStart1ActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/camera-512.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jLabel4.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabelSongTitle.setForeground(new java.awt.Color(0, 255, 0));
        jLabelSongTitle.setText("Music Title");

        jStopMusic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stopmusic.png"))); // NOI18N
        jStopMusic.setToolTipText("Stop Song");
        jStopMusic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStopMusicActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStart1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(147, 147, 147)
                .addComponent(ischecked, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelSongTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jStopMusic, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jStopMusic, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnStart1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ischecked)
                            .addComponent(jLabelSongTitle))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 50, 100));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Live Camera", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Copperplate Gothic Light", 0, 12), new java.awt.Color(212, 35, 122))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N

        liveCamera.setBackground(new java.awt.Color(51, 51, 51));
        liveCamera.setPreferredSize(new java.awt.Dimension(500, 500));
        liveCamera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                liveCameraMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(liveCamera);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(487, 487, 487))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 50, 100));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Process", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Copperplate Gothic Light", 0, 12), new java.awt.Color(212, 35, 122))); // NOI18N
        jPanel8.setForeground(new java.awt.Color(212, 35, 122));
        jPanel8.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        result.setBackground(new java.awt.Color(102, 102, 102));
        result.setPreferredSize(new java.awt.Dimension(500, 500));
        result.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(result);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
        );

        jMusicList.setModel(new javax.swing.DefaultListModel());
        jMusicList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jMusicListValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(jMusicList);

        jLabelGenre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelGenre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelGenre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGenre.setText("Genre");
        jLabelGenre.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelMood.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMood.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMood.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMood.setText("MOOD");
        jLabelMood.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jpanelbodyLayout = new javax.swing.GroupLayout(jpanelbody);
        jpanelbody.setLayout(jpanelbodyLayout);
        jpanelbodyLayout.setHorizontalGroup(
            jpanelbodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelbodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelbodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpanelbodyLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(jpanelbodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpanelbodyLayout.createSequentialGroup()
                                .addComponent(jLabelMood, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jpanelbodyLayout.setVerticalGroup(
            jpanelbodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelbodyLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelbodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpanelbodyLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpanelbodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelMood, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jpanelbody, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1200, 530));

        jTabbedPane1.addTab("Fetch Data", jPanel2);

        jPanel4.setBackground(new java.awt.Color(0, 50, 100));
        jPanel4.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        ProcessImageLable.setBackground(new java.awt.Color(255, 255, 255));
        ProcessImageLable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(212, 35, 122)));

        jTable2.setBackground(new java.awt.Color(102, 102, 102));
        jTable2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable2.setForeground(new java.awt.Color(212, 35, 122));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr. No", "mouthw", "mouthh", "reyew", "reyeh", "leyew", "leyeh", "nosew"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel6.setForeground(new java.awt.Color(212, 35, 122));
        jLabel6.setText("Image Feature Data");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable3.setBackground(new java.awt.Color(102, 102, 102));
        jTable3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr. No", "Disicion"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton3.setBackground(new java.awt.Color(212, 35, 122));
        jButton3.setText("Brows Image");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        pathField1.setText("D:\\work\\ReceivedFiles\\00.jpg");
        pathField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathField1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Final Desicion");

        jLabelFinal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jButton1.setText("Generate Data");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pathField1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ProcessImageLable, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pathField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(ProcessImageLable, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1722, 1722, 1722)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Analysis Data", jPanel4);

        jPanel10.setBackground(new java.awt.Color(0, 50, 100));
        jPanel10.setLayout(null);

        jTable4.setAutoCreateRowSorter(true);
        jTable4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable4.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jTable4.setForeground(new java.awt.Color(212, 35, 122));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sr. No", "mouthw", "mouthh", "reyew", "reyeh", "leyew", "leyeh", "nosew", "dicision"
            }
        ));
        jTable4.setGridColor(new java.awt.Color(212, 35, 122));
        jScrollPane4.setViewportView(jTable4);

        jPanel10.add(jScrollPane4);
        jScrollPane4.setBounds(10, 62, 977, 450);

        jLabel7.setBackground(java.awt.Color.lightGray);
        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(240, 240, 240));
        jLabel7.setText("Pretrained DataSet");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.add(jLabel7);
        jLabel7.setBounds(10, 19, 364, 25);

        jButton4.setBackground(new java.awt.Color(212, 35, 122));
        jButton4.setText("Load Dataset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton4);
        jButton4.setBounds(378, 19, 120, 25);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.jpg"))); // NOI18N
        jPanel10.add(jLabel8);
        jLabel8.setBounds(0, 0, 1450, 600);

        jTabbedPane1.addTab("Pretrainde DataSet", jPanel10);

        jLabel15.setText("Moods");

        jLabel16.setText("Genre to be Played");

        jbtnAddGenre.setText("Add");
        jbtnAddGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddGenreActionPerformed(evt);
            }
        });

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Sr.No", "Mood String", "Genre To Be Played"
            }
        ));
        jScrollPane7.setViewportView(jTable5);

        jMoodList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jGenreList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jMoodList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jGenreList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(jbtnAddGenre)))))
                .addContainerGap(717, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnAddGenre)
                            .addComponent(jMoodList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jGenreList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(29, 29, 29)))
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Player Mood Settings", jPanel7);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 120, 1220, 560);

        jPanel3.setBackground(new java.awt.Color(0, 50, 100));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/music.png"))); // NOI18N

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/musicicon_emoji.png"))); // NOI18N

        jLabel9.setBackground(new java.awt.Color(0, 50, 100));
        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(240, 240, 240));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Face Player");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(79, 79, 79)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(726, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(10, 5, 1220, 97);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.jpg"))); // NOI18N
        //new DatabaseHelper();
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1230, 690);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        StringBuffer infile = FileHelper.getFileContent(ServerConstants.trainddatafilepath);
//        File f=new File(dir);
        String file = StringHelper.n2s(infile);
        String[] con = file.split("\n");
   //     System.out.println(con.length);
        model1 = (DefaultTableModel) jTable4.getModel();
        model1.setRowCount(0);

        String listval;
//        listval= con[0];
//        System.out.println("list"+listval);
//        System.out.println("" + allData.size());
        for (int i = 0; i < con.length; i++) {

//            for (int j = 0; j < arrayList.size(); j++) {
            listval = con[i];
            String[] strings = listval.split(",");
            model1.addRow(new Object[]{(i + 1), strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7]});
//            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

        StringBuffer sb2 = new OsHelper().getCommandOutput("cmd /c taskkill /f /im python.exe", "C:\\windows\\system32", 0);
    }//GEN-LAST:event_formWindowClosing

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String sname = file.getAbsolutePath();
            pathField1.setText(sname);

            BufferedImage bufImage = null;
            try {
                bufImage = ImageIO.read(new File(sname));
                bufImage = resize(bufImage, 480, 640);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            ProcessImageLable.setIcon(new ImageIcon(bufImage));
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void pathField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathField1ActionPerformed

    private void resultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_resultMouseClicked

    private void liveCameraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_liveCameraMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_liveCameraMouseClicked

    private void btnStart1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStart1ActionPerformed
        // TODO add your handling code here:
        new SwingWorker<Object, Object>() {

            @Override
            protected Object doInBackground() {
                try {
//                    Mp3Player audioPlayer = Mp3Player.InitializeAudioPlayer();
                    String wavMusicFilePath = "";

                    cap = new VideoCapture(0);
                    captured = new Mat();
                    if (captured != null) {
                        cap.read(captured);
                        System.out.println("Width/Height " + captured.cols() + "/" + captured.rows());
                        captured.release();
                    }
                    Thread.sleep(5000);
                    while (!breakLoop) {
                        allDataRatingsWord = new ArrayList();
//                       // System.out.println("");
                        captured = new Mat();
                        cap.read(captured);
                        if (captured != null) {

                            Mat gray = new Mat(480, 600, 1);
                            ServerConstants.imgw = captured.size().width();
                            ServerConstants.imgh = captured.size().height();
                            cvtColor(captured, gray, COLOR_BGR2GRAY);
                            equalizeHist(gray, gray);
                            RectVector faces = new RectVector();
                            faceDetector.detectMultiScale(gray, faces);
                            StringBuffer xy = new StringBuffer();
                            StringBuffer hw = new StringBuffer();
                            StringBuffer xx = new StringBuffer();
                            StringBuffer yy = new StringBuffer();
//            System.out.println ("Faces detected: "+faces.size());
                            // Verify is at least one face is detected
                            // With some Facemark algorithms it crashes if there is no faces
                            Frame capt = OpenCVHelper.mat2frame(captured);
                            BufferedImage bicap = OpenCVHelper.frame2buffered(capt);
                            liveCamera.setIcon(new ImageIcon(bicap));
                            if (!faces.empty()) {

                                for (int i = 0; i < faces.size(); i++) {
                                    Rect r = faces.get(i);
                                    rectangle(
                                            captured,
                                            new org.bytedeco.javacpp.opencv_core.Point(r.x(), r.y()),
                                            new org.bytedeco.javacpp.opencv_core.Point(r.x() + r.width(), r.y() + r.height()), Scalar.GREEN, 1, 8, 0);

                                }
           
                                // Variable for landmarks. 
                                // Landmarks for one face is a vector of points
                                // There can be more than one face in the image.
                                Point2fVectorVector landmarks = new Point2fVectorVector();

                                // Run landmark detector
                                boolean success = facemark.fit(captured, faces, landmarks);

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

                                        }

                                        Rect object = faces.get(i);

                                        int h = object.height();
                                        int w = object.width();
                                        int x = object.x();
                                        int y = object.y();

                                        rectangle(
                                                captured,
                                                new org.bytedeco.javacpp.opencv_core.Point(x, y),
                                                new org.bytedeco.javacpp.opencv_core.Point(x + w, y + h), Scalar.GREEN, 1, 8, 0);
                                        hw.append(h + "," + w + "," + x + "," + y);
                                        xy.append(xx + "," + yy);
                                        int[] ip = FaceEmotions.StringBufferToIntegerArray(xy);
                                        double[] hwp = FaceEmotions.StringBufferTODoubleArray(hw);
                                        double[] feature = new double[7];
                                      //  System.out.println("Mouth width,mouth height,right eye width,right eye heigth,left eye width,left eye heigth,nose width ");
                                        int[] featurePointIndex = {36, 39, 38, 40, 42, 45, 44, 46, 21, 22, 54, 48, 57, 51, 31, 35};
                                        int ratio = 0;
                                        int faceIndex = 1;
                                        //System.out.println("p.length " + p.length + " landmarks.size() " + landmarks.size() + " ip " + ip.length + " " + faces.size());
                                        feature[0] = (hwp[ratio + 1]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[11], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[10], ip, faceIndex - 1)));

                                        feature[1] = (hwp[ratio]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[13], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[12], ip, faceIndex - 1)));
                                        feature[2] = (hwp[ratio] + 1) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[15], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[14], ip, faceIndex - 1)));
                                        feature[3] = (hwp[ratio + 1]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[1], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[0], ip, faceIndex - 1)));
                                        feature[4] = (hwp[ratio]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[3], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[2], ip, faceIndex - 1)));
                                        feature[5] = (hwp[ratio + 1]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[4], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[5], ip, faceIndex - 1)));
                                        feature[6] = (hwp[ratio]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[7], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[6], ip, faceIndex - 1)));
                                        System.out.println(java.util.Arrays.toString(feature));

                                        if (ischecked.isSelected()) {
                                            Mat m = new Mat(gray, object);
                                            imwrite(ServerConstants.writefilepath, m);
                                            String detectedMood = getResult.getSVMOutput(ServerConstants.writefilepath, java.util.Arrays.toString(feature));

                                            if (detectedMood != null) {
                                                allDataRatingsWord.add(detectedMood);
                                                System.out.println(detectedMood);
                                                putText(captured, detectedMood, new Point(x + 10, y + 10), 0, 1, Scalar.RED);
                                                changeSong(detectedMood);
                                                //set music title on label
                                            }
                                            m.release();
                                        }

//                            System.out.println("pt = "+pt.length);
//                            System.out.println("hwp "+hwp.length);
                                        xy.delete(0, xy.length());
                                        hw.delete(0, hw.length());
                                        if (ischecked.isSelected()) {
                                            Mat img1 = null;// SingleImagePoints.drawSumMarks(pt, captured, hwp);
                                            if (img1 != null) // Display results 
                                            {
                                                capt = OpenCVHelper.mat2frame(img1);
                                                bicap = OpenCVHelper.frame2buffered(capt);

                                            }
                                            capt = OpenCVHelper.mat2frame(captured);
                                            bicap = OpenCVHelper.frame2buffered(capt);
                                            result.setIcon(new ImageIcon(bicap));
                                            img1 = null;
                                        }
                                        capt = null;
                                        bicap = null;
                                    }

                                }
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
                                    if (ischecked.isSelected()) {
                                        Mat m = new Mat(gray, object);
                                        imwrite(ServerConstants.writefilepath, m);
                                        String detectedMood = getResult.main(ServerConstants.writefilepath);

                                        if (detectedMood != null) {
                                            allDataRatingsWord.add(detectedMood);
                                            System.out.println(detectedMood);
                                            putText(captured, detectedMood, new Point(x + 10, y + 10), 0, 1, Scalar.RED);
                                            changeSong(detectedMood);
                                            //set music title on label
                                        }
                                        m.release();
                                    }
                                    object.deallocate();
                                }
                                gray.release();
                                captured.release();
                                faces.deallocate();
                                xy.delete(0, xy.length());
                                hw.delete(0, hw.length());
                                yy.delete(0, yy.length());
                                xx.delete(0, xx.length());
                            }
                        }

                    }
                    if (cap != null) {
                        cap.release();
                    }
                    //            private void rectangle(Mat captured, opencv_core.Point point, opencv_core.Point point0, Scalar GREEN, int i, int i0, int i1) {
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

        }.execute();
    }//GEN-LAST:event_btnStart1ActionPerformed
    private void playMusic(File selectedMusicFile) {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run(); //To change body of generated methods, choose Tools | Templates.
//                if (mp3 != null && mp3.isPlaying()) {
//                    mp3.stop();
//                }
//                 mp3 = new Mp3Player(selectedMusicFile.getAbsolutePath().toString());
//                mp3.play();
//            }
//        }.start();

//        jStopMusicActionPerformed(null);
        if (mp3 != null) {
            if (mp3.songTotalLength > 0) {
                mp3.Stop();

//                mp3 = new Mp3Player(selectedMusicFile.getAbsolutePath().toString());
//                mp3.play();
            }
            mp3.Play(selectedMusicFile.getAbsolutePath());

        } else {
            mp3 = new MP3PlayerThreaded();
            mp3.Play(selectedMusicFile.getAbsolutePath());
            System.out.println("Another song is already playing");
        }
//            mp3.stop();
//    }

//        else {
//            mp3 = new Mp3Player(selectedMusicFile.getAbsolutePath().toString());
//        mp3.play();
//        
//    }
    }
    private void ischeckedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ischeckedItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ischeckedItemStateChanged

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed

        if (cap != null) {
            cap.release();
            breakLoop = true;
            cap = null;
        }
        //        if (faceDetector != null || eyeDetector != null) {
        //            faceDetector.empty();
        //            eyeDetector.empty();
        //        }
    }//GEN-LAST:event_btnStopActionPerformed
    HashMap<String, String> moodGenreMapping = new HashMap<>();
    private void jbtnAddGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddGenreActionPerformed

        if (jMoodList.getSelectedIndex() == -1) {
            return;
        }
        if (jGenreList.getSelectedIndex() == -1) {
            return;
        }
        String mood = jMoodList.getSelectedItem().toString();
        String genre = jGenreList.getSelectedItem().toString();

//        music = "D:\\work\\project\\FacePlayer\\music\\" + "\\" + mood + "\\" + genre + "\\";
//        ServerConstants.music = music;
//        System.out.println("music path" + music);
        moodGenreMapping.put(mood, genre);

        model1 = (DefaultTableModel) jTable5.getModel();
//        model1.setRowCount(0);

        model1.addRow(new Object[]{(model1.getRowCount() + 1), mood, genre});
        ObjectFileHelper.writeObject2File(moodGenreMapping, "moodGenreMapping.bin");

    }//GEN-LAST:event_jbtnAddGenreActionPerformed

    private void jMusicListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jMusicListValueChanged
        File audioFile = (File) dropDownMusicFiles.get(jMusicList.getSelectedValue());
        jLabelSongTitle.setText(audioFile.getName());
        playMusic(audioFile);


    }//GEN-LAST:event_jMusicListValueChanged

    private void jStopMusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStopMusicActionPerformed
        // TODO add your handling code here:

        if (mp3 != null) {
            mp3.Stop();
        }
    }//GEN-LAST:event_jStopMusicActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        model2 = (DefaultTableModel) jTable2.getModel();
        model2.setRowCount(0);
        model3 = (DefaultTableModel) jTable3.getModel();
        model3.setRowCount(0);
        StringBuffer xy = new StringBuffer();
        StringBuffer hw = new StringBuffer();
        StringBuffer xx = new StringBuffer();
        StringBuffer yy = new StringBuffer();

        String path = pathField1.getText();
        Mat captured = OpenCVHelper.file2mat(path);
        ProcessImageLable.setIcon(new ImageIcon(OpenCVHelper.frame2buffered(OpenCVHelper.mat2frame(captured))));

        ServerConstants.imgw = captured.size().width();
        ServerConstants.imgh = captured.size().height();
        Mat gray = new Mat(480, 600, 1);

        cvtColor(captured, gray, COLOR_BGR2GRAY);
        equalizeHist(gray, gray);
        RectVector faces = new RectVector();
        faceDetector.detectMultiScale(gray, faces);
        if (!faces.empty()) {
            Point2fVectorVector landmarks = new Point2fVectorVector();

            // Run landmark detector
            boolean success = facemark.fit(captured, faces, landmarks);

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
                    }

                    Rect object = faces.get(i);
                    int h = object.height();
                    int w = object.width();
                    int x = object.x();
                    int y = object.y();

                    rectangle(
                            captured,
                            new org.bytedeco.javacpp.opencv_core.Point(x, y),
                            new org.bytedeco.javacpp.opencv_core.Point(x + w, y + h), Scalar.GREEN, 1, 8, 0);
                    hw.append(h + "," + w + "," + x + "," + y);
                    xy.append(xx + "," + yy);
                    int[] ip = FaceEmotions.StringBufferToIntegerArray(xy);
                    double[] hwp = FaceEmotions.StringBufferTODoubleArray(hw);
                    double[] feature = new double[7];
                    System.out.println("Mouth width,mouth height,right eye width,right eye heigth,left eye width,left eye heigth,nose width ");
                    int[] featurePointIndex = {36, 39, 38, 40, 42, 45, 44, 46, 21, 22, 54, 48, 57, 51, 31, 35};
                    int ratio = 0;
                    int faceIndex = 1;
                    System.out.println("p.length " + p.length + " landmarks.size() " + landmarks.size() + " ip " + ip.length + " " + faces.size());
                    feature[0] = (hwp[ratio + 1]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[11], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[10], ip, faceIndex - 1)));

                    feature[1] = (hwp[ratio]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[13], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[12], ip, faceIndex - 1)));
                    feature[2] = (hwp[ratio] + 1) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[15], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[14], ip, faceIndex - 1)));
                    feature[3] = (hwp[ratio + 1]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[1], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[0], ip, faceIndex - 1)));
                    feature[4] = (hwp[ratio]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[3], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[2], ip, faceIndex - 1)));
                    feature[5] = (hwp[ratio + 1]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[4], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[5], ip, faceIndex - 1)));
                    feature[6] = (hwp[ratio]) / (SingleImagePoints.getDistance(SingleImagePoints.getPoint(featurePointIndex[7], ip, faceIndex - 1), SingleImagePoints.getPoint(featurePointIndex[6], ip, faceIndex - 1)));
                    model2.addRow(new Object[]{(i + 1), feature[0], feature[1], feature[2], feature[3], feature[4], feature[5], feature[6]});

                    System.out.println(java.util.Arrays.toString(feature));
                    Mat m = new Mat(gray, object);
                    imwrite(ServerConstants.writefilepath, m);
                    String detectedMood = getResult.getSVMOutput(ServerConstants.writefilepath, java.util.Arrays.toString(feature));
                    model3.addRow(new Object[]{(i + 1), detectedMood});

                }
            }
        }
        result.setIcon(new ImageIcon(OpenCVHelper.frame2buffered(OpenCVHelper.mat2frame(captured))));
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    public void changeSong(String detectedMood) {
        try {
            // TODO add your handling code here:
            File selectedSong = selectSong(detectedMood, genereWiseMapping, StringHelper.n2s(moodGenreMapping.get(detectedMood)));
            if (selectedSong != null) {
                jLabelSongTitle.setText(selectedSong.getName());
                for (int i = 0; i < jMusicList.getModel().getSize(); i++) {

                    if (jMusicList.getModel().getElementAt(i).toString().indexOf(selectedSong.getName()) != -1) {
                        jMusicList.setSelectedIndex(i);
                    }
                    jMusicList.ensureIndexIsVisible(jMusicList.getSelectedIndex());
                    jMusicList.repaint();
                }
                playMusic(selectedSong);

            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (LineUnavailableException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File selectSong(String detectedMood, HashMap<String, File[]> genereWiseMapping, String playbackGenre)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (playbackGenre == null) {
            if (detectedMood.equalsIgnoreCase("happy")) {
                playbackGenre = "party";
            } else if (detectedMood.equalsIgnoreCase("angry")) {
                playbackGenre = "jazz";
            } else if (detectedMood.equalsIgnoreCase("sad")) {
                playbackGenre = "classic";
            } else if (detectedMood.equalsIgnoreCase("neutral")) {
                playbackGenre = "pop";
            }

        }
        jLabelGenre.setText(playbackGenre);
        jLabelMood.setText(detectedMood);
        File[] list = genereWiseMapping.get(playbackGenre);
        System.out.println("detectedMood " + detectedMood + " playbackGenre " + playbackGenre + " " + list);
        if (list != null) {
            File selectedMusicFile = list[(int) ((list.length) * Math.random())];
            return selectedMusicFile;
        } else {
            return null;
        }
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ProcessImageLable;
    private javax.swing.JButton btnStart1;
    private javax.swing.JButton btnStop;
    private javax.swing.JCheckBox ischecked;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jGenreList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFinal;
    private javax.swing.JLabel jLabelGenre;
    private javax.swing.JLabel jLabelMood;
    private javax.swing.JLabel jLabelSongTitle;
    private javax.swing.JComboBox<String> jMoodList;
    private javax.swing.JList<String> jMusicList;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JButton jStopMusic;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JButton jbtnAddGenre;
    private javax.swing.JPanel jpanelbody;
    private javax.swing.JLabel liveCamera;
    private javax.swing.JTextField pathField1;
    private javax.swing.JLabel result;
    // End of variables declaration//GEN-END:variables

    private String getMaxEmotion(String str) {
        int count[] = new int[ASCII_SIZE];

        // Construct character count array from the input
        // string.
        int len = str.length();
        for (int i = 0; i < len; i++) {
            count[str.charAt(i)]++;
        }

        int max = -1; // Initialize max count
        char result = ' '; // Initialize result

        // Traversing through the string and maintaining
        // the count of each character
        for (int i = 0; i < len; i++) {
            if (max < count[str.charAt(i)]) {
                max = count[str.charAt(i)];
                result = str.charAt(i);
            }
        }

        return StringHelper.n2s(result);
    }
}
