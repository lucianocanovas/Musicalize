import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Song extends Media {

    private String name;
    private String artist;

    public Song(String path, String name, String artist) {
        super(path);
        this.name = name;
        this.artist = artist;
    }

    @Override
    public void play() {
        Scanner input = new Scanner(System.in);
        System.out.println("║ SONG: "+ this.name +" - "+ this.artist);
        mediaThread = new Thread(() -> {
            try {
                FileInputStream file = new FileInputStream(this.path);
                player = new AdvancedPlayer(file);
                player.play();
            } catch (FileNotFoundException | JavaLayerException e) {
                System.out.println("[!] ERROR: " + e.getMessage());
                stop();
            }
        });
        mediaThread.start();
        System.out.println("╠═ [~] PRESS ANY KEY TO STOP THE PLAYER");
        System.out.print("║ ");
        input.nextLine();
        stop();
    }

}
