package model;

import java.io.Serializable;

public class SavedGame implements Serializable {

    private double timeCode;
    private int songID;
    private int score;
    
    public SavedGame(double timeCode, int songID, int score) {
        this.timeCode = timeCode;
        this.songID = songID;
        this.score = score;
    }

    public double getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(double timeCode) {
        this.timeCode = timeCode;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
