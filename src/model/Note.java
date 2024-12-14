package model;

import java.io.Serializable;

import model.enums.KeyCodes;

public class Note implements Serializable, Comparable<Note> {

    private double timeCode, duration;
    private KeyCodes keyCode;

    public Note(double timeCode, double duration, KeyCodes keyCode) {
        this.timeCode = timeCode;
        this.duration = duration;
        this.keyCode = keyCode;
    }

    public double getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(double timeCode) {
        this.timeCode = timeCode;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public KeyCodes getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(KeyCodes keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public int compareTo(Note otherNote) {
        return Double.compare(this.getTimeCode(), otherNote.getTimeCode()); // May be backwards
    }
}
