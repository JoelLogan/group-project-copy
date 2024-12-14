package model;

import java.util.List;

import model.enums.GameStates;
import model.enums.KeyCodes;
import model.observers.DefaultObserverImplementer;
import model.observers.GameStatusObserver;
import model.observers.HudObserver;
import model.observers.NoteUpdateObserver;

public class MusicGame {

    private static MusicGame instance = null;
    private GameStatusObserver gameStatusObserver;
    private NoteUpdateObserver noteUpdateObserver;
    private HudObserver hudObserver;
    private SongManager songManager;
    private NoteProcessor noteProcessor = NoteProcessor.getInstance();
    private GameStates gameState;
    private ScoreManager scoreManager;
    private double timeCode;
    private PowerupManager powerupManager;
    private boolean cheatMode;
    private int powerupCountdown = 0;
    private double initialTimecode = 0; // For loading saved game
    private boolean savedSong;

    public final int VISIBLE_NOTE_TIME = 4000;

    private MusicGame() {
        DefaultSongs.saveDefaultSongs();
        songManager = new SongManager();
        updateSavedAndLoadGame(false);
        DefaultObserverImplementer implementer = new DefaultObserverImplementer();
        setGameStatusObserver(implementer);
        setNoteUpdateObserver(implementer);
        setHudObserver(implementer);
        gameState = GameStates.INACTIVE;
        cheatMode = false;
        scoreManager = new ScoreManager();
        powerupManager = new PowerupManager();
        powerupManager.setPowerupObserver(implementer);
    }

    public void startLevel(int id) {
        noteProcessor.setActiveSong(songManager.getSong(id));
        System.out.println("Starting level: " + songManager.getSong(id).getTitle());
        gameState = GameStates.PLAYING;
        gameStatusObserver.updateGUIStatus(gameState);
        changePowerups(false);
    }

    public void pauseLevel() {
        gameState = GameStates.PAUSED;
        gameStatusObserver.updateGUIStatus(gameState);
    }

    public void resumeLevel() {
        gameState = GameStates.RESUME;
        gameStatusObserver.updateGUIStatus(gameState);
    }

    public void finishLevel() {
        NoteProcessor.getInstance().getActiveSong().setHighScore(scoreManager.getHighScore());
        songManager.saveCurrentSong();
        gameState = GameStates.SCORES;
        gameStatusObserver.updateGUIStatus(gameState);
    }

    public void exitLevel() {
        gameState = GameStates.INACTIVE;
        scoreManager.setScore(0);
        gameStatusObserver.updateGUIStatus(gameState);
        noteUpdateObserver.updateNoteGUI(List.of(), true);
        noteProcessor.softReset();
    }

    /** Should not be used except for testing */
    public void setState(GameStates state) {
        gameState = state;
    }

    public void pollVisibleNotes(double timeCode, boolean visible) {
        this.timeCode = timeCode;
        noteUpdateObserver.updateNoteGUI(noteProcessor.getVisibleNotes(timeCode, VISIBLE_NOTE_TIME), visible);
        hudObserver.updateHUD(scoreManager.getScore());
        if (powerupCountdown > 0) {
            powerupCountdown--;
        } else {
            powerupManager.deactivatePowerup();
        }
    }

    public GameStates getState() {
        return gameState;
    }

    public boolean isCheatMode() {
        return cheatMode;
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode = cheatMode;
    }

    // Codes 97-105 are NumPad 1-9. 27 is ESC. They are implemented as ENUMs in
    // KeyCodes.java.

    public void processKeyPress(int keyCode) {
        KeyCodes key = getKeyCode(keyCode);
        if (key != null) {
            if (key == KeyCodes.ESC) {
                handleEscapeKey();
            } else if (isActiveState() && key != KeyCodes.SHIFT) {
                handleNumpadKeyPress(key);
            }
        }
    }

    private KeyCodes getKeyCode(int keyCode) {
        for (KeyCodes k : KeyCodes.values()) {
            if (k.getValue() == keyCode) {
                return k;
            }
        }
        return null;
    }

    private void handleEscapeKey() {
        if (isActiveState()) {
            pauseLevel();
        } else if (gameState == GameStates.PAUSED) {
            resumeLevel();
        }
    }

    private void handleNumpadKeyPress(KeyCodes key) {
        var currentNotes = noteProcessor.getVisibleNotes(timeCode, 0);
        boolean activeNote = currentNotes.stream().anyMatch(n -> n.getKeyCode() == key);
        int score = activeNote ? 2 : -1;
        scoreManager.activateScoring(key.ordinal() - 1, score);
    }

    public void processKeyRelease(int keyCode) {
        KeyCodes key = getKeyCode(keyCode);
        if (key != null) {
            if (key == KeyCodes.SHIFT) {
                handleShiftKey();
            } else if (key != KeyCodes.ESC) {
                handleNumpadKeyRelease(key);
            }
        }
    }

    private void handleShiftKey() {
        if (powerupCountdown <= 0) {
            getPowerupManager().usePowerup();
        }
    }

    private void handleNumpadKeyRelease(KeyCodes key) {
        scoreManager.deactivateScoring(key.ordinal() - 1);
    }

    public boolean isActiveState() {
        return gameState == GameStates.PLAYING || gameState == GameStates.RESUME;
    }

    public void changePowerups(boolean clear) {
        hudObserver.updatePowerups(powerupManager.getAvailablePowerups().toArray(), clear);
    }

    public void saveGameForLater() {
        SavedGame gameToSave = new SavedGame(timeCode, noteProcessor.getActiveSong().getId(), scoreManager.getScore());
        Serializer.serialize(gameToSave, DefaultSongs.SONGSLOCATION, songManager.SAVEDGAMENAME);
    }

    public PowerupManager getPowerupManager() {
        return powerupManager;
    }

    public int getPowerupCountdown() {
        return powerupCountdown;
    }

    public void setPowerupCountdown(int powerupCountdown) {
        this.powerupCountdown = powerupCountdown;
    }

    public double getInitialTimecode() {
        return initialTimecode;
    }

    public void setInitialTimecode(double initialTimecode) {
        this.initialTimecode = initialTimecode;
    }

    public boolean updateSavedAndLoadGame(boolean checkTemp) {
        savedSong = songManager.loadSongs(checkTemp);
        return savedSong;
    }

    public boolean isSongSaved() {
        return savedSong;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public SongManager getSongManager() {
        return songManager;
    }

    public static MusicGame getInstance() {
        if (instance == null) {
            instance = new MusicGame();
        }
        return instance;
    }

    public void reset() {
        instance = null;
        noteProcessor.softReset();
    }

    public void setGameStatusObserver(GameStatusObserver observer) {
        gameStatusObserver = observer;
    }

    public void setNoteUpdateObserver(NoteUpdateObserver observer) {
        noteUpdateObserver = observer;
    }

    public void setHudObserver(HudObserver observer) {
        hudObserver = observer;
    }
}
