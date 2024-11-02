import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Podcast extends Media implements Playable {

    private String title;
    private String host;

    public Podcast(String path, String title, String host) {
        super(path);
        this.title = title;
        this.host = host;
    }

    @Override
    public void play() {
        Scanner input = new Scanner(System.in);
        mediaThread = new Thread(() -> {
            try {
                FileInputStream file = new FileInputStream(this.path);
                player = new AdvancedPlayer(file);
                player.play();
            } catch (FileNotFoundException | JavaLayerException e) {
                System.out.println("║ [!] ERROR: " + e.getMessage());
            }
        });
        mediaThread.start();
        System.out.println("║ PODCAST: "+ this.title +" - "+ this.host);
        System.out.println("╠═ [~] PRESS ANY KEY TO STOP");
        System.out.print("║ [#] ⇒ ");
        input.nextLine();
        stop();
    }
}
