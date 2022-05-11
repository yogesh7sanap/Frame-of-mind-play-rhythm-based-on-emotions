/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

/**
 *
 * @author technowings
 */
// Java program to play an Audio 
// file using Clip Object 
import com.constant.ServerConstants;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;




public class SimpleAudioPlayer {

    // to store current position 
    Long currentFrame;
    Clip clip;
    Boolean playing = false;
    // current status of clip 
    String status;
    AudioInputStream audioInputStream;
    static String filePath;
    static String music = ServerConstants.music;
//    static String sad = ServerConstants.sadMusic;
//    static String happy = ServerConstants.happyMusic;
    static SimpleAudioPlayer player;// = new SimpleAudioPlayer(sad);

    // constructor to initialize streams and clip 
    public SimpleAudioPlayer(String wavMusicFilePath) {
        try {
            System.out.println("wavMusicFilePath: " + wavMusicFilePath);

            audioInputStream = AudioSystem.getAudioInputStream(new File(wavMusicFilePath).getAbsoluteFile());

            // create clip reference 
            clip = AudioSystem.getClip();

            // open audioInputStream to the clip 
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // create AudioInputStream object 

    }

    public static SimpleAudioPlayer InitializeAudioPlayer() {
        try {
            player = new SimpleAudioPlayer(music + "1.wav");
            player.pause();

            SimpleAudioPlayer runner = new SimpleAudioPlayer(music + "1.wav");
            runner.pause();
            return runner;
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
        return null;
    }

    public static double getRandomDoubleBetweenRange(double min, double max) {

        double x = (Math.random() * ((max - min) + 1)) + min;

        return x;

    }

    // Work as the user enters his choice 
    public static File gotoChoice(File selectedMusicFile)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
       
         if (player != null && player.isPlaying()) {
                    player.stop();
                }

        player = new SimpleAudioPlayer(selectedMusicFile.getAbsolutePath());
        player.play();
        if (true) {
            return selectedMusicFile;
        }

//pick up randome number from 1 to 3 and play randome song
//        double songNum = getRandomDoubleBetweenRange(1, 3);
//
//        switch (detectedMood) {
//            case "angry":
//                if (player.isPlaying() == true) {
//                    player.pause();
//                }
//
//                System.out.println("playing angry " + StringHelper.n2i(songNum) + ".wav");
//                if (player != null && player.isPlaying()) {
//                    player.stop();
//                }
//
//                player = new SimpleAudioPlayer(selectedMusicFile.getAbsolutePath());
//                player.play();
//                break;
//            case "sad":
//                if (player.isPlaying() == true) {
//                    player.pause();
//                }
//
//                System.out.println("playing angry " + StringHelper.n2i(songNum) + ".wav");
//                player = new SimpleAudioPlayer(music + StringHelper.n2i(songNum) + ".wav");
//                player.play();
//                break;
//            case "happy":
//                if (player.isPlaying() == true) {
//                    player.pause();
//                }
//
//                System.out.println("playing angry " + StringHelper.n2i(songNum) + ".wav");
//                player = new SimpleAudioPlayer(music + StringHelper.n2i(songNum) + ".wav");
//                player.play();
//                break;
//
//            case "stop":
//                if (player.isPlaying() == true) {
//                    player.pause();
//                }
//
//                break;
////            case "else":
////                System.out.println("Enter time (" + 0
////                        + ", " + clip.getMicrosecondLength() + ")");
////                Scanner sc = new Scanner(System.in);
////                long c1 = sc.nextLong();
////                jump(c1);
////                break;
//
//        }
        return null;
    }

    // Method to play the audio 
    public void play() {
        //start the clip 
        clip.start();
        playing = true;

        status = "play";
    }

    public boolean isPlaying() {
        return playing;
    }

    // Method to pause the audio 
    public void pause() {
//        if (status.equals("paused")) {
//            System.out.println("audio is already paused");
//            return;
//        }
        this.currentFrame
                = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Method to resume the audio 
    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        if (status.equals("play")) {
            System.out.println("Audio is already "
                    + "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    // Method to restart the audio 
    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    // Method to stop the audio 
    public void stop() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        playing = false;
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    // Method to jump over a specific part 
    public void jump(long c) throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        if (c > 0 && c < clip.getMicrosecondLength()) {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }

    // Method to reset audio stream 
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void main(String args[]) {
        player = new SimpleAudioPlayer(music + "1.wav");
        // player.pause();
    }
}
