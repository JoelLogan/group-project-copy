package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.observers.DefaultObserverImplementer;
import model.observers.ErrorObserver;

// The file related methods have not been tested.
public class LevelDesigner {
    private static LevelDesigner instance = null;
    private SongManager songManager;
    private ErrorObserver errorObserver;
    public final List<List<Note>> notes = new ArrayList<>();

    private LevelDesigner() {
        songManager = new SongManager();
        DefaultObserverImplementer implementer = new DefaultObserverImplementer();
        setErrorObserver(implementer);
        for (int i = 0; i < 9; i++) {
            notes.add(new ArrayList<>());
        }
    }

    public void loadSong(File songFile) {
        try {
            songManager.loadSong(songFile);
            getCurrentSong().getNotes().forEach(n -> notes
                    .get(n.getKeyCode().ordinal())
                    .add(n));
        } catch (IOException i) {
            errorObserver.notifyError(i.getMessage());
        } catch (ClassNotFoundException c) {
            errorObserver.notifyError(c.getMessage());
        }
    }

    public void newSong(File newFile) {
        songManager.newSong(newFile);
    }

    public Song getCurrentSong() {
        return songManager.getCurrentSong();
    }

    public void saveCurrentSong() {
        if (getCurrentSong() == null) {
            return;
        }
        if (getCurrentSong().getNotes() != null) {
            getCurrentSong().getNotes().clear();
        }
        notes.forEach(l -> l.forEach(n -> getCurrentSong().getNotes().add(n)));

        getCurrentSong().getNotes().forEach(n -> System.out.println("new Note(" + (int)n.getTimeCode() + ", " + (int)n.getDuration() + ", KeyCodes." + n.getKeyCode() + "),"));
        songManager.saveCurrentSong();
    }

    public void setErrorObserver(ErrorObserver observer) {
        errorObserver = observer;
    }

    public static LevelDesigner getInstance() {
        if (instance == null) {
            instance = new LevelDesigner();
        }
        return instance;
    }

    public void reset() {
        instance = null;
    }
}
