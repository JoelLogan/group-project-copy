package model.observers;

import model.enums.GameStates;

public interface GameStatusObserver {
    public void updateGUIStatus(GameStates status);
}
