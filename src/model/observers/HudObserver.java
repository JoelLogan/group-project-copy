package model.observers;

public interface HudObserver {
    public void updateHUD(int score);
    public void updatePowerups(Object[] powerups, boolean clear);
}
