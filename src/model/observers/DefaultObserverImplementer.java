package model.observers;

import java.util.List;

import model.Note;
import model.enums.GameStates;

public class DefaultObserverImplementer implements NoteUpdateObserver, GameStatusObserver, PowerupObserver, HudObserver {

    @Override
    public void updateNoteGUI(List<Note> notes, boolean visible) {
        // Placeholder GUI Update Listener
    }

    @Override
    public void updateGUIStatus(GameStates status) {
        // Placeholder GUI Status Update Listener
    }

    @Override
    public void updateGUIPerspective(boolean enable) {
        // Placeholder Powerup AV Update Listener
    }

    @Override
    public void updateHUD(int score) {
        // Placeholder HUD Update Listener
    }

    @Override
    public void updatePowerups(Object[] powerups, boolean clear) {
        // Placeholder Powerup Update Listener
    }

}
