package model.observers;

import java.util.List;

import model.Note;

public interface NoteUpdateObserver {
    public void updateNoteGUI(List<Note> notes, boolean visible);
}
