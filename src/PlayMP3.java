import jaco.mp3.player.MP3Player;

import java.io.File;

public class PlayMP3 {
    public static void play(String filename) {
        Thread t = new Thread(() -> {
            new MP3Player(new File(filename)).play();
        });
        t.start();
    }

    public static void wordTrue() {
        Thread t = new Thread(() -> new MP3Player(new File("D:\\java_project\\LexicalUniverse\\src\\other_audio\\yes.mp3")).play());
        t.start();
    }
}