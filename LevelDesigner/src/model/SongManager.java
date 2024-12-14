package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.enums.SongDifficulties;

public class SongManager {
    private Song currentSong;
    private File currentSongFile;

    public void loadSong(File songFile) throws IOException, ClassNotFoundException {
        currentSong = ((Song) Serializer.deserialize(songFile.getPath()));
        currentSongFile = songFile;
    }

    public void newSong(File newFile) {
        currentSongFile = newFile;
        long randomNumber = (long) (Math.random() * (999999 - 1000 + 1)) + 1000;
        currentSong = new Song("./src/media/Not Set", newFile.getName(), 0.0, new ArrayList<Note>(), SongDifficulties.EASY, (int) randomNumber, 0);
        saveCurrentSong();
    }

    public Song getCurrentSong() {
        if (currentSong != null) {
            return currentSong;
        }
        return null;
    }

    public void saveCurrentSong() {
        System.out.println("Saving song to " + currentSongFile.getAbsolutePath());
        System.out.println("Song name: " + currentSongFile.getName());
        Serializer.serialize(currentSong, currentSongFile.getAbsolutePath());
    }

}
