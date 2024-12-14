package model;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class SongManager {
    private Map<Integer, Song> songs = new HashMap<>();

    public final String SAVEDGAMENAME = ".savedGame.ser";

    public boolean loadSongs(boolean checkTemp) {
        boolean tempFile = false;
        File directory = new File(DefaultSongs.SONGSLOCATION);
        String[] extensions = new String[] { "ser" };
        List<File> files = (List<File>) FileUtils.listFiles(directory, extensions, true);
        for (File file : files) {
            if (file.getName().equals(SAVEDGAMENAME)) {
                tempFile = true;
                continue;
            }
            if (!checkTemp) {
                addSong((Song) Serializer.deserialize(file.getPath(), false));
            }
        }
        return tempFile;
    }

    public void loadAndStartSavedGame() {
        SavedGame savedGame = (SavedGame) Serializer.deserialize(DefaultSongs.SONGSLOCATION + SAVEDGAMENAME, true);
        MusicGame.getInstance().startLevel(savedGame.getSongID());
        MusicGame.getInstance().setInitialTimecode(savedGame.getTimeCode());
        MusicGame.getInstance().getScoreManager().setScore(savedGame.getScore());
    }

    public void addSong(Song song) {
        songs.put(song.getId(), song);
    }

    public Song getSong(int id) {
        return songs.get(id);
    }

    public Collection<Song> getSongs() {
        return songs.values();
    }

    public void saveCurrentSong() {
        Song currentSong = NoteProcessor.getInstance().getActiveSong();
        Serializer.serialize(currentSong, DefaultSongs.SONGSLOCATION, currentSong.getTitle() + ".ser");
    }

}
