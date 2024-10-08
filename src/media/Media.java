package media;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Media {

    private String path;

    public Media(String path) {
        this.path = path;
    }

    public void play() {
        try {
            FileInputStream File = new FileInputStream(this.path);
            Player Player = new Player(File);
            Player.play();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo MP3 no encontrado: " + e.getMessage());
        } catch (JavaLayerException e) {
            System.out.println("Error al reproducir el MP3: " + e.getMessage());
        }
    }

    public void pause() {

    }

    public void next() {

    }

    public void previous() {

    }

}
