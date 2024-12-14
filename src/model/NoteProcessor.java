package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.enums.KeyCodes;

public class NoteProcessor {

    private static NoteProcessor instance = null;
    private Song activeSong;
    private List<ArrayList<Note>> queues = new ArrayList<>();
    private Map<KeyCodes, ArrayList<Note>> keyCodeQueueMap;

    public List<Note> getVisibleNotes(double timeCode, double howFarAhead) {
        ArrayList<Note> visibleNotes = new ArrayList<>();
        for (ArrayList<Note> queue : queues) {
             queue.stream().filter(n -> {
                if (n.getTimeCode() <= (timeCode + howFarAhead) && (n.getTimeCode() + n.getDuration()) >= timeCode) {
                    return true;
                }
                return false;
            }).forEach(n -> visibleNotes.add(n));
        }
        return visibleNotes;
    }

    private void loadNotes() {
        activeSong.getNotes().forEach(note -> {
            ArrayList<Note> queue = keyCodeQueueMap.get(note.getKeyCode());
            queue.add(note);
        });
    }

    public void setActiveSong(Song song) {
        queues.forEach(q -> q.clear());
        activeSong = song;
        loadNotes();
        MusicGame.getInstance().getScoreManager().setHighScore(activeSong.getHighScore());
    }

    public Song getActiveSong() {
        return activeSong;
    }

    private NoteProcessor() {
        for (int i = 0; i < 9; i++) {
            queues.add(new ArrayList<>());
        }
        keyCodeQueueMap = Map.of(
            KeyCodes.NUMPAD1, queues.get(0),
            KeyCodes.NUMPAD2, queues.get(1),
            KeyCodes.NUMPAD3, queues.get(2),
            KeyCodes.NUMPAD4, queues.get(3),
            KeyCodes.NUMPAD5, queues.get(4),
            KeyCodes.NUMPAD6, queues.get(5),
            KeyCodes.NUMPAD7, queues.get(6),
            KeyCodes.NUMPAD8, queues.get(7),
            KeyCodes.NUMPAD9, queues.get(8));
    }

    public static NoteProcessor getInstance() {
        if (instance == null) {
            instance = new NoteProcessor();
        }
        return instance;
    }

    public void reset() {
        instance = null;
    }

    public void softReset() {
        for (ArrayList<Note> queue : queues) {
            queue.clear();
        }
    }
}
