package helper;

/**
 *
 * @author kiaan
 */
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.PlayerApplet;

public class Mp3Player {

    private String filename;
    private Player player;

    //  private Thread playerThread;
    boolean loop, playing=false;

    static int mp3playing = 0;

    public Mp3Player() {
    }

    public Mp3Player(String filename) {
        this.filename = filename;
        this.loop = loop;
    }

    public boolean isPlaying() {
        return playing;
    }

    public File gotoChoice(File selectedMusicFile) {
//        if (mp3 != null ) {
//            mp3.stop();
//        }
//        mp3 = new Mp3Player(selectedMusicFile.getAbsolutePath().toString());
//        mp3.play();

        if (true) {
            return selectedMusicFile;
        }
        return null;
    }
    Thread playerThread = null;

    public void play() {

        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    new FileInputStream(filename));
            player = new Player(buffer);

            playerThread = new PlayerThread(this.player);
            playerThread.start();
            playing=true;
//            playerThread.start();
        } catch (Exception e) {

            System.out.println(e);
        }

    }

    class PlayerThread extends Thread {

        Player player = null;

        public PlayerThread(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            try {
                super.run(); //To change body of generated methods, choose Tools | Templates.
                if(player!=null)
                     player.play();
                playing = true;
            } catch (Exception ex) {
                Logger.getLogger(Mp3Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void stop() {
//        playing = false;

        player.close();
        playerThread.interrupt();
        playerThread.stop();
        playing=false;
    }

    public static void main(String[] args) {

        Mp3Player mp3 = new Mp3Player("D:\\work\\project\\FacePlayer\\music\\genre-songs\\party\\02 Toota Jo Kabhi Tara - Flying Jatt (Atif Aslam) 190Kbps.mp3");
        //  Thread t = new Thread(mp3);
        // t.start();
        mp3.play();
    }
}
