import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import model.DefaultSongs;
import model.MusicGame;
import model.Note;
import model.NoteProcessor;
import model.PowerupManager;
import model.SavedGame;
import model.ScoreManager;
import model.Song;
import model.enums.GameStates;
import model.enums.KeyCodes;
import model.enums.Powerups;
import model.enums.SongDifficulties;
import model.observers.DefaultObserverImplementer;

public class MusicGameTests {

    @Test
    public void new_music_game() {
        MusicGame.getInstance();
        MusicGame.getInstance().reset();
        MusicGame game = MusicGame.getInstance();
        new DefaultSongs();
        DefaultSongs.saveDefaultSongs();
        assertTrue(game.getState().equals(GameStates.INACTIVE));
        game.startLevel(0);
        game.pollVisibleNotes(0, true);
        List<Note> notes = NoteProcessor.getInstance().getVisibleNotes(0, game.VISIBLE_NOTE_TIME);
        assertTrue(game != null);
        assertTrue(notes.size() > 0);
        assertTrue(game.getState().equals(GameStates.PLAYING));
        game.pauseLevel();
        assertTrue(game.getState().equals(GameStates.PAUSED));
        game.resumeLevel();
        assertTrue(game.getState().equals(GameStates.RESUME));
        game.finishLevel();
        assertTrue(game.getState().equals(GameStates.SCORES));
        game.exitLevel();
        assertTrue(game.getState().equals(GameStates.INACTIVE));
        game.setInitialTimecode(5);
        assertTrue(game.getInitialTimecode() == 5);
    }

    @Test
    public void test_powerup_observer() {
        MusicGame game = MusicGame.getInstance();
        DefaultObserverImplementer observer = new DefaultObserverImplementer();
        game.getPowerupManager().setPowerupObserver(observer);
        observer.updatePowerups(null, true);
        assertTrue(game.getPowerupManager().getPowerupObserver() != null);
    }

    @Test
    public void test_powerup_countdown() {
        MusicGame game = MusicGame.getInstance();
        PowerupManager manager = new PowerupManager();
        manager.usePowerup();
        assertTrue(game.getPowerupCountdown() == 0);
        manager.addPowerup();
        manager.addPowerup();
        manager.addPowerup();
        manager.addPowerup();
        assertTrue(manager.getAvailablePowerups().size() == 3);
        manager.usePowerup();
        assertTrue(game.getPowerupCountdown() == 5000);
        game.startLevel(0);
        game.pollVisibleNotes(0, true);
        assertTrue(game.getPowerupCountdown() == 4999);
        game.setPowerupCountdown(0);
        assertTrue(game.getPowerupCountdown() == 0);
        manager.clearPowerups();
        assertTrue(manager.getAvailablePowerups().size() == 0);
        manager.setPowerups(List.of(Powerups.FREE_MISS, Powerups.XP, Powerups.PERSPECTIVE));
        assertTrue(manager.getAvailablePowerups().size() == 3);
    }

    @Test
    public void test_all_powerups() {
        PowerupManager manager = new PowerupManager();
        for (int i = 0; i < 100; i++) {
            manager.addPowerup();
            manager.usePowerup();
        }
        assertTrue(manager.getAvailablePowerups().size() == 0);
    }

    @Test
    public void test_add_streak() {
        MusicGame game = MusicGame.getInstance();
        game.getPowerupManager().clearPowerups();
        ScoreManager manager = new ScoreManager();
        manager.addScore(200);
        manager.addScore(1);
        assertTrue(game.getPowerupManager().getAvailablePowerups().size() == 1);
    }

    @Test
    public void test_correct_scoring() {
        MusicGame game = MusicGame.getInstance();
        game.getScoreManager().activateScoring(1, 1);
        game.getScoreManager().updateScore();
        game.getScoreManager().deactivateScoring(1);
        assertEquals(1, game.getScoreManager().getScore());
        game.setCheatMode(true);
        game.getScoreManager().activateScoring(1, -1);
        game.getScoreManager().updateScore();
        game.getScoreManager().deactivateScoring(1);
        assertEquals(2, game.getScoreManager().getScore());
        game.setCheatMode(false);
        game.getScoreManager().setNoMisses(true);
        game.getScoreManager().activateScoring(1, -1);
        game.getScoreManager().updateScore();
        game.getScoreManager().deactivateScoring(1);
        assertEquals(2, game.getScoreManager().getScore());
        game.getScoreManager().setNoMisses(false);
        game.getScoreManager().setDoubleScoring(true);
        game.getScoreManager().activateScoring(1, 1);
        game.getScoreManager().updateScore();
        game.getScoreManager().deactivateScoring(1);
        assertEquals(4, game.getScoreManager().getScore());
    }

    @Test
    public void test_cheat_mode_state() {
        MusicGame game = MusicGame.getInstance();
        game.setCheatMode(true);
        assertTrue(game.isCheatMode());
        game.setCheatMode(false);
        assertFalse(game.isCheatMode());
    }

    @Test
    public void regenerate_default_songs() {
        File directory = new File("./src/data");
        String[] extensions = new String[] { "ser" };
        List<File> files = (List<File>) FileUtils.listFiles(directory, extensions, true);
        for (File file : files) {
            file.delete();
        }
        MusicGame game = MusicGame.getInstance();
        game.reset();
        assertTrue(game != null);
    }

    @Test
    public void keycodes_get_keys() {
        assertTrue(KeyCodes.NUMPAD1.getValue() == 97);
        assertTrue(KeyCodes.NUMPAD9.getValue() == 105);
        assertTrue(KeyCodes.ESC.getValue() == 27);
        assertTrue(KeyCodes.NUMPAD1.getColorBlue() == 0 / 255.0);
        assertTrue(KeyCodes.NUMPAD1.getColorGreen() == 255 / 255.0);
        assertTrue(KeyCodes.NUMPAD1.getColorRed() == 128 / 255.0);
        assertTrue(KeyCodes.NUMPAD1.getTranslateX() == 128);
        assertTrue(KeyCodes.NUMPAD1.getTranslateY() == 600);
    }

    @Test
    public void powerups_get_ordinals() {
        assertTrue(Powerups.FREE_MISS.ordinal() == 0);
        assertTrue(Powerups.XP.ordinal() == 1);
        assertTrue(Powerups.PERSPECTIVE.ordinal() == 2);
    }

    @Test
    public void test_song_manager_get_song() {
        MusicGame game = MusicGame.getInstance();
        assertTrue(game.getSongManager().getSong(0).getTitle().equals("Cinematic Experience"));
    }

    @Test
    public void test_note_methods() {
        Note note = new Note(0, 0, KeyCodes.NUMPAD1);
        assertTrue(note.getTimeCode() == 0);
        assertTrue(note.getDuration() == 0);
        assertTrue(note.getKeyCode().equals(KeyCodes.NUMPAD1));
        note.setTimeCode(123);
        note.setDuration(100);
        note.setKeyCode(KeyCodes.NUMPAD2);
        assertTrue(note.getTimeCode() == 123);
        assertTrue(note.getDuration() == 100);
        assertTrue(note.getKeyCode().equals(KeyCodes.NUMPAD2));
    }

    @Test
    public void test_song_methods() {
        Song song = new Song("null", "TITLE", 2, List.of(), SongDifficulties.EASY, 4, 0);
        assertTrue(song.getSource().equals("null"));
        assertTrue(song.getTitle().equals("TITLE"));
        assertTrue(song.getLength() == 2);
        assertTrue(song.getNotes().size() == 0);
        assertTrue(song.getId() == 4);
        assertTrue(song.getDifficulty().equals(SongDifficulties.EASY));
        song.setSource("HERE");
        song.setTitle("WOW");
        song.setLength(3);
        song.setNotes(List.of(new Note(0, 0, null)));
        song.setDifficulty(SongDifficulties.HARD);
        song.setId(5);
        assertTrue(song.getSource().equals("HERE"));
        assertTrue(song.getTitle().equals("WOW"));
        assertTrue(song.getLength() == 3);
        assertTrue(song.getNotes().size() == 1);
        assertTrue(song.getDifficulty().equals(SongDifficulties.HARD));
        assertTrue(song.getId() == 5);
    }

    @Test
    public void test_note_processor() {
        NoteProcessor.getInstance();
        NoteProcessor noteProcessor = NoteProcessor.getInstance();
        noteProcessor.reset();
        List<Note> notes = new ArrayList<>(List.of(
                new Note(0, 5, KeyCodes.NUMPAD1),
                new Note(0, 3, KeyCodes.NUMPAD2),
                new Note(7, 77, KeyCodes.NUMPAD1),
                new Note(25, 54, KeyCodes.NUMPAD2),
                new Note(30, 5, KeyCodes.NUMPAD3),
                new Note(20, 5, KeyCodes.NUMPAD3)));
        notes.sort(null);
        noteProcessor.setActiveSong(new Song("nolocation", "Testing Song", 100, notes, SongDifficulties.EASY, 2, 0));
        assertTrue(noteProcessor.getActiveSong().getTitle().equals("Testing Song"));
        List<Note> nextNotes = noteProcessor.getVisibleNotes(7, 2);
        assertTrue(nextNotes.size() == 1);
        nextNotes.forEach(n -> {
            if (n.getKeyCode().equals(KeyCodes.NUMPAD1)) {
                assertTrue(n.getDuration() == 77);
            }
        });
    }

    @Test
    public void test_note_processor_weird_cases() {
        NoteProcessor.getInstance();
        NoteProcessor noteProcessor = NoteProcessor.getInstance();
        noteProcessor.reset();
        List<Note> notes = new ArrayList<>(List.of(
                new Note(0, 5, KeyCodes.NUMPAD1),
                new Note(0, 3, KeyCodes.NUMPAD2),
                new Note(7, 77, KeyCodes.NUMPAD1),
                new Note(25, 54, KeyCodes.NUMPAD2)));
        notes.sort(null);
        noteProcessor.setActiveSong(new Song("nolocation", "Testing Song", 100, notes, SongDifficulties.EASY, 2, 0));
    }

    @Test
    public void test_song_manager_size() {
        MusicGame game = MusicGame.getInstance();
        assertTrue(game.getSongManager().getSongs().size() >= 3);
    }

    @Test
    public void test_score_manager_getters_setters() {
        ScoreManager manager = new ScoreManager();
        manager.setScore(10);
        assertEquals(10, manager.getScore());
        manager.setScore(5);
        assertEquals(10, manager.getHighScore());
    }

    @Test
    public void test_keypress_in_model() {
        NoteProcessor.getInstance().reset();
        NoteProcessor.getInstance();
        MusicGame.getInstance().reset();
        MusicGame game = MusicGame.getInstance();
        game.processKeyPress(KeyCodes.NUMPAD1.getValue()); // Inactive state
        game.processKeyPress(KeyCodes.NUMPAD2.getValue());
        game.processKeyPress(KeyCodes.NUMPAD3.getValue());
        game.processKeyPress(KeyCodes.NUMPAD4.getValue());
        game.processKeyPress(KeyCodes.NUMPAD5.getValue());
        game.processKeyPress(KeyCodes.NUMPAD6.getValue());
        game.processKeyPress(KeyCodes.NUMPAD7.getValue());
        game.processKeyPress(KeyCodes.NUMPAD8.getValue());
        game.processKeyPress(KeyCodes.NUMPAD9.getValue());
        game.processKeyPress(KeyCodes.SHIFT.getValue());
        game.processKeyPress(KeyCodes.ESC.getValue());
        List<Note> notes = List.of(
            new Note(0, 2, KeyCodes.NUMPAD1),
            new Note(0, 2, KeyCodes.NUMPAD2),
            new Note(0, 2, KeyCodes.NUMPAD3),
            new Note(0, 2, KeyCodes.NUMPAD4),
            new Note(0, 2, KeyCodes.NUMPAD5),
            new Note(0, 2, KeyCodes.NUMPAD6),
            new Note(0, 2, KeyCodes.NUMPAD7),
            new Note(0, 2, KeyCodes.NUMPAD8),
            new Note(0, 2, KeyCodes.NUMPAD9));
        Song song = new Song("null", "null", 5, notes, SongDifficulties.EASY, 3, 0);
        game.getSongManager().addSong(song);
        game.startLevel(3);
        assertTrue(game.getState().equals(GameStates.PLAYING));
        game.pollVisibleNotes(0, true);
        game.processKeyPress(KeyCodes.NUMPAD1.getValue()); // Playing state
        game.processKeyPress(KeyCodes.NUMPAD2.getValue());
        game.processKeyPress(KeyCodes.NUMPAD3.getValue());
        game.processKeyPress(KeyCodes.NUMPAD4.getValue());
        game.processKeyPress(KeyCodes.NUMPAD5.getValue());
        game.processKeyPress(KeyCodes.NUMPAD6.getValue());
        game.processKeyPress(KeyCodes.NUMPAD7.getValue());
        game.processKeyPress(KeyCodes.NUMPAD8.getValue());
        game.processKeyPress(KeyCodes.NUMPAD9.getValue());
        game.processKeyPress(KeyCodes.SHIFT.getValue());
        game.processKeyPress(KeyCodes.ESC.getValue());
        game.getScoreManager().updateScore();
        assertEquals(18, game.getScoreManager().getScore());
        assertTrue(game.getState().equals(GameStates.PAUSED));
        game.processKeyPress(KeyCodes.ESC.getValue());
        assertTrue(game.getState().equals(GameStates.RESUME));
        game.pollVisibleNotes(0, true);
        game.processKeyRelease(KeyCodes.NUMPAD1.getValue()); // Resumed state
        game.processKeyRelease(KeyCodes.NUMPAD2.getValue());
        game.processKeyRelease(KeyCodes.NUMPAD3.getValue());
        game.processKeyRelease(KeyCodes.NUMPAD4.getValue());
        game.processKeyRelease(KeyCodes.NUMPAD5.getValue());
        game.processKeyRelease(KeyCodes.NUMPAD6.getValue());
        game.processKeyRelease(KeyCodes.NUMPAD7.getValue());
        game.processKeyRelease(KeyCodes.NUMPAD8.getValue());
        game.processKeyRelease(KeyCodes.NUMPAD9.getValue());
        game.processKeyRelease(KeyCodes.SHIFT.getValue());
        game.setPowerupCountdown(2);
        game.processKeyRelease(KeyCodes.SHIFT.getValue());
        game.processKeyRelease(KeyCodes.ESC.getValue());
        game.getScoreManager().updateScore();
        assertEquals(18, game.getScoreManager().getScore());
        game.finishLevel();
        assertTrue(game.getState().equals(GameStates.SCORES));
        game.processKeyPress(KeyCodes.NUMPAD1.getValue()); // Scores state
        game.processKeyPress(KeyCodes.NUMPAD2.getValue());
        game.processKeyPress(KeyCodes.NUMPAD3.getValue());
        game.processKeyPress(KeyCodes.NUMPAD4.getValue());
        game.processKeyPress(KeyCodes.NUMPAD5.getValue());
        game.processKeyPress(KeyCodes.NUMPAD6.getValue());
        game.processKeyPress(KeyCodes.NUMPAD7.getValue());
        game.processKeyPress(KeyCodes.NUMPAD8.getValue());
        game.processKeyPress(KeyCodes.NUMPAD9.getValue());
        game.processKeyPress(KeyCodes.SHIFT.getValue());
        game.processKeyPress(KeyCodes.ESC.getValue());
    }

    @Test
    public void test_all_states() {
        for (GameStates state : GameStates.values()) {
            MusicGame game = MusicGame.getInstance();
            game.setState(state);
            assertTrue(game.getState().equals(state));
            if (state.equals(GameStates.PLAYING) || state.equals(GameStates.RESUME)) {
                assertTrue(game.isActiveState());
            }
        }
    }

    @Test
    public void test_playing_state_empty_song() {
        NoteProcessor.getInstance().reset();
        NoteProcessor.getInstance();
        MusicGame.getInstance().reset();
        MusicGame game = MusicGame.getInstance();
        Song song = new Song("null2", "null2", 5, List.of(), SongDifficulties.EASY, 4, 0);
        game.getSongManager().addSong(song);
        game.startLevel(4);
        assertTrue(game.getState().equals(GameStates.PLAYING));
        game.pollVisibleNotes(0, true);
        game.processKeyPress(KeyCodes.NUMPAD1.getValue());
        game.processKeyPress(KeyCodes.NUMPAD2.getValue());
        game.processKeyPress(KeyCodes.NUMPAD3.getValue());
        game.processKeyPress(KeyCodes.NUMPAD4.getValue());
        game.processKeyPress(KeyCodes.NUMPAD5.getValue());
        game.processKeyPress(KeyCodes.NUMPAD6.getValue());
        game.processKeyPress(KeyCodes.NUMPAD7.getValue());
        game.processKeyPress(KeyCodes.NUMPAD8.getValue());
        game.processKeyPress(KeyCodes.NUMPAD9.getValue());
    }

    @Test
    public void test_invalid_key() {
        MusicGame game = MusicGame.getInstance();
        game.processKeyPress(999);
        game.processKeyRelease(999);
        assertTrue(game.getState().equals(GameStates.INACTIVE));
    }

    @Test
    public void test_saved_game() {
        SavedGame savedGame = new SavedGame(3, 3, 3);
        assertTrue(savedGame.getTimeCode() == 3);
        assertTrue(savedGame.getScore() == 3);
        assertTrue(savedGame.getSongID() == 3);
        savedGame.setTimeCode(4);
        savedGame.setScore(4);
        savedGame.setSongID(4);
        assertTrue(savedGame.getTimeCode() == 4);
        assertTrue(savedGame.getScore() == 4);
        assertTrue(savedGame.getSongID() == 4);
    }
}
