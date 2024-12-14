package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import model.enums.Powerups;
import model.observers.DefaultObserverImplementer;
import model.observers.PowerupObserver;

public class PowerupManager {
    private Queue<Powerups> availablePowerups;
    private PowerupObserver powerupObserver;

    public PowerupManager() {
        availablePowerups = new LinkedList<>();
        DefaultObserverImplementer observer = new DefaultObserverImplementer();
        setPowerupObserver(observer);
    }

    public Queue<Powerups> getAvailablePowerups() {
        return availablePowerups;
    }

    public void setPowerups(List<Powerups> powerups) {
        availablePowerups.addAll(powerups);
    }

    public void addPowerup() {
        Random rand = new Random();
        Powerups power = Powerups.values()[rand.nextInt(3)];
        if (availablePowerups.size() < 3) {
            availablePowerups.add(power);
            MusicGame.getInstance().changePowerups(false);
        }
    }

    public void usePowerup() {
        if (availablePowerups.size() > 0) {
            Powerups power = availablePowerups.poll();
            switch (power) {
                case FREE_MISS:
                    MusicGame.getInstance().getScoreManager().setNoMisses(true);
                    break;
                case XP:
                    MusicGame.getInstance().getScoreManager().setDoubleScoring(true);
                    break;
                default:
                    powerupObserver.updateGUIPerspective(true);
                    break;
            }
            MusicGame.getInstance().changePowerups(false);
            MusicGame.getInstance().setPowerupCountdown(5000);
        }
    }

    public void deactivatePowerup() {
        MusicGame.getInstance().getScoreManager().setNoMisses(false);
        MusicGame.getInstance().getScoreManager().setDoubleScoring(false);
        powerupObserver.updateGUIPerspective(false);
    }

    public void clearPowerups() {
        availablePowerups.clear();
        MusicGame.getInstance().changePowerups(true);
    }

    public PowerupObserver getPowerupObserver() {
        return powerupObserver;
    }

    public void setPowerupObserver(PowerupObserver observer) {
        powerupObserver = observer;
    }
}