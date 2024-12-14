import org.junit.jupiter.api.Test;

import model.LevelDesigner;
import model.Note;
import model.Song;
import model.enums.KeyCodes;
import model.enums.SongDifficulties;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DesignerTests {

    @Test
    public void test_basic_level_designer(){
        LevelDesigner.getInstance().reset();
        LevelDesigner.getInstance();
        LevelDesigner designer = LevelDesigner.getInstance();
        assertNotNull(designer);
        assertNull(designer.getCurrentSong());
        designer.saveCurrentSong();
    }

    @Test
    public void test_song_methods() {
        Song song = new Song("source", "title", 1.0, List.of(), SongDifficulties.EASY, 0, 0);
        assertEquals("source", song.getSource());
        assertEquals("title", song.getTitle());
        assertEquals(1.0, song.getLength());
        assertEquals(List.of(), song.getNotes());
        assertEquals(SongDifficulties.EASY, song.getDifficulty());
        assertEquals(0, song.getId());
        assertEquals(0, song.getHighScore());
        song.setSource("new source");
        assertEquals("./src/media/new source", song.getSource());
        song.setTitle("new title");
        assertEquals("new title", song.getTitle());
        song.setLength(2.0);
        assertEquals(2.0, song.getLength());
        List<Note> note = List.of(new Note(0, 1, KeyCodes.NUMPAD1));
        song.setNotes(note);
        assertEquals(note, song.getNotes());
        song.setDifficulty(SongDifficulties.MEDIUM);
        assertEquals(SongDifficulties.MEDIUM, song.getDifficulty());
        song.setId(1);
        assertEquals(1, song.getId());
        song.setHighScore(100);
        assertEquals(100, song.getHighScore());
    }

    @Test
    public void test_note_methods() {
        Note note = new Note(0, 1, KeyCodes.NUMPAD1);
        assertEquals(0, note.getTimeCode());
        assertEquals(1, note.getDuration());
        assertEquals(KeyCodes.NUMPAD1, note.getKeyCode());
        note.setTimeCode(1);
        assertEquals(1, note.getTimeCode());
        note.setDuration(2);
        assertEquals(2, note.getDuration());
        note.setKeyCode(KeyCodes.NUMPAD2);
        assertEquals(KeyCodes.NUMPAD2, note.getKeyCode());
        assertEquals(note.compareTo(new Note(0, 0, null)), 1);
    }

}
