package model;

public class ScoreManager {
    private int score, highScore;
    private int[] activeScoringNotes = new int[9];
    private boolean doubleScoring = false;
    private boolean noMisses = false;
    private int streak = 0;

    public ScoreManager() {
        score = 0;
        highScore = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if (score > highScore) {
            setHighScore(score);
        }
        if (streak >= 200) {
            MusicGame.getInstance().getPowerupManager().addPowerup();
            streak = 0;
        }
    }

    public void addScore(int increment) {
        setScore(score + increment);
        if (increment > 0) {
            streak += increment;
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void activateScoring(int key, int score) {
        activeScoringNotes[key - 1] = score;
    }

    public void deactivateScoring(int key) {
        activeScoringNotes[key - 1] = 0;
    }

    public void setDoubleScoring(boolean doubleScoring) {
        this.doubleScoring = doubleScoring;
    }

    public void setNoMisses(boolean noMisses) {
        this.noMisses = noMisses;
    }

    public void updateScore() {
        for (int i : activeScoringNotes) {
            if (MusicGame.getInstance().isCheatMode()) {
                addScore(Math.abs(i));
            }
            else if (noMisses) {
                addScore(Math.max(0, i));
            } 
            else if (doubleScoring) {
                addScore(i * 2);
            }
            else {
                addScore(i);
            }
        }
    }
}
