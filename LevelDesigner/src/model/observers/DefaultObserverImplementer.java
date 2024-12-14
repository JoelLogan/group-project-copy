package model.observers;

public class DefaultObserverImplementer implements HudObserver, ErrorObserver {

    @Override
    public void updateHUD(int score) {
        // Blank implementation
    }

    @Override
    public void notifyError(String message) {
        // Blank implementation
    }

}
